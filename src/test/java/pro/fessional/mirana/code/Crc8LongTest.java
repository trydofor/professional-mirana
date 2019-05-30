package pro.fessional.mirana.code;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * @author trydofor
 * @since 2019-05-26
 */
public class Crc8LongTest {

    private Crc8Long crc8System = new Crc8Long();
    private Crc8Long crc8Custom = new Crc8Long(new int[]{56, 49, 42, 35, 28, 14, 7, 1});

    private Random random = new Random();

    private void checkRandom(Crc8Long crc8) {
        for (int i = 0; i < 100000; i++) {
            long e = crc8.encode(i);
            long d = crc8.decode(e);
            Assert.assertEquals(i, d);
        }
        for (int i = 0; i < 100000; i++) {
            long n = random.nextLong() & Crc8Long.MAX_NUMBER;
            long e = crc8.encode(n);
            long d = crc8.decode(e);
            Assert.assertEquals(n, d);
        }
    }

    private void checkBound(Crc8Long crc8) {
        long e2 = crc8.encode(Crc8Long.MAX_NUMBER);
        long d2 = crc8.decode(e2);
        Assert.assertEquals(Crc8Long.MAX_NUMBER, d2);

        long e1 = crc8.encode(Crc8Long.MIN_NUMBER);
        long d1 = crc8.decode(e1);
        Assert.assertEquals(Crc8Long.MIN_NUMBER, d1);
    }

    @Test
    public void testRandom() {
        checkRandom(crc8System);
        checkRandom(crc8Custom);
    }

    @Test
    public void testound() {
        checkBound(crc8System);
        checkBound(crc8Custom);
    }
}