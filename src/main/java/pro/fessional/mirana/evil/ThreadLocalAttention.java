package pro.fessional.mirana.evil;

/**
 * <pre>
 * Using ThreadLocal internally, there are leak pitfalls, you must use one of the following modes.
 * (1) static, the only Ref in JVM, to avoid creating temporary Ref multiple times.
 * (2) use try-finally-close mode to remove the Ref.
 * </pre>
 *
 * @author trydofor
 * @since 2022-10-27
 */
public class ThreadLocalAttention extends Attention {
    public ThreadLocalAttention() {
        super();
    }

    public ThreadLocalAttention(Throwable cause) {
        super(cause);
    }
}
