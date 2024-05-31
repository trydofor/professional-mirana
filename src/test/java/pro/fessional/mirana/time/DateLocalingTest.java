package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author trydofor
 * @since 2020-06-22
 */
public class DateLocalingTest {

    final ZoneId jpZid = ZoneId.of("Asia/Tokyo");
    final ZoneId cnZid = ZoneId.of("Asia/Shanghai");
    final LocalDate ld = LocalDate.of(2017, 4, 26);
    final LocalTime jpLt = LocalTime.of(12, 34, 56, 0);

    final LocalDateTime jpLdt = LocalDateTime.of(ld, jpLt);
    final ZonedDateTime jpZdt = ZonedDateTime.of(jpLdt, jpZid);
    final LocalDateTime cnLdt = jpLdt.plusHours(-1);
    final ZonedDateTime cnZdt = ZonedDateTime.of(cnLdt, cnZid);

    final LocalDateTime utcLdt = jpLdt.plusHours(-9);
    final long utcMs = cnZdt.toEpochSecond() * 1000;

    @Test
    public void infoPrint() {
        Testing.println(jpLdt);
        Testing.println(DateLocaling.useLdt(jpLdt, cnZid));
        Testing.println(DateLocaling.local(jpZid, jpLdt, cnZid));
        Testing.println(DateLocaling.local(jpZdt, cnZid));
        Testing.println(DateLocaling.dateTime(cnZid));
        Testing.println(DateLocaling.dateTime(jpZid));
        Testing.println("thisMonday=" + DateLocaling.monday(jpZid));
        Testing.println("thisSunday=" + DateLocaling.sunday(jpZid));
        Testing.println("thisMonth=" + DateLocaling.month(jpZid));
        Testing.println("today=" + DateLocaling.today(jpZid));
        //
        Testing.println("2020-01-31@2=" + LocalDate.of(2020, 1, 31).withMonth(2));
        Testing.println("2020-01-31+1M=" + LocalDate.of(2020, 1, 31).plusMonths(1));

        Testing.printf("%,.2f%%", 300000.14159);
    }

    @Test
    public void sysAll() {
        try {
            TimeZone tz7 = TimeZone.getTimeZone("GMT+7");
            ThreadNow.TweakZone.tweakGlobal(tz7);
            Assertions.assertEquals(tz7, ThreadNow.sysTimeZone());

            LocalDateTime ldt7 = cnLdt.plusHours(-1);
            assertEquals(ldt7, DateLocaling.sysLdt(utcMs));
            assertEquals(ldt7, DateLocaling.sysLdt(cnZdt));
            assertEquals(ldt7, DateLocaling.sysLdt(cnZid, cnLdt));

            assertEquals(utcMs, DateLocaling.sysEpoch(ldt7));

            ZonedDateTime zdt7 = ZonedDateTime.of(ldt7, tz7.toZoneId());
            assertEquals(zdt7, DateLocaling.sysZdt(ldt7));
            assertEquals(zdt7, DateLocaling.sysZdt(cnZdt));
            assertEquals(zdt7, DateLocaling.sysZdt(cnZid, cnLdt));
        }
        finally {
            ThreadNow.TweakZone.resetGlobal();
        }
    }

    @Test
    public void useAll() {
        assertEquals(utcMs, DateLocaling.useEpoch(cnLdt, cnZid));
        assertEquals(utcMs, DateLocaling.useEpoch(jpLdt, jpZid));
        assertEquals(cnZdt, DateLocaling.useZdt(jpZdt, cnZid));
        assertEquals(cnLdt, DateLocaling.useLdt(jpZdt, cnZid));
        assertEquals(cnLdt, DateLocaling.useLdt(utcMs, cnZid));

        try {
            // cnLdt default timezone is UTC+8
            ThreadNow.TweakZone.tweakGlobal(TimeZone.getTimeZone("GMT+8"));
            assertEquals(cnZdt, DateLocaling.useZdt(cnLdt, cnZid));
            assertEquals(cnLdt, DateLocaling.useLdt(cnLdt, cnZid));
        }
        finally {
            ThreadNow.TweakZone.resetGlobal();
        }
    }

    @Test
    public void utcAll() {
        assertEquals(utcLdt, DateLocaling.utcLdt(utcMs));
        assertEquals(utcMs, DateLocaling.utcEpoch(utcLdt));
    }

    @Test
    public void testZoned() {
        ZonedDateTime z1 = DateLocaling.zoned(jpZdt, cnZid);
        assertEquals(cnZdt, z1);

        ZonedDateTime z2 = DateLocaling.zoned(jpZdt, jpZid);
        assertEquals(jpZdt, z2);

        ZonedDateTime z3 = DateLocaling.zoned(jpZid, jpLdt, cnZid);
        assertEquals(cnZdt, z3);

        ZonedDateTime z4 = DateLocaling.zoned(jpZid, jpLdt, jpZid);
        assertEquals(jpZdt, z4);
    }
}
