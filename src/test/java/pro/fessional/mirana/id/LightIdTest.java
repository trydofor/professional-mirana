package pro.fessional.mirana.id;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2019-06-26
 */
public class LightIdTest {

    private final Random random = new Random();

    @Test
    public void testZero() {
        LightId zero = new LightId(0, 0);
        assertEquals(LightId.ZERO, zero);
        assertEquals(0, zero.component1());
        assertEquals(0, zero.component2());
    }

    @Test
    public void testCode() {
        for (int i = 1; i <= 10000; i++) {
            long seq = random.nextLong();

            LightId wid = new LightId(0, seq & LightId.MAX_SEQ_WHOLE);
            assertTrue(LightIdUtil.valid(wid));
            long wdi = wid.toLong();
            LightId wid1 = LightIdUtil.toLightId(wdi);
            assertEquals(wid, wid1);

            for (int j = 1; j <= 512; j++) {
                LightId bid = new LightId(j, seq & LightId.MAX_SEQ_BLOCK);
                assertTrue(LightIdUtil.valid(bid));
                long bdi = bid.toLong();
                LightId bid1 = LightIdUtil.toLightId(bdi);
                assertEquals(bid, bid1);
            }
        }
    }

    @Test
    public void testBound() {
        assertEquals(Integer.MAX_VALUE, LightIdUtil.sequenceInt(LightId.MAX_SEQ_WHOLE));
        assertEquals(Integer.MAX_VALUE, LightIdUtil.sequenceInt((1L << 33) - 1));
        assertEquals(Integer.MAX_VALUE, LightIdUtil.sequenceInt(Long.MAX_VALUE));
        assertEquals(0, LightIdUtil.sequenceInt(Long.MIN_VALUE));
        assertEquals(0, LightIdUtil.sequenceInt(0));
        int seq = 1097;
        LightId id = new LightId(1, seq);
        assertEquals(LightIdUtil.sequenceInt(id.toLong()), seq);
    }

    @Test
    public void testHash() {
        LightId n1 = new LightId(0, -1);
        LightId n2 = new LightId(-1, -2);
        assertEquals(n1, n2);
        assertEquals(n2, n1);
        assertEquals(LightId.NONE, n2);
        assertEquals(LightId.NONE, n1);
        assertEquals(n1, LightId.NONE);
        assertEquals(n2, LightId.NONE);

        assertEquals(n1.hashCode(), n2.hashCode());
        assertEquals(LightId.NONE.hashCode(), n2.hashCode());
    }


    @Test
    public void testForce() {
        try {
            for (int f = 0; f < 2; f++) {
                LightIdUtil.forceBlockFirst(f == 0);
                for (int b = 3; b < 23; b++) {
                    LightIdUtil.forceBlockBit(b);
                    LightId wid = new LightId(2, 9);
                    assertTrue(LightIdUtil.valid(wid));
                    long wdi = wid.toLong();
                    LightId wid1 = LightIdUtil.toLightId(wdi);
                    assertEquals(wid, wid1);
                    System.out.println(Long.toBinaryString(wdi) + "\t" +f + ":" + b + "|" + wdi + "\t" + wid1);
                }
            }
            System.out.println("=========");
            for (int f = 0; f < 2; f++) {
                LightIdUtil.forceBlockFirst(f == 0);
                for (int b = 3; b < 23; b++) {
                    LightIdUtil.forceBlockBit(b);
                    for (int i = 1; i <= 10000; i++) {
                        long seq = random.nextLong();

                        LightId wid = new LightId(b, seq & LightIdUtil.getSequenceMax());
                        assertTrue(LightIdUtil.valid(wid));
                        long wdi = wid.toLong();
                        LightId wid1 = LightIdUtil.toLightId(wdi);
                        assertEquals(wid, wid1);
                        if (i == 1) {
                            System.out.println(Long.toBinaryString(wdi) + "\t" + f + ":" + b + "|" + wdi + "\t" + wid1);
                        }

                        int max = (int) Math.pow(2, b);
                        for (int j = 1; j <= 512 && j <= max; j++) {
                            LightId bid = new LightId(j, seq & LightIdUtil.getSequenceMax());
                            assertTrue(LightIdUtil.valid(bid));
                            long bdi = bid.toLong();
                            LightId bid1 = LightIdUtil.toLightId(bdi);
                            assertEquals(bid, bid1);
                        }
                    }
                }
            }
        }
        finally {
            LightIdUtil.forceBlockFirst(true);
            LightIdUtil.forceBlockBit(LightId.BIT_BLOCK);
        }
    }
}
