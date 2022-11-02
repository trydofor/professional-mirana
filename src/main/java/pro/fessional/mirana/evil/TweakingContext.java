package pro.fessional.mirana.evil;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

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

    private final AtomicReference<T> defaultValue = new AtomicReference<>();
    private final AtomicReference<T> globalValue = new AtomicReference<>();
    private final ThreadLocalProxy<T> threadValue = new ThreadLocalProxy<>();

    /**
     * 无全局默认值
     */
    public TweakingContext() {
    }

    /**
     * 有全局默认值
     */
    public TweakingContext(T initGlobal) {
        initGlobal(initGlobal);
    }

    /**
     * 初始全局默认值
     */
    public void initGlobal(T value) {
        defaultValue.set(value);
    }

    /**
     * 初始线程默认值，最好无initialValue，采用全局默认值
     */
    public void initThread(@NotNull ThreadLocal<T> threadLocal, boolean tryToCleanOld) throws ThreadLocalAttention {
        threadValue.replaceBackend(threadLocal, tryToCleanOld);
    }

    /**
     * 调整全局设定值
     */
    public void tweakGlobal(T stack) {
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
        final T t = globalValue.get();
        if (t == null && notnull) {
            throw new IllegalStateException("global value is null");
        }
        return t;
    }

    @Contract("true->!null")
    public T threadValue(boolean notnull) {
        final T t = threadValue.get();
        if (t == null && notnull) {
            throw new IllegalStateException("thread value is null");
        }
        return t;
    }

    @Contract("true->!null")
    public T defaultValue(boolean notnull) {
        final T t = defaultValue.get();
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
        final T t = threadValue.get();
        if (t != null) {
            return t;
        }
        final T g = globalValue.get();
        if (g != null) {
            return g;
        }
        final T d = defaultValue.get();
        if (d == null && notnull) {
            throw new IllegalStateException("global default value is null");
        }
        return d;
    }
}
