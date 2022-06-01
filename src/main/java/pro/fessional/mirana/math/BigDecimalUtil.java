package pro.fessional.mirana.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.FLOOR;
import static java.math.RoundingMode.HALF_UP;

/**
 * null友好的BigDecimal工具类
 *
 * @author trydofor
 * @since 2015-12-11.
 */
public class BigDecimalUtil {

    private static final MathContext MC = MathContext.DECIMAL32;

    // ////// string //////

    /**
     * null时返回空
     *
     * @param num 数字
     * @return 字符串
     */
    @NotNull
    public static String string(BigDecimal num) {
        return string(num, "");
    }

    /**
     * 舍去scale+1位后，向上取值
     *
     * @param num   数字
     * @param scale 小数点几位
     * @return 字符串
     */
    @NotNull
    public static String string(BigDecimal num, int scale) {
        BigDecimal dec = ceil(num, scale);
        return dec.toPlainString();
    }

    /**
     * 字符串化
     *
     * @param num 数字
     * @param elz null时返回
     * @return 字符串
     */
    public static String string(BigDecimal num, String elz) {
        if (num == null) return elz;
        return num.toPlainString();
    }

    /**
     * 舍去末尾多余的0
     *
     * @param num   数字
     * @param strip 舍去末尾的多余的0
     * @return 字符串
     */
    @NotNull
    public static String string(BigDecimal num, boolean strip) {
        return string(num, "", strip);
    }

    /**
     * 舍去scale+1位后，向上取值，并舍去末尾多余的0
     *
     * @param num   数字
     * @param scale 保留小数点几位
     * @param strip 舍去末尾的多余的0
     * @return 字符串
     */
    @NotNull
    public static String string(BigDecimal num, int scale, boolean strip) {
        BigDecimal dec = ceil(num, scale);
        return string(dec, "", strip);
    }

    /**
     * 舍去scale+1位后，向上取值，并舍去末尾多余的0
     *
     * @param num   数字
     * @param elze  null时返回
     * @param strip 舍去末尾的多余的0
     * @return 字符串
     */
    public static String string(BigDecimal num, String elze, boolean strip) {
        if (num == null) return elze;
        return strip ? num.stripTrailingZeros().toPlainString() : num.toPlainString();
    }

    // ////// object //////

    @Contract("!null -> !null")
    public static BigDecimal object(Object num) {
        return object(num, null, false);
    }

    @Contract("_, !null -> !null")
    public static BigDecimal object(Object num, BigDecimal elze) {
        return object(num, elze, false);
    }

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
     * 取得第一个非Null数字
     *
     * @param nums 数字
     * @return 非null
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
     * true时返回A，否则B
     *
     * @param cond 条件
     * @param a    true时返回
     * @param b    false时返回
     * @return 非空值
     */
    @NotNull
    public static BigDecimal ifElse(boolean cond, Object a, Object b) {
        final BigDecimal d = cond ? object(a) : object(b);
        return Objects.requireNonNull(d);
    }

    // ////// avg //////

    /**
     * 平均数，null被忽略
     *
     * @param nums 数字
     * @return 平均数
     */
    @NotNull
    public static BigDecimal avg(Object... nums) {
        BigDecimal total = avgNull(nums);
        return Objects.requireNonNull(total);
    }

    @NotNull
    public static BigDecimal avgMap(Collection<?> cols) {
        BigDecimal total = avgMapNull(cols);
        return Objects.requireNonNull(total);
    }

    @NotNull
    public static <T> BigDecimal avgMap(Collection<T> cols, Function<? super T, ?> mapper) {
        BigDecimal total = avgMapNull(cols, mapper);
        return Objects.requireNonNull(total);
    }

    public static BigDecimal avgNull(Object... nums) {
        if (nums == null) return null;
        return avgMapNull(Arrays.asList(nums));
    }

    public static BigDecimal avgMapNull(Collection<?> cols) {
        BigDecimal total = null;
        int count = 0;
        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                total = total == null ? d : total.add(d, MC);
                count++;
            }
        }
        return total == null ? null : total.divide(new BigDecimal(count), MC);
    }

    public static <T> BigDecimal avgMapNull(Collection<T> cols, Function<? super T, ?> mapper) {
        BigDecimal total = null;
        int count = 0;
        for (T e : cols) {
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
    public static BigDecimal maxMap(Collection<?> cols) {
        final BigDecimal max = maxMapNull(cols);
        return Objects.requireNonNull(max);
    }

    @NotNull
    public static <T> BigDecimal maxMap(Collection<T> cols, Function<? super T, ?> mapper) {
        final BigDecimal max = maxMapNull(cols, mapper);
        return Objects.requireNonNull(max);
    }

    public static BigDecimal maxNull(Object... nums) {
        if (nums == null) return null;
        return maxMapNull(Arrays.asList(nums));
    }

    public static BigDecimal maxMapNull(Collection<?> cols) {
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

    public static <T> BigDecimal maxMapNull(Collection<T> cols, Function<? super T, ?> mapper) {
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
    public static BigDecimal minMap(Collection<?> cols) {
        final BigDecimal min = minMapNull(cols);
        return Objects.requireNonNull(min);
    }

    @NotNull
    public static <T> BigDecimal minMap(Collection<T> cols, Function<? super T, ?> mapper) {
        final BigDecimal min = minMapNull(cols, mapper);
        return Objects.requireNonNull(min);
    }

    public static BigDecimal minNull(Object... nums) {
        if (nums == null) return null;
        return minMapNull(Arrays.asList(nums));
    }

    public static BigDecimal minMapNull(Collection<?> cols) {
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

    public static <T> BigDecimal minMapNull(Collection<T> cols, Function<? super T, ?> mapper) {
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
     * 结果为null时，以ZERO返回
     */
    @NotNull
    public static BigDecimal sum(Object a, Object b, Object... nums) {
        return addElse(ZERO, a, b, nums);
    }

    @NotNull
    public static BigDecimal sumMap(Collection<?> cols) {
        return addMapElse(ZERO, cols);
    }

    @NotNull
    public static <T> BigDecimal sumMap(Collection<T> cols, Function<? super T, ?> mapper) {
        return addMapElse(ZERO, cols, mapper);
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
    public static BigDecimal addMap(Collection<?> cols) {
        BigDecimal t = addMapNull(cols);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static <T> BigDecimal addMap(Collection<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = addMapNull(cols, mapper);
        return Objects.requireNonNull(t);
    }

    @Contract("!null,_,_ -> !null")
    public static BigDecimal addElse(BigDecimal e, Object a, Object b) {
        BigDecimal t = addNull(a, b);
        return t == null ? e : t;
    }

    @Contract("!null,_,_,_ -> !null")
    public static BigDecimal addElse(BigDecimal e, Object a, Object b, Object... nums) {
        BigDecimal t = addNull(a, b, nums);
        return t == null ? e : t;
    }

    @Contract("!null,_ -> !null")
    public static BigDecimal addMapElse(BigDecimal e, Collection<?> cols) {
        BigDecimal t = addMapNull(cols);
        return t == null ? e : t;
    }

    @Contract("!null,_,_ -> !null")
    public static <T> BigDecimal addMapElse(BigDecimal e, Collection<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = addMapNull(cols, mapper);
        return t == null ? e : t;
    }

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

    public static BigDecimal addMapNull(Collection<?> cols) {
        if (cols == null) return null;
        BigDecimal t = null;
        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                t = t == null ? d : t.add(d, MC);
            }
        }
        return t;
    }

    public static <T> BigDecimal addMapNull(Collection<T> cols, Function<? super T, ?> mapper) {
        if (cols == null) return null;
        BigDecimal t = null;
        for (T e : cols) {
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
    public static BigDecimal subMap(Object a, Collection<?> cols) {
        BigDecimal t = Objects.requireNonNull(object(a));
        if (cols == null) return t;

        for (Object e : cols) {
            final BigDecimal d = object(e);
            if (d != null) {
                t = t.subtract(d, MC);
            }
        }

        return t;
    }

    @NotNull
    public static <T> BigDecimal subMap(Object a, Collection<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = Objects.requireNonNull(object(a));
        if (cols == null) return t;

        for (T e : cols) {
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
     * 结果为null时，以ZERO返回
     */
    @NotNull
    public static BigDecimal prd(Object a, Object b, Object... nums) {
        return mulElse(ZERO, a, b, nums);
    }

    @NotNull
    public static BigDecimal prdMap(Collection<?> cols) {
        return mulMapElse(ZERO, cols);
    }

    @NotNull
    public static <T> BigDecimal prdMap(Collection<T> cols, Function<? super T, ?> mapper) {
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
    public static BigDecimal mulMap(Collection<?> cols) {
        BigDecimal t = mulMapNull(cols);
        return Objects.requireNonNull(t);
    }

    @NotNull
    public static <T> BigDecimal mulMap(Collection<T> cols, Function<? super T, ?> mapper) {
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
    public static <T> BigDecimal mulMapElse(BigDecimal e, Collection<T> cols) {
        BigDecimal t = mulMapNull(cols);
        return t == null ? e : t;
    }

    @Contract("!null,_,_ -> !null")
    public static <T> BigDecimal mulMapElse(BigDecimal e, Collection<T> cols, Function<? super T, ?> mapper) {
        BigDecimal t = mulMapNull(cols, mapper);
        return t == null ? e : t;
    }

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

    public static BigDecimal mulMapNull(Collection<?> cols) {
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

    public static <T> BigDecimal mulMapNull(Collection<T> cols, Function<? super T, ?> mapper) {
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
    public static BigDecimal divMap(Object a, Collection<?> cols) {
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
    public static <T> BigDecimal divMap(Object a, Collection<T> cols, Function<? super T, ?> mapper) {
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

    public static BigDecimal negNull(Object num) {
        final BigDecimal d = object(num);
        return d == null ? null : d.negate(MC);
    }

    // ///////// scale /////////

    /**
     * 向上取整
     *
     * @param num   null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal ceil(Object num, int scale) {
        return scale(num, scale, CEILING);
    }

    /**
     * 向下取整
     *
     * @param num   null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal floor(Object num, int scale) {
        return scale(num, scale, FLOOR);
    }

    // ///////// round /////////

    /**
     * 四舍五入
     *
     * @param num   null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal round(Object num, int scale) {
        return scale(num, scale, HALF_UP);
    }

    /**
     * 砍掉`scale+1`位之后的所有数字，对`scale+1`位进行进位操作。
     *
     * @param num   null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 新值
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
     * @param num  数值
     * @param unit 单位
     * @return 处理结果
     * @see #unitUp(Object, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal unitUp(Object num, BigDecimal unit) {
        return unitUp(num, unit, ZERO);
    }

    /**
     * <pre>
     * 以单位向上取整, null 当零处理, scale以unit为准。
     * 以称重计价为例，称的精度0.01，每0.5计价
     * 当x &gt; 0.1时，按0.5处理，否则按0处理
     * 当x &gt; 0.6时，按1处理，否则按0.5处理
     * </pre>
     *
     * @param num  数值
     * @param unit 单位
     * @param down 单位后余数，小于等于该值则舍去
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitUp(Object num, BigDecimal unit, BigDecimal down) {
        final int unitScale = unit.scale();
        BigDecimal d = object(num, ZERO);

        if (down == null) down = ZERO;
        BigDecimal[] dr = d.divideAndRemainder(unit);
        d = dr[0].multiply(unit);

        if (compareTo(dr[1], down, unitScale, FLOOR) > 0) { //向上进位
            d = d.add(unit);
        }

        if (d.scale() != unitScale) {
            d = d.setScale(unitScale, FLOOR);
        }
        return d;
    }

    /**
     * @param num  数值
     * @param unit 单位
     * @return 处理结果
     * @see #unitDown(Object, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal unitDown(Object num, BigDecimal unit) {
        return unitDown(num, unit, unit);
    }

    /**
     * <pre>
     * 以单位向下取整, null 当零处理, scale以unit为准。
     * 以称重计价为例，称的精度0.01，每0.5计价
     * 当x &gt;= 0.4时，按0.5处理，否则按0处理
     * 当x &gt;= 0.9时，按1处理，否则按0.5处理
     * </pre>
     *
     * @param num  数值
     * @param unit 单位
     * @param upto 单位后余数，大于等于该值则进位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitDown(Object num, BigDecimal unit, BigDecimal upto) {
        final int unitScale = unit.scale();

        BigDecimal d = object(num, ZERO);

        BigDecimal[] dr = d.divideAndRemainder(unit);
        d = dr[0].multiply(unit);

        if (compareTo(dr[1], upto, unitScale, FLOOR) >= 0) { //向上进位
            d = d.add(unit);
        }

        if (d.scale() != unitScale) {
            d = d.setScale(unitScale, FLOOR);
        }
        return d;
    }

    /**
     * null小于一切
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
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
     * null小于一切
     *
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 结果
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
     * null == null, null != notnull
     *
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 结果
     * @see BigDecimal#compareTo(BigDecimal)
     */
    public static boolean equalsValue(Object a, Object b, int scale, RoundingMode mode) {
        return compareTo(a, b, scale, mode) == 0;
    }

    /**
     * null当零处理，非线程安全
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
        public W addMap(Collection<?> cols) {
            final BigDecimal x = BigDecimalUtil.addMapNull(cols);
            value = BigDecimalUtil.add(value, x);
            return this;
        }

        @NotNull
        public <T> W addMap(Collection<T> cols, Function<? super T, ?> mapper) {
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
        public W subMap(Collection<?> cols) {
            value = BigDecimalUtil.subMap(value, cols);
            return this;
        }

        @NotNull
        public <T> W subMap(Collection<T> cols, Function<? super T, ?> mapper) {
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
        public W mulMap(Collection<?> cols) {
            final BigDecimal x = BigDecimalUtil.mulMapNull(cols);
            value = BigDecimalUtil.mul(value, x);
            return this;
        }

        @NotNull
        public <T> W mulMap(Collection<T> cols, Function<? super T, ?> mapper) {
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
        public W divMap(Collection<?> cols) {
            value = BigDecimalUtil.divMap(value, cols);
            return this;
        }

        @NotNull
        public <T> W divMap(Collection<T> cols, Function<? super T, ?> mapper) {
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
