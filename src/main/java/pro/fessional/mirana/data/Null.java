package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

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

    @NotNull
    public static final Enum<?> Enm = Eu.Null;
    @NotNull
    public static final Class<?> Clz = Void.class;

    //
    @NotNull
    public static final String Str = "";
    @NotNull
    public static final boolean[] Bools = new boolean[0];
    @NotNull
    public static final byte[] Bytes = new byte[0];
    @NotNull
    public static final char[] Chars = new char[0];
    @NotNull
    public static final short[] Shorts = new short[0];
    @NotNull
    public static final int[] Ints = new int[0];
    @NotNull
    public static final long[] Longs = new long[0];
    @NotNull
    public static final float[] Floats = new float[0];
    @NotNull
    public static final double[] Doubles = new double[0];
    @NotNull
    public static final Object[] Objects = new Object[0];
    @NotNull
    public static final BigDecimal[] BigDecimals = new BigDecimal[0];

    @NotNull
    public static final Boolean[] BoolArr = new Boolean[0];
    @NotNull
    public static final Byte[] ByteArr = new Byte[0];
    @NotNull
    public static final Short[] ShortArr = new Short[0];
    @NotNull
    public static final Integer[] IntArr = new Integer[0];
    @NotNull
    public static final Long[] LongArr = new Long[0];
    @NotNull
    public static final Float[] FloatArr = new Float[0];
    @NotNull
    public static final Double[] DoubleArr = new Double[0];
    @NotNull
    public static final String[] StrArr = new String[0];

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

    @NotNull
    public static Boolean notNull(Boolean v) {
        return v == null ? Int01 : v;
    }

    @NotNull
    public static Byte notNull(Byte v) {
        return v == null ? Int08 : v;
    }

    @NotNull
    public static Character notNull(Character v) {
        return v == null ? Int16 : v;
    }

    @NotNull
    public static Short notNull(Short v) {
        return v == null ? (short) Int16 : v;
    }

    @NotNull
    public static Integer notNull(Integer v) {
        return v == null ? Int32 : v;
    }

    @NotNull
    public static Long notNull(Long v) {
        return v == null ? Int64 : v;
    }

    @NotNull
    public static Float notNull(Float v) {
        return v == null ? Flt32 : v;
    }

    @NotNull
    public static Double notNull(Double v) {
        return v == null ? Flt64 : v;
    }

    @NotNull
    public static String notNull(CharSequence v) {
        return v == null ? Str : v.toString();
    }

    @NotNull
    public static boolean[] notNull(boolean[] v) {
        return v == null ? Bools : v;
    }

    @NotNull
    public static byte[] notNull(byte[] v) {
        return v == null ? Bytes : v;
    }

    @NotNull
    public static char[] notNull(char[] v) {
        return v == null ? Chars : v;
    }

    @NotNull
    public static short[] notNull(short[] v) {
        return v == null ? Shorts : v;
    }

    @NotNull
    public static int[] notNull(int[] v) {
        return v == null ? Ints : v;
    }

    @NotNull
    public static long[] notNull(long[] v) {
        return v == null ? Longs : v;
    }

    @NotNull
    public static float[] notNull(float[] v) {
        return v == null ? Floats : v;
    }

    @NotNull
    public static double[] notNull(double[] v) {
        return v == null ? Doubles : v;
    }

    @NotNull
    public static Object[] notNull(Object[] v) {
        return v == null ? Objects : v;
    }
}
