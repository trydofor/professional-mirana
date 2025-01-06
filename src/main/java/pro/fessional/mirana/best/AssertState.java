package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.BadStateException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * post-check. throw IllegalStateException or BadStateException(without stack) if not match.
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class AssertState {

    @Contract("_, false, _ -> fail")
    public static void isTrue(@NotNull String name, boolean b, @NotNull String msg) {
        if (!b) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, false, _, _ -> fail")
    public static void isTrue(@NotNull String name, boolean b, @NotNull String msg, Object... args) {
        if (!b) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, false, _ -> fail")
    public static void isTrue(@NotNull String name, boolean b, @NotNull CodeEnum code) {
        if (!b) throw new BadStateException(name, code);
    }

    @Contract("_, false, _, _ -> fail")
    public static void isTrue(@NotNull String name, boolean b, @NotNull CodeEnum code, Object... args) {
        if (!b) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, true, _ -> fail")
    public static void isFalse(@NotNull String name, boolean b, @NotNull String msg) {
        if (b) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, true, _, _ -> fail")
    public static void isFalse(@NotNull String name, boolean b, @NotNull String msg, Object... args) {
        if (b) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, true, _ -> fail")
    public static void isFalse(@NotNull String name, boolean b, @NotNull CodeEnum code) {
        if (b) throw new BadStateException(name, code);
    }

    @Contract("_, true, _, _ -> fail")
    public static void isFalse(@NotNull String name, boolean b, @NotNull CodeEnum code, Object... args) {
        if (b) throw new BadStateException(name, code, args);
    }

    // ////
    @Contract("_, !null, _ -> fail")
    public static void isNull(@NotNull String name, @Nullable Object b, @NotNull String msg) {
        if (b != null) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, !null, _, _ -> fail")
    public static void isNull(@NotNull String name, @Nullable Object b, @NotNull String msg, Object... args) {
        if (b != null) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, !null, _ -> fail")
    public static void isNull(@NotNull String name, @Nullable Object b, @NotNull CodeEnum code) {
        if (b != null) throw new BadStateException(name, code);
    }

    @Contract("_, !null, _, _ -> fail")
    public static void isNull(@NotNull String name, @Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b != null) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _ -> fail")
    public static void notNull(@NotNull String name, @Nullable Object b, @NotNull String msg) {
        if (b == null) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _ -> fail")
    public static void notNull(@NotNull String name, @Nullable Object b, @NotNull String msg, Object... args) {
        if (b == null) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _ -> fail")
    public static void notNull(@NotNull String name, @Nullable Object b, @NotNull CodeEnum code) {
        if (b == null) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _ -> fail")
    public static void notNull(@NotNull String name, @Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b == null) throw new BadStateException(name, code, args);
    }

    // ////
    public static void isEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c != null && c.length() > 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c != null && c.length() > 0) throw new BadStateException(name, code);
    }

    public static void isEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length() > 0) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c == null || c.length() == 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c == null || c.length() == 0) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length() == 0) throw new BadStateException(name, code, args);
    }

    // ////
    public static void isEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new BadStateException(name, code);
    }

    public static void isEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(name, code, args);
    }

    // ////
    public static void isEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new BadStateException(name, code);
    }

    public static void isEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(name, code, args);
    }

    // ////
    public static void isEmpty(@NotNull String name, @Nullable Object[] c, @NotNull String msg) {
        if (c != null && c.length > 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@NotNull String name, @Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c != null && c.length > 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@NotNull String name, @Nullable Object[] c, @NotNull CodeEnum code) {
        if (c != null && c.length > 0) throw new BadStateException(name, code);
    }

    public static void isEmpty(@NotNull String name, @Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length > 0) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Object[] c, @NotNull String msg) {
        if (c == null || c.length == 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c == null || c.length == 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Object[] c, @NotNull CodeEnum code) {
        if (c == null || c.length == 0) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _ -> fail")
    public static void notEmpty(@NotNull String name, @Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length == 0) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || !a.equals(b)) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || !a.equals(b)) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || !a.equals(b)) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aEqb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || !a.equals(b)) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) < 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) < 0) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) <= 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) <= 0) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aGtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) > 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) > 0) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLeb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new BadStateException(name, code, args);
    }

    //
    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) >= 0) throw new IllegalStateException(FormatUtil.named(name, msg));
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new IllegalStateException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("_, null, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) >= 0) throw new BadStateException(name, code);
    }

    @Contract("_, null, _, _, _ -> fail")
    public static <T extends Comparable<T>> void aLtb(@NotNull String name, @Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new BadStateException(name, code, args);
    }
}
