package pro.fessional.mirana.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

/**
 * @author trydofor
 * @since 2022-03-22
 */
class AnyIntegerUtilTest {

    @Test
    void valAll() {
        Assertions.assertEquals(1L, AnyIntegerUtil.val64("1"));
        Assertions.assertEquals(1L, AnyIntegerUtil.val64((String) null, 1L));
        Assertions.assertEquals(1L, AnyIntegerUtil.val64("", 1L));
        Assertions.assertEquals(1L, AnyIntegerUtil.val64(1));
        Assertions.assertEquals(1L, AnyIntegerUtil.val64((Integer) null, 1L));

        Assertions.assertEquals(1, AnyIntegerUtil.val32("1"));
        Assertions.assertEquals(1, AnyIntegerUtil.val32((String) null, 1));
        Assertions.assertEquals(1, AnyIntegerUtil.val32("", 1));
        Assertions.assertEquals(1, AnyIntegerUtil.val32(1));
        Assertions.assertEquals(1, AnyIntegerUtil.val32((Long) null, 1));

        Assertions.assertEquals(1L, AnyIntegerUtil.obj64("1"));
        Assertions.assertEquals(1L, AnyIntegerUtil.obj64((String) null, 1L));
        Assertions.assertEquals(1L, AnyIntegerUtil.obj64("", 1L));
        Assertions.assertEquals(1L, AnyIntegerUtil.obj64(1));
        Assertions.assertEquals(1L, AnyIntegerUtil.obj64((Integer) null, 1L));

        Assertions.assertEquals(1, AnyIntegerUtil.obj32("1"));
        Assertions.assertEquals(1, AnyIntegerUtil.obj32((String) null, 1));
        Assertions.assertEquals(1, AnyIntegerUtil.obj32("", 1));
        Assertions.assertEquals(1, AnyIntegerUtil.obj32(1));
        Assertions.assertEquals(1, AnyIntegerUtil.obj32((Long) null, 1));

        Assertions.assertEquals("-10", AnyIntegerUtil.trimToInteger("-10.1"));
        Assertions.assertEquals("10", AnyIntegerUtil.trimToInteger("10.1"));
    }

    @Test
    void equalsAll() {
        Assertions.assertTrue(AnyIntegerUtil.equals(1L, 1L));
        Assertions.assertTrue(AnyIntegerUtil.equals(1, 1));
        Assertions.assertFalse(AnyIntegerUtil.equals((Long) null, 1L));
        Assertions.assertFalse(AnyIntegerUtil.equals((Integer) null, 1));
    }

    @Test
    void stringAll() {
        Assertions.assertEquals("1", AnyIntegerUtil.string(1L));
        Assertions.assertEquals("1", AnyIntegerUtil.string((Long) null, "1"));
        Assertions.assertEquals("1", AnyIntegerUtil.string(1));
        Assertions.assertEquals("1", AnyIntegerUtil.string((Integer) null, "1"));
    }

    @Test
    void range32() {
        final int unit = 5;
        int next = 0;
        for (int i = 0; i < 100; i++) {
            if (i % unit == 0) next += unit;
            if (i <= 20) SystemOut.println("i=" + i + ", next=" + next);
            Assertions.assertEquals(next, AnyIntegerUtil.next32(i, unit));
            Assertions.assertEquals(next, AnyIntegerUtil.next32((Integer) i, unit));
            Assertions.assertEquals(next, AnyIntegerUtil.next32(String.valueOf(i), unit));
        }
        int prev = -unit;
        for (int i = 0; i < 100; i++) {
            if (i % unit == 1) prev += unit;
            if (i <= 20) SystemOut.println("i=" + i + ", prev=" + prev);
            Assertions.assertEquals(prev, AnyIntegerUtil.prev32(i, unit));
            Assertions.assertEquals(prev, AnyIntegerUtil.prev32((Integer) i, unit));
            Assertions.assertEquals(prev, AnyIntegerUtil.prev32(String.valueOf(i), unit));
        }
    }

    @Test
    void range64() {
        final int unit = 5;
        int next = 0;
        for (long i = 0; i < 100; i++) {
            if (i % unit == 0) next += unit;
            if (i <= 20) SystemOut.println("i=" + i + ", next=" + next);
            Assertions.assertEquals(next, AnyIntegerUtil.next64(i, unit));
            Assertions.assertEquals(next, AnyIntegerUtil.next64((Long) i, unit));
            Assertions.assertEquals(next, AnyIntegerUtil.next64(String.valueOf(i), unit));
        }
        int prev = -unit;
        for (long i = 0; i < 100; i++) {
            if (i % unit == 1) prev += unit;
            if (i <= 20) SystemOut.println("i=" + i + ", prev=" + prev);
            Assertions.assertEquals(prev, AnyIntegerUtil.prev64(i, unit));
            Assertions.assertEquals(prev, AnyIntegerUtil.prev64((Long) i, unit));
            Assertions.assertEquals(prev, AnyIntegerUtil.prev64(String.valueOf(i), unit));
        }
    }
}
