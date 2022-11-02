package pro.fessional.mirana.evil;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 仅用作static的，启动后可以被更换的场景，避免内测泄露
 *
 * @author trydofor
 * @since 2022-10-26
 */
public class ThreadLocalProxy<T> extends ThreadLocal<T> {

    /**
     * 尝试通过反射，清除所有线程中的threadLocal
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

            // 通过反射清理
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
     * 变更内部的ThreadLocal，返回旧值，注意不要多次变更,
     * tryToCleanOld == true,
     * WARNING: An illegal reflective access operation has occurred
     * 当提供服务前或少量内存使用时，可以tryToCleanOld=false
     */
    @NotNull
    public ThreadLocal<T> replaceBackend(@NotNull ThreadLocal<T> threadLocal, boolean tryToCleanOld) throws ThreadLocalAttention {
        final ThreadLocal<T> old = backend;
        backend = threadLocal;
        if (tryToCleanOld) {
            // 先替换值，后清理。清理工程不影响新值的使用
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
