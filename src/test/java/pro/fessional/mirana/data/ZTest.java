package pro.fessional.mirana.data;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<Integer> set0 = new HashSet<>();
        set0.add(1);
        assertEquals(Collections.singletonList(1), Z.uniq(set0));
    }

    @Test
    public void testFind() {
        U.Two<Integer, String> u = Z.find(it -> it.one() == 1, U.of(1, "A"),
                U.of(2, "B"),
                U.of(1, "A"));
        assertEquals(u, U.of(1, "A"));
        assertNull(Z.find(it -> it.one() == 2, U.of(1, "A")));
        assertEquals(U.of(2, "B"), Z.findSure(U.of(2, "B"), it -> it.one() == 2, U.of(1, "A")));
        assertEquals(U.of(2, "B"), Z.findSure(U.of(2, "B"), it -> it.one() == 2, Collections.singletonList(U.of(1, "A"))));
        assertEquals(U.of(2, "B"), Z.findSafe(() -> U.of(2, "B"), it -> it.one() == 2, U.of(1, "A")));
        assertEquals(U.of(2, "B"), Z.findSafe(() -> U.of(2, "B"), it -> it.one() == 2, Collections.singletonList(U.of(1, "A"))));
    }

    @Test
    public void testMake() {
        BigDecimal d1 = Z.make(BigDecimal::new, "null", null, "1");
        assertEquals(d1, BigDecimal.ONE);
        List<String> list = Arrays.asList("null", null, "1");
        BigDecimal d2 = Z.make(BigDecimal::new, list);
        assertEquals(d2, BigDecimal.ONE);

        List<String> els = Collections.emptyList();
        BigDecimal d3 = Z.makeSure(BigDecimal.ONE, BigDecimal::new, "");
        assertEquals(d3, BigDecimal.ONE);
        BigDecimal d4 = Z.makeSure(BigDecimal.ONE, BigDecimal::new, els);
        assertEquals(d4, BigDecimal.ONE);

        BigDecimal d5 = Z.makeSafe(() -> BigDecimal.ONE, BigDecimal::new, "");
        assertEquals(d5, BigDecimal.ONE);
        BigDecimal d6 = Z.makeSafe(() -> BigDecimal.ONE, BigDecimal::new, els);
        assertEquals(d6, BigDecimal.ONE);
    }

    @Test
    public void notNull() {
        assertEquals("1", Z.notNull(null, "1"));
        assertEquals(Null.Str, Z.notNull(Null.Str, null, "1"));
        assertEquals(Null.Str, Z.notNull(Arrays.asList(Null.Str, null, "1")));
        final String str = null;
        assertEquals("", Z.notNullSure(Null.Str, str));
        assertEquals("1", Z.notNullSure(Null.Str, null, "1"));
        assertEquals("1", Z.notNullSure(Null.Str, Arrays.asList(null, "1")));
        assertEquals("", Z.notNullSafe(() -> Null.Str, str));
        assertEquals("1", Z.notNullSafe(() -> Null.Str, null, "1"));
        assertEquals("1", Z.notNullSafe(() -> Null.Str, Arrays.asList(null, "1")));
    }

    @Test
    public void notEmpty() {
        assertEquals("1", Z.notEmpty(null, "1"));
        assertEquals("1", Z.notEmpty(Null.Str, null, "1"));
        assertEquals("1", Z.notEmpty(Arrays.asList(Null.Str, null, "1")));
        final String str = null;
        assertEquals("", Z.notEmptySure(Null.Str, str));
        assertEquals("1", Z.notEmptySure(Null.Str, Arrays.asList(Null.Str, null, "1")));
        assertEquals("", Z.notEmptySafe(() -> Null.Str, str));
        assertEquals("1", Z.notEmptySafe(() -> Null.Str, Arrays.asList(Null.Str, null, "1")));
    }

    @Test
    public void notBlank() {
        assertEquals("1", Z.notBlank(null, "1"));
        assertEquals("1", Z.notBlank(Null.Str, null, "1"));
        assertEquals("1", Z.notBlank(Arrays.asList(Null.Str, null, "1")));
        final String str = null;
        assertEquals("", Z.notBlankSure(Null.Str, str));
        assertEquals("1", Z.notBlankSure(Null.Str, Arrays.asList(Null.Str, null, "1")));
        assertEquals("", Z.notBlankSafe(() -> Null.Str, str));
        assertEquals("1", Z.notBlankSafe(() -> Null.Str, Arrays.asList(Null.Str, null, "1")));
    }

    @Test
    public void decimal() {
        assertEquals(new BigDecimal("1"), Z.decimal(null, "1"));
        assertEquals(new BigDecimal("1"), Z.decimal(Arrays.asList(null, "1")));

        assertEquals(new BigDecimal("1"), Z.decimalSure(BigDecimal.ONE, "1"));
        assertEquals(new BigDecimal("1"), Z.decimalSafe(() -> BigDecimal.ONE, "1"));

        List<String> els = Collections.singletonList("A");
        assertEquals(new BigDecimal("1"), Z.decimalSure(BigDecimal.ONE, els));
        assertEquals(new BigDecimal("1"), Z.decimalSafe(() -> BigDecimal.ONE, els));

        assertEquals(1, Z.int32(null, "1"));
        assertEquals(1, Z.int32(Arrays.asList(null, "1")));
        assertEquals(1, Z.int32Sure(1, "a"));
        assertEquals(1, Z.int32Sure(1, Arrays.asList(null, "1")));
        assertEquals(1, Z.int32Safe(() -> 1, "a"));
        assertEquals(1, Z.int32Safe(() -> 1, Arrays.asList(null, "1")));

        assertEquals(1L, Z.int64(null, "1"));
        assertEquals(1L, Z.int64(Arrays.asList(null, "1")));
        assertEquals(1L, Z.int64Sure(1L, "a"));
        assertEquals(1L, Z.int64Sure(1L, Arrays.asList(null, "1")));
        assertEquals(1L, Z.int64Safe(() -> 1L, "a"));
        assertEquals(1L, Z.int64Safe(() -> 1L, Arrays.asList(null, "1")));
    }
}
