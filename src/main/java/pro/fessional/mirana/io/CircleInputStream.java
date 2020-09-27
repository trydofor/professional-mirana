package pro.fessional.mirana.io;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 当读完stream，即-1时，再次read时，自动从头读起，重新available。
 * 特殊需要，非线程安全，谨慎使用，尤其要正确使用mark, reset功能。
 *
 * @author trydofor
 * @since 2020-09-25
 */
public class CircleInputStream extends InputStream {

    protected InputStream backend;
    protected ByteArrayOutputStream cache = new ByteArrayOutputStream();

    protected ByteArrayInputStream circle = null;
    protected byte[] content = null;
    private boolean finished = false;

    public CircleInputStream(InputStream backend) {
        this.backend = backend;
    }

    /**
     * 是否本次以读完流(-1)
     *
     * @return 是否已读完
     */
    public boolean isFinished() {
        return finished;
    }

    private void switchIfCircle() {
        if (content == null) {
            content = cache.toByteArray();
            finished = false;
        }
        if (circle == null) {
            circle = new ByteArrayInputStream(content);
            finished = false;
        }
    }

    @Override
    public int read() throws IOException {
        if (backend == null) {
            switchIfCircle();
            int c = circle.read();
            if (c < 0) {
                finished = true;
                circle = null;
            }
            return c;
        } else {
            int c = backend.read();
            if (c < 0) {
                finished = true;
                backend = null;
            } else {
                cache.write(c);
            }
            return c;
        }
    }

    @Override
    public int read(@NotNull byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(@NotNull byte[] b, int off, int len) throws IOException {
        if (backend == null) {
            switchIfCircle();
            int c = circle.read(b, off, len);
            if (c < 0) {
                finished = true;
                circle = null;
            }
            return c;
        } else {
            int c = backend.read(b, off, len);
            if (c < 0) {
                finished = true;
                backend = null;
            } else {
                cache.write(b, 0, c);
            }
            return c;
        }
    }

    @Override
    public long skip(long n) throws IOException {
        if (backend == null) {
            return circle == null ? 0 : circle.skip(n);
        } else {
            long r = n;
            for (int c; r > 0 && (c = backend.read()) >= 0; r--) {
                cache.write(c);
            }
            return n - r;
        }
    }

    @Override
    public int available() throws IOException {
        if (backend == null) {
            switchIfCircle();
            return circle.available();
        } else {
            return backend.available();
        }
    }

    @Override
    public void close() throws IOException {
        if (backend != null) {
            backend.close();
        }
    }

    private byte[] markBytes = null;

    @Override
    public void mark(int readlimit) {
        if (backend == null) {
            if (circle != null) {
                circle.mark(readlimit);
            }
        } else {
            markBytes = cache.toByteArray();
            backend.mark(readlimit);
        }
    }

    @Override
    public void reset() throws IOException {
        if (backend == null) {
            if (circle != null) {
                circle.reset();
            }
        } else {
            backend.reset();
            cache.reset();
            if (markBytes != null) {
                cache.write(markBytes);
                markBytes = null;
            }
        }
    }

    @Override
    public boolean markSupported() {
        if (backend == null) {
            return circle != null;
        } else {
            return backend.markSupported();
        }
    }
}
