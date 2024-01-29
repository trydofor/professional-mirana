package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-08-31
 */
public class BoxedCastUtilTest {

    @Test
    public void boolOr() {
        assertTrue(BoxedCastUtil.orTrue(null));
        assertTrue(BoxedCastUtil.orTrue(true));
        assertFalse(BoxedCastUtil.orTrue(false));

        assertFalse(BoxedCastUtil.orFalse(null));
        assertTrue(BoxedCastUtil.orFalse(true));
        assertFalse(BoxedCastUtil.orFalse(false));
    }

    @Test
    public void charOr() {
        Character vn = null;
        Character v0 = 0;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void byteOr() {
        Byte vn = null;
        Byte v0 = 0;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void shortOr() {
        Short vn = null;
        Short v0 = 0;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void intOr() {
        Integer vn = null;
        Integer v0 = 0;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void longOr() {
        Long vn = null;
        Long v0 = 0L;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void doubleOr() {
        Double vn = null;
        Double v0 = 0D;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void floatOr() {
        Float vn = null;
        Float v0 = 0F;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void decimalOr() {
        BigDecimal vn = null;
        BigDecimal v0 = BigDecimal.ZERO;
        assertEquals(v0, BoxedCastUtil.orZero(vn));
        assertEquals(v0, BoxedCastUtil.orElse(vn, v0));
        assertEquals(v0, BoxedCastUtil.orElse(v0, v0));
    }

    @Test
    public void bools() {
        Boolean v1 = true;
        Boolean v2 = false;
        Object obj = new boolean[]{v1, v2};
        List<Boolean> lst = Arrays.asList(v1, v2);
        boolean[] arr = BoxedCastUtil.bools(lst);

        assertEquals(0, BoxedCastUtil.bools(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void chars() {
        Character v1 = '1';
        Character v2 = '0';
        Object obj = new char[]{v1, v2};
        List<Character> lst = Arrays.asList(v1, v2);
        char[] arr = BoxedCastUtil.chars(lst);

        assertEquals(0, BoxedCastUtil.chars(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void bytes() {
        Byte v1 = '1';
        Byte v2 = '0';
        Object obj = new byte[]{v1, v2};
        List<Byte> lst = Arrays.asList(v1, v2);
        byte[] arr = BoxedCastUtil.bytes(lst);

        assertEquals(0, BoxedCastUtil.bytes(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void shorts() {
        Short v1 = '1';
        Short v2 = '0';
        Object obj = new short[]{v1, v2};
        List<Short> lst = Arrays.asList(v1, v2);
        short[] arr = BoxedCastUtil.shorts(lst);

        assertEquals(0, BoxedCastUtil.shorts(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void ints() {
        Integer v1 = 1;
        Integer v2 = 0;
        Object obj = new int[]{v1, v2};
        List<Integer> lst = Arrays.asList(v1, v2);
        int[] arr = BoxedCastUtil.ints(lst);

        assertEquals(0, BoxedCastUtil.ints(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void longs() {
        Long v1 = 1L;
        Long v2 = 0L;
        Object obj = new long[]{v1, v2};
        List<Long> lst = Arrays.asList(v1, v2);
        long[] arr = BoxedCastUtil.longs(lst);

        assertEquals(0, BoxedCastUtil.longs(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void floats() {
        Float v1 = 1F;
        Float v2 = 0F;
        Object obj = new float[]{v1, v2};
        List<Float> lst = Arrays.asList(v1, v2);
        float[] arr = BoxedCastUtil.floats(lst);

        assertEquals(0, BoxedCastUtil.floats(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void doubles() {
        Double v1 = 1D;
        Double v2 = 0D;
        Object obj = new double[]{v1, v2};
        List<Double> lst = Arrays.asList(v1, v2);
        double[] arr = BoxedCastUtil.doubles(lst);

        assertEquals(0, BoxedCastUtil.doubles(null).length);
        assertEquals(2, arr.length);
        assertEquals(v1, arr[0]);
        assertEquals(v2, arr[1]);
        assertEquals(lst, BoxedCastUtil.list(obj));
        assertEquals(lst, BoxedCastUtil.list(arr));
    }

    @Test
    public void list() {
        List<Integer> lst1 = Arrays.asList(1, 2);
        assertEquals(lst1, BoxedCastUtil.list(lst1));
        Integer[] arr = new Integer[]{1, 2};
        assertEquals(lst1, BoxedCastUtil.list(arr));
    }
}