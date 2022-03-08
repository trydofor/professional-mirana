package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

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
        System.out.println(ln);
        System.out.println(DateLocaling.useLdt(ln, cn));
        System.out.println(DateLocaling.local(jp, ln, cn));
        System.out.println(DateLocaling.local(zn, cn));
        System.out.println(dateTime(cn));
        System.out.println(dateTime(jp));
        System.out.println("thisMonday=" + monday(jp));
        System.out.println("thisSunday=" + sunday(jp));
        System.out.println("thisMonth=" + month(jp));
        System.out.println("today=" + today(jp));
        //
        System.out.println("2020-01-31@2=" + LocalDate.of(2020, 1, 31).withMonth(2));
        System.out.println("2020-01-31+1M=" + LocalDate.of(2020, 1, 31).plusMonths(1));

        System.out.printf("%,.2f%%", 300000.14159);
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
