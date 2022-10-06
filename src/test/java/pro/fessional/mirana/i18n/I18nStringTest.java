package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-19
 */
class I18nStringTest {

    @Test
    void test() {
        I18nString is = new I18nString("200", "{0} is ok", "name");
        final String s = is.toString(Locale.ENGLISH);
        assertEquals("name is ok", s);
    }
}
