package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static pro.fessional.mirana.id.LightId.BIT_SEQ_WHOLE;
import static pro.fessional.mirana.id.LightId.MAX_SEQ_WHOLE;
import static pro.fessional.mirana.id.LightId.MIN_BLOCK;
import static pro.fessional.mirana.id.LightId.MIN_SEQ;
import static pro.fessional.mirana.id.LightId.NONE;
import static pro.fessional.mirana.id.LightId.TKN_LAYOUT;

/**
 * @author trydofor
 * @since 2019-05-20
 */
public class LightIdUtil {

    private static boolean BLOCK_FIRST = true;
    private static int BIT_BLOCK = LightId.BIT_BLOCK;
    private static int MAX_BLOCK = LightId.MAX_BLOCK;
    private static int BIT_SEQUENCE = LightId.BIT_SEQ_BLOCK;
    private static long MAX_SEQUENCE = LightId.MAX_SEQ_BLOCK;

    /**
     * 强制使用不同于LightId的默认BIT_BLOCK布局，以调整Block和Sequence比例
     *
     * @param count bit数量，应该在[3-23]之间
     */
    public static void forceBlockBit(int count) {
        if (count < 3) {
            throw new IllegalArgumentException("not enough block, count should more than 3");
        }
        if (count > 23) { // BIT_SEQ_WHOLE - Int.Max = 54 - 31 = 23
            throw new IllegalArgumentException("not enough sequence, count should less than 23");
        }

        BIT_BLOCK = count;
        MAX_BLOCK = 1 << BIT_BLOCK;
        BIT_SEQUENCE = BIT_SEQ_WHOLE - BIT_BLOCK;
        MAX_SEQUENCE = (1L << BIT_SEQUENCE) - 1;
    }

    /**
     * 是采用Block+Sequence布局，还是Sequence+Block布局
     */
    public static void forceBlockFirst(boolean b) {
        BLOCK_FIRST = b;
    }

    /**
     * 是采用Block+Sequence布局，还是Sequence+Block布局
     */
    public static boolean isBlockFirst() {
        return BLOCK_FIRST;
    }

    /**
     * 当前的Block位数
     */
    public static int getBlockBit() {
        return BIT_BLOCK;
    }

    /**
     * 当前的Block最大值
     */
    public static int getBlockMax() {
        return MAX_BLOCK;
    }

    /**
     * 当前的Sequence位数
     */
    public static int getSequenceBit() {
        return BIT_SEQUENCE;
    }

    /**
     * 当前的Sequence最大值
     */
    public static long getSequenceMax() {
        return MAX_SEQUENCE;
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
            return sequence >= MIN_SEQ && sequence <= MAX_SEQUENCE;
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

        if ((lightId & TKN_LAYOUT) == 0) {
            block = 0;
            sequence = lightId & MAX_SEQ_WHOLE;
        }
        else {
            final int mask = MAX_BLOCK - 1;
            if (BLOCK_FIRST) {
                block = (int) ((lightId >> BIT_SEQUENCE) & mask) + 1;
                sequence = lightId & MAX_SEQUENCE;
            }
            else {
                block = (int) (lightId & mask) + 1;
                sequence = (lightId >> BIT_BLOCK) & MAX_SEQUENCE;
            }
        }

        if (valid(block, sequence)) {
            return new LightId(block, sequence);
        }
        else {
            return NONE;
        }
    }

    public static long toId(int block, long sequence) {
        if (block <= MIN_BLOCK) return sequence;

        long id;
        if (BLOCK_FIRST) {
            id = (((long) block - 1) << BIT_SEQUENCE) | sequence;
        }
        else {
            id = (sequence << BIT_BLOCK) | (block - 1);
        }
        return id | TKN_LAYOUT;
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

    public static int sequenceInt(long lightId) {
        return (int) (lightId & Integer.MAX_VALUE);
    }
}

