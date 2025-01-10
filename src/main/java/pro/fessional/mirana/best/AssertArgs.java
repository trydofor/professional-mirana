package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.cond.PredictVal;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.BadArgsException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 * pre-check. throw IllegalArgumentException or BadArgsException(without stack) if not match.
 *
 * NOTE1: should use isTure/isFalse instead of xxObj to assert primitive type (int/long/float/double)
 * </pre>
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class AssertArgs {

    //
    @Contract("false,_,_->fail")
    public static void isTrue(boolean b, @NotNull String name, @NotNull String msg) {
        if (!b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("false,_,_,_->fail")
    public static void isTrue(boolean b, @NotNull String name, @NotNull String msg, Object... args) {
        if (!b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("false,_,_->fail")
    public static void isTrue(boolean b, @NotNull String name, @NotNull CodeEnum code) {
        if (!b) throw new BadArgsException(name, code);
    }

    @Contract("false,_,_,_->fail")
    public static void isTrue(boolean b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (!b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void isTrue(boolean[] bs, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.is(bs, true), name, msg);
    }

    @Contract("null,_,_,_->fail")
    public static void isTrue(boolean[] bs, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.is(bs, true), name, msg, args);
    }

    @Contract("null,_,_->fail")
    public static void isTrue(boolean[] bs, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.is(bs, true), name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void isTrue(boolean[] bs, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.is(bs, true), name, code, args);
    }

    //
    @Contract("true,_,_->fail")
    public static void isFalse(boolean b, @NotNull String name, @NotNull String msg) {
        if (b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("true,_,_,_->fail")
    public static void isFalse(boolean b, @NotNull String name, @NotNull String msg, Object... args) {
        if (b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("true,_,_->fail")
    public static void isFalse(boolean b, @NotNull String name, @NotNull CodeEnum code) {
        if (b) throw new BadArgsException(name, code);
    }

    @Contract("true,_,_,_->fail")
    public static void isFalse(boolean b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (b) throw new BadArgsException(name, code, args);
    }


    //
    @Contract("null,_,_->fail")
    public static void isFalse(boolean[] bs, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.is(bs, false), name, msg);
    }

    @Contract("null,_,_,_->fail")
    public static void isFalse(boolean[] bs, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.is(bs, false), name, msg, args);
    }

    @Contract("null,_,_->fail")
    public static void isFalse(boolean[] bs, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.is(bs, false), name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void isFalse(boolean[] bs, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.is(bs, false), name, code, args);
    }

    // ////
    @Contract("!null,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String name, @NotNull String msg) {
        if (b != null) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("!null,_,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String name, @NotNull String msg, Object... args) {
        if (b != null) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("!null,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String name, @NotNull CodeEnum code) {
        if (b != null) throw new BadArgsException(name, code);
    }

    @Contract("!null,_,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (b != null) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String name, @NotNull String msg) {
        if (b == null) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String name, @NotNull String msg, Object... args) {
        if (b == null) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String name, @NotNull CodeEnum code) {
        if (b == null) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (b == null) throw new BadArgsException(name, code, args);
    }

    // ////
    public static void isEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c != null && c.length() > 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull CodeEnum code) {
        if (c != null && c.length() > 0) throw new BadArgsException(name, code);
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length() > 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c == null || c.length() == 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull CodeEnum code) {
        if (c == null || c.length() == 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length() == 0) throw new BadArgsException(name, code, args);
    }

    // ////
    public static void isEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new BadArgsException(name, code);
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadArgsException(name, code, args);
    }

    // ////
    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new BadArgsException(name, code);
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadArgsException(name, code, args);
    }

    // ////
    public static void isEmpty(@Nullable Object[] c, @NotNull String name, @NotNull String msg) {
        if (c != null && c.length > 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c != null && c.length > 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull String name, @NotNull CodeEnum code) {
        if (c != null && c.length > 0) throw new BadArgsException(name, code);
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length > 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String name, @NotNull String msg) {
        if (c == null || c.length == 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String name, @NotNull String msg, Object... args) {
        if (c == null || c.length == 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String name, @NotNull CodeEnum code) {
        if (c == null || c.length == 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length == 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull String msg) {
        if (a == null || !a.equals(b)) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || !a.equals(b)) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || !a.equals(b)) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || !a.equals(b)) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.equals(b)) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.equals(b)) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.equals(b)) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.equals(b)) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.compareTo(b) != 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) != 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) != 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) != 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.compareTo(b) == 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) == 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) == 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) == 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.compareTo(b) < 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) < 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.compareTo(b) <= 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) <= 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.compareTo(b) > 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) > 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg) {
        if (a == null || a.compareTo(b) >= 0) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) >= 0) throw new BadArgsException(name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new BadArgsException(name, code, args);
    }

    //
    public static void eqVal(int a, int b, @NotNull String name, @NotNull String msg) {
        if (a != b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void eqVal(int a, int b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a != b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void eqVal(int a, int b, @NotNull String name, @NotNull CodeEnum code) {
        if (a != b) throw new BadArgsException(name, code);
    }

    public static void eqVal(int a, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a != b) throw new BadArgsException(name, code, args);
    }

    //
    public static void eqVal(long a, long b, @NotNull String name, @NotNull String msg) {
        if (a != b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void eqVal(long a, long b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a != b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void eqVal(long a, long b, @NotNull String name, @NotNull CodeEnum code) {
        if (a != b) throw new BadArgsException(name, code);
    }

    public static void eqVal(long a, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a != b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.eq(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.eq(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.eq(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.eq(as, b), name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.eq(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.eq(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.eq(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.eq(as, b), name, code, args);
    }

    //
    public static void neVal(int a, int b, @NotNull String name, @NotNull String msg) {
        if (a == b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void neVal(int a, int b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void neVal(int a, int b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == b) throw new BadArgsException(name, code);
    }

    public static void neVal(int a, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == b) throw new BadArgsException(name, code, args);
    }

    //
    public static void neVal(long a, long b, @NotNull String name, @NotNull String msg) {
        if (a == b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void neVal(long a, long b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a == b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void neVal(long a, long b, @NotNull String name, @NotNull CodeEnum code) {
        if (a == b) throw new BadArgsException(name, code);
    }

    public static void neVal(long a, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a == b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.ne(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ne(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.ne(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ne(as, b), name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.ne(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ne(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.ne(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ne(as, b), name, code, args);
    }

    //
    public static void geVal(int a, int b, @NotNull String name, @NotNull String msg) {
        if (a < b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void geVal(int a, int b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a < b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void geVal(int a, int b, @NotNull String name, @NotNull CodeEnum code) {
        if (a < b) throw new BadArgsException(name, code);
    }

    public static void geVal(int a, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a < b) throw new BadArgsException(name, code, args);
    }

    //
    public static void geVal(long a, long b, @NotNull String name, @NotNull String msg) {
        if (a < b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void geVal(long a, long b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a < b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void geVal(long a, long b, @NotNull String name, @NotNull CodeEnum code) {
        if (a < b) throw new BadArgsException(name, code);
    }

    public static void geVal(long a, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a < b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.ge(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ge(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.ge(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ge(as, b), name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.ge(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ge(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.ge(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ge(as, b), name, code, args);
    }


    //
    public static void gtVal(int a, int b, @NotNull String name, @NotNull String msg) {
        if (a <= b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void gtVal(int a, int b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a <= b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void gtVal(int a, int b, @NotNull String name, @NotNull CodeEnum code) {
        if (a <= b) throw new BadArgsException(name, code);
    }

    public static void gtVal(int a, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a <= b) throw new BadArgsException(name, code, args);
    }

    //
    public static void gtVal(long a, long b, @NotNull String name, @NotNull String msg) {
        if (a <= b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void gtVal(long a, long b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a <= b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void gtVal(long a, long b, @NotNull String name, @NotNull CodeEnum code) {
        if (a <= b) throw new BadArgsException(name, code);
    }

    public static void gtVal(long a, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a <= b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.gt(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.gt(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.gt(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.gt(as, b), name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.gt(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.gt(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.gt(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.gt(as, b), name, code, args);
    }

    //
    public static void leVal(int a, int b, @NotNull String name, @NotNull String msg) {
        if (a > b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void leVal(int a, int b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a > b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void leVal(int a, int b, @NotNull String name, @NotNull CodeEnum code) {
        if (a > b) throw new BadArgsException(name, code);
    }

    public static void leVal(int a, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a > b) throw new BadArgsException(name, code, args);
    }

    //
    public static void leVal(long a, long b, @NotNull String name, @NotNull String msg) {
        if (a > b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void leVal(long a, long b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a > b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void leVal(long a, long b, @NotNull String name, @NotNull CodeEnum code) {
        if (a > b) throw new BadArgsException(name, code);
    }

    public static void leVal(long a, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a > b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.le(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.le(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.le(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.le(as, b), name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.le(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.le(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.le(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.le(as, b), name, code, args);
    }

    //
    public static void ltVal(int a, int b, @NotNull String name, @NotNull String msg) {
        if (a >= b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void ltVal(int a, int b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a >= b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void ltVal(int a, int b, @NotNull String name, @NotNull CodeEnum code) {
        if (a >= b) throw new BadArgsException(name, code);
    }

    public static void ltVal(int a, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a >= b) throw new BadArgsException(name, code, args);
    }

    //
    public static void ltVal(long a, long b, @NotNull String name, @NotNull String msg) {
        if (a >= b) throw new IllegalArgumentException(FormatUtil.named(name, msg));
    }

    public static void ltVal(long a, long b, @NotNull String name, @NotNull String msg, Object... args) {
        if (a >= b) throw new IllegalArgumentException(FormatUtil.named(name, FormatUtil.logback(msg, args)));
    }

    public static void ltVal(long a, long b, @NotNull String name, @NotNull CodeEnum code) {
        if (a >= b) throw new BadArgsException(name, code);
    }

    public static void ltVal(long a, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        if (a >= b) throw new BadArgsException(name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.lt(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.lt(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.lt(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.lt(as, b), name, code, args);
    }

    //
    @Contract("null,_,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull String name, @NotNull String msg) {
        isTrue(PredictVal.lt(as, b), name, msg);
    }

    @Contract("null,_,_,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull String name, @NotNull String msg, Object... args) {
        isTrue(PredictVal.lt(as, b), name, msg, args);
    }

    @Contract("null,_,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code) {
        isTrue(PredictVal.lt(as, b), name, code);
    }

    @Contract("null,_,_,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull String name, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.lt(as, b), name, code, args);
    }
}
