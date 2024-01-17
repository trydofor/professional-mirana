package pro.fessional.mirana;

/*
 * @author trydofor
 * @since 2024-01-01
 */
public class SystemOut {

    public static final String TestVerbose = "TEST_VERBOSE";
    private static volatile Boolean NotTestVerbose = null;

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

    public static boolean notTestVerbose() {
        if (NotTestVerbose == null) {
            NotTestVerbose = "false".equalsIgnoreCase(System.getenv(TestVerbose));
        }
        return NotTestVerbose;
    }

    public static void print(Object s) {
        if (notTestVerbose()) return;
        System.out.print(s);
    }
}
