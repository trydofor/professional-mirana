package pro.fessional.mirana.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 对LocalDateTime和ZonedDateTime进行时区的转换，默认立足System时区看Viewer时区
 *
 * @author trydofor
 * @since 2019-10-16
 */
public class DateLocaling {

    /**
     * 当前的日时
     *
     * @param at 时区
     * @return now
     */
    public static LocalDateTime dateTime(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDateTime();
    }

    /**
     * 当前的日期
     *
     * @param at 时区
     * @return now
     */
    public static LocalDate date(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDate();
    }

    /**
     * 当前的时间
     *
     * @param at 时区
     * @return now
     */
    public static LocalTime time(ZoneId at) {
        return ZonedDateTime.now(at).toLocalTime();
    }

    /**
     * 今天的零时（0:0:0.0）
     *
     * @param at 时区
     * @return 凌晨
     */
    public static LocalDateTime today(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDate().atStartOfDay();
    }

    /**
     * 月初（最近的一日）的零时（0:0:0.0）
     *
     * @param at 时区
     * @return 月初的 0:0:0.0
     */
    public static LocalDateTime month(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDate().withDayOfMonth(1).atStartOfDay();
    }

    /**
     * 最近的周一的零时（0:0:0.0）
     *
     * @param at 时区
     * @return 日时
     */
    public static LocalDateTime monday(ZoneId at) {
        return week(at, DayOfWeek.MONDAY);
    }

    /**
     * 最近的周日的零时（0:0:0.0）
     *
     * @param at 时区
     * @return 日时
     */
    public static LocalDateTime sunday(ZoneId at) {
        return week(at, DayOfWeek.SUNDAY);
    }

    /**
     * 最近的周几的零时（0:0:0.0）
     *
     * @param at  时区
     * @param day 星期
     * @return 日时
     */
    public static LocalDateTime week(ZoneId at, DayOfWeek day) {
        LocalDateTime ldt = ZonedDateTime.now(at).toLocalDate().atStartOfDay();
        int v = ldt.getDayOfWeek().getValue(); // 0:Monday; 6:Sunday
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

    // ////////// system //////////

    /**
     * 转为System时区
     *
     * @param ldt 日时
     * @return System日时
     */
    public static ZonedDateTime sysZdt(LocalDateTime ldt) {
        if (ldt == null) return null;
        return ldt.atZone(ZoneId.systemDefault());
    }

    /**
     * 转为System时区
     *
     * @param zdt 日时
     * @return System日时
     */
    public static LocalDateTime sysLdt(ZonedDateTime zdt) {
        return local(zdt, ZoneId.systemDefault());
    }

    /**
     * 转为System时区
     *
     * @param zdt 日时
     * @return System日时
     */
    public static ZonedDateTime sysZdt(ZonedDateTime zdt) {
        return zoned(zdt, ZoneId.systemDefault());
    }

    /**
     * 转为System时区
     *
     * @param viewer 时区
     * @param ldt    日时
     * @return System日时
     */
    public static LocalDateTime sysLdt(ZoneId viewer, LocalDateTime ldt) {
        return local(viewer, ldt, ZoneId.systemDefault());
    }

    /**
     * 转为System时区
     *
     * @param viewer 时区
     * @param ldt    日时
     * @return System日时
     */
    public static ZonedDateTime sysZdt(ZoneId viewer, LocalDateTime ldt) {
        return zoned(viewer, ldt, ZoneId.systemDefault());
    }

    // ////////// viewer //////////

    /**
     * 转为Viewer时区
     *
     * @param zdt    日时
     * @param viewer 时区
     * @return Viewer日时
     */
    public static LocalDateTime useLdt(ZonedDateTime zdt, ZoneId viewer) {
        return local(zdt, viewer);
    }

    /**
     * 转为Viewer时区
     *
     * @param zdt    日时
     * @param viewer 时区
     * @return Viewer日时
     */
    public static ZonedDateTime useZdt(ZonedDateTime zdt, ZoneId viewer) {
        return zoned(zdt, viewer);
    }

    /**
     * 转为Viewer时区
     *
     * @param ldt    日时
     * @param viewer 时区
     * @return Viewer日时
     */
    public static LocalDateTime useLdt(LocalDateTime ldt, ZoneId viewer) {
        return local(ZoneId.systemDefault(), ldt, viewer);
    }

    /**
     * 转为Viewer时区
     *
     * @param ldt    日时
     * @param viewer 时区
     * @return Viewer日时
     */
    public static ZonedDateTime useZdt(LocalDateTime ldt, ZoneId viewer) {
        return zoned(ZoneId.systemDefault(), ldt, viewer);
    }


    // ////////// locate //////////

    /**
     * 从at转为to时区
     *
     * @param at  所在
     * @param ldt 日时
     * @param to  目标
     * @return 目标日时
     */
    public static LocalDateTime local(ZoneId at, LocalDateTime ldt, ZoneId to) {
        if (ldt == null) return null;
        if (to == null || at.equals(to)) return ldt;
        return ldt.atZone(at).withZoneSameInstant(to).toLocalDateTime();
    }

    /**
     * 从at转为to时区
     *
     * @param at  所在
     * @param ldt 日时
     * @param to  目标
     * @return 目标日时
     */
    public static ZonedDateTime zoned(ZoneId at, LocalDateTime ldt, ZoneId to) {
        if (ldt == null) return null;
        if (to == null || at.equals(to)) return ldt.atZone(at);
        return ldt.atZone(at).withZoneSameInstant(to);
    }

    /**
     * 转为to时区
     *
     * @param zdt 日时
     * @param to  目标
     * @return 目标日时
     */
    public static LocalDateTime local(ZonedDateTime zdt, ZoneId to) {
        if (zdt == null) return null;
        if (to == null) return zdt.toLocalDateTime();

        if (zdt.getZone().equals(to)) {
            return zdt.toLocalDateTime();
        }
        else {
            return zdt.withZoneSameInstant(to).toLocalDateTime();
        }
    }

    /**
     * 转为to时区
     *
     * @param zdt 日时
     * @param to  目标
     * @return 目标日时
     */
    public static ZonedDateTime zoned(ZonedDateTime zdt, ZoneId to) {
        if (zdt == null) return null;
        if (to == null) return zdt;

        if (zdt.getZone().equals(to)) {
            return zdt;
        }
        else {
            return zdt.withZoneSameInstant(to);
        }
    }
}
