package pro.fessional.mirana.cast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * autoboxing and unboxing
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class BoxedCastUtil {

    public static boolean orTrue(@Nullable Boolean b) {
        if (b == null) return true;
        return b;
    }

    public static boolean orFalse(@Nullable Boolean b) {
        if (b == null) return false;
        return b;
    }

    public static char orZero(@Nullable Character n) {
        return orElse(n, (char) 0);
    }

    public static char orElse(@Nullable Character n, char o) {
        if (n == null) return o;
        return n;
    }

    public static byte orZero(@Nullable Byte n) {
        return orElse(n, (byte) 0);
    }

    public static byte orElse(@Nullable Byte n, byte o) {
        if (n == null) return o;
        return n;
    }

    public static short orZero(@Nullable Short n) {
        return orElse(n, (short) 0);
    }

    public static short orElse(@Nullable Short n, short o) {
        if (n == null) return o;
        return n;
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

    @NotNull
    public static boolean[] bools(Collection<Boolean> cols) {
        if (cols == null || cols.isEmpty()) return Null.Bools;
        boolean[] arr = new boolean[cols.size()];
        int i = 0;
        for (Boolean v : cols) {
            arr[i++] = v != null && v;
        }
        return arr;
    }

    @NotNull
    public static char[] chars(Collection<Character> cols) {
        if (cols == null || cols.isEmpty()) return Null.Chars;
        char[] arr = new char[cols.size()];
        int i = 0;
        for (Character v : cols) {
            arr[i++] = v == null ? (char) 0 : v;
        }
        return arr;
    }

    @NotNull
    public static byte[] bytes(Collection<? extends Number> cols) {
        if (cols == null || cols.isEmpty()) return Null.Bytes;
        byte[] arr = new byte[cols.size()];
        int i = 0;
        for (Number v : cols) {
            arr[i++] = v == null ? (byte) 0 : v.byteValue();
        }
        return arr;
    }

    @NotNull
    public static short[] shorts(Collection<? extends Number> cols) {
        if (cols == null || cols.isEmpty()) return Null.Shorts;
        short[] arr = new short[cols.size()];
        int i = 0;
        for (Number v : cols) {
            arr[i++] = v == null ? (short) 0 : v.shortValue();
        }
        return arr;
    }

    @NotNull
    public static int[] ints(Collection<? extends Number> cols) {
        if (cols == null || cols.isEmpty()) return Null.Ints;
        int[] arr = new int[cols.size()];
        int i = 0;
        for (Number v : cols) {
            arr[i++] = v == null ? 0 : v.intValue();
        }
        return arr;
    }

    @NotNull
    public static long[] longs(Collection<? extends Number> cols) {
        if (cols == null || cols.isEmpty()) return Null.Longs;
        long[] arr = new long[cols.size()];
        int i = 0;
        for (Number v : cols) {
            arr[i++] = v == null ? 0L : v.longValue();
        }
        return arr;
    }

    @NotNull
    public static float[] floats(Collection<? extends Number> cols) {
        if (cols == null || cols.isEmpty()) return Null.Floats;
        float[] arr = new float[cols.size()];
        int i = 0;
        for (Number v : cols) {
            arr[i++] = v == null ? 0F : v.floatValue();
        }
        return arr;
    }

    @NotNull
    public static double[] doubles(Collection<? extends Number> cols) {
        if (cols == null || cols.isEmpty()) return Null.Doubles;
        double[] arr = new double[cols.size()];
        int i = 0;
        for (Number v : cols) {
            arr[i++] = v == null ? 0D : v.doubleValue();
        }
        return arr;
    }

    @NotNull
    public static List<Boolean> list(boolean[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Boolean> lst = new ArrayList<>(arr.length);
        for (boolean b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Character> list(char[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Character> lst = new ArrayList<>(arr.length);
        for (char b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Byte> list(byte[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Byte> lst = new ArrayList<>(arr.length);
        for (byte b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Short> list(short[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Short> lst = new ArrayList<>(arr.length);
        for (short b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Integer> list(int[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Integer> lst = new ArrayList<>(arr.length);
        for (int b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Long> list(long[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Long> lst = new ArrayList<>(arr.length);
        for (long b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Float> list(float[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Float> lst = new ArrayList<>(arr.length);
        for (float b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    public static List<Double> list(double[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();

        List<Double> lst = new ArrayList<>(arr.length);
        for (double b : arr) {
            lst.add(b);
        }
        return lst;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static List<Object> list(Object obj) {
        if (obj == null) return Collections.emptyList();

        List<?> vs;
        if (obj instanceof boolean[]) {
            vs = BoxedCastUtil.list((boolean[]) obj);
        }
        else if (obj instanceof byte[]) {
            vs = BoxedCastUtil.list((byte[]) obj);
        }
        else if (obj instanceof char[]) {
            vs = BoxedCastUtil.list((char[]) obj);
        }
        else if (obj instanceof int[]) {
            vs = BoxedCastUtil.list((int[]) obj);
        }
        else if (obj instanceof long[]) {
            vs = BoxedCastUtil.list((long[]) obj);
        }
        else if (obj instanceof float[]) {
            vs = BoxedCastUtil.list((float[]) obj);
        }
        else if (obj instanceof double[]) {
            vs = BoxedCastUtil.list((double[]) obj);
        }
        else if (obj instanceof Collection) {
            vs = new ArrayList<>((Collection<?>) obj);
        }
        else if (obj.getClass().isArray()) {
            vs = Arrays.asList((Object[]) obj);
        }
        else {
            vs = Collections.singletonList(obj);
        }

        return (List<Object>) vs;
    }
}
