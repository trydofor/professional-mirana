package pro.fessional.mirana.text;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;
import pro.fessional.mirana.evil.ThreadLocalAttention;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class FormatUtilTest {


    @Test
    public void count() {
        FormatUtil.V all = (fmt, idx, str, sub) -> {
            Testing.printf("%d,%d,%s\n", idx, sub, str);
            return true;
        };

        String ptn = "%s-%s-%s%%%%";
        Testing.println(ptn);
        int[] r1 = FormatUtil.count(all, ptn, "%", "%%", "%%%", "%%%%");
        assertEquals(7, r1[0]);
        assertEquals(2, r1[1]);
        assertEquals(1, r1[2]);
        assertEquals(1, r1[3]);

        FormatUtil.V fst = (fmt, idx, str, sub) -> {
            Testing.printf("%d,%d,%s\n", idx, sub, str);
            return false;
        };

        int[] r2 = FormatUtil.count(fst, ptn, "%", "%%", "%%%", "%%%%");
        assertEquals(1, r2[0]);
        assertEquals(1, r2[1]);
        assertEquals(1, r2[2]);
        assertEquals(1, r2[3]);
        assertEquals(0, FormatUtil.count(null,""));
        assertEquals(0, FormatUtil.count("", (String) null));
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
        {
            assertEquals("a 1 null b", FormatUtil.logback("a {} {} b", "1"));
        }
    }

    @Test
    public void format() {
        String f = "%s-%s-%s%%";
        assertEquals("1--%", FormatUtil.format(f, "1"));
        // IDE warn les args
        // https://youtrack.jetbrains.com/issue/IDEA-283556/Add-annotation-to-printf-like-methods
        assertEquals("1--%", FormatUtil.format("%s-%s-%s%%", "1", null));
    }

    @Test
    public void message() {
        String f = "{0}{1}{3}";
        assertEquals("1", FormatUtil.message(f, "1"));
        assertEquals("1", FormatUtil.message(f, "1", null));
        assertEquals("1", FormatUtil.message(f, "1", null, null));
        assertEquals("12", FormatUtil.message(f, "1", 2, 3));
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

    @Test
    public void holder() throws ThreadLocalAttention {
        FormatHolder hd = new FormatHolder("%s");
        assertEquals("%s", hd.getPattern());
    }

    @Test
    public void mess() {
        assertEquals("a,b,c", FormatUtil.join(true, ",", Arrays.asList("a", "b", "c")));
        assertEquals("[true,false]", FormatUtil.toString(new boolean[]{true,false}));
        assertEquals("[1,2]", FormatUtil.toString(new short[]{1,2}));
        assertEquals("[1,2]", FormatUtil.toString(new int[]{1,2}));
        assertEquals("[1,2]", FormatUtil.toString(new long[]{1L,2L}));
        assertEquals("[1.0,2.0]", FormatUtil.toString(new float[]{1.0F,2.0F}));
        assertEquals("[1.0,2.0]", FormatUtil.toString(new double[]{1.0D,2.0D}));
    }

    @Test
    public void sortParam() {
        Map<String, String> p1 = new HashMap<>();
        p1.put("k2", "v2");
        p1.put("k1", "v1");
        assertEquals("k1=v1&k2=v2", FormatUtil.sortParam(p1));

        Map<String, String> p2 = new TreeMap<>();
        p2.put("k2", "v2");
        p2.put("k1", "v1");
        assertEquals("k1=v1&k2=v2", FormatUtil.sortParam(p2));
    }

    @Test
    public void fixArgs() {
        assertArrayEquals(new Object[]{}, FormatUtil.fixArgs(0, "a", "b"));
        assertArrayEquals(new Object[]{"a"}, FormatUtil.fixArgs(1, "a", "b"));
        assertArrayEquals(new Object[]{"a","b"}, FormatUtil.fixArgs(2, "a", "b"));
        assertArrayEquals(new Object[]{"a","b",""}, FormatUtil.fixArgs(3, "a", "b"));
        assertArrayEquals(new Object[]{"a","",""}, FormatUtil.fixArgs(3, "a", null));
        Object[] args = null;
        assertArrayEquals(new Object[]{"","",""}, FormatUtil.fixArgs(3, args));
    }
}
