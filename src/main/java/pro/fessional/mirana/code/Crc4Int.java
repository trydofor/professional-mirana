package pro.fessional.mirana.code;

/**
 * crc4 mixed number with the readable last 4th digit.
 * number to crc4int's length mapping
 * <p>
 * 1 - 9_999 = 6 <p>
 * 10_000 - 99_999 = 7 <p>
 * 100_000 - 999_999 = 8 <p>
 * 1_000_000 - 9_999_999 = 9 <p>
 * 10_000_000 - 99_999_999 = 10 <p>
 * 100_000_000  = 11 <p>
 *
 * @author trydofor
 * @since 2018-08-17
 */

public class Crc4Int {

    private static final int MAX_BIT = 31;
    private static final int CRC_BIT = 4;
    private static final int MID_BIT = 14;
    private static final int MSK_MID = (1 << MID_BIT) - 1;
    private static final int MSK_CRC = (1 << CRC_BIT) - 1;

    public static final int MAX_SEQ = 1 << (MAX_BIT - CRC_BIT);
    public static final int MIN_SEQ = 0;
    public static final int MAX_LEN = 10; // 2^31
    public static final int MIN_LEN = 7;  // 2^(CRC_BIT + MID_BIT)


    // return -1 if invalid
    public static int decode(final int seq) {
        int foot = seq & MSK_MID;
        int temp = seq >>> MID_BIT;
        int body = temp & MSK_CRC;
        int head = temp >>> CRC_BIT;
        int result = (head << MID_BIT) | foot;
        return body == crc4(result) ? result : -1;
    }

    public static int encode(final int seq) {
        final int crc = crc4(seq);
        int foot = seq & MSK_MID;
        int head = seq >>> MID_BIT;
        int temp = (head << CRC_BIT) | crc;
        return temp << MID_BIT | foot;
    }

    private static int crc4(int b) {
        final int gply = 19; // x^4 + x^1 + x^0
        final int mask = 15; // 1111
        final int offb = MAX_BIT - 4;
        for (int i = 0; b > mask; i++) { //0111;
            if (((b >>> (MAX_BIT - i)) & 1) == 1) {
                b ^= gply << (offb - i);
            }
        }
        return b;
    }
}
