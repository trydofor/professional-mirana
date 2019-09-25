package pro.fessional.mirana.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author trydofor
 * @since 2015-12-11.
 */
public class BigDecimalUtil {

    private static final MathContext MC = MathContext.DECIMAL32;

    public static String string(BigDecimal v) {
        return string(v, "");
    }

    public static String string(BigDecimal v, int scale) {
        BigDecimal dec = ceil(v, scale);
        return dec.toString();
    }

    public static String string(BigDecimal v, String elz) {
        if (v == null) return elz;
        return v.toString();
    }

    public static BigDecimal object(String str) {
        return object(str, null);
    }

    public static BigDecimal object(String str, BigDecimal elz) {
        if (str == null || str.length() == 0)
            return elz;
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return elz;
        }
    }

    public static BigDecimal avg(BigDecimal... v) {
        if (v == null || v.length == 0)
            return BigDecimal.ZERO;
        BigDecimal total = sum(v);
        return total.divide(new BigDecimal(v.length), MC);
    }

    public static BigDecimal sum(BigDecimal... v) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal x : v) {
            total = add(total, x);
        }
        return total;
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.max(b);
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.min(b);
    }

    /*
        null = Zero
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return BigDecimal.ZERO;
        if (a == null) return b;
        if (b == null) return a;
        return a.add(b);
    }

    /*
        null = Zero
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return BigDecimal.ZERO;
        if (b == null) return a;
        if (a == null) return b.negate();
        return a.subtract(b);
    }

    /*
        null = One
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return BigDecimal.ZERO;
        if (b == null) return a;
        if (a == null) return b;
        return a.multiply(b);
    }

    /*
        null = One
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        if (b == null) return a;
        if (a == null) return BigDecimal.ONE.divide(b, MC);
        return a.divide(b, MC);
    }

    /**
     * 向上取整
     *
     * @param value null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal ceil(BigDecimal value, int scale) {
        return scale(value, scale, BigDecimal.ROUND_CEILING);
    }

    /**
     * 向下取整
     *
     * @param value null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal floor(BigDecimal value, int scale) {
        return scale(value, scale, BigDecimal.ROUND_FLOOR);
    }

    /**
     * 四舍五入
     *
     * @param value null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal round(BigDecimal value, int scale) {
        return scale(value, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 砍掉`scale+1`位之后的所有数字，对`scale+1`位进行进位操作。
     *
     * @param value null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @return 新值
     */
    @NotNull
    public static BigDecimal scale(BigDecimal value, int scale, int type) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(scale, type);
        }
        if (value.scale() == scale) {
            return value;
        } else {
            return value.setScale(scale + 1, BigDecimal.ROUND_FLOOR).setScale(scale, type);
        }
    }

    /**
     * 以单位向上取整, null 当零处理
     * @param value 数值
     * @param unit 单位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitUp(BigDecimal value, BigDecimal unit) {
        return unitUp(value, unit, BigDecimal.ZERO);
    }
    /**
     * 以单位向上取整, null 当零处理
     * @param value 数值
     * @param unit 单位
     * @param zeroing 单位中小于等于zero的值当零处理
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitUp(BigDecimal value, BigDecimal unit, BigDecimal zeroing) {
        if (value == null) return BigDecimal.ZERO;
        if (zeroing == null) zeroing = BigDecimal.ZERO;

        BigDecimal[] dr = value.divideAndRemainder(unit);
        value = dr[0].multiply(unit);
        if (dr[1].compareTo(zeroing) > 0) { //向上进位
            value = value.add(unit);
        }
        return value;
    }

    /**
     * 以单位向上取整, null 当零处理
     * @param value 数值
     * @param unit 单位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitDown(BigDecimal value, BigDecimal unit) {
        if (value == null) return BigDecimal.ZERO;
        BigDecimal[] dr = value.divideAndRemainder(unit);
        return dr[0].multiply(unit);
    }

    /**
     * null = zero
     */
    public static int compareCeil(BigDecimal a, BigDecimal b, int scale) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return ceil(a, scale).compareTo(ceil(b, scale));
    }

    public static int compareTo(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

    public static int compareTo(BigDecimal a, double b) {
        if (a == null) return -1;
        double d = a.doubleValue();
        if (d < b) return -1;
        if (d > b) return 1;
        return 0;
    }


    public static boolean equalsValue(BigDecimal val1, BigDecimal val2) {
        return equalsFloor(val1, val2, -1);
    }

    public static boolean equalsFloor(BigDecimal val1, BigDecimal val2, int scale) {
        if (val1 == null && val2 == null) return true;
        if (val1 == null || val2 == null) return false;
        boolean eq = val1.compareTo(val2) == 0;
        if (eq) return true;
        if (scale < 0) return eq;
        BigDecimal fl1 = floor(val1, scale);
        BigDecimal fl2 = floor(val2, scale);
        return fl1.compareTo(fl2) == 0;
    }
}
