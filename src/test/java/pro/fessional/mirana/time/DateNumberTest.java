package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateNumberTest {

    final LocalDate ld = LocalDate.of(2019, 5, 21);
    final LocalTime lt = LocalTime.of(12, 34, 56, 789_000_000);
    final LocalDateTime ldt = LocalDateTime.of(ld, lt);
    final ZonedDateTime zdt = ZonedDateTime.of(ld, lt, ZoneId.systemDefault());

    @Test
    public void date8() {
        assertEquals(20190521, DateNumber.date8(ld));
        assertEquals(20190521, DateNumber.date8(ldt));
        assertEquals(20190521, DateNumber.date8(zdt));
    }

    @Test
    public void dateTime14() {
        assertEquals(20190521123456L, DateNumber.dateTime14(ldt));
        assertEquals(20190521123456L, DateNumber.dateTime14(zdt));
    }

    @Test
    public void dateTime17() {
        assertEquals(20190521123456789L, DateNumber.dateTime17(ldt));
        assertEquals(20190521123456789L, DateNumber.dateTime17(zdt));
    }

    @Test
    public void time6() {
        assertEquals(123456, DateNumber.time6(lt));
        assertEquals(123456, DateNumber.time6(ldt));
        assertEquals(123456, DateNumber.time6(zdt));
    }

    @Test
    public void time9() {
        assertEquals(123456789, DateNumber.time9(lt));
        assertEquals(123456789, DateNumber.time9(ldt));
        assertEquals(123456789, DateNumber.time9(zdt));
    }

    @Test
    public void parse() {
        assertEquals(lt, DateNumber.parseTime(123456789));
        assertEquals(ld, DateNumber.parseDate(20190521));
        assertEquals(ldt, DateNumber.parseDateTime(20190521123456789L));
    }
}