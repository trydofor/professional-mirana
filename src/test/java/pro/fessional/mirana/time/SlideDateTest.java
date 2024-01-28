package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author trydofor
 * @since 2021-01-31
 */
class SlideDateTest {

    @Test
    void infoPrint() {
        Duration os1 = Duration.ofMinutes(-5);
        final SlideDate sd = SlideDate.of(os1);


        SystemOut.println("=== LocalDate");
        SystemOut.println(LocalDate.now());
        SystemOut.println(sd.localDate());

        SystemOut.println("=== LocalTime");
        SystemOut.println(LocalTime.now());
        SystemOut.println(sd.localTime());
        SystemOut.println("=== UtilDate");
        SystemOut.println(new Date());
        SystemOut.println(sd.utilDate());

        Assertions.assertEquals(os1, sd.getOffset());

        SystemOut.println("=== LocalDateTime");
        LocalDateTime ldt0 = LocalDateTime.now();
        LocalDateTime ldt1 = sd.localDateTime();
        SystemOut.println(ldt0);
        SystemOut.println(ldt1);
        Assertions.assertTrue(Duration.between(ldt1, ldt0).toMinutes() >= 4);

        long ms0 = System.currentTimeMillis();
        long ms1 = sd.currentMillis();
        Assertions.assertTrue(Duration.ofMillis(ms0 - ms1).toMinutes() >= 4);

        long s1 = sd.currentSeconds();
        Assertions.assertTrue(Duration.ofSeconds(ms0 / 1000 - s1).toMinutes() >= 4);

        long s2 = sd.current(TimeUnit.SECONDS);
        Assertions.assertTrue(Duration.ofSeconds(ms0 / 1000 - s2).toMinutes() >= 4);

        Assertions.assertNotNull(sd.getClock());

    }
}
