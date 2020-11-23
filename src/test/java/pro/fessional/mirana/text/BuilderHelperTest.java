package pro.fessional.mirana.text;


import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.Arr;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-07-02
 */
public class BuilderHelperTest {

    @Test
    public void append() {
        BuilderHelper.W sb = BuilderHelper.w();
        sb.append(1);
        sb.append((String) null);
        sb.append(2);
        assertEquals("12", sb.toString());
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
        Object[] arr = Arr.of(1, "2", 3L, null, '5');
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
}