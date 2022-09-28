package pro.fessional.mirana.data;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-10-29
 */
public class ZTest {

    @Test
    public void testUniq() {
        List<U.Two<Integer, String>> list = Arrays.asList(
                U.of(1, "A"),
                U.of(2, "B"),
                U.of(1, "A"),
                U.of(2, "B")
        );
        List<U.Two<Integer, String>> uniq = Z.uniq(list, U.Two::one, U.Two::two);
        assertEquals(uniq, Arrays.asList(
                U.of(1, "A"),
                U.of(2, "B")));
    }

    @Test
    public void testFind() {
        U.Two<Integer, String> u = Z.find(it -> it.one() == 1, U.of(1, "A"),
                U.of(2, "B"),
                U.of(1, "A"));
        assertEquals(u, U.of(1, "A"));
    }

    @Test
    public void testMake() {
        BigDecimal d = Z.make(BigDecimal::new, "null", null, "1");
        assertEquals(d, BigDecimal.ONE);
    }

    @Test
    public void testNotNull() {
        assertEquals("1", Z.notNull(null, "1"));
        assertEquals(Null.Str, Z.notNull(Null.Str, null, "1"));
        final String str = null;
        assertEquals("1", Z.notNullSafe(Null.Str, str));
        assertEquals("1", Z.notNullSafe(Null.Str, null, "1"));
    }
}
