package pro.fessional.mirana.evil;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Used only for static scenarios, which can be replaced after startup to avoid memory leaks.
 *
 * @author trydofor
 * @since 2022-10-26
 */
public class ThreadLocalProxy<T> extends ThreadLocal<T> {

    /**
     * try to clear the values in threadLocal by reflection.
     * WARNING: An illegal reflective access operation has occurred
     */
    public static void tryClear(ThreadLocal<?> threadLocal) throws ThreadLocalAttention {
        if (threadLocal == null) return;
        // try to clean by reflect
        try {
            // BGN copy from apache ThreadUtils#getAllThreads
            ThreadGroup systemGroup = Thread.currentThread().getThreadGroup();
            while (systemGroup.getParent() != null) {
                systemGroup = systemGroup.getParent();
            }
            int count = systemGroup.activeCount();
            Thread[] threads;
            do {
                threads = new Thread[count + (count / 2) + 1]; //slightly grow the array size
                count = systemGroup.enumerate(threads, true);
                //return value of enumerate() must be strictly less than the array size according to javadoc
            } while (count >= threads.length);
            // END

            // clear the values in threadLocal by reflection.
            final Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);

            Class<?> threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
            Method removeMethod = threadLocalMapClass.getDeclaredMethod("remove", ThreadLocal.class);
            removeMethod.setAccessible(true);

            for (int i = 0; i < count; i++) {
                final Object threadLocalMap = threadLocalsField.get(threads[i]);
                if (threadLocalMap != null) {
                    removeMethod.invoke(threadLocalMap, threadLocal);
                }
            }
        }
        catch (ReflectiveOperationException e) {
            throw new ThreadLocalAttention(e);
        }
    }

    private volatile ThreadLocal<T> backend;

    public ThreadLocalProxy() {
        this.backend = new ThreadLocal<>();
    }

    public ThreadLocalProxy(@NotNull ThreadLocal<T> tl) {
        this.backend = tl;
    }

    @NotNull
    public ThreadLocal<T> getBackend() {
        return backend;
    }

    /**
     * <pre>
     * change the internal ThreadLocal impl, return the old value. do NOT change it more than once.
     * tryToCleanOld == true, will log
     * WARNING: An illegal reflective access operation has occurred
     *
     * use tryToCleanOld=false if before starting any service or a few memory is in use.
     * </pre>
     */
    @NotNull
    public ThreadLocal<T> replaceBackend(@NotNull ThreadLocal<T> threadLocal, boolean tryToCleanOld) throws ThreadLocalAttention {
        final ThreadLocal<T> old = backend;
        backend = threadLocal;
        if (tryToCleanOld) {
            // Replace the value first, then clear.
            // Clear does not affect the use of new values
            tryClear(old);
        }
        return old;
    }

    @Override
    public T get() {
        return backend.get();
    }

    @Override
    public void set(T value) {
        backend.set(value);
    }

    @Override
    public void remove() {
        backend.remove();
    }
}
