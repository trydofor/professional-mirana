package pro.fessional.mirana.text;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-09-11
 */
public class CaseSwitcherTest {

    @Test
    public void testCamel() {
        assertEquals("hello123", CaseSwitcher.camel("hello123"));
        assertEquals("hello123", CaseSwitcher.camel("hello_123"));
        assertEquals("hello123", CaseSwitcher.camel("HELLO_123"));
        assertEquals("hello123", CaseSwitcher.camel("Hello-123"));
        assertEquals("hello123", CaseSwitcher.camel("hello-123"));

        assertEquals("helloWorld", CaseSwitcher.camel("hello_world"));
        assertEquals("helloWorld", CaseSwitcher.camel("HELLO_WORLD"));
        assertEquals("helloWorld", CaseSwitcher.camel("Hello-World"));
        assertEquals("helloWorld", CaseSwitcher.camel("hello-world"));
        assertEquals("helloWorld", CaseSwitcher.camel("helloWorld"));
        assertEquals("helloWorldAbc", CaseSwitcher.camel("HelloWorldAbc"));

        assertEquals("helloWorld", CaseSwitcher.camel("hello__world"));
        assertEquals("helloWorld", CaseSwitcher.camel("HELLO__WORLD"));
        assertEquals("helloWorld", CaseSwitcher.camel("Hello--World"));
        assertEquals("helloWorld", CaseSwitcher.camel("hello--world"));
        assertEquals("helloWorld", CaseSwitcher.camel("helloWORld"));
        assertEquals("helloWorldAbc", CaseSwitcher.camel("HelloWORldAbc"));
    }

    @Test
    public void testKebab() {
        assertEquals("hello123", CaseSwitcher.kebab("hello123"));
        assertEquals("hello-123", CaseSwitcher.kebab("hello_123"));
        assertEquals("hello-123", CaseSwitcher.kebab("HELLO_123"));
        assertEquals("hello-123", CaseSwitcher.kebab("Hello-123"));
        assertEquals("hello-123", CaseSwitcher.kebab("hello-123"));

        assertEquals("hello-world", CaseSwitcher.kebab("hello_world"));
        assertEquals("hello-world", CaseSwitcher.kebab("HELLO_WORLD"));
        assertEquals("hello-world", CaseSwitcher.kebab("Hello-World"));
        assertEquals("hello-world", CaseSwitcher.kebab("hello-world"));
        assertEquals("hello-world", CaseSwitcher.kebab("helloWorld"));
        assertEquals("hello-world-abc", CaseSwitcher.kebab("HelloWorldAbc"));

        assertEquals("hello-world", CaseSwitcher.kebab("hello__world"));
        assertEquals("hello-world", CaseSwitcher.kebab("HELLO__WORLD"));
        assertEquals("hello-world", CaseSwitcher.kebab("Hello--World"));
        assertEquals("hello-world", CaseSwitcher.kebab("hello--world"));
        assertEquals("hello-world", CaseSwitcher.kebab("helloWORld"));
        assertEquals("hello-world-abc", CaseSwitcher.kebab("HelloWORldAbc"));
    }

    @Test
    public void testSnake() {
        assertEquals("hello123", CaseSwitcher.snake("hello123"));
        assertEquals("hello_123", CaseSwitcher.snake("hello_123"));
        assertEquals("hello_123", CaseSwitcher.snake("HELLO_123"));
        assertEquals("hello_123", CaseSwitcher.snake("Hello-123"));
        assertEquals("hello_123", CaseSwitcher.snake("hello-123"));

        assertEquals("hello_world", CaseSwitcher.snake("hello_world"));
        assertEquals("hello_world", CaseSwitcher.snake("HELLO_WORLD"));
        assertEquals("hello_world", CaseSwitcher.snake("Hello-World"));
        assertEquals("hello_world", CaseSwitcher.snake("hello-world"));
        assertEquals("hello_world", CaseSwitcher.snake("helloWorld"));
        assertEquals("hello_world_abc", CaseSwitcher.snake("HelloWorldAbc"));

        assertEquals("hello_world", CaseSwitcher.snake("hello__world"));
        assertEquals("hello_world", CaseSwitcher.snake("HELLO__WORLD"));
        assertEquals("hello_world", CaseSwitcher.snake("Hello--World"));
        assertEquals("hello_world", CaseSwitcher.snake("hello--world"));
        assertEquals("hello_world", CaseSwitcher.snake("helloWORld"));
        assertEquals("hello_world_abc", CaseSwitcher.snake("HelloWORldAbc"));
    }

    @Test
    public void testDot() {
        assertEquals("hello123", CaseSwitcher.dot("hello123"));
        assertEquals("hello.123", CaseSwitcher.dot("hello_123"));
        assertEquals("hello.123", CaseSwitcher.dot("HELLO_123"));
        assertEquals("hello.123", CaseSwitcher.dot("Hello-123"));
        assertEquals("hello.123", CaseSwitcher.dot("hello-123"));

        assertEquals("hello.world", CaseSwitcher.dot("hello_world"));
        assertEquals("hello.world", CaseSwitcher.dot("HELLO_WORLD"));
        assertEquals("hello.world", CaseSwitcher.dot("Hello-World"));
        assertEquals("hello.world", CaseSwitcher.dot("hello-world"));
        assertEquals("hello.world", CaseSwitcher.dot("helloWorld"));
        assertEquals("hello.world.abc", CaseSwitcher.dot("HelloWorldAbc"));

        assertEquals("hello.world", CaseSwitcher.dot("hello__world"));
        assertEquals("hello.world", CaseSwitcher.dot("HELLO__WORLD"));
        assertEquals("hello.world", CaseSwitcher.dot("Hello--World"));
        assertEquals("hello.world", CaseSwitcher.dot("hello--world"));
        assertEquals("hello.world", CaseSwitcher.dot("helloWORld"));
        assertEquals("hello.world.abc", CaseSwitcher.dot("HelloWORldAbc"));
    }

    @Test
    public void testPascal() {
        assertEquals("Hello123", CaseSwitcher.pascal("hello123"));
        assertEquals("Hello123", CaseSwitcher.pascal("hello_123"));
        assertEquals("Hello123", CaseSwitcher.pascal("HELLO_123"));
        assertEquals("Hello123", CaseSwitcher.pascal("Hello-123"));
        assertEquals("Hello123", CaseSwitcher.pascal("hello-123"));

        assertEquals("HelloWorld", CaseSwitcher.pascal("hello_world"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("HELLO_WORLD"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("Hello-World"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("hello-world"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("helloWorld"));
        assertEquals("HelloWorldAbc", CaseSwitcher.pascal("HelloWorldAbc"));

        assertEquals("HelloWorld", CaseSwitcher.pascal("hello__world"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("HELLO__WORLD"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("Hello--World"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("hello--world"));
        assertEquals("HelloWorld", CaseSwitcher.pascal("helloWORld"));
        assertEquals("HelloWorldAbc", CaseSwitcher.pascal("helloWORldAbc"));
    }

    @Test
    public void testScream() {
        assertEquals("HELLO123", CaseSwitcher.scream("hello123"));
        assertEquals("HELLO_123", CaseSwitcher.scream("hello_123"));
        assertEquals("HELLO_123", CaseSwitcher.scream("HELLO_123"));
        assertEquals("HELLO_123", CaseSwitcher.scream("Hello-123"));
        assertEquals("HELLO_123", CaseSwitcher.scream("hello-123"));

        assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello_world"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("HELLO_WORLD"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("Hello-World"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello-world"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("helloWorld"));
        assertEquals("HELLO_WORLD_ABC", CaseSwitcher.scream("HelloWorldAbc"));

        assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello__world"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("HELLO__WORLD"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("Hello--World"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("hello--world"));
        assertEquals("HELLO_WORLD", CaseSwitcher.scream("helloWORld"));
        assertEquals("HELLO_WORLD_ABC", CaseSwitcher.scream("helloWORldAbc"));
    }
}
