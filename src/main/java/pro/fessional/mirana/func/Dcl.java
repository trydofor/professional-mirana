package pro.fessional.mirana.func;

/**
 * Double Checked Locking of Runnable.
 * 适用于准备期多次写，运行时只处理一次的多次读场景。
 *
 * @author trydofor
 * @since 2021-02-19
 */
public class Dcl {

    private final Runnable runnable;
    private volatile boolean dirty = true;

    public Dcl(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * 未排序则排序，已排序则返回
     */
    public void runIfDirty() {
        if (dirty) {
            synchronized (runnable) {
                if (dirty) {
                    runnable.run();
                    dirty = false;
                }
            }
        }
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

    public static Dcl of(Runnable runnable) {
        return new Dcl(runnable);
    }
}
