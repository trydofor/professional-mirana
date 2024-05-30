package pro.fessional.mirana.lock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-03-09
 */
class JvmStaticGlobalLockTest {

    @Test
    void lock() throws InterruptedException {
        JvmStaticGlobalLock globalLock = new JvmStaticGlobalLock();
        Lock lock = globalLock.getLock("test-lock");
        try {
            if (lock.tryLock(1, TimeUnit.MILLISECONDS)) {
                assertTrue(true, "get Lock");
                CountDownLatch latch = new CountDownLatch(1);
                lockFail(latch);
                latch.await();
            }
        }
        finally {
            lock.unlock();
        }

        try (GlobalLock.AutoLock ignore = globalLock.lock("test-lock")) {
            assertTrue(true, "get Lock");
            CountDownLatch latch = new CountDownLatch(1);
            lockFail(latch);
            latch.await();
        }

        try (GlobalLock.AutoLock ignore = JvmStaticGlobalLock.autolock(new ArrayKey("test-lock"))) {
            assertTrue(true, "get Lock");
            CountDownLatch latch = new CountDownLatch(1);
            lockFail(latch);
            latch.await();
        }

        int lockCount = JvmStaticGlobalLock.countLocks();
        Assertions.assertTrue(lockCount >= 1);
    }

    void lockFail(CountDownLatch latch) {
        final JvmStaticGlobalLock globalLock = new JvmStaticGlobalLock();
        new Thread(() -> {
            Lock lock = globalLock.getLock("test-lock");
            final boolean b;
            try {
                b = lock.tryLock(1, TimeUnit.MILLISECONDS);
                assertFalse(b);
            }
            catch (InterruptedException e) {
                SystemOut.printStackTrace(e);
            }
            latch.countDown();
        }).start();
    }

    @Test
    void testGc() throws InterruptedException {
        int ths = 10;
        int lcs = 1_000;
        int hld = lcs / 3;
        final JvmStaticGlobalLock globalLock = new JvmStaticGlobalLock();
        CountDownLatch latch = new CountDownLatch(ths);
        ConcurrentHashMap<Lock, Lock> hds = new ConcurrentHashMap<>(hld);
        ConcurrentHashMap<Lock, Lock> all = new ConcurrentHashMap<>(lcs);
        for (int i = 0; i < ths; i++) {
            new Thread(() -> {
                for (int j = 0; j < lcs; j++) {
                    try {
                        Thread.sleep((j % 5) + 1);
                    }
                    catch (InterruptedException e) {
                        SystemOut.printStackTrace(e);
                    }
                    final Lock lk = globalLock.getLock("lock-" + j);
                    if (j < hld) {
                        hds.put(lk, lk);
                    }
                    final Lock ck = all.computeIfAbsent(lk, k -> k);
                    if (ck != lk) {
                        throw new IllegalStateException("diff reference");
                    }
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        assertEquals(lcs, JvmStaticGlobalLock.countLocks());
        assertEquals(lcs, all.size());
        assertEquals(hld, hds.size());
        all.clear();
        int tm = 1;
        while (JvmStaticGlobalLock.countLocks() > hld) {
            System.gc();
            SystemOut.println("gc and sleep " + (tm++) + " second");
            Thread.sleep(1_000);
        }
        SystemOut.println("init lock=" + lcs + " and gc left=" + hld);

        for (int i = 1; i <= 10; i++) {
            System.gc();
            SystemOut.println("gc and sleep " + i + " second");
            Thread.sleep(1_000);
        }
        assertEquals(hld, JvmStaticGlobalLock.countLocks());
    }
}
