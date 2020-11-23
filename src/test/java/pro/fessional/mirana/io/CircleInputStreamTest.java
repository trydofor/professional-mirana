package pro.fessional.mirana.io;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-09-25
 */
public class CircleInputStreamTest {

    private final String str = "POM is a girl";

    @Test
    public void read() {
        ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes());
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

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        CircleInputStream cis = new CircleInputStream(new ByteArrayInputStream(bytes));
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

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        CircleInputStream cis = new CircleInputStream(new ByteArrayInputStream(bytes));
        int b0 = bis.available();
        assertEquals(bis.read(), cis.read());
        bis.reset();
        cis.reset();
        assertEquals(b0, bis.available());
        assertEquals(b0, cis.available());

        assertEquals(bis.read(), cis.read());
        bis.mark(100);
        cis.mark(100);
        assertEquals(bis.read(), cis.read());
        bis.reset();
        cis.reset();
        assertEquals(b0 - 1, bis.available());
        assertEquals(b0 - 1, cis.available());

        byte[] b1 = InputStreams.readBytes(bis);
        assertEquals(str.substring(1), new String(b1));

        byte[] c1 = InputStreams.readBytes(cis);
        assertEquals(str.substring(1), new String(c1));

        byte[] c2 = InputStreams.readBytes(cis);
        assertEquals(str, new String(c2));
    }
}