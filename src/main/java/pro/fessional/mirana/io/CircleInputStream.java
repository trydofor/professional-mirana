package pro.fessional.mirana.io;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.best.DummyBlock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * <pre>
 * 采用byte[]缓存读过的流，当读完stream，即-1时，
 * 再次read时，自动从头读起，重新available。
 * 特殊需要，非线程安全，谨慎使用，尤其要正确使用mark, reset功能。
 *
 * 仅对ByteArrayInputStream和由文件构造的FileInputStream进行了优化。
 * 其他Stream，会缓存全部读取内容，需要注意内存占用。
 * 若原 InputStream 支持重复读，从性能考虑，应该使用 NonCloseStream 代替
 * </pre>
 *
 * @author trydofor
 * @see NonCloseStream
 * @since 2020-09-25
 */
public class CircleInputStream extends InputStream {

    @NotNull
    protected InputStream backend;
    protected ByteArrayOutputStream caching = null;
    protected File file = null;

    protected byte[] content = null;
    protected int offset = 0;
    protected int length = 0;

    private boolean finished = false;

    public CircleInputStream(@NotNull InputStream backend) {
        if (backend instanceof CircleInputStream) {
            CircleInputStream cis = (CircleInputStream) backend;
            this.backend = cis.backend;
            this.caching = cis.caching;
            this.file = cis.file;
            this.content = cis.content;
            this.offset = cis.offset;
            this.length = cis.length;
            this.finished = cis.finished;
            return;
        }
        else if (backend instanceof FileInputStream && FileInputStreamPath != null) {
            try {
                final String path = (String) FileInputStreamPath.get(backend);
                if (path != null) {
                    this.file = new File(path);
                    this.backend = backend;
                    return;
                }
            }
            catch (Exception e) {
                DummyBlock.ignore(e);
            }
        }
        else if (backend instanceof ByteArrayInputStream && ByteArrayInputStreamBuf != null && ByteArrayInputStreamPos != null && ByteArrayInputStreamCount != null) {
            try {
                final byte[] buf = (byte[]) ByteArrayInputStreamBuf.get(backend);
                final int pos = ByteArrayInputStreamPos.getInt(backend);
                final int cnt = ByteArrayInputStreamCount.getInt(backend);
                this.content = buf;
                this.offset = pos;
                this.length = cnt;
                this.backend = backend;
                return;
            }
            catch (Exception e) {
                DummyBlock.ignore(e);
            }
        }

        this.backend = backend;
        this.caching = new ByteArrayOutputStream();
    }

    public CircleInputStream(@NotNull File file) {
        this.backend = initByFile(file);
    }

    public CircleInputStream(@NotNull ByteArrayOutputStream cache) {
        this.backend = initByByte(cache.toByteArray());
    }

    public CircleInputStream(byte @NotNull [] cache) {
        this.backend = initByByte(cache);
    }

    public CircleInputStream(byte @NotNull [] cache, int offset, int length) {
        this.backend = initByByte(cache, offset, length);
    }

    /**
     * 是否本次以读完流(-1)
     *
     * @return 是否已读完
     */
    public boolean isFinished() {
        return finished;
    }

    private InputStream initByFile(@NotNull File file) {
        try {
            this.file = file;
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private InputStream initByByte(byte @NotNull [] bytes) {
        return initByByte(bytes, 0, bytes.length);
    }

    private InputStream initByByte(byte @NotNull [] bytes, int off, int len) {
        content = bytes;
        offset = off;
        length = len;
        return new ByteArrayInputStream(content, offset, length);
    }

    private void renewBackend() {
        if (content == null && caching != null) {
            content = caching.toByteArray();
            caching = null;
            offset = 0;
            length = content.length;
        }

        try {
            backend.close();
        }
        catch (IOException e) {
            DummyBlock.ignore(e);
        }

        if (content != null) {
            backend = new ByteArrayInputStream(content, offset, length);
        }
        else {
            try {
                backend = new FileInputStream(file);
            }
            catch (FileNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        finished = false;
    }

    @Override
    public int read() throws IOException {
        // 已读完或缓存
        if (finished) {
            renewBackend();
        }

        // 源流读
        int c = backend.read();
        if (c < 0) {
            finished = true;
        }
        else {
            if (caching != null) {
                caching.write(c);
            }
        }
        return c;

    }

    @Override
    public int read(byte @NotNull [] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte @NotNull [] b, int off, int len) throws IOException {
        // 已读完或缓存
        if (finished) {
            renewBackend();
        }

        // 源流读
        int c = backend.read(b, off, len);
        if (c < 0) {
            finished = true;
        }
        else {
            if (caching != null) {
                caching.write(b, off, c);
            }
        }
        return c;

    }

    @Override
    public long skip(long n) throws IOException {
        // 源流读
        if (caching != null) {
            long r = n;
            for (; r > 0; r--) {
                int c = backend.read();
                if (c < 0) {
                    finished = true;
                }
                else {
                    caching.write(c);
                }
            }
            return n - r;
        }

        return backend.skip(n);
    }

    @Override
    public int available() throws IOException {
        if (finished) {
            if (file != null) {
                return (int) file.length();
            }
            if (content != null) {
                return content.length;
            }
            if (caching != null) {
                return caching.size();
            }
        }
        return backend.available();
    }

    @Override
    public void close() throws IOException {
        finished = true;
        backend.close();
    }

    private byte[] markBytes = null;

    @Override
    public void mark(int readlimit) {
        if (caching != null) {
            markBytes = caching.toByteArray();
        }
        backend.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        backend.reset();
        if (caching != null && markBytes != null) {
            caching.reset();
            caching.write(markBytes);
            markBytes = null;
        }
    }

    @Override
    public boolean markSupported() {
        return backend.markSupported();
    }

    public byte[] getContent() {
        return content;
    }

    public File getFile() {
        return file;
    }

    ////
    private static final Field FileInputStreamPath = reflectField(FileInputStream.class, "path");
    private static final Field ByteArrayInputStreamBuf = reflectField(ByteArrayInputStream.class, "buf");
    private static final Field ByteArrayInputStreamPos = reflectField(ByteArrayInputStream.class, "pos");
    private static final Field ByteArrayInputStreamCount = reflectField(ByteArrayInputStream.class, "count");

    private static Field reflectField(Class<?> clz, String fld) {
        try {
            final Field fd = clz.getDeclaredField(fld);
            fd.setAccessible(true);
            return fd;
        }
        catch (NoSuchFieldException e) {
            return null;
        }
    }
}
