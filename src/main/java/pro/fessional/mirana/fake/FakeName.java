package pro.fessional.mirana.fake;


import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.code.RandCode;

import java.util.concurrent.ThreadLocalRandom;


/**
 * Generate a random person name
 *
 * @author trydofor
 * @since 2021-03-13.
 */
public abstract class FakeName {

    /**
     * Generate Chinese names with 2-4 chars
     */
    @NotNull
    public static String chinese() {
        @SuppressWarnings("RandomModInteger") final int s = Math.abs(ThreadLocalRandom.current().nextInt() % 3);
        return RandCode.sur(1) + RandCode.cjk(s + 1);
    }

    /**
     * Generate Chinese names with given length chars
     */
    @NotNull
    public static String chinese(int len) {
        return RandCode.sur(1) + RandCode.cjk(Math.max(1, len - 1));
    }
}
