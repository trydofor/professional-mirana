package pro.fessional.mirana.math;

/**
 * 自动处理 [-+]?[-_0-9,]+的字符串变成整数
 *
 * @author trydofor
 * @since 2015-12-12.
 */
public class AnyIntegerUtil {

    public static long val64(String str) {
        return val64(str, 0);
    }

    public static long val64(String str, long elze) {
        if (str == null)
            return elze;
        try {
            return Long.parseLong(trimToInteger(str));
        } catch (NumberFormatException e) {
            return elze;
        }
    }

    public static long val64(Number num) {
        return val64(num, 0);
    }

    public static long val64(Number num, long elze) {
        if (num == null) return elze;
        return num.longValue();
    }

    public static int val32(String str) {
        return val32(str, 0);
    }

    public static int val32(String str, int elze) {
        if (str == null)
            return elze;
        try {
            return Integer.parseInt(trimToInteger(str));
        } catch (NumberFormatException e) {
            return elze;
        }
    }

    public static int val32(Number num) {
        return val32(num, 0);
    }

    public static int val32(Number num, int elze) {
        if (num == null) return elze;
        return num.intValue();
    }

    //

    public static Long obj64(Number num) {
        return obj64(num, null);
    }

    public static Long obj64(Number num, Long elze) {
        if (num == null) return elze;
        return num.longValue();
    }

    public static Long obj64(String str) {
        return obj64(str, null);
    }

    public static Long obj64(String str, Long elze) {
        if (str == null)
            return elze;
        try {
            return Long.valueOf(trimToInteger(str));
        } catch (NumberFormatException e) {
            return elze;
        }
    }


    public static Integer obj32(Number num) {
        return obj32(num, null);
    }

    public static Integer obj32(Number num, Integer elze) {
        if (num == null) return elze;
        return num.intValue();
    }

    public static Integer obj32(String str) {
        return obj32(str, null);
    }

    public static Integer obj32(String str, Integer elze) {
        if (str == null)
            return elze;
        try {
            return Integer.valueOf(trimToInteger(str));
        } catch (NumberFormatException e) {
            return elze;
        }
    }

    //

    public static boolean equals(Long i1, long i2) {
        return i1 != null && i1 == i2;
    }

    public static boolean equals(Integer i1, int i2) {
        return i1 != null && i1 == i2;
    }

    //

    public static String string(Long v) {
        return string(v, "");
    }

    public static String string(Long v, String elz) {
        if (v == null) return elz;
        return v.toString();
    }

    public static String string(Integer v) {
        return string(v, "");
    }

    public static String string(Integer v, String elz) {
        if (v == null) return elz;
        return v.toString();
    }

    /**
     * 自动处理 [-+]?[-_0-9,]+的字符串变成整数
     *
     * @param str 字符串
     * @return -?[0-9]+
     */
    public static String trimToInteger(String str) {
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == '-' && sb.length() == 0) {
                sb.append(c);
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
            } else if (c == '.') {
                break;
            }
        }
        return sb.toString();
    }
}
