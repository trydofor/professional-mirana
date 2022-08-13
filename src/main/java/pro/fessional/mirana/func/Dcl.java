package pro.fessional.mirana.func;

import java.util.function.Supplier;

/**
 * Double Checked Locking of Runnable.
 * 适用于准备期多次写，运行时只处理一次的多次读场景。
 *
 * @author trydofor
 * @since 2021-02-19
 */
public class Dcl<T> {

    private final Supplier<T> supplier;
    private volatile boolean dirty = true;
    private volatile T result = null;

    public Dcl(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T runIfDirty() {
        if (dirty) {
            synchronized (supplier) {
                if (dirty) {
                    result = supplier.get();
                    dirty = false;
                }
            }
        }
        return result;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        dirty = true;
    }

    public void setDirty(boolean b) {
        dirty = b;
    }

    public static Dcl<Void> of(Runnable runnable) {
        return new Dcl<>(() -> {
            runnable.run();
            return null;
        });
    }

    public static <T> Dcl<T> of(Supplier<T> supplier) {
        return new Dcl<>(supplier);
    }
}
