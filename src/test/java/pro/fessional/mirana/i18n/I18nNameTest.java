package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.Null;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class I18nNameTest {

    @Test
    public void testConstructorAndGetter() {
        String testName = "testName";
        I18nName i18nName = new I18nName(testName);
        assertEquals(testName, i18nName.getName());
        assertEquals(testName, i18nName.getI18nCode());
    }

    @Test
    public void testEqualsAndHashCode() {
        String name1 = "name1";
        String name2 = "name2";

        I18nName i18nName1 = new I18nName(name1);
        I18nName i18nName2 = new I18nName(name1);
        I18nName i18nName3 = new I18nName(name2);

        assertEquals(i18nName1, i18nName2);
        assertEquals(i18nName1.hashCode(), i18nName2.hashCode());

        assertNotEquals(i18nName1, i18nName3);
        assertNotEquals(i18nName1.hashCode(), i18nName3.hashCode());
    }

    @Test
    public void testToString() {
        String testName = "testToString";
        I18nName i18nName = new I18nName(testName);
        assertEquals(testName, i18nName.toString());
    }

    @Test
    public void testOfMethod() {
        String testName = "testOfMethod";
        I18nName i18nName = I18nName.of(testName);
        assertEquals(testName, i18nName.getName());
        assertEquals(testName, i18nName.getI18nCode());
    }

    @Test
    public void testI18nAwareDefaultMethods() {
        String testName = "testI18nAware";
        I18nName i18nName = new I18nName(testName);

        assertNull(i18nName.getI18nHint());
        assertNull(i18nName.getI18nArgs());

        I18nString i18nString = i18nName.toI18nString();
        assertEquals(testName, i18nString.getI18nCode());
        assertEquals("", i18nString.getI18nHint());
        assertArrayEquals(Null.Objects, i18nString.getI18nArgs());

        i18nString = i18nName.toI18nString("hint");
        assertEquals(testName, i18nString.getI18nCode());
        assertEquals("hint", i18nString.getI18nHint());
        assertArrayEquals(Null.Objects, i18nString.getI18nArgs());

        i18nString = i18nName.toI18nString(null, "arg1");
        assertEquals(testName, i18nString.getI18nCode());
        assertEquals("", i18nString.getI18nHint());
        assertArrayEquals(new Object[]{ "arg1" }, i18nString.getI18nArgs());

        assertEquals(testName, i18nName.toString(Locale.getDefault()));

        I18nAware.I18nSource source = (code, args, hint, lang) -> "formatted_message";
        assertEquals("formatted_message", i18nName.toString(Locale.getDefault(), source));
    }
}