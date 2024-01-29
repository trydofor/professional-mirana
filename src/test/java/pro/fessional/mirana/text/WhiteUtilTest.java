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
}