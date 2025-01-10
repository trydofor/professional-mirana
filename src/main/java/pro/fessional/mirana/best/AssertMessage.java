package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.cond.PredictVal;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.pain.MessageException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 * pre-check / post-check. throw MessageException(without stack) if not match.
 *
 * NOTE1: should use isTure/isFalse instead of xxObj to assert primitive type (int/long/float/double)
 * </pre>
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class AssertMessage {

    //
    @Contract("false,_->fail")
    public static void isTrue(boolean b, @NotNull String msg) {
        if (!b) throw new MessageException(msg);
    }

    @Contract("false,_,_->fail")
    public static void isTrue(boolean b, @NotNull String msg, Object... args) {
        if (!b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("false,_->fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code) {
        if (!b) throw new MessageException(code);
    }

    @Contract("false,_,_->fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code, Object... args) {
        if (!b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_->fail")
    public static void isTrue(boolean[] bs, @NotNull String msg) {
        isTrue(PredictVal.is(bs, true), msg);
    }

    @Contract("null,_,_->fail")
    public static void isTrue(boolean[] bs, @NotNull String msg, Object... args) {
        isTrue(PredictVal.is(bs, true), msg, args);
    }

    @Contract("null,_->fail")
    public static void isTrue(boolean[] bs, @NotNull CodeEnum code) {
        isTrue(PredictVal.is(bs, true), code);
    }

    @Contract("null,_,_->fail")
    public static void isTrue(boolean[] bs, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.is(bs, true), code, args);
    }

    //
    @Contract("true,_->fail")
    public static void isFalse(boolean b, @NotNull String msg) {
        if (b) throw new MessageException(msg);
    }

    @Contract("true,_,_->fail")
    public static void isFalse(boolean b, @NotNull String msg, Object... args) {
        if (b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("true,_->fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code) {
        if (b) throw new MessageException(code);
    }

    @Contract("true,_,_->fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code, Object... args) {
        if (b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_->fail")
    public static void isFalse(boolean[] bs, @NotNull String msg) {
        isTrue(PredictVal.is(bs, false), msg);
    }

    @Contract("null,_,_->fail")
    public static void isFalse(boolean[] bs, @NotNull String msg, Object... args) {
        isTrue(PredictVal.is(bs, false), msg, args);
    }

    @Contract("null,_->fail")
    public static void isFalse(boolean[] bs, @NotNull CodeEnum code) {
        isTrue(PredictVal.is(bs, false), code);
    }

    @Contract("null,_,_->fail")
    public static void isFalse(boolean[] bs, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.is(bs, false), code, args);
    }

    // ////
    @Contract("!null,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String msg) {
        if (b != null) throw new MessageException(msg);
    }

    @Contract("!null,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b != null) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("!null,_->fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code) {
        if (b != null) throw new MessageException(code);
    }

    @Contract("!null,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b != null) throw new MessageException(code, args);
    }

    //
    @Contract("null,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String msg) {
        if (b == null) throw new MessageException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b == null) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notNull(@Nullable Object b, @NotNull CodeEnum code) {
        if (b == null) throw new MessageException(code);
    }

    @Contract("null,_,_->fail")
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
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new MessageException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c == null || c.length() == 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c == null || c.length() == 0) throw new MessageException(code);
    }

    @Contract("null,_,_->fail")
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
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new MessageException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new MessageException(code);
    }

    @Contract("null,_,_->fail")
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
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new MessageException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new MessageException(code);
    }

    @Contract("null,_,_->fail")
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
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c == null || c.length == 0) throw new MessageException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c == null || c.length == 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code) {
        if (c == null || c.length == 0) throw new MessageException(code);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length == 0) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg) {
        if (a == null || !a.equals(b)) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg, Object... args) {
        if (a == null || !a.equals(b)) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code) {
        if (a == null || !a.equals(b)) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code, Object... args) {
        if (a == null || !a.equals(b)) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg) {
        if (a == null || a.equals(b)) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg, Object... args) {
        if (a == null || a.equals(b)) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code) {
        if (a == null || a.equals(b)) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.equals(b)) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) != 0) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) != 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) != 0) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) != 0) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) == 0) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) == 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) == 0) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) == 0) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new MessageException(code, args);
    }

    //
    public static void eqVal(int a, int b, @NotNull String msg) {
        if (a != b) throw new MessageException(msg);
    }

    public static void eqVal(int a, int b, @NotNull String msg, Object... args) {
        if (a != b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void eqVal(int a, int b, @NotNull CodeEnum code) {
        if (a != b) throw new MessageException(code);
    }

    public static void eqVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a != b) throw new MessageException(code, args);
    }

    //
    public static void eqVal(long a, long b, @NotNull String msg) {
        if (a != b) throw new MessageException(msg);
    }

    public static void eqVal(long a, long b, @NotNull String msg, Object... args) {
        if (a != b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void eqVal(long a, long b, @NotNull CodeEnum code) {
        if (a != b) throw new MessageException(code);
    }

    public static void eqVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a != b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull String msg) {
        isTrue(PredictVal.eq(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.eq(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull CodeEnum code) {
        isTrue(PredictVal.eq(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void eqVal(int[] as, int b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.eq(as, b), code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull String msg) {
        isTrue(PredictVal.eq(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.eq(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull CodeEnum code) {
        isTrue(PredictVal.eq(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void eqVal(long[] as, long b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.eq(as, b), code, args);
    }

    //
    public static void neVal(int a, int b, @NotNull String msg) {
        if (a == b) throw new MessageException(msg);
    }

    public static void neVal(int a, int b, @NotNull String msg, Object... args) {
        if (a == b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void neVal(int a, int b, @NotNull CodeEnum code) {
        if (a == b) throw new MessageException(code);
    }

    public static void neVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a == b) throw new MessageException(code, args);
    }

    //
    public static void neVal(long a, long b, @NotNull String msg) {
        if (a == b) throw new MessageException(msg);
    }

    public static void neVal(long a, long b, @NotNull String msg, Object... args) {
        if (a == b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void neVal(long a, long b, @NotNull CodeEnum code) {
        if (a == b) throw new MessageException(code);
    }

    public static void neVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a == b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull String msg) {
        isTrue(PredictVal.ne(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ne(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull CodeEnum code) {
        isTrue(PredictVal.ne(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void neVal(int[] as, int b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ne(as, b), code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull String msg) {
        isTrue(PredictVal.ne(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ne(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull CodeEnum code) {
        isTrue(PredictVal.ne(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void neVal(long[] as, long b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ne(as, b), code, args);
    }

    //
    public static void geVal(int a, int b, @NotNull String msg) {
        if (a < b) throw new MessageException(msg);
    }

    public static void geVal(int a, int b, @NotNull String msg, Object... args) {
        if (a < b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void geVal(int a, int b, @NotNull CodeEnum code) {
        if (a < b) throw new MessageException(code);
    }

    public static void geVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a < b) throw new MessageException(code, args);
    }

    //
    public static void geVal(long a, long b, @NotNull String msg) {
        if (a < b) throw new MessageException(msg);
    }

    public static void geVal(long a, long b, @NotNull String msg, Object... args) {
        if (a < b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void geVal(long a, long b, @NotNull CodeEnum code) {
        if (a < b) throw new MessageException(code);
    }

    public static void geVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a < b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull String msg) {
        isTrue(PredictVal.ge(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ge(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull CodeEnum code) {
        isTrue(PredictVal.ge(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void geVal(int[] as, int b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ge(as, b), code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull String msg) {
        isTrue(PredictVal.ge(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.ge(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull CodeEnum code) {
        isTrue(PredictVal.ge(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void geVal(long[] as, long b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.ge(as, b), code, args);
    }


    //
    public static void gtVal(int a, int b, @NotNull String msg) {
        if (a <= b) throw new MessageException(msg);
    }

    public static void gtVal(int a, int b, @NotNull String msg, Object... args) {
        if (a <= b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void gtVal(int a, int b, @NotNull CodeEnum code) {
        if (a <= b) throw new MessageException(code);
    }

    public static void gtVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a <= b) throw new MessageException(code, args);
    }

    //
    public static void gtVal(long a, long b, @NotNull String msg) {
        if (a <= b) throw new MessageException(msg);
    }

    public static void gtVal(long a, long b, @NotNull String msg, Object... args) {
        if (a <= b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void gtVal(long a, long b, @NotNull CodeEnum code) {
        if (a <= b) throw new MessageException(code);
    }

    public static void gtVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a <= b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull String msg) {
        isTrue(PredictVal.gt(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.gt(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull CodeEnum code) {
        isTrue(PredictVal.gt(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void gtVal(int[] as, int b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.gt(as, b), code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull String msg) {
        isTrue(PredictVal.gt(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.gt(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull CodeEnum code) {
        isTrue(PredictVal.gt(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void gtVal(long[] as, long b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.gt(as, b), code, args);
    }

    //
    public static void leVal(int a, int b, @NotNull String msg) {
        if (a > b) throw new MessageException(msg);
    }

    public static void leVal(int a, int b, @NotNull String msg, Object... args) {
        if (a > b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void leVal(int a, int b, @NotNull CodeEnum code) {
        if (a > b) throw new MessageException(code);
    }

    public static void leVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a > b) throw new MessageException(code, args);
    }

    //
    public static void leVal(long a, long b, @NotNull String msg) {
        if (a > b) throw new MessageException(msg);
    }

    public static void leVal(long a, long b, @NotNull String msg, Object... args) {
        if (a > b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void leVal(long a, long b, @NotNull CodeEnum code) {
        if (a > b) throw new MessageException(code);
    }

    public static void leVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a > b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull String msg) {
        isTrue(PredictVal.le(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.le(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull CodeEnum code) {
        isTrue(PredictVal.le(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void leVal(int[] as, int b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.le(as, b), code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull String msg) {
        isTrue(PredictVal.le(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.le(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull CodeEnum code) {
        isTrue(PredictVal.le(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void leVal(long[] as, long b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.le(as, b), code, args);
    }

    //
    public static void ltVal(int a, int b, @NotNull String msg) {
        if (a >= b) throw new MessageException(msg);
    }

    public static void ltVal(int a, int b, @NotNull String msg, Object... args) {
        if (a >= b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void ltVal(int a, int b, @NotNull CodeEnum code) {
        if (a >= b) throw new MessageException(code);
    }

    public static void ltVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a >= b) throw new MessageException(code, args);
    }

    //
    public static void ltVal(long a, long b, @NotNull String msg) {
        if (a >= b) throw new MessageException(msg);
    }

    public static void ltVal(long a, long b, @NotNull String msg, Object... args) {
        if (a >= b) throw new MessageException(FormatUtil.logback(msg, args));
    }

    public static void ltVal(long a, long b, @NotNull CodeEnum code) {
        if (a >= b) throw new MessageException(code);
    }

    public static void ltVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a >= b) throw new MessageException(code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull String msg) {
        isTrue(PredictVal.lt(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.lt(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull CodeEnum code) {
        isTrue(PredictVal.lt(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void ltVal(int[] as, int b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.lt(as, b), code, args);
    }

    //
    @Contract("null,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull String msg) {
        isTrue(PredictVal.lt(as, b), msg);
    }

    @Contract("null,_,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull String msg, Object... args) {
        isTrue(PredictVal.lt(as, b), msg, args);
    }

    @Contract("null,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull CodeEnum code) {
        isTrue(PredictVal.lt(as, b), code);
    }

    @Contract("null,_,_,_->fail")
    public static void ltVal(long[] as, long b, @NotNull CodeEnum code, Object... args) {
        isTrue(PredictVal.lt(as, b), code, args);
    }
}
