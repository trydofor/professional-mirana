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
     * parse case-insensitive `true|t|yes|y` to true
     * Note, no pass-through relationship with asFalse.
     */
    public static boolean asTrue(@Nullable String str) {
        if (str == null) return false;
        return "true".equalsIgnoreCase(str) ||
               "t".equalsIgnoreCase(str) ||
               "yes".equalsIgnoreCase(str) ||
               "y".equalsIgnoreCase(str);
    }

    /**
     * parse case-insensitive `null`, `empty`, `blank` and `false,f,no,n` to false (BUT return true)
     * NOTE1, no pass-through relationship with asTrue.
     * NOTE2, return true if asFalse, e.g. "false".equalsIgnoreCase(str)
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
     * convert to long, or elz if fail
     */
    public static long asLong(@Nullable String str, long elz) {
        if (str == null) return elz;
        try {
            return Long.parseLong(str);
        }
        catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * convert to int, or elz if fail
     */
    public static int asInt(@Nullable String str, int elz) {
        if (str == null) return elz;
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * convert to float, or elz if fail
     */
    public static float asFloat(@Nullable String str, float elz) {
        if (str == null) return elz;
        try {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * convert to double, or elz if fail
     */
    public static double asDouble(@Nullable String str, double elz) {
        if (str == null) return elz;
        try {
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e) {
            return elz;
        }
    }

    /**
     * convert to BigDecimal, or elz if fail
     */
    @NotNull
    public static BigDecimal asDecimal(String str, @NotNull BigDecimal elz) {
        if (str == null) return elz;
        try {
            return new BigDecimal(str);
        }
        catch (Exception e) {
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
