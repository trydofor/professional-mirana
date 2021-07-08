package pro.fessional.mirana.lock;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
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
     * 所以key必须实现 hashcode和equals方法，以便匹配正确锁。
     * 如果只有一个参数，且是ArrayKey，则直接使用
     *
     * @param key 锁的key
     * @return 锁
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
