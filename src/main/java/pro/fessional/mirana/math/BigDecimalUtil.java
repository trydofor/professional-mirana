package pro.fessional.mirana.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * null友好的BigDecimal工具类
 *
 * @author trydofor
 * @since 2015-12-11.
 */
public class BigDecimalUtil {

    private static final MathContext MC = MathContext.DECIMAL32;

    /**
     * null时返回空
     *
     * @param v 数字
     * @return 字符串
     */
    @NotNull
    public static String string(BigDecimal v) {
        return string(v, "");
    }

    /**
     * 舍去scale+1位后，向上取值
     *
     * @param v     数字
     * @param scale 小数点几位
     * @return 字符串
     */
    @NotNull
    public static String string(BigDecimal v, int scale) {
        BigDecimal dec = ceil(v, scale);
        return dec.toPlainString();
    }

    /**
     * 字符串化
     *
     * @param v   数字
     * @param elz null时返回
     * @return 字符串
     */
    public static String string(BigDecimal v, String elz) {
        if (v == null) return elz;
        return v.toPlainString();
    }

    /**
     * 从字符串构造
     *
     * @param str 字符串
     * @return 数字，失败时null
     */
    public static BigDecimal object(String str) {
        return object(str, null);
    }

    /**
     * 从字符串构造
     *
     * @param str 字符串
     * @param elz null时返回
     * @return 数字
     */
    public static BigDecimal object(String str, BigDecimal elz) {
        if (str == null || str.length() == 0)
            return elz;
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return elz;
        }
    }

    /**
     * 平均数，null当做零处理
     *
     * @param v 数字
     * @return 平均数
     */
    public static BigDecimal avg(BigDecimal... v) {
        if (v == null || v.length == 0)
            return BigDecimal.ZERO;
        BigDecimal total = sum(v);
        return total.divide(new BigDecimal(v.length), MC);
    }

    /**
     * 平均数，null当做零处理或忽略
     *
     * @param skipNull 是否忽略null
     * @param v        数字
     * @return 平均数
     */
    public static BigDecimal avg(boolean skipNull, BigDecimal... v) {
        if (!skipNull) {
            return avg(v);
        }

        BigDecimal total = BigDecimal.ZERO;
        int count = 0;
        for (BigDecimal x : v) {
            if (x != null) {
                total = total.add(x);
                count++;
            }
        }
        return total.divide(new BigDecimal(count), MC);
    }

    /**
     * 求和
     *
     * @param v 数字
     * @return 和
     */
    public static BigDecimal sum(BigDecimal... v) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal x : v) {
            total = add(total, x);
        }
        return total;
    }

    /**
     * 求最大值
     *
     * @param a 数字
     * @param b 数字
     * @return 最大值
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.max(b);
    }

    /**
     * 最小值
     *
     * @param a 数字
     * @param b 数字
     * @return 最小值
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.min(b);
    }

    /**
     * 加法，null当零处理
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return BigDecimal.ZERO;
        if (a == null) return b;
        if (b == null) return a;
        return a.add(b);
    }

    /**
     * 减法，null当零处理
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return BigDecimal.ZERO;
        if (b == null) return a;
        if (a == null) return b.negate();
        return a.subtract(b);
    }

    /**
     * 乘法，null当一处理
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return BigDecimal.ZERO;
        if (b == null) return a;
        if (a == null) return b;
        return a.multiply(b);
    }

    /**
     * 除法，null当一处理
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        if (b == null) return a;
        if (a == null) return BigDecimal.ONE.divide(b, MC);
        return a.divide(b, MC);
    }

    /**
     * 乘方，null当零处理
     *
     * @param a 数字
     * @param n 数字
     * @return 结果
     */
    public static BigDecimal pow(BigDecimal a, int n) {
        if (a == null) return BigDecimal.ZERO;
        return a.pow(n);
    }

    /**
     * 负数，null当零处理
     *
     * @param a 数字
     * @return 结果
     */
    public static BigDecimal neg(BigDecimal a) {
        if (a == null) return BigDecimal.ZERO;
        return a.negate();
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
     *
     * @param value 数值
     * @param unit  单位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitUp(BigDecimal value, BigDecimal unit) {
        return unitUp(value, unit, BigDecimal.ZERO);
    }

    /**
     * 以单位向上取整, null 当零处理
     *
     * @param value   数值
     * @param unit    单位
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
     *
     * @param value 数值
     * @param unit  单位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitDown(BigDecimal value, BigDecimal unit) {
        if (value == null) return BigDecimal.ZERO;
        BigDecimal[] dr = value.divideAndRemainder(unit);
        return dr[0].multiply(unit);
    }

    /**
     * null小于一切，上取整
     *
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位
     * @return 结果
     */
    public static int compareCeil(BigDecimal a, BigDecimal b, int scale) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return ceil(a, scale).compareTo(ceil(b, scale));
    }

    /**
     * null小于一切
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static int compareTo(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

    /**
     * null小于一切
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static int compareTo(BigDecimal a, double b) {
        if (a == null) return -1;
        double d = a.doubleValue();
        return Double.compare(d, b);
    }

    /**
     * null小于一切，取2者最大小数点比较
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     */
    public static boolean equalsValue(BigDecimal a, BigDecimal b) {
        int ac = 0, bc = 0;
        if (a != null) ac = a.scale();
        if (b != null) bc = b.scale();
        return equalsFloor(a, b, Math.max(ac, bc));
    }

    /**
     * 下取整比较，小于一切
     *
     * @param val1  数字
     * @param val2  数字
     * @param scale 小数点
     * @return 结果
     */
    public static boolean equalsFloor(BigDecimal val1, BigDecimal val2, int scale) {
        if (val1 == null && val2 == null) return true;
        if (val1 == null || val2 == null) return false;
        boolean eq = val1.compareTo(val2) == 0;
        if (eq) return true;
        BigDecimal fl1 = floor(val1, scale);
        BigDecimal fl2 = floor(val2, scale);
        return fl1.compareTo(fl2) == 0;
    }

    /**
     * null在操作符右侧时，计算时忽略
     * 在左侧时，加减当零，乘除当一
     */
    public static class W {
        private BigDecimal value;
        private final int scale;

        public W(BigDecimal... vs) {
            BigDecimal d = null;
            for (BigDecimal v : vs) {
                if (v != null) {
                    d = v;
                    break;
                }
            }
            if (d == null) {
                this.value = BigDecimal.ZERO;
                this.scale = 0;
            } else {
                this.value = d;
                this.scale = d.scale();
            }
        }

        public W(int scale, BigDecimal... vs) {
            BigDecimal d = null;
            for (BigDecimal v : vs) {
                if (v != null) {
                    d = v;
                    break;
                }
            }
            if (d == null) {
                this.value = BigDecimal.ZERO;
            } else {
                this.value = d;
            }
            this.scale = scale;
        }

        public W(BigDecimal value, int scale) {
            this.value = value;
            this.scale = scale;
        }

        @NotNull
        public BigDecimal resultCeil() {
            return ceil(value, scale);
        }

        @NotNull
        public BigDecimal resultRound() {
            return round(value, scale);
        }

        @NotNull
        public BigDecimal resultFloor() {
            return floor(value, scale);
        }

        @NotNull
        public W add(BigDecimal v) {
            value = BigDecimalUtil.add(value, v);
            return this;
        }

        @NotNull
        public W add(W v) {
            value = BigDecimalUtil.add(value, v.value);
            return this;
        }

        @NotNull
        public W sub(BigDecimal v) {
            value = BigDecimalUtil.sub(value, v);
            return this;
        }

        @NotNull
        public W sub(W v) {
            value = BigDecimalUtil.sub(value, v.value);
            return this;
        }

        @NotNull
        public W mul(BigDecimal v) {
            value = BigDecimalUtil.mul(value, v);
            return this;
        }

        @NotNull
        public W mul(W v) {
            value = BigDecimalUtil.mul(value, v.value);
            return this;
        }

        @NotNull
        public W div(BigDecimal v) {
            value = BigDecimalUtil.div(value, v);
            return this;
        }

        @NotNull
        public W div(W v) {
            value = BigDecimalUtil.div(value, v.value);
            return this;
        }

        @NotNull
        public W pow(int n) {
            value = BigDecimalUtil.pow(value, n);
            return this;
        }

        @NotNull
        public W neg() {
            value = BigDecimalUtil.neg(value);
            return this;
        }
    }

    @NotNull
    public static W w(String value) {
        return new W(object(value));
    }

    @NotNull
    public static W w(String value, int scale) {
        return new W(object(value), scale);
    }

    @NotNull
    public static W w(BigDecimal value) {
        return new W(value);
    }

    @NotNull
    public static W w(BigDecimal value, int scale) {
        return new W(value, scale);
    }

    @NotNull
    public static W w(BigDecimal... value) {
        return new W(value);
    }

    @NotNull
    public static W w(int scale, BigDecimal... value) {
        return new W(scale, value);
    }
}
