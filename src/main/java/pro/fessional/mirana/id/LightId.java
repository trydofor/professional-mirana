package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 * 64 bit = 1bit(0 fixed) + 8bit (CRC8) + 1bit(Layout) + 54bit(block+sequence)
 * 8bit reserved for Crc8Long encode, default 0
 * 1bit whole-seq(0); block-seq(1)
 * whole-seq(0) = sequence(54bit=18014398509481983)
 * block-seq(1) = block(9bit=512) + sequence((54-9=45)bit=35184372088831)
 * for 512 block unstopped 50000 id/second, running
 * (2^45 -1)/(365*24*3600*50000) = 22.3 years
 * </pre>
 *
 * @author trydofor
 * @see pro.fessional.mirana.code.Crc8Long
 * @since 2019-05-20
 */
public class LightId {

    public static final LightId NONE = new LightId(-1, -1);
    public static final LightId ZERO = new LightId(0, 0);

    public static final int BIT_LIGHT = 55;

    public static final int BIT_BLOCK = 9;
    public static final int MIN_BLOCK = 0;
    public static final int MAX_BLOCK = 1 << BIT_BLOCK;

    public static final int BIT_SEQ_WHOLE = BIT_LIGHT - 1;
    public static final int BIT_SEQ_BLOCK = BIT_SEQ_WHOLE - BIT_BLOCK;
    public static final long MIN_SEQ = 0L;
    public static final long MAX_SEQ_WHOLE = (1L << BIT_SEQ_WHOLE) - 1;
    public static final long MAX_SEQ_BLOCK = (1L << BIT_SEQ_BLOCK) - 1;

    public static final long TKN_LAYOUT = 1L << BIT_SEQ_WHOLE;

    private final int block;
    private final long sequence;

    /**
     * Constructed by block and sequence.
     * The default value of block is from 0 to 512, which can be modified by LightIdUtil.forceBlockBit.
     * 0 means whole-seq layout, above 1 is block-seq layout.
     */
    public LightId(int block, long sequence) {
        this.block = block;
        this.sequence = sequence;
    }

    /**
     * Get block, 0 means whole-seq layout, above 1 is block-seq layout
     */
    public int getBlock() {
        return block;
    }

    public long getSequence() {
        return sequence;
    }

    public final int component1() {
        return block;
    }

    public final long component2() {
        return sequence;
    }

    public long toLong() {
        return LightIdUtil.toId(block, sequence);
    }

    @NotNull
    @Override
    public String toString() {
        return "LightId(block=" + block + ", sequence=" + sequence + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LightId)) return false;

        LightId lightId = (LightId) o;

        // None
        if ((block < 0 || sequence < 0) && (lightId.block < 0 || lightId.sequence < 0)) return true;

        if (block != lightId.block) return false;
        return sequence == lightId.sequence;
    }

    @Override
    public int hashCode() {
        // None
        if (block < 0 || sequence < 0) return -1;

        int result = block;
        result = 31 * result + (int) (sequence ^ (sequence >>> 32));
        return result;
    }
}

