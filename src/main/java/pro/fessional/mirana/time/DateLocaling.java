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
     * @param to  时区
     * @param day 星期
     * @return 日时
     */
    public static LocalDateTime pastWeek(ZoneId to, DayOfWeek day) {
        LocalDateTime ldt = ZonedDateTime.now(to).toLocalDate().atStartOfDay();
        int v = ldt.getDayOfWeek().getValue();
        int m = day.getValue();
        if (m > v) {
            return ldt.plusDays((m - v - 7));
        }
        else if (m < v) {
            return ldt.plusDays((m - v));
        }
        else {
            return ldt;
        }
    }

    /**
     * 把time从来源时区变为系统时区
     *
     * @param time 时间
     * @param from 来源时区
     * @return 系统时间
     */
    public static LocalDateTime fromZone(LocalDateTime time, ZoneId from) {
        if (time == null) return null;
        if (from == null) return time;
        ZoneId to = ZoneId.systemDefault();
        return toZone(time, from, to);
    }

    /**
     * 把time从系统时区变为 to的时区
     *
     * @param time 时间
     * @param to   目标时区
     * @return 目标时间
     */
    public static LocalDateTime toZone(LocalDateTime time, ZoneId to) {
        if (time == null) return null;
        if (to == null) return time;
        ZoneId from = ZoneId.systemDefault();
        return toZone(time, from, to);
    }

    /**
     * 把时间从from变为to时区
     *
     * @param time 时间
     * @param from 来源
     * @param to   目标
     * @return 目标时间
     */
    public static LocalDateTime toZone(LocalDateTime time, ZoneId from, ZoneId to) {
        if (time == null) return null;
        if (to == null || from.equals(to)) return time;
        return time.atZone(from).withZoneSameInstant(to).toLocalDateTime();
    }

    /**
     * 把时间变为to时区
     *
     * @param time 时间
     * @param to   目标时区
     * @return 目标时间
     */
    public static LocalDateTime toZone(ZonedDateTime time, ZoneId to) {
        if (time == null) return null;
        if (to == null) return time.toLocalDateTime();

        if (time.getZone().equals(to)) {
            return time.toLocalDateTime();
        }
        else {
            return time.withZoneSameInstant(to).toLocalDateTime();
        }
    }

    /**
     * 把time从系统时区
     *
     * @param time 时间
     * @return 目标时间
     */
    public static ZonedDateTime atZone(LocalDateTime time) {
        if (time == null) return null;
        return time.atZone(ZoneId.systemDefault());
    }

    /**
     * 把time从系统时区变为 to的时区
     *
     * @param time 时间
     * @param to   目标时区
     * @return 目标时间
     */
    public static ZonedDateTime atZone(LocalDateTime time, ZoneId to) {
        return atZone(time, ZoneId.systemDefault(), to);
    }

    /**
     * 把时间从from变为to时区
     *
     * @param time 时间
     * @param from 来源
     * @param to   目标
     * @return 目标时间
     */
    public static ZonedDateTime atZone(LocalDateTime time, ZoneId from, ZoneId to) {
        if (time == null) return null;
        if (to == null || from.equals(to)) return time.atZone(from);
        return time.atZone(from).withZoneSameInstant(to);
    }


    /**
     * 把时间变为系统时区
     *
     * @param time 时间
     * @return 目标时间
     */
    public static LocalDateTime toSystem(ZonedDateTime time) {
        return toZone(time, ZoneId.systemDefault());
    }

    /**
     * 把同一时间不同时区
     *
     * @param time 时间
     * @param to   目标时区
     * @return 目标时间
     */
    public static ZonedDateTime zoneZone(ZonedDateTime time, ZoneId to) {
        if (time == null) return null;
        if (to == null) return time;

        if (time.getZone().equals(to)) {
            return time;
        }
        else {
            return time.withZoneSameInstant(to);
        }
    }

    /**
     * 调整到系统时区
     *
     * @param time 时间
     * @return 目标时间
     */
    public static ZonedDateTime zoneSystem(ZonedDateTime time) {
        return zoneZone(time, ZoneId.systemDefault());
    }
}
