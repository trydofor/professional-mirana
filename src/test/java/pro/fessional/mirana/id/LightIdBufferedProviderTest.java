package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class LightIdBufferedProviderTest {

    private final AtomicLong sleep = new AtomicLong(100);
    private final AtomicBoolean s404 = new AtomicBoolean(true);
    public final AtomicLong sequence = new AtomicLong(1);

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 64, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, "test-buffer-" + counter.getAndIncrement());
        }
    });

    private final LightIdProvider.Loader loader = new LightIdProvider.Loader() {
        @NotNull
        @Override
        public LightIdProvider.Segment require(@NotNull String name, int block, int count) {
            if (name.equals("404") && s404.get()) throw new NoSuchElementException("404");
            if (name.equals("403")) {
                System.out.println(Thread.currentThread().getName() + ">>>>403");
                throw new IllegalStateException("403");
            }

            final long s = sleep.get();
            if (s > 0) {
                try {
                    Thread.sleep(s);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count < 100) {
                count = 100;
            }

            long next = sequence.addAndGet(count);
            long start = next - count;

            return new LightIdProvider.Segment(name, block, start, next - 1);
        }

        @NotNull
        @Override
        public List<LightIdProvider.Segment> preload(int block) {
            return Collections.singletonList(require("test", block, 0));
        }
    };

    private LightIdBufferedProvider bufferedProvider() {
        sequence.set(1);
        return new LightIdBufferedProvider(loader, executor);
    }

    private LightIdStampedProvider stampedProvider() {
        sequence.set(1);
        return new LightIdStampedProvider(loader, executor);
    }

    private LightIdProvider directProvider() {
        sequence.set(1);
        return (name, block, timeout) -> {
            if (name.equals("404") && s404.get()) throw new NoSuchElementException("404");
            if (name.equals("403")) throw new IllegalStateException("403");
            return sequence.getAndIncrement();
        };
    }


    @Test
    public void testErr() {
        HashMap<Exception, Exception> err = new HashMap<>();
        LightIdBufferedProvider provider = bufferedProvider();
        for (int i = 0; i < 10; i++) {
            try {
                provider.next("403", 0);
            }
            catch (Exception e) {
                err.put(e, e);
            }
        }
        for (Exception key : err.keySet()) {
            System.out.println("============ print for 403 ===================");
            key.printStackTrace();
        }
    }

    @Test
    public void test404() {
        long s2 = System.currentTimeMillis();
        LightIdProvider directProvider = directProvider();
        try {
            long next = directProvider.next("404", 0);
        }
        catch (Exception e) {
            assertTrue(e instanceof NoSuchElementException);
        }
        finally {
            System.out.println("direct 404 cost = " + (System.currentTimeMillis() - s2));
        }

        long s3 = System.currentTimeMillis();
        LightIdBufferedProvider bufferedProvider = bufferedProvider();
        try {
            long next = bufferedProvider.next("404", 0);
        }
        catch (Exception e) {
            assertTrue(e instanceof NoSuchElementException);
        }
        finally {
            System.out.println("buffered-1 404 cost = " + (System.currentTimeMillis() - s3));
        }

        long s4 = System.currentTimeMillis();
        try {
            long next = bufferedProvider.next("404", 0);
        }
        catch (Exception e) {
            assertTrue(e instanceof NoSuchElementException);
        }
        finally {
            System.out.println("buffered-2 404 cost = " + (System.currentTimeMillis() - s4));
        }

        long s5 = System.currentTimeMillis();
        try {
            s404.set(false);
            bufferedProvider.cleanError("404", 0);
            long next = bufferedProvider.next("404", 0, 10_000_000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("buffered-3 404 cost = " + (System.currentTimeMillis() - s5));
        }
    }

    @Test
    @Disabled("手动执行，时间长")
    public void testNext() {
        sleep.set(0);

        for (int i = 0; i < 10; i++) {
            // 单线程
            int capacity = 500_0000;
            int threads = 1;
            System.out.printf("\n[capacity=%9d, threads=%3d] speed(ms)", capacity, threads);

            run(true, directProvider(), capacity, threads);
            run(true, bufferedProvider(), capacity, threads);
            run(true, stampedProvider(), capacity, threads);
        }
        for (int i = 0; i < 10; i++) {
            // 多线程
            int capacity = 10_0000;
            int threads = 500;
            System.out.printf("\n[capacity=%9d, threads=%3d] speed(ms)", capacity, threads);

            run(true, directProvider(), capacity, threads);
            run(true, bufferedProvider(), capacity, threads);
            run(true, stampedProvider(), capacity, threads);
        }
    }

    /*
    [4080, sleep= 0, step= 40500] speed(ms), BufferedTest=34482, Buffered=28248, Stamped=22222
    [4081, sleep= 1, step= 41000] speed(ms), BufferedTest=36764, Buffered=25000, Stamped=15337
    [4082, sleep= 2, step= 41500] speed(ms), BufferedTest=34722, Buffered=26881, Stamped=20920
    [4083, sleep= 3, step= 42000] speed(ms), BufferedTest=34246, Buffered=26881, Stamped=22123
    [4084, sleep= 4, step= 42500] speed(ms), BufferedTest=36231, Buffered=28089, Stamped=16129
    [4085, sleep= 5, step= 43000] speed(ms), BufferedTest=36231, Buffered=25906, Stamped=18382
    [4086, sleep= 6, step= 43500] speed(ms), BufferedTest=35714, Buffered=22222, Stamped=21834
    [4087, sleep= 7, step= 44000] speed(ms), BufferedTest=27777, Buffered=23809, Stamped=16025
    [4088, sleep= 8, step= 44500] speed(ms), BufferedTest=28409, Buffered=27027, Stamped=21834
    [4089, sleep= 9, step= 45000] speed(ms), BufferedTest=35971, Buffered=24271, Stamped= 7012
     */
    @Test
    @Disabled("手动执行，速度对比测试")
    public void testSpeed() {
        int capacity = 500_0000;
        int threads = 500;

        LightIdProvider directProvider = directProvider();
        LightIdBufferedProvider bufferedProvider = bufferedProvider();
        LightIdStampedProvider stampedProvider = stampedProvider();

        for (int i = 0; i < 10000; i++) {
            int s = i % 10;
            sleep.set(s);
            int step = ((i + 1) % 100) * 500;
            bufferedProvider.setMaxCount(step);
            stampedProvider.setMaxCount(step);
            System.out.printf("\n[%3d, sleep=%2d, step=%6d] speed(ms)", i, s, step);

            run(false, directProvider, capacity, threads);
            run(false, bufferedProvider, capacity, threads);
            run(false, stampedProvider, capacity, threads);
        }
    }

    private void run(boolean check, final LightIdProvider provider, int capacity, int threads) {
        try {
            System.gc();
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            // ignore
        }
        final long timeout = 1000000;
        final boolean errorBreak = true;
        final LinkedHashMap<Long, String> ids = new LinkedHashMap<>(capacity);
        final AtomicInteger counter = new AtomicInteger(capacity);

        final CountDownLatch latchStart = new CountDownLatch(threads);
        final CountDownLatch latchStop = new CountDownLatch(threads);
        final String pname = provider.getClass().getSimpleName().replaceAll("\\$.*$|LightId|Provider", "");
        //
        final long s0 = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            final String name = "TEST-" + (i % 10);
            new Thread() {
                @Override
                public void run() {
                    try {
                        latchStart.await();
                        while (counter.getAndDecrement() > 0) {
                            long id;
                            try {
                                id = provider.next(name, 0, timeout);
                            }
                            catch (RuntimeException e) {
                                if (errorBreak) {
                                    throw e;
                                }
                                else {
                                    System.err.println(this.getName() + " error=" + e.getMessage());
                                    continue;
                                }
                            }
                            if (check) {
                                synchronized (ids) {
                                    final String old = ids.put(id, this.getName());
                                    if (old != null) {
                                        throw new RuntimeException(pname + " duplicated id=" + LightIdUtil.toLightId(id));
                                    }
                                }
                            }
                            //Thread.sleep(5);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                    finally {
                        latchStop.countDown();
                    }
                }
            }.start();
            latchStart.countDown();
        }
        try {
            latchStop.await();
            System.out.printf(", %s=%5d", pname, (capacity / (System.currentTimeMillis() - s0)));
            //
            if (check) {
                Map<String, Long> map = new HashMap<>();
                long old = 0;
                for (Map.Entry<Long, String> entry : ids.entrySet()) {
                    Long id = entry.getKey();
                    String name = entry.getValue();
                    Long ck = map.get(name);
                    if (ck != null && id <= ck) {
                        throw new RuntimeException(name + " not increasing id=" + LightIdUtil.toLightId(id) + "old=" + LightIdUtil.toLightId(ck));
                    }
                    map.put(name, id);
                    if (threads == 1) {
                        long seq = LightIdUtil.sequenceLong(id);
                        if (old + 1 != seq) {
                            System.err.println("lost seq, old-seq=" + old + ", cur-seq=" + seq);
                        }
                        old = seq;
                    }
                }
                int sz = ids.size();
                if (capacity != sz) {
                    throw new RuntimeException("capacity=" + capacity + " not equals ids'size=" + sz);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
