package pro.fessional.mirana.io;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author trydofor
 * @since 2020-10-14
 */
public class JsTest {

    @Test
    public void runNew() {
        Map<String, Object> args = new HashMap<>();
        args.put("name", "mirana");
        String rst = Js.run("var msg = 'hello ' + name; print(1); msg;", args);
        Assert.assertEquals("hello mirana", rst);
    }

    @Test
    public void runCtx() {
        Map<String, Object> args = new HashMap<>();
        args.put("name", "mirana");
        String rst = Js.run(true, "var msg = 'hello ' + name; print(1); msg;", args);
        Assert.assertEquals("hello mirana", rst);

        boolean brt = Js.run(true, "name == 'mirana' && name.length() > 0");
        Assert.assertTrue(brt);
    }
}