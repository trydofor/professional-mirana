package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;

import java.math.BigDecimal;

/**
 * @author trydofor
 * @since 2019-07-20
 */
public class StringCastUtil {

    /**
     * true,t,yes,y,不区分大小写，认为是true
     * 注意，和asFalse不是存在传递关系
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean asTrue(@Nullable String str) {
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
    public static boolean asFalse(@Nullable String str) {
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
     * @param elz 默认值
     * @return 成功的结果或默认值
     */
    public static long asLong(@Nullable String str, long elz) {
        if (str == null) return elz;
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * 转换成long
     *
     * @param str 字符串
     * @param elz 默认值
     * @return 成功的结果或默认值
     */
    public static int asInt(@Nullable String str, int elz) {
        if (str == null) return elz;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * 转换成float
     *
     * @param str 字符串
     * @param elz 默认值
     * @return 成功的结果或默认值
     */
    public static float asFloat(@Nullable String str, float elz) {
        if (str == null) return elz;
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * 转换成double
     *
     * @param str 字符串
     * @param elz 默认值
     * @return 成功的结果或默认值
     */
    public static double asDouble(@Nullable String str, double elz) {
        if (str == null) return elz;
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return elz;
        }
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

    @NotNull
    public static String string(BigDecimal v) {
        return v == null ? Null.Str : v.toPlainString();
    }

    @NotNull
    public static String string(Object v) {
        return v == null ? Null.Str : String.valueOf(v);
    }

    @Contract("_,!null -> !null")
    public static String string(BigDecimal v, String elz) {
        return v == null ? elz : v.toPlainString();
    }

    @Contract("_,!null -> !null")
    public static String string(Object v, String elz) {
        return v == null ? elz : String.valueOf(v);
    }
}
