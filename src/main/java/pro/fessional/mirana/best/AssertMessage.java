package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.MessageException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * pre/post-check. throw MessageException(without stack) if not match.
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class AssertMessage {


    @Contract("false, _ -> fail")
    public static void isTrue(boolean b, @NotNull String msg) {
        if (!b) throw new MessageException(msg);
    }

    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean b, @NotNull String msg, Object... args) {
        if (!b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("false, _ -> fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code) {
        if (!b) throw new MessageException(code);
    }

    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code, Object... args) {
        if (!b) throw new MessageException(code, args);
    }

    //
    @Contract("true, _ -> fail")
    public static void isFalse(boolean b, @NotNull String msg) {
        if (b) throw new MessageException(msg);
    }

    @Contract("true, _, _ -> fail")
    public static void isFalse(boolean b, @NotNull String msg, Object... args) {
        if (b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("true, _ -> fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code) {
        if (b) throw new MessageException(code);
    }

    @Contract("true, _, _ -> fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code, Object... args) {
        if (b) throw new MessageException(code, args);
    }

    // ////
    @Contract("!null, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull String msg) {
        if (b != null) throw new MessageException(msg);
    }

    @Contract("!null, _, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b != null) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("!null, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code) {
        if (b != null) throw new MessageException(code);
    }

    @Contract("!null, _, _ -> fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b != null) throw new MessageException(code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull String msg) {
        if (b == null) throw new MessageException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b == null) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull CodeEnum code) {
        if (b == null) throw new MessageException(code);
    }

    @Contract("null, _, _ -> fail")
    public static void notNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b == null) throw new MessageException(code, args);
    }

    // ////
    public static void isEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new MessageException(msg);
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c != null && c.length() > 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c != null && c.length() > 0) throw new MessageException(code);
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length() > 0) throw new MessageException(code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new MessageException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c == null || c.length() == 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c == null || c.length() == 0) throw new MessageException(code);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length() == 0) throw new MessageException(code, args);
    }

    // ////
    public static void isEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new MessageException(msg);
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new MessageException(code);
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new MessageException(code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new MessageException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new MessageException(code);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new MessageException(code, args);
    }

    // ////
    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new MessageException(msg);
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new MessageException(code);
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new MessageException(code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new MessageException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new MessageException(code);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new MessageException(code, args);
    }

    // ////
    public static void isEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c != null && c.length > 0) throw new MessageException(msg);
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c != null && c.length > 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull CodeEnum code) {
        if (c != null && c.length > 0) throw new MessageException(code);
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length > 0) throw new MessageException(code, args);
    }

    //
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c == null || c.length == 0) throw new MessageException(msg);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c == null || c.length == 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code) {
        if (c == null || c.length == 0) throw new MessageException(code);
    }

    @Contract("null, _, _ -> fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length == 0) throw new MessageException(code, args);
    }

    //
    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || !a.equals(b)) throw new MessageException(msg);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || !a.equals(b)) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || !a.equals(b)) throw new MessageException(code);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || !a.equals(b)) throw new MessageException(code, args);
    }

    //
    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(msg);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(code);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(code, args);
    }

    //
    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(msg);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(code);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(code, args);
    }

    //
    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(msg);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(code);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(code, args);
    }

    //
    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(msg);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(code);
    }

    @Contract("null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(code, args);
    }
}
