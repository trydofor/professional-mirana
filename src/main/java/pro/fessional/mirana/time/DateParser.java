package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.text.HalfCharUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 解析固定格式的，包含日期数字的字符串，支持以下格式<p/>
 * 可以处理末尾的填充，日期以01填充，时间以00填充。<p/>
 * (date8) yyyyMMdd<p/>
 * (datetime14) yyyyMMddHHmmss<p/>
 * (datetime17) yyyyMMddHHmmssSSS<p/>
 * (date8) MMddyyyy<p/>
 * (datetime14) MMddyyyyHHmmss<p/>
 * (datetime17) MMddyyyyHHmmssSSS<p/>
 * (time6) HHmmss<p/>
 * (time9) HHmmssSSS<p/>
 *
 * @author trydofor
 * @see DateNumber
 * @since 2019-06-26
 */
public class DateParser {

    private DateParser() {
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalTime parseTime(@NotNull String num) {
        return parseTime(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalDate parseDate(@NotNull String num) {
        return parseDate(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalDateTime parseDateTime(@NotNull String num) {
        return parseDateTime(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p/>
     * (time6) HHmmss<p/>
     * (time9) HHmmssSSS<p/>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static LocalTime parseTime(@NotNull CharSequence str, int off) {
        String num = digit(str, off, 9);
        int len = num.length();
        if (len != 6 && len != 9) {
            throw new IllegalArgumentException("only support time6,time9 format");
        }
        return time(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p/>
     * (date8) yyyyMMdd<p/>
     * (date8) MMddyyyy<p/>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static LocalDate parseDate(@NotNull CharSequence str, int off) {
        String num = digit(str, off, 8);
        int len = num.length();
        if (len != 8) {
            throw new IllegalArgumentException("only support date8 format");
        }

        return date(num);
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p/>
     * (datetime14) yyyyMMddHHmmss<p/>
     * (datetime17) yyyyMMddHHmmssSSS<p/>
     * (datetime14) MMddyyyyHHmmss<p/>
     * (datetime17) MMddyyyyHHmmssSSS<p/>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static LocalDateTime parseDateTime(@NotNull CharSequence str, int off) {
        String num = digit(str, off, 17);
        int len = num.length();
        if (len != 14 && len != 17) {
            throw new IllegalArgumentException("only support datetime14,datetime17 format");
        }

        LocalDate ld = date(num);
        LocalTime lt = time(num, 8);
        return LocalDateTime.of(ld, lt);
    }

    @NotNull
    public static String digit(@Nullable CharSequence str, int off, int max) {
        if (str == null) return "";

        int idx = 0;
        StringBuilder[] buff = new StringBuilder[4];
        buff[idx] = new StringBuilder(max);

        int cnt = 0;
        int nan = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = HalfCharUtil.half(str.charAt(i));
            if (c >= '0' && c <= '9') {
                cnt++;
                if (cnt > off) {
                    buff[idx].append(c);
                    nan = 1;
                }
            } else {
                if (nan == 1 && idx < 3) {
                    buff[++idx] = new StringBuilder(max);
                    nan = 2;
                }
            }
        }

        // 处理MMddyyyy
        if (idx >= 2) {
            if (buff[0].length() == 2 && buff[1].length() == 2 && buff[2].length() == 4) {
                StringBuilder tp = buff[2];
                buff[2] = buff[1];
                buff[1] = buff[0];
                buff[0] = tp;
            }
        }

        StringBuilder sb = buff[0];
        for (int i = 1; i <= idx; i++) {
            sb.append(buff[i]);
        }

        // 处理填充，日期01填充，时间00填充
        int sbl = sb.length();
        int dst = max - sbl;
        if (dst == 0) {
            return sb.toString();
        } else if (dst < 0) {
            return sb.substring(0, max);
        } else {
            int ldx = sbl - 1;
            if (max <= 8) { // 日期
                if (sbl % 2 != 0) {
                    if (sb.charAt(ldx) == '0') {
                        sb.append('1');
                    } else {
                        sb.insert(ldx, '0');
                    }
                    dst--;
                }
                for (int i = 0; i < dst; i += 2) {
                    sb.append('0').append('1');
                }
            } else { // 时间
                if (sbl % 2 != 0) {
                    if (sb.charAt(ldx) == '0') {
                        sb.append('0');
                    } else {
                        sb.insert(ldx, '0');
                    }
                    dst--;
                }
                for (int i = 0; i < dst; i++) {
                    sb.append('0');
                }
            }
            return sb.toString();
        }
    }

    // /////////////////////////////
    private static LocalDate date(String num) {
        int y = Integer.parseInt(num.substring(0, 4));
        int m = Integer.parseInt(num.substring(4, 6));
        int d = Integer.parseInt(num.substring(6, 8));

        return LocalDate.of(y, m, d);
    }

    private static LocalTime time(String num, int off) {
        int h = Integer.parseInt(num.substring(off, off + 2));
        int m = Integer.parseInt(num.substring(off + 2, off + 4));
        int s = Integer.parseInt(num.substring(off + 4, off + 6));
        int n = num.length() - off <= 6 ? 0 : Integer.parseInt(num.substring(off + 6)) * 1_000_000;

        return LocalTime.of(h, m, s, n);
    }
}
