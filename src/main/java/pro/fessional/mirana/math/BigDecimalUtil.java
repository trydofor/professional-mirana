package pro.fessional.mirana.math;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

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

    // ////// object //////

    public static BigDecimal object(Number num) {
        return object(num, null);
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

    public static BigDecimal object(Number num, BigDecimal elz) {
        BigDecimal r;
        if (num == null) {
            r = elz;
        } else if (num instanceof Integer) {
            r = new BigDecimal(num.intValue());
        } else if (num instanceof Long) {
            r = new BigDecimal(num.longValue());
        } else if (num instanceof Double) {
            r = BigDecimal.valueOf(num.doubleValue());
        } else if (num instanceof Float) {
            r = BigDecimal.valueOf(num.floatValue());
        } else if (num instanceof BigInteger) {
            r = new BigDecimal((BigInteger) num);
        } else if (num instanceof BigDecimal) {
            r = (BigDecimal) num;
        } else {
            r = new BigDecimal(num.toString());
        }

        return r;
    }

    /**
     * 从字符串构造
     *
     * @param vs  字符串
     * @param elz null时返回
     * @return 数字
     */
    public static BigDecimal[] objects(BigDecimal elz, Number... vs) {
        if (vs == null || vs.length == 0) return Null.BigDecimals;
        BigDecimal[] arr = new BigDecimal[vs.length];
        for (int i = 0; i < vs.length; i++) {
            arr[i] = object(vs[i], elz);
        }
        return arr;
    }

    /**
     * 从字符串构造
     *
     * @param vs  字符串
     * @param elz null时返回
     * @return 数字
     */
    public static BigDecimal[] objects(BigDecimal elz, String... vs) {
        if (vs == null || vs.length == 0) return Null.BigDecimals;
        BigDecimal[] arr = new BigDecimal[vs.length];
        for (int i = 0; i < vs.length; i++) {
            arr[i] = object(vs[i], elz);
        }
        return arr;
    }

    // ////// avg //////

    /**
     * @param vs var args
     * @return 平均数
     * @see #avg(BigDecimal...)
     */
    @NotNull
    public static BigDecimal avg(String... vs) {
        return avg(objects(null, vs));
    }

    /**
     * @param vs var args
     * @return 平均数
     * @see #avg(BigDecimal...)
     */
    @NotNull
    public static BigDecimal avg(Number... vs) {
        return avg(objects(null, vs));
    }

    /**
     * 平均数，null当做零处理
     *
     * @param vs 数字
     * @return 平均数
     */
    @NotNull
    public static BigDecimal avg(BigDecimal... vs) {
        if (vs == null || vs.length == 0) return ZERO;
        BigDecimal total = sum(vs);
        return total.divide(new BigDecimal(vs.length), MC);
    }

    /**
     * @param skipNull 是否忽略null
     * @param vs       数字
     * @return 平均数
     * @see #avg(boolean, BigDecimal...)
     */
    @NotNull
    public static BigDecimal avg(boolean skipNull, String... vs) {
        return avg(skipNull, objects(null, vs));
    }

    /**
     * @param skipNull 是否忽略null
     * @param vs       数字
     * @return 平均数
     * @see #avg(boolean, BigDecimal...)
     */
    @NotNull
    public static BigDecimal avg(boolean skipNull, Number... vs) {
        return avg(skipNull, objects(null, vs));
    }

    /**
     * 平均数，null当做零处理或忽略
     *
     * @param skipNull 是否忽略null
     * @param vs       数字
     * @return 平均数
     */
    @NotNull
    public static BigDecimal avg(boolean skipNull, BigDecimal... vs) {
        if (!skipNull) {
            return avg(vs);
        }

        BigDecimal total = ZERO;
        int count = 0;
        for (BigDecimal v : vs) {
            if (v != null) {
                total = total.add(v);
                count++;
            }
        }
        return total.divide(new BigDecimal(count), MC);
    }

    // ////// sum //////

    /**
     * @param vs 数字
     * @return 求和
     * @see #sum(BigDecimal...)
     */
    @NotNull
    public static BigDecimal sum(String... vs) {
        return sum(objects(null, vs));
    }

    /**
     * @param vs 数字
     * @return 求和
     * @see #sum(BigDecimal...)
     */
    @NotNull
    public static BigDecimal sum(Number... vs) {
        return sum(objects(null, vs));
    }

    /**
     * 求和
     *
     * @param vs 数字
     * @return 和
     */
    @NotNull
    public static BigDecimal sum(BigDecimal... vs) {
        BigDecimal total = ZERO;
        for (BigDecimal v : vs) {
            if (v != null) {
                total = total.add(v);
            }
        }
        return total;
    }

    // ////// prd //////

    /**
     * 联乘，忽略null或当零处理
     *
     * @param skipNull 忽略null或当零处理
     * @param vs       数字
     * @return 积
     * @see #prd(boolean, BigDecimal...)
     */
    @NotNull
    public static BigDecimal prd(boolean skipNull, String... vs) {
        return prd(skipNull, objects(null, vs));
    }

    /**
     * 联乘，忽略null或当零处理
     *
     * @param skipNull 忽略null或当零处理
     * @param vs       数字
     * @return 积
     * @see #prd(boolean, BigDecimal...)
     */
    @NotNull
    public static BigDecimal prd(boolean skipNull, Number... vs) {
        return prd(skipNull, objects(null, vs));
    }

    /**
     * 联乘，忽略null或当零处理
     *
     * @param skipNull 忽略null或当零处理
     * @param vs       数字
     * @return 积
     */
    @NotNull
    public static BigDecimal prd(boolean skipNull, BigDecimal... vs) {
        BigDecimal total = ZERO;
        for (BigDecimal v : vs) {
            if (v == null) {
                if (!skipNull) return ZERO;
            } else {
                total = total.multiply(v);
            }
        }
        return total;
    }

    // ////// notnull //////

    @NotNull
    public static BigDecimal notNull(@NotNull BigDecimal elz, String... vs) {
        if (vs != null) {
            for (String s : vs) {
                BigDecimal v = object(s);
                if (v != null) return v;
            }
        }
        return elz;
    }

    @NotNull
    public static BigDecimal notNull(@NotNull BigDecimal elz, Number... vs) {
        if (vs != null) {
            for (Number v : vs) {
                if (v != null) return object(v);
            }
        }
        return elz;
    }

    @NotNull
    public static BigDecimal notNull(@NotNull BigDecimal elz, BigDecimal... vs) {
        if (vs != null) {
            for (BigDecimal v : vs) {
                if (v != null) return v;
            }
        }
        return elz;
    }

    // ////// max //////

    /**
     * 求最大值，null忽略
     *
     * @param a 数字
     * @param b 数字
     * @return 最大值
     * @see #max(BigDecimal, BigDecimal)
     */
    public static BigDecimal max(BigDecimal a, String b) {
        return max(a, object(b));
    }

    /**
     * 求最大值，null忽略
     *
     * @param a 数字
     * @param b 数字
     * @return 最大值
     * @see #max(BigDecimal, BigDecimal)
     */
    public static BigDecimal max(BigDecimal a, Number b) {
        return max(a, object(b));
    }

    /**
     * 求最大值，null忽略
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

    // ////// min //////

    /**
     * 最小值，null忽略
     *
     * @param a 数字
     * @param b 数字
     * @return 最小值
     * @see #min(BigDecimal, BigDecimal)
     */
    public static BigDecimal min(BigDecimal a, String b) {
        return max(a, object(b));
    }

    /**
     * 最小值，null忽略
     *
     * @param a 数字
     * @param b 数字
     * @return 最小值
     * @see #min(BigDecimal, BigDecimal)
     */
    public static BigDecimal min(BigDecimal a, Number b) {
        return max(a, object(b));
    }

    /**
     * 最小值，null忽略
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

    // ///////// add /////////

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, int b) {
        return add(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, long b) {
        return add(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, double b) {
        return add(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, Number b) {
        return add(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, String b) {
        return add(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return add(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, String b, BigDecimal c) {
        return add(a, object(b), c);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #add(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, Number b, BigDecimal c) {
        return add(a, object(b), c);
    }

    /**
     * <pre>
     * 加法，R=A+(B==null?C:B)，null = Zero
     * ① null + null + null = Zero
     * ② null + null + C = C
     * ③ null + B + null = B
     * ④ null + B + C = B
     * ⑤ A + null + null = A
     * ⑥ A + null + C = A + C
     * ⑦ A + B + null = A + B
     * ⑧ A + B + C = A + B
     * </pre>
     *
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     */
    @NotNull
    public static BigDecimal add(BigDecimal a, BigDecimal b, BigDecimal c) {
        if (a == null) {
            if (b == null) {
                if (c == null) {
                    return ZERO;
                } else {
                    return c;
                }
            } else {
                return b;
            }
        } else {
            if (b == null) {
                if (c == null) {
                    return a;
                } else {
                    return a.add(c);
                }
            } else {
                return a.add(b);
            }
        }
    }

    // ///////// sub /////////

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, int b) {
        return sub(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, long b) {
        return sub(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, double b) {
        return sub(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, String b) {
        return sub(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, Number b) {
        return sub(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        return sub(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, String b, BigDecimal c) {
        return sub(a, object(b), c);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #sub(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, Number b, BigDecimal c) {
        return sub(a, object(b), c);
    }

    /**
     * <pre>
     * 减法，R=A-(B==null?C:B)，null = Zero
     * ① null - null - null = Zero
     * ② null - null - C = -C
     * ③ null - B - null = -B
     * ④ null - B - C = -B
     * ⑤ A - null - null = A
     * ⑥ A - null - C = A - C
     * ⑦ A - B - null = A - B
     * ⑧ A - B - C = A - B
     * </pre>
     *
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     */
    @NotNull
    public static BigDecimal sub(BigDecimal a, BigDecimal b, BigDecimal c) {
        if (a == null) {
            if (b == null) {
                if (c == null) {
                    return ZERO;
                } else {
                    return c.negate();
                }
            } else {
                return b.negate();
            }
        } else {
            if (b == null) {
                if (c == null) {
                    return a;
                } else {
                    return a.subtract(c);
                }
            } else {
                return a.subtract(b);
            }
        }
    }

    // ///////// mul /////////

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, int b) {
        return mul(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, long b) {
        return mul(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, double b) {
        return mul(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, String b) {
        return mul(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, Number b) {
        return mul(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, BigDecimal b) {
        return mul(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, String b, BigDecimal c) {
        return mul(a, object(b), c);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #mul(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, Number b, BigDecimal c) {
        return mul(a, object(b), c);
    }

    /**
     * <pre>
     * 乘法，R=Ax(B==null?C:B)，null = Zero
     * ① null x null x null = Zero
     * ② null x null x C = Zero
     * ③ null x B x null = Zero
     * ④ null x B x C = Zero
     * ⑤ A x null x null = Zero
     * ⑥ A x null x C = A x C
     * ⑦ A x B x null = A x B
     * ⑧ A x B x C = A x B
     * </pre>
     *
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     */
    @NotNull
    public static BigDecimal mul(BigDecimal a, BigDecimal b, BigDecimal c) {
        if (a == null) {
            return ZERO;
        } else {
            if (b == null) {
                if (c == null) {
                    return ZERO;
                } else {
                    return a.multiply(c);
                }
            } else {
                return a.multiply(b);
            }
        }
    }

    // ///////// div /////////

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, int b) {
        return div(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, long b) {
        return div(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, double b) {
        return div(a, new BigDecimal(b), null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, String b) {
        return div(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, Number b) {
        return div(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        return div(a, b, null);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, String b, BigDecimal c) {
        return div(a, object(b), c);
    }

    /**
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     * @see #div(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, Number b, BigDecimal c) {
        return div(a, object(b), c);
    }

    /**
     * <pre>
     * 除法，R=A/(B==null?C:B)，null = Zero, Error
     * ① null / null / null = ERROR
     * ② null / null / C = Zero
     * ③ null / B / null = Zero
     * ④ null / B / C = Zero
     * ⑤ A / null / null = ERROR
     * ⑥ A / null / C = A / C
     * ⑦ A / B / null = A / B
     * ⑧ A / B / C = A / B
     * </pre>
     *
     * @param a 数字
     * @param b 数字
     * @param c 数字
     * @return 结果
     */
    @NotNull
    public static BigDecimal div(BigDecimal a, BigDecimal b, BigDecimal c) {
        if (b == null && c == null) throw new ArithmeticException("Division by null");

        if (a == null) {
            return ZERO;
        } else {
            if (b == null) {
                return a.divide(c, MC);
            } else {
                return a.divide(b, MC);
            }
        }
    }

    // ///////// pow /////////

    @NotNull
    public static BigDecimal pow(String a, int n) {
        if (a == null) return ZERO;
        return pow(object(a), n);
    }

    @NotNull
    public static BigDecimal pow(Number a, int n) {
        if (a == null) return ZERO;
        return pow(object(a), n);
    }

    /**
     * 乘方，null当零处理
     *
     * @param a 数字
     * @param n 数字
     * @return 结果
     */
    @NotNull
    public static BigDecimal pow(BigDecimal a, int n) {
        if (a == null) return ZERO;
        return a.pow(n);
    }

    // ///////// neg /////////

    @NotNull
    public static BigDecimal neg(String a) {
        if (a == null) return ZERO;
        return neg(object(a));
    }

    @NotNull
    public static BigDecimal neg(Number a) {
        if (a == null) return ZERO;
        return neg(object(a));
    }

    /**
     * 负数，null当零处理
     *
     * @param a 数字
     * @return 结果
     */
    @NotNull
    public static BigDecimal neg(BigDecimal a) {
        if (a == null) return ZERO;
        return a.negate();
    }

    // ///////// ceil /////////

    @NotNull
    public static BigDecimal ceil(String value, int scale) {
        return ceil(object(value), scale);
    }

    @NotNull
    public static BigDecimal ceil(Number value, int scale) {
        return ceil(object(value), scale);
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
        return scale(value, scale, CEILING);
    }

    // ///////// floor /////////

    @NotNull
    public static BigDecimal floor(String value, int scale) {
        return floor(object(value), scale);
    }

    @NotNull
    public static BigDecimal floor(Number value, int scale) {
        return floor(object(value), scale);
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
        return scale(value, scale, FLOOR);
    }

    // ///////// round /////////

    @NotNull
    public static BigDecimal round(String value, int scale) {
        return round(object(value), scale);
    }

    @NotNull
    public static BigDecimal round(Number value, int scale) {
        return round(object(value), scale);
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
        return scale(value, scale, HALF_UP);
    }

    // ///////// scale /////////

    @NotNull
    public static BigDecimal scale(String value, int scale, RoundingMode mode) {
        return scale(object(value), scale, mode);
    }

    @NotNull
    public static BigDecimal scale(Number value, int scale, RoundingMode mode) {
        return scale(object(value), scale, mode);
    }

    /**
     * 砍掉`scale+1`位之后的所有数字，对`scale+1`位进行进位操作。
     *
     * @param value null返回 0.xxx
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 新值
     */
    @NotNull
    public static BigDecimal scale(BigDecimal value, int scale, RoundingMode mode) {
        if (value == null) {
            return ZERO.setScale(scale, mode);
        }
        if (value.scale() == scale) {
            return value;
        } else {
            return value.setScale(scale + 1, FLOOR).setScale(scale, mode);
        }
    }

    /**
     * @param value 数值
     * @param unit  单位
     * @return 处理结果
     * @see #unitUp(BigDecimal, BigDecimal, BigDecimal)
     */
    @NotNull
    public static BigDecimal unitUp(BigDecimal value, BigDecimal unit) {
        return unitUp(value, unit, ZERO);
    }

    /**
     * <pre>
     * 以单位向上取整, null 当零处理, scale以unit为准。
     * 以称重计价为例，称的精度0.01，每0.5计价
     * 当x &gt; 0.1时，按0.5处理，否则按0处理
     * 当x &gt; 0.6时，按1处理，否则按0.5处理
     * </pre>
     *
     * @param value 数值
     * @param unit  单位
     * @param down  单位后余数，小于等于该值则舍去
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitUp(BigDecimal value, BigDecimal unit, BigDecimal down) {
        final int unitScale = unit.scale();

        if (value == null) {
            value = ZERO;
        } else {
            if (down == null) down = ZERO;
            BigDecimal[] dr = value.divideAndRemainder(unit);
            value = dr[0].multiply(unit);

            if (compareTo(dr[1], down, unitScale, FLOOR) > 0) { //向上进位
                value = value.add(unit);
            }
        }

        if (value.scale() != unitScale) {
            value = value.setScale(unitScale, FLOOR);
        }
        return value;
    }

    /**
     * @see #unitDown(BigDecimal, BigDecimal, BigDecimal)
     * @param value 数值
     * @param unit  单位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitDown(BigDecimal value, BigDecimal unit) {
        return unitDown(value, unit, unit);
    }

    /**
     * <pre>
     * 以单位向下取整, null 当零处理, scale以unit为准。
     * 以称重计价为例，称的精度0.01，每0.5计价
     * 当x &gt;= 0.4时，按0.5处理，否则按0处理
     * 当x &gt;= 0.9时，按1处理，否则按0.5处理
     * </pre>
     *
     * @param value 数值
     * @param unit  单位
     * @param upto  单位后余数，大于等于该值则进位
     * @return 处理结果
     */
    @NotNull
    public static BigDecimal unitDown(BigDecimal value, BigDecimal unit, BigDecimal upto) {
        final int unitScale = unit.scale();

        if (value == null) {
            value = ZERO;
        } else {
            BigDecimal[] dr = value.divideAndRemainder(unit);
            value = dr[0].multiply(unit);

            if (compareTo(dr[1], upto, unitScale, FLOOR) >= 0) { //向上进位
                value = value.add(unit);
            }
        }

        if (value.scale() != unitScale) {
            value = value.setScale(unitScale, FLOOR);
        }
        return value;
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #compareTo(BigDecimal, BigDecimal)
     */
    public static int compareTo(BigDecimal a, String b) {
        return compareTo(a, object(b));
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #compareTo(BigDecimal, BigDecimal)
     */
    public static int compareTo(BigDecimal a, Number b) {
        return compareTo(a, object(b));
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
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 结果
     * @see #compareTo(BigDecimal, BigDecimal, int, RoundingMode)
     */
    public static int compareTo(BigDecimal a, String b, int scale, RoundingMode mode) {
        return compareTo(a, object(b), scale, mode);
    }

    /**
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 结果
     * @see #compareTo(BigDecimal, BigDecimal, int, RoundingMode)
     */
    public static int compareTo(BigDecimal a, Number b, int scale, RoundingMode mode) {
        return compareTo(a, object(b), scale, mode);
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
    public static int compareTo(BigDecimal a, BigDecimal b, int scale, RoundingMode mode) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;

        if (a.scale() > scale) {
            a = scale(a, scale, mode);
        }
        if (b.scale() > scale) {
            b = scale(b, scale, mode);
        }
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
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #equalsValue(BigDecimal, BigDecimal)
     */
    public static boolean equalsValue(BigDecimal a, String b) {
        return equalsValue(a, object(b));
    }

    /**
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see #equalsValue(BigDecimal, BigDecimal)
     */
    public static boolean equalsValue(BigDecimal a, Number b) {
        return equalsValue(a, object(b));
    }

    /**
     * null == null, null != notnull
     *
     * @param a 数字
     * @param b 数字
     * @return 结果
     * @see BigDecimal#compareTo(BigDecimal)
     */
    public static boolean equalsValue(BigDecimal a, BigDecimal b) {
        return compareTo(a, b) == 0;
    }

    /**
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 结果
     * @see #equalsValue(BigDecimal, BigDecimal, int, RoundingMode)
     */
    public static boolean equalsValue(BigDecimal a, String b, int scale, RoundingMode mode) {
        return equalsValue(a, object(b), scale, mode);
    }

    /**
     * @param a     数字
     * @param b     数字
     * @param scale 小数点位数，如2为0.00
     * @param mode  舍入类型
     * @return 结果
     * @see #equalsValue(BigDecimal, BigDecimal, int, RoundingMode)
     */
    public static boolean equalsValue(BigDecimal a, Number b, int scale, RoundingMode mode) {
        return equalsValue(a, object(b), scale, mode);
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
    public static boolean equalsValue(BigDecimal a, BigDecimal b, int scale, RoundingMode mode) {
        return compareTo(a, b, scale, mode) == 0;
    }

    /**
     * null当零处理，非线程安全
     */
    public static class W {
        private BigDecimal value;
        private int scale;

        public W(BigDecimal d) {
            if (d == null) {
                this.value = ZERO;
                this.scale = 0;
            } else {
                this.value = d;
                this.scale = d.scale();
            }
        }

        public W(BigDecimal value, int scale) {
            this.value = value == null ? ZERO : value;
            this.scale = scale;
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
        public W setValue(BigDecimal value) {
            this.value = value == null ? ZERO : value;
            return this;
        }

        // add
        @NotNull
        public W add(int b) {
            value = BigDecimalUtil.add(value, b);
            return this;
        }

        @NotNull
        public W add(long b) {
            value = BigDecimalUtil.add(value, b);
            return this;
        }

        @NotNull
        public W add(double b) {
            value = BigDecimalUtil.add(value, b);
            return this;
        }

        @NotNull
        public W add(Number b) {
            value = BigDecimalUtil.add(value, b);
            return this;
        }

        @NotNull
        public W add(BigDecimal b) {
            value = BigDecimalUtil.add(value, b);
            return this;
        }

        @NotNull
        public W add(String b) {
            value = BigDecimalUtil.add(value, b);
            return this;
        }

        @NotNull
        public W add(String b, BigDecimal c) {
            value = BigDecimalUtil.add(value, b, c);
            return this;
        }

        @NotNull
        public W add(BigDecimal b, BigDecimal c) {
            value = BigDecimalUtil.add(value, b, c);
            return this;
        }

        @NotNull
        public W add(W b) {
            value = BigDecimalUtil.add(value, b.value);
            return this;
        }

        // sub
        @NotNull
        public W sub(int b) {
            value = BigDecimalUtil.sub(value, b);
            return this;
        }

        @NotNull
        public W sub(long b) {
            value = BigDecimalUtil.sub(value, b);
            return this;
        }

        @NotNull
        public W sub(double b) {
            value = BigDecimalUtil.sub(value, b);
            return this;
        }

        @NotNull
        public W sub(Number b) {
            value = BigDecimalUtil.sub(value, b);
            return this;
        }

        @NotNull
        public W sub(String b) {
            value = BigDecimalUtil.sub(value, b);
            return this;
        }

        @NotNull
        public W sub(BigDecimal b) {
            value = BigDecimalUtil.sub(value, b);
            return this;
        }

        @NotNull
        public W sub(String b, BigDecimal c) {
            value = BigDecimalUtil.sub(value, b, c);
            return this;
        }

        @NotNull
        public W sub(BigDecimal b, BigDecimal c) {
            value = BigDecimalUtil.sub(value, b, c);
            return this;
        }

        @NotNull
        public W sub(W b) {
            value = BigDecimalUtil.sub(value, b.value);
            return this;
        }

        // mul
        @NotNull
        public W mul(int b) {
            value = BigDecimalUtil.mul(value, b);
            return this;
        }

        @NotNull
        public W mul(long b) {
            value = BigDecimalUtil.mul(value, b);
            return this;
        }

        @NotNull
        public W mul(double b) {
            value = BigDecimalUtil.mul(value, b);
            return this;
        }

        @NotNull
        public W mul(Number b) {
            value = BigDecimalUtil.mul(value, b);
            return this;
        }

        @NotNull
        public W mul(String b) {
            value = BigDecimalUtil.mul(value, b);
            return this;
        }

        @NotNull
        public W mul(BigDecimal b) {
            value = BigDecimalUtil.mul(value, b);
            return this;
        }

        @NotNull
        public W mul(String b, BigDecimal c) {
            value = BigDecimalUtil.mul(value, b, c);
            return this;
        }

        @NotNull
        public W mul(BigDecimal b, BigDecimal c) {
            value = BigDecimalUtil.mul(value, b, c);
            return this;
        }

        @NotNull
        public W mul(W b) {
            value = BigDecimalUtil.mul(value, b.value);
            return this;
        }

        // div
        @NotNull
        public W div(int b) {
            value = BigDecimalUtil.div(value, b);
            return this;
        }

        @NotNull
        public W div(long b) {
            value = BigDecimalUtil.div(value, b);
            return this;
        }

        @NotNull
        public W div(double b) {
            value = BigDecimalUtil.div(value, b);
            return this;
        }

        @NotNull
        public W div(Number b) {
            value = BigDecimalUtil.div(value, b);
            return this;
        }

        @NotNull
        public W div(String b) {
            value = BigDecimalUtil.div(value, b);
            return this;
        }

        @NotNull
        public W div(BigDecimal b) {
            value = BigDecimalUtil.div(value, b);
            return this;
        }

        @NotNull
        public W div(String b, BigDecimal c) {
            value = BigDecimalUtil.div(value, b, c);
            return this;
        }

        @NotNull
        public W div(BigDecimal b, BigDecimal c) {
            value = BigDecimalUtil.div(value, b, c);
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

        @NotNull
        public W sum(String... vs) {
            value = BigDecimalUtil.sum(vs);
            return this;
        }

        @NotNull
        public W sum(BigDecimal... vs) {
            value = BigDecimalUtil.sum(vs);
            return this;
        }

        @NotNull
        public W prd(boolean skipNull, String... vs) {
            value = BigDecimalUtil.prd(skipNull, vs);
            return this;
        }

        @NotNull
        public W prd(boolean skipNull, Number... vs) {
            value = BigDecimalUtil.prd(skipNull, vs);
            return this;
        }

        @NotNull
        public W prd(boolean skipNull, BigDecimal... vs) {
            value = BigDecimalUtil.prd(skipNull, vs);
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
    public static W w(String v) {
        return new W(object(v));
    }

    @NotNull
    public static W w(String v, int scale) {
        return new W(object(v), scale);
    }

    @NotNull
    public static W w(String v, int scale, @NotNull BigDecimal el) {
        return new W(object(v, el), scale);
    }

    @NotNull
    public static W w(Number v) {
        return new W(object(v));
    }

    @NotNull
    public static W w(Number v, int scale) {
        return new W(object(v), scale);
    }

    @NotNull
    public static W w(Number v, int scale, @NotNull BigDecimal el) {
        return new W(object(v, el), scale);
    }

    @NotNull
    public static W w(BigDecimal v) {
        return new W(v);
    }

    @NotNull
    public static W w(BigDecimal v, int scale) {
        return new W(v, scale);
    }

    @NotNull
    public static W w(BigDecimal... vs) {
        return new W(notNull(ZERO, vs));
    }

    @NotNull
    public static W w(int scale, BigDecimal... vs) {
        return new W(notNull(ZERO, vs), scale);
    }

    @NotNull
    public static W w(int scale, Number... vs) {
        return new W(notNull(ZERO, vs), scale);
    }
}
