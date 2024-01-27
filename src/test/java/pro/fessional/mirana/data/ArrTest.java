package pro.fessional.mirana.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2024-01-27
 */
class ArrTest {

    @Test
    void ofBoolean() {
        boolean[] b0 = null;
        boolean[] b1 = Arr.of(b0);
        boolean[] b2 = Arr.of(true, false);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertTrue(b2[0]);
        Assertions.assertFalse(b2[1]);
    }

    @Test
    void setBoolean() {
        boolean v1 = true;
        boolean v2 = false;
        boolean[] b1 = {v1, v2};
        boolean[] b2 = Arr.set(b1, 0, v2);
        boolean[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }

    @Test
    void ofByte() {
        byte v1 = 'x';
        byte v2 = 'y';
        byte[] b0 = null;
        byte[] b1 = Arr.of(b0);
        byte[] b2 = Arr.of(v1, v2);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setByte() {
        byte v1 = 'x';
        byte v2 = 'y';
        byte[] b1 = {v1, v2};
        byte[] b2 = Arr.set(b1, 0, v2);
        byte[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }

    @Test
    void ofShort() {
        short v1 = 'x';
        short v2 = 'y';
        short[] b0 = null;
        short[] b1 = Arr.of(b0);
        short[] b2 = Arr.of(v1, v2);
        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setShort() {
        short v1 = 'x';
        short v2 = 'y';
        short[] b1 = {v1, v2};
        short[] b2 = Arr.set(b1, 0, v2);
        short[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }


    @Test
    void ofChar() {
        char v1 = 'x';
        char v2 = 'y';
        char[] b0 = null;
        char[] b1 = Arr.of(b0);
        char[] b2 = Arr.of(v1, v2);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setChar() {
        char v1 = 'x';
        char v2 = 'y';
        char[] b1 = {v1, v2};
        char[] b2 = Arr.set(b1, 0, v2);
        char[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }


    @Test
    void ofInt() {
        int v1 = 'x';
        int v2 = 'y';
        int[] b0 = null;
        int[] b1 = Arr.of(b0);
        int[] b2 = Arr.of(v1, v2);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setInt() {
        int v1 = 'x';
        int v2 = 'y';
        int[] b1 = {v1, v2};
        int[] b2 = Arr.set(b1, 0, v2);
        int[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }

    @Test
    void ofLong() {
        long v1 = 'x';
        long v2 = 'y';
        long[] b0 = null;
        long[] b1 = Arr.of(b0);
        long[] b2 = Arr.of(v1, v2);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setLong() {
        long v1 = 'x';
        long v2 = 'y';
        long[] b1 = {v1, v2};
        long[] b2 = Arr.set(b1, 0, v2);
        long[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }

    @Test
    void ofFloat() {
        float v1 = 'x';
        float v2 = 'y';
        float[] b0 = null;
        float[] b1 = Arr.of(b0);
        float[] b2 = Arr.of(v1, v2);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setFloat() {
        float v1 = 'x';
        float v2 = 'y';
        float[] b1 = {v1, v2};
        float[] b2 = Arr.set(b1, 0, v2);
        float[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }

    @Test
    void ofDouble() {
        double v1 = 'x';
        double v2 = 'y';
        double[] b0 = null;
        double[] b1 = Arr.of(b0);
        double[] b2 = Arr.of(v1, v2);

        Assertions.assertNotNull(b1);
        Assertions.assertEquals(0, b1.length);

        Assertions.assertEquals(2, b2.length);
        Assertions.assertEquals(v1, b2[0]);
        Assertions.assertEquals(v2, b2[1]);
    }

    @Test
    void setDouble() {
        double v1 = 'x';
        double v2 = 'y';
        double[] b1 = {v1, v2};
        double[] b2 = Arr.set(b1, 0, v2);
        double[] b3 = Arr.set(b1, 2, v2);

        Assertions.assertSame(b1, b2);
        Assertions.assertEquals(v2, b2[0]);

        Assertions.assertNotSame(b1, b3);
        Assertions.assertEquals(3, b3.length);
        Assertions.assertEquals(v2, b3[2]);
    }

}