package pro.fessional.mirana.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author trydofor
 * @since 2022-11-08
 */
class BarStringTest {

    @Test
    void split0() {
        BarString bs = new BarString();
        final String str = bs.toString();
        final ArrayList<String> split = BarString.split(str, -1, false);
        Assertions.assertEquals("", str);
        Assertions.assertEquals(0, split.size());
    }

    @Test
    void append() {
        BarString bs = new BarString();
        bs.append("str");
        bs.append(BigDecimal.ONE);
        bs.append((Object) null);
        bs.append(1L);
        bs.append(2);
        bs.append(true);
        bs.append(3F);
        bs.append(4D);
        String str = bs.toString();

        ArrayList<String> values = bs.values(true);
        Assertions.assertEquals(values, BarString.split(str));

        String v0 = values.get(0);
        String ve = str.substring(v0.length() + 2, str.length() - 1);
        Assertions.assertEquals(Arrays.asList(v0, ve), BarString.split(str, 2));

        String bad = str.substring(0, str.length() - 1);
        Assertions.assertEquals(Collections.singletonList(bad), BarString.split(bad, 2, true));

        Assertions.assertEquals(Collections.emptyList(), BarString.split(str, 50, true));
    }

    @Test
    void split1() {
        BarString bs = new BarString();
        bs.append(123);
        final String str = bs.toString();
        final ArrayList<String> split = BarString.split(str, -1, false);
        Assertions.assertEquals("|123|", str);
        Assertions.assertEquals(1, split.size());
        Assertions.assertEquals("123", split.get(0));

        BarString bs0 = new BarString();
        bs0.append(null);
        final String str0 = bs0.toString();
        final ArrayList<String> split0 = BarString.split(str0, -1, false);
        Assertions.assertEquals("||", str0);
        Assertions.assertEquals(1, split0.size());
        Assertions.assertEquals("", split0.get(0));
    }

    @Test
    void split2() {
        BarString bs = new BarString();
        bs.append(null);
        bs.append(123);
        final String str = bs.toString();
        final ArrayList<String> split = BarString.split(str, -1, false);
        Assertions.assertEquals("||123|", str);
        Assertions.assertEquals(2, split.size());
        Assertions.assertEquals("", split.get(0));
        Assertions.assertEquals("123", split.get(1));

        BarString bs0 = new BarString();
        bs0.append(null);
        bs0.append(null);
        final String str0 = bs0.toString();
        final ArrayList<String> split0 = BarString.split(str0, -1, false);
        Assertions.assertEquals("|||", str0);
        Assertions.assertEquals(2, split0.size());
        Assertions.assertEquals("", split0.get(0));
        Assertions.assertEquals("", split0.get(1));

        BarString bs3 = new BarString();
        bs3.append(null);
        bs3.append(null);
        final String str3 = bs3.toString();
        final ArrayList<String> split3 = BarString.split(str3, 0, false);
        Assertions.assertEquals("|||", str3);
        Assertions.assertEquals(0, split3.size());

        BarString bs4 = new BarString();
        bs4.append(null);
        bs4.append(null);
        final String str4 = bs4.toString();
        final ArrayList<String> split4 = BarString.split(str4, 1, false);
        Assertions.assertEquals("|||", str4);
        Assertions.assertEquals(1, split4.size());
        Assertions.assertEquals("|", split4.get(0));
    }

    @Test
    void split6() {
        BarString bs = new BarString();
        bs.append(123);
        bs.append(new BigDecimal("45.67"));
        bs.append(null);
        bs.append(null);
        bs.append(null);
        bs.append(true);
        final String str = bs.toString();
        Assertions.assertEquals("|123|45.67||||true|", str);
        Assertions.assertTrue(bs.isValid());

        final ArrayList<String> split = BarString.split(str, 6, true);
        Assertions.assertEquals(6, split.size());
        Assertions.assertEquals("123", split.get(0));
        Assertions.assertEquals("45.67", split.get(1));
        Assertions.assertEquals("", split.get(2));
        Assertions.assertEquals("", split.get(3));
        Assertions.assertEquals("", split.get(4));
        Assertions.assertEquals("true", split.get(5));

        final ArrayList<String> pt2 = BarString.split(str, 5, false);
        Assertions.assertEquals(5, pt2.size());
        Assertions.assertEquals("123", pt2.get(0));
        Assertions.assertEquals("45.67", pt2.get(1));
        Assertions.assertEquals("", pt2.get(2));
        Assertions.assertEquals("", pt2.get(3));
        Assertions.assertEquals("|true", pt2.get(4));
    }

    @Test
    void splitX() {
        BarString bs = new BarString();
        bs.append('|');
        bs.append('¦');
        bs.append('‖');
        bs.append('｜');
        bs.append('∥');
        bs.append('ǀ');
        bs.append('ǁ');
        bs.append('∣');
        bs.append('│');
        bs.append('।');
        // bs.append("॥");
        final String str = bs.toString();
        Assertions.assertEquals("॥|॥¦॥‖॥｜॥∥॥ǀ॥ǁ॥∣॥│॥।॥", str);
        Assertions.assertTrue(bs.isValid());
        final ArrayList<String> split = BarString.split(str);
        Assertions.assertEquals(10, split.size());
        Assertions.assertEquals("|", split.get(0));
        Assertions.assertEquals("¦", split.get(1));
        Assertions.assertEquals("‖", split.get(2));
        Assertions.assertEquals("｜", split.get(3));
        Assertions.assertEquals("∥", split.get(4));
        Assertions.assertEquals("ǀ", split.get(5));
        Assertions.assertEquals("ǁ", split.get(6));
        Assertions.assertEquals("∣", split.get(7));
        Assertions.assertEquals("│", split.get(8));
        Assertions.assertEquals("।", split.get(9));
    }

    @Test
    void splitY() {
        BarString bs = new BarString();
        bs.append('|');
        bs.append('¦');
        bs.append('‖');
        bs.append('｜');
        bs.append('∥');
        bs.append('ǀ');
        bs.append('ǁ');
        bs.append('∣');
        bs.append('│');
        bs.append('।');
        bs.append('॥');
        final String str = bs.toString();
        Assertions.assertEquals("॥|॥¦॥‖॥｜॥∥॥ǀ॥ǁ॥∣॥│॥।॥॥॥", str);
        Assertions.assertFalse(bs.isValid());

        final ArrayList<String> split = BarString.split(str, 11);
        final Iterator<String> it = split.iterator();
        Assertions.assertEquals(11, split.size());
        Assertions.assertEquals("|", it.next());
        Assertions.assertEquals("¦", it.next());
        Assertions.assertEquals("‖", it.next());
        Assertions.assertEquals("｜", it.next());
        Assertions.assertEquals("∥", it.next());
        Assertions.assertEquals("ǀ", it.next());
        Assertions.assertEquals("ǁ", it.next());
        Assertions.assertEquals("∣", it.next());
        Assertions.assertEquals("│", it.next());
        Assertions.assertEquals("।", it.next());
        Assertions.assertEquals("॥", it.next());
    }
}
