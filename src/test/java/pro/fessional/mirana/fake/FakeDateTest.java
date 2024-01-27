package pro.fessional.mirana.fake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class FakeDateTest {

    @Test
    public void fake() {
        int off = 3;
        LocalDate min1 = LocalDate.of(2019, 5, 21);
        LocalDateTime min2 = LocalDateTime.of(2019, 5, 21, 12, 34, 56, 0);
        LocalDateTime max = LocalDateTime.of(2019, 5, 21, 13, 0, 0);

        for (int i = 0; i < 20; i++) {
            SystemOut.println(FakeDate.dateTime(min1, off * i));
            SystemOut.println(FakeDate.dateTime(min1, off * i, max.toLocalDate()));
            SystemOut.println(FakeDate.dateTime(min2, off * i));
        }

         LocalDateTime d2 = FakeDate.dateTime(min2, off, max);
        for (int i = 0; i < 10; i++) {
            LocalDateTime d = FakeDate.dateTime(min2, off, max);
            Assertions.assertEquals(d2, d);
        }
    }
}