package pro.fessional.mirana.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import static pro.fessional.mirana.time.ThreadNow.TweakClock;

/**
 * @author trydofor
 * @since 2022-10-10
 */
class ThreadNowTest {

    final ZoneId cnZid = ZoneId.of("Asia/Shanghai");
    final ZoneId jpZid = ZoneId.of("Asia/Tokyo");

    @Test
    void test1() {
        try {
            final Instant n = Instant.now();
            TweakClock.tweakThread(Clock.fixed(n, cnZid));
            // 2022-10-10T04:33:39.180Z
            Assertions.assertEquals(n, ThreadNow.instant());
            // 2022-10-10T13:33:39.180+09:00[Asia/Tokyo]
            Assertions.assertEquals(n.atZone(jpZid), ThreadNow.zonedDateTime(jpZid));
        }
        finally {
            TweakClock.resetThread();
        }
    }

    @Test
    void clock() {
        Instant now = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(now, cnZid);
        ZoneOffset offset = cnZid.getRules().getOffset(now);
        try {
            TweakClock.tweakGlobal(Clock.fixed(now, cnZid));
            Assertions.assertEquals(TimeZone.getTimeZone("UTC"), ThreadNow.utcTimeZone());

            Assertions.assertEquals(ZonedDateTime.of(ldt, cnZid), ThreadNow.zonedDateTime());
            Assertions.assertEquals(ZonedDateTime.of(ldt, cnZid), ThreadNow.zonedDateTime(cnZid));

            Assertions.assertEquals(OffsetDateTime.of(ldt, offset), ThreadNow.offsetDateTime());
            Assertions.assertEquals(OffsetDateTime.of(ldt, offset), ThreadNow.offsetDateTime(cnZid));

            Assertions.assertEquals(new Date(now.toEpochMilli()), ThreadNow.utilDate());
            Assertions.assertEquals(new Date(now.toEpochMilli()), ThreadNow.utilDate(cnZid));

            Assertions.assertEquals(now, ThreadNow.instant());
            Assertions.assertEquals(now, ThreadNow.instant(cnZid));

            Assertions.assertEquals(ldt.toLocalDate(), ThreadNow.localDate());
            Assertions.assertEquals(ldt.toLocalDate(), ThreadNow.localDate(cnZid));

            Assertions.assertEquals(ldt.toLocalTime(), ThreadNow.localTime());
            Assertions.assertEquals(ldt.toLocalTime(), ThreadNow.localTime(cnZid));

            Assertions.assertEquals(ldt, ThreadNow.localDateTime());
            Assertions.assertEquals(ldt, ThreadNow.localDateTime(cnZid));
        }
        finally {
            TweakClock.resetGlobal();
        }
    }

    @Test
    void same() {
        LocalDateTime ldt1 = DateLocaling.dateTime(cnZid);
        Instant ins1 = ldt1.toInstant(ZoneOffset.UTC);
        LocalDateTime ldt2 = ThreadNow.localDateTime(cnZid);
        Instant ins2 = ldt2.toInstant(ZoneOffset.UTC);
        long ms1 = ins1.toEpochMilli();
        long ms2 = ins2.toEpochMilli();
        System.out.println("ldt1=" + ldt1 + ", ldt2=" + ldt2);
        Assertions.assertTrue(Math.abs(ms1 - ms2) < 500);
    }
}
