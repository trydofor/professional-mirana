package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * <pre>
 * Provides 26 chars (A-Z) and 32 chars (0-9A-Z, without UOIL. also called de-oiled U/OIL) encoding.
 * Supports padding, the encoded number in the range: [0,{@link Long#MAX_VALUE}]
 * log(16;2^63) = 63/4 = 15.75, positive long, up to 16 characters.
 *
 * see <a href="https://www.crockford.com/base32.html">base32</a>
 * </pre>
 *
 * @author trydofor
 * @since 2019-05-20
 */
@ThreadSafe
public class LeapCode {

    public static final long MAX_NUMBER = Long.MAX_VALUE;
    public static final long MIN_NUMBER = 0;

    // Im A9, so 9 is magic
    private static final int A9 = 9;

    private final char[] dict22 = new char[22];
    private final char[] dict26 = new char[26];
    private final char[] dict32 = new char[32];

    private final Supplier<Random> random;


    /**
     * default seed dict
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
     * <pre>
     * Customize the seed dict, require that the dict
     * do not have repeat char
     * not less than the number of encode length.
     * </pre>
     *
     * @param rand random
     * @param seed 26 letters and 10 numbers randomly in dict
     * @throws IllegalArgumentException The count of unique chars in the dict is less than 26+10.
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

            if (c >= '0' && c <= '9') {
                dict32[idx32++] = c;
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                dict26[idx26++] = c;
                // remove UOIL
                if (c != 'U' && c != 'O' && c != 'I' && c != 'L') {
                    dict32[idx32++] = c;
                    dict22[idx24++] = c;
                }
            }
        }

        if (idx32 != max) {
            throw new IllegalArgumentException("seed=" + seed + " need " + max + " chars, [0-9A-Z]");
        }
        this.random = rand;
    }

    /**
     * encode in 26 letters (A-Z)
     */
    @NotNull
    public String encode26(long number) {
        return encode(26, number, -1);
    }

    /**
     * encode in 32 de-oiled (A-Z without UOIL)
     */
    @NotNull
    public String encode32(long number) {
        return encode(32, number, -1);
    }

    /**
     * encode in 26 letters (A-Z), and length no less than `len`
     */
    @NotNull
    public String encode26(long number, int len) {
        return encode(26, number, len);
    }

    /**
     * encode in 32 de-oiled (A-Z without UOIL), and length no less than `len`
     */
    @NotNull
    public String encode32(long number, int len) {
        return encode(32, number, len);
    }

    /**
     * encode in 26 or 32, and length no less than `len`
     *
     * @throws IllegalArgumentException if `base` not 26 or 32
     */
    @NotNull
    public String encode(final int base, long number, final int len) {

        final int off1;
        final char[] dict;
        final StringBuilder buff = new StringBuilder(Math.max(len, 16));

        // define dict and mode
        if (base == 26) {
            dict = dict26;
            off1 = (int) (number % A9);
            buff.append(dict22[off1]);
            number = number / A9;
        }
        else if (base == 32) {
            final int mod = dict22.length - A9;
            dict = dict32;
            off1 = (int) (number % mod);
            buff.append(dict22[off1 + A9]);
            number = number / mod;
        }
        else {
            throw new IllegalArgumentException("base must one of (26,32)");
        }

        // use 16 chars to encode
        while (number > 0) {
            int idx = (int) (number & 15); // 1111
            number = number >>> 4;
            buff.append(dict[off1 + idx]);
        }

        int bln = buff.length();
        if (bln >= len) {
            return buff.toString();
        }

        final int off2 = off1 + 16; // 2nd part without 16 char
        final int ln = dict.length - 16;
        final int[] uq = new int[ln / 2];
        final Random rand = random.get();
        for (int pt = 0, rnd = rand.nextInt() & Integer.MAX_VALUE; bln < len; pt++) {

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

            if (idx >= off1) {
                idx = off2 + idx;
                if (idx >= dict.length) {
                    idx = idx % dict.length;
                }
            }

            bln = buff.length();
            int p = rnd % bln;
            if (p > 0) {
                buff.insert(p, dict[idx]);
            }
            else {
                buff.append(dict[idx]);
            }
        }

        return buff.toString();
    }


    /**
     * decode the number, return {@link Long#MIN_VALUE} if fail
     */
    public long decode(@Nullable String value) {
        if (value == null) return Long.MIN_VALUE;
        return decode(value, 0, value.length());
    }

    /**
     * decode the number from offset, return {@link Long#MIN_VALUE} if fail
     *
     * @param value Encoded string, case-insensitive
     * @param off   offset to start
     * @param len   length ot decode
     */
    public long decode(@Nullable CharSequence value, int off, int len) {
        if (value == null || len <= 1 || off < 0 || value.length() < len) return Long.MIN_VALUE;

        final char f = value.charAt(off);
        final int f1 = find(dict22, dict22.length, f >= 'a' && f <= 'z' ? (char) (f - 32) : f);
        if (f1 < 0) return Long.MIN_VALUE;

        final char[] dict;
        final boolean b32;
        final int off1;
        if (f1 < A9) {
            dict = dict26;
            b32 = false;
            off1 = f1;
        }
        else {
            dict = dict32;
            b32 = true;
            off1 = f1 - A9;
        }

        final int off2 = off1 + 16;

        long number = 0L;
        int pos = 0;
        for (int i = off + 1; i < len; i++) {
            char c = value.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c - 32);
            }
            else if (c < '0' || (c > '9' && c < 'A') || c > 'Z') {
                continue;
            }

            if (b32) {
                if (c == 'O') {
                    c = '0';
                }
                else if (c == 'I' || c == 'L') {
                    c = '1';
                }
            }

            long v = find(dict, dict.length, c);
            if (v < off1 || v >= off2) {
                continue;
            }

            number = number | ((v - off1) << pos);
            pos = pos + 4;
        }

        if (dict == dict32) {
            number = number * (dict22.length - A9) + off1;
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
