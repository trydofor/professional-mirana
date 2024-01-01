package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.fessional.mirana.time.DateLocaling.dateTime;
import static pro.fessional.mirana.time.DateLocaling.monday;
import static pro.fessional.mirana.time.DateLocaling.month;
import static pro.fessional.mirana.time.DateLocaling.sunday;
import static pro.fessional.mirana.time.DateLocaling.sysLdt;
import static pro.fessional.mirana.time.DateLocaling.today;

/**
 * @author trydofor
 * @since 2020-06-22
 */
public class DateLocalingTest {

    final ZoneId jp = ZoneId.of("Asia/Tokyo");
    final ZoneId cn = ZoneId.of("Asia/Shanghai");
    final LocalDateTime ln = LocalDateTime.now();
    final ZonedDateTime zn = ZonedDateTime.now();

    @Test
    public void testPrint() {
        SystemOut.println(ln);
        SystemOut.println(DateLocaling.useLdt(ln, cn));
        SystemOut.println(DateLocaling.local(jp, ln, cn));
        SystemOut.println(DateLocaling.local(zn, cn));
        SystemOut.println(dateTime(cn));
        SystemOut.println(dateTime(jp));
        SystemOut.println("thisMonday=" + monday(jp));
        SystemOut.println("thisSunday=" + sunday(jp));
        SystemOut.println("thisMonth=" + month(jp));
        SystemOut.println("today=" + today(jp));
        //
        SystemOut.println("2020-01-31@2=" + LocalDate.of(2020, 1, 31).withMonth(2));
        SystemOut.println("2020-01-31+1M=" + LocalDate.of(2020, 1, 31).plusMonths(1));

        SystemOut.printf("%,.2f%%", 300000.14159);
    }

    @Test
    public void testFromZone() {
        LocalDateTime ldtJp = sysLdt(jp, DateLocaling.local(zn, jp));
        LocalDateTime ldtCn = sysLdt(cn, DateLocaling.local(zn, cn));
        assertEquals(ldtCn, ldtJp);
    }

    @Test
    public void testToZone() {
        LocalDateTime ldtJp = DateLocaling.useLdt(ln, jp);
        LocalDateTime ldtCn = DateLocaling.useLdt(ln, cn);
        assertEquals(ldtCn.plusHours(1), ldtJp);
    }
}
