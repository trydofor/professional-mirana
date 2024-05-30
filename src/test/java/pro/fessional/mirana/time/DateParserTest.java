package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateParserTest {

    @Test
    public void parseTime() {
        String str1 = "２０１９年０５月２１日　１２点３４分５６秒";
        String str2 = "２０１９年０５月２１日　１２点３４分５６秒789";
        LocalTime lt1 = DateParser.parseTime(str1, 8);
        LocalTime lt2 = DateParser.parseTime(str2, 8);
        assertEquals(LocalTime.of(12, 34, 56, 0), lt1);
        assertEquals(LocalTime.of(12, 34, 56, 789_000_000), lt2);
    }

    @Test
    public void parseDate() {
        String str1 = "２０１９年０５月２１日　１２点３４分５６秒";
        String str2 = "２０１９年０５月２１日　１２点３４分５６秒789";
        String str3 = "２０１９年５月２１日　１２点３４分５６秒789";
        LocalDate ld1 = DateParser.parseDate(str1);
        LocalDate ld2 = DateParser.parseDate(str2);
        LocalDate ld3 = DateParser.parseDate(str3);
        assertEquals(LocalDate.of(2019, 5, 21), ld1);
        assertEquals(LocalDate.of(2019, 5, 21), ld2);
        assertEquals(LocalDate.of(2019, 5, 21), ld3);
    }

    @Test
    public void parseZone() {
        // 2011-12-03T10:15:30+01:00[Europe/Paris]
        String str1 = "２０１９年０５月２１日　12:34+0800 无效信息";
        String str2 = "２０１９年０５月２１日　１２点３４GMT+8 无效信息";
        String str3 = "２０１９年５月２１日　１２点３４分５６秒789+01:00[Europe/Paris] 夏令时无效信息";
        final ZoneId zid = ZoneId.of("UTC");
        ZonedDateTime ld1 = DateParser.parseZoned(str1, zid);
        ZonedDateTime ld2 = DateParser.parseZoned(str2, zid);
        ZonedDateTime ld3 = DateParser.parseZoned(str3, zid);

        assertEquals(LocalDateTime.of(2019, 5, 21, 4, 34, 0, 0), ld1.toLocalDateTime());
        assertEquals(LocalDateTime.of(2019, 5, 21, 4, 34, 0, 0), ld2.toLocalDateTime());
        assertEquals(LocalDateTime.of(2019, 5, 21, 10, 34, 56, 789_000_000), ld3.toLocalDateTime());

        assertEquals(zid, ld1.getZone());
        assertEquals(zid, ld2.getZone());
        assertEquals(zid, ld3.getZone());

        assertEquals(ZoneId.of("+0800"), DateParser.parseZoned(str1).getZone());
        assertEquals(ZoneId.of("GMT+8"), DateParser.parseZoned(str2).getZone());
        assertEquals(ZoneId.of("Europe/Paris"), DateParser.parseZoned(str3).getZone());

        assertEquals(ld1, DateParser.parseZoned(ld1, zid));
        assertEquals(ld1, DateParser.parseZoned(OffsetDateTime.of(ld1.toLocalDateTime(), ld1.getOffset()), zid));
    }

    @Test
    public void parseUtilDate() throws ParseException {
        String str1 = "２０１９年０５月２１日";
        String str2 = "２０１９年０５月２１日　１２点３４";
        String str3 = "２０１９年５月２１日　１２点３４分５６秒789";
        Date ld1 = DateParser.parseUtilDate(str1);
        Date ld2 = DateParser.parseUtilDate(str2);
        Date ld3 = DateParser.parseUtilDate(str3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        assertEquals(sdf.parse("2019-05-21 00:00:00.000"), ld1);
        assertEquals(sdf.parse("2019-05-21 12:34:00.000"), ld2);
        assertEquals(sdf.parse("2019-05-21 12:34:56.789"), ld3);
    }

    @Test
    public void parseDateTime() {
        String str1 = "２０１９年０５月２１日　１２点３４分５６秒";
        String str2 = "２０１９年０５月２１日　１２点３４分５６秒789";
        String str3 = "２０１９年５月１日　２点４分６秒789";
        LocalDate ld1 = DateParser.parseDate(str1);
        LocalDate ld2 = DateParser.parseDate(str2);
        LocalTime lt1 = DateParser.parseTime(str1, 8);
        LocalTime lt2 = DateParser.parseTime(str2, 8);

        LocalDateTime ldt = DateParser.parseDateTime(str3);

        assertEquals(LocalDateTime.of(2019, 5, 21, 12, 34, 56, 0), LocalDateTime.of(ld1, lt1));
        assertEquals(LocalDateTime.of(2019, 5, 21, 12, 34, 56, 789_000_000), LocalDateTime.of(ld2, lt2));
        assertEquals(LocalDateTime.of(2019, 5, 1, 2, 4, 6, 789_000_000), ldt);
    }

    @Test
    public void parseM2d2y4() {
        assertEquals(LocalDateTime.of(2019, 12, 26, 9, 46), DateParser.parseDateTime("19/12/26 9:46"));
        assertEquals(LocalDateTime.of(2019, 12, 26, 9, 46), DateParser.parseDateTime("12/26/19 9:46"));
        assertEquals(LocalDateTime.of(2019, 12, 26, 9, 46), DateParser.parseDateTime("12/26/2019 9:46"));
        assertEquals(LocalDate.of(2019, 5, 21), DateParser.parseDate("０５/２１/２０１９"));
    }

    @Test
    public void parseAppend() {
        assertEquals(LocalDateTime.of(2019, 5, 1, 0, 0, 0, 0), DateParser.parseDateTime("2019-05"));
        assertEquals(LocalDateTime.of(2019, 5, 1, 0, 0, 0, 0), DateParser.parseDateTime("2019-05-1"));
        assertEquals(LocalDateTime.of(2019, 5, 21, 1, 0, 0, 0), DateParser.parseDateTime("2019-05-21 1"));

        assertEquals(LocalDate.of(2019, 1, 1), DateParser.parseDate("2019-"));
        assertEquals(LocalDate.of(2019, 1, 1), DateParser.parseDate("2019-1"));
        assertEquals(LocalDate.of(2019, 1, 1), DateParser.parseDate("2019-01"));
        assertEquals(LocalDate.of(2019, 1, 1), DateParser.parseDate("2019-01-1"));
        assertEquals(LocalDate.of(2019, 1, 1), DateParser.parseDate("2019-01-0"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("1"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("01:"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("01:0"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("01:00:"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("01:00:0"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("01:00:00"));
        assertEquals(LocalTime.of(1, 0, 0), DateParser.parseTime("01:00:00.0"));
        assertEquals(LocalTime.of(1, 0, 0, 0), DateParser.parseTime("01:00:00.00"));
    }

    @Test
    public void parseTemporal() {
        List<DateTimeFormatter> dtf = Arrays.asList(
                DateTimeFormatter.ofPattern("y-M-d"),
                DateTimeFormatter.ofPattern("y-M-d[ H:m:s]"),
                DateTimeFormatter.ofPattern("MMM/d/[yyyy][yy][ H:m:s]"),
                DateTimeFormatter.ofPattern("H:m:s")
        );

        {
            final TemporalAccessor ta = DateParser.parseTemporal("2021-01-2", dtf, false);
            assertEquals(LocalDate.of(2021, 1, 2), ta.query(LocalDate::from));
        }
        {
            final TemporalAccessor ta = DateParser.parseTemporal("2021-01-2 3", dtf, false);
            assertEquals(LocalDate.of(2021, 1, 2), ta.query(LocalDate::from));
        }
        {
            final TemporalAccessor ta = DateParser.parseTemporal("2021-01-2 3:4:5", dtf, false);
            assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5), ta.query(LocalDateTime::from));
        }
        {
            final TemporalAccessor ta = DateParser.parseTemporal("Jan/2/21", dtf, false);
            assertEquals(LocalDate.of(2021, 1, 2), ta.query(LocalDate::from));
        }
        {
            final TemporalAccessor ta = DateParser.parseTemporal("Jan/2/2021 3:4:5", dtf, false);
            assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5), ta.query(LocalDateTime::from));
        }
        {
            final TemporalAccessor ta = DateParser.parseTemporal("3:4:5", dtf, false);
            assertEquals(LocalTime.of(3, 4, 5), ta.query(LocalTime::from));
        }

        List<DateParser.QuietPos> ta = DateParser.parseTemporal(dtf, "Jan/2/2021 3:4:5", false);
        for (DateParser.QuietPos qp : ta) {
            SystemOut.println("====");
            SystemOut.println("formatter=" + qp.getFormatter().toString());
            SystemOut.println("error index=" + qp.getErrorIndexReal());
            SystemOut.println("index=" + qp.getIndex());
            SystemOut.println("Temporal=" + qp.getTemporal());
            final RuntimeException ex = qp.getException();
            if (ex != null) {
                SystemOut.println("ignore this exception");
                SystemOut.printStackTrace(ex);
            }
        }
    }

    @Test
    public void parseJ8Time() {
        final DateTimeFormatter f1 = DateTimeFormatter.ofPattern("H[:m][:s]");
        assertEquals(LocalTime.of(3, 4, 5), DateParser.parseTime("3:4:5", f1));
        assertEquals(LocalTime.of(3, 4, 0), DateParser.parseTime("3:4", f1));
        assertEquals(LocalTime.of(3, 0, 0), DateParser.parseTime("3", f1));
    }

    @Test
    public void parseJ8Date() {
        final DateTimeFormatter f1 = DateTimeFormatter.ofPattern("[yyyy][yy][-M][-d]");
        assertEquals(LocalDate.of(2021, 1, 2), DateParser.parseDate("21-1-02", f1));
        assertEquals(LocalDate.of(2021, 1, 2), DateParser.parseDate("2021-01-02", f1));
        assertEquals(LocalDate.of(2021, 1, 1), DateParser.parseDate("2021-1", f1));
        assertEquals(LocalDate.of(2021, 1, 1), DateParser.parseDate("21", f1));
    }

    @Test
    public void parseOffset() {
        ZoneId zid = ZoneId.of("+0800");
        OffsetDateTime odt = OffsetDateTime.of(2024, 1, 28, 20, 19, 49, 0, ZoneOffset.ofHours(8));
        System.out.println(DateFormatter.FMT_FULL_OZ.format(odt));
        assertEquals(odt, DateParser.parseOffset("2024-01-28 20:19:49 +08:00", zid, DateFormatter.FMT_FULL_OZ));

    }

    @Test
    public void parseJ8DateTime() {
        final DateTimeFormatter f1 = DateTimeFormatter.ofPattern("[yyyy][yy][-M][-d][ ][H][:m][:s]");
        assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5), DateParser.parseDateTime("21-1-02 03:4:05", f1));
        assertEquals(LocalDateTime.of(2021, 1, 2, 3, 0, 0), DateParser.parseDateTime("2021-01-02 3", f1));
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), DateParser.parseDateTime("2021-1", f1));
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), DateParser.parseDateTime("21", f1));
    }

    @Test
    public void parseJ8Zoned() {
        final ZoneId g8 = ZoneId.of("Asia/Shanghai");
        final DateTimeFormatter f1 = DateTimeFormatter.ofPattern("[yyyy][yy][-M][-d][ ][H][:m][:s][ VV]");
        assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4, 5).atZone(g8), DateParser.parseZoned("21-1-02 03:4:05 Asia/Shanghai", g8, f1));
        assertEquals(LocalDateTime.of(2021, 1, 2, 3, 0, 0).atZone(g8), DateParser.parseZoned("2021-01-02 3", g8, f1));
    }
}
