package pro.fessional.mirana.pain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {

    /**
     * print StackTrace to String
     */
    @NotNull
    public static String toString(Throwable t) {
        if (t == null) return "";
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * print root StackTrace to String
     */
    @NotNull
    public static String rootString(Throwable t) {
        Throwable r = root(t);
        return toString(r);
    }

    /**
     * get the root StackTrace which is the root cause.
     */
    @Contract("!null->!null")
    public static Throwable root(Throwable t) {
        while (t != null) {
            Throwable x = t.getCause();
            if (x == null) {
                return t;
            }
            else {
                t = x;
            }
        }
        return t;
    }

    /**
     * Whether the specified type exception is included in the exception stack.
     *
     * @param t the exception stack
     * @param e the specified type
     */
    public static boolean contains(Throwable t, Class<? extends Throwable> e) {
        if (e == null) return false;
        while (t != null) {
            if (e.isInstance(t)) return true;
            t = t.getCause();
        }
        return false;
    }

    /**
     * from bottom to top (old to new) of the stack, find the first (newest) specified type exception
     *
     * @param t   the exception stack
     * @param e   the specified type
     * @param <T> Type
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T firstCause(Throwable t, Class<T> e) {
        if (e == null) return null;
        T f = null;
        while (t != null) {
            if (e.isInstance(t)) {
                f = (T) t;
            }
            t = t.getCause();
        }
        return f;
    }

    /**
     * from bottom to top of the stack (old to new), find the last (oldest) specified type exception
     *
     * @param t   the exception stack
     * @param e   the specified type
     * @param <T> Type
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T lastCause(Throwable t, Class<T> e) {
        if (e == null) return null;
        while (t != null) {
            if (e.isInstance(t)) {
                return (T) t;
            }
            t = t.getCause();
        }
        return null;
    }

    @SafeVarargs
    public static void throwMatch(Throwable t, Class<? extends RuntimeException>... runtime) {
        if (runtime != null) {
            for (Class<? extends RuntimeException> re : runtime) {
                if (re.isInstance(t)) {
                    throw (RuntimeException) t;
                }
            }
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        else {
            throw new RuntimeException(t);
        }
    }

    @SafeVarargs
    public static void throwCause(Throwable t, Class<? extends RuntimeException>... runtime) {
        if (runtime != null && runtime.length > 0) {
            while (t != null) {
                for (Class<? extends RuntimeException> e : runtime) {
                    if (e.isInstance(t)) {
                        throw (RuntimeException) t;
                    }
                }
                t = t.getCause();
            }
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        else {
            throw new RuntimeException(t);
        }
    }
}
