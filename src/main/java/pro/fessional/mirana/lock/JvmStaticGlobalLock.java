package pro.fessional.mirana.lock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 以WeakHashMap包装ReentrantLock作为底层实现。
 * WeakReference用以控制内存使用和正确的锁。
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
     * key必须实现 hashcode和equals方法，以便匹配正确锁
     *
     * @param key 锁的key
     * @return 锁
     */
    public static @NotNull Lock get(@NotNull Object... key) {
        final Hd hd = new Hd(key);
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
     * 同步方法，当前仅用在测试时使用。
     *
     * @return 保持的锁量
     */
    public static int countLocks() {
        synchronized (locks) {
            return locks.size();
        }
    }

    public static class Hd extends ReentrantLock {
        private final Object[] cacheKey;
        private final int hashCode;

        public Hd(Object... keys) {
            this.cacheKey = keys;
            this.hashCode = Arrays.deepHashCode(keys);
        }

        @Override
        public boolean equals(@Nullable Object other) {
            return (this == other || (other instanceof Hd && Arrays.deepEquals(this.cacheKey, ((Hd) other).cacheKey)));
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }
}
