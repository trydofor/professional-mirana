package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static pro.fessional.mirana.id.LightId.BIT_SEQ_BLOCK;
import static pro.fessional.mirana.id.LightId.BIT_SEQ_WHOLE;
import static pro.fessional.mirana.id.LightId.MAX_BLOCK;
import static pro.fessional.mirana.id.LightId.MAX_SEQ_BLOCK;
import static pro.fessional.mirana.id.LightId.MAX_SEQ_WHOLE;
import static pro.fessional.mirana.id.LightId.MIN_BLOCK;
import static pro.fessional.mirana.id.LightId.MIN_SEQ;
import static pro.fessional.mirana.id.LightId.NONE;

/**
 * @author trydofor
 * @since 2019-05-20
 */
public class LightIdUtil {
    protected LightIdUtil() {
    }

    public static boolean valid(@Nullable LightId id) {
        return id != null && valid(id.getBlock(), id.getSequence());
    }

    public static boolean valid(@Nullable Integer block, @Nullable Long sequence) {
        return block != null && sequence != null && valid(block.intValue(), sequence.longValue());
    }

    public static boolean valid(int block, long sequence) {
        if (block < MIN_BLOCK || block > MAX_BLOCK) return false;
        if (block == 0) {
            return sequence >= MIN_SEQ && sequence <= MAX_SEQ_WHOLE;
        }
        else {
            return sequence >= MIN_SEQ && sequence <= MAX_SEQ_BLOCK;
        }
    }

    public static LightId toLightId(@Nullable Long lightId) {
        if (lightId == null) return NONE;
        return toLightId(lightId.longValue());
    }

    @NotNull
    public static LightId toLightId(long lightId) {
        final int block;
        final long sequence;

        if (((lightId >> BIT_SEQ_WHOLE) & 1) == 0) {
            block = 0;
            sequence = lightId & MAX_SEQ_WHOLE;
        }
        else {
            block = (int) ((lightId >> BIT_SEQ_BLOCK) & (MAX_BLOCK - 1)) + 1;
            sequence = lightId & MAX_SEQ_BLOCK;
        }

        if (valid(block, sequence)) {
            return new LightId(block, sequence);
        }
        else {
            return NONE;
        }
    }

    public static long toId(int block, long sequence) {
        if (block <= 0) return sequence;
        long id = MAX_BLOCK | (block - 1);
        id = (id << BIT_SEQ_BLOCK) | sequence;
        return id;
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
        if (valid(lightId.getBlock(), sequence)) {
            return sequence;
        }
        else {
            return elze;
        }
    }

    public static long sequenceLong(long lightId) {
        return lightId & MAX_SEQ_WHOLE;
    }

    private static final long INT_MAX = Integer.MAX_VALUE;

    public static int sequenceInt(long lightId) {
        return (int) (lightId & INT_MAX);
    }
}

