package pro.fessional.mirana.time;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateFormatterTest {

    @Test
    public void printDateTime() {
        System.out.println("------");
        System.out.println(DateFormatter.dateTime("2017-04-26"));
        System.out.println(DateFormatter.dateTime("2017-04-26 12"));
        System.out.println(DateFormatter.dateTime("2017-04-26 12:34"));
        System.out.println(DateFormatter.dateTime("2017-04-26 12:34:56"));
        System.out.println(DateFormatter.dateTime("2017-04-26 1"));
        System.out.println(DateFormatter.dateTime("2017-04-26 12:3"));
        System.out.println(DateFormatter.dateTime("2017-04-26 12:34:5"));
    }
}