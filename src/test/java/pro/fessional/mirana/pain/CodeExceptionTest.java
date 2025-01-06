package pro.fessional.mirana.pain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;
import pro.fessional.mirana.data.Null;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pro.fessional.mirana.pain.CodeException.TweakStack;

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

        Testing.printStackTrace(noc);
        Testing.println("======");
        Testing.printStackTrace(hoc);
    }

    @Test
    void testEmpty() {
        final CodeException noc = new CodeException(false, "");

        assertNull(noc.getCause());
        assertEquals(Null.Str, noc.getI18nCode());
        assertEquals(Null.Str, noc.getCode());
        assertEquals(0, noc.getStackTrace().length);
        assertEquals(0, noc.getSuppressed().length);
    }

    @Test
    void testCode() {
        String stack = "stack";
        TweakStack.tweakCode(stack, true);
        final CodeException st1 = new CodeException(stack);
        Assertions.assertTrue(st1.getStackTrace().length > 0);

        TweakStack.tweakCode(stack, false);
        final CodeException st2 = new CodeException(stack);
        Assertions.assertEquals(0, st2.getStackTrace().length);

        TweakStack.resetCode(stack);
        final CodeException st3 = new CodeException(stack);
        Assertions.assertEquals(0, st3.getStackTrace().length);

        TweakStack.tweakGlobal(true);
        final CodeException st4 = new CodeException(stack);
        Assertions.assertTrue(st4.getStackTrace().length > 0);
        TweakStack.resetGlobal();
    }

    @Test
    void testMessage() {
        String stack = "stack";
        TweakStack.tweakCode(stack, true);
        final MessageException st1 = new MessageException(stack);
        Assertions.assertTrue(st1.getStackTrace().length > 0);

        TweakStack.tweakCode(stack, false);
        final MessageException st2 = new MessageException(stack);
        Assertions.assertEquals(0, st2.getStackTrace().length);

        TweakStack.resetCode(stack);
        final MessageException st3 = new MessageException(stack);
        Assertions.assertEquals(0, st3.getStackTrace().length);

        TweakStack.tweakGlobal(true);
        final MessageException st4 = new MessageException(stack);
        Assertions.assertNotEquals(0, st4.getStackTrace().length);

        TweakStack.resetGlobal();
        final MessageException st5 = new MessageException(stack);
        Assertions.assertEquals(0, st5.getStackTrace().length);
    }

    @Test
    void testPriority() {
        String code = "stack";
        Class<MessageException> claz = MessageException.class;
        Assertions.assertEquals(CodeException.DefaultStack, TweakStack.current(code, claz, null));
        Assertions.assertTrue(TweakStack.current(code, claz, true));
        Assertions.assertFalse(TweakStack.current(code, claz, false));

        TweakStack.tweakGlobal(true);
        Assertions.assertTrue(TweakStack.current(code, claz, null));
        Assertions.assertFalse(TweakStack.current(code, claz, false));

        TweakStack.tweakThread(false);
        Assertions.assertFalse(TweakStack.current(code, claz, null));
        Assertions.assertTrue(TweakStack.current(code, claz, true));

        TweakStack.tweakClass(claz, true);
        Assertions.assertTrue(TweakStack.current(code, claz, null));
        Assertions.assertTrue(TweakStack.current(code, claz, false));

        TweakStack.tweakCode(code, false);
        Assertions.assertFalse(TweakStack.current(code, claz, null));
        Assertions.assertFalse(TweakStack.current(code, claz, false));

        TweakStack.resetCode(code);
        Assertions.assertTrue(TweakStack.current(code, claz, null));
        Assertions.assertTrue(TweakStack.current(code, claz, false));

        TweakStack.resetClass(claz);
        Assertions.assertFalse(TweakStack.current(code, claz, null));
        Assertions.assertTrue(TweakStack.current(code, claz, true));

        TweakStack.resetThread();
        Assertions.assertTrue(TweakStack.current(code, claz, null));
        Assertions.assertFalse(TweakStack.current(code, claz, false));

        TweakStack.resetGlobal();
        Assertions.assertTrue(TweakStack.current(code, claz, true));
        Assertions.assertFalse(TweakStack.current(code, claz, false));
        Assertions.assertEquals(CodeException.DefaultStack, TweakStack.current(code, claz, null));
    }
}
