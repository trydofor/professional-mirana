package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 生成一个固定容量的随机数槽，
 * 每次获得伪随机数（消耗一个空槽）
 * 当没有空槽时，重置当前槽。
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
    private final AtomicInteger[] slot;
    private final Supplier<Random> rand;

    /**
     * 初始化一个固定容量[1,size]的随机槽
     *
     * @param size 设置code的最大值(包括)。
     */
    public SlotCode(int size) {
        this(size, ThreadLocalRandom::current);
    }

    public SlotCode(int size, Random rand) {
        this(size, () -> rand);
    }

    /**
     * 初始化一个固定容量[1,size]的随机槽
     *
     * @param size 设置code的最大值(包括)。
     * @param rand 随机数发生器。
     */
    public SlotCode(int size, Supplier<Random> rand) {
        final int page = (size - 1) / bits + 1;
        this.size = size;
        this.last = (1 << (bits - size % bits)) - 1;
        this.slot = new AtomicInteger[page];
        //
        for (int i = 0; i < page; i++) slot[i] = new AtomicInteger(0);
        slot[page - 1].set(last);
        this.rand = rand;
    }

    public void reset() {
        synchronized (slot) {
            int idx = slot.length - 1;
            for (int i = 0; i < idx; i++) slot[i].set(0);
            slot[idx].set(last);
        }
    }

    public int next() {
        int rst = -1;
        while (rst < 0 || rst > size) {
            final int idx;
            synchronized (slot) {
                idx = select();
            }

            final AtomicInteger deal = slot[idx];
            synchronized (deal) {
                rst = pickup(deal, idx);
            }
        }

        return rst;
    }

    // sync deal
    private int pickup(final AtomicInteger deal, int page) {
        int len = mask.length;
        int off = rand.get().nextInt(len);
        int cur = deal.get();
        for (int i = 0; i < len; i++) {
            int idx = (off + i) % len;
            int msk = mask[idx];
            if ((cur & msk) == 0) {
                deal.set(cur | msk);
                return page * bits + idx + 1;
            }
        }
        return -1;
    }

    // sync slot
    private int select() {
        int len = slot.length;
        int off = rand.get().nextInt(len);
        // rand loop
        for (int i = 0; i < len; i++) {
            int idx = (off + i) % len;
            if (slot[idx].get() != full) return idx;
        }
        // full & reset
        // no need sync deal
        int idx = slot.length - 1;
        for (int i = 0; i < idx; i++) slot[i].set(0);
        slot[idx].set(last);

        return off;
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
