package pro.fessional.mirana.code;


import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-11-14
 */
public class SlotCodeTest {

    @Test
    public void print() {
        int size = 99;
        SlotCode sc = new SlotCode(size);
        for (int i = 0; i < size; i++) {
            Testing.printf("%02d:%02d\n", i + 1, sc.next());
        }
    }

    @Test
    public void single() {
        int size = 10000;
        int ts = 20;
        SlotCode sc = new SlotCode(size);
        final ConcurrentHashMap<Integer, AtomicInteger> count = new ConcurrentHashMap<>(size);
        final long sms = System.currentTimeMillis();
        for (int j = 0; j < ts; j++) {
            for (int i = 0; i < size; i++) {
                Integer next = sc.next();
                count.computeIfAbsent(next, s -> new AtomicInteger(0)).incrementAndGet();
            }
        }

        Testing.println("cost=" + (System.currentTimeMillis() - sms));
        sc.reset();
        int cur = 0;
        int cnt = 0;
        for (Map.Entry<Integer, AtomicInteger> entry : count.entrySet()) {
            int i = entry.getValue().get();
            cnt += i;
            if (i != ts) {
                if (cur++ < 100) {
                    Testing.println(entry.getKey() + "=" + i);
                }
            }
        }
        assertEquals(0, cur, "exist");
        assertEquals(cnt, size * ts, "total");
    }

    @Test
    public void thread() throws InterruptedException {
        int size = 1000000;
        int ts = 10;
        final SlotCode sc = new SlotCode(size);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(ts);
        final ConcurrentHashMap<Integer, AtomicInteger> count = new ConcurrentHashMap<>(size);
        final long sms = System.currentTimeMillis();
        for (int i = 0; i < ts; i++) {
            new Thread(() -> {
                try {
                    start.await();
                }
                catch (InterruptedException e) {
                    Testing.printStackTrace(e);
                }
                for (int j = 0; j < size; j++) {
                    Integer next = sc.next();
                    count.computeIfAbsent(next, s -> new AtomicInteger(0)).incrementAndGet();
                }
                done.countDown();
            }).start();
        }
        start.countDown();
        done.await();
        Testing.println("cost=" + (System.currentTimeMillis() - sms));
        int cur = 0;
        int cnt = 0;
        for (Map.Entry<Integer, AtomicInteger> entry : count.entrySet()) {
            int i = entry.getValue().get();
            cnt += i;
            if (i != ts) {
                if (cur++ < 100) {
                    Testing.println(entry.getKey() + "=" + i);
                }
            }
        }
        assertEquals(0, cur, "exist");
        assertEquals(cnt, size * ts, "total");
    }
}
