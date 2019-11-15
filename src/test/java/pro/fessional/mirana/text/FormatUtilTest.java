package pro.fessional.mirana.text;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class FormatUtilTest {

    @Test
    public void format() {
        String f = "%s-%s-%s%%";
        assertEquals("1--%", FormatUtil.format(f, "1"));
        assertEquals("1--%", FormatUtil.format(f, "1",null));
    }

    @Test
    public void leftFix() {
        assertEquals("0123456789", FormatUtil.leftFix("123456789", 10, '0'));
        assertEquals("1234567890", FormatUtil.leftFix("1234567890", 10, '0'));
        assertEquals("9012345678", FormatUtil.leftFix("123456789012345678", 10, '0'));
    }
    @Test
    public void rightFix() {
        assertEquals("1234567890", FormatUtil.rightFix("123456789", 10, '0'));
        assertEquals("1234567890", FormatUtil.rightFix("1234567890", 10, '0'));
        assertEquals("1234567890", FormatUtil.rightFix("123456789012345678", 10, '0'));
    }
}