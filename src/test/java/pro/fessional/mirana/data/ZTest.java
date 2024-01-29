package pro.fessional.mirana.data;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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
        assertSame(list, Z.uniq(list));
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
        assertNull(Z.find(it -> it.one() == 2, U.of(1, "A")));
    }

    @Test
    public void testMake() {
        BigDecimal d = Z.make(BigDecimal::new, "null", null, "1");
        assertEquals(d, BigDecimal.ONE);
    }

    @Test
    public void notNull() {
        assertEquals("1", Z.notNull(null, "1"));
        assertEquals(Null.Str, Z.notNull(Null.Str, null, "1"));
        final String str = null;
        assertEquals("", Z.notNullSafe(Null.Str, str));
        assertEquals("1", Z.notNullSafe(Null.Str, null, "1"));
    }

    @Test
    public void notEmpty() {
        assertEquals("1", Z.notEmpty(null, "1"));
        assertEquals("1", Z.notEmpty(Null.Str, null, "1"));
        final String str = null;
        assertEquals("", Z.notEmptySafe(Null.Str, str));
        assertEquals("1", Z.notEmptySafe(Null.Str, null, "1"));
    }

    @Test
    public void notBlank() {
        assertEquals("1", Z.notBlank(null, "1"));
        assertEquals("1", Z.notBlank(Null.Str, null, "1"));
        final String str = null;
        assertEquals("", Z.notBlankSafe(Null.Str, str));
        assertEquals("1", Z.notBlankSafe(Null.Str, null, "1"));
    }

    @Test
    public void decimal() {
        assertEquals(new BigDecimal("1"), Z.decimal(null, "1"));
        assertEquals(1, Z.int32(null, "1"));
        assertEquals(1L, Z.int64(null, "1"));
    }
}
