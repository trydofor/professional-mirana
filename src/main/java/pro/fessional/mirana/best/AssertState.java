package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.cond.PredictVal;
import pro.fessional.mirana.i18n.AssertErrorEnum;
import pro.fessional.mirana.i18n.CodeEnum;
import pro.fessional.mirana.pain.BadStateException;
import pro.fessional.mirana.text.FormatUtil;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 * post-check. throw IllegalStateException or BadStateException(default stackless) if not match.
 *
 * NOTE1: should use isTure/isFalse instead of xxObj to assert primitive type (int/long/float/double)
 * </pre>
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class AssertState {

    //
    @Contract("false,_->fail")
    public static void isTrue(boolean b, @NotNull String msg) {
        if (!b) throw new IllegalStateException(msg);
    }

    @Contract("false,_,_->fail")
    public static void isTrue(boolean b, @NotNull String msg, Object... args) {
        if (!b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("false,_->fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code) {
        if (!b) throw new BadStateException(code);
    }

    @Contract("false,_,_->fail")
    public static void isTrue(boolean b, @NotNull CodeEnum code, Object... args) {
        if (!b) throw new BadStateException(code, args);
    }

    @Contract("false->fail")
    public static void isTrue(boolean b) {
        isTrue(b, AssertErrorEnum.AssertTrue);
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

    @Contract("null->fail")
    public static void isTrue(boolean[] bs) {
        isTrue(bs, AssertErrorEnum.AssertTrue);
    }

    //
    @Contract("true,_->fail")
    public static void isFalse(boolean b, @NotNull String msg) {
        if (b) throw new IllegalStateException(msg);
    }

    @Contract("true,_,_->fail")
    public static void isFalse(boolean b, @NotNull String msg, Object... args) {
        if (b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("true,_->fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code) {
        if (b) throw new BadStateException(code);
    }

    @Contract("true,_,_->fail")
    public static void isFalse(boolean b, @NotNull CodeEnum code, Object... args) {
        if (b) throw new BadStateException(code, args);
    }

    @Contract("true->fail")
    public static void isFalse(boolean b) {
        isFalse(b, AssertErrorEnum.AssertFalse);
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

    @Contract("null->fail")
    public static void isFalse(boolean[] bs) {
        isFalse(bs, AssertErrorEnum.AssertFalse);
    }

    // ////
    @Contract("!null,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String msg) {
        if (b != null) throw new IllegalStateException(msg);
    }

    @Contract("!null,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b != null) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("!null,_->fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code) {
        if (b != null) throw new BadStateException(code);
    }

    @Contract("!null,_,_->fail")
    public static void isNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b != null) throw new BadStateException(code, args);
    }

    @Contract("!null->fail")
    public static void isNull(@Nullable Object b) {
        isNull(b, AssertErrorEnum.AssertNull);
    }

    //
    @Contract("null,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String msg) {
        if (b == null) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull String msg, Object... args) {
        if (b == null) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notNull(@Nullable Object b, @NotNull CodeEnum code) {
        if (b == null) throw new BadStateException(code);
    }

    @Contract("null,_,_->fail")
    public static void notNull(@Nullable Object b, @NotNull CodeEnum code, Object... args) {
        if (b == null) throw new BadStateException(code, args);
    }

    @Contract("null->fail")
    public static void notNull(@Nullable Object b) {
        notNull(b, AssertErrorEnum.AssertNotNull);
    }

    // ////
    public static void isEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c != null && c.length() > 0) throw new IllegalStateException(msg);
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c != null && c.length() > 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c != null && c.length() > 0) throw new BadStateException(code);
    }

    public static void isEmpty(@Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length() > 0) throw new BadStateException(code, args);
    }

    public static void isEmpty(@Nullable CharSequence c) {
        isEmpty(c, AssertErrorEnum.AssertEmpty);
    }

    //
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg) {
        if (c == null || c.length() == 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull String msg, Object... args) {
        if (c == null || c.length() == 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull CodeEnum code) {
        if (c == null || c.length() == 0) throw new BadStateException(code);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable CharSequence c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length() == 0) throw new BadStateException(code, args);
    }

    @Contract("null->fail")
    public static void notEmpty(@Nullable CharSequence c) {
        notEmpty(c, AssertErrorEnum.AssertNotEmpty);
    }

    // ////
    public static void isEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(msg);
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code);
    }

    public static void isEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code, args);
    }

    public static void isEmpty(@Nullable Collection<?> c) {
        isEmpty(c, AssertErrorEnum.AssertEmpty);
    }

    //
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new BadStateException(code);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Collection<?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(code, args);
    }

    @Contract("null->fail")
    public static void notEmpty(@Nullable Collection<?> c) {
        notEmpty(c, AssertErrorEnum.AssertNotEmpty);
    }

    // ////
    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(msg);
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c != null && !c.isEmpty()) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code);
    }

    public static void isEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c != null && !c.isEmpty()) throw new BadStateException(code, args);
    }

    public static void isEmpty(@Nullable Map<?, ?> c) {
        isEmpty(c, AssertErrorEnum.AssertEmpty);
    }

    //
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull String msg, Object... args) {
        if (c == null || c.isEmpty()) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code) {
        if (c == null || c.isEmpty()) throw new BadStateException(code);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Map<?, ?> c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.isEmpty()) throw new BadStateException(code, args);
    }

    @Contract("null->fail")
    public static void notEmpty(@Nullable Map<?, ?> c) {
        notEmpty(c, AssertErrorEnum.AssertNotEmpty);
    }

    // ////
    public static void isEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c != null && c.length > 0) throw new IllegalStateException(msg);
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c != null && c.length > 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull CodeEnum code) {
        if (c != null && c.length > 0) throw new BadStateException(code);
    }

    public static void isEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c != null && c.length > 0) throw new BadStateException(code, args);
    }

    public static void isEmpty(@Nullable Object[] c) {
        isEmpty(c, AssertErrorEnum.AssertEmpty);
    }

    //
    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg) {
        if (c == null || c.length == 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull String msg, Object... args) {
        if (c == null || c.length == 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code) {
        if (c == null || c.length == 0) throw new BadStateException(code);
    }

    @Contract("null,_,_->fail")
    public static void notEmpty(@Nullable Object[] c, @NotNull CodeEnum code, Object... args) {
        if (c == null || c.length == 0) throw new BadStateException(code, args);
    }

    @Contract("null->fail")
    public static void notEmpty(@Nullable Object[] c) {
        notEmpty(c, AssertErrorEnum.AssertNotEmpty);
    }

    //
    @Contract("null,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg) {
        if (a == null || !a.equals(b)) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg, Object... args) {
        if (a == null || !a.equals(b)) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code) {
        if (a == null || !a.equals(b)) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code, Object... args) {
        if (a == null || !a.equals(b)) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static void isEqual(@Nullable Object a, @NotNull Object b) {
        isEqual(a, b, AssertErrorEnum.AssertEqual1, b);
    }

    //
    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg) {
        if (a != null && a.equals(b)) throw new IllegalStateException(msg);
    }

    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull String msg, Object... args) {
        if (a != null && a.equals(b)) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code) {
        if (a != null && a.equals(b)) throw new BadStateException(code);
    }

    public static void notEqual(@Nullable Object a, @NotNull Object b, @NotNull CodeEnum code, Object... args) {
        if (a != null && a.equals(b)) throw new BadStateException(code, args);
    }

    public static void notEqual(@Nullable Object a, @NotNull Object b) {
        notEqual(a, b, AssertErrorEnum.AssertNotEqual1, b);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) != 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) != 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) != 0) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) != 0) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static <T extends Comparable<T>> void eqObj(@Nullable T a, @NotNull T b) {
        eqObj(a, b, AssertErrorEnum.AssertEqual1, b);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) == 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) == 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) == 0) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) == 0) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static <T extends Comparable<T>> void neObj(@Nullable T a, @NotNull T b) {
        neObj(a, b, AssertErrorEnum.AssertNotEqual1, b);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) < 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) < 0) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) < 0) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static <T extends Comparable<T>> void geObj(@Nullable T a, @NotNull T b) {
        geObj(a, b, AssertErrorEnum.AssertGreaterEqual1, b);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) <= 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) <= 0) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) <= 0) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static <T extends Comparable<T>> void gtObj(@Nullable T a, @NotNull T b) {
        gtObj(a, b, AssertErrorEnum.AssertGreater1, b);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) > 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) > 0) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) > 0) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static <T extends Comparable<T>> void leObj(@Nullable T a, @NotNull T b) {
        leObj(a, b, AssertErrorEnum.AssertLessEqual1, b);
    }

    //
    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String msg) {
        if (a == null || a.compareTo(b) >= 0) throw new IllegalStateException(msg);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull String msg, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    @Contract("null,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code) {
        if (a == null || a.compareTo(b) >= 0) throw new BadStateException(code);
    }

    @Contract("null,_,_,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b, @NotNull CodeEnum code, Object... args) {
        if (a == null || a.compareTo(b) >= 0) throw new BadStateException(code, args);
    }

    @Contract("null,_->fail")
    public static <T extends Comparable<T>> void ltObj(@Nullable T a, @NotNull T b) {
        ltObj(a, b, AssertErrorEnum.AssertLess1, b);
    }

    //
    public static void eqVal(int a, int b, @NotNull String msg) {
        if (a != b) throw new IllegalStateException(msg);
    }

    public static void eqVal(int a, int b, @NotNull String msg, Object... args) {
        if (a != b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void eqVal(int a, int b, @NotNull CodeEnum code) {
        if (a != b) throw new BadStateException(code);
    }

    public static void eqVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a != b) throw new BadStateException(code, args);
    }

    public static void eqVal(int a, int b) {
        eqVal(a, b, AssertErrorEnum.AssertEqual1, b);
    }

    //
    public static void eqVal(long a, long b, @NotNull String msg) {
        if (a != b) throw new IllegalStateException(msg);
    }

    public static void eqVal(long a, long b, @NotNull String msg, Object... args) {
        if (a != b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void eqVal(long a, long b, @NotNull CodeEnum code) {
        if (a != b) throw new BadStateException(code);
    }

    public static void eqVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a != b) throw new BadStateException(code, args);
    }

    public static void eqVal(long a, long b) {
        eqVal(a, b, AssertErrorEnum.AssertEqual1, b);
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

    @Contract("null,_->fail")
    public static void eqVal(int[] as, int b) {
        eqVal(as, b, AssertErrorEnum.AssertEqual1, b);
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

    @Contract("null,_->fail")
    public static void eqVal(long[] as, long b) {
        eqVal(as, b, AssertErrorEnum.AssertEqual1, b);
    }

    //
    public static void neVal(int a, int b, @NotNull String msg) {
        if (a == b) throw new IllegalStateException(msg);
    }

    public static void neVal(int a, int b, @NotNull String msg, Object... args) {
        if (a == b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void neVal(int a, int b, @NotNull CodeEnum code) {
        if (a == b) throw new BadStateException(code);
    }

    public static void neVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a == b) throw new BadStateException(code, args);
    }

    public static void neVal(int a, int b) {
        neVal(a, b, AssertErrorEnum.AssertNotEqual1, b);
    }

    //
    public static void neVal(long a, long b, @NotNull String msg) {
        if (a == b) throw new IllegalStateException(msg);
    }

    public static void neVal(long a, long b, @NotNull String msg, Object... args) {
        if (a == b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void neVal(long a, long b, @NotNull CodeEnum code) {
        if (a == b) throw new BadStateException(code);
    }

    public static void neVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a == b) throw new BadStateException(code, args);
    }

    public static void neVal(long a, long b) {
        neVal(a, b, AssertErrorEnum.AssertNotEqual1, b);
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

    @Contract("null,_->fail")
    public static void neVal(int[] as, int b) {
        neVal(as, b, AssertErrorEnum.AssertNotEqual1, b);
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

    @Contract("null,_->fail")
    public static void neVal(long[] as, long b) {
        neVal(as, b, AssertErrorEnum.AssertNotEqual1, b);
    }

    //
    public static void geVal(int a, int b, @NotNull String msg) {
        if (a < b) throw new IllegalStateException(msg);
    }

    public static void geVal(int a, int b, @NotNull String msg, Object... args) {
        if (a < b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void geVal(int a, int b, @NotNull CodeEnum code) {
        if (a < b) throw new BadStateException(code);
    }

    public static void geVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a < b) throw new BadStateException(code, args);
    }

    public static void geVal(int a, int b) {
        geVal(a, b, AssertErrorEnum.AssertGreaterEqual1, b);
    }

    //
    public static void geVal(long a, long b, @NotNull String msg) {
        if (a < b) throw new IllegalStateException(msg);
    }

    public static void geVal(long a, long b, @NotNull String msg, Object... args) {
        if (a < b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void geVal(long a, long b, @NotNull CodeEnum code) {
        if (a < b) throw new BadStateException(code);
    }

    public static void geVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a < b) throw new BadStateException(code, args);
    }

    public static void geVal(long a, long b) {
        geVal(a, b, AssertErrorEnum.AssertGreaterEqual1, b);
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

    @Contract("null,_->fail")
    public static void geVal(int[] as, int b) {
        geVal(as, b, AssertErrorEnum.AssertGreaterEqual1, b);
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

    @Contract("null,_->fail")
    public static void geVal(long[] as, long b) {
        geVal(as, b, AssertErrorEnum.AssertGreaterEqual1, b);
    }

    //
    public static void gtVal(int a, int b, @NotNull String msg) {
        if (a <= b) throw new IllegalStateException(msg);
    }

    public static void gtVal(int a, int b, @NotNull String msg, Object... args) {
        if (a <= b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void gtVal(int a, int b, @NotNull CodeEnum code) {
        if (a <= b) throw new BadStateException(code);
    }

    public static void gtVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a <= b) throw new BadStateException(code, args);
    }

    public static void gtVal(int a, int b) {
        gtVal(a, b, AssertErrorEnum.AssertGreater1, b);
    }

    //
    public static void gtVal(long a, long b, @NotNull String msg) {
        if (a <= b) throw new IllegalStateException(msg);
    }

    public static void gtVal(long a, long b, @NotNull String msg, Object... args) {
        if (a <= b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void gtVal(long a, long b, @NotNull CodeEnum code) {
        if (a <= b) throw new BadStateException(code);
    }

    public static void gtVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a <= b) throw new BadStateException(code, args);
    }

    public static void gtVal(long a, long b) {
        gtVal(a, b, AssertErrorEnum.AssertGreater1, b);
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

    @Contract("null,_->fail")
    public static void gtVal(int[] as, int b) {
        gtVal(as, b, AssertErrorEnum.AssertGreater1, b);
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

    @Contract("null,_->fail")
    public static void gtVal(long[] as, long b) {
        gtVal(as, b, AssertErrorEnum.AssertGreater1, b);
    }

    //
    public static void leVal(int a, int b, @NotNull String msg) {
        if (a > b) throw new IllegalStateException(msg);
    }

    public static void leVal(int a, int b, @NotNull String msg, Object... args) {
        if (a > b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void leVal(int a, int b, @NotNull CodeEnum code) {
        if (a > b) throw new BadStateException(code);
    }

    public static void leVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a > b) throw new BadStateException(code, args);
    }

    public static void leVal(int a, int b) {
        leVal(a, b, AssertErrorEnum.AssertLessEqual1, b);
    }

    //
    public static void leVal(long a, long b, @NotNull String msg) {
        if (a > b) throw new IllegalStateException(msg);
    }

    public static void leVal(long a, long b, @NotNull String msg, Object... args) {
        if (a > b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void leVal(long a, long b, @NotNull CodeEnum code) {
        if (a > b) throw new BadStateException(code);
    }

    public static void leVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a > b) throw new BadStateException(code, args);
    }

    public static void leVal(long a, long b) {
        leVal(a, b, AssertErrorEnum.AssertLessEqual1, b);
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

    @Contract("null,_->fail")
    public static void leVal(int[] as, int b) {
        leVal(as, b, AssertErrorEnum.AssertLessEqual1, b);
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

    @Contract("null,_->fail")
    public static void leVal(long[] as, long b) {
        leVal(as, b, AssertErrorEnum.AssertLessEqual1, b);
    }

    //
    public static void ltVal(int a, int b, @NotNull String msg) {
        if (a >= b) throw new IllegalStateException(msg);
    }

    public static void ltVal(int a, int b, @NotNull String msg, Object... args) {
        if (a >= b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void ltVal(int a, int b, @NotNull CodeEnum code) {
        if (a >= b) throw new BadStateException(code);
    }

    public static void ltVal(int a, int b, @NotNull CodeEnum code, Object... args) {
        if (a >= b) throw new BadStateException(code, args);
    }

    public static void ltVal(int a, int b) {
        ltVal(a, b, AssertErrorEnum.AssertLess1, b);
    }

    //
    public static void ltVal(long a, long b, @NotNull String msg) {
        if (a >= b) throw new IllegalStateException(msg);
    }

    public static void ltVal(long a, long b, @NotNull String msg, Object... args) {
        if (a >= b) throw new IllegalStateException(FormatUtil.logback(msg, args));
    }

    public static void ltVal(long a, long b, @NotNull CodeEnum code) {
        if (a >= b) throw new BadStateException(code);
    }

    public static void ltVal(long a, long b, @NotNull CodeEnum code, Object... args) {
        if (a >= b) throw new BadStateException(code, args);
    }

    public static void ltVal(long a, long b) {
        ltVal(a, b, AssertErrorEnum.AssertLess1, b);
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

    @Contract("null,_->fail")
    public static void ltVal(int[] as, int b) {
        ltVal(as, b, AssertErrorEnum.AssertLess1, b);
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

    @Contract("null,_->fail")
    public static void ltVal(long[] as, long b) {
        ltVal(as, b, AssertErrorEnum.AssertLess1, b);
    }
}
