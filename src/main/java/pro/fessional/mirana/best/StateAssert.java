package pro.fessional.mirana.best;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.BadStateException;

import java.util.Collection;
import java.util.Map;

/**
 * 后置检查，条件不满足时，抛出 BadStateException
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class StateAssert {

    public static void isTrue(boolean b, @NotNull String msg) {
        if (!b) throw new BadStateException(msg);
    }

    public static void isTrue(boolean b, @NotNull String code, @NotNull String msg) {
        if (!b) throw new BadStateException(code, msg);
    }

    public static void isTrue(boolean b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (!b) throw new BadStateException(code, args);
    }

    // 
    public static void isFalse(boolean b, @NotNull String msg) {
        if (b) throw new BadStateException(msg);
    }

    public static void isFalse(boolean b, @NotNull String code, @NotNull String msg) {
        if (b) throw new BadStateException(code, msg);
    }

    public static void isFalse(boolean b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (b) throw new BadStateException(code, args);
    }

    // ////
    public static void isNull(Object b, @NotNull String msg) {
        if (b != null) throw new BadStateException(msg);
    }

    public static void isNull(Object b, @NotNull String code, @NotNull String msg) {
        if (b != null) throw new BadStateException(code, msg);
    }

    public static void isNull(Object b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (b != null) throw new BadStateException(code, args);
    }

    // 
    public static void notNull(Object b, @NotNull String msg) {
        if (b == null) throw new BadStateException(msg);
    }

    public static void notNull(Object b, @NotNull String code, @NotNull String msg) {
        if (b == null) throw new BadStateException(code, msg);
    }

    public static void notNull(Object b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (b == null) throw new BadStateException(code, args);
    }

    // ////
    public static void isEmpty(CharSequence c, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new BadStateException(msg);
    }

    public static void isEmpty(CharSequence c, @NotNull String code, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new BadStateException(code, msg);
    }

    public static void isEmpty(CharSequence c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && c.length() > 0) throw new BadStateException(code, args);
    }

    // 
    public static void notEmpty(CharSequence c, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new BadStateException(msg);
    }

    public static void notEmpty(CharSequence c, @NotNull String code, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new BadStateException(code, msg);
    }

    public static void notEmpty(CharSequence c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.length() == 0) throw new BadStateException(code, args);
    }

    // ////
    public static void isEmpty(Collection<?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new BadStateException(msg);
    }

    public static void isEmpty(Collection<?> c, @NotNull String code, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code, msg);
    }

    public static void isEmpty(Collection<?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code, args);
    }

    // 
    public static void notEmpty(Collection<?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new BadStateException(msg);
    }

    public static void notEmpty(Collection<?> c, @NotNull String code, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new BadStateException(code, msg);
    }

    public static void notEmpty(Collection<?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(code, args);
    }

    // ////
    public static void isEmpty(Map<?, ?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new BadStateException(msg);
    }

    public static void isEmpty(Map<?, ?> c, @NotNull String code, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code, msg);
    }

    public static void isEmpty(Map<?, ?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code, args);
    }

    // 
    public static void notEmpty(Map<?, ?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new BadStateException(msg);
    }

    public static void notEmpty(Map<?, ?> c, @NotNull String code, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new BadStateException(code, msg);
    }

    public static void notEmpty(Map<?, ?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(code, args);
    }

    // ////
    public static void isEmpty(Object[] c, @NotNull String msg) {
        if (c != null && c.length > 0) throw new BadStateException(msg);
    }

    public static void isEmpty(Object[] c, @NotNull String code, @NotNull String msg) {
        if (c != null && c.length > 0) throw new BadStateException(code, msg);
    }

    public static void isEmpty(Object[] c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && c.length > 0) throw new BadStateException(code, args);
    }

    // 
    public static void notEmpty(Object[] c, @NotNull String msg) {
        if (c == null || c.length == 0) throw new BadStateException(msg);
    }

    public static void notEmpty(Object[] c, @NotNull String code, @NotNull String msg) {
        if (c == null || c.length == 0) throw new BadStateException(code, msg);
    }

    public static void notEmpty(Object[] c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.length == 0) throw new BadStateException(code, args);
    }
}
