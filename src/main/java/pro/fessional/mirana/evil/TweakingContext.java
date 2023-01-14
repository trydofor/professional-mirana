package pro.fessional.mirana.evil;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * <pre>
 * init方法，应该在服务前初始化，并且最好一次。
 * global方法，全局生效，应该在系统级层面使用
 * thread方法，线程内生效，应该使用try {tweak} finally{reset} 模式
 * </pre>
 *
 * @author trydofor
 * @since 2022-10-30
 */
public class TweakingContext<T> {

    private final AtomicReference<Supplier<T>> defaultValue = new AtomicReference<>();
    private final AtomicReference<Supplier<T>> globalValue = new AtomicReference<>();
    private final ThreadLocalProxy<Supplier<T>> threadValue = new ThreadLocalProxy<>();

    /**
     * 无默认值
     */
    public TweakingContext() {
    }

    /**
     * 有默认值
     */
    public TweakingContext(T initDefault) {
        initDefault(initDefault);
    }

    /**
     * 有默认值
     */
    public TweakingContext(Supplier<T> initDefault) {
        initDefault(initDefault);
    }

    /**
     * 初始默认值
     */
    public void initDefault(T value) {
        defaultValue.set(() -> value);
    }

    /**
     * 初始默认值
     */
    public void initDefault(Supplier<T> value) {
        defaultValue.set(value);
    }

    /**
     * 初始线程默认值，最好无initialValue，采用全局默认值
     */
    public void initThread(@NotNull ThreadLocal<Supplier<T>> threadLocal, boolean tryToCleanOld) throws ThreadLocalAttention {
        threadValue.replaceBackend(threadLocal, tryToCleanOld);
    }

    /**
     * 调整全局设定值
     */
    public void tweakGlobal(T stack) {
        globalValue.set(() -> stack);
    }

    /**
     * 调整全局设定值
     */
    public void tweakGlobal(Supplier<T> stack) {
        globalValue.set(stack);
    }

    /**
     * 重置全局设定值
     */
    public void resetGlobal() {
        globalValue.set(null);
    }

    /**
     * 调整Thread设定值。内含ThreadLocal，建议使用try {tweak} finally{reset} 模式。
     */
    public void tweakThread(T stack) {
        if (stack == null) {
            threadValue.remove();
        }
        else {
            threadValue.set(() -> stack);
        }
    }

    /**
     * 调整Thread设定值。内含ThreadLocal，建议使用try {tweak} finally{reset} 模式。
     */
    public void tweakThread(Supplier<T> stack) {
        if (stack == null) {
            threadValue.remove();
        }
        else {
            threadValue.set(stack);
        }
    }

    /**
     * 重置Thread设定值。内含ThreadLocal，建议使用try {tweak} finally{reset} 模式。
     */
    public void resetThread() {
        threadValue.remove();
    }

    //
    @Contract("true->!null")
    public T globalValue(boolean notnull) {
        final Supplier<T> s = globalValue.get();
        final T t = s == null ? null : s.get();
        if (t == null && notnull) {
            throw new IllegalStateException("global value is null");
        }
        return t;
    }

    @Contract("true->!null")
    public T threadValue(boolean notnull) {
        final Supplier<T> s = threadValue.get();
        final T t = s == null ? null : s.get();
        if (t == null && notnull) {
            throw new IllegalStateException("thread value is null");
        }
        return t;
    }

    @Contract("true->!null")
    public T defaultValue(boolean notnull) {
        final Supplier<T> s = defaultValue.get();
        final T t = s == null ? null : s.get();
        if (t == null && notnull) {
            throw new IllegalStateException("default value is null");
        }
        return t;
    }

    /**
     * 当前是设定值，顺序为线程设定值，全局设定值，全局默认值
     */
    @Contract("true->!null")
    public T current(boolean notnull) {
        final T t = threadValue(false);
        if (t != null) {
            return t;
        }
        final T g = globalValue(false);
        if (g != null) {
            return g;
        }
        return defaultValue(notnull);
    }
}
