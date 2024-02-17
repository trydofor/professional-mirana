package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Thead.sleep
 *
 * @author trydofor
 * @since 2023-01-05
 */
public class Sleep {

    /**
     * ignore InterruptedException and immediately wake up with interrupted status
     */
    public static void ignoreInterrupt(@NotNull Duration time) {
        ignoreInterrupt(time.toMillis());
    }

    /**
     * ignore InterruptedException and immediately wake up with interrupted status
     */
    public static void ignoreInterrupt(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * random sleep between min and max and return slept ms.
     * ignore InterruptedException and immediately wake up with interrupted status
     */
    public static long ignoreInterrupt(long min, long max) {
        long ms = ThreadLocalRandom.current().nextLong(min, max);
        long now = System.currentTimeMillis();
        ignoreInterrupt(ms);
        return System.currentTimeMillis() - now;
    }

    /**
     * ignore InterruptedException and continue to sleep, at the end wake up with interrupted status
     */
    public static void snoozeInterrupt(@NotNull Duration time) {
        snoozeInterrupt(time.toMillis());
    }

    /**
     * ignore InterruptedException and continue to sleep, at the end wake up with interrupted status
     */
    public static void snoozeInterrupt(long ms) {
        final long wake = System.currentTimeMillis() + ms;
        boolean rup = false;
        do {
            try {
                //noinspection BusyWait
                Thread.sleep(ms);
                break;
            }
            catch (InterruptedException e) {
                rup = true;
                final long now = System.currentTimeMillis();
                if (now >= wake) {
                    break;
                }
                else {
                    ms = wake - now;
                }
            }
        } while (true);

        if (rup) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * random sleep between min and max and return slept ms.
     * ignore InterruptedException and continue to sleep, at the end wake up with interrupted status
     */
    public static long snoozeInterrupt(long min, long max) {
        long ms = ThreadLocalRandom.current().nextLong(min, max);
        snoozeInterrupt(ms);
        return ms;
    }


    /**
     * throw IllegalStateException, and set whether to keep interrupted status
     */
    public static void throwsInterrupt(@NotNull Duration time, boolean keep) {
        throwsInterrupt(time.toMillis(), keep);
    }

    /**
     * throw IllegalStateException, and set whether to keep interrupted status
     */
    public static void throwsInterrupt(long ms, boolean keep) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            if (keep) {
                Thread.currentThread().interrupt();
            }
            throw new IllegalStateException(e);
        }
    }

    /**
     * random sleep between min and max.
     * throw IllegalStateException, and set whether to keep interrupted status
     */
    public static void throwsInterrupt(long min, long max, boolean keep) {
        long ms = ThreadLocalRandom.current().nextLong(min, max);
        throwsInterrupt(ms, keep);
    }
}
