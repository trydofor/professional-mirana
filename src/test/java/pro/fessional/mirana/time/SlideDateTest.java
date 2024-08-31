package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

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


        Testing.println("=== LocalDate");
        Testing.println(LocalDate.now());
        Testing.println(sd.localDate());

        Testing.println("=== LocalTime");
        Testing.println(LocalTime.now());
        Testing.println(sd.localTime());
        Testing.println("=== UtilDate");
        Testing.println(new Date());
        Testing.println(sd.utilDate());

        Assertions.assertEquals(os1, sd.getOffset());

        Testing.println("=== LocalDateTime");
        LocalDateTime ldt0 = LocalDateTime.now();
        LocalDateTime ldt1 = sd.localDateTime();
        Testing.println(ldt0);
        Testing.println(ldt1);
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
