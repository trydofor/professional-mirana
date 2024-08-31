package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author trydofor
 * @since 2021-01-19
 */
class I18nStringTest {

    @Test
    void test() {
        I18nString s1 = new I18nString("200");
        assertEquals("200", s1.getCode());
        assertEquals("200", s1.getI18nCode());
        assertEquals("", s1.getHint());
        assertEquals("", s1.getI18nHint());
        assertEquals(0, s1.getArgs().length);
        assertEquals(0, s1.getI18nArgs().length);
        assertNull(s1.getI18n());
        s1.setI18n("i18n");
        assertEquals("i18n", s1.getI18n());
        assertFalse(s1.isEmpty());
        I18nString s2 = new I18nString("200", "name");
        assertEquals(s1, s2);

        Testing.println(s1);
        Testing.println(s2);

        I18nString s3 = new I18nString("200", "{0} is ok", "name");
        final String s = s3.toString(Locale.ENGLISH);
        assertEquals("name is ok", s);
        assertEquals("name is ok", s);
    }
}
