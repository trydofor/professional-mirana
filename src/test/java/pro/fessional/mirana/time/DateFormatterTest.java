package pro.fessional.mirana.time;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

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
    public void speed() {
        DateFormat df = new SimpleDateFormat(DateFormatter.PTN_FULL_19);
        Date now = new Date();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            df.format(now);
        }
        System.out.println("one-SimpleDateFormat.format=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            DateFormatter.full19(now, null);
        }
        System.out.println("DateFormatter.full19(Date,null)=" + (System.currentTimeMillis() - s2));

        ZonedDateTime dtn = DateFormatter.zoned(now, null);
        long s3 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            DateFormatter.full19(dtn);
        }
        System.out.println("DateFormatter.full19(ZonedDateTime)=" + (System.currentTimeMillis() - s3));

        long s4 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            dtn.format(DateFormatter.FMT_FULL_19);
        }
        System.out.println("ZonedDateTime.format=" + (System.currentTimeMillis() - s4));

        long s5 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            new SimpleDateFormat(DateFormatter.PTN_FULL_19).format(now);
        }
        System.out.println("new-SimpleDateFormat.format=" + (System.currentTimeMillis() - s5));

        long s6 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            DateFormatter.time08(now);
        }
        System.out.println("DateFormatter.full19(Date)=" + (System.currentTimeMillis() - s6));
    }
}