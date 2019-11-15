package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * 对 55(63-8) bit长的long型数字，进行伪随机混淆，输入输出都为非负数。
 * 根据pseudoSeed 插入crc8 校验位，提供encode，decode等方法
 *
 * @author trydofor
 * @see <a href="https://github.com/optimasc/java-digests/blob/master/src/com/optimasc/digest/CRC8Digest.java">CRC8Digest.java</a>
 * @since 2019-05-20
 */
@ThreadSafe
public class Crc8Long {

    public static final long MAX_NUMBER = (1L << 55) - 1;
    public static final long MIN_NUMBER = 0;

    /**
     * CRC-8, poly = x^8 + x^2 + x^1 + x^0, init = 0
     */
    // @formatter:off
    private static final int[] CRC8_TABLE = {
        0x00, 0x07, 0x0E, 0x09, 0x1C, 0x1B, 0x12, 0x15,
        0x38, 0x3F, 0x36, 0x31, 0x24, 0x23, 0x2A, 0x2D,
        0x70, 0x77, 0x7E, 0x79, 0x6C, 0x6B, 0x62, 0x65,
        0x48, 0x4F, 0x46, 0x41, 0x54, 0x53, 0x5A, 0x5D,
        0xE0, 0xE7, 0xEE, 0xE9, 0xFC, 0xFB, 0xF2, 0xF5,
        0xD8, 0xDF, 0xD6, 0xD1, 0xC4, 0xC3, 0xCA, 0xCD,
        0x90, 0x97, 0x9E, 0x99, 0x8C, 0x8B, 0x82, 0x85,
        0xA8, 0xAF, 0xA6, 0xA1, 0xB4, 0xB3, 0xBA, 0xBD,
        0xC7, 0xC0, 0xC9, 0xCE, 0xDB, 0xDC, 0xD5, 0xD2,
        0xFF, 0xF8, 0xF1, 0xF6, 0xE3, 0xE4, 0xED, 0xEA,
        0xB7, 0xB0, 0xB9, 0xBE, 0xAB, 0xAC, 0xA5, 0xA2,
        0x8F, 0x88, 0x81, 0x86, 0x93, 0x94, 0x9D, 0x9A,
        0x27, 0x20, 0x29, 0x2E, 0x3B, 0x3C, 0x35, 0x32,
        0x1F, 0x18, 0x11, 0x16, 0x03, 0x04, 0x0D, 0x0A,
        0x57, 0x50, 0x59, 0x5E, 0x4B, 0x4C, 0x45, 0x42,
        0x6F, 0x68, 0x61, 0x66, 0x73, 0x74, 0x7D, 0x7A,
        0x89, 0x8E, 0x87, 0x80, 0x95, 0x92, 0x9B, 0x9C,
        0xB1, 0xB6, 0xBF, 0xB8, 0xAD, 0xAA, 0xA3, 0xA4,
        0xF9, 0xFE, 0xF7, 0xF0, 0xE5, 0xE2, 0xEB, 0xEC,
        0xC1, 0xC6, 0xCF, 0xC8, 0xDD, 0xDA, 0xD3, 0xD4,
        0x69, 0x6E, 0x67, 0x60, 0x75, 0x72, 0x7B, 0x7C,
        0x51, 0x56, 0x5F, 0x58, 0x4D, 0x4A, 0x43, 0x44,
        0x19, 0x1E, 0x17, 0x10, 0x05, 0x02, 0x0B, 0x0C,
        0x21, 0x26, 0x2F, 0x28, 0x3D, 0x3A, 0x33, 0x34,
        0x4E, 0x49, 0x40, 0x47, 0x52, 0x55, 0x5C, 0x5B,
        0x76, 0x71, 0x78, 0x7F, 0x6A, 0x6D, 0x64, 0x63,
        0x3E, 0x39, 0x30, 0x37, 0x22, 0x25, 0x2C, 0x2B,
        0x06, 0x01, 0x08, 0x0F, 0x1A, 0x1D, 0x14, 0x13,
        0xAE, 0xA9, 0xA0, 0xA7, 0xB2, 0xB5, 0xBC, 0xBB,
        0x96, 0x91, 0x98, 0x9F, 0x8A, 0x8D, 0x84, 0x83,
        0xDE, 0xD9, 0xD0, 0xD7, 0xC2, 0xC5, 0xCC, 0xCB,
        0xE6, 0xE1, 0xE8, 0xEF, 0xFA, 0xFD, 0xF4, 0xF3
    };
    // @formatter:on

    /**
     * crc8后8个bit在 63bit中的从右侧起第几位，取值范围[1,60]。
     */
    private final int[] pseudoSeed = new int[]{51, 43, 37, 31, 23, 17, 11, 2};

    /**
     * 使用默认伪随机seed
     */
    public Crc8Long() {
    }

    /**
     * 指定伪随机seed，产生用混淆值(crc8)的插入位置。<br/>
     * 相当于在seed指定的位置(1-base)，用8个bit把55bit的number分成9段。
     *
     * seed要求，每个元素取值范围是[1,60]，元素间数字递减，相差大于2。
     * 元素个数超过8个时，只取前8个，不足8个时，报异常。
     *
     * @param seed 伪随机seed
     * @throws IllegalArgumentException 去重后不足8个元素。
     */
    public Crc8Long(@NotNull int[] seed) {
        if (seed.length < 8) {
            throw new IllegalArgumentException("seed.length can not less than 8");
        }

        for (int i = 0; i < pseudoSeed.length; i++) {
            int v = seed[i];
            if (v < 0 || v > 60) {
                throw new IllegalArgumentException("item value must between [0,60], but item[" + i + "]=" + v);
            }

            if (i > 0 && seed[i - 1] - v < 2) {
                throw new IllegalArgumentException("item value must decrease by 2+, but item[" + i + "] vs item[" + (i - 1) + "]");
            }
        }

        System.arraycopy(seed, 0, pseudoSeed, 0, pseudoSeed.length);
    }


    /**
     * 编码，生成伪随机数字。<p/>
     * 注意：通过比较{@link Long#MIN_VALUE}检测失败情况
     *
     * @param number 编码前数字。
     * @return 成功时，返回编码后数字，失败时返回{@link Long#MIN_VALUE}。
     */
    public long encode(final long number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            return Long.MIN_VALUE;
        }

        final int crc = crc8(number);
        final int idx = pseudoSeed.length - 1;

        long value = number;
        for (int i = idx; i >= 0; i--) {

            int pos = pseudoSeed[i];
            int zero = crc & (1 << (idx - i));

            int rem = pos - 1;
            long head = (value >>> rem) << pos;
            long body = 1L << rem;
            long tail = value & (body - 1);
            if (zero != 0) {
                value = head | body | tail;
            } else {
                value =  head | tail;
            }
        }

        return value;
    }

    /**
     * 解码，从伪随机数字中找到编码前数字<p/>
     * 注意：通过比较{@link Long#MIN_VALUE}检测失败情况
     *
     * @param value 伪随机数字
     * @return 成功时，返回原始数字，解码或校验失败时返回 {@link Long#MIN_VALUE}。
     */
    public long decode(final long value) {
        long crc = 0L;
        long number = value;

        final int len = pseudoSeed.length;
        for (int i = 0; i < len; i++) {
            int rem = pseudoSeed[i] - 1;
            long head = number >>> rem;
            crc = crc | ((head & 1) << (len - i - 1));
            head = (head >>> 1) << rem;
            long tail = number & ((1L << rem) - 1);
            number = head | tail;
        }

        if (crc != crc8(number)) {
            return Long.MIN_VALUE;
        }

        return number;
    }


    private int crc8(long value) {
        int crc = 0;
        int mask = (1 << 8) - 1;
        for (int i = 0; i < 8; i++) {
            crc = CRC8_TABLE[crc ^ (int)(mask & value)];
            value = value >>> 8;
        }
        return crc;
    }
}
