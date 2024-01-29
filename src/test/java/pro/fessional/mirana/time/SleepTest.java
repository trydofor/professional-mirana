package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.best.DummyBlock;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author trydofor
 * @since 2024-01-26
 */
class SleepTest {

    @Test
    void ignoreInterrupt() throws InterruptedException {
        Sleep.ignoreInterrupt(Duration.ofMillis(100)); // fix never call

        final AtomicBoolean done = new AtomicBoolean(false);
        final AtomicBoolean loop = new AtomicBoolean(true);
        final Thread td = new Thread(() -> {
            Sleep.ignoreInterrupt(2000, 4000);
            done.set(true);
            while (loop.get()) {
                // busy wait to alive
                DummyBlock.empty();
            }
        });
        td.start();
        Thread.sleep(500);
        td.interrupt();
        Thread.sleep(500);
        Assertions.assertTrue(td.isInterrupted());
        Assertions.assertTrue(td.isAlive());
        loop.set(false);
        Assertions.assertTrue(done.get());
    }

    @Test
    void snoozeInterrupt() throws InterruptedException {
        Sleep.snoozeInterrupt(Duration.ofMillis(100)); // fix never call

        final AtomicBoolean done = new AtomicBoolean(false);
        final Thread td = new Thread(() -> {
            Sleep.snoozeInterrupt(2000, 4000);
            done.set(true);
        });
        td.start();
        Thread.sleep(500);
        td.interrupt();
        Thread.sleep(500);
        Assertions.assertFalse(done.get());
        td.join();
        Assertions.assertTrue(done.get());
    }

    @Test
    void throwsInterrupt() throws InterruptedException {
        Sleep.throwsInterrupt(Duration.ofMillis(100),true); // fix never call

        final AtomicReference<Exception> ex = new AtomicReference<>();
        final AtomicBoolean loop = new AtomicBoolean(true);
        final Thread td = new Thread(() -> {
            try {
                Sleep.throwsInterrupt(2000, 4000, true);
            }
            catch (Exception e) {
                ex.set(e);
            }

            while (loop.get()) {
                // busy wait to alive
                DummyBlock.empty();
            }
        });

        td.start();
        Thread.sleep(500);
        td.interrupt();
        Thread.sleep(500);
        Assertions.assertTrue(td.isInterrupted());
        Assertions.assertTrue(td.isAlive());
        loop.set(false);
        Assertions.assertInstanceOf(IllegalStateException.class, ex.get());
    }
}