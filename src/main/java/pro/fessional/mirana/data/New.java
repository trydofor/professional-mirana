package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2020-06-08
 */
public class New {

    @SafeVarargs
    public static <T> T[] array(T... ts) {
        return ts;
    }

    @NotNull
    public static int[] array(int... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static long[] array(long... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static float[] array(float... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static double[] array(double... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static boolean[] array(boolean... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static short[] array(short... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static char[] array(char... ts) {
        return Null.notNull(ts);
    }

    @NotNull
    public static byte[] array(byte... ts) {
        return Null.notNull(ts);
    }

}
