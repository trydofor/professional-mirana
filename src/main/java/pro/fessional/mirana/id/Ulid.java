package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.time.ThreadNow;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 仅简单生成[ULID](https://github.com/ulid/spec)
 * 重度使用，推荐 https://github.com/huxi/sulky/tree/master/sulky-ulid
 *
 * @author trydofor
 * @since 2022-10-12
 */
public class Ulid {

    @NotNull
    public static String next() {
        return next(ThreadNow.millis(), ThreadLocalRandom.current());
    }

    @NotNull
    public static String next(Random random) {
        return next(ThreadNow.millis(), random);
    }

    @NotNull
    public static String next(long time) {
        return next(time, ThreadLocalRandom.current());
    }

    @NotNull
    public static String next(long time, Random random) {
        StringBuilder buff = new StringBuilder(26);
        next(buff, time, random);
        return buff.toString();
    }

    //
    public static void next(@NotNull StringBuilder buff) {
        next(buff, ThreadNow.millis(), ThreadLocalRandom.current());
    }


    public static void next(@NotNull StringBuilder buff, Random random) {
        next(buff, ThreadNow.millis(), random);
    }

    public static void next(@NotNull StringBuilder buff, long time) {
        next(buff, time, ThreadLocalRandom.current());
    }

    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
            'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
            'Y', 'Z',
            };

    public static void next(@NotNull StringBuilder buff, long time, Random random) {
        if ((time & 0xFFFF_0000_0000_0000L) != 0) {
            throw new IllegalArgumentException("UTC timestamp must before 10889-08-02T05:31:50.655Z");
        }

        final byte[] bits80 = new byte[10];
        random.nextBytes(bits80);

        //// time 48 = 3+9*5
        buff.append(CHARS[((int) (time >>> 45)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 40)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 35)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 30)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 25)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 20)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 15)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 10)) & 0x1F]);
        buff.append(CHARS[((int) (time >>> 5)) & 0x1F]);
        buff.append(CHARS[((int) (time)) & 0x1F]);

        //// bits 80 = 16*5
        buff.append(CHARS[((bits80[0] & 0xFF) >>> 3)]);
        buff.append(CHARS[(((bits80[0] << 2) | ((bits80[1] & 0xFF) >>> 6)) & 0x1F)]);
        buff.append(CHARS[(((bits80[1] & 0xFF) >>> 1) & 0x1F)]);
        buff.append(CHARS[(((bits80[1] << 4) | ((bits80[2] & 0xFF) >>> 4)) & 0x1F)]);
        buff.append(CHARS[(((bits80[2] << 1) | ((bits80[3] & 0xFF) >>> 7)) & 0x1F)]);
        buff.append(CHARS[(((bits80[3] & 0xFF) >>> 2) & 0x1F)]);
        buff.append(CHARS[(((bits80[3] << 3) | ((bits80[4] & 0xFF) >>> 5)) & 0x1F)]);
        buff.append(CHARS[(bits80[4] & 0x1F)]);

        buff.append(CHARS[((bits80[5] & 0xFF) >>> 3)]);
        buff.append(CHARS[(((bits80[5] << 2) | ((bits80[6] & 0xFF) >>> 6)) & 0x1F)]);
        buff.append(CHARS[(((bits80[6] & 0xFF) >>> 1) & 0x1F)]);
        buff.append(CHARS[(((bits80[6] << 4) | ((bits80[7] & 0xFF) >>> 4)) & 0x1F)]);
        buff.append(CHARS[(((bits80[7] << 1) | ((bits80[8] & 0xFF) >>> 7)) & 0x1F)]);
        buff.append(CHARS[(((bits80[8] & 0xFF) >>> 2) & 0x1F)]);
        buff.append(CHARS[(((bits80[8] << 3) | ((bits80[9] & 0xFF) >>> 5)) & 0x1F)]);
        buff.append(CHARS[(bits80[9] & 0x1F)]);
    }
}
