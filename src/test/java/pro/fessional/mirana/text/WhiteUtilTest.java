package pro.fessional.mirana.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author trydofor
 * @since 2023-10-27
 */
class WhiteUtilTest {

    @Test
    void trim() {
        Assertions.assertEquals("", WhiteUtil.trim(" \t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.trim("a \t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.trim(" a\t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.trim(" \ta\n\r "));
        Assertions.assertEquals("a", WhiteUtil.trim(" \t\na\r "));
        Assertions.assertEquals("a", WhiteUtil.trim(" \t\n\ra "));
        Assertions.assertEquals("a", WhiteUtil.trim(" \t\n\r a"));
        Assertions.assertEquals("a\na", WhiteUtil.trim(" \ta\na\r "));
        Assertions.assertEquals("a\na", WhiteUtil.trim(" \ta\na\r "));

        Assertions.assertEquals("", WhiteUtil.trim(" \t\n\r ", ' ', '\t', '\n', '\r'));
        Assertions.assertEquals("", WhiteUtil.trim("a \ta\na\r a", ' ', '\t', 'a', '\n', '\r'));
        Assertions.assertEquals("a\na", WhiteUtil.trim(" \ta\na\r ", ' ', '\t', '\n', '\r'));
    }

    @Test
    void mess() {
        Assertions.assertTrue(WhiteUtil.isWhiteSpace('\t'));
        Assertions.assertTrue(WhiteUtil.isAllWhite(" \t\n\r "));
        Assertions.assertEquals("", WhiteUtil.delete(" \t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.delete(" \ta\n\r "));
        Assertions.assertTrue(WhiteUtil.equalsWithDeleted(" \ta\n\r ", "a"));
        Assertions.assertTrue(WhiteUtil.equalsWithSpaced(" \ta\n\r ", " a "));
        Assertions.assertEquals(Arrays.asList("a", "b"), WhiteUtil.lines("a\nb"));
    }

    @Test
    void space() {
        Assertions.assertEquals("", WhiteUtil.space(" \t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.space("a \t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.space(" a\t\n\r "));
        Assertions.assertEquals("a", WhiteUtil.space(" \ta\n\r "));
        Assertions.assertEquals("a", WhiteUtil.space(" \t\na\r "));
        Assertions.assertEquals("a", WhiteUtil.space(" \t\n\ra "));
        Assertions.assertEquals("a", WhiteUtil.space(" \t\n\r a"));
        Assertions.assertEquals("a a", WhiteUtil.space(" \ta\na\r "));
        Assertions.assertEquals("a a", WhiteUtil.space(" \ta \na\r "));
        Assertions.assertEquals("a a", WhiteUtil.space(" \ta \n a\r "));
        Assertions.assertEquals("a a", WhiteUtil.space("a \t \n a\r "));
        Assertions.assertEquals("a a", WhiteUtil.space("a \t \n \r a"));
    }


    @Test
    void white() {
        Assertions.assertFalse(WhiteUtil.isWhite((byte) '1'));

        Assertions.assertTrue(WhiteUtil.isWhite((byte) '\t'));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) '\r'));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) '\n'));

        // UTF-8 BOM: EF BB BF
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xEF));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xBB));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xBF));
        // UTF-16 BE BOM: FE FF
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFE));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFF));
        // UTF-16 LE BOM: FF FE
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFF));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFE));
        // UTF-32 BE BOM: 00 00 FE FF
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0x00));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFE));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFF));
        // UTF-32 LE BOM: FF FE 00 00
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFF));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0xFE));
        Assertions.assertTrue(WhiteUtil.isWhite((byte) 0x00));
    }

    @Test
    void firstLast() {
        String str = " 0\r\n";
        Assertions.assertEquals((byte)'0', WhiteUtil.firstNonWhite(str));
        Assertions.assertEquals((byte)'0', WhiteUtil.lastNonWhite(str));

        byte[] bs = str.getBytes();
        Assertions.assertEquals((byte)'0', WhiteUtil.firstNonWhite(bs));
        Assertions.assertEquals((byte)'0', WhiteUtil.lastNonWhite(bs));

        char[] cs = str.toCharArray();
        Assertions.assertEquals((byte)'0', WhiteUtil.firstNonWhite(cs));
        Assertions.assertEquals((byte)'0', WhiteUtil.lastNonWhite(cs));

    }
}