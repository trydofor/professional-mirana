package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.code.RandCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-26
 */
class BytesTest {

    @Test
    void normal() {
        for (int i = 0; i < 10; i++) {
            String s1 = RandCode.human(20);
            final String h1 = Bytes.hex(s1.getBytes());
            final byte[] b2 = Bytes.hex(h1);
            String s2 = new String(b2);
            assertEquals(s1, s2);
            final String h3 = h1.replaceAll("(\\d)", "$1 ");
            System.out.println(h3);
            String s3 = new String(Bytes.hex(h3));
            assertEquals(s1, s3);
        }
    }

    @Test
    void empty() {
        String s1 = "";
        final String h1 = Bytes.hex(s1.getBytes());
        final byte[] b2 = Bytes.hex(h1);
        String s2 = new String(b2);
        assertEquals(s1, s2);
        final String h3 = h1.replaceAll("(\\d)", "$1 ");
        System.out.println(h3);
        String s3 = new String(Bytes.hex(h3));
        assertEquals(s1, s3);
    }

    @Test
    void print() {
        final byte[] bytes = RandCode.human(20).getBytes();
        System.out.println(Bytes.hex(bytes, true));
        System.out.println(Bytes.hex(bytes, false));
    }
}
