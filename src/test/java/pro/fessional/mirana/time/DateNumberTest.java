package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateNumberTest {

    @Test
    public void date8() {
        LocalDate ld = LocalDate.of(2019, 5, 21);
        assertEquals(20190521, DateNumber.date8(ld));
    }

    @Test
    public void dateTime14() {
        LocalDate ld = LocalDate.of(2019, 5, 21);
        LocalTime lt = LocalTime.of(12, 34, 56, 789_000_000);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        assertEquals(20190521123456L, DateNumber.dateTime14(ldt));
    }

    @Test
    public void dateTime17() {
        LocalDate ld = LocalDate.of(2019, 5, 21);
        LocalTime lt = LocalTime.of(12, 34, 56, 789_000_000);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        assertEquals(20190521123456789L, DateNumber.dateTime17(ldt));
    }

    @Test
    public void time6() {
        LocalTime lt = LocalTime.of(12, 34, 56, 789_000_000);
        assertEquals(123456, DateNumber.time6(lt));
    }

    @Test
    public void time9() {
        LocalTime lt = LocalTime.of(12, 34, 56, 789_000_000);
        assertEquals(123456789, DateNumber.time9(lt));
    }
}