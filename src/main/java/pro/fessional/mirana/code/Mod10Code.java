package pro.fessional.mirana.code;

/**
 * <pre>
 * Step 1: Set up a two-row matrix, labeled 1 through 22 (or up to 26);
 * 1 being the most significant position in the matrix (i.e., the right-most position).
 * Starting from the least significant position of the matrix (position 22 up to 26),
 * copy each digit of the PIC all the way to position 2 (excluding the position of the check digit
 * shown in the example below by a “?”).
 * Pos 22 21 20 19 18 17 16 15 14 13 12 11 10 09 08 07 06 05 04 03 02 01
 * PIC  9  2  1  2  3  9  1  2  3  4  5  6  7  8  1  2  3  4  5  6  7  ?
 *
 * Step 2: Starting from position 2 of the matrix, add the values (shaded) in the even-numbered boxes.
 * For the example: 7+5+3+1+7+5+3+1+3+1+9 = 45
 *
 * Step 3: Multiply the result of step 2 by 3. For the example: 45 x 3 = 135
 *
 * Step 4: Starting from position 3 of the matrix, add up the values (shaded) in the odd-numbered boxes.
 * For the example: 6+4+2+8+6+4+2+9+2+2 = 45
 *
 * Step 5: Add up the results for steps 3 and 4. For the example: 135+ 45 = 180
 *
 * Step 6: The check digit is the smallest number which, when added to the result obtained through step 5,
 * gives a number that is a multiple of 10.
 * For example: 180 + X = 180; X = 0
 *
 * NOTE: The dimension of the matrix (the number of cells) will vary depending on the length of the
 * Sequential Package ID. In this example, the Sequential Package ID is eight digits long (00000001),
 * requiring a matrix with 22 cells (including the cell for the check digit).
 *
 * </pre>
 *
 * @author trydofor
 * @since 2020-05-21
 */
public class Mod10Code {

    public static int calc(long num) {
        return calc(Long.toString(num));
    }

    public static int calc(CharSequence str) {
        return calc(str, 0, str.length());
    }

    public static int calc(CharSequence str, int off) {
        return calc(str, off, str.length());
    }

    public static int calc(CharSequence str, int off, int len) {
        // even-numbered boxes
        int even = 0;
        // odd-numbered boxes
        int odd = 0;

        // 倒序，第一个有效位就是box的第2位
        for (int i = len - 1, s, p = 2; i >= off; i--) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                s = c - '0';
            }
            else if (c >= '０' && c <= '９') {
                s = c - '０';
            }
            else {
                continue;
            }

            if (p++ % 2 == 0) {
                even += s;
            }
            else {
                odd += s;
            }
        }

        int cnt = even * 3 + odd;
        int chk = cnt % 10;
        return chk == 0 ? 0 : 10 - chk;
    }

    public static boolean check(CharSequence str) {
        return check(str, 0, str.length());
    }

    public static boolean check(CharSequence str, int off) {
        return check(str, off, str.length());
    }

    public static boolean check(CharSequence str, int off, int len) {
        int last = len - 1;
        int chk = calc(str, off, last);
        char c = str.charAt(last);
        int s = -1;
        if (c >= '0' && c <= '9') {
            s = c - '0';
        }
        else if (c >= '０' && c <= '９') {
            s = c - '０';
        }

        return s == chk;
    }

    public static void append(StringBuilder sb) {
        append(sb, 0);
    }

    public static void append(StringBuilder sb, int off) {
        int c = calc(sb, off);
        sb.append(c);
    }
}
