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
}