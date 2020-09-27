package pro.fessional.mirana.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
        Assert.assertEquals(str, new String(b1));

        byte[] b2 = InputStreams.readBytes(cis);
        Assert.assertEquals(str, new String(b2));

        byte[] b3 = InputStreams.readBytes(cis);
        Assert.assertEquals(str, new String(b3));
    }

    @Test
    public void available() throws IOException {
        byte[] bytes = str.getBytes();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        CircleInputStream cis = new CircleInputStream(new ByteArrayInputStream(bytes));
        int b0 = bis.available();
        int c0 = cis.available();
        Assert.assertEquals(b0, c0);
        Assert.assertEquals(bis.skip(5L), cis.skip(5L));
        Assert.assertEquals(bis.available(), cis.available());
        Assert.assertEquals(bis.skip(5L), cis.skip(5L));
        Assert.assertEquals(bis.available(), cis.available());
        Assert.assertEquals(bis.skip(5L), cis.skip(5L));
        Assert.assertEquals(bis.available(), cis.available());
        Assert.assertEquals(bis.skip(5L), cis.skip(5L));
        Assert.assertEquals(bis.available(), cis.available());
        Assert.assertEquals(-1, bis.read());
        Assert.assertEquals(-1, cis.read());
        Assert.assertEquals(0, bis.available());
        Assert.assertEquals(b0, cis.available());
    }

    @Test
    public void reset() throws IOException {
        byte[] bytes = str.getBytes();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        CircleInputStream cis = new CircleInputStream(new ByteArrayInputStream(bytes));
        int b0 = bis.available();
        Assert.assertEquals(bis.read(), cis.read());
        bis.reset();
        cis.reset();
        Assert.assertEquals(b0, bis.available());
        Assert.assertEquals(b0, cis.available());

        Assert.assertEquals(bis.read(), cis.read());
        bis.mark(100);
        cis.mark(100);
        Assert.assertEquals(bis.read(), cis.read());
        bis.reset();
        cis.reset();
        Assert.assertEquals(b0 - 1, bis.available());
        Assert.assertEquals(b0 - 1, cis.available());

        byte[] b1 = InputStreams.readBytes(bis);
        Assert.assertEquals(str.substring(1), new String(b1));

        byte[] c1 = InputStreams.readBytes(cis);
        Assert.assertEquals(str.substring(1), new String(c1));

        byte[] c2 = InputStreams.readBytes(cis);
        Assert.assertEquals(str, new String(c2));
    }
}