package pro.fessional.mirana.text;

import org.junit.Assert;
import org.junit.Test;
import pro.fessional.mirana.data.Arr;

import java.util.Arrays;
import java.util.List;

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
        Assert.assertEquals("12", sb.toString());
    }

    @Test
    public void delete() {
        StringBuilder sb = new StringBuilder();
        sb.append("1234567890");
        BuilderHelper.delete(sb, 1);
        Assert.assertEquals("123456789", sb.toString());
        BuilderHelper.delete(sb, 0);
        Assert.assertEquals("123456789", sb.toString());
        BuilderHelper.delete(sb, -1);
        Assert.assertEquals("123456789", sb.toString());
        BuilderHelper.delete(sb, 100);
        Assert.assertEquals("", sb.toString());
    }

    @Test
    public void join() {
        StringBuilder sb = new StringBuilder();
        Object[] arr = Arr.of(1, "2", 3L, null, '5');
        BuilderHelper.join(sb, ",", arr);
        Assert.assertEquals("1,2,3,,5", sb.toString());

        BuilderHelper.delete(sb);
        BuilderHelper.join(sb, true, ",", arr);
        Assert.assertEquals("1,2,3,5", sb.toString());

        List<Object> lst = Arrays.asList(1, "2", 3L, null, '5');
        BuilderHelper.delete(sb);
        BuilderHelper.join(sb, ",", lst);
        Assert.assertEquals("1,2,3,,5", sb.toString());

        BuilderHelper.delete(sb);
        BuilderHelper.join(sb, true, ",", lst);
        Assert.assertEquals("1,2,3,5", sb.toString());
    }
}