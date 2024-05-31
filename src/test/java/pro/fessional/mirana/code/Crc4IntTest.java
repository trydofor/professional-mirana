package pro.fessional.mirana.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import static pro.fessional.mirana.code.Crc4Int.MAX_SEQ;
import static pro.fessional.mirana.code.Crc4Int.MIN_SEQ;
import static pro.fessional.mirana.code.Crc4Int.decode;
import static pro.fessional.mirana.code.Crc4Int.encode;

/**
 * @author trydofor
 * @since 2021-05-11
 */
class Crc4IntTest {

    @Test
    public void testEncode() {
        for (int i = MIN_SEQ; i < MAX_SEQ; i++) {
            int num = encode(i);
            int seq = decode(num);
            if (i != seq) {
                Assertions.fail(i + ":" + seq + ":" + num);
            }
            if (i < 10000) Testing.printf("%04d\t%06d\n", i, num);
        }
    }

    /**
     * 1=6
     * 2=6
     * 3=6
     * 4=6
     * 5=7
     * 6=8
     * 7=9
     * 8=10
     * 9=11
     */
    @Test
    @Disabled
    public void lenMaxTest() {
        for (int i = 1; i < 10; i++) {
            lenMax(i);
        }
    }

    private void lenMax(int b) {
        int n = (int) Math.pow(10, b);
        int m = 0;
        for (int i = n / 10; i < n; i++) {
            int e = encode(i);
            final String s = String.valueOf(e);
            final int l = s.length();
            if (l > m) {
                m = l;
            }
        }
        Testing.printf("%d=%d\n", b, m);
    }
}
