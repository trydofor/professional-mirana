package pro.fessional.mirana.evil;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.lang.ref.SoftReference;

/**
 * <pre>
 * Using ThreadLocal internally, there are leak pitfalls, you must use one of the following modes.
 * (1) static, the only Ref in JVM, to avoid creating temporary Ref multiple times.
 * (2) use try-finally-close mode to remove the Ref.
 * </pre>
 *
 * @author trydofor
 * @since 2022-09-22
 */
public abstract class ThreadLocalSoft<T> implements Closeable {

    /** follow usage pattern */
    private final ThreadLocal<SoftReference<T>> threadLocal;

    @SuppressWarnings("RedundantThrows")
    public ThreadLocalSoft(ThreadLocal<SoftReference<T>> threadLocal) throws ThreadLocalAttention {
        this.threadLocal = threadLocal;
    }

    /**
     * Init in a single thread, first time or anewValue() call
     */
    @NotNull
    public abstract T initValue();

    /**
     * Reset to current value in a single thread and decide whether it is available
     *
     * @param t current value
     * @return whether to initValue again.
     */
    public boolean anewValue(@NotNull T t) {
        return false;
    }


    /**
     * <pre>
     * (1) static, the only Ref in JVM, to avoid creating temporary Ref multiple times.
     * (2) use try-finally-close mode to remove the Ref.
     * </pre>
     */
    @NotNull
    public T use() {
        T cur = null;
        SoftReference<T> ref = threadLocal.get();
        if (ref != null) {
            final T c = ref.get();
            if (c != null) {
                cur = c;
            }
        }

        if (cur == null) {
            cur = initValue();
            threadLocal.set(new SoftReference<>(cur));
        }
        else {
            if (anewValue(cur)) {
                cur = initValue();
                threadLocal.set(new SoftReference<>(cur));
            }
        }

        return cur;
    }

    @Override
    public void close() {
        threadLocal.remove();
    }
}
