package pro.fessional.mirana.cond;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-04-01
 */
public class ContainsTest {

    @Test
    public void hasVal() {
        assertTrue(Contains.eqVal(1, 1, 2, 3));
        assertTrue(Contains.eqVal(1, Arrays.asList(1, 2, 3)));
        assertTrue(Contains.eqVal(1, 1L, 2L, 3));
        assertTrue(Contains.eqVal(1, 1, 2L, 3));
        assertTrue(Contains.eqVal(1, new BigDecimal(1), 2L, 3));
        assertTrue(Contains.eqVal(new BigDecimal("1.0"), new BigDecimal(1), 2L, 3));
    }

    @Test
    public void testStr() {
        assertTrue(Contains.eqCase("1", "1", "2"));
        assertTrue(Contains.eqCase("1", Arrays.asList("1", "2")));
        assertTrue(Contains.igCase("a", "A", "2"));
        assertTrue(Contains.igCase("a", Arrays.asList("A", "2")));
    }

}