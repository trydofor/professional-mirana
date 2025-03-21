package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-19
 */
class I18nStringTest {

    @Test
    void testDefaultConstructor() {
        I18nString i18nString = new I18nString("testCode");
        assertEquals("testCode", i18nString.getI18nCode());
        assertArrayEquals(new Object[]{}, i18nString.getI18nArgs());
        assertEquals("", i18nString.getI18nHint());
    }

    @Test
    void testConstructorWithHint() {
        I18nString i18nString = new I18nString("testCode", "testHint");
        assertEquals("testCode", i18nString.getI18nCode());
        assertArrayEquals(new Object[]{}, i18nString.getI18nArgs());
        assertEquals("testHint", i18nString.getI18nHint());
    }

    @Test
    void testConstructorWithArgs() {
        I18nString i18nString = new I18nString("testCode", "testHint", "arg1", "arg2");
        assertEquals("testCode", i18nString.getI18nCode());
        assertArrayEquals(new Object[]{ "arg1", "arg2" }, i18nString.getI18nArgs());
        assertEquals("testHint", i18nString.getI18nHint());
    }

    @Test
    void testSetI18nHintWithString() {
        I18nString i18nString = new I18nString("testCode");
        i18nString.setI18nHint("newHint");
        assertEquals("newHint", i18nString.getI18nHint());
    }

    @Test
    void testIsEmpty() {
        I18nString i18nString1 = new I18nString("");
        assertTrue(i18nString1.isEmpty());
        I18nString i18nString2 = new I18nString("testCode");
        assertFalse(i18nString2.isEmpty());
    }

    @Test
    void testEquals1() {
        I18nString i18nString1 = new I18nString("testCode", "testHint", "arg1", "arg2");
        I18nString i18nString2 = new I18nString("testCode", "testHint", "arg1", "arg2");
        I18nString i18nString3 = new I18nString("testCode", "testHint", "arg1");
        assertEquals(i18nString1, i18nString2);
        assertNotEquals(i18nString1, i18nString3);
    }

    @Test
    void testHashCode() {
        I18nString i18nString1 = new I18nString("testCode", "testHint", "arg1", "arg2");
        I18nString i18nString2 = new I18nString("testCode", "testHint", "arg1", "arg2");
        assertEquals(i18nString1.hashCode(), i18nString2.hashCode());
    }

    @Test
    void testOf() {
        I18nString i18nString = I18nString.of("testString");
        assertEquals("testString", i18nString.getI18nCode());
        assertArrayEquals(new Object[]{}, i18nString.getI18nArgs());
        assertEquals("", i18nString.getI18nHint());
    }

    @Test
    void testEquals() {
        I18nString s1 = new I18nString("200");
        assertEquals("200", s1.getI18nCode());
        assertEquals("200", s1.getI18nCode());
        assertEquals("", s1.getI18nHint());
        assertEquals("", s1.getI18nHint());
        assertEquals(0, s1.getI18nArgs().length);
        assertEquals(0, s1.getI18nArgs().length);
        assertFalse(s1.isEmpty());
        I18nString s2 = new I18nString("200", "name");
        assertEquals(s1, s2);

        Testing.println(s1);
        Testing.println(s2);
    }

    @Test
    void testString() {
        I18nString s1 = new I18nString("200", "{0} is ok", "name");
        assertEquals("name is ok", s1.toString(Locale.ENGLISH));

        I18nString s2 = new I18nString("200", "{0} is ok", I18nString.of("name"));
        I18nAware.I18nSource i18nSource = (code, args, hint, lang) -> {
            if (code.equals("name")) return "trydofor";
            return new MessageFormat(hint, lang).format(args);
        };
        final String str2 = s2.toString(Locale.ENGLISH, i18nSource);
        assertEquals("trydofor is ok", str2);
        s2.applyLocale(Locale.ENGLISH, i18nSource);
        assertEquals(str2, s2.getI18nCache());
        assertEquals(str2, s2.toString());

        I18nString s3 = s1.toI18nString();
        assertEquals(s1, s3);

        I18nString s4 = s1.toI18nString(null);
        assertEquals(s1, s4);

        I18nString s5 = s1.toI18nString(null, "trydofor");
        assertEquals("trydofor is ok", s5.toString());

        I18nString s6 = s1.toI18nString("{0} is fine");
        assertEquals("name is fine", s6.toString());

        I18nString s7 = s1.toI18nString("{0} is fine", "trydofor");
        assertEquals("trydofor is fine", s7.toString());
    }
}
