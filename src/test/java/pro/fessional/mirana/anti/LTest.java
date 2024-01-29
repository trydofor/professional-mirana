package pro.fessional.mirana.anti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.Null;

import java.util.List;

/**
 * @author trydofor
 * @since 2021-03-24
 */
class LTest {

    @Test
    void normal() {
        L.error("error message");
        L.warn("userid={}", 1);
        L.message("message");

        List<L.D> ld = L.inspect();
        Assertions.assertEquals(3, ld.size());
        L.D d0 = ld.get(0);
        Assertions.assertEquals("error", d0.cate);
        Assertions.assertEquals("error message", d0.message);

        L.D d1 = ld.get(1);
        Assertions.assertEquals("warn", d1.cate);
        Assertions.assertEquals("userid={}", d1.message);
        Assertions.assertEquals(1, d1.args[0]);

        L.D d2 = ld.get(2);
        Assertions.assertNull(d2.cate);
        Assertions.assertEquals("message", d2.message);
        Assertions.assertEquals(Null.Objects, d2.args);

        final String rs1 = L.finish();
        Assertions.assertEquals(
                "error:error message\n"
                + "warn:userid=1\n"
                + "message",
                rs1);

    }

    @Test
    void remove() {
        L.error("error message");
        L.warn("userid={}", 1);
        L.message("message");

        List<L.D> ld = L.remove();
        Assertions.assertEquals(3, ld.size());
        L.D d0 = ld.get(0);
        Assertions.assertEquals("error", d0.cate);
        Assertions.assertEquals("error message", d0.message);

        L.D d1 = ld.get(1);
        Assertions.assertEquals("warn", d1.cate);
        Assertions.assertEquals("userid={}", d1.message);
        Assertions.assertEquals(1, d1.args[0]);

        L.D d2 = ld.get(2);
        Assertions.assertNull(d2.cate);
        Assertions.assertEquals("message", d2.message);
        Assertions.assertEquals(Null.Objects, d2.args);
    }
}
