package pro.fessional.mirana.time;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.text.HalfCharUtil;

import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

/**
 * 解析固定格式的，包含日期数字的字符串，支持以下格式<p>
 * 可以处理末尾的填充，日期以01填充，时间以00填充。<p>
 * (date8) yyyyMMdd<p>
 * (datetime14) yyyyMMddHHmmss<p>
 * (datetime17) yyyyMMddHHmmssSSS<p>
 * (date8) MMddyyyy<p>
 * (datetime14) MMddyyyyHHmmss<p>
 * (datetime17) MMddyyyyHHmmssSSS<p>
 * (time6) HHmmss<p>
 * (time9) HHmmssSSS<p>
 *
 * @author trydofor
 * @see DateNumber
 * @since 2019-06-26
 */
public class DateParser {

    protected DateParser() {
    }

    public static final TemporalQuery<LocalTime> QueryTime = (temporal) -> {
        if (temporal instanceof LocalTime) {
            return (LocalTime) temporal;
        }

        if (temporal.isSupported(NANO_OF_DAY)) {
            return LocalTime.ofNanoOfDay(temporal.getLong(NANO_OF_DAY));
        }

        // commonly never here
        if (temporal.isSupported(HOUR_OF_DAY)) {
            int m = 0;
            if (temporal.isSupported(MINUTE_OF_HOUR)) {
                m = temporal.get(MINUTE_OF_HOUR);
            }

            int s = 0;
            if (temporal.isSupported(SECOND_OF_MINUTE)) {
                s = temporal.get(SECOND_OF_MINUTE);
            }

            int n = 0;
            if (temporal.isSupported(NANO_OF_SECOND)) {
                n = temporal.get(NANO_OF_SECOND);
            }
            else if (temporal.isSupported(MILLI_OF_SECOND)) {
                n = temporal.get(MILLI_OF_SECOND) * 1000_000;
            }
            return LocalTime.of(temporal.get(HOUR_OF_DAY), m, s, n);
        }

        return null;
    };

    public static final TemporalQuery<LocalDate> QueryDate = (temporal) -> {
        if (temporal instanceof LocalDate) {
            return (LocalDate) temporal;
        }

        if (temporal.isSupported(EPOCH_DAY)) {
            return LocalDate.ofEpochDay(temporal.getLong(EPOCH_DAY));
        }

        // commonly never here
        if (temporal.isSupported(YEAR)) {
            int m = 1;
            if (temporal.isSupported(MONTH_OF_YEAR)) {
                m = temporal.get(MONTH_OF_YEAR);
            }

            int d = 1;
            if (temporal.isSupported(DAY_OF_MONTH)) {
                d = temporal.get(DAY_OF_MONTH);
            }

            return LocalDate.of(temporal.get(YEAR), m, d);
        }

        return null;
    };

    public static final TemporalQuery<LocalDateTime> QueryDateTime = (temporal) -> {
        if (temporal instanceof LocalDateTime) {
            return (LocalDateTime) temporal;
        }

        if (temporal instanceof ZonedDateTime) {
            return ((ZonedDateTime) temporal).toLocalDateTime();
        }

        if (temporal instanceof OffsetDateTime) {
            return ((OffsetDateTime) temporal).toLocalDateTime();
        }

        LocalDate date = QueryDate.queryFrom(temporal);
        if (date == null) return null;

        LocalTime time = QueryTime.queryFrom(temporal);
        if (time == null) {
            time = LocalTime.of(0, 0);
        }

        return LocalDateTime.of(date, time);
    };

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalTime parseTime(@NotNull CharSequence num) {
        return parseTime(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalDate parseDate(@NotNull CharSequence num) {
        return parseDate(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static Date parseUtilDate(@NotNull CharSequence num) {
        return parseUtilDate(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalDateTime parseDateTime(@NotNull CharSequence num) {
        return parseDateTime(num, 0);
    }

    @NotNull
    public static ZonedDateTime parseZoned(@NotNull CharSequence str) {
        return parseZoned(str, null, 0);
    }

    @NotNull
    public static ZonedDateTime parseZoned(@NotNull CharSequence str, ZoneId zid) {
        return parseZoned(str, zid, 0);
    }

    @NotNull
    public static LocalTime parseTime(@NotNull CharSequence str, Iterable<DateTimeFormatter> dtf) {
        final TemporalAccessor ta = parseTemporal(str, dtf, false);
        final LocalTime dt = ta.query(QueryTime);
        if (dt == null) {
            throw new DateTimeException("Unable to obtain LocalTime " + ta + " of type " + ta.getClass().getName());
        }
        return dt;
    }

    @NotNull
    public static LocalDate parseDate(@NotNull CharSequence str, Iterable<DateTimeFormatter> dtf) {
        final TemporalAccessor ta = parseTemporal(str, dtf, false);
        final LocalDate dt = ta.query(QueryDate);
        if (dt == null) {
            throw new DateTimeException("Unable to obtain LocalDate " + ta + " of type " + ta.getClass().getName());
        }
        return dt;
    }

    @NotNull
    public static LocalDateTime parseDateTime(@NotNull CharSequence str, Iterable<DateTimeFormatter> dtf) {
        final TemporalAccessor ta = parseTemporal(str, dtf, false);
        final LocalDateTime dt = ta.query(QueryDateTime);
        if (dt == null) {
            throw new DateTimeException("Unable to obtain LocalDateTime " + ta + " of type " + ta.getClass().getName());
        }
        return dt;
    }

    @NotNull
    public static ZonedDateTime parseZoned(@NotNull TemporalAccessor ta, ZoneId zid) {
        if (ta instanceof ZonedDateTime) {
            ZonedDateTime zdt = (ZonedDateTime) ta;
            return zid == null ? zdt : zdt.withZoneSameInstant(zid);
        }

        if (ta instanceof OffsetDateTime) {
            final OffsetDateTime odt = (OffsetDateTime) ta;
            return zid == null ? odt.toZonedDateTime() : odt.atZoneSameInstant(zid);
        }

        final LocalDateTime ldt = ta.query(QueryDateTime);
        final ZoneId z = ta.query(TemporalQueries.zone());
        return concatZoned(ldt, z, zid);
    }

    @NotNull
    private static ZonedDateTime concatZoned(LocalDateTime ldt, ZoneId may, ZoneId zid) {
        if (may == null) {
            if (zid == null) {
                return ZonedDateTime.of(ldt, ZoneId.systemDefault());
            }
            else {
                return ZonedDateTime.of(ldt, zid);
            }
        }
        else {
            if (zid == null) {
                return ZonedDateTime.of(ldt, may);
            }
            else {
                return ZonedDateTime.of(ldt, may).withZoneSameInstant(zid);
            }
        }
    }

    @NotNull
    public static ZonedDateTime parseZoned(@NotNull CharSequence str, ZoneId zid, Iterable<DateTimeFormatter> dtf) {
        final TemporalAccessor ta = parseTemporal(str, dtf, false);
        return parseZoned(ta, zid);
    }

    @NotNull
    public static OffsetDateTime parseOffset(@NotNull TemporalAccessor ta, ZoneId zid) {
        if (ta instanceof ZonedDateTime) {
            ZonedDateTime zdt = (ZonedDateTime) ta;
            if (zid == null) {
                return zdt.toOffsetDateTime();
            }
            else {
                return zdt.withZoneSameInstant(zid).toOffsetDateTime();
            }
        }

        if (ta instanceof OffsetDateTime) {
            OffsetDateTime odt = (OffsetDateTime) ta;
            if (zid == null) {
                return odt;
            }
            else {
                return odt.atZoneSameInstant(zid).toOffsetDateTime();
            }
        }

        final LocalDateTime ldt = ta.query(QueryDateTime);
        ZoneOffset zof = ta.query(TemporalQueries.offset());
        if (zof != null) {
            final OffsetDateTime odt = OffsetDateTime.of(ldt, zof);
            if (zid == null) {
                return odt;
            }
            else {
                return odt.atZoneSameInstant(zid).toOffsetDateTime();
            }
        }

        final ZoneId z = ta.query(TemporalQueries.zone());
        return concatZoned(ldt, z, zid).toOffsetDateTime();
    }

    @NotNull
    public static OffsetDateTime parseOffset(@NotNull CharSequence str, ZoneId zid, Iterable<DateTimeFormatter> dtf) {
        final TemporalAccessor ta = parseTemporal(str, dtf, false);
        return parseOffset(ta, zid);
    }

    @NotNull
    public static LocalTime parseTime(@NotNull CharSequence str, DateTimeFormatter... dtf) {
        return parseTime(str, Arrays.asList(dtf));
    }

    @NotNull
    public static LocalDate parseDate(@NotNull CharSequence str, DateTimeFormatter... dtf) {
        return parseDate(str, Arrays.asList(dtf));
    }

    @NotNull
    public static LocalDateTime parseDateTime(@NotNull CharSequence str, DateTimeFormatter... dtf) {
        return parseDateTime(str, Arrays.asList(dtf));
    }

    @NotNull
    public static ZonedDateTime parseZoned(@NotNull CharSequence str, ZoneId zid, DateTimeFormatter... dtf) {
        return parseZoned(str, zid, Arrays.asList(dtf));
    }

    @NotNull
    public static OffsetDateTime parseOffset(@NotNull CharSequence str, ZoneId zid, DateTimeFormatter... dtf) {
        return parseOffset(str, zid, Arrays.asList(dtf));
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p>
     * (time6) HHmmss<p>
     * (time9) HHmmssSSS<p>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static LocalTime parseTime(@NotNull CharSequence str, int off) {
        String num = digit(str, off, Ptn.TIME);
        int len = num.length();
        if (len != 6 && len != 9) {
            throw new DateTimeException("only support time6,time9 format, but " + num);
        }
        return time(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p>
     * (date8) yyyyMMdd<p>
     * (date8) MMddyyyy<p>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static LocalDate parseDate(@NotNull CharSequence str, int off) {
        String num = digit(str, off, Ptn.DATE);
        int len = num.length();
        if (len != 8) {
            throw new DateTimeException("only support date8 format, but " + num);
        }

        return date(num);
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p>
     * 支持全类型<p>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static Date parseUtilDate(@NotNull CharSequence str, int off) {
        String num = digit(str, off, Ptn.FULL);
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(num.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.parseInt(num.substring(4, 6)) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(num.substring(6, 8)));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(num.substring(8, 10)));
        cal.set(Calendar.MINUTE, Integer.parseInt(num.substring(10, 12)));
        cal.set(Calendar.SECOND, Integer.parseInt(num.substring(12, 14)));
        cal.set(Calendar.MILLISECOND, Integer.parseInt(num.substring(14)));
        return cal.getTime();
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p>
     * (datetime14) yyyyMMddHHmmss<p>
     * (datetime17) yyyyMMddHHmmssSSS<p>
     * (datetime14) MMddyyyyHHmmss<p>
     * (datetime17) MMddyyyyHHmmssSSS<p>
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static LocalDateTime parseDateTime(@NotNull CharSequence str, int off) {
        String num = digit(str, off, Ptn.FULL);
        int len = num.length();
        if (len != 14 && len != 17) {
            throw new DateTimeException("only support datetime14,datetime17 format, but " + num);
        }

        LocalDate ld = date(num);
        LocalTime lt = time(num, 8);
        return LocalDateTime.of(ld, lt);
    }

    /**
     * 把任意包含日期信息的数字变成日期，解析时只关注数字，忽略非数字字符<p>
     * (datetime14) yyyyMMddHHmmss<p>
     * (datetime17) yyyyMMddHHmmssSSS<p>
     * (datetime14) MMddyyyyHHmmss<p>
     * (datetime17) MMddyyyyHHmmssSSS<p>
     * 时区部分位于时间之后由`+- [`分隔，仅支持offset和zid格式，且zid优先于offset<p>
     * V time-zone ID             zone-id   America/Los_Angeles; Z; -08:30<p>
     * O localized zone-offset    offset-O  GMT+8; GMT+08:00; UTC-08:00;<p>
     * X zone-offset 'Z' for zero offset-X  Z; -08; -0830; -08:30; -083015; -08:30:15;<p>
     * x zone-offset              offset-x  +0000; -08; -0830; -08:30; -083015; -08:30:15;<p>
     * Z zone-offset              offset-Z  +0000; -0800; -08:00;<p>
     * ISO_ZONED_DATE_TIME +01:00[Europe/Paris]
     *
     * @param str 任意包括全角或半角数字的字符串
     * @param zid 期望的zone
     * @param off 数字位置偏移量，不考虑非数字
     * @return 日期
     */
    @NotNull
    public static ZonedDateTime parseZoned(@NotNull CharSequence str, ZoneId zid, int off) {
        String ptn = digit(str, off, Ptn.ZONE);
        int ztk = 0;
        if (ptn.length() > 14 && ptn.charAt(14) == '@') {
            ztk = 14;
        }
        else if (ptn.length() > 17 && ptn.charAt(17) == '@') {
            ztk = 17;
        }

        if (ztk != 14 && ztk != 17) {
            throw new DateTimeException("only support datetime14, datetime17 format, but " + ptn);
        }

        final String num = ptn.substring(0, ztk);
        final String zzz = ptn.substring(ztk + 1);

        ZoneId z = zid(zzz);
        LocalDateTime ldt = LocalDateTime.of(date(num), time(num, 8));
        return concatZoned(ldt, z, zid);
    }

    /**
     * 无异常解析，返回最优匹配（无异常，正确解析数量最多）
     *
     * @param str   任意包括全角或半角数字的字符串
     * @param dtf   尝试的formatter
     * @param quiet 返回null而非异常
     * @return 日期
     */
    @Contract("_,_,false->!null")
    public static TemporalAccessor parseTemporal(@NotNull CharSequence str, @NotNull Iterable<DateTimeFormatter> dtf, boolean quiet) {
        QuietPos best = null;

        for (QuietPos qp : parseTemporal(dtf, str, true)) {
            if (best == null) {
                best = qp;
            }
            else {
                final TemporalAccessor ta = qp.getTemporal();
                if (best.getTemporal() == null && ta != null) {
                    best = qp;
                    continue;
                }
                final int ix = qp.getIndex();
                if (best.getIndex() < ix) {
                    best = qp;
                }
            }
        }

        if (best == null) {
            if (quiet) {
                return null;
            }
            else {
                throw new DateTimeParseException("can not apply any Formatter to parse", str, -1);
            }
        }

        final TemporalAccessor tp = best.getTemporal();
        if (tp == null) {
            if (quiet) {
                return null;
            }

            final RuntimeException ex = best.getException();
            if (ex != null) {
                throw ex;
            }

            final String message = best.getFormatter().toString();
            throw new DateTimeParseException(message, str, best.getErrorIndexReal());
        }

        return tp;
    }

    /**
     * 全部解析，并按输入顺序返回结果。
     *
     * @param dtf           格式
     * @param str           字符串
     * @param stopOnSuccess 当有完成匹配者时，是否终止后续匹配，性能有关
     * @return 结果
     */
    @NotNull
    public static List<QuietPos> parseTemporal(@NotNull Iterable<DateTimeFormatter> dtf, @NotNull CharSequence str, boolean stopOnSuccess) {
        List<QuietPos> result = new ArrayList<>();
        for (DateTimeFormatter ft : dtf) {
            QuietPos pos = new QuietPos(0);
            pos.formatter = ft;
            try {
                pos.temporal = ft.parse(str, pos);
            }
            catch (RuntimeException e) {
                pos.exception = e;
            }

            result.add(pos);

            if (stopOnSuccess && pos.temporal != null && pos.getErrorIndexReal() < 0) {
                break;
            }
        }
        return result;
    }

    @NotNull
    public static String digit(@Nullable CharSequence str, int off, Ptn ptn) {
        if (str == null) return "";

        int idx = 0;
        StringBuilder[] buff = new StringBuilder[ptn.pad.length];
        buff[idx] = new StringBuilder(ptn.len);

        int cnt = 0;
        int nan = 0;
        int chi = 0;
        final int len = str.length();
        for (; chi < len && cnt - off < ptn.len; chi++) {
            char c = HalfCharUtil.half(str.charAt(chi));
            if (c >= '0' && c <= '9') {
                cnt++;
                if (cnt > off) {
                    buff[idx].append(c);
                    nan = 1;
                }
            }
            else {
                if (nan == 1 && idx < ptn.pad.length - 1) {
                    buff[++idx] = new StringBuilder(ptn.len);
                    nan = 2;
                }

                if (ptn == Ptn.ZONE && idx > 3 && (c == ' ' || c == '\t' || (c != ':' && isZidChar(c)))) {
                    break;
                }
            }
        }

        // 处理MMddyyyy
        if (ptn != Ptn.TIME && idx >= 2 && buff[1].length() <= 2 && isMonth(buff[0]) && !isMonth(buff[2])) {
            StringBuilder tp = buff[2];
            buff[2] = buff[1];
            buff[1] = buff[0];
            buff[0] = tp;
        }

        // 处理填充
        for (int i = 0; i < ptn.pad.length; i++) {
            if (i <= idx) {
                int cln = buff[i].length();
                int sln = ptn.pad[i].length();
                if (cln == sln) {
                    continue;
                }
                else if (cln > sln) {
                    break;
                }

                boolean az = true;
                for (int j = 0; j < cln; j++) {
                    if (buff[i].charAt(j) != '0') {
                        az = false;
                        break;
                    }
                }
                if (az) {
                    buff[i].replace(0, cln, ptn.pad[i]);
                }
                else {
                    buff[i].insert(0, ptn.pad[i], 0, sln - cln);
                }
            }
            else {
                buff[idx].append(ptn.pad[i]);
            }
        }

        // 拼接
        StringBuilder sb = buff[0];
        for (int i = 1; i <= idx; i++) {
            sb.append(buff[i]);
        }

        // 截断
        if (sb.length() > ptn.len) {
            sb.setLength(ptn.len);
        }

        // 处理时区，只取1段
        if (ptn == Ptn.ZONE) {
            sb.append('@');
            boolean spc = false;
            for (int i = chi; i < len; i++) {
                char c = str.charAt(i);
                // +-:0-9a-z/[]
                if (isZidChar(c)) {
                    sb.append(c);
                    spc = true;
                }
                else {
                    if (spc) break;
                }
            }
        }

        return sb.toString();
    }

    public static boolean isZidChar(char c) {
        return c == '+' || c == '-' || c == ':' || c == '/' || c == '[' || c == ']'
               || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static class QuietPos extends ParsePosition {

        private TemporalAccessor temporal;
        private DateTimeFormatter formatter;
        private RuntimeException exception;
        private int error;

        public QuietPos(int index) {
            super(index);
        }

        public int getErrorIndexReal() {
            return error;
        }

        public TemporalAccessor getTemporal() {
            return temporal;
        }

        public DateTimeFormatter getFormatter() {
            return formatter;
        }

        public RuntimeException getException() {
            return exception;
        }

        @Override
        public void setErrorIndex(int ei) {
            this.error = ei;
        }

        @Override
        public int getErrorIndex() {
            return -1;
        }
    }

    // /////////////////////////////

    enum Ptn {
        DATE(8, new String[]{"2000", "01", "01"}),
        TIME(9, new String[]{"00", "00", "00", "000"}),
        FULL(17, new String[]{"2000", "01", "01", "00", "00", "00", "000"}),
        ZONE(17, new String[]{"2000", "01", "01", "00", "00", "00", "000"}),
        ;
        final int len;
        final String[] pad;

        Ptn(int len, String[] pad) {
            this.len = len;
            this.pad = pad;
        }
    }

    private static ZoneId zid(String str) {
        try {
            int p1 = str.indexOf('[');
            if (p1 >= 0) {
                int p2 = str.indexOf(']', p1);
                if (p2 > p1) {
                    return ZoneId.of(str.substring(p1 + 1, p2));
                }
                else {
                    return ZoneId.of(str.substring(p1 + 1));
                }
            }
            else {
                return ZoneId.of(str);
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    private static boolean isMonth(CharSequence str) {
        int len = str.length();
        if (len == 1) {
            char c = str.charAt(0);
            return c >= '1' && c <= '9';
        }
        else if (len == 2) {
            char c1 = str.charAt(0);
            char c2 = str.charAt(1);
            if (c1 == '0' && c2 >= '1' && c2 <= '9') {
                return true;
            }
            return c1 == '1' && c2 >= '0' && c2 <= '2';
        }

        return false;
    }

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
