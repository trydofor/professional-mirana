package pro.fessional.mirana.stat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;
import pro.fessional.mirana.time.Sleep;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author trydofor
 * @since 2021-07-12
 */
class JvmStatTest {

    @Test
    void infoStat(){
        int pid = JvmStat.jvmPid();
        String jvm = JvmStat.jvmName();
        Testing.println(pid);
        Testing.println(jvm);

        final JvmStat.Stat stat = JvmStat.stat();
        JvmStat.buildCpuLoad(stat);
        JvmStat.buildMemory(stat);
        JvmStat.buildThread(stat);
        JvmStat.buildRuntime(stat);
        Testing.println(stat);
    }

    @Test
    @Disabled("pressure")
    void pressure() {
        final int p = Runtime.getRuntime().availableProcessors();
        Testing.println("Processors=" + p);
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
            Testing.println(stat);
            Testing.println("runtime total=" + runtime.totalMemory()
                            + ", free=" + runtime.freeMemory()
                            + ", max=" + runtime.maxMemory()
            );
            Sleep.ignoreInterrupt(1_000L);
        }
    }
}
