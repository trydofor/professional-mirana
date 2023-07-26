package pro.fessional.mirana.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.FLOOR;
import static java.math.RoundingMode.HALF_UP;

/**
 * Null-friendly BigDecimal utility
 *
 * @author trydofor
 * @since 2015-12-11.
 */
public class BigDecimalUtil {

    private static final MathContext MC = MathContext.DECIMAL32;

    // ////// string //////

    /**
     * Return empty if `num` is null
     */
    @NotNull
    public static String string(BigDecimal num) {
        return string(num, "");
    }

    /**
     * Remove all digit after `scale` (exclude) then `ceil` the number.
     */
    @NotNull
    public static String string(BigDecimal num, int scale) {
        BigDecimal dec = ceil(num, scale);
        return dec.toPlainString();
    }

    /**
     * Return `elze` if `num` is null
     */
    public static String string(BigDecimal num, String elze) {
        if (num == null) return elze;
        return num.toPlainString();
    }

    /**
     * Remove zeros at the end if `strip`.
     */
    @NotNull
    public static String string(BigDecimal num, boolean strip) {
        return string(num, "", strip);
    }

    /**
     * <pre>
     * Remove all digit after `scale` (exclude), then `ceil` the number
     * Remove zeros at the end if `strip`.
     * </pre>
     */
    @NotNull
    public static String string(BigDecimal num, int scale, boolean strip) {
        BigDecimal dec = ceil(num, scale);
        return string(dec, "", strip);
    }

    /**
     * <pre>
     * Return `elze` if `num` null.
     * Remove all digit after `scale` (exclude), then `ceil` the number
     * Remove zeros at the end if `strip`.
     * </pre>
     */
    public static String string(BigDecimal num, String elze, boolean strip) {
        if (num == null) return elze;
        return strip ? num.stripTrailingZeros().toPlainString() : num.toPlainString();
    }

    // ////// object //////

    /**
     * convert `num` object to BigDecimal
     */
    @Contract("!null -> !null")
    public static BigDecimal object(Object num) {
        return object(num, null, false);
    }

    /**
     * convert `num` object to BigDecimal, return `elze` if `num` is null.
     */
    @Contract("_, !null -> !null")
    public static BigDecimal object(Object num, BigDecimal elze) {
        return object(num, elze, false);
    }

    /**
     * convert `num` object to BigDecimal, return `elze` if `num` is null.
     * throw the exception if there is exception and `fail`
     */
    @Contract("_, !null, _ -> !null")
    public static BigDecimal object(Object num, BigDecimal elze, boolean fail) {
        BigDecimal r;
        if (num == null) {
            r = elze;
        }
        else if (num instanceof Integer) {
            r = new BigDecimal((Integer) num);
        }
        else if (num instanceof Long) {
            r = new BigDecimal((Long) num);
        }
        else if (num instanceof Double) {
            r = BigDecimal.valueOf((Double) num);
        }
        else if (num instanceof Float) {
            r = BigDecimal.valueOf((Float) num);
        }
        else if (num instanceof BigInteger) {
            r = new BigDecimal((BigInteger) num);
        }
        else if (num instanceof BigDecimal) {
            r = (BigDecimal) num;
        }
        else {
            final String str = num.toString().trim();
            try {
                r = str.isEmpty() ? elze : new BigDecimal(str);
            }
            catch (Exception e) {
                if (fail) {
                    throw e;
                }
                else {
                    r = elze;
                }
            }

        }

        return r;
    }

    @NotNull
    public static BigDecimal[] objects(BigDecimal elze, Object... nums) {
        if (nums == null || nums.length == 0) return Null.BigDecimals;
        BigDecimal[] arr = new BigDecimal[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = object(nums[i], elze, false);
        }
        return arr;
    }

    // ////// notnull //////

    /**
     * get the first parsed non-null number, NullPointerException if all null.
     */
    @NotNull
    public static BigDecimal notNull(Object... nums) {
        BigDecimal d = null;
        for (Object s : nums) {
            d = object(s);
            if (d != null) break;
        }
        return Objects.requireNonNull(d);
    }

    /**
     * parse `a` if `cond`, else `b`, NullPointerException if all null.
     */
    @NotNull
    public static BigDecimal ifElse(boolean cond, Object a, Object b) {
        final BigDecimal d = cond ? object(a) : object(b);
        return Objects.requireNonNull(d);
    }

    // ////// avg //////

    /**
     * skip null num and get the average, NullPointerException if get null.
     */
    @NotNull
    public static BigDecimal avg(Object... nums) {
        BigDecimal total = avgNull(nums);
        return Objects.requireNonNull(total);
    }

    /**
     * skip null num and get the average, NullPointerException if get null.
     */
    @NotNull
    public static BigDecimal avgMap(Iterable<?> nums) {
        BigDecimal total = avgMapNull(nums);
        return Objects.requireNonNull(total);
    }

    /**
     * skip null num and get the average, NullPointerException if get null.
     */
    @NotNull
    public static <T> BigDecimal avgMap(Iterable<T> nums, Function<? super T, ?> mapper) {
        BigDecimal total = avgMapNull(nums, mapper);
        return Objects.requireNonNull(total);
    }

    /**
     * skip null num and get the average
     */
    @Nullable
    public static BigDecimal avgNull(Object... nums) {
        if (nums == null) return null;
        return avgMapNull(Arrays.asList(nums));
    }

    /**
     * skip null num and get the average
     */
    @Nullable
    public static BigDecimal avgMapNull(Iterable<?> nums) {
        BigDecimal total = null;
        int count = 0;
        for (Object e : nums) {
            final BigDecimal d = object(e);
            if (d != null) {
                total = total == null ? d : total.add(d, MC);
                count++;
            }
        }
        return total == null ? null : total.divide(new BigDecimal(count), MC);
    }

    /**
     * mapping and skip null num and get the average
     */
    @Nullable
    public static <T> BigDecimal avgMapNull(Iterable<T> nums, Function<? super T, ?> mapper) {
        BigDecimal total = null;
        int count = 0;
        for (T e : nums) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                total = total == null ? d : total.add(d, MC);
                count++;
            }
        }
        return total == null ? null : total.divide(new BigDecimal(count), MC);
    }


    // ////// max & min //////
    @NotNull
    public static BigDecimal max(Object... nums) {
        BigDecimal max = maxNull(nums);
        return Objects.requireNonNull(max);
    }

    @NotNull
    public static BigDecimal maxMap(Iterable<?> cols) {
        final BigDecimal max = maxMapNull(cols);
        return Objects.requireNonNull(max);
    }

    @NotNull
    public static <T> BigDecimal maxMap(Iterable<T> cols, Function<? super T, ?> mapper) {
        final BigDecimal max = maxMapNull(cols, mapper);
        return Objects.requireNonNull(max);
    }

    @Nullable
    public static BigDecimal maxNull(Object... nums) {
        if (nums == null) return null;
        return maxMapNull(Arrays.asList(nums));
    }

    @Nullable
    public static BigDecimal maxMapNull(Iterable<?> cols) {
        if (cols == null) return null;

        BigDecimal max = null;
        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                max = max == null ? d : max.max(d);
            }
        }
        return max;
    }

    @Nullable
    public static <T> BigDecimal maxMapNull(Iterable<T> cols, Function<? super T, ?> mapper) {
        if (cols == null) return null;

        BigDecimal max = null;
        for (T e : cols) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                max = max == null ? d : max.max(d);
            }
        }
        return max;
    }

    @NotNull
    public static BigDecimal min(Object... nums) {
        BigDecimal min = minNull(nums);
        return Objects.requireNonNull(min);
    }

    @NotNull
    public static BigDecimal minMap(Iterable<?> cols) {
        final BigDecimal min = minMapNull(cols);
        return Objects.requireNonNull(min);
    }

    @NotNull
    public static <T> BigDecimal minMap(Iterable<T> cols, Function<? super T, ?> mapper) {
        final BigDecimal min = minMapNull(cols, mapper);
        return Objects.requireNonNull(min);
    }

    @Nullable
    public static BigDecimal minNull(Object... nums) {
        if (nums == null) return null;
        return minMapNull(Arrays.asList(nums));
    }

    @Nullable
    public static BigDecimal minMapNull(Iterable<?> cols) {
        if (cols == null) return null;

        BigDecimal min = null;
        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                min = min == null ? d : min.min(d);
            }
        }
        return min;
    }

    @Nullable
    public static <T> BigDecimal minMapNull(Iterable<T> cols, Function<? super T, ?> mapper) {
        if (cols == null) return null;

        BigDecimal min = null;
        for (T e : cols) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                min = min == null ? d : min.min(d);
            }
        }
        return min;
    }

    // ///////// add & sub /////////

    /**
     * sum `a`, `b` and `nums`, return zero if result is null
     */
    @NotNull
    public static BigDecimal sum(Object a, Object b, Object... nums) {
        return addElse(ZERO, a, b, nums);
    }

    /**
     * sum `nums`, return zero if result is null
     */
    @NotNull
    public static BigDecimal sumMap(Iterable<?> nums) {
        return addMapElse(ZERO, nums);
    }

    /**
     * mapping and sum `nums`, return zero if get null
     */
    @NotNull
    public static <T> BigDecimal sumMap(Iterable<T> nums, Function<? super T, ?> mapper) {
        return addMapElse(ZERO, nums, mapper);
    }

    @NotNull
    public static BigDecimal add(Object a, Object b) {
        BigDecimal t = addNull(a, b);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static BigDecimal add(Object a, Object b, Object... nums) {
        BigDecimal t = addNull(a, b, nums);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static BigDecimal addMap(Iterable<?> nums) {
        BigDecimal t = addMapNull(nums);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static <T> BigDecimal addMap(Iterable<T> nums, Function<? super T, ?> mapper) {
        BigDecimal t = addMapNull(nums, mapper);
        return Objects.requireNonNull(t);
    }

    @Contract("!null,_,_ -> !null")
    public static BigDecimal addElse(BigDecimal elze, Object a, Object b) {
        BigDecimal t = addNull(a, b);
        return t == null ? elze : t;
    }

    @Contract("!null,_,_,_ -> !null")
    public static BigDecimal addElse(BigDecimal elze, Object a, Object b, Object... nums) {
        BigDecimal t = addNull(a, b, nums);
        return t == null ? elze : t;
    }

    @Contract("!null,_ -> !null")
    public static BigDecimal addMapElse(BigDecimal elze, Iterable<?> nums) {
        BigDecimal t = addMapNull(nums);
        return t == null ? elze : t;
    }

    @Contract("!null,_,_ -> !null")
    public static <T> BigDecimal addMapElse(BigDecimal elze, Iterable<T> nums, Function<? super T, ?> mapper) {
        BigDecimal t = addMapNull(nums, mapper);
        return t == null ? elze : t;
    }

    @Nullable
    public static BigDecimal addNull(Object a, Object b) {
        final BigDecimal x = object(a);
        final BigDecimal y = object(b);
        if (x == null) {
            return y;
        }
        else {
            return y == null ? x : x.add(y, MC);
        }
    }

    @Nullable
    public static BigDecimal addNull(Object a, Object b, Object... nums) {
        BigDecimal t = addNull(a, b);
        if (nums == null) return t;

        for (Object num : nums) {
            final BigDecimal n = object(num);
            if (n != null) {
                t = t == null ? n : t.add(n, MC);
            }
        }
        return t;
    }

    @Nullable
    public static BigDecimal addMapNull(Iterable<?> nums) {
        if (nums == null) return null;
        BigDecimal t = null;
        for (Object e : nums) {
            final BigDecimal d = object(e);
            if (d != null) {
                t = t == null ? d : t.add(d, MC);
            }
        }
        return t;
    }

    @Nullable
    public static <T> BigDecimal addMapNull(Iterable<T> nums, Function<? super T, ?> mapper) {
        if (nums == null) return null;
        BigDecimal t = null;
        for (T e : nums) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                t = t == null ? d : t.add(d, MC);
            }
        }
        return t;
    }

    @NotNull
    public static BigDecimal sub(Object a, Object b) {
        BigDecimal t = Objects.requireNonNull(object(a));
        final BigDecimal x = object(b);
        return x == null ? t : t.subtract(x, MC);
    }

    @NotNull
    public static BigDecimal sub(Object a, Object b, Object... nums) {
        BigDecimal t = sub(a, b);
        if (nums == null) return t;
        return subMap(t, Arrays.asList(nums));
    }

    @NotNull
    public static BigDecimal subMap(Object a, Iterable<?> nums) {
        BigDecimal t = Objects.requireNonNull(object(a));
        if (nums == null) return t;

        for (Object e : nums) {
            final BigDecimal d = object(e);
            if (d != null) {
                t = t.subtract(d, MC);
            }
        }

        return t;
    }

    @NotNull
    public static <T> BigDecimal subMap(Object a, Iterable<T> nums, Function<? super T, ?> mapper) {
        BigDecimal t = Objects.requireNonNull(object(a));
        if (nums == null) return t;

        for (T e : nums) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                t = t.subtract(d, MC);
            }
        }

        return t;
    }

    // ///////// mul & div /////////

    /**
     * product `a`, `b` and `sums`
     */
    @NotNull
    public static BigDecimal prd(Object a, Object b, Object... nums) {
        return mulElse(ZERO, a, b, nums);
    }

    @NotNull
    public static BigDecimal prdMap(Iterable<?> cols) {
        return mulMapElse(ZERO, cols);
    }

    @NotNull
    public static <T> BigDecimal prdMap(Iterable<T> cols, Function<? super T, ?> mapper) {
        return mulMapElse(ZERO, cols, mapper);
    }

    @NotNull
    public static BigDecimal mul(Object a, Object b) {
        BigDecimal t = mulNull(a, b);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static BigDecimal mul(Object a, Object b, Object... nums) {
        BigDecimal t = mulNull(a, b, nums);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static BigDecimal mulMap(Iterable<?> cols) {
        BigDecimal t = mulMapNull(cols);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static <T> BigDecimal mulMap(Iterable<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = mulMapNull(cols, mapper);
        return Objects.requireNonNull(t);
    }

    @Contract("!null,_,_ -> !null")
    public static BigDecimal mulElse(BigDecimal e, Object a, Object b) {
        BigDecimal t = mulNull(a, b);
        return t == null ? e : t;
    }

    @Contract("!null,_,_,_ -> !null")
    public static BigDecimal mulElse(BigDecimal e, Object a, Object b, Object... nums) {
        BigDecimal t = mulNull(a, b, nums);
        return t == null ? e : t;
    }

    @Contract("!null,_ -> !null")
    public static <T> BigDecimal mulMapElse(BigDecimal e, Iterable<T> cols) {
        BigDecimal t = mulMapNull(cols);
        return t == null ? e : t;
    }

    @Contract("!null,_,_ -> !null")
    public static <T> BigDecimal mulMapElse(BigDecimal e, Iterable<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = mulMapNull(cols, mapper);
        return t == null ? e : t;
    }

    @Nullable
    public static BigDecimal mulNull(Object a, Object b) {
        final BigDecimal x = object(a);
        final BigDecimal y = object(b);
        if (x == null) {
            return y;
        }
        else {
            return y == null ? x : x.multiply(y, MC);
        }
    }

    @Nullable
    public static BigDecimal mulNull(Object a, Object b, Object... nums) {
        BigDecimal t = mulNull(a, b);
        if (nums == null) return t;

        for (Object num : nums) {
            final BigDecimal d = object(num);
            if (d != null) {
                t = t == null ? d : t.multiply(d, MC);
            }
        }

        return t;
    }

    @Nullable
    public static BigDecimal mulMapNull(Iterable<?> cols) {
        if (cols == null) return null;
        BigDecimal t = null;

        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                t = t == null ? d : t.multiply(d, MC);
            }
        }
        return t;
    }

    @Nullable
    public static <T> BigDecimal mulMapNull(Iterable<T> cols, Function<? super T, ?> mapper) {
        if (cols == null) return null;
        BigDecimal t = null;
        for (T e : cols) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                t = t == null ? d : t.multiply(d, MC);
            }
        }
        return t;
    }

    @NotNull
    public static BigDecimal div(Object a, Object b) {
        BigDecimal t = Objects.requireNonNull(object(a));
        final BigDecimal x = object(b);
        return x == null ? t : t.divide(x, MC);
    }


    @NotNull
    public static BigDecimal div(Object a, Object b, Object... nums) {
        BigDecimal t = div(a, b);
        if (nums == null) return t;
        return divMap(t, Arrays.asList(nums));
    }

    @NotNull
    public static BigDecimal divMap(Object a, Iterable<?> cols) {
        BigDecimal t = Objects.requireNonNull(object(a));
        if (cols == null) return t;

        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                t = t.divide(d, MC);
            }
        }

        return t;
    }


    @NotNull
    public static <T> BigDecimal divMap(Object a, Iterable<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = Objects.requireNonNull(object(a));
        if (cols == null) return t;

        for (T e : cols) {
            final Object m = mapper.apply(e);
            final BigDecimal d = object(m);
            if (d != null) {
                t = t.divide(d, MC);
            }
        }

        return t;
    }


    // ///////// pow & neg /////////

    @NotNull
    public static BigDecimal pow(Object num, int n) {
        final BigDecimal d = powNull(num, n);
        return Objects.requireNonNull(d);
    }

    public static BigDecimal powNull(Object num, int n) {
        final BigDecimal d = object(num);
        return d == null ? null : d.pow(n, MC);
    }

    @NotNull
    public static BigDecimal neg(Object num) {
        final BigDecimal d = negNull(num);
        return Objects.requireNonNull(d);
    }

    @Nullable
    public static BigDecimal negNull(Object num) {
        final BigDecimal d = object(num);
        return d == null ? null : d.negate(MC);
    }

    // ///////// scale /////////

    /**
     * Remove all digit after `scale` (exclude), then
     * ceil the `num`. return `0.xxx` if null, xxx is `scale`
     */
    @NotNull
    public static BigDecimal ceil(Object num, int scale) {
        return scale(num, scale, CEILING);
    }

    /**
     * Remove all digit after `scale` (exclude), then
     * floor the `num`. return `0.xxx` if null, xxx is `scale`
     */
    @NotNull
    public static BigDecimal floor(Object num, int scale) {
        return scale(num, scale, FLOOR);
    }

    // ///////// round /////////

    /**
     * Remove all digit after `scale` (exclude), then
     * round the `num`. return `0.xxx` if null, xxx is `scale`
     */
    @NotNull
    public static BigDecimal round(Object num, int scale) {
        return scale(num, scale, HALF_UP);
    }

    /**
     * Remove all digit after `scale` (exclude), then apply RoundingMode
     */
    @NotNull
    public static BigDecimal scale(Object num, int scale, RoundingMode mode) {
        final BigDecimal d = object(num);
        if (d == null) {
            return ZERO.setScale(scale, mode);
        }
        if (d.scale() == scale) {
            return d;
        }
        else {
            return d.setScale(scale + 1, FLOOR).setScale(scale, mode);
        }
    }

    /**
     * @see #unitUp(Object, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal unitUp(Object num, BigDecimal unit) {
        return unitUp(num, unit, ZERO);
    }

    /**
     * <pre>
     * The `unit` is round up, null treated as zero, and scale is based on unit.
     * Take weighing and pricing as an example, weighing accuracy 0.01, every 0.5 pricing
     * When x > 0.1, treated as 0.5, otherwise treated as 0.
     * When x > 0.6, treated as 1, otherwise treated as 0.5.
     * </pre>
     *
     * @param num  the number
     * @param unit the unit
     * @param down less than or equal to the value is rounded off
     */
    @NotNull
    public static BigDecimal unitUp(Object num, BigDecimal unit, BigDecimal down) {
        final int unitScale = unit.scale();
        BigDecimal d = object(num, ZERO);

        if (down == null) down = ZERO;
        BigDecimal[] dr = d.divideAndRemainder(unit);
        d = dr[0].multiply(unit);

        if (compareTo(dr[1], down, unitScale, FLOOR) > 0) { // round up
            d = d.add(unit);
        }

        if (d.scale() != unitScale) {
            d = d.setScale(unitScale, FLOOR);
        }
        return d;
    }

    /**
     * @see #unitDown(Object, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal unitDown(Object num, BigDecimal unit) {
        return unitDown(num, unit, unit);
    }

    /**
     * <pre>
     * The `unit` is round down, null treated as zero, and scale is based on unit.
     * Take weighing and pricing as an example, weighing accuracy 0.01, every 0.5 pricing
     * When x > 0.4, treated as 0.5, otherwise treated as 0.
     * When x > 0.9, treated as 1, otherwise treated as 0.5.
     * </pre>
     *
     * @param num  the number
     * @param unit the unit
     * @param upto greater than or equal to the value is rounded up
     */
    @NotNull
    public static BigDecimal unitDown(Object num, BigDecimal unit, BigDecimal upto) {
        final int unitScale = unit.scale();

        BigDecimal d = object(num, ZERO);

        BigDecimal[] dr = d.divideAndRemainder(unit);
        d = dr[0].multiply(unit);

        if (compareTo(dr[1], upto, unitScale, FLOOR) >= 0) { // round up
            d = d.add(unit);
        }

        if (d.scale() != unitScale) {
            d = d.setScale(unitScale, FLOOR);
        }
        return d;
    }

    /**
     * parse and compare the number, null less than any number
     */
    public static int compareTo(Object a, Object b) {
        final BigDecimal x = object(a);
        final BigDecimal y = object(b);

        if (x == null && y == null) return 0;
        if (x == null) return -1;
        if (y == null) return 1;

        return x.compareTo(y);
    }

    /**
     * parse, round and compare the number, null less than any number
     */
    public static int compareTo(Object a, Object b, int scale, RoundingMode mode) {
        BigDecimal x = object(a);
        BigDecimal y = object(b);

        if (x == null && y == null) return 0;
        if (x == null) return -1;
        if (y == null) return 1;

        if (x.scale() > scale) {
            x = scale(x, scale, mode);
        }
        if (y.scale() > scale) {
            y = scale(y, scale, mode);
        }
        return x.compareTo(y);
    }

    public static boolean equalsValue(Object a, Object b) {
        return compareTo(a, b) == 0;
    }

    /**
     * parse, round and compare the number, null == null, null != notnull
     */
    public static boolean equalsValue(Object a, Object b, int scale, RoundingMode mode) {
        return compareTo(a, b, scale, mode) == 0;
    }

    /**
     * null treated as zero, not thread safe.
     */
    public static class W {
        private BigDecimal value;
        private int scale;

        public W(@NotNull BigDecimal d) {
            this.value = d;
            this.scale = d.scale();
        }

        // result
        @NotNull
        public BigDecimal result(RoundingMode mode) {
            return value.setScale(scale, mode);
        }

        @NotNull
        public BigDecimal resultRaw() {
            return value;
        }

        @NotNull
        public BigDecimal resultCeil() {
            return BigDecimalUtil.ceil(value, scale);
        }

        @NotNull
        public BigDecimal resultRound() {
            return BigDecimalUtil.round(value, scale);
        }

        @NotNull
        public BigDecimal resultFloor() {
            return BigDecimalUtil.floor(value, scale);
        }

        @NotNull
        public BigDecimal resultUnitUp(BigDecimal unit) {
            return BigDecimalUtil.unitUp(value, unit);
        }

        @NotNull
        public BigDecimal resultUnitUp(BigDecimal unit, BigDecimal zeroing) {
            return BigDecimalUtil.unitUp(value, unit, zeroing);
        }

        @NotNull
        public BigDecimal resultUnitDown(BigDecimal unit) {
            return BigDecimalUtil.unitDown(value, unit);
        }

        @NotNull
        public BigDecimal result(int scale, RoundingMode mode) {
            return value.setScale(scale, mode);
        }

        @NotNull
        public BigDecimal resultCeil(int scale) {
            return BigDecimalUtil.ceil(value, scale);
        }

        @NotNull
        public BigDecimal resultRound(int scale) {
            return BigDecimalUtil.round(value, scale);
        }

        @NotNull
        public BigDecimal resultFloor(int scale) {
            return BigDecimalUtil.floor(value, scale);
        }

        @NotNull
        public W setScale(int scale) {
            this.scale = scale;
            return this;
        }

        @NotNull
        public W setValue(Object... value) {
            this.value = notNull(value);
            return this;
        }

        // ////
        @NotNull
        public W add(Object a) {
            value = BigDecimalUtil.add(value, a);
            return this;
        }

        @NotNull
        public W add(Object a, Object... num) {
            value = BigDecimalUtil.add(value, a, num);
            return this;
        }

        @NotNull
        public W addIf(boolean c, Object a, Object b) {
            final BigDecimal num = c ? object(a) : object(b);
            value = BigDecimalUtil.add(value, num);
            return this;
        }

        @NotNull
        public W addMap(Iterable<?> cols) {
            final BigDecimal x = BigDecimalUtil.addMapNull(cols);
            value = BigDecimalUtil.add(value, x);
            return this;
        }

        @NotNull
        public <T> W addMap(Iterable<T> cols, Function<? super T, ?> mapper) {
            final BigDecimal x = BigDecimalUtil.addMapNull(cols, mapper);
            value = BigDecimalUtil.add(value, x);
            return this;
        }

        @NotNull
        public W sub(Object a) {
            value = BigDecimalUtil.sub(value, a);
            return this;
        }

        @NotNull
        public W sub(Object a, Object... num) {
            value = BigDecimalUtil.sub(value, a, num);
            return this;
        }

        @NotNull
        public W subIf(boolean c, Object a, Object b) {
            final BigDecimal num = c ? object(a) : object(b);
            value = BigDecimalUtil.sub(value, num);
            return this;
        }

        @NotNull
        public W subMap(Iterable<?> cols) {
            value = BigDecimalUtil.subMap(value, cols);
            return this;
        }

        @NotNull
        public <T> W subMap(Iterable<T> cols, Function<? super T, ?> mapper) {
            value = BigDecimalUtil.subMap(value, cols, mapper);
            return this;
        }

        @NotNull
        public W mul(Object a) {
            value = BigDecimalUtil.mul(value, a);
            return this;
        }

        @NotNull
        public W mul(Object a, Object... num) {
            value = BigDecimalUtil.mul(value, a, num);
            return this;
        }

        @NotNull
        public W mulIf(boolean c, Object a, Object b) {
            final BigDecimal num = c ? object(a) : object(b);
            value = BigDecimalUtil.mul(value, num);
            return this;
        }

        @NotNull
        public W mulMap(Iterable<?> cols) {
            final BigDecimal x = BigDecimalUtil.mulMapNull(cols);
            value = BigDecimalUtil.mul(value, x);
            return this;
        }

        @NotNull
        public <T> W mulMap(Iterable<T> cols, Function<? super T, ?> mapper) {
            final BigDecimal x = BigDecimalUtil.mulMapNull(cols, mapper);
            value = BigDecimalUtil.mul(value, x);
            return this;
        }


        @NotNull
        public W div(Object a) {
            value = BigDecimalUtil.div(value, a);
            return this;
        }

        @NotNull
        public W div(Object a, Object... num) {
            value = BigDecimalUtil.div(value, a, num);
            return this;
        }

        @NotNull
        public W divIf(boolean c, Object a, Object b) {
            final BigDecimal num = c ? object(a) : object(b);
            value = BigDecimalUtil.div(value, num);
            return this;
        }


        @NotNull
        public W divMap(Iterable<?> cols) {
            value = BigDecimalUtil.divMap(value, cols);
            return this;
        }

        @NotNull
        public <T> W divMap(Iterable<T> cols, Function<? super T, ?> mapper) {
            value = BigDecimalUtil.divMap(value, cols, mapper);
            return this;
        }

        @NotNull
        public W neg() {
            value = value.negate(MC);
            return this;
        }

        @NotNull
        public W pow(int n) {
            value = value.pow(n, MC);
            return this;
        }

        @NotNull
        public W ceil(int scale) {
            value = BigDecimalUtil.ceil(value, scale);
            return this;
        }

        @NotNull
        public W round(int scale) {
            value = BigDecimalUtil.round(value, scale);
            return this;
        }

        @NotNull
        public W floor(int scale) {
            value = BigDecimalUtil.floor(value, scale);
            return this;
        }
    }

    @NotNull
    public static W w(Object v) {
        return new W(object(v));
    }

    @NotNull
    public static W w(BigDecimal v, int scale) {
        final W w = w(v);
        return w.setScale(scale);
    }
}
