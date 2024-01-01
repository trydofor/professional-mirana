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
    void next32() {
        final int unit = 5;
        int next = 0;
        for (int i = 0; i < 100; i++) {
            if (i % unit == 0) next += unit;
            if (i <= 20) SystemOut.println("i=" + i + ", next=" + next);
            Assertions.assertEquals(next, AnyIntegerUtil.next32(i, unit));
        }
    }

    @Test
    void prev32() {
        final int unit = 5;
        int prev = -unit;
        for (int i = 0; i < 100; i++) {
            if (i % unit == 1) prev += unit;
            if (i <= 20) SystemOut.println("i=" + i + ", prev=" + prev);
            Assertions.assertEquals(prev, AnyIntegerUtil.prev32(i, unit));
        }
    }
}
