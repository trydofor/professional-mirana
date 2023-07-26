package pro.fessional.mirana.math;

/**
 * Auto convert string in `[-+]?[-_0-9,]+` to number
 *
 * @author trydofor
 * @since 2015-12-12.
 */
public class AnyIntegerUtil {

    public static long val64(CharSequence str) {
        return val64(str, 0);
    }

    public static long val64(CharSequence str, long elze) {
        if (str == null)
            return elze;
        try {
            return Long.parseLong(trimToInteger(str));
        }
        catch (NumberFormatException e) {
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

    public static int val32(CharSequence str) {
        return val32(str, 0);
    }

    public static int val32(CharSequence str, int elze) {
        if (str == null)
            return elze;
        try {
            return Integer.parseInt(trimToInteger(str));
        }
        catch (NumberFormatException e) {
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

    public static Long obj64(CharSequence str) {
        return obj64(str, null);
    }

    public static Long obj64(CharSequence str, Long elze) {
        if (str == null)
            return elze;
        try {
            return Long.valueOf(trimToInteger(str));
        }
        catch (NumberFormatException e) {
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

    public static Integer obj32(CharSequence str) {
        return obj32(str, null);
    }

    public static Integer obj32(CharSequence str, Integer elze) {
        if (str == null)
            return elze;
        try {
            return Integer.valueOf(trimToInteger(str));
        }
        catch (NumberFormatException e) {
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
     * Auto convert string to number string in `[-+]?[-_0-9,]+`
     */
    public static String trimToInteger(CharSequence str) {
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == '-' && sb.length() == 0) {
                sb.append(c);
            }
            else if (c >= '0' && c <= '9') {
                sb.append(c);
            }
            else if (c == '.') {
                break;
            }
        }
        return sb.toString();
    }

    public static int next32(CharSequence num, int unit) {
        if (num == null) return unit;
        return next32(val32(num, 0), unit);
    }

    public static int next32(Number num, int unit) {
        if (num == null) return unit;
        return next32(num.intValue(), unit);
    }

    /**
     * Round up to the next UNIT. eg. if unit=10, num=1, next=10; num=10, next=20
     */
    public static int next32(int num, int unit) {
        if (unit == 0) return num;
        return num - num % unit + unit;
    }

    public static long next64(CharSequence num, long unit) {
        if (num == null) return unit;
        return next64(val64(num, 0), unit);
    }

    public static long next64(Number num, long unit) {
        if (num == null) return unit;
        return next64(num.longValue(), unit);
    }

    /**
     * Round up to the next UNIT. eg. if unit=10, num=1, next=10; num=10, next=20
     */
    public static long next64(long num, long unit) {
        if (unit == 0) return num;
        return num - num % unit + unit;
    }

    public static int prev32(CharSequence num, int unit) {
        if (num == null) return unit;
        return prev32(val32(num, 0), unit);
    }

    public static int prev32(Number num, int unit) {
        if (num == null) return unit;
        return prev32(num.intValue(), unit);
    }

    /**
     * Round up to the previous unit. eg. if unit=10, num=10, prev=0; num=11, prev=10
     */
    public static int prev32(int num, int unit) {
        if (unit == 0) return num;
        final int rn = num % unit;
        return rn == 0 ? num - unit : num - rn;
    }

    public static long prev64(CharSequence num, long unit) {
        if (num == null) return unit;
        return prev64(val64(num, 0), unit);
    }

    public static long prev64(Number num, long unit) {
        if (num == null) return unit;
        return prev64(num.longValue(), unit);
    }

    /**
     * Round up to the previous unit. eg. if unit=10, num=10, prev=0; num=11, prev=10
     */
    public static long prev64(long num, long unit) {
        if (unit == 0) return num;
        final long rn = num % unit;
        return rn == 0 ? num - unit : num - rn;
    }
}
