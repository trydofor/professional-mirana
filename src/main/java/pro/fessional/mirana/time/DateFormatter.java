package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.text.HalfCharUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 线程安全的，比正常formatter要快
 * <p/>
 * DateTimeFormatter is immutable and thread-safe.<p/>
 * DateFormat is not thread-safe.<p/>
 *
 * @author trydofor
 * @link https://javarevisited.blogspot.com/2013/01/threadlocal-memory-leak-in-java-web.html
 * @link https://stackoverflow.com/questions/17968803/threadlocal-memory-leak
 * @since 2019-07-03
 */

public class DateFormatter {
    private DateFormatter() {
    }

    private static final ZoneId SYS_ZONE_ID = ZoneId.systemDefault();

    public static final String PTN_DATE_10 = "yyyy-MM-dd";
    public static final String PTN_TIME_08 = "HH:mm:ss";
    public static final String PTN_FULL_19 = "yyyy-MM-dd HH:mm:ss";

    // This class is immutable and thread-safe.
    public static final DateTimeFormatter FMT_DATE_10 = DateTimeFormatter.ofPattern(PTN_DATE_10);
    public static final DateTimeFormatter FMT_TIME_08 = DateTimeFormatter.ofPattern(PTN_TIME_08);
    public static final DateTimeFormatter FMT_FULL_19 = DateTimeFormatter.ofPattern(PTN_FULL_19);


    public static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<>();


    public static DateFormat full19() {
        DateFormat df = DATE_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat(PTN_FULL_19);
            DATE_FORMAT.set(df);
        }
        return df;
    }

    /**
     * 格式化成 yyyy-MM-dd 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String date10(@Nullable ZonedDateTime date) {
        return date10(date, null);
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String full19(@Nullable ZonedDateTime date) {
        return full19(date, null);
    }

    /**
     * 格式化成 HH:mm:ss 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String time08(@Nullable ZonedDateTime date) {
        return time08(date, null);
    }

    /**
     * 格式化成 yyyy-MM-dd 格式，null时返回空支付串
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static String date10(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        return date10(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss 格式，null时返回空支付串
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static String full19(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        return full19(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
    }

    /**
     * 格式化成 HH:mm:ss 格式，null时返回空支付串
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static String time08(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        return time08(date.getHour(), date.getMinute(), date.getSecond());
    }

    /**
     * 格式化成 yyyy-MM-dd 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String date10(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return date10(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String full19(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return full19(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
    }

    /**
     * 格式化成 HH:mm:ss 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String time08(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return time08(date.getHour(), date.getMinute(), date.getSecond());
    }


    /**
     * 格式化成 yyyy-MM-dd 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String date10(@Nullable LocalDate date) {
        if (date == null) return "";
        return date10(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * 格式化成 HH:mm:ss 格式，null时返回空支付串
     *
     * @param time 日期
     * @return 结果
     */
    @NotNull
    public static String time08(@Nullable LocalTime time) {
        if (time == null) return "";
        return time08(time.getHour(), time.getMinute(), time.getSecond());
    }


    /**
     * 格式化成 yyyy-MM-dd 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String date10(@Nullable Date date) {
        return full19().format(date).substring(0, 8);
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String full19(@Nullable Date date) {
        return full19().format(date);
    }

    /**
     * 格式化成 HH:mm:ss 格式，null时返回空支付串
     *
     * @param date 日期
     * @return 结果
     */
    @NotNull
    public static String time08(@Nullable Date date) {
        return full19().format(date).substring(9);
    }


    /**
     * 格式化成 yyyy-MM-dd 格式，null时返回空支付串
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static String date10(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return date10(dateTime, null);
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss 格式，null时返回空支付串
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static String full19(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return full19(dateTime, null);
    }

    /**
     * 格式化成 HH:mm:ss 格式，null时返回空支付串
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static String time08(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return time08(dateTime, null);
    }

    /**
     * 把日期转到相应的时区
     *
     * @param date   日期
     * @param zoneId 时区
     * @return 结果
     */
    @NotNull
    public static ZonedDateTime zoned(@NotNull Date date, @Nullable ZoneId zoneId) {
        if (zoneId == null) {
            return date.toInstant().atZone(SYS_ZONE_ID);
        } else {
            return date.toInstant().atZone(zoneId);
        }
    }

    /**
     * 保证一致，即相同的字符串，返回结果一致。
     * 修复日期字符串，yyyy-MM-dd HH:mm:ss
     * `HH`,`MM`,`ss`根据`yyyy-MM-dd`伪随机生成
     * `yyyy-MM-dd`，分别默认为`1979-01-01`
     *
     * @param date 任意包含数字的日期，会自动修复时分秒
     * @return 修复后的日期, null如果输入为null或数字不足8位
     */
    @NotNull
    public static String fixFull19(@Nullable String date) {
        if (date == null) return "1979-01-01";

        StringBuilder tmp = new StringBuilder(date.length());
        int seed = 0;
        for (int i = 0; i < date.length(); i++) {
            char c = HalfCharUtil.half(date.charAt(i));
            if (c >= '0' && c <= '9') {
                tmp.append(c);
                seed = 31 * seed + c;
            } else {
                tmp.append(' ');
            }
        }

        StringBuilder buf = new StringBuilder(19);
        int[] idx = new int[]{0, 0};

        // yyyy-MM-dd
        fillDigit(tmp, 4, idx, buf, 1970, '-');
        fillDigit(tmp, 2, idx, buf, 1, '-');
        fillDigit(tmp, 2, idx, buf, 1, ' ');

        if (seed < 0) {
            seed = seed >>> 1;
        }

        int h = seed % 24;
        int m = seed % 60;
        int s = (seed & 0xFF) % 60;

        // HH:mm:ss
        fillDigit(tmp, 2, idx, buf, h, ':');
        fillDigit(tmp, 2, idx, buf, m, ':');
        fillDigit(tmp, 2, idx, buf, s, '\0');

        return buf.toString();
    }

    private static void fillDigit(CharSequence str, int max, int[] idx, StringBuilder buf, int nil, char end) {
        // 获得数字断
        if (idx[1] >= 0) {
            int cnt = 0;
            int off = idx[1];
            idx[1] = -1;
            for (int i = off; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (cnt == 0) {
                        idx[0] = i;
                    }
                    idx[1] = i;
                    cnt++;
                } else {
                    if (cnt > 0) {
                        cnt = max; // 直接满
                    }
                }
                if (cnt >= max) {
                    break;
                }
            }
            if (idx[1] > 0) {
                idx[1] = idx[1] + 1;
            }
        }

        //填充
        int len = idx[1] - idx[0];
        if (idx[1] > 0 && len > 0) {
            for (int i = len; i < max; i++) {
                buf.append('0');
            }
            buf.append(str, idx[0], idx[1]);
        } else {
            String s = String.valueOf(nil);
            for (int i = s.length(); i < max; i++) {
                buf.append('0');
            }
            buf.append(s);
        }
        if (end > 0) {
            buf.append(end);
        }
    }

    private static String full19(int y, int m, int d, int h, int i, int s) {
        StringBuilder sb = new StringBuilder(19);
        fixFull19(sb, y, m, d, h, i, s);
        return sb.toString();
    }

    private static String date10(int y, int m, int d) {
        StringBuilder sb = new StringBuilder(10);
        fixDate10(sb, y, m, d);
        return sb.toString();
    }

    private static String time08(int h, int m, int s) {
        StringBuilder sb = new StringBuilder(8);
        fixTime08(sb, h, m, s);
        return sb.toString();
    }

    private static void fixFull19(StringBuilder buf, int y, int m, int d, int h, int i, int s) {
        fixDate10(buf, y, m, d);
        buf.append(' ');
        fixTime08(buf, h, i, s);
    }

    private static void fixDate10(StringBuilder buf, int y, int m, int d) {
        buf.append(y).append('-');
        if (m < 10) buf.append('0');
        buf.append(m).append('-');
        if (d < 10) buf.append('0');
        buf.append(d);
    }

    private static void fixTime08(StringBuilder buf, int h, int m, int s) {
        if (h < 10) buf.append('0');
        buf.append(h).append(':');
        if (m < 10) buf.append('0');
        buf.append(m).append(':');
        if (s < 10) buf.append('0');
        buf.append(s);
    }
}
