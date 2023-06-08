package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.BadArgsException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * pre-check. throw IllegalArgumentException or BadArgsException(without stack) if not match.
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class AssertArgs {

    @Contract("false, _ -> fail")
    public static void isTrue(boolean b, @NotNull String msg) {
        if (!b) throw new IllegalArgumentException(msg);
    }

    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean b, @NotNull String msg, Object... args) {
        if (!b) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code, Object... args) {
        if (!b) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("true, _ -> fail")
    public static void isFalse(boolean b, @NotNull String msg) {
        if (b) throw new IllegalArgumentException(msg);
    }

    @Contract("true, _, _ -> fail")
    public static void isFalse(boolean b, @NotNull String msg, Object... args) {
        if (b) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("true, _, _ -> fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code, Object... args) {
        if (b) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("!null, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull String msg) {
        if (b != null) throw new IllegalArgumentException(msg);
    }

    @Contract("!null, _, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b != null) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("!null, _, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b != null) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull String msg) {
        if (b == null) throw new IllegalArgumentException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b == null) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b == null) throw new BadArgsException(false, code, args);
    }

    // ////
    public static void isEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new IllegalArgumentException(msg);
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c != null && c.length() > 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length() > 0) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new IllegalArgumentException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c == null || c.length() == 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length() == 0) throw new BadArgsException(false, code, args);
    }

    // ////
    public static void isEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(msg);
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadArgsException(false, code, args);
    }

    // ////
    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(msg);
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadArgsException(false, code, args);
    }

    // ////
    public static void isEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c != null && c.length > 0) throw new IllegalArgumentException(msg);
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c != null && c.length > 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length > 0) throw new BadArgsException(false, code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c == null || c.length == 0) throw new IllegalArgumentException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c == null || c.length == 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length == 0) throw new BadArgsException(false, code, args);
    }

    //
    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @Nullable T b, @NotNull String msg) {
        if (a == null && b == null) return;
        if (a == null || !a.equals(b)) throw new IllegalArgumentException(msg);
    }

    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @Nullable T b, @NotNull String msg, Object... args) {
        if (a == null && b == null) return;
        if (a == null || !a.equals(b)) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @Nullable T b, @NotNull CodeEnum code, Object... args) {
        if (a == null && b == null) return;
        if (a == null || !a.equals(b)) throw new BadArgsException(false, code, args);
    }

    //
    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @Nullable T b, @NotNull String msg) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) < 0) throw new IllegalArgumentException(msg);
    }

    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @Nullable T b, @NotNull String msg, Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) < 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @Nullable T b, @NotNull CodeEnum code, Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) < 0) throw new BadArgsException(false, code, args);
    }

    //
    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @Nullable T b, @NotNull String msg) {
        if (a == null || b == null || a.compareTo(b) <= 0) throw new IllegalArgumentException(msg);
    }

    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @Nullable T b, @NotNull String msg, Object... args) {
        if (a == null || b == null || a.compareTo(b) <= 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @Nullable T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || b == null || a.compareTo(b) <= 0) throw new BadArgsException(false, code, args);
    }

    //
    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @Nullable T b, @NotNull String msg) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) > 0) throw new IllegalArgumentException(msg);
    }

    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @Nullable T b, @NotNull String msg, Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) > 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @Nullable T b, @NotNull CodeEnum code, Object... args) {
        if (a == null && b == null) return;
        if (a == null || b == null || a.compareTo(b) > 0) throw new BadArgsException(false, code, args);
    }

    //
    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @Nullable T b, @NotNull String msg) {
        if (a == null || b == null || a.compareTo(b) >= 0) throw new IllegalArgumentException(msg);
    }

    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @Nullable T b, @NotNull String msg, Object... args) {
        if (a == null || b == null || a.compareTo(b) >= 0) throw new IllegalArgumentException(FormatUtil.logback(msg, args));
    }

    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @Nullable T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || b == null || a.compareTo(b) >= 0) throw new BadArgsException(false, code, args);
    }
}
