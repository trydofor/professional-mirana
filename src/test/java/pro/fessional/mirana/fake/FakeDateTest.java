package pro.fessional.mirana.fake;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class FakeDateTest {

    @Test
    public void printDateTime() {
        int off = 3;
        LocalDate min1 = LocalDate.of(2019, 5, 21);
        LocalDateTime min2 = LocalDateTime.of(2019, 5, 21, 12, 34, 56, 0);
        for (int i = 0; i < 20; i++) {
            System.out.println(FakeDate.dateTime(min1, off * i));
            System.out.println(FakeDate.dateTime(min2, off * i));
        }
    }
}