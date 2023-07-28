package pro.fessional.mirana.io;

import org.jetbrains.annotations.NotNull;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * ignore close() on this stream
 *
 * @author trydofor
 * @since 2022-06-05
 */
public class NonCloseStream {


    public static InputStream nonClosing(InputStream in) {
        return new NonCloseInputStream(in);
    }

    public static OutputStream nonClosing(OutputStream out) {
        return new NonCloseOutputStream(out);
    }

    public static Reader nonClosing(Reader in) {
        return new NonCloseReader(in);
    }

    public static Writer nonClosing(Writer out) {
        return new NonCloseWriter(out);
    }

    public static class NonCloseInputStream extends FilterInputStream {
        protected NonCloseInputStream(InputStream in) {
            super(in);
        }

        @Override
        public void close() {
            // skip
        }
    }

    public static class NonCloseOutputStream extends FilterOutputStream {

        public NonCloseOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void close() {
            // skip
        }
    }

    public static class NonCloseReader extends FilterReader {
        protected NonCloseReader(@NotNull Reader in) {
            super(in);
        }

        @Override
        public void close() {
            // skip
        }
    }

    public static class NonCloseWriter extends FilterWriter {

        protected NonCloseWriter(Writer out) {
            super(out);
        }

        @Override
        public void close() {
            // skip
        }
    }
}
