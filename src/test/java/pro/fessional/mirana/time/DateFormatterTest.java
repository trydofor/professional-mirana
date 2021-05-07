package pro.fessional.mirana.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateFormatterTest {

    @Test
    public void printDateTime() {
        System.out.println("------");
        System.out.println(DateFormatter.fixFull19("2017-04-26"));
        System.out.println(DateFormatter.fixFull19("2017-04-26 12"));
        System.out.println(DateFormatter.fixFull19("2017-04-26 12:34"));
        System.out.println(DateFormatter.fixFull19("2017-04-26 12:34:56"));
        System.out.println(DateFormatter.fixFull19("2017-04-26 1"));
        System.out.println(DateFormatter.fixFull19("2017-04-26 12:3"));
        System.out.println(DateFormatter.fixFull19("2017-04-26 12:34:5"));
    }

    @Test
    public void printFull23() {
        System.out.println("------");

        Date d = new Date();

        System.out.println(DateFormatter.full23().format(d));
        System.out.println(DateFormatter.full23(d));

        LocalDateTime n = LocalDateTime.now();
        System.out.println(DateFormatter.FMT_FULL_23.format(n));
        System.out.println(DateFormatter.full23(n));
    }

    /**
     *      V       time-zone ID                zone-id           America/Los_Angeles; Z; -08:30
     *      O       localized zone-offset       offset-O          GMT+8; GMT+08:00; UTC-08:00;
     *      X       zone-offset 'Z' for zero    offset-X          Z; -08; -0830; -08:30; -083015; -08:30:15;
     *      x       zone-offset                 offset-x          +0000; -08; -0830; -08:30; -083015; -08:30:15;
     *      Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
     */
    @Test
    public void printZone(){
        // 2021-05-06 20:27:04.633 Asia/Shanghai
        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS VV");
        // 2021-05-06 20:28:26.883 +0800 Asia/Shanghai
        DateTimeFormatter d2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS ZZ VV");
        final ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now.format(d1));
        System.out.println(now.format(d2));

        for (String zid : ZoneId.getAvailableZoneIds()) {
            System.out.println(zid);
        }
    }
}
