package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

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
                e.printStackTrace();
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
            watch.close();
        }
        System.out.println(stopWatch);
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
