package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author trydofor
 * @since 2025-01-14
 */
class I18nMessageTest {
    @Test
    public void testSetAndGet() {
        Object[] args = {1, "arg2"};
        I18nMessage m1 = new I18nMessage();
        m1.setMessage("Test message");
        m1.setI18nCode("test.code");
        m1.setI18nArgs(args);
        assertEquals("Test message", m1.getMessage());
        assertEquals("Test message", m1.toString(Locale.US));
        assertEquals("test.code", m1.getI18nCode());
        assertArrayEquals(args, m1.getI18nArgs());

        I18nMessage m2 = new I18nMessage();
        m2.setMessageBy("Test message", "test.code");
        assertEquals("Test message", m2.getMessage());
        assertEquals("Test message", m2.toString(Locale.US));
        assertEquals("test.code", m2.getI18nCode());

        I18nMessage m3 = new I18nMessage();
        m3.setMessageBy("Test message", "test.code", args);
        assertEquals("Test message", m3.getMessage());
        assertEquals("Test message", m3.toString(Locale.US));
        assertEquals("test.code", m3.getI18nCode());
        assertArrayEquals(args, m3.getI18nArgs());
    }

    @Test
    public void testSetMessageWithI18nAware() {
        I18nMessage message = new I18nMessage();
        I18nAware aware = new I18nAware() {
            @Override
            public String getI18nCode() {
                return "code.from.aware";
            }

            @Override
            public String getI18nHint() {
                return "hint.from.aware";
            }

            @Override
            public Object[] getI18nArgs() {
                return new Object[]{"arg1", 2};
            }
        };

        message.setMessageBy(aware);

        assertEquals("hint.from.aware", message.getMessage());
        assertEquals("code.from.aware", message.getI18nCode());
        assertArrayEquals(new Object[]{"arg1", 2}, message.getI18nArgs());

        message.setMessageBy("", aware);
        assertEquals("", message.getMessage());
        assertEquals("code.from.aware", message.getI18nCode());
        assertArrayEquals(new Object[]{"arg1", 2}, message.getI18nArgs());
    }

    @Test
    public void testSetMessageWithLocale() {
        I18nMessage message = new I18nMessage();
        message.setMessageBy(Locale.US);
        assertNull(message.getMessage());

        message.setMessage("{0} is ok")
            .setI18nCode("test.code")
            .setI18nArgs("name");
        message.setMessageBy(Locale.US);
        assertEquals("name is ok", message.getMessage());
    }

    @Test
    public void testSetMessageWithLocaleAndSource() {
        I18nMessage message = new I18nMessage();
        I18nAware.I18nSource source = (code, args, hint, lang) -> "test.code".equals(code)
            ? "Message from source: " + code
            : hint;

        message.setMessageBy(Locale.US, source);
        assertNull(message.getMessage());

        message.setMessage("message");
        message.setMessageBy(Locale.US, source);
        assertEquals("message", message.getMessage());

        message.setI18nCode("test.code");
        message.setMessageBy(Locale.US, source);
        assertEquals("Message from source: test.code", message.getMessage());

        message.setMessage("message");
        message.setI18nCode("test.code2");
        message.setMessageBy(Locale.US, source);
        assertEquals("message", message.getMessage());

        message.setMessage(null);
        message.setMessageBy(Locale.US, source);
        assertEquals("test.code2", message.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        I18nMessage message1 = new I18nMessage();
        I18nMessage message2 = new I18nMessage();

        message1.setMessage("Test").setI18nCode("code").setI18nArgs("arg1");
        message2.setMessage("Test").setI18nCode("code").setI18nArgs("arg1");

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    public void testToString() {
        I18nMessage message = new I18nMessage();
        message.setMessage("Test").setI18nCode("code").setI18nArgs("arg1", 2);

        String expected = "I18nMessage{message='Test', i18nCode='code', i18nArgs=[arg1, 2]}";
        assertEquals(expected, message.toString());
    }

    @Test
    public void testChainingMethods() {
        I18nMessage message = new I18nMessage()
            .setMessage("Chained message")
            .setI18nCode("chained.code")
            .setI18nArgs("arg1", "arg2");

        assertEquals("Chained message", message.getMessage());
        assertEquals("chained.code", message.getI18nCode());
        assertArrayEquals(new Object[]{"arg1", "arg2"}, message.getI18nArgs());
    }
}