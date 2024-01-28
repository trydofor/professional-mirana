package pro.fessional.mirana.time;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Convert the timezone of LocalDateTime and ZonedDateTime,
 * based on the System timezone to Viewer timezone by default.
 *
 * @author trydofor
 * @since 2019-10-16
 */
public class DateLocaling {

    /**
     * convert epoch millis at UTC
     */
    public static LocalDateTime utcLdt(long epochMilli) {
        return useLdt(epochMilli, ThreadNow.utcZoneId());
    }

    /**
     * convert epoch millis at System timezone
     */
    public static LocalDateTime sysLdt(long epochMilli) {
        return useLdt(epochMilli, ThreadNow.sysZoneId());
    }

    /**
     * convert epoch millis at zoneId
     */
    public static LocalDateTime useLdt(long epochMilli, ZoneId zone) {
        final Instant ins = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(ins, zone);
    }

    /**
     * get epoch millis at UTC
     */
    public static long utcEpoch(LocalDateTime ldt) {
        return useEpoch(ldt, ThreadNow.utcZoneId());
    }

    /**
     * get epoch millis at System timezone
     */
    public static long sysEpoch(LocalDateTime ldt) {
        return useEpoch(ldt, ThreadNow.sysZoneId());
    }

    /**
     * get epoch millis at zoneId
     */
    public static long useEpoch(LocalDateTime ldt, ZoneId zone) {
        return ZonedDateTime.of(ldt, zone).toInstant().toEpochMilli();
    }

    /**
     * current local datetime at zoneId
     */
    public static LocalDateTime dateTime(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDateTime();
    }


    /**
     * current local 0:0:0.0 at zoneId
     */
    public static LocalDateTime today(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDate().atStartOfDay();
    }

    /**
     * 1st date(0:0:0.0) of current month at zoneId
     */
    public static LocalDateTime month(ZoneId at) {
        return ZonedDateTime.now(at).toLocalDate().withDayOfMonth(1).atStartOfDay();
    }

    /**
     * MONDAY(0:0:0.0) of current week at zoneId
     */
    public static LocalDateTime monday(ZoneId at) {
        return week(at, DayOfWeek.MONDAY);
    }

    /**
     * SUNDAY(0:0:0.0) of current week at zoneId
     */
    public static LocalDateTime sunday(ZoneId at) {
        return week(at, DayOfWeek.SUNDAY);
    }

    /**
     * Week day(0:0:0.0) of current week at zoneId
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
     * local datetime at System timezone
     */
    public static ZonedDateTime sysZdt(LocalDateTime ldt) {
        if (ldt == null) return null;
        return ldt.atZone(ThreadNow.sysZoneId());
    }

    /**
     * local datetime at System timezone
     */
    public static LocalDateTime sysLdt(ZonedDateTime zdt) {
        return local(zdt, ThreadNow.sysZoneId());
    }

    /**
     * datetime at System timezone
     */
    public static ZonedDateTime sysZdt(ZonedDateTime zdt) {
        return zoned(zdt, ThreadNow.sysZoneId());
    }

    /**
     * viewer local datetime at System timezone
     */
    public static LocalDateTime sysLdt(ZoneId viewer, LocalDateTime ldt) {
        return local(viewer, ldt, ThreadNow.sysZoneId());
    }

    /**
     * viewer datetime at System timezone
     */
    public static ZonedDateTime sysZdt(ZoneId viewer, LocalDateTime ldt) {
        return zoned(viewer, ldt, ThreadNow.sysZoneId());
    }

    // ////////// viewer //////////

    /**
     * local datetime at viewer timezone
     */
    public static LocalDateTime useLdt(ZonedDateTime zdt, ZoneId viewer) {
        return local(zdt, viewer);
    }

    /**
     * datetime at viewer timezone
     */
    public static ZonedDateTime useZdt(ZonedDateTime zdt, ZoneId viewer) {
        return zoned(zdt, viewer);
    }

    /**
     * system local datetime at viewer timezone
     */
    public static LocalDateTime useLdt(LocalDateTime ldt, ZoneId viewer) {
        return local(ThreadNow.sysZoneId(), ldt, viewer);
    }

    /**
     * system datetime at viewer timezone
     */
    public static ZonedDateTime useZdt(LocalDateTime ldt, ZoneId viewer) {
        return zoned(ThreadNow.sysZoneId(), ldt, viewer);
    }


    // ////////// locate //////////

    /**
     * convert local datetime from `at` to `to`
     */
    public static LocalDateTime local(ZoneId at, LocalDateTime ldt, ZoneId to) {
        if (ldt == null) return null;
        if (to == null || at.equals(to)) return ldt;
        return ldt.atZone(at).withZoneSameInstant(to).toLocalDateTime();
    }

    /**
     * convert datetime from `at` to `to`
     */
    public static ZonedDateTime zoned(ZoneId at, LocalDateTime ldt, ZoneId to) {
        if (ldt == null) return null;
        if (to == null || at.equals(to)) return ldt.atZone(at);
        return ldt.atZone(at).withZoneSameInstant(to);
    }

    /**
     * convert datetime to `to`
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
     * convert datetime to `to`
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
