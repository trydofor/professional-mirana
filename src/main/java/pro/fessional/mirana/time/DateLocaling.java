package pro.fessional.mirana.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author trydofor
 * @since 2019-10-16
 */
public class DateLocaling {

    public static LocalDateTime nowDateTime(ZoneId to) {
        return ZonedDateTime.now(to).toLocalDateTime();
    }

    public static LocalDate nowDate(ZoneId to) {
        return ZonedDateTime.now(to).toLocalDate();
    }

    public static LocalTime nowTime(ZoneId to) {
        return ZonedDateTime.now(to).toLocalTime();
    }

    /**
     * 今天的 0:0:0.0
     *
     * @param to 时区
     * @return 凌晨
     */
    public static LocalDateTime today(ZoneId to) {
        return ZonedDateTime.now(to).toLocalDate().atStartOfDay();
    }

    /**
     * 月初的 0:0:0.0
     *
     * @param to 时区
     * @return 月初的 0:0:0.0
     */
    public static LocalDateTime thisMonth(ZoneId to) {
        return ZonedDateTime.now(to).toLocalDate().withDayOfMonth(1).atStartOfDay();
    }

    /**
     * 当前或过去的周一的 0:0:0.0
     *
     * @param to 时区
     * @return 日时
     */
    public static LocalDateTime pastMonday(ZoneId to) {
        return pastWeek(to, DayOfWeek.MONDAY);
    }

    /**
     * 当前或过去的周日的 0:0:0.0
     *
     * @param to 时区
     * @return 日时
     */
    public static LocalDateTime pastSunday(ZoneId to) {
        return pastWeek(to, DayOfWeek.SUNDAY);
    }

    /**
     * 当前或过去的周几的 0:0:0.0
     *
     * @param to 时区
     * @return 日时
     */
    public static LocalDateTime pastWeek(ZoneId to, DayOfWeek day) {
        LocalDateTime ldt = ZonedDateTime.now(to).toLocalDate().atStartOfDay();
        int v = ldt.getDayOfWeek().getValue();
        int m = day.getValue();
        if (m > v) {
            return ldt.plusDays((m - v - 7));
        } else if (m < v) {
            return ldt.plusDays((m - v));
        } else {
            return ldt;
        }
    }

    public static LocalDateTime toZone(LocalDateTime time, ZoneId to) {
        if (time == null) return null;
        if (to == null) return time;
        ZoneId from = ZoneId.systemDefault();
        return toZone(time, from, to);
    }

    public static LocalDateTime toZone(LocalDateTime time, ZoneId from, ZoneId to) {
        if (time == null) return null;
        if (to == null || from == null) return time;
        if (from.equals(to)) {
            return time;
        } else {
            return time.atZone(from).withZoneSameInstant(to).toLocalDateTime();
        }
    }

    public static LocalDateTime toZone(ZonedDateTime time, ZoneId to) {
        if (time == null) return null;
        if (to == null) return time.toLocalDateTime();

        if (time.getZone().equals(to)) {
            return time.toLocalDateTime();
        } else {
            return time.withZoneSameInstant(to).toLocalDateTime();
        }
    }

    public static void main(String[] args) {
        ZoneId from = ZoneId.of("Asia/Tokyo");
        ZoneId to = ZoneId.of("America/New_York");
        LocalDateTime ln = LocalDateTime.now();
        ZonedDateTime zn = ZonedDateTime.now();
        System.out.println(ln);
        System.out.println(toZone(ln, to));
        System.out.println(toZone(ln, from, to));
        System.out.println(toZone(zn, to));
        System.out.println(nowDateTime(to));
        System.out.println(nowDateTime(from));
        System.out.println("thisMonday=" + pastMonday(from));
        System.out.println("thisSunday=" + pastSunday(from));
        System.out.println("thisMonth=" + thisMonth(from));
        System.out.println("today=" + today(from));
        //
        System.out.println("2020-01-31@2=" + LocalDate.of(2020, 1, 31).withMonth(2));
        System.out.println("2020-01-31+1M=" + LocalDate.of(2020, 1, 31).plusMonths(1));

        System.out.printf("%,.2f%%", 300000.14159);
    }
}
