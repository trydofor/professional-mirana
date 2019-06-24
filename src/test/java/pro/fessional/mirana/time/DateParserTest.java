package pro.fessional.mirana.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

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
        LocalDate ld1 = DateParser.parseDate(str1);
        LocalDate ld2 = DateParser.parseDate(str2);
        assertEquals(LocalDate.of(2019, 5, 21), ld1);
        assertEquals(LocalDate.of(2019, 5, 21), ld2);
    }

    @Test
    public void parseDateTime() {
        String str1 = "２０１９年０５月２１日　１２点３４分５６秒";
        String str2 = "２０１９年０５月２１日　１２点３４分５６秒789";
        LocalDate ld1 = DateParser.parseDate(str1);
        LocalDate ld2 = DateParser.parseDate(str2);
        LocalTime lt1 = DateParser.parseTime(str1, 8);
        LocalTime lt2 = DateParser.parseTime(str2, 8);

        assertEquals(LocalDateTime.of(2019, 5, 21, 12, 34, 56, 0), LocalDateTime.of(ld1, lt1));
        assertEquals(LocalDateTime.of(2019, 5, 21, 12, 34, 56, 789_000_000), LocalDateTime.of(ld2, lt2));
    }
}