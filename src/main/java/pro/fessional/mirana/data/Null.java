package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author trydofor
 * @since 2019-10-15
 */
public class Null {

    public enum Eu {
        Null
    }

    public static final boolean Int01 = false;
    public static final byte Int08 = 0;
    public static final char Int16 = 0;
    public static final int Int32 = 0;
    public static final long Int64 = 0L;
    public static final float Flt32 = 0.0F;
    public static final double Flt64 = 0.0D;

    public static final @NotNull Enum<?> Enm = Eu.Null;
    public static final @NotNull Class<?> Clz = Void.class;

    //
    public static final @NotNull String Str = "";
    public static final boolean @NotNull [] Bools = new boolean[0];
    public static final byte @NotNull [] Bytes = new byte[0];
    public static final char @NotNull [] Chars = new char[0];
    public static final short @NotNull [] Shorts = new short[0];
    public static final int @NotNull [] Ints = new int[0];
    public static final long @NotNull [] Longs = new long[0];
    public static final float @NotNull [] Floats = new float[0];
    public static final double @NotNull [] Doubles = new double[0];
    public static final Object @NotNull [] Objects = new Object[0];
    public static final BigDecimal @NotNull [] BigDecimals = new BigDecimal[0];

    public static final Boolean @NotNull [] BoolArr = new Boolean[0];
    public static final Byte @NotNull [] ByteArr = new Byte[0];
    public static final Short @NotNull [] ShortArr = new Short[0];
    public static final Integer @NotNull [] IntArr = new Integer[0];
    public static final Long @NotNull [] LongArr = new Long[0];
    public static final Float @NotNull [] FloatArr = new Float[0];
    public static final Double @NotNull [] DoubleArr = new Double[0];
    public static final String @NotNull [] StrArr = new String[0];
    public static final Class<?> @NotNull [] ClzArr = new Class<?>[0];

    // /////////////////
    public static boolean asNull(boolean v) {
        return v == Int01;
    }

    public static boolean asNull(byte v) {
        return v == Int08;
    }

    public static boolean asNull(char v) {
        return v == Int16;
    }

    public static boolean asNull(short v) {
        return v == Int16;
    }

    public static boolean asNull(int v) {
        return v == Int32;
    }

    public static boolean asNull(long v) {
        return v == Int64;
    }

    public static boolean asNull(float v) {
        return v == Flt32;
    }

    public static boolean asNull(double v) {
        return v == Flt64;
    }

    public static boolean asNull(Boolean v) {
        return v == null || v == Int01;
    }

    public static boolean asNull(Byte v) {
        return v == null || v == Int08;
    }

    public static boolean asNull(Character v) {
        return v == null || v == Int16;
    }

    public static boolean asNull(Short v) {
        return v == null || v == Int16;
    }

    public static boolean asNull(Integer v) {
        return v == null || v == Int32;
    }

    public static boolean asNull(Long v) {
        return v == null || v == Int64;
    }

    public static boolean asNull(Float v) {
        return v == null || v == Flt32;
    }

    public static boolean asNull(Double v) {
        return v == null || v == Flt64;
    }

    public static boolean asNull(CharSequence v) {
        return v == null || v.length() == 0;
    }

    public static boolean asNull(boolean[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(byte[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(char[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(short[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(int[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(long[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(float[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(double[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(Object[] v) {
        return v == null || v.length == 0;
    }

    public static boolean asNull(Enum<?> v) {
        return v == null || v == Enm;
    }

    public static boolean asNull(Class<?> v) {
        return v == null || v == Clz;
    }

    // /////////////////

    public static boolean notNull(Boolean v) {
        return v == null ? Int01 : v;
    }

    public static byte notNull(Byte v) {
        return v == null ? Int08 : v;
    }

    public static char notNull(Character v) {
        return v == null ? Int16 : v;
    }

    public static short notNull(Short v) {
        return v == null ? (short) Int16 : v;
    }

    public static int notNull(Integer v) {
        return v == null ? Int32 : v;
    }

    public static long notNull(Long v) {
        return v == null ? Int64 : v;
    }

    public static float notNull(Float v) {
        return v == null ? Flt32 : v;
    }

    public static double notNull(Double v) {
        return v == null ? Flt64 : v;
    }

    public static @NotNull String notNull(CharSequence v) {
        return v == null ? Str : v.toString();
    }

    public static boolean @NotNull [] notNull(boolean[] v) {
        return v == null ? Bools : v;
    }

    public static byte @NotNull [] notNull(byte[] v) {
        return v == null ? Bytes : v;
    }

    public static char @NotNull [] notNull(char[] v) {
        return v == null ? Chars : v;
    }

    public static short @NotNull [] notNull(short[] v) {
        return v == null ? Shorts : v;
    }

    public static int @NotNull [] notNull(int[] v) {
        return v == null ? Ints : v;
    }

    public static long @NotNull [] notNull(long[] v) {
        return v == null ? Longs : v;
    }

    public static float @NotNull [] notNull(float[] v) {
        return v == null ? Floats : v;
    }

    public static double @NotNull [] notNull(double[] v) {
        return v == null ? Doubles : v;
    }

    public static Object @NotNull [] notNull(Object[] v) {
        return v == null ? Objects : v;
    }

    public static @NotNull Enum<?> notNull(Enum<?> v) {
        return v == null ? Enm : v;
    }

    public static @NotNull Class<?> notNull(Class<?> v) {
        return v == null ? Clz : v;
    }

    // /////////////////

    public static boolean notNull(Boolean v, boolean e) {
        return v == null ? e : v;
    }

    public static byte notNull(Byte v, byte e) {
        return v == null ? e : v;
    }

    public static char notNull(Character v, char e) {
        return v == null ? e : v;
    }

    public static short notNull(Short v, short e) {
        return v == null ? e : v;
    }

    public static int notNull(Integer v, int e) {
        return v == null ? e : v;
    }

    public static long notNull(Long v, long e) {
        return v == null ? e : v;
    }

    public static float notNull(Float v, float e) {
        return v == null ? e : v;
    }

    public static double notNull(Double v, double e) {
        return v == null ? e : v;
    }

    public static @NotNull String notNull(CharSequence v, @NotNull String e) {
        return v == null ? e : v.toString();
    }

    public static boolean @NotNull [] notNull(boolean[] v, boolean @NotNull [] e) {
        return v == null ? e : v;
    }

    public static byte @NotNull [] notNull(byte[] v, byte @NotNull [] e) {
        return v == null ? e : v;
    }

    public static char @NotNull [] notNull(char[] v, char @NotNull [] e) {
        return v == null ? e : v;
    }

    public static short @NotNull [] notNull(short[] v, short @NotNull [] e) {
        return v == null ? e : v;
    }

    public static int @NotNull [] notNull(int[] v, int @NotNull [] e) {
        return v == null ? e : v;
    }

    public static long @NotNull [] notNull(long[] v, long @NotNull [] e) {
        return v == null ? e : v;
    }

    public static float @NotNull [] notNull(float[] v, float @NotNull [] e) {
        return v == null ? e : v;
    }

    public static double @NotNull [] notNull(double[] v, double @NotNull [] e) {
        return v == null ? e : v;
    }

    public static Object @NotNull [] notNull(Object[] v, Object @NotNull [] e) {
        return v == null ? e : v;
    }

    public static <T extends Enum<T>> @NotNull T notNull(T v, T e) {
        return v == null ? e : v;
    }

    public static <T> @NotNull Class<T> notNull(Class<T> v, Class<T> e) {
        return v == null ? e : v;
    }

    /**
     * @deprecated replaced by {@link pro.fessional.mirana.cond.IfSetter#nonnull(Consumer, Object)}
     */
    public static <T> void notNull(@Nullable T obj, @NotNull Consumer<T> con) {
        if (obj != null) con.accept(obj);
    }

    @NotNull
    public static <T> T notNull(@Nullable T obj, @NotNull Supplier<T> sup) {
        return obj != null ? obj : sup.get();
    }

    @NotNull
    public static <K, V> Map<K, V> notNull(@Nullable Map<K, V> obj) {
        return obj != null ? obj : Collections.emptyMap();
    }

    @NotNull
    public static <V> Set<V> notNull(@Nullable Set<V> obj) {
        return obj != null ? obj : Collections.emptySet();
    }

    @NotNull
    public static <V> List<V> notNull(@Nullable List<V> obj) {
        return obj != null ? obj : Collections.emptyList();
    }
}
