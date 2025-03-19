package pro.fessional.mirana.cond;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2025-01-10
 */
class PredictValTest {


    @Test
    void testIs() {
        assertTrue(PredictVal.is(new boolean[]{ true, true }, true));
        assertFalse(PredictVal.is(new boolean[]{ true, false }, true));
        assertFalse(PredictVal.is(null, true));
        assertFalse(PredictVal.is(new boolean[]{}, true));
    }

    @Test
    void testEqNeInt() {
        assertTrue(PredictVal.eq(5, 5));
        assertFalse(PredictVal.eq(5, 6));
        assertTrue(PredictVal.ne(5, 6));
        assertFalse(PredictVal.ne(5, 5));
    }

    @Test
    void testGtGeLtLeInt() {
        assertTrue(PredictVal.gt(6, 5));
        assertFalse(PredictVal.gt(5, 6));

        assertFalse(PredictVal.ge(4, 5));
        assertTrue(PredictVal.ge(5, 5));
        assertTrue(PredictVal.ge(6, 5));

        assertTrue(PredictVal.lt(5, 6));
        assertFalse(PredictVal.lt(6, 5));

        assertFalse(PredictVal.le(5, 4));
        assertTrue(PredictVal.le(5, 5));
        assertTrue(PredictVal.le(5, 6));
    }

    @Test
    void testEqNeIntArray() {
        assertTrue(PredictVal.eq(new int[]{ 5, 5 }, 5));
        assertFalse(PredictVal.eq(new int[]{ 5, 6 }, 5));
        assertFalse(PredictVal.eq((int[]) null, 5));
        assertFalse(PredictVal.eq(new int[]{}, 5));

        assertTrue(PredictVal.ne(new int[]{ 6, 7 }, 5));
        assertFalse(PredictVal.ne(new int[]{ 5, 5 }, 5));
        assertFalse(PredictVal.ne((int[]) null, 5));
        assertFalse(PredictVal.ne(new int[]{}, 5));
    }

    @Test
    void testGtGeLtLeIntArray() {
        assertTrue(PredictVal.gt(new int[]{ 6, 7 }, 5));
        assertFalse(PredictVal.gt(new int[]{ 5, 4 }, 5));
        assertFalse(PredictVal.gt((int[]) null, 5));
        assertFalse(PredictVal.gt(new int[]{}, 5));

        assertTrue(PredictVal.ge(new int[]{ 5, 6 }, 5));
        assertFalse(PredictVal.ge(new int[]{ 4, 3 }, 5));
        assertFalse(PredictVal.ge((int[]) null, 5));
        assertFalse(PredictVal.ge(new int[]{}, 5));

        assertTrue(PredictVal.lt(new int[]{ 4, 3 }, 5));
        assertFalse(PredictVal.lt(new int[]{ 5, 6 }, 5));
        assertFalse(PredictVal.lt((int[]) null, 5));
        assertFalse(PredictVal.lt(new int[]{}, 5));

        assertTrue(PredictVal.le(new int[]{ 5, 4 }, 5));
        assertFalse(PredictVal.le(new int[]{ 6, 7 }, 5));
        assertFalse(PredictVal.le((int[]) null, 5));
        assertFalse(PredictVal.le(new int[]{}, 5));
    }

    @Test
    void testEqNeLong() {
        assertTrue(PredictVal.eq(5L, 5L));
        assertFalse(PredictVal.eq(5L, 6L));
        assertTrue(PredictVal.ne(5L, 6L));
        assertFalse(PredictVal.ne(5L, 5L));
    }

    @Test
    void testGtGeLtLeLong() {
        assertTrue(PredictVal.gt(6L, 5L));
        assertFalse(PredictVal.gt(5L, 6L));

        assertFalse(PredictVal.ge(4L, 5L));
        assertTrue(PredictVal.ge(5L, 5L));
        assertTrue(PredictVal.ge(6L, 5L));

        assertTrue(PredictVal.lt(5L, 6L));
        assertFalse(PredictVal.lt(6L, 5L));

        assertFalse(PredictVal.le(5L, 4L));
        assertTrue(PredictVal.le(5L, 5L));
        assertTrue(PredictVal.le(5L, 6L));
    }

    @Test
    void testEqNeLongArray() {
        assertTrue(PredictVal.eq(new long[]{ 5L, 5L }, 5L));
        assertFalse(PredictVal.eq(new long[]{ 5L, 6L }, 5L));
        assertFalse(PredictVal.eq(null, 5L));
        assertFalse(PredictVal.eq(new long[]{}, 5L));

        assertTrue(PredictVal.ne(new long[]{ 6L, 7L }, 5L));
        assertFalse(PredictVal.ne(new long[]{ 5L, 5L }, 5L));
        assertFalse(PredictVal.ne(null, 5L));
        assertFalse(PredictVal.ne(new long[]{}, 5L));
    }

    @Test
    void testGtGeLtLeLongArray() {
        assertTrue(PredictVal.gt(new long[]{ 6L, 7L }, 5L));
        assertFalse(PredictVal.gt(new long[]{ 5L, 4L }, 5L));
        assertFalse(PredictVal.gt(null, 5L));
        assertFalse(PredictVal.gt(new long[]{}, 5L));

        assertTrue(PredictVal.ge(new long[]{ 5L, 6L }, 5L));
        assertFalse(PredictVal.ge(new long[]{ 4L, 3L }, 5L));
        assertFalse(PredictVal.ge(null, 5L));
        assertFalse(PredictVal.ge(new long[]{}, 5L));

        assertTrue(PredictVal.lt(new long[]{ 4L, 3L }, 5L));
        assertFalse(PredictVal.lt(new long[]{ 5L, 6L }, 5L));
        assertFalse(PredictVal.lt(null, 5L));
        assertFalse(PredictVal.lt(new long[]{}, 5L));

        assertTrue(PredictVal.le(new long[]{ 5L, 4L }, 5L));
        assertFalse(PredictVal.le(new long[]{ 6L, 7L }, 5L));
        assertFalse(PredictVal.le(null, 5L));
        assertFalse(PredictVal.le(new long[]{}, 5L));
    }
}