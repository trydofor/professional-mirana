package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;
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
            Testing.println(h3);
            String s3 = new String(Bytes.hex(h3));
            assertEquals(s1, s3);
        }
    }

    @Test
    void hex() {
        Assertions.assertTrue(Bytes.isHex("0123456789abcdef",16));
        Assertions.assertTrue(Bytes.isHex("0123456789ABCDEF",16));
        Assertions.assertTrue(Bytes.isHex("0123456789abcdef"));
        Assertions.assertTrue(Bytes.isHex("0123456789ABCDEF"));

        Assertions.assertFalse(Bytes.isHex("0123456789abcdef",15));
        Assertions.assertFalse(Bytes.isHex(""));
        Assertions.assertFalse(Bytes.isHex("",15));
        Assertions.assertFalse(Bytes.isHex(null));
        Assertions.assertFalse(Bytes.isHex(null,15));
        Assertions.assertFalse(Bytes.isHex("1 2 3"));
        Assertions.assertFalse(Bytes.isHex("1 2 3",3));
        Assertions.assertFalse(Bytes.isHex("1 2 3",5));

        Assertions.assertTrue(Bytes.asHex("0123456789abcdef",16));
        Assertions.assertTrue(Bytes.asHex("0123456789ABCDEF",16));
        Assertions.assertTrue(Bytes.asHex("0123456789abcdef"));
        Assertions.assertTrue(Bytes.asHex("0123456789ABCDEF"));

        Assertions.assertFalse(Bytes.asHex("0123456789abcdef",15));
        Assertions.assertFalse(Bytes.asHex(""));
        Assertions.assertFalse(Bytes.asHex("",15));
        Assertions.assertFalse(Bytes.asHex(null));
        Assertions.assertFalse(Bytes.asHex(null,15));
        Assertions.assertTrue(Bytes.asHex("1 2 3"));
        Assertions.assertTrue(Bytes.asHex("1 2 3",3));
        Assertions.assertFalse(Bytes.asHex("1 2 3",5));
    }

    @Test
    void empty() {
        String s1 = "";
        final String h1 = Bytes.hex(s1.getBytes());
        final byte[] b2 = Bytes.hex(h1);
        String s2 = new String(b2);
        assertEquals(s1, s2);
        final String h3 = h1.replaceAll("(\\d)", "$1 ");
        Testing.println(h3);
        String s3 = new String(Bytes.hex(h3));
        assertEquals(s1, s3);
    }

    @Test
    void print() {
        final byte[] bytes = RandCode.human(20).getBytes();
        Testing.println(Bytes.hex(bytes, true));
        Testing.println(Bytes.hex(bytes, false));
    }
}
