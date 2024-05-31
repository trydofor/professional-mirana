package pro.fessional.mirana;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * @author trydofor
 * @since 2024-01-01
 */
public class Testing {

    public static final String TestVerbose = "TEST_VERBOSE";
    private static volatile Boolean NotTestVerbose = null;

    public static boolean notTestVerbose() {
        if (NotTestVerbose == null) {
            NotTestVerbose = "false".equalsIgnoreCase(System.getenv(TestVerbose));
        }
        return NotTestVerbose;
    }

    // print
    public static void printf(String format, Object... args) {
        if (notTestVerbose()) return;
        System.out.printf(format, args);
    }

    public static void println(Object ptn) {
        if (notTestVerbose()) return;
        System.out.println(ptn);
    }

    public static void printStackTrace(Throwable t) {
        if (notTestVerbose()) return;
        t.printStackTrace(System.err);
    }


    public static void print(Object s) {
        if (notTestVerbose()) return;
        System.out.print(s);
    }

    /**
     * assert equals, hashCode, toString
     */
    public static <T> void assertEqualsHashCodeToString(T t1, T t2) {
        assertEquals(t1, t2);

        HashMap<T, Boolean> map = new HashMap<>();
        map.put(t1, Boolean.TRUE);
        Boolean b2 = map.get(t2);
        assertEquals(b2, Boolean.TRUE);

        assertEquals(t1.toString(), t2.toString());
    }
}
