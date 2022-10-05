package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;

/**
 * Array的一些操作
 *
 * @author trydofor
 * @since 2020-06-08
 */
public class Arr {

    @SafeVarargs
    public static <T> T[] of(T... ts) {
        return ts;
    }

    public static boolean @NotNull [] of(boolean... ts) {
        return Null.notNull(ts);
    }

    public static byte @NotNull [] of(byte... ts) {
        return Null.notNull(ts);
    }

    public static short @NotNull [] of(short... ts) {
        return Null.notNull(ts);
    }

    public static char @NotNull [] of(char... ts) {
        return Null.notNull(ts);
    }

    public static int @NotNull [] of(int... ts) {
        return Null.notNull(ts);
    }

    public static long @NotNull [] of(long... ts) {
        return Null.notNull(ts);
    }

    public static float @NotNull [] of(float... ts) {
        return Null.notNull(ts);
    }

    public static double @NotNull [] of(double... ts) {
        return Null.notNull(ts);
    }


    public static boolean[] set(boolean[] arr, int idx, boolean v) {
        // ensure length
        if (idx >= 0 && idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            boolean[] tmp = new boolean[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static byte[] set(byte[] arr, int idx, byte v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            byte[] tmp = new byte[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static short[] set(short[] arr, int idx, short v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            short[] tmp = new short[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static char[] set(char[] arr, int idx, char v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            char[] tmp = new char[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static int[] set(int[] arr, int idx, int v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            int[] tmp = new int[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static long[] set(long[] arr, int idx, long v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            long[] tmp = new long[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static float[] set(float[] arr, int idx, float v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            float[] tmp = new float[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }

    public static double[] set(double[] arr, int idx, double v) {
        // ensure length
        if (idx < arr.length) {
            arr[idx] = v;
            return arr;
        }
        else {
            double[] tmp = new double[arr.length * 2];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            tmp[idx] = v;
            return tmp;
        }
    }
}
