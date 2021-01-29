package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.BadStateException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 后置检查，条件不满足时，抛出 BadStateException
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class StateAssert {

    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean b, @NotNull String msg, @NotNull Object... args) {
        if (!b) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (!b) throw new BadStateException(true, code, args);
    }

    //
    @Contract("true, _, _ -> fail")
    public static void isFalse(boolean b, @NotNull String msg, @NotNull Object... args) {
        if (b) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    @Contract("true, _, _ -> fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (b) throw new BadStateException(true, code, args);
    }

    // ////
    @Contract("!null, _, _ -> fail")
    public static void isNull(Object b, @NotNull String msg, @NotNull Object... args) {
        if (b != null) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    @Contract("!null, _, _ -> fail")
    public static void isNull(Object b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (b != null) throw new BadStateException(true, code, args);
    }

    //
    @Contract("null, _, _ -> fail")
    public static void notNull(Object b, @NotNull String msg, @NotNull Object... args) {
        if (b == null) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static void notNull(Object b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (b == null) throw new BadStateException(true, code, args);
    }

    // ////
    public static void isEmpty(CharSequence c, @NotNull String msg, @NotNull Object... args) {
        if (c != null && c.length() > 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void isEmpty(CharSequence c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && c.length() > 0) throw new BadStateException(true, code, args);
    }

    //
    public static void notEmpty(CharSequence c, @NotNull String msg, @NotNull Object... args) {
        if (c == null || c.length() == 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void notEmpty(CharSequence c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.length() == 0) throw new BadStateException(true, code, args);
    }

    // ////
    public static void isEmpty(Collection<?> c, @NotNull String msg, @NotNull Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void isEmpty(Collection<?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(true, code, args);
    }

    //
    public static void notEmpty(Collection<?> c, @NotNull String msg, @NotNull Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void notEmpty(Collection<?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(true, code, args);
    }

    // ////
    public static void isEmpty(Map<?, ?> c, @NotNull String msg, @NotNull Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void isEmpty(Map<?, ?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(true, code, args);
    }

    //
    public static void notEmpty(Map<?, ?> c, @NotNull String msg, @NotNull Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void notEmpty(Map<?, ?> c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(true, code, args);
    }

    // ////
    public static void isEmpty(Object[] c, @NotNull String msg, @NotNull Object... args) {
        if (c != null && c.length > 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void isEmpty(Object[] c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c != null && c.length > 0) throw new BadStateException(true, code, args);
    }

    //
    public static void notEmpty(Object[] c, @NotNull String msg, @NotNull Object... args) {
        if (c == null || c.length == 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static void notEmpty(Object[] c, @NotNull CodeEnum code, @NotNull Object... args) {
        if (c == null || c.length == 0) throw new BadStateException(true, code, args);
    }


    //
    public static <T extends Comparable<T>> void aEqb(T a, T b, @NotNull String msg, @NotNull Object... args) {
        if (a == null && b == null) return;
        if (a == null || !a.equals(b)) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static <T extends Comparable<T>> void aEqb(T a, T b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (a == null && b == null) return;
        if (a == null || !a.equals(b)) throw new BadStateException(true, code, args);
    }

    //
    public static <T extends Comparable<T>> void aGeb(T a, T b, @NotNull String msg, @NotNull Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) < 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static <T extends Comparable<T>> void aGeb(T a, T b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) < 0) throw new BadStateException(true, code, args);
    }

    //
    public static <T extends Comparable<T>> void aGtb(T a, T b, @NotNull String msg, @NotNull Object... args) {
        if (a == null || b == null || a.compareTo(b) <= 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static <T extends Comparable<T>> void aGtb(T a, T b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (a == null || b == null || a.compareTo(b) <= 0) throw new BadStateException(true, code, args);
    }

    //
    public static <T extends Comparable<T>> void aLeb(T a, T b, @NotNull String msg, @NotNull Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) > 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static <T extends Comparable<T>> void aLeb(T a, T b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) > 0) throw new BadStateException(true, code, args);
    }

    //
    public static <T extends Comparable<T>> void aLtb(T a, T b, @NotNull String msg, @NotNull Object... args) {
        if (a == null || b == null || a.compareTo(b) >= 0) throw new IllegalStateException(FormatUtil.format(msg, args));
    }

    public static <T extends Comparable<T>> void aLtb(T a, T b, @NotNull CodeEnum code, @NotNull Object... args) {
        if (a == null || b == null || a.compareTo(b) >= 0) throw new BadStateException(true, code, args);
    }
}
