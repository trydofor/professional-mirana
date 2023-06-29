package pro.fessional.mirana.cond;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * In most cases, the stream and lambda can be used
 *
 * @author trydofor
 * @since 2020-04-01
 */
public class EqualsUtil {

    //
    public static boolean eqVal(Number a, Number b) {
        if (a == null || b == null) return false;

        if (a instanceof Integer) {
            return a.intValue() == b.intValue();
        }
        if (a instanceof Long) {
            return a.longValue() == b.longValue();
        }
        if (a instanceof BigDecimal) {
            if (b instanceof BigDecimal) {
                return ((BigDecimal) a).compareTo((BigDecimal) b) == 0;
            }
            else {
                return ((BigDecimal) a).compareTo(new BigDecimal(b.toString())) == 0;
            }
        }

        if (a instanceof Float) {
            return Float.compare(a.floatValue(), b.floatValue()) == 0;
        }
        if (a instanceof Double) {
            return Double.compare(a.doubleValue(), b.doubleValue()) == 0;
        }
        if (a instanceof Byte) {
            return a.byteValue() == b.byteValue();
        }
        if (a instanceof Short) {
            return a.shortValue() == b.shortValue();
        }
        if (a instanceof BigInteger) {
            if (b instanceof BigInteger) {
                return ((BigInteger) a).compareTo((BigInteger) b) == 0;
            }
            else {
                final BigDecimal x = new BigDecimal((BigInteger) a);
                final BigDecimal y = new BigDecimal(b.toString());
                return x.compareTo(y) == 0;
            }
        }

        final BigDecimal x = new BigDecimal(a.toString());
        final BigDecimal y = new BigDecimal(b.toString());
        return x.compareTo(y) == 0;
    }

    //
    public static boolean inVal(Number a, Number[] nums) {
        if (a == null || nums == null) return false;
        return inVal(a, Arrays.asList(nums));
    }

    public static boolean inVal(Number a, Number b, Number... nums) {
        if (a == null || b == null || nums == null) return false;
        final boolean e = eqVal(a, b);
        return e ? e : inVal(a, Arrays.asList(nums));

    }

    public static boolean inVal(Number a, Iterable<? extends Number> nums) {
        if (a == null || nums == null) return false;
        for (Number n : nums) {
            if (n != null && eqVal(a, n)) {
                return true;
            }
        }
        return false;
    }

    //
    public static boolean inVal(Number a, int[] nums) {
        if (a == null || nums == null) return false;
        final int v = a.intValue();
        for (int n : nums) {
            if (v == n) return true;
        }
        return false;
    }

    public static boolean inVal(Number a, long[] nums) {
        if (a == null || nums == null) return false;
        final long v = a.longValue();
        for (long n : nums) {
            if (v == n) return true;
        }
        return false;
    }

    public static boolean inVal(Number a, float[] nums) {
        if (a == null || nums == null) return false;
        final float v = a.floatValue();
        for (float n : nums) {
            if (Float.compare(v, n) == 0) return true;
        }
        return false;
    }

    public static boolean inVal(Number a, double[] nums) {
        if (a == null || nums == null) return false;
        final double v = a.doubleValue();
        for (double n : nums) {
            if (Double.compare(v, n) == 0) return true;
        }
        return false;
    }

    //
    public static boolean eqCase(String a, CharSequence b) {
        if (a == null || b == null) return false;
        return a.equals(b.toString());
    }

    public static boolean inCase(String a, CharSequence[] strs) {
        if (a == null || strs == null) return false;
        return inCase(a, Arrays.asList(strs));
    }

    public static boolean inCase(String a, CharSequence b, CharSequence... strs) {
        if (a == null || b == null || strs == null) return false;
        final boolean e = eqCase(a, b);
        return e ? e : inCase(a, Arrays.asList(strs));
    }

    public static boolean inCase(String a, Iterable<? extends CharSequence> strs) {
        if (a == null || strs == null) return false;
        for (CharSequence s : strs) {
            if (s != null && a.equals(s.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean eqCaseless(String a, CharSequence b) {
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b.toString());
    }

    public static boolean inCaseless(String a, CharSequence[] strs) {
        if (a == null || strs == null) return false;
        return inCaseless(a, Arrays.asList(strs));
    }

    public static boolean inCaseless(String a, CharSequence b, CharSequence... strs) {
        if (a == null || b == null || strs == null) return false;
        final boolean e = eqCaseless(a, b);
        return e ? e : inCaseless(a, Arrays.asList(strs));
    }

    public static boolean inCaseless(String a, Iterable<? extends CharSequence> strs) {
        if (a == null || strs == null) return false;
        for (CharSequence s : strs) {
            if (s != null && a.equalsIgnoreCase(s.toString())) {
                return true;
            }
        }
        return false;
    }
}
