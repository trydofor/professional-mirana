package pro.fessional.mirana.stat;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author trydofor
 * @since 2021-07-12
 */
class JvmStatTest {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        final int p = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors=" + p);
        AtomicInteger count = new AtomicInteger(p);
        for (int i = 0; i < p; i++) {
            new Thread(() -> {
                String s = "";
                for (int j = 0; j < 30_000; j++) {
                    s = s + (Math.PI * j);
                }
                count.decrementAndGet();
            }).start();
        }

        while (count.get() > 0) {
            final JvmStat.Stat stat = JvmStat.stat();
            Runtime runtime = Runtime.getRuntime();
            System.out.println(stat);
            System.out.println("runtime total=" + runtime.totalMemory()
                               + ", free=" + runtime.freeMemory()
                               + ", max=" + runtime.maxMemory()
            );
            Thread.sleep(1_000L);
        }
    }
}
