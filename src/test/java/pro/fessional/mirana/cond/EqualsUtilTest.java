package pro.fessional.mirana.cond;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-04-01
 */
public class EqualsUtilTest {

    @Test
    public void eqVal() {
        byte byte1 = 1;
        short short1 = 1;
        int int1 = 1;
        long long1 = 1L;
        float float1 = 1F;
        double double1 = 1D;
        BigDecimal bigDecimal1 = BigDecimal.ONE;
        BigInteger bigInteger1 = BigInteger.ONE;
        AtomicInteger atomicInteger1 = new AtomicInteger(1);

        Number[] nums = new Number[]{
                byte1,
                short1,
                int1,
                long1,
                float1,
                double1,
                bigDecimal1,
                bigInteger1,
                atomicInteger1,
                };

        for (Number n1 : nums) {
            for (Number n2 : nums) {
                assertTrue(EqualsUtil.eqVal(n1, n2));
            }
        }
    }

    @Test
    public void inVal() {
        assertTrue(EqualsUtil.inVal(1, 1L));
        assertTrue(EqualsUtil.inVal(1, new Number[]{1, 2, 3}));
        assertTrue(EqualsUtil.inVal(1, 1, 2, 3));
        assertTrue(EqualsUtil.inVal(1, Arrays.asList(1, 2, 3)));
        assertTrue(EqualsUtil.inVal(1, 1L, 2L, 3));
        assertTrue(EqualsUtil.inVal(1, 1, 2L, 3));
        assertTrue(EqualsUtil.inVal(1, new BigDecimal(1), 2L, 3));

        assertTrue(EqualsUtil.inVal(new BigDecimal("1.0"), new BigDecimal(1), 2L, 3));
        assertFalse(EqualsUtil.inVal(new BigDecimal("4"), new BigDecimal(1), 2L, 3));

        {
            int[] arr = {1, 2, 3};
            assertTrue(EqualsUtil.inVal(new BigDecimal("1.0"), arr));
            assertFalse(EqualsUtil.inVal(new BigDecimal("4"), arr));
        }
        {
            long[] arr = {1, 2, 3};
            assertTrue(EqualsUtil.inVal(new BigDecimal("1.0"), arr));
            assertFalse(EqualsUtil.inVal(new BigDecimal("4"), arr));
        }
        {
            float[] arr = {1, 2, 3};
            assertTrue(EqualsUtil.inVal(new BigDecimal("1.0"), arr));
            assertFalse(EqualsUtil.inVal(new BigDecimal("4"), arr));
        }
        {
            double[] arr = {1, 2, 3};
            assertTrue(EqualsUtil.inVal(new BigDecimal("1.0"), arr));
            assertFalse(EqualsUtil.inVal(new BigDecimal("4"), arr));
        }
    }

    @Test
    public void inCase() {
        assertTrue(EqualsUtil.inCase("1", "1"));
        assertTrue(EqualsUtil.inCase("1", "1", "2"));
        assertTrue(EqualsUtil.inCase("1", new String[]{"1", "2"}));
        assertTrue(EqualsUtil.inCase("1", Arrays.asList("1", "2")));
        assertFalse(EqualsUtil.inCase("3", Arrays.asList("1", "2")));

        assertTrue(EqualsUtil.inCaseless("a", "A"));
        assertTrue(EqualsUtil.inCaseless("a", "A", "2"));
        assertTrue(EqualsUtil.inCaseless("a", new String[]{"A", "2"}));
        assertTrue(EqualsUtil.inCaseless("a", Arrays.asList("A", "2")));
        assertFalse(EqualsUtil.inCaseless("b", Arrays.asList("A", "2")));
    }

}
