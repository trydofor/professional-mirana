package pro.fessional.mirana.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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