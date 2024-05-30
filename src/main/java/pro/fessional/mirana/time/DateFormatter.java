package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.text.HalfCharUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <pre>
 * Thread-safe. Faster than normal formatter.
 *
 * - DateTimeFormatter is immutable and thread-safe.
 * - DateFormat is not thread-safe.
 *
 * see <a href="https://javarevisited.blogspot.com/2013/01/threadlocal-memory-leak-in-java-web.html">threadlocal-memory-leak-in-java-web</a>
 * see <a href="https://stackoverflow.com/questions/17968803/threadlocal-memory-leak">threadlocal-memory-leak</a>
 *
 * </pre>
 *
 * @author trydofor
 * @since 2019-07-03
 */

public class DateFormatter {

    public static final String PTN_DATE_10 = "yyyy-MM-dd";
    public static final String PTN_TIME_08 = "HH:mm:ss";
    public static final String PTN_TIME_12 = "HH:mm:ss.SSS";
    public static final String PTN_FULL_19 = "yyyy-MM-dd HH:mm:ss";
    public static final String PTN_FULL_23 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String PTN_FULL_TZ = "yyyy[-MM][-dd][ ][HH][:mm][:ss][ ][VV]";
    public static final String PTN_FULL_OZ = "yyyy[-MM][-dd][ ][HH][:mm][:ss][ ][xxx]";

    public static final String PTN_TIME_PSE = "H[:m][:s][.SSS]";
    public static final String PTN_DATE_PSE = "[yyyy][yy][-][/][.][M][-][/][.][d]";
    public static final String PTN_FULL_PSE = "[yyyy][yy][-][/][.][M][-][/][.][d][ ]['T'][H][:m][:s][.SSS]";
    public static final String PTN_ZONE_PSE = "[yyyy][yy][-][/][.][M][-][/][.][d][ ]['T'][H][:m][:s][.SSS][XXX][XX][X]['['][ ][VV][']']";
    public static final String PTN_DATE_PSE_US = "[MMMM][MMM][M][-][/][.][d][-][/][.][yyyy][yy]";
    public static final String PTN_FULL_PSE_US = "[MMMM][MMM][M][-][/][.][d][-][/][.][yyyy][yy][ ]['T'][H][:m][:s][.SSS]";
    public static final String PTN_ZONE_PSE_US = "[MMMM][MMM][M][-][/][.][d][-][/][.][yyyy][yy][ ]['T'][H][:m][:s][.SSS][XXXXX][XXXX][XXX][XX][X]['['][ ][VV][']']";

    // This class is immutable and thread-safe.
    public static final DateTimeFormatter FMT_DATE_10 = DateTimeFormatter.ofPattern(PTN_DATE_10);
    public static final DateTimeFormatter FMT_TIME_08 = DateTimeFormatter.ofPattern(PTN_TIME_08);
    public static final DateTimeFormatter FMT_TIME_12 = DateTimeFormatter.ofPattern(PTN_TIME_12);
    public static final DateTimeFormatter FMT_FULL_19 = DateTimeFormatter.ofPattern(PTN_FULL_19);
    public static final DateTimeFormatter FMT_FULL_23 = DateTimeFormatter.ofPattern(PTN_FULL_23);

    public static final DateTimeFormatter FMT_TIME_PSE = DateTimeFormatter.ofPattern(PTN_TIME_PSE);
    public static final DateTimeFormatter FMT_DATE_PSE = DateTimeFormatter.ofPattern(PTN_DATE_PSE);
    public static final DateTimeFormatter FMT_FULL_PSE = DateTimeFormatter.ofPattern(PTN_FULL_PSE);
    public static final DateTimeFormatter FMT_ZONE_PSE = DateTimeFormatter.ofPattern(PTN_ZONE_PSE);
    public static final DateTimeFormatter FMT_DATE_PSE_US = DateTimeFormatter.ofPattern(PTN_DATE_PSE_US);
    public static final DateTimeFormatter FMT_FULL_PSE_US = DateTimeFormatter.ofPattern(PTN_FULL_PSE_US);
    public static final DateTimeFormatter FMT_ZONE_PSE_US = DateTimeFormatter.ofPattern(PTN_ZONE_PSE_US);

    public static final DateTimeFormatter FMT_FULL_TZ = DateTimeFormatter.ofPattern(PTN_FULL_TZ);
    public static final DateTimeFormatter FMT_FULL_OZ = DateTimeFormatter.ofPattern(PTN_FULL_OZ);


    /** no leak, for static */
    public static final ThreadLocal<DateFormat> DATE_FORMAT_19 = ThreadLocal.withInitial(() -> new SimpleDateFormat(PTN_FULL_19));
    /** no leak, for static */
    public static final ThreadLocal<DateFormat> DATE_FORMAT_23 = ThreadLocal.withInitial(() -> new SimpleDateFormat(PTN_FULL_23));


    public static DateFormat full19() {
        return DATE_FORMAT_19.get();
    }

    public static DateFormat full23() {
        return DATE_FORMAT_23.get();
    }


    @NotNull
    public static String fullTz(@Nullable ZonedDateTime date) {
        if (date == null) return Null.Str;
        return FMT_FULL_TZ.format(date);
    }


    @NotNull
    public static String fullTz(@Nullable OffsetDateTime date) {
        if (date == null) return Null.Str;
        return FMT_FULL_OZ.format(date);
    }

    /**
     * Format to yyyy-MM-dd. empty string if null.
     */
    @NotNull
    public static String date10(@Nullable ZonedDateTime date) {
        return date10(date, null);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String full(@Nullable ZonedDateTime date) {
        return full(date, null);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String full19(@Nullable ZonedDateTime date) {
        return full19(date, null);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String full23(@Nullable ZonedDateTime date) {
        return full23(date, null);
    }

    /**
     * Format to HH:mm:ss or HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time(@Nullable ZonedDateTime date) {
        if (date == null || date.getNano() > 999_999) {
            return time12(date, null);
        }
        else {
            return time08(date, null);
        }
    }

    /**
     * Format to HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String time08(@Nullable ZonedDateTime date) {
        return time08(date, null);
    }

    /**
     * Format to HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time12(@Nullable ZonedDateTime date) {
        return time12(date, null);
    }

    /**
     * Format to yyyy-MM-dd at zoneId. empty string if null.
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
     * Format to yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String full(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        if (date.getNano() > 999_999) {
            return full23(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
        }
        else {
            return full19(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
        }
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss at zoneId. empty string if null.
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
     * Format to yyyy-MM-dd HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String full23(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        return full23(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
    }

    /**
     * Format to HH:mm:ss or HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String time(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        if (date.getNano() > 999_999) {
            return time12(date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
        }
        else {
            return time08(date.getHour(), date.getMinute(), date.getSecond());
        }
    }

    /**
     * Format to HH:mm:ss at zoneId. empty string if null.
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
     * Format to HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String time12(@Nullable ZonedDateTime date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        if (zoneId != null && !zoneId.equals(date.getZone())) {
            date = date.withZoneSameInstant(zoneId);
        }
        return time12(date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
    }

    /**
     * Format to yyyy-MM-dd. empty string if null.
     */
    @NotNull
    public static String date10(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return date10(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String full(@Nullable LocalDateTime date) {
        if (date == null) return "";
        if (date.getNano() > 999_999) {
            return full23(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
        }
        else {
            return full19(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
        }
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String full19(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return full19(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String full23(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return full23(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
    }

    /**
     * Format to HH:mm:ss or HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time(@Nullable LocalDateTime date) {
        if (date == null) return "";
        if (date.getNano() > 999_999) {
            return time12(date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
        }
        else {
            return time08(date.getHour(), date.getMinute(), date.getSecond());
        }
    }

    /**
     * Format to HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String time08(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return time08(date.getHour(), date.getMinute(), date.getSecond());
    }

    /**
     * Format to HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time12(@Nullable LocalDateTime date) {
        if (date == null) return "";
        return time12(date.getHour(), date.getMinute(), date.getSecond(), date.getNano());
    }


    /**
     * Format to yyyy-MM-dd. empty string if null.
     */
    @NotNull
    public static String date10(@Nullable LocalDate date) {
        if (date == null) return "";
        return date10(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * Format to HH:mm:ss or HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time(@Nullable LocalTime time) {
        if (time == null) return "";
        if (time.getNano() > 999_999) {
            return time12(time.getHour(), time.getMinute(), time.getSecond(), time.getNano());
        }
        else {
            return time08(time.getHour(), time.getMinute(), time.getSecond());
        }
    }

    /**
     * Format to HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String time08(@Nullable LocalTime time) {
        if (time == null) return "";
        return time08(time.getHour(), time.getMinute(), time.getSecond());
    }

    /**
     * Format to HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time12(@Nullable LocalTime time) {
        if (time == null) return "";
        return time12(time.getHour(), time.getMinute(), time.getSecond(), time.getNano());
    }


    /**
     * Format to yyyy-MM-dd. empty string if null.
     */
    @NotNull
    public static String date10(@Nullable Date date) {
        return full19().format(date).substring(0, 10);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String full(@Nullable Date date) {
        final String ft = full23().format(date);
        if (ft.endsWith(".000")) {
            return ft.substring(0, 19);
        }
        else {
            return ft;
        }
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String full19(@Nullable Date date) {
        return full19().format(date);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String full23(@Nullable Date date) {
        return full23().format(date);
    }

    /**
     * Format to HH:mm:ss or HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time(@Nullable Date date) {
        final String ft = full23().format(date);
        if (ft.endsWith(".000")) {
            return ft.substring(11, 19);
        }
        else {
            return ft.substring(11);
        }
    }

    /**
     * Format to HH:mm:ss. empty string if null.
     */
    @NotNull
    public static String time08(@Nullable Date date) {
        return full19().format(date).substring(11);
    }

    /**
     * Format to HH:mm:ss.SSS. empty string if null.
     */
    @NotNull
    public static String time12(@Nullable Date date) {
        return full23().format(date).substring(11);
    }


    /**
     * Format to yyyy-MM-dd at zoneId. empty string if null.
     */
    @NotNull
    public static String date10(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return date10(dateTime, null);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String full(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        if (dateTime.getNano() > 999_999) {
            return full23(dateTime, null);
        }
        else {
            return full19(dateTime, null);
        }
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss at zoneId. empty string if null.
     */
    @NotNull
    public static String full19(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return full19(dateTime, null);
    }

    /**
     * Format to yyyy-MM-dd HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String full23(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return full23(dateTime, null);
    }

    /**
     * Format to HH:mm:ss or HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String time(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return time(dateTime, null);
    }

    /**
     * Format to HH:mm:ss at zoneId. empty string if null.
     */
    @NotNull
    public static String time08(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return time08(dateTime, null);
    }

    /**
     * Format to HH:mm:ss.SSS at zoneId. empty string if null.
     */
    @NotNull
    public static String time12(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null) return "";
        ZonedDateTime dateTime = zoned(date, zoneId);
        return time12(dateTime, null);
    }

    /**
     * convert date at zoneId
     */
    @NotNull
    public static ZonedDateTime zoned(@NotNull Date date, @Nullable ZoneId zoneId) {
        if (zoneId == null) {
            return date.toInstant().atZone(ThreadNow.sysZoneId());
        }
        else {
            return date.toInstant().atZone(zoneId);
        }
    }

    /**
     * <pre>
     * Guarantee consistency, i.e. the same string, returns consistent results.
     * Fix date string to yyyy-MM-dd HH:mm:ss
     * `HH`, `MM`, `ss` are pseudo-randomly generated based on `yyyy-MM-dd`,
     * which defaults to `1979-01-01`, respectively.
     * </pre>
     *
     * @param date Any date containing numbers, will automatically fix the hours, minutes and seconds.
     * @return Fixed date.
     */
    @NotNull
    public static String fixFull19(@Nullable String date) {
        if (date == null) return "1979-01-01";

        int len = date.length();
        StringBuilder tmp = new StringBuilder(len);
        int seed = 0;
        for (int i = 0; i < len; i++) {
            char c = HalfCharUtil.half(date.charAt(i));
            if (c >= '0' && c <= '9') {
                tmp.append(c);
                seed = 31 * seed + c;
            }
            else {
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
        // get digit
        if (idx[1] >= 0) {
            int cnt = 0;
            int off = idx[1];
            idx[1] = -1;
            int len = str.length();
            for (int i = off; i < len; i++) {
                char c = str.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (cnt == 0) {
                        idx[0] = i;
                    }
                    idx[1] = i;
                    cnt++;
                }
                else {
                    if (cnt > 0) {
                        cnt = max; // reach the max
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

        // padding
        int len = idx[1] - idx[0];
        if (idx[1] > 0 && len > 0) {
            for (int i = len; i < max; i++) {
                buf.append('0');
            }
            buf.append(str, idx[0], idx[1]);
        }
        else {
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
        fixDate10(sb, y, m, d);
        sb.append(' ');
        fixTime08(sb, h, i, s);
        return sb.toString();
    }

    private static String full23(int y, int m, int d, int h, int i, int s, int n) {
        StringBuilder sb = new StringBuilder(19);
        fixDate10(sb, y, m, d);
        sb.append(' ');
        fixTime08(sb, h, i, s);
        fixNano(sb, n);
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

    private static String time12(int h, int m, int s, int n) {
        StringBuilder sb = new StringBuilder(8);
        fixTime08(sb, h, m, s);
        fixNano(sb, n);
        return sb.toString();
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

    private static void fixNano(StringBuilder buf, int n) {
        buf.append('.');
        // the nano-of-second, from 0 to 999,999,999
        int s = n / 1_000_000;
        if (s < 10) buf.append('0');
        if (s < 100) buf.append('0');
        buf.append(s);
    }
}
