package pro.fessional.mirana.pain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-29
 */
class CodeExceptionTest {

    @Test
    void test() {
        final CodeException noc = new CodeException(false, "123");
        final CodeException hoc = new CodeException(true, "123");

        assertNull(noc.getCause());
        assertEquals(0, noc.getStackTrace().length);
        assertEquals(0, noc.getSuppressed().length);

        assertTrue(hoc.getStackTrace().length > 0);

        noc.printStackTrace();
        System.out.println("======");
        hoc.printStackTrace();
    }
}
