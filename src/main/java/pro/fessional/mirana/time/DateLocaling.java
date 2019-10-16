package pro.fessional.mirana.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author trydofor
 * @since 2019-10-16
 */
public class DateLocaling {

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
    }
}
