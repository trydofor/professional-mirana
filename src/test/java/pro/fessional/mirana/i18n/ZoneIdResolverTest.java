package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author trydofor
 * @since 2019-07-03
 */
public class ZoneIdResolverTest {

    @Test
    public void old() {
        SystemOut.println(ZoneId.of("Asia/Shanghai"));
        // exception
//        SystemOut.println(ZoneId.of("ASIA/SHANGHAI"));
//        SystemOut.println(ZoneId.of("asia/shanghai"));
//        SystemOut.println(ZoneId.of("Shanghai"));
//        SystemOut.println(ZoneId.of("ShangHai"));
    }

    @Test
    public void print() {
        SystemOut.println(ZoneIdResolver.zoneId("Asia/Shanghai"));
        SystemOut.println(ZoneIdResolver.zoneId("ASIA/SHANGHAI"));
        SystemOut.println(ZoneIdResolver.zoneId("asia/shanghai"));
        SystemOut.println(ZoneIdResolver.zoneId("Shanghai"));
        SystemOut.println(ZoneIdResolver.zoneId("ShangHai"));
        SystemOut.println(ZoneIdResolver.timeZone("Asia/Shanghai"));
        SystemOut.println(ZoneIdResolver.timeZone("Shanghai"));
        SystemOut.println(ZoneIdResolver.timeZone("ShangHai"));
    }

    @Test
    public void loop() {
        long s0 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneId.of("Asia/Shanghai");
        }
        SystemOut.println("ZoneId.of=" + (System.currentTimeMillis() - s0));

        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneId.of("Asia/Shanghai");
        }
        SystemOut.println("ZoneId.of=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            TimeZone.getTimeZone("Asia/Shanghai");
        }
        SystemOut.println("TimeZone.getTimeZone=" + (System.currentTimeMillis() - s2));

        long s3 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneIdResolver.zoneId("Asia/Shanghai");
        }
        SystemOut.println("ZoneIdResolver.zoneId=" + (System.currentTimeMillis() - s3));

        long s4 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneIdResolver.timeZone("Asia/Shanghai");
        }
        SystemOut.println("ZoneIdResolver.timeZone=" + (System.currentTimeMillis() - s4));
    }
}