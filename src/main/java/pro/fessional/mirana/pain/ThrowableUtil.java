package pro.fessional.mirana.pain;

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
    public static Throwable root(Throwable t) {
        while (t != null) {
            Throwable x = t.getCause();
            if (x == null) {
                return t;
            } else {
                t = x;
            }
        }
        return null;
    }

    /**
     * 异常链中，是否保护制定异常。
     *
     * @param t 具体异常对象
     * @param e 需要匹配的异常类型
     * @return 结果
     */
    public static boolean contains(Throwable t, Class<? extends Throwable> e) {
        if (t == null || e == null) return false;
        while (t != null) {
            if (e.isInstance(t)) return true;
            t = t.getCause();
        }
        return false;
    }
}
