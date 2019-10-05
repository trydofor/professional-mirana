package pro.fessional.mirana.cast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * @author trydofor
 * @since 2019-10-05
 */
public class WrapTypeUtil {

    public static boolean orTrue(@Nullable Boolean b) {
        if (b == null) return true;
        return b;
    }

    public static boolean orFalse(@Nullable Boolean b) {
        if (b == null) return false;
        return b;
    }

    public static int orZero(@Nullable Integer n) {
        return orElse(n, 0);
    }

    public static int orElse(@Nullable Integer n, int o) {
        if (n == null) return o;
        return n;
    }

    public static long orZero(@Nullable Long n) {
        return orElse(n, 0L);
    }

    public static long orElse(@Nullable Long n, long o) {
        if (n == null) return o;
        return n;
    }

    public static double orZero(@Nullable Double n) {
        return orElse(n, 0.0);
    }

    public static double orElse(@Nullable Double n, double o) {
        if (n == null) return o;
        return n;
    }

    public static float orZero(@Nullable Float n) {
        return orElse(n, 0.0F);
    }

    public static float orElse(@Nullable Float n, float o) {
        if (n == null) return o;
        return n;
    }

    public static BigDecimal orZero(@Nullable BigDecimal n) {
        return orElse(n, BigDecimal.ZERO);
    }

    @NotNull
    public static <T> T orElse(@Nullable T n, @NotNull T o) {
        if (n == null) return o;
        return n;
    }
}
