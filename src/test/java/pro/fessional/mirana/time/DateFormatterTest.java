package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.fessional.mirana.time.DateFormatter.FMT_DATE_PSE;
import static pro.fessional.mirana.time.DateFormatter.FMT_TIME_PSE;
import static pro.fessional.mirana.time.DateFormatter.PTN_FULL_19;
import static pro.fessional.mirana.time.DateFormatter.PTN_FULL_23;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateFormatterTest {
    final ZoneId tz8 = ZoneId.of("GMT+8");
    final ZoneId tz9 = ZoneId.of("GMT+9");
    final LocalDate ld = LocalDate.of(2017, 4, 26);
    final LocalTime lm9 = LocalTime.of(12, 34, 56, 789_000_000);
    final ZonedDateTime zdt9 = ZonedDateTime.of(ld, lm9, tz8);
    final LocalDateTime ldt9 = LocalDateTime.of(ld, lm9);
    final Date ud9 = new Date(zdt9.toEpochSecond() * 1000L + 789);

    final LocalTime lm0 = LocalTime.of(12, 34, 56, 0);
    final ZonedDateTime zdt0 = ZonedDateTime.of(ld, lm0, tz8);
    final LocalDateTime ldt0 = LocalDateTime.of(ld, lm0);
    final Date ud0 = new Date(zdt0.toEpochSecond() * 1000L);

    @Test
    public void full19() {
        Assertions.assertEquals("2017-04-26 14:38:22", DateFormatter.fixFull19("2017-04-26"));
        Assertions.assertEquals("2017-04-26 12:25:17", DateFormatter.fixFull19("2017-04-26 12"));
        Assertions.assertEquals("2017-04-26 12:34:55", DateFormatter.fixFull19("2017-04-26 12:34"));
        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.fixFull19("2017-04-26 12:34:56"));
        Assertions.assertEquals("2017-04-26 01:54:14", DateFormatter.fixFull19("2017-04-26 1"));
        Assertions.assertEquals("2017-04-26 12:03:07", DateFormatter.fixFull19("2017-04-26 12:3"));
        Assertions.assertEquals("2017-04-26 12:34:05", DateFormatter.fixFull19("2017-04-26 12:34:5"));

        Assertions.assertEquals(new SimpleDateFormat(PTN_FULL_19), DateFormatter.full19());
        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.full19(ldt9));
        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.full19(zdt9));
        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.full19(ud9));
        Assertions.assertEquals("2017-04-26 13:34:56", DateFormatter.full19(zdt9, tz9));
        Assertions.assertEquals("2017-04-26 13:34:56", DateFormatter.full19(ud9, tz9));
    }

    @Test
    public void full23() {
        Assertions.assertEquals(new SimpleDateFormat(PTN_FULL_23), DateFormatter.full23());
        Assertions.assertEquals("2017-04-26 12:34:56.789", DateFormatter.full23(ldt9));
        Assertions.assertEquals("2017-04-26 12:34:56.789", DateFormatter.full23(zdt9));
        Assertions.assertEquals("2017-04-26 12:34:56.789", DateFormatter.full23(ud9));
        Assertions.assertEquals("2017-04-26 13:34:56.789", DateFormatter.full23(zdt9, tz9));
        Assertions.assertEquals("2017-04-26 13:34:56.789", DateFormatter.full23(ud9, tz9));

        Assertions.assertEquals("2017-04-26 12:34:56.000", DateFormatter.full23(ldt0));
        Assertions.assertEquals("2017-04-26 12:34:56.000", DateFormatter.full23(zdt0));
        Assertions.assertEquals("2017-04-26 12:34:56.000", DateFormatter.full23(ud0));
        Assertions.assertEquals("2017-04-26 13:34:56.000", DateFormatter.full23(zdt0, tz9));
        Assertions.assertEquals("2017-04-26 13:34:56.000", DateFormatter.full23(ud0, tz9));
    }


    @Test
    public void full() {
        Assertions.assertEquals("2017-04-26 12:34:56.789", DateFormatter.full(ldt9));
        Assertions.assertEquals("2017-04-26 12:34:56.789", DateFormatter.full(zdt9));
        Assertions.assertEquals("2017-04-26 12:34:56.789", DateFormatter.full(ud9));
        Assertions.assertEquals("2017-04-26 13:34:56.789", DateFormatter.full(zdt9, tz9));
        Assertions.assertEquals("2017-04-26 13:34:56.789", DateFormatter.full(ud9, tz9));

        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.full(ldt0));
        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.full(zdt0));
        Assertions.assertEquals("2017-04-26 12:34:56", DateFormatter.full(ud0));
        Assertions.assertEquals("2017-04-26 13:34:56", DateFormatter.full(zdt0, tz9));
        Assertions.assertEquals("2017-04-26 13:34:56", DateFormatter.full(ud0, tz9));
    }

    @Test
    public void date10() {
        Assertions.assertEquals("2017-04-26", DateFormatter.date10(ld));
        Assertions.assertEquals("2017-04-26", DateFormatter.date10(ldt9));
        Assertions.assertEquals("2017-04-26", DateFormatter.date10(zdt9));
        Assertions.assertEquals("2017-04-26", DateFormatter.date10(ud9));
        Assertions.assertEquals("2017-04-26", DateFormatter.date10(zdt9, tz9));
        Assertions.assertEquals("2017-04-26", DateFormatter.date10(ud9, tz9));
    }


    @Test
    public void time() {
        Assertions.assertEquals("12:34:56", DateFormatter.time(lm0));
        Assertions.assertEquals("12:34:56.789", DateFormatter.time(lm9));

        Assertions.assertEquals("12:34:56.789", DateFormatter.time(ldt9));
        Assertions.assertEquals("12:34:56.789", DateFormatter.time(zdt9));
        Assertions.assertEquals("12:34:56.789", DateFormatter.time(ud9));
        Assertions.assertEquals("13:34:56.789", DateFormatter.time(zdt9, tz9));
        Assertions.assertEquals("13:34:56.789", DateFormatter.time(ud9, tz9));

        Assertions.assertEquals("12:34:56", DateFormatter.time(ldt0));
        Assertions.assertEquals("12:34:56", DateFormatter.time(zdt0));
        Assertions.assertEquals("12:34:56", DateFormatter.time(ud0));
        Assertions.assertEquals("13:34:56", DateFormatter.time(zdt0, tz9));
        Assertions.assertEquals("13:34:56", DateFormatter.time(ud0, tz9));
    }

    @Test
    public void time08() {
        Assertions.assertEquals("12:34:56", DateFormatter.time08(lm0));
        Assertions.assertEquals("12:34:56", DateFormatter.time08(lm9));

        Assertions.assertEquals("12:34:56", DateFormatter.time08(ldt9));
        Assertions.assertEquals("12:34:56", DateFormatter.time08(zdt9));
        Assertions.assertEquals("12:34:56", DateFormatter.time08(ud9));
        Assertions.assertEquals("13:34:56", DateFormatter.time08(zdt9, tz9));
        Assertions.assertEquals("13:34:56", DateFormatter.time08(ud9, tz9));

        Assertions.assertEquals("12:34:56", DateFormatter.time08(ldt0));
        Assertions.assertEquals("12:34:56", DateFormatter.time08(zdt0));
        Assertions.assertEquals("12:34:56", DateFormatter.time08(ud0));
        Assertions.assertEquals("13:34:56", DateFormatter.time08(zdt0, tz9));
        Assertions.assertEquals("13:34:56", DateFormatter.time08(ud0, tz9));
    }

    @Test
    public void time12() {
        Assertions.assertEquals("12:34:56.000", DateFormatter.time12(lm0));
        Assertions.assertEquals("12:34:56.789", DateFormatter.time12(lm9));

        Assertions.assertEquals("12:34:56.789", DateFormatter.time12(ldt9));
        Assertions.assertEquals("12:34:56.789", DateFormatter.time12(zdt9));
        Assertions.assertEquals("12:34:56.789", DateFormatter.time12(ud9));
        Assertions.assertEquals("13:34:56.789", DateFormatter.time12(zdt9, tz9));
        Assertions.assertEquals("13:34:56.789", DateFormatter.time12(ud9, tz9));

        Assertions.assertEquals("12:34:56.000", DateFormatter.time12(ldt0));
        Assertions.assertEquals("12:34:56.000", DateFormatter.time12(zdt0));
        Assertions.assertEquals("12:34:56.000", DateFormatter.time12(ud0));
        Assertions.assertEquals("13:34:56.000", DateFormatter.time12(zdt0, tz9));
        Assertions.assertEquals("13:34:56.000", DateFormatter.time12(ud0, tz9));
    }

    @Test
    public void zone() {
        try {
            ThreadNow.TweakZone.tweakGlobal(TimeZone.getTimeZone("GMT+8"));
            ZoneId zoneId = ThreadNow.sysZoneId();
            Assertions.assertEquals(tz8, zoneId);
            ZonedDateTime zoned = DateFormatter.zoned(ud0, null);
            Assertions.assertEquals(zdt0, zoned);
        }
        finally {
            ThreadNow.TweakZone.resetGlobal();
        }
    }

    /**
     * V       time-zone ID                zone-id           America/Los_Angeles; Z; -08:30
     * O       localized zone-offset       offset-O          GMT+8; GMT+08:00; UTC-08:00;
     * X       zone-offset 'Z' for zero    offset-X          Z; -08; -0830; -08:30; -083015; -08:30:15;
     * x       zone-offset                 offset-x          +0000; -08; -0830; -08:30; -083015; -08:30:15;
     * Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
     */
    @Test
    public void infoZone() {
        // 2021-05-06 20:27:04.633 Asia/Shanghai
        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS VV");
        // 2021-05-06 20:28:26.883 +0800 Asia/Shanghai
        DateTimeFormatter d2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS ZZ VV");
        final ZonedDateTime now = ZonedDateTime.now();
        SystemOut.println(now.format(d1));
        SystemOut.println(now.format(d2));

        for (String zid : ZoneId.getAvailableZoneIds()) {
            SystemOut.println(zid);
        }
        SystemOut.println(DateFormatter.fullTz(now));
        SystemOut.println(DateFormatter.fullTz(now.toOffsetDateTime()));
    }

    @Test
    public void testPnt() {
        assertEquals(LocalTime.of(1, 2, 3), LocalTime.parse("1:2:3", FMT_TIME_PSE));
        assertEquals(LocalTime.of(1, 2, 0), LocalTime.parse("1:2", FMT_TIME_PSE));
        assertEquals(LocalTime.of(1, 0, 0), LocalTime.parse("1", FMT_TIME_PSE));

        assertEquals(LocalDate.of(2021, 1, 2), FMT_DATE_PSE.parse("21-1-2", DateParser.QueryDate));
        assertEquals(LocalDate.of(2021, 1, 2), FMT_DATE_PSE.parse("2021/1/2", DateParser.QueryDate));
        assertEquals(LocalDate.of(2021, 1, 1), FMT_DATE_PSE.parse("21-1", DateParser.QueryDate));
    }
}
