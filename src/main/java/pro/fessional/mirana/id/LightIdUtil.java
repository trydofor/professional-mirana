package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2019-05-20
 */
public class LightIdUtil {
    protected LightIdUtil() {
    }

    public static boolean valid(@Nullable LightId id) {
        return id != null && id.getBlock() >= 0 && id.getSequence() >= 0;
    }

    public static boolean valid(@Nullable Integer block, @Nullable Long sequence) {
        return validBlock(block) && validSequence(sequence);
    }

    public static boolean valid(int block, long sequence) {
        return validBlock(block) && validSequence(sequence);
    }

    public static boolean validBlock(@Nullable Integer block) {
        if (block == null) return false;
        return validBlock(block.intValue());
    }

    public static boolean validBlock(int block) {
        return block >= LightId.MIN_BLOCK && block <= LightId.MAX_BLOCK;
    }

    public static boolean validSequence(@Nullable Long sequence) {
        if (sequence == null) return false;
        return validSequence(sequence.longValue());
    }

    public static boolean validSequence(long sequence) {
        return sequence >= LightId.MIN_SEQUENCE && sequence <= LightId.MAX_SEQUENCE;
    }

    public static LightId toLightId(@Nullable Long lightId) {
        if (lightId == null) return LightId.NONE;
        return toLightId(lightId.longValue());
    }

    @NotNull
    public static LightId toLightId(long lightId) {
        long sequence = lightId & LightId.MAX_SEQUENCE;
        int block = (int) ((lightId >> LightId.BIT_SEQUENCE) & LightId.MAX_BLOCK);

        if (valid(block, sequence)) {
            return new LightId(block, sequence);
        } else {
            return LightId.NONE;
        }
    }

    public static long toId(int block, long sequence) {
        return (((long) block) << LightId.BIT_SEQUENCE) | sequence;
    }

    public static boolean isNone(@Nullable LightId id) {
        if (id == null) return true;
        return (id.getBlock() < 0 || id.getSequence() < 0);
    }

    public static boolean isZero(@Nullable LightId id) {
        if (id == null) return true;
        return (id.getBlock() == 0 && id.getSequence() == 0);
    }

    public static boolean isZeroSequence(@Nullable LightId id) {
        if (id == null) return true;
        return id.getSequence() == 0;
    }

    public static long sequenceOrElse(@Nullable LightId lightId, long elze) {
        if (lightId == null) return elze;
        final long sequence = lightId.getSequence();
        if (validSequence(sequence)) {
            return sequence;
        } else {
            return elze;
        }
    }

    public static long sequenceLong(long lightId) {
        return lightId & LightId.MAX_SEQUENCE;
    }

    private static final long INT_MAX = Integer.MAX_VALUE;

    public static int sequenceInt(long lightId) {
        return (int) (lightId & INT_MAX);
    }
}

