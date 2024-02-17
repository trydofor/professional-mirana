package pro.fessional.mirana.evil;

import org.jetbrains.annotations.Contract;
import pro.fessional.mirana.dync.OrderedSpi;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * <pre>
 * default - should init before the service and called once.
 * global - global scope, should be used at the system level.
 * thread - thread scope, should use try {tweak} finally{reset} pattern.
 * </pre>
 *
 * @author trydofor
 * @since 2022-10-30
 */
public class TweakingContext<T> {

    private final AtomicReference<Supplier<T>> defaultValue = new AtomicReference<>();
    private final AtomicReference<Supplier<T>> globalValue = new AtomicReference<>();
    private final ThreadLocal<Supplier<T>> threadValue;

    /**
     * without default value
     */
    @SuppressWarnings("unchecked")
    public TweakingContext() {
        ThreadLocalProvider spi = OrderedSpi.first(ThreadLocalProvider.class);
        threadValue = spi == null ? new ThreadLocal<>() : (ThreadLocal<Supplier<T>>) spi.get();
    }

    /**
     * init with default value
     */
    public TweakingContext(T initDefault) {
        this();
        initDefault(initDefault);
    }

    /**
     * init with default value
     */
    public TweakingContext(Supplier<T> initDefault) {
        this();
        initDefault(initDefault);
    }

    /**
     * init the default value
     */
    public void initDefault(T value) {
        defaultValue.set(() -> value);
    }

    /**
     * init the default value
     */
    public void initDefault(Supplier<T> value) {
        defaultValue.set(value);
    }

    /**
     * init the default value
     */
    public void initGlobal(T value) {
        tweakGlobal(value);
    }

    /**
     * init the default value
     */
    public void initGlobal(Supplier<T> value) {
        tweakGlobal(value);
    }

    /**
     * init thread value
     */
    public void initThread(T value) {
        tweakThread(value);
    }

    /**
     * init thread value
     */
    public void initThread(Supplier<T> value) {
        tweakThread(value);
    }

    /**
     * tweak global value
     */
    public void tweakGlobal(T value) {
        globalValue.set(() -> value);
    }

    /**
     * tweak global value
     */
    public void tweakGlobal(Supplier<T> value) {
        globalValue.set(value);
    }

    /**
     * reset global value
     */
    public void resetGlobal() {
        globalValue.set(null);
    }

    /**
     * tweak thread value. should use try {tweak} finally{reset} pattern
     */
    public void tweakThread(T value) {
        threadValue.set(() -> value);
    }

    /**
     * tweak thread value. should use try {tweak} finally{reset} pattern
     */
    public void tweakThread(Supplier<T> value) {
        threadValue.set(value);
    }

    /**
     * reset thread value. should use try {tweak} finally{reset} pattern
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
     * get current value, in the order of thread, global, default value
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
