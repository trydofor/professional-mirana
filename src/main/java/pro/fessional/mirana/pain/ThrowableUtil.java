package pro.fessional.mirana.pain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {

    /**
     * 把异常堆栈，打成String
     *
     * @param t 异常
     * @return 堆栈
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
     * 把异常root堆栈，打成String
     *
     * @param t 异常
     * @return root堆栈
     */
    @NotNull
    public static String rootString(Throwable t) {
        Throwable r = root(t);
        return toString(r);
    }

    /**
     * 获得最底层异常，触发异常。
     *
     * @param t 具体异常
     * @return rootCause
     */
    @Contract("!null->!null")
    public static Throwable root(Throwable t) {
        while (t != null) {
            Throwable x = t.getCause();
            if (x == null) {
                return t;
            } else {
                t = x;
            }
        }
        return t;
    }

    /**
     * 异常链中，是否保护制定异常。
     *
     * @param t 具体异常对象
     * @param e 需要匹配的异常类型
     * @return 结果
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
     * 在cause栈中，从栈底到顶查找，第一个e类型的异常
     *
     * @param t   异常
     * @param e   类型
     * @param <T> 类型
     * @return 栈
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
     * 在cause栈中，从栈顶到底查找，第一个e类型的异常
     *
     * @param t   异常
     * @param e   类型
     * @param <T> 类型
     * @return 栈
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
        } else {
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
        } else {
            throw new RuntimeException(t);
        }
    }
}
