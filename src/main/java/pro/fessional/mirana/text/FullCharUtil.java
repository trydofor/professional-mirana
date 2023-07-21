package pro.fessional.mirana.text;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Only Chinese truncation is supported. A non-ascii character has a width of 2, an ascii character has a width of 1.
 *
 * @author trydofor
 */
public class FullCharUtil {

    /**
     * From the left side, cut N width characters as new string (ascii counts as 1, non-ascii counts as 2).
     *
     * @see #leftCut(CharSequence, int, int)
     */
    @NotNull
    public static String leftCut(@Nullable CharSequence str, int count) {
        return leftCut(str, count, 0);
    }

    /**
     * From the offset on the left side, cut N width characters as new string (ascii counts as 1, non-ascii counts as 2).
     *
     * @param str   the string
     * @param count count of width
     * @param off   offset on the left
     * @return left N width chars
     */
    @NotNull
    public static String leftCut(@Nullable CharSequence str, int count, int off) {
        if (str == null || count < 1) return "";
        int hzCnt = 0;
        final int hfCnt = count * 2;
        StringBuilder sb = new StringBuilder(hfCnt);

        int len = str.length();
        for (int i = Math.max(off, 0); i < len; i++) {
            char c = str.charAt(i);
            hzCnt += c <= Byte.MAX_VALUE ? 1 : 2;
            if (hzCnt == hfCnt) {
                sb.append(c);
                break;
            }
            else if (hzCnt > hfCnt) {
                break;
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * From the right side, cut N width characters as new string (ascii counts as 1, non-ascii counts as 2).
     */
    @NotNull
    public static String rightCut(@Nullable CharSequence str, int count) {
        if (str == null) return "";
        return rightCut(str, count, 0);
    }

    /**
     * From the offset on the right side, cut N width characters as new string (ascii counts as 1, non-ascii counts as 2).
     *
     * @param str   the string
     * @param count count of width
     * @param off   offset on the right.
     * @return left N width chars
     */
    @NotNull
    public static String rightCut(@Nullable CharSequence str, int count, int off) {
        if (str == null || count < 1) return "";
        int hzCnt = 0;
        final int hfCnt = count * 2;
        StringBuilder sb = new StringBuilder(hfCnt);

        for (int i = str.length() - (off < 0 ? 1 : off + 1); i >= 0; i--) {
            char c = str.charAt(i);
            hzCnt += c <= Byte.MAX_VALUE ? 1 : 2;
            if (hzCnt == hfCnt) {
                sb.append(c);
                break;
            }
            else if (hzCnt > hfCnt) {
                break;
            }
            else {
                sb.append(c);
            }
        }
        return sb.reverse().toString();
    }

    public static int countHalf(@Nullable CharSequence str) {
        if (str == null) return -1;
        int count = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            count += str.charAt(i) <= Byte.MAX_VALUE ? 1 : 2;
        }
        return count;
    }
}

