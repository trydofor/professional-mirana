package pro.fessional.mirana.text;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 只考虑汉字截断
 * 一个非ascii字符宽度为2,ascii为1
 */
public class FullCharUtil {
    /**
     * 从左侧截取N个汉字长度的字符（英文算1,汉字算2）
     *
     * @param str   文字
     * @param count 汉字长度
     * @return 截取后，非null
     */
    @NotNull
    public static String leftCut(@Nullable CharSequence str, int count) {
        return leftCut(str, count, 0);
    }

    /**
     * 从左侧截取N个汉字长度的字符（英文算1,汉字算2）
     *
     * @param str   文字
     * @param count 汉字长度
     * @param off   左侧字符偏移量（不管汉字还是半角）
     * @return 截取后，非null
     */
    @NotNull
    public static String leftCut(@Nullable CharSequence str, int count, int off) {
        if (str == null || count < 1) return "";
        int hzCnt = 0;
        final int hfCnt = count * 2;
        StringBuilder sb = new StringBuilder(hfCnt);

        for (int i = Math.max(off, 0); i < str.length(); i++) {
            char c = str.charAt(i);
            hzCnt += c <= Byte.MAX_VALUE ? 1 : 2;
            if (hzCnt == hfCnt) {
                sb.append(c);
                break;
            } else if (hzCnt > hfCnt) {
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 从右侧截取N个汉字长度的字符（英文算1,汉字算2）
     *
     * @param str   文字
     * @param count 汉字长度
     * @return 截取后，非null
     */
    @NotNull
    public static String rightCut(@Nullable CharSequence str, int count) {
        if (str == null) return "";
        return rightCut(str, count, 0);
    }

    /**
     * 从右侧截取N个汉字长度的字符（英文算1,汉字算2）
     *
     * @param str   文字
     * @param count 汉字长度
     * @param off   右侧字符偏移量（不管汉字还是半角）
     * @return 截取后，非null
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
            } else if (hzCnt > hfCnt) {
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.reverse().toString();
    }

    public static int countHalf(@Nullable CharSequence str) {
        if (str == null) return -1;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            count += str.charAt(i) <= Byte.MAX_VALUE ? 1 : 2;
        }
        return count;
    }
}

