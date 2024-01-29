package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-17
 */
class BoxedTypeUtilTest {

    @Test
    void tryBox() {
        assertEquals(Boolean.class, BoxedTypeUtil.box(boolean.class));
        assertEquals(Boolean.class, BoxedTypeUtil.box(Boolean.class));
        assertEquals(Integer.class, BoxedTypeUtil.box(int.class));
        assertEquals(Integer.class, BoxedTypeUtil.box(Integer.class));
        assertEquals(Long.class, BoxedTypeUtil.box(long.class));
        assertEquals(Long.class, BoxedTypeUtil.box(Long.class));
        assertEquals(Float.class, BoxedTypeUtil.box(float.class));
        assertEquals(Float.class, BoxedTypeUtil.box(Float.class));
        assertEquals(Double.class, BoxedTypeUtil.box(double.class));
        assertEquals(Double.class, BoxedTypeUtil.box(Double.class));
        assertEquals(Void.class, BoxedTypeUtil.box(void.class));
    }

    @Test
    void isAssignable() {
        assertTrue(BoxedTypeUtil.isAssignable(Boolean.class, boolean.class));
        assertTrue(BoxedTypeUtil.isAssignable(Boolean.class, Boolean.class));
        assertTrue(BoxedTypeUtil.isAssignable(Integer.class, int.class));
        assertTrue(BoxedTypeUtil.isAssignable(Integer.class, Integer.class));
        assertTrue(BoxedTypeUtil.isAssignable(boolean.class, Boolean.class));
        assertTrue(BoxedTypeUtil.isAssignable(Boolean.class, Boolean.class));
        assertTrue(BoxedTypeUtil.isAssignable(int.class, Integer.class));
        assertTrue(BoxedTypeUtil.isAssignable(Integer.class, Integer.class));
        assertTrue(BoxedTypeUtil.isAssignable(Number.class, Integer.class));
    }

    @Test
    void isInstance() {
        assertTrue(BoxedTypeUtil.isInstance(Boolean.class, true));
        assertTrue(BoxedTypeUtil.isInstance(Boolean.class, Boolean.TRUE));
        assertTrue(BoxedTypeUtil.isInstance(Integer.class, 1));
        assertTrue(BoxedTypeUtil.isInstance(Integer.class, Integer.valueOf("100000")));
        assertTrue(BoxedTypeUtil.isInstance(boolean.class, true));
        assertTrue(BoxedTypeUtil.isInstance(Boolean.class, Boolean.TRUE));
        assertTrue(BoxedTypeUtil.isInstance(int.class, 1));
        assertTrue(BoxedTypeUtil.isInstance(Integer.class, Integer.valueOf("100000")));
        assertTrue(BoxedTypeUtil.isInstance(Number.class, Integer.valueOf("100000")));
    }
}
