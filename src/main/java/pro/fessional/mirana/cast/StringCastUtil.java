package pro.fessional.mirana.cast;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * @author trydofor
 * @since 2019-07-20
 */
public class StringCastUtil {
    private StringCastUtil() {
    }

    /**
     * true,t,yes,y,不区分大小写，认为是true
     * 注意，和asFalse不是存在传递关系
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean asTrue(String str) {
        if (str == null) return false;
        return "true".equalsIgnoreCase(str) ||
                "t".equalsIgnoreCase(str) ||
                "yes".equalsIgnoreCase(str) ||
                "y".equalsIgnoreCase(str);
    }

    /**
     * null, empty, blank, false,f,no,n不区分大小写，认为是false
     * 注意，和asTrue不是存在传递关系
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean asFalse(String str) {
        if (str == null) return true;
        return "false".equalsIgnoreCase(str) ||
                "f".equalsIgnoreCase(str) ||
                "no".equalsIgnoreCase(str) ||
                "n".equalsIgnoreCase(str) ||
                str.isEmpty() ||
                str.trim().isEmpty();
    }

    /**
     * 转换成long
     *
     * @param str 字符串
     * @return 结果
     * @throws NullPointerException  如果 str为null
     * @throws NumberFormatException 如果 解析失败
     */
    public static long asLong(String str) {
        if (str == null) throw new NullPointerException();
        return Long.parseLong(str);
    }

    /**
     * 转换成long
     *
     * @param str 字符串
     * @param elz 默认值
     * @return 成功的结果或默认值
     */
    public static long asLong(String str, long elz) {
        if (str == null) return elz;
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * 转换成BigDecimal
     *
     * @param str 字符串
     * @return 结果
     * @throws NullPointerException  如果 str为null
     * @throws NumberFormatException 如果 解析失败
     */
    @NotNull
    public static BigDecimal asDecimal(String str) {
        if (str == null) throw new NullPointerException();
        return new BigDecimal(str);
    }

    /**
     * 转换成BigDecimal
     *
     * @param str 字符串
     * @param elz 默认值
     * @return 成功的结果或默认值
     */
    @NotNull
    public static BigDecimal asDecimal(String str, @NotNull BigDecimal elz) {
        if (str == null) return elz;
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return elz;
        }
    }
}
