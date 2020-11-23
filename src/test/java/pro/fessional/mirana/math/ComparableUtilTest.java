package pro.fessional.mirana.math;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-01-04
 */
public class ComparableUtilTest {

    @Test
    public void string() {
        String a = null;
        String b = "1";
        String c = "2";
        assertEquals(ComparableUtil.min(a, b), b);
        assertEquals(ComparableUtil.min(a, b, c), b);
        assertEquals(ComparableUtil.max(a, b), b);
        assertEquals(ComparableUtil.max(a, b, c), c);
    }

    @Test
    public void number() {
        Integer a = null;
        Integer b = 1;
        Integer c = 2;

        assertEquals(ComparableUtil.min(a, b), b);
        assertEquals(ComparableUtil.min(a, b, c), b);
        assertEquals(ComparableUtil.max(a, b), b);
        assertEquals(ComparableUtil.max(a, b, c), c);
    }

    @Test
    public void date() {
        Date a = null;
        Date b = new Date(1000);
        Date c = new Date(2000);

        Date r = ComparableUtil.min(a, b);
        assertEquals(r, b);
        assertEquals(ComparableUtil.min(a, b, c), b);
        assertEquals(ComparableUtil.max(a, b), b);
        assertEquals(ComparableUtil.max(a, b, c), c);
    }

    @Test
    public void ldt() {
        LocalDateTime a = null;
        LocalDateTime b = LocalDateTime.now();
        LocalDateTime c = b.plusDays(1);

        LocalDateTime r = ComparableUtil.min(a, b);
        assertEquals(r, b);
        assertEquals(ComparableUtil.min(a, b, c), b);
        assertEquals(ComparableUtil.max(a, b), b);
        assertEquals(ComparableUtil.max(a, b, c), c);
    }
}