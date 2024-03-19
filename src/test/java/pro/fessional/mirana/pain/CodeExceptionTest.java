package pro.fessional.mirana.pain;

import org.junit.jupiter.api.Assertions;
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

    @Test
    void testCode() {
        String stack = "stack";
        CodeException.TweakStack.tweakCode(stack, true);
        final CodeException st1 = new CodeException(stack);
        Assertions.assertTrue(st1.getStackTrace().length > 0);

        CodeException.TweakStack.tweakCode(stack, false);
        final CodeException st2 = new CodeException(stack);
        Assertions.assertEquals(0, st2.getStackTrace().length);

        CodeException.TweakStack.resetCode(stack);
        final CodeException st3 = new CodeException(stack);
        Assertions.assertEquals(0, st3.getStackTrace().length);
    }
}
