package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * @author trydofor
 * @since 2022-10-10
 */
class ThreadNowTest {

    @Test
    void test1() {
        final Instant n = Instant.now();
        final ZoneId CN = ZoneId.of("Asia/Shanghai");
        final ZoneId JP = ZoneId.of("Asia/Tokyo");
        try {
            ThreadNow.adjust(Clock.fixed(n, CN));
            // 2022-10-10T04:33:39.180Z
            Assertions.assertEquals(n, ThreadNow.instant());
            // 2022-10-10T13:33:39.180+09:00[Asia/Tokyo]
            Assertions.assertEquals(n.atZone(JP), ThreadNow.zonedDateTime(JP));
        }
        finally {
            ThreadNow.remove();
        }
    }

    @Test
    void test2() {
        try {
            new ThreadNow() {{
                init(new InheritableThreadLocal<>(), 2);
            }};
            new ThreadNow() {{
                init(new InheritableThreadLocal<>(), 3);
            }};
            new ThreadNow() {{
                init(new InheritableThreadLocal<>(), 3);
            }};
            Assertions.fail();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
