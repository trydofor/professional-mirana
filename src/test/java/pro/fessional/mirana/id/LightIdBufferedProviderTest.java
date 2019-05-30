package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class LightIdBufferedProviderTest {

    private final AtomicLong sleep = new AtomicLong(100);
    private final AtomicBoolean s404 = new AtomicBoolean(true);

    private final LightIdProvider.Loader loader = new LightIdProvider.Loader() {

        private final AtomicLong sequence = new AtomicLong(0);

        @NotNull
        @Override
        public LightIdProvider.Segment require(@NotNull String name, int block, int count) {
            if (name.equals("404") && s404.get()) throw new NoSuchElementException("404");
            if (name.equals("403")) {
                System.out.println(Thread.currentThread().getName() + ">>>>403");
                throw new IllegalStateException("403");
            }

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

            return new LightIdProvider.Segment(name, block, start, endin);
        }

        @NotNull
        @Override
        public List<LightIdProvider.Segment> preload(int block) {
            return Collections.singletonList(require("test", block, 0));
        }
    };

    private final LightIdBufferedProvider bufferedProvider = new LightIdBufferedProvider(loader);

    private final LightIdProvider directProvider = new LightIdProvider() {
        private final AtomicLong sequence = new AtomicLong(0);

        @Override
        public long next(@NotNull String name, int block, long timeout) {
            if (name.equals("404") && s404.get()) throw new NoSuchElementException("404");
            if (name.equals("403")) throw new IllegalStateException("403");
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

    @Test
    public void testErr() {
        HashMap<Exception, Exception> err = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            try {
                long next = bufferedProvider.next("403", 0);
            } catch (Exception e) {
                err.put(e, e);
            }
        }
        for (Exception key : err.keySet()) {
            System.out.println("===============================");
            key.printStackTrace();
        }
    }

    @Test
    public void test404() {
        long s2 = System.currentTimeMillis();
        try {
            long next = directProvider.next("404", 0);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        } finally {
            System.out.println("direct 404 cost = " + (System.currentTimeMillis() - s2));
        }

        long s3 = System.currentTimeMillis();
        try {
            long next = bufferedProvider.next("404", 0);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        } finally {
            System.out.println("buffered-1 404 cost = " + (System.currentTimeMillis() - s3));
        }

        long s4 = System.currentTimeMillis();
        try {
            long next = bufferedProvider.next("404", 0);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        } finally {
            System.out.println("buffered-2 404 cost = " + (System.currentTimeMillis() - s4));
        }

        long s5 = System.currentTimeMillis();
        try {
            s404.set(false);
            bufferedProvider.cleanError("404", 0);
            long next = bufferedProvider.next("404", 0, 10000000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("buffered-3 404 cost = " + (System.currentTimeMillis() - s5));
        }
    }


    private void run(final LightIdProvider provider, final int capacity, final long timeout, final boolean timeoutBreak) {
        final LinkedHashMap<Long, String> ids = new LinkedHashMap<>(capacity);
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
                                    System.out.println(this.getName() + " error=" + e.getMessage());
                                    continue;
                                }
                            }
                            synchronized (ids) {
                                if (ids.containsKey(id)) {
                                    System.out.println(this.getName() + " duplicated id=" + LightIdUtil.toLightId(id));
                                    System.exit(-1);
                                } else {
                                    ids.put(id, this.getName());
                                }
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
            //
            Map<String, Long> map = new HashMap<>();
            for (Map.Entry<Long, String> entry : ids.entrySet()) {
                Long id = entry.getKey();
                String name = entry.getValue();
                Long ck = map.get(name);
                if (ck != null && id <= ck) {
                    System.out.println(name + " not increasing id=" + LightIdUtil.toLightId(id) + "old=" + LightIdUtil.toLightId(ck));
                    System.exit(-1);
                }
                map.put(name, id);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}