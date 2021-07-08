package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * 提供26字符(A-Z)和32字符（0-9A-Z，去掉01OI）编码。
 * 支持补齐填充，被编码数字取值范围是：[0,{@link Long#MIN_VALUE}]
 * <p>
 * log(16;2^63) = 63/4 = 15.75，正数long，最多16个字符。
 *
 * @author trydofor
 * @since 2019-05-20
 */
@ThreadSafe
public class LeapCode {

    public static final long MAX_NUMBER = Long.MAX_VALUE;
    public static final long MIN_NUMBER = 0;

    // Im A9, so 9 is magic, and 9+15 = 24
    private static final int A9 = 9;

    private final char[] dict24 = new char[24];
    private final char[] dict26 = new char[26];
    private final char[] dict32 = new char[32];

    private final Supplier<Random> random;


    /**
     * 使用系统字符字典
     */
    public LeapCode() {
        this("BY2AH0IC9SX4UTV7GP5LNR6FK1WOE8ZQD3JM");
    }

    public LeapCode(@NotNull String seed) {
        this(seed, ThreadLocalRandom::current);
    }

    public LeapCode(@NotNull String seed, Random rand) {
        this(seed, () -> rand);
    }

    /**
     * 自定义字典编码，要求字典不重复字符不少于编码字符数。
     *
     * @param rand 随机数
     * @param seed 混乱的26字母和10数字组合
     * @throws IllegalArgumentException 字典内唯一字符数量位数不足26+10。
     */
    public LeapCode(@NotNull String seed, Supplier<Random> rand) {

        int max = 32;
        int idx32 = 0;
        int idx26 = 0;
        int idx24 = 0;

        int len = seed.length();
        for (int i = 0; i < len && idx32 < max; i++) {
            char c = seed.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c - 32);
            }

            if (find(dict32, idx32, c) >= 0) {
                continue;
            }

            // remove `0`, `1`
            if (c >= '2' && c <= '9') {
                dict32[idx32++] = c;
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                dict26[idx26++] = c;
                // remove `I`,`O`
                if (c != 'I' && c != 'O') {
                    dict32[idx32++] = c;
                    dict24[idx24++] = c;
                }
            }
        }

        if (idx32 != max) {
            throw new IllegalArgumentException("seed=" + seed + " need " + max + " chars, [0-9A-Z]");
        }
        this.random = rand;
    }

    /**
     * 以 26 字符编码
     *
     * @param number 要编码的数字
     * @return 编码后字符串
     */
    @NotNull
    public String encode26(long number) {
        return encode(26, number, -1);
    }

    /**
     * 以 32 字符编码
     *
     * @param number 要编码的数字
     * @return 编码后字符串
     */
    @NotNull
    public String encode32(long number) {
        return encode(32, number, -1);
    }

    /**
     * 以 26 字符编码，编码后字符串不少于len
     *
     * @param number 要编码的数字
     * @param len    要编码的数字
     * @return 编码后字符串
     */
    @NotNull
    public String encode26(long number, int len) {
        return encode(26, number, len);
    }

    /**
     * 以 32 字符编码，编码后字符串不少于len
     *
     * @param number 要编码的数字
     * @param len    要编码的数字
     * @return 编码后字符串
     */
    @NotNull
    public String encode32(long number, int len) {
        return encode(32, number, len);
    }

    /**
     * 以 26/32 字符编码，编码后字符串不少于len
     *
     * @param base   编码的方式，26或32固定
     * @param number 要编码的数字
     * @param len    要编码的数字
     * @return 编码后字符串
     * @throws IllegalArgumentException 如果base不是26或32
     */
    @NotNull
    public String encode(final int base, long number, final int len) {

        final int off;
        final char[] dict;
        StringBuilder sb = new StringBuilder(Math.max(len, 16));

        if (base == 26) {
            dict = dict26;
            off = (int) (number % A9); // 不要动，小魔法自己琢磨，26-16-1
            sb.append(dict24[off]);
            number = number / A9;
        }
        else if (base == 32) {
            dict = dict32;
            off = (int) (number % 15); // 不要动，小魔法自己琢磨，32-16-1
            sb.append(dict24[off + A9]);
            number = number / 15;
        }
        else {
            throw new IllegalArgumentException("base must one of (26,32)");
        }

        while (number > 0) {
            int idx = (int) (number & 15); // 1111
            number = number >>> 4;
            sb.append(dict[off + idx]);
        }

        if (sb.length() >= len) {
            return sb.toString();
        }

        int f2 = off + 16; // 第二段分界
        int ln = dict.length - 16;
        int[] uq = new int[ln / 2];
        int pt = 0;
        Random rand = random.get();
        for (int rnd = rand.nextInt() & Integer.MAX_VALUE; sb.length() < len; pt++) {

            int idx = -1;
            while (idx < 0) {
                idx = (rnd % ln);
                rnd = rnd / ln;

                if (rnd <= 0) {
                    rnd = rand.nextInt() & Integer.MAX_VALUE;
                }

                for (int i : uq) {
                    if (idx == i) {
                        idx = -1;
                        break;
                    }
                }
            }

            uq[pt % uq.length] = idx;

            if (idx >= off) {
                idx = f2 + idx;
                if (idx >= dict.length) {
                    idx = idx % dict.length;
                }
            }

            sb.append(dict[idx]);
        }

        return sb.toString();
    }


    /**
     * 解码出数值, {@link Long#MIN_VALUE}解码失败
     *
     * @param value 编码后字符串
     * @return 原始数字。
     */
    public long decode(@Nullable String value) {
        if (value == null) return Long.MIN_VALUE;
        return decode(value, 0, value.length());
    }

    /**
     * 解码出数值, {@link Long#MIN_VALUE}解码失败
     *
     * @param value 编码后字符串
     * @param off   开始位置
     * @param len   长度
     * @return 原始数字。
     */
    public long decode(@Nullable CharSequence value, int off, int len) {
        if (value == null) return Long.MIN_VALUE;

        int off1 = -1;
        int off2 = -1;
        char[] dict = null;

        long number = 0L;
        int pos = 0;
        for (int i = off; i < len; i++) {
            char c = value.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c - 32);
            }
            else if (c < '0' || (c > '9' && c < 'A') || c > 'Z') {
                continue;
            }


            if (dict == null) {
                off1 = find(dict24, dict24.length, c);

                if (off1 < A9) {
                    dict = dict26;
                }
                else {
                    dict = dict32;
                    off1 = off1 - A9;
                }
                off2 = off1 + 16;
            }
            else {
                long v = find(dict, dict.length, c);
                if (v < off1 || v >= off2) {
                    continue;
                }

                number = number | ((v - off1) << pos);
                pos = pos + 4;
            }
        }

        if (dict == dict32) {
            number = number * 15 + off1;
        }
        else {
            number = number * A9 + off1;
        }

        return number;
    }


    private int find(char[] dict, int max, char c) {
        for (int j = 0; j < max; j++) {
            if (dict[j] == c) return j;
        }
        return -1;
    }
}
