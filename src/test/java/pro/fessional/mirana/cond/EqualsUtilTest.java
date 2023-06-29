package pro.fessional.mirana.cond;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-04-01
 */
public class EqualsUtilTest {

    @Test
    public void hasVal() {
        assertTrue(EqualsUtil.inVal(1, 1L));
        assertTrue(EqualsUtil.inVal(1, new Number[]{1, 2, 3}));
        assertTrue(EqualsUtil.inVal(1, 1, 2, 3));
        assertTrue(EqualsUtil.inVal(1, Arrays.asList(1, 2, 3)));
        assertTrue(EqualsUtil.inVal(1, 1L, 2L, 3));
        assertTrue(EqualsUtil.inVal(1, 1, 2L, 3));
        assertTrue(EqualsUtil.inVal(1, new BigDecimal(1), 2L, 3));
        assertTrue(EqualsUtil.inVal(new BigDecimal("1.0"), new BigDecimal(1), 2L, 3));
    }

    @Test
    public void testStr() {
        assertTrue(EqualsUtil.inCase("1", "1", "2"));
        assertTrue(EqualsUtil.inCase("1", Arrays.asList("1", "2")));
        assertTrue(EqualsUtil.inCaseless("a", "A", "2"));
        assertTrue(EqualsUtil.inCaseless("a", Arrays.asList("A", "2")));
    }

}
