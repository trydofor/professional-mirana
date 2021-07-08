package pro.fessional.mirana.dync;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author trydofor
 * @since 2020-11-23
 */
public class JavaTest {

    public interface DynTest {
        String test();
    }

    @Test
    public void compile() {
        //
        Class<DynTest> dtClz = Java.compile("pro.fessional.mirana.dync.DyncJava",
                "package pro.fessional.mirana.dync;\n" +
                "import static pro.fessional.mirana.dync.JavaTest.DynTest;\n" +
                "public class DyncJava implements DynTest {\n" +
                "  @Override\n" +
                "  public String test() {\n" +
                "      return \"good\";\n" +
                "  }\n" +
                "}");

        DynTest dt = Java.create(dtClz);

        assertEquals("good", dt.test());
    }
}
