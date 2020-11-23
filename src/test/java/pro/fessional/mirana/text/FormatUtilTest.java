package pro.fessional.mirana.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class FormatUtilTest {


    @Test
    public void count() {
        FormatUtil.V all = (fmt, idx, str, sub) -> {
            System.out.printf("%d,%d,%s\n", idx, sub, str);
            return true;
        };

        String ptn = "%s-%s-%s%%%%";
        System.out.println(ptn);
        int[] r1 = FormatUtil.count(all, ptn, "%", "%%", "%%%", "%%%%");
        assertEquals(7, r1[0]);
        assertEquals(2, r1[1]);
        assertEquals(1, r1[2]);
        assertEquals(1, r1[3]);

        FormatUtil.V fst = (fmt, idx, str, sub) -> {
            System.out.printf("%d,%d,%s\n", idx, sub, str);
            return false;
        };

        int[] r2 = FormatUtil.count(fst, ptn, "%", "%%", "%%%", "%%%%");
        assertEquals(1, r2[0]);
        assertEquals(1, r2[1]);
        assertEquals(1, r2[2]);
        assertEquals(1, r2[3]);
    }

    @Test
    public void logback() {
        {
            String pattern = null;
            Object[] args = null;
            assertEquals("", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "a {} {} b";
            Object[] args = null;
            assertEquals("a null null b", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "a {{}} {{}} b";
            Object[] args = null;
            assertEquals("a {null} {null} b", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "a {{}} {b} b";
            Object[] args = null;
            assertEquals("a {null} {b} b", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c {a} {b} d";
            Object[] args = null;
            assertEquals("c {a} {b} d", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c \\{} \\{} d";
            Object[] args = null;
            assertEquals("c {} {} d", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c \\{\\} \\{\\} d";
            Object[] args = null;
            assertEquals("c {} {} d", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c \\\\} \\\\} d";
            Object[] args = null;
            assertEquals("c \\} \\} d", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c \\";
            Object[] args = null;
            assertEquals("c \\", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c {";
            Object[] args = null;
            assertEquals("c {", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c {a {a";
            Object[] args = null;
            assertEquals("c {a {a", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c {\\";
            Object[] args = null;
            assertEquals("c {\\", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c {\\}";
            Object[] args = null;
            assertEquals("c {}", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "c {\\a";
            Object[] args = null;
            assertEquals("c {\\a", FormatUtil.logback(pattern, args));
        }

        {
            String pattern = "a {} {} b";
            Object[] args = new Object[]{"1", 2, 3};
            assertEquals("a 1 2 b", FormatUtil.logback(pattern, args));
        }
    }

    @Test
    public void format() {
        String f = "%s-%s-%s%%";
        assertEquals("1--%", FormatUtil.format(f, "1"));
        assertEquals("1--%", FormatUtil.format(f, "1", null));
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