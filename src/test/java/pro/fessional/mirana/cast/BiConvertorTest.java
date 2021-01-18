package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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
    }

    @Test
    void testInverse() {
        final BiConvertor<Integer, String> int2Str = str2Int.reverseBiConvertor();
        assertEquals(1, int2Str.toSource("1"));
        assertEquals("1", int2Str.toTarget(1));
        assertSame(int2Str.reverseBiConvertor(), str2Int);
    }

    @Test
    void testCompose() {
        final BiConvertor<String, String> compose = str2Int.compose(str2Int.reverseBiConvertor());
        assertEquals("1", compose.toTarget("1"));
        assertEquals("1", compose.toSource("1"));
    }
}
