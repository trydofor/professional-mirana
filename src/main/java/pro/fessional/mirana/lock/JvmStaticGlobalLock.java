package pro.fessional.mirana.lock;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Wrapping ReentrantLock with WeakHashMap as the underlying implementation.
 * WeakReference is used to control memory usage and correct locking.
 *
 * @author trydofor
 * @since 2021-03-08
 */
public class JvmStaticGlobalLock implements GlobalLock {

    private static final Map<Hd, WeakReference<Hd>> locks = new WeakHashMap<>();

    @Override
    public @NotNull Lock getLock(@NotNull String name) {
        return get(name);
    }

    /**
     * All keys must implement hashcode and equals methods in order to match the correct lock.
     * If there is only one parameter and it is an ArrayKey, then use it directly.
     *
     * @param key keys to get lock
     * @return lock
     */
    public static @NotNull Lock get(@NotNull Object... key) {
        final Hd hd;
        if (key.length == 1 && key[0] instanceof ArrayKey) {
            hd = new Hd((ArrayKey) key[0]);
        }
        else {
            hd = new Hd(key);
        }
        synchronized (locks) {
            final WeakReference<Hd> rf = locks.computeIfAbsent(hd, WeakReference::new);
            final Hd lk = rf.get();
            if (lk == null) {
                throw new IllegalStateException("should not gc if key exist， report bug.");
            }
            return lk;
        }
    }

    /**
     * try-resource pattern lock
     *
     * @param key keys to get lock
     * @return AutoLock
     * @see #lock(String)
     */
    public static @NotNull AutoLock lock(@NotNull Object... key) {
        return new AutoLock(get(key));
    }

    /**
     * sync method to count currently locks. used only for testing purposes.
     *
     * @return count of locks
     */
    public static int countLocks() {
        synchronized (locks) {
            return locks.size();
        }
    }

    public static class Hd extends ReentrantLock {
        private final ArrayKey arrayKey;

        public Hd(ArrayKey key) {
            this.arrayKey = key;
        }

        public Hd(Object... keys) {
            this.arrayKey = new ArrayKey(keys);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Hd hd = (Hd) o;
            return arrayKey.equals(hd.arrayKey);
        }

        @Override
        public int hashCode() {
            return arrayKey.hashCode();
        }
    }
}
