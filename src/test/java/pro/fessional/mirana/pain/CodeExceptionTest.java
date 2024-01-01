package pro.fessional.mirana.pain;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.data.Null;

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

        SystemOut.printStackTrace(noc);
        SystemOut.println("======");
        SystemOut.printStackTrace(hoc);
    }

    @Test
    void testNull() {
        final CodeException noc = new CodeException(false, (String) null);

        assertNull(noc.getCause());
        assertNull(noc.getI18nCode());
        assertEquals(Null.Str, noc.getCode());
        assertEquals(0, noc.getStackTrace().length);
        assertEquals(0, noc.getSuppressed().length);
    }
}
