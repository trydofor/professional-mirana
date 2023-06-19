package pro.fessional.mirana.lock;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.Lock;

/**
 * Global locks, which can be implemented based on JVM, database, distributed, etc.
 * Because of the different lock implementations, it is not recommended to keep Lock references on a large scale.
 * It should getLock every time , in the following pattern.
 *
 * <p>A typical usage idiom for this method would be:
 * <pre> {@code
 * // ① pattern try-resource
 * try(xxx.lock("...")){
 *   // biz code
 * }
 * // ② pattern lock-finally
 * final Lock lock = xxx.getLock("...");
 * lock.lock()
 * try {
 *   // biz code
 * } finally {
 *   lock.unlock();
 * }
 * // ③ pattern tryLock-finally
 * final Lock lock = xxx.getLock("...");
 * if (lock.tryLock()) {
 *   try {
 *     // biz code
 *   } finally {
 *     lock.unlock();
 *   }
 * } else {
 *   // perform alternative actions
 * }
 * }</pre>
 *
 * @author trydofor
 * @since 2021-03-08
 */
public interface GlobalLock {

    /**
     * Create a lock instance by its name. and tryLock and unlock methods must be implemented in the lock instance
     *
     * @param name name of lock
     * @return lock
     */
    @NotNull
    Lock getLock(@NotNull String name);

    /**
     * syntax sugar for lock
     * <pre> {@code
     * final Lock lock = xxx.getLock("...");
     * lock.lock();
     * try {
     *     // biz code
     * } finally {
     *   lock.unlock();
     * }
     * }</pre>
     *
     * @param name name of lock
     * @return AutoCloseable lock
     */
    @NotNull
    default AutoLock lock(@NotNull String name) {
        return new AutoLock(getLock(name));
    }

    class AutoLock implements AutoCloseable {
        private final Lock locker;

        public AutoLock(@NotNull Lock lock) {
            // lock first
            lock.lock();
            // assign later. notnull or throw
            this.locker = lock;
        }

        @Override
        public void close() {
            if (locker != null) {
                locker.unlock();
            }
        }
    }
}
