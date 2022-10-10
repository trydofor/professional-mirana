package pro.fessional.mirana.anti;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.lang.ref.SoftReference;

/**
 * <pre>
 * 内部使用 ThreadLocal，有leak隐患，必须使用以下模式之一。
 * ① static，JVM内唯一Ref，避免多次创建临时Ref
 * ② 使用 try-finally-close 模式，remove掉Ref
 * </pre>
 *
 * @author trydofor
 * @since 2022-09-22
 */
public abstract class S<T> implements Closeable {

    /** follow usage pattern */
    private final ThreadLocal<SoftReference<T>> threadLocal;

    public S(ThreadLocal<SoftReference<T>> threadLocal) {
        this.threadLocal = threadLocal;
    }

    /**
     * 在单线程中初始化，首次或anewValue()调用
     *
     * @return 初始值
     */
    @NotNull
    public abstract T initValue();

    /**
     * 在单线程中重置现值，并决定是否可用
     *
     * @param t 现值
     * @return 是否需要重新init
     */
    public boolean anewValue(@NotNull T t){
        return false;
    }


    /**
     * ① static，JVM内唯一Ref，避免多次创建临时Ref
     * ② 使用 try-finally-close 模式，remove掉Ref
     *
     * @return T 值
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
