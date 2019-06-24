package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.text.HalfCharUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 解析固定格式的，包含日期数字的字符串，支持以下格式<p/>
 * (date8) yyyyMMdd<p/>
 * (datetime14) yyyyMMddHHmmss<p/>
 * (datetime17) yyyyMMddHHmmssSSS<p/>
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

        StringBuilder sb = new StringBuilder(max);
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = HalfCharUtil.half(str.charAt(i));
            if (c >= '0' && c <= '9') {
                cnt++;
                if (cnt > off) {
                    sb.append(c);
                }
                if (sb.length() >= max) {
                    break;
                }
            }
        }
        return sb.toString();
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
