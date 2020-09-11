package pro.fessional.mirana.text;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author trydofor
 * @since 2020-09-11
 */
public class CaseSwitcherTest {

    @Test
    public void testCamel() {
        Assert.assertEquals("hello123", CaseSwitcher.camel("hello123"));
        Assert.assertEquals("hello123", CaseSwitcher.camel("hello_123"));
        Assert.assertEquals("hello123", CaseSwitcher.camel("HELLO_123"));
        Assert.assertEquals("hello123", CaseSwitcher.camel("Hello-123"));
        Assert.assertEquals("hello123", CaseSwitcher.camel("hello-123"));

        Assert.assertEquals("helloWorld", CaseSwitcher.camel("hello_world"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("HELLO_WORLD"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("Hello-World"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("hello-world"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("helloWorld"));

        Assert.assertEquals("helloWorld", CaseSwitcher.camel("hello__world"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("HELLO__WORLD"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("Hello--World"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("hello--world"));
        Assert.assertEquals("helloWorld", CaseSwitcher.camel("helloWORld"));
    }

    @Test
    public void testKebab() {
        Assert.assertEquals("hello123", CaseSwitcher.kebab("hello123"));
        Assert.assertEquals("hello-123", CaseSwitcher.kebab("hello_123"));
        Assert.assertEquals("hello-123", CaseSwitcher.kebab("HELLO_123"));
        Assert.assertEquals("hello-123", CaseSwitcher.kebab("Hello-123"));
        Assert.assertEquals("hello-123", CaseSwitcher.kebab("hello-123"));

        Assert.assertEquals("hello-world", CaseSwitcher.kebab("hello_world"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("HELLO_WORLD"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("Hello-World"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("hello-world"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("helloWorld"));

        Assert.assertEquals("hello-world", CaseSwitcher.kebab("hello__world"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("HELLO__WORLD"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("Hello--World"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("hello--world"));
        Assert.assertEquals("hello-world", CaseSwitcher.kebab("helloWORld"));
    }

    @Test
    public void testSnake() {
        Assert.assertEquals("hello123", CaseSwitcher.snake("hello123"));
        Assert.assertEquals("hello_123", CaseSwitcher.snake("hello_123"));
        Assert.assertEquals("hello_123", CaseSwitcher.snake("HELLO_123"));
        Assert.assertEquals("hello_123", CaseSwitcher.snake("Hello-123"));
        Assert.assertEquals("hello_123", CaseSwitcher.snake("hello-123"));

        Assert.assertEquals("hello_world", CaseSwitcher.snake("hello_world"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("HELLO_WORLD"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("Hello-World"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("hello-world"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("helloWorld"));

        Assert.assertEquals("hello_world", CaseSwitcher.snake("hello__world"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("HELLO__WORLD"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("Hello--World"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("hello--world"));
        Assert.assertEquals("hello_world", CaseSwitcher.snake("helloWORld"));
    }

    @Test
    public void testPascal() {
        Assert.assertEquals("Hello123", CaseSwitcher.pascal("hello123"));
        Assert.assertEquals("Hello123", CaseSwitcher.pascal("hello_123"));
        Assert.assertEquals("Hello123", CaseSwitcher.pascal("HELLO_123"));
        Assert.assertEquals("Hello123", CaseSwitcher.pascal("Hello-123"));
        Assert.assertEquals("Hello123", CaseSwitcher.pascal("hello-123"));

        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("hello_world"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("HELLO_WORLD"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("Hello-World"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("hello-world"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("helloWorld"));

        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("hello__world"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("HELLO__WORLD"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("Hello--World"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("hello--world"));
        Assert.assertEquals("HelloWorld", CaseSwitcher.pascal("helloWORld"));
    }

    @Test
    public void testScream() {
        Assert.assertEquals("HELLO123", CaseSwitcher.scream("hello123"));
        Assert.assertEquals("HELLO_123", CaseSwitcher.scream("hello_123"));
        Assert.assertEquals("HELLO_123", CaseSwitcher.scream("HELLO_123"));
        Assert.assertEquals("HELLO_123", CaseSwitcher.scream("Hello-123"));
        Assert.assertEquals("HELLO_123", CaseSwitcher.scream("hello-123"));

        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello_world"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("HELLO_WORLD"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("Hello-World"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello-world"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("helloWorld"));

        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello__world"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("HELLO__WORLD"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("Hello--World"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello--world"));
        Assert.assertEquals("HELLO_WORLD", CaseSwitcher.scream("helloWORld"));
    }
}