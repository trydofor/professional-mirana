package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class LightIdBufferedProviderTest {

    private final AtomicLong sleep = new AtomicLong(100);

    private final LightIdProvider.Loader loader = new LightIdProvider.Loader() {

        private final AtomicLong sequence = new AtomicLong(0);

        @NotNull
        @Override
        public LightIdProvider.Segment require(@NotNull String name, int block, int count) {
            if (sleep.get() > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count < 100) {
                count = 100;
            }

            long endin = sequence.addAndGet(count + 1) - 1;
            long start = endin - count;
            // System.out.println(Thread.currentThread().getName() + " require " + count + "  start=" + start + " ,endin=" + endin);

            return new LightIdProvider.Segment(name, block, start, endin);
        }

        @NotNull
        @Override
        public List<LightIdProvider.Segment> preload(int block, int count) {
            return Collections.singletonList(require("test", block, count));
        }
    };

    private final LightIdBufferedProvider bufferedProvider = new LightIdBufferedProvider(loader);

    private final LightIdProvider directProvider = new LightIdProvider() {
        private final AtomicLong sequence = new AtomicLong(0);

        @Override
        public long next(@NotNull String name, int block, long timeout) {
            return sequence.getAndIncrement();
        }
    };


    @Test
    public void next() {
        sleep.set(0);
        int capacity = 10000000;
        long s1 = System.currentTimeMillis();
        run(bufferedProvider, capacity, 0, false);
        System.out.println("============= speed buffer=" + (capacity / (System.currentTimeMillis() - s1)) + "/ms"); // 389/ms

        long s2 = System.currentTimeMillis();
        run(directProvider, capacity, 0, false);
        System.out.println("============= speed direct=" + (capacity / (System.currentTimeMillis() - s2)) + "/ms"); // 692/ms
    }

    private void run(final LightIdProvider provider, final int capacity, final long timeout, final boolean timeoutBreak) {
        final ConcurrentHashMap<Long, Boolean> ids = new ConcurrentHashMap<>(capacity);
        final AtomicInteger counter = new AtomicInteger(capacity);
        final int threads = 50;

        CountDownLatch latchStart = new CountDownLatch(threads);
        CountDownLatch latchStop = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            final String name = "TEST-" + (i % 10);
            new Thread() {
                @Override
                public void run() {
                    try {
                        latchStart.await();
                        while (counter.getAndDecrement() > 0) {
                            long id = 0;
                            try {
                                id = provider.next(name, 0, timeout);
                            } catch (RuntimeException e) {
                                if (timeoutBreak) {
                                    throw e;
                                } else {
                                    System.out.println(this.getName() + e.getMessage());
                                    continue;
                                }
                            }
                            if (ids.containsKey(id)) {
                                System.out.println(this.getName() + " duplicated id=" + LightIdUtil.toLightId(id));
                                System.exit(-1);
                            } else {
                                ids.put(id, Boolean.TRUE);
                            }
                            //Thread.sleep(5);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latchStop.countDown();
                    }
                }
            }.start();
            latchStart.countDown();
        }
        try {
            latchStop.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}