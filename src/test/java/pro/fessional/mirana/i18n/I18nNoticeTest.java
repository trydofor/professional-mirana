package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class I18nNoticeTest {

    @Test
    void testI18nNoticeSettersAndGetters() {
        I18nNotice notice = new I18nNotice();
        notice.setType("BindingValidation");
        notice.setTarget("city");

        assertEquals("BindingValidation", notice.getType());
        assertEquals("city", notice.getTarget());
    }

    @Test
    void testI18nNoticeEquals() {
        I18nNotice notice1 = new I18nNotice();
        notice1.setType("BindingValidation");
        notice1.setTarget("city");

        I18nNotice notice2 = new I18nNotice();
        notice2.setType("BindingValidation");
        notice2.setTarget("city");

        I18nNotice notice3 = new I18nNotice();
        notice3.setType("IllegalArgument");
        notice3.setTarget("tab1.zipcode");

        assertEquals(notice1, notice2);
        assertNotEquals(notice1, notice3);
    }

    @Test
    void testI18nNoticeHashCode() {
        I18nNotice notice1 = new I18nNotice();
        notice1.setType("BindingValidation");
        notice1.setTarget("city");

        I18nNotice notice2 = new I18nNotice();
        notice2.setType("BindingValidation");
        notice2.setTarget("city");

        assertEquals(notice1.hashCode(), notice2.hashCode());
    }

    @Test
    void testI18nNoticeToString() {
        I18nNotice notice = new I18nNotice();
        notice.setType("BindingValidation");
        notice.setTarget("city");
        notice.message = "default message";
        notice.i18nCode = "i18n_code";
        notice.i18nArgs = new Object[]{ "arg1", "arg2" };

        String expected = "I18nNotice{type='BindingValidation', target='city', message='default message', i18nCode='i18n_code', i18nArgs=[arg1, arg2]} ";
        assertEquals(expected, notice.toString());
    }

    @Test
    void testI18nAwareDefaultMethods() {
        I18nNotice notice = new I18nNotice();
        notice.i18nCode = "test_code";
        notice.message = "test_hint";
        notice.i18nArgs = new Object[]{ "test_arg" };

        assertEquals("test_code", notice.getI18nCode());
        assertEquals("test_hint", notice.getI18nHint());
        assertArrayEquals(new Object[]{ "test_arg" }, notice.getI18nArgs());

        I18nString i18nString = notice.toI18nString();
        assertEquals("test_code", i18nString.getI18nCode());
        assertEquals("test_hint", i18nString.getI18nHint());
        assertArrayEquals(new Object[]{ "test_arg" }, i18nString.getI18nArgs());

        i18nString = notice.toI18nString("new_hint");
        assertEquals("test_code", i18nString.getI18nCode());
        assertEquals("new_hint", i18nString.getI18nHint());
        assertArrayEquals(new Object[]{ "test_arg" }, i18nString.getI18nArgs());

        i18nString = notice.toI18nString(null, "new_arg");
        assertEquals("test_code", i18nString.getI18nCode());
        assertEquals("test_hint", i18nString.getI18nHint());
        assertArrayEquals(new Object[]{ "new_arg" }, i18nString.getI18nArgs());

        assertEquals("test_hint", notice.toString(Locale.getDefault()));

        I18nAware.I18nSource source = (code, args, hint, lang) -> "formatted_message";
        assertEquals("formatted_message", notice.toString(Locale.getDefault(), source));
    }
}