package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author trydofor
 * @since 2019-07-03
 */
public class ZoneIdResolverTest {

    @Test
    public void old() {
        Testing.println(ZoneId.of("Asia/Shanghai"));
        // exception
//        SystemOut.println(ZoneId.of("ASIA/SHANGHAI"));
//        SystemOut.println(ZoneId.of("asia/shanghai"));
//        SystemOut.println(ZoneId.of("Shanghai"));
//        SystemOut.println(ZoneId.of("ShangHai"));
    }

    @Test
    public void test() {
        ZoneId zidSh = ZoneId.of("Asia/Shanghai");
        Assertions.assertEquals(zidSh, ZoneIdResolver.zoneId("Asia/Shanghai"));
        Assertions.assertEquals(zidSh, ZoneIdResolver.zoneId("ASIA/SHANGHAI"));
        Assertions.assertEquals(zidSh, ZoneIdResolver.zoneId("asia/shanghai"));
        Assertions.assertEquals(zidSh, ZoneIdResolver.zoneId("Shanghai"));
        Assertions.assertEquals(zidSh, ZoneIdResolver.zoneId("ShangHai"));
        TimeZone tzSh = TimeZone.getTimeZone("Asia/Shanghai");
        Assertions.assertEquals(tzSh, ZoneIdResolver.timeZone("Asia/Shanghai"));
        Assertions.assertEquals(tzSh, ZoneIdResolver.timeZone("Shanghai"));
        Assertions.assertEquals(tzSh, ZoneIdResolver.timeZone("ShangHai"));
    }

    @Test
    @Disabled("time test")
    public void loop() {
        long s0 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneId.of("Asia/Shanghai");
        }
        Testing.println("ZoneId.of=" + (System.currentTimeMillis() - s0));

        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneId.of("Asia/Shanghai");
        }
        Testing.println("ZoneId.of=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            TimeZone.getTimeZone("Asia/Shanghai");
        }
        Testing.println("TimeZone.getTimeZone=" + (System.currentTimeMillis() - s2));

        long s3 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneIdResolver.zoneId("Asia/Shanghai");
        }
        Testing.println("ZoneIdResolver.zoneId=" + (System.currentTimeMillis() - s3));

        long s4 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneIdResolver.timeZone("Asia/Shanghai");
        }
        Testing.println("ZoneIdResolver.timeZone=" + (System.currentTimeMillis() - s4));
    }
}