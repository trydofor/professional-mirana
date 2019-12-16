package pro.fessional.mirana.time;

import org.junit.Test;

import java.time.LocalDateTime;
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
}