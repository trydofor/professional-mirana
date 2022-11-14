package pro.fessional.mirana.io;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-09-25
 */
public class CircleInputStreamTest {

    private final String str = "POM is a girl";
    final File tmp = InputStreams.saveTemp(new ByteArrayInputStream(str.getBytes()));

    @Test
    public void read() throws IOException {
        read(new ByteArrayInputStream(str.getBytes()));
        read(new FileInputStream(tmp));
        read(new CircleInputStream(tmp));
        read(new CircleInputStream(str.getBytes()));
    }

    private void read(InputStream bis) {
        CircleInputStream cis = new CircleInputStream(bis);
        byte[] b1 = InputStreams.readBytes(cis);
        assertEquals(str, new String(b1));

        byte[] b2 = InputStreams.readBytes(cis);
        assertEquals(str, new String(b2));

        byte[] b3 = InputStreams.readBytes(cis);
        assertEquals(str, new String(b3));
    }

    @Test
    public void available() throws IOException {
        byte[] bytes = str.getBytes();
        available(new ByteArrayInputStream(bytes), new ByteArrayInputStream(bytes));
        available(new FileInputStream(tmp), new FileInputStream(tmp));
        available(new FileInputStream(tmp), new CircleInputStream(tmp));
        available(new ByteArrayInputStream(bytes), new CircleInputStream(str.getBytes()));
    }

    private void available(InputStream bis, InputStream in2) throws IOException {
        CircleInputStream cis = new CircleInputStream(in2);
        int b0 = bis.available();
        int c0 = cis.available();
        assertEquals(b0, c0);
        assertEquals(bis.skip(5L), cis.skip(5L));
        assertEquals(bis.available(), cis.available());
        assertEquals(bis.skip(5L), cis.skip(5L));
        assertEquals(bis.available(), cis.available());
        assertEquals(bis.skip(5L), cis.skip(5L));
        assertEquals(bis.available(), cis.available());
        assertEquals(bis.skip(5L), cis.skip(5L));
        assertEquals(bis.available(), cis.available());
        assertEquals(-1, bis.read());
        assertEquals(-1, cis.read());
        assertEquals(0, bis.available());
        assertEquals(b0, cis.available());
    }

    @Test
    public void reset() throws IOException {
        byte[] bytes = str.getBytes();
        reset(new ByteArrayInputStream(bytes), new ByteArrayInputStream(bytes));
        reset(new FileInputStream(tmp), new FileInputStream(tmp));
        reset(new ByteArrayInputStream(bytes), new CircleInputStream(bytes));
        reset(new FileInputStream(tmp), new CircleInputStream(tmp));
    }

    public void reset(InputStream bis, InputStream in2) throws IOException {
        CircleInputStream cis = new CircleInputStream(in2);
        int b0 = bis.available();
        assertEquals(bis.read(), cis.read());
        if (bis.markSupported()) {
            bis.reset();
            assertEquals(b0, bis.available());
        }
        else {
            assertEquals(b0 - 1, bis.available());
        }
        if (cis.markSupported()) {
            cis.reset();
            assertEquals(b0, cis.available());
        }
        else {
            assertEquals(b0 - 1, cis.available());
        }

        assertEquals(bis.read(), cis.read());
        bis.mark(100);
        cis.mark(100);
        assertEquals(bis.read(), cis.read());
        if (bis.markSupported()) {
            bis.reset();
            assertEquals(b0 - 1, bis.available());
        }
        else {
            assertEquals(b0 - 3, bis.available());
        }
        if (cis.markSupported()) {
            cis.reset();
            assertEquals(b0 - 1, cis.available());
        }
        else {
            assertEquals(b0 - 3, cis.available());
        }

        byte[] b1 = InputStreams.readBytes(bis);
        if (bis.markSupported()) {
            assertEquals(str.substring(1), new String(b1));
        }
        else {
            assertEquals(str.substring(3), new String(b1));
        }

        byte[] c1 = InputStreams.readBytes(cis);
        if (cis.markSupported()) {
            assertEquals(str.substring(1), new String(c1));
        }
        else {
            assertEquals(str.substring(3), new String(c1));
        }

        //
        byte[] c2 = InputStreams.readBytes(cis);
        assertEquals(str, new String(c2));
    }

    @Test
    public void file() throws FileNotFoundException {

        CircleInputStream cis = new CircleInputStream(new FileInputStream(tmp));
        String b1 = InputStreams.readText(cis);
        assertEquals(str, b1);

        String b2 = InputStreams.readText(cis);
        assertEquals(str, b2);

        String b3 = InputStreams.readText(cis);
        assertEquals(str, b3);
    }
}
