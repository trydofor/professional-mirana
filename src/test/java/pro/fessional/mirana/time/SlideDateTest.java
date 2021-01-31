package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author trydofor
 * @since 2021-01-31
 */
class SlideDateTest {

    @Test
    void testPrint() {
        final SlideDate sd = SlideDate.of(Duration.ofHours(-72));
        System.out.println("=== LocalDate");
        System.out.println(LocalDate.now());
        System.out.println(sd.localDate());
        System.out.println("=== LocalDateTime");
        System.out.println(LocalDateTime.now());
        System.out.println(sd.localDateTime());
        System.out.println("=== LocalTime");
        System.out.println(LocalTime.now());
        System.out.println(sd.localTime());
        System.out.println("=== UtilDate");
        System.out.println(new Date());
        System.out.println(sd.utilDate());
    }
}
