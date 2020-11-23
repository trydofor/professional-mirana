package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.fessional.mirana.time.DateLocaling.fromZone;
import static pro.fessional.mirana.time.DateLocaling.nowDateTime;
import static pro.fessional.mirana.time.DateLocaling.pastMonday;
import static pro.fessional.mirana.time.DateLocaling.pastSunday;
import static pro.fessional.mirana.time.DateLocaling.thisMonth;
import static pro.fessional.mirana.time.DateLocaling.toZone;
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
        System.out.println(toZone(ln, cn));
        System.out.println(toZone(ln, jp, cn));
        System.out.println(toZone(zn, cn));
        System.out.println(nowDateTime(cn));
        System.out.println(nowDateTime(jp));
        System.out.println("thisMonday=" + pastMonday(jp));
        System.out.println("thisSunday=" + pastSunday(jp));
        System.out.println("thisMonth=" + thisMonth(jp));
        System.out.println("today=" + today(jp));
        //
        System.out.println("2020-01-31@2=" + LocalDate.of(2020, 1, 31).withMonth(2));
        System.out.println("2020-01-31+1M=" + LocalDate.of(2020, 1, 31).plusMonths(1));

        System.out.printf("%,.2f%%", 300000.14159);
    }

    @Test
    public void testFromZone() {
        LocalDateTime ldtJp = fromZone(toZone(zn, jp), jp);
        LocalDateTime ldtCn = fromZone(toZone(zn, cn), cn);
        assertEquals(ldtCn, ldtJp);
    }

    @Test
    public void testToZone() {
        LocalDateTime ldtJp = toZone(ln, jp);
        LocalDateTime ldtCn = toZone(ln, cn);
        assertEquals(ldtCn.plusHours(1), ldtJp);
    }
}