package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * <pre>
 * Generate a fixed-capacity slot of random numbers.
 * Consume an empty slot each time a pseudo-random number is generated.
 * Reset the current slot if there are no empty slots.
 * </pre>
 *
 * @author trydofor
 * @since 2019-11-14
 */
@ThreadSafe
public class SlotCode {

    private static final int[] mask = new int[32];
    private static final int full = -1;
    private static final int bits = 32;

    static {
        for (int i = 0; i < mask.length; i++) mask[i] = 1 << (bits - i - 1);
    }

    public final int size;
    private final int last;
    private final Slot[] slot;
    private final Random rand;

    /**
     * Init a fixed capacity [1,size] random slot
     */
    public SlotCode(int size) {
        this(size, ThreadLocalRandom::current);
    }

    public SlotCode(int size, Supplier<Random> rand) {
        this(size, rand.get());
    }

    /**
     * Init a fixed capacity [1,size] random slot whit given random
     */
    public SlotCode(int size, Random rand) {
        final int page = (size - 1) / bits + 1;
        this.size = size;
        this.last = (1 << (bits - size % bits)) - 1;
        this.rand = rand;
        this.slot = new Slot[page];
        for (int i = 0; i < page; i++) {
            slot[i] = new Slot();
        }
        slot[page - 1].value = last;
    }

    public void reset() {
        synchronized (slot) {
            resetSlot();
        }
    }

    public int next() {
        int rst = -1;
        while (rst < 0 || rst > size) {
            final int idx;
            synchronized (slot) {
                idx = select();
            }

            // sync deal
            rst = slot[idx].pickup(rand, idx);
        }

        return rst;
    }

    // sync slot
    private int select() {
        int len = slot.length;
        int off = rand.nextInt(len);
        // rand loop
        for (int i = 0; i < len; i++) {
            int idx = (off + i) % len;
            if (slot[idx].value != full) return idx;
        }
        // full & reset
        resetSlot();

        return off;
    }

    private void resetSlot() {
        int idx = slot.length - 1;
        for (int i = 0; i < idx; i++) slot[i].value = 0;
        slot[idx].value = 0;
    }

    private static class Slot {
        private volatile int value = 0;

        public synchronized int pickup(final Random rand, int page) {
            int len = mask.length;
            int off = rand.nextInt(len);
            int cur = value;
            for (int i = 0; i < len; i++) {
                int idx = (off + i) % len;
                int msk = mask[idx];
                if ((cur & msk) == 0) {
                    value = (cur | msk);
                    return page * bits + idx + 1;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(full);
        System.out.println("--:" + Integer.toBinaryString(full));
        System.out.println("===========");
        for (int i = 0; i < mask.length; i++) {
            System.out.println(String.format("%02d:%32s", i + 1, Integer.toBinaryString(mask[i])).replace(' ', '0'));
        }
        System.out.println("===========");
        for (int i = 1; i <= 36; i++) {
            SlotCode sc = new SlotCode(i);
            System.out.println(String.format("%02d:%32s", i, Integer.toBinaryString(sc.last)).replace(' ', '0'));
        }
    }
}
