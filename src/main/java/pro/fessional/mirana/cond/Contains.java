package pro.fessional.mirana.cond;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author trydofor
 * @since 2020-04-01
 */
public class Contains {

    public static boolean eqVal(@Nullable Integer num, @Nullable Number... nums) {
        if (num == null || nums == null) return false;
        for (Number n : nums) {
            if (n != null && n.intValue() == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean eqVal(@Nullable Integer num, @Nullable Collection<? extends Number> nums) {
        if (num == null || nums == null) return false;
        for (Number n : nums) {
            if (n != null && n.intValue() == num) {
                return true;
            }
        }
        return false;
    }

    //
    public static boolean eqVal(@Nullable Long num, @Nullable Number... nums) {
        if (num == null || nums == null) return false;
        for (Number n : nums) {
            if (n != null && n.longValue() == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean eqVal(@Nullable Long num, @Nullable Collection<? extends Number> nums) {
        if (num == null || nums == null) return false;
        for (Number n : nums) {
            if (n != null && n.longValue() == num) {
                return true;
            }
        }
        return false;
    }

    //
    public static boolean eqVal(@Nullable BigDecimal num, @Nullable Number... nums) {
        if (num == null || nums == null) return false;
        for (Number n : nums) {
            if (n instanceof BigDecimal) {
                if (num.compareTo((BigDecimal) n) == 0) return true;
            }
            else if (n != null) {
                BigDecimal d = BigDecimal.valueOf(n.doubleValue()).setScale(num.scale(), BigDecimal.ROUND_UP);
                if (num.compareTo(d) == 0) return true;
            }
        }
        return false;
    }

    public static boolean eqVal(@Nullable BigDecimal num, @Nullable Collection<? extends Number> nums) {
        if (num == null || nums == null) return false;
        for (Number n : nums) {
            if (n instanceof BigDecimal) {
                if (num.compareTo((BigDecimal) n) == 0) return true;
            }
            else if (n != null) {
                BigDecimal d = BigDecimal.valueOf(n.doubleValue()).setScale(num.scale(), BigDecimal.ROUND_UP);
                if (num.compareTo(d) == 0) return true;
            }
        }
        return false;
    }

    //
    public static boolean eqCase(@Nullable String str, @Nullable CharSequence... strs) {
        if (str == null || strs == null) return false;
        for (CharSequence s : strs) {
            if (s != null && str.equals(s.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean eqCase(@Nullable String str, @Nullable Collection<? extends CharSequence> strs) {
        if (str == null || strs == null) return false;
        for (CharSequence s : strs) {
            if (s != null && str.equals(s.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean igCase(@Nullable String str, @Nullable CharSequence... strs) {
        if (str == null || strs == null) return false;
        for (CharSequence s : strs) {
            if (s != null && str.equalsIgnoreCase(s.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean igCase(@Nullable String str, @Nullable Collection<? extends CharSequence> strs) {
        if (str == null || strs == null) return false;
        for (CharSequence s : strs) {
            if (s != null && str.equalsIgnoreCase(s.toString())) {
                return true;
            }
        }
        return false;
    }
}
