package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

/**
 * @author trydofor
 * @since 2022-11-21
 */
class StopWatchTest {

    private final StopWatch stopWatch = new StopWatch();

    @Test
    void start() throws InterruptedException {
        {
            final StopWatch.Watch watch = stopWatch.start("flat1");
            Thread.sleep(100);
            watch.close();
        }
        new Thread(() -> {
            try (StopWatch.Watch w = stopWatch.start("tree0:0")) {
                tree1(0);
            }
            catch (InterruptedException e) {
                Testing.printStackTrace(e);
            }
        }).start();

        for (int i = 1; i < 3; i++) {
            try (StopWatch.Watch w = stopWatch.start("tree0:" + i)) {
                tree1(i);
            }
        }
        {
            final StopWatch.Watch watch = stopWatch.start("flat2");
            Thread.sleep(500);

            watch.setMark("mark");
            Assertions.assertEquals("mark", watch.getMark());
            Assertions.assertTrue(watch.isRunning());
            watch.close();
            Assertions.assertFalse(watch.isRunning());
            Assertions.assertTrue(watch.getElapse() > 0);
            Assertions.assertTrue(watch.getElapseMs() > 0);
        }

        // nothing but coverage
        Assertions.assertTrue(stopWatch.totalElapse() > 0);
        Assertions.assertFalse(stopWatch.getWatches().isEmpty());
        Assertions.assertFalse(stopWatch.isRunning());
        Testing.println(stopWatch);
        stopWatch.clear();
    }

    void tree1(int tm) throws InterruptedException {
        try (StopWatch.Watch w = stopWatch.start("tree1:" + tm)) {
            Thread.sleep(100);
            tree2(tm);
        }
    }

    void tree2(int tm) throws InterruptedException {
        try (StopWatch.Watch w = stopWatch.start("tree2:" + tm)) {
            Thread.sleep(100);
        }
    }
}
