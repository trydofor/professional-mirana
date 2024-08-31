package pro.fessional.mirana.code;


import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-05-26
 */
public class Crc8LongTest {

    private final Random random = new Random();

    private void checkRandom(Crc8Long crc8) {
        for (int i = 0; i < 100000; i++) {
            long e = crc8.encode(i);
            long d = crc8.decode(e);
            assertEquals(i, d);
        }
        for (int i = 0; i < 100000; i++) {
            long n = random.nextLong() & Crc8Long.MAX_NUMBER;
            long e = crc8.encode(n);
            long d = crc8.decode(e);
            assertEquals(n, d);
        }
    }

    private void checkBound(Crc8Long crc8) {
        long e2 = crc8.encode(Crc8Long.MAX_NUMBER);
        long d2 = crc8.decode(e2);
        assertEquals(Crc8Long.MAX_NUMBER, d2);

        long e1 = crc8.encode(Crc8Long.MIN_NUMBER);
        long d1 = crc8.decode(e1);
        assertEquals(Crc8Long.MIN_NUMBER, d1);
    }

    @Test
    public void testRandom() {
        checkRandom(Crc8LongUtil.BIG);
        checkRandom(Crc8LongUtil.MID);
        checkRandom(Crc8LongUtil.LOW);
    }

    @Test
    public void testBound() {
        checkBound(Crc8LongUtil.BIG);
        checkBound(Crc8LongUtil.MID);
        checkBound(Crc8LongUtil.LOW);
    }

    @Test
    public void print() {
        for (int i = 0; i < 100; i++) {
            long b = Crc8LongUtil.BIG.encode(i);
            long m = Crc8LongUtil.MID.encode(i);
            long l = Crc8LongUtil.LOW.encode(i);

            Testing.printf("\n%d\t\t\t%d\t\t\t%d\t\t\t%d", i, b, m, l);
        }
    }
}