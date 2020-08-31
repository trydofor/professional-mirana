package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * @author trydofor
 * @since 2019-10-15
 */
public class Nulls {

    public static final boolean Int01 = false;
    public static final byte Int08 = 0;
    public static final char Int16 = 0;
    public static final int Int32 = 0;
    public static final long Int64 = 0L;
    public static final float Flt32 = 0.0F;
    public static final double Flt64 = 0.0D;

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

    public static boolean asNull(String v) {
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
}
