package pro.fessional.mirana.text;


import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.Arr;
import pro.fessional.mirana.evil.ThreadLocalAttention;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-07-02
 */
public class BuilderHelperTest {

    @Test
    public void append() {
        BuilderHelper.W sbw = BuilderHelper.w();
        sbw.append(true);
        sbw.append(1);
        sbw.append(2L);
        sbw.append(3.0F);
        sbw.append(4.0D);
        sbw.append('5');
        sbw.append(new StringBuffer("6"));
        sbw.append("7".toCharArray(), 0, 1);
        sbw.append((Object) null);
        sbw.append("8");
        sbw.append("9", 0, 1);
        sbw.append("0".toCharArray());
        assertEquals("true123.04.0567890", sbw.toString());
        sbw.delete(1);
        sbw.delete(0, 2);
        assertEquals("ue123.04.056789", sbw.toString());
        sbw.delete();
        assertEquals("", sbw.toString());
        sbw.join(",", 1, 2);
        sbw.join(",", Arrays.asList(3, 4));
        sbw.join(true, ",", 5, null, 6);
        sbw.join(true, ",", Arrays.asList(7, null, 8));
        sbw.join(true, ",", Arrays.asList(9, null, 0), it -> it);
        sbw.join(",", Arrays.asList("a", null, "b"), it -> it);
        String expected = "1,23,45,67,89,0a,,b";
        assertEquals(expected, sbw.toString());
        assertEquals(expected.length(), sbw.length());
        assertEquals(expected.charAt(3), sbw.charAt(3));
        assertEquals(expected.substring(0, 3), sbw.subSequence(0, 3).toString());
    }

    @Test
    public void normal() {
        StringBuilder sb = new StringBuilder();
        BuilderHelper.append(sb, null);
        BuilderHelper.append(sb, "1,".toCharArray());
        BuilderHelper.join(sb, ",", Arrays.asList("a", null, "b"), it -> it);
        BuilderHelper.append(sb, ",2,");
        BuilderHelper.join(sb, true, ",", Arrays.asList("a", null, "b"), it -> it);
        assertEquals("1,a,,b,2,a,b", sb.toString());
    }

    @Test
    public void delete() {
        StringBuilder sb = new StringBuilder();
        sb.append("1234567890");
        BuilderHelper.delete(sb, 1);
        assertEquals("123456789", sb.toString());
        BuilderHelper.delete(sb, 0);
        assertEquals("123456789", sb.toString());
        BuilderHelper.delete(sb, -1);
        assertEquals("123456789", sb.toString());
        BuilderHelper.delete(sb, 100);
        assertEquals("", sb.toString());
    }

    @Test
    public void join() {
        StringBuilder sb = new StringBuilder();
        Object[] arr = Arr.obj(1, "2", 3L, null, '5');
        BuilderHelper.join(sb, ",", arr);
        assertEquals("1,2,3,,5", sb.toString());

        BuilderHelper.delete(sb);
        BuilderHelper.join(sb, true, ",", arr);
        assertEquals("1,2,3,5", sb.toString());

        List<Object> lst = Arrays.asList(1, "2", 3L, null, '5');
        BuilderHelper.delete(sb);
        BuilderHelper.join(sb, ",", lst);
        assertEquals("1,2,3,,5", sb.toString());

        BuilderHelper.delete(sb);
        BuilderHelper.join(sb, true, ",", lst);
        assertEquals("1,2,3,5", sb.toString());
    }

    @Test
    public void holder() throws ThreadLocalAttention {
        BuilderHolder hd = new BuilderHolder(1, 10);
        StringBuilder sb = new StringBuilder("1234567890abcdefg");
        assertTrue(hd.anewValue(sb));
    }
}