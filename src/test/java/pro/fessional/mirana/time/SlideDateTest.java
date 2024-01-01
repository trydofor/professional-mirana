package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

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
        SystemOut.println("=== LocalDate");
        SystemOut.println(LocalDate.now());
        SystemOut.println(sd.localDate());
        SystemOut.println("=== LocalDateTime");
        SystemOut.println(LocalDateTime.now());
        SystemOut.println(sd.localDateTime());
        SystemOut.println("=== LocalTime");
        SystemOut.println(LocalTime.now());
        SystemOut.println(sd.localTime());
        SystemOut.println("=== UtilDate");
        SystemOut.println(new Date());
        SystemOut.println(sd.utilDate());
    }
}
