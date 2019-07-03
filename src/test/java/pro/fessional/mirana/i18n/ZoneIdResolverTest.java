package pro.fessional.mirana.i18n;

import org.junit.Test;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author trydofor
 * @since 2019-07-03
 */
public class ZoneIdResolverTest {

    @Test
    public void old() {
        System.out.println(ZoneId.of("Asia/Shanghai"));
        // exception
//        System.out.println(ZoneId.of("ASIA/SHANGHAI"));
//        System.out.println(ZoneId.of("asia/shanghai"));
//        System.out.println(ZoneId.of("Shanghai"));
//        System.out.println(ZoneId.of("ShangHai"));
    }

    @Test
    public void print() {
        System.out.println(ZoneIdResolver.zoneId("Asia/Shanghai"));
        System.out.println(ZoneIdResolver.zoneId("ASIA/SHANGHAI"));
        System.out.println(ZoneIdResolver.zoneId("asia/shanghai"));
        System.out.println(ZoneIdResolver.zoneId("Shanghai"));
        System.out.println(ZoneIdResolver.zoneId("ShangHai"));
        System.out.println(ZoneIdResolver.timeZone("Asia/Shanghai"));
        System.out.println(ZoneIdResolver.timeZone("Shanghai"));
        System.out.println(ZoneIdResolver.timeZone("ShangHai"));
    }

    @Test
    public void loop() {
        long s0 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneId.of("Asia/Shanghai");
        }
        System.out.println("ZoneId.of=" + (System.currentTimeMillis() - s0));

        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneId.of("Asia/Shanghai");
        }
        System.out.println("ZoneId.of=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            TimeZone.getTimeZone("Asia/Shanghai");
        }
        System.out.println("TimeZone.getTimeZone=" + (System.currentTimeMillis() - s2));

        long s3 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneIdResolver.zoneId("Asia/Shanghai");
        }
        System.out.println("ZoneIdResolver.zoneId=" + (System.currentTimeMillis() - s3));

        long s4 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            ZoneIdResolver.timeZone("Asia/Shanghai");
        }
        System.out.println("ZoneIdResolver.timeZone=" + (System.currentTimeMillis() - s4));
    }
}