package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;

/**
 * 64 bit = 1bit(0 fixed) + 8bit (CRC8) + 55bit(block+sequence) <p>
 * 8bit for Crc8Long encode
 * 55 bit = block(10bit=1023) + sequence(45bit=35184372088831)<p>
 * for 1023 block unstopped 50000 id/second, running<p>
 * (2^45 -1)/(365*24*3600*50000) = 22.3 years<p>
 *
 * @author trydofor
 * @see pro.fessional.mirana.code.Crc8Long
 * @since 2019-05-20
 */
public class LightId {

    public static final LightId NONE = new LightId(-1, -1);
    public static final LightId ZERO = new LightId(0, 0);

    public static final int BIT_LIGHT = 55;

    public static final int BIT_BLOCK = 10;
    public static final int MIN_BLOCK = 0;
    public static final int MAX_BLOCK = (1 << BIT_BLOCK) - 1;

    public static final int BIT_SEQUENCE = BIT_LIGHT - BIT_BLOCK;
    public static final long MIN_SEQUENCE = 0L;
    public static final long MAX_SEQUENCE = (1L << BIT_SEQUENCE) - 1;

    private final int block;
    private final long sequence;

    public LightId(int block, long sequence) {
        this.block = block;
        this.sequence = sequence;
    }

    public int getBlock() {
        return block;
    }

    public long getSequence() {
        return sequence;
    }

    public final int component1() {
        return this.block;
    }

    public final long component2() {
        return this.sequence;
    }

    public long toLong() {
        return LightIdUtil.toId(block, sequence);
    }

    @NotNull
    @Override
    public String toString() {
        return "LightId(block=" + this.block + ", sequence=" + this.sequence + ")";
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

