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

    @Test
    public void testAll() {
        LightId zero = new LightId(0, 0);
        assertEquals(LightId.ZERO, zero);
        assertEquals(0, zero.component1());
        assertEquals(0, zero.component2());
    }

    @Test
    public void testCode() {
        Random random = new Random();
        for (int i = 1; i <= 10000; i++) {

            long seq = random.nextLong() & LightId.MAX_SEQUENCE;
            LightId id = new LightId(1, seq);
            assertTrue(LightIdUtil.valid(id));
            long di = id.toLong();
            LightId id1 = LightIdUtil.toLightId(di);
            assertEquals(id, id1);
        }
    }

    @Test
    public void testBound() {
        assertEquals(Integer.MAX_VALUE, LightIdUtil.sequenceInt(LightId.MAX_SEQUENCE));
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
}
