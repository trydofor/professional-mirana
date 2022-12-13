package pro.fessional.mirana.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author trydofor
 * @since 2022-12-13
 */
class LamTest {

    @Test
    void ref() {
        final ArrayList<Object> p0 = new ArrayList<>();
        final Lam.Ref r0 = Lam.ref(p0::clear);
        Assertions.assertEquals("clear", r0.method.getName());
        Assertions.assertSame(p0, r0.object);

        final String p1 = "123";
        final Lam.Ref r1 = Lam.<String>ref(p1::split);
        Assertions.assertEquals("split", r1.method.getName());
        Assertions.assertSame(p1, r1.object);

        final LocalDateTime p2 = LocalDateTime.now();
        final Lam.Ref r2 = Lam.ref(p2::getHour);
        Assertions.assertEquals("getHour", r2.method.getName());
        Assertions.assertSame(p2, r2.object);

        final HashMap<String, String> p3 = new HashMap<>();
        final Lam.Ref r3 = Lam.ref(p3::put);
        Assertions.assertEquals("put", r3.method.getName());
        Assertions.assertSame(p3, r3.object);

        final Lam.Ref r5 = Lam.ref(this::test1);
        Assertions.assertEquals("test1", r5.method.getName());
        Assertions.assertSame(this, r5.object);

    }

    @Test
    void cfn() throws ClassNotFoundException {
        final Class<?> iia = Class.forName("[[I");
        Assertions.assertEquals(int[][].class, iia);
        final Class<?> ia = Class.forName("[I");
        Assertions.assertEquals(int[].class, ia);
    }

    int test1(String[] str1, int[][] intArrArr, long b) {
        return 0;
    }
}
