package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-17
 */
class BiConvertorTest {

    private final BiConvertor<String, Integer> str2Int = BiConvertor.of(
            String.class,
            Integer.class,
            Integer::valueOf, Object::toString);

    @Test
    void testSimple() {
        assertEquals(1, str2Int.toTarget("1"));
        assertEquals("1", str2Int.toSource(1));
        assertEquals(String.class, str2Int.sourceType());
        assertEquals(Integer.class, str2Int.targetType());
        assertTrue(str2Int.canToTarget("1"));
        assertTrue(str2Int.canToSource(1));
        assertFalse(str2Int.canToTarget(1));
        assertFalse(str2Int.canToSource(""));
        assertEquals(1, str2Int.tryToTarget("1"));
        assertEquals("1", str2Int.tryToSource(1));
    }

    @Test
    void testTryDo() {
        BiConvertor<String, Number> sup = BiConvertor.of(
                String.class,
                Number.class,
                Integer::valueOf, Object::toString);

        assertEquals(1, sup.tryToTarget(Integer.class, "1"));
        assertNull(sup.tryToTarget(Date.class, "1"));

        assertEquals("1", sup.tryToSource(CharSequence.class,1));
        assertNull(sup.tryToSource(Date.class, 1));
    }

    @Test
    void testInverse() {
        final BiConvertor<Integer, String> int2Str = str2Int.reverseBiConvertor();
        assertEquals(1, int2Str.toSource("1"));
        assertEquals("1", int2Str.toTarget(1));
        assertSame(int2Str.reverseBiConvertor(), str2Int);
        assertEquals(Integer.class, int2Str.sourceType());
        assertEquals(String.class, int2Str.targetType());
    }

    @Test
    void testCompose() {
        final BiConvertor<String, String> compose = str2Int.compose(str2Int.reverseBiConvertor());
        assertEquals("1", compose.toTarget("1"));
        assertEquals("1", compose.toSource("1"));
        assertEquals(String.class, compose.sourceType());
        assertEquals(String.class, compose.targetType());
    }
}
