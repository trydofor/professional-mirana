package pro.fessional.mirana.pain;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-29
 */
class ThrowableUtilTest {

    private final NullPointerException e0 = new NullPointerException();
    private final IllegalArgumentException e1 = new IllegalArgumentException(e0);
    private final IllegalArgumentException e2 = new IllegalArgumentException(e1);

    public void ex2() {
        try {
            ex1();
        }
        catch (Exception e) {
            throw e2;
        }
    }

    public void ex1() {
        try {
            ex0();
        }
        catch (Exception e) {
            throw e1;
        }
    }

    public void ex0() {
        throw e0;
    }

    @Test
    void testToString() {
        try {
            ex2();
        }
        catch (Exception e) {
            final String s = ThrowableUtil.toString(e);
            Testing.println(s);
        }
    }

    @Test
    void root() {
        try {
            ex2();
        }
        catch (Exception e) {
            Throwable root = ThrowableUtil.root(e);
            assertSame(e0, root);
            assertEquals(ThrowableUtil.rootString(e), ThrowableUtil.rootString(root));
        }
    }

    @Test
    void contains() {
        try {
            ex2();
        }
        catch (Exception e) {
            assertTrue(ThrowableUtil.contains(e, NullPointerException.class));
            assertTrue(ThrowableUtil.contains(e, IllegalArgumentException.class));
            assertFalse(ThrowableUtil.contains(e, IllegalStateException.class));
        }
    }

    @Test
    void cause() {
        assertSame(e0, ThrowableUtil.cause(e0, -1));
        assertSame(e0, ThrowableUtil.cause(e0, 0));
        assertSame(e0, ThrowableUtil.cause(e0, 1));
        assertSame(e0, ThrowableUtil.cause(e0, 2));

        assertSame(e1, ThrowableUtil.cause(e1, -1));
        assertSame(e1, ThrowableUtil.cause(e1, 0));
        assertSame(e0, ThrowableUtil.cause(e1, 1));
        assertSame(e0, ThrowableUtil.cause(e1, 2));

        assertEquals(ThrowableUtil.causeString(e1, 1), ThrowableUtil.toString(e0));
    }

    @Test
    void firstCause() {
        try {
            ex2();
        }
        catch (Exception e) {
            final IllegalArgumentException f = ThrowableUtil.firstCause(e, IllegalArgumentException.class);
            assertSame(e1, f);
        }
    }

    @Test
    void lastCause() {
        try {
            ex2();
        }
        catch (Exception e) {
            final IllegalArgumentException f = ThrowableUtil.lastCause(e, IllegalArgumentException.class);
            assertSame(e2, f);
        }
    }

    @Test
    void throwMatch() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                ex2();
            }
            catch (Exception e) {
                ThrowableUtil.throwMatch(e, IllegalArgumentException.class);
            }
        });

        assertThrows(RuntimeException.class, () -> {
            try {
                ex2();
            }
            catch (Exception e) {
                ThrowableUtil.throwMatch(e, IllegalStateException.class);
            }
        });
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                ex2();
            }
            catch (Exception e) {
                ThrowableUtil.throwMatch(e, RuntimeException.class);
            }
        });
    }

    @Test
    void throwCause() {
        assertThrows(NullPointerException.class, () -> {
            try {
                ex2();
            }
            catch (Exception e) {
                ThrowableUtil.throwCause(e, NullPointerException.class);
            }
        });
    }

    @Test
    void print() {
        String e2str = ThrowableUtil.toString(e2);
        Testing.println(e2str);

        {
            Testing.println("true, true, 5, 10");
            StringBuilder sb = new StringBuilder();
            ThrowableUtil.print(sb, e2, true, true, 5, 10);
            Testing.println(sb);
        }
        {
            Testing.println("true, false, 5, 10");
            StringBuilder sb = new StringBuilder();
            ThrowableUtil.print(sb, e2, true, false, 5, 10);
            Testing.println(sb);
        }
        {
            Testing.println("false, true, 5, 10");
            StringBuilder sb = new StringBuilder();
            ThrowableUtil.print(sb, e2, false, true, 5, 10);
            Testing.println(sb);
        }
        {
            Testing.println("false, false, 5, 10");
            StringBuilder sb = new StringBuilder();
            ThrowableUtil.print(sb, e2, false, false, 5, 10);
            Testing.println(sb);
        }
        {
            Testing.println("false, false, 5, 1");
            StringBuilder sb = new StringBuilder();
            ThrowableUtil.print(sb, e2, false, false, 5, 1);
            Testing.println(sb);
        }
    }
}
