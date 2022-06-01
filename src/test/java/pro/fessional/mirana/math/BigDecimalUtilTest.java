package pro.fessional.mirana.math;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author trydofor
 * @since 2020-06-28
 */
public class BigDecimalUtilTest {
    static String NINE = "9";

    @Test
    public void add() {
        assertNull(BigDecimalUtil.addNull(null, null, null, null));
        assertEquals(ONE, BigDecimalUtil.add(null, ONE, null, null));
        assertEquals(ONE, BigDecimalUtil.add(null, null, ONE));

        assertEquals(ONE, BigDecimalUtil.add(ONE, null, null, null));
        assertEquals(TEN, BigDecimalUtil.add(ONE, null, NINE));
        assertEquals(TEN, BigDecimalUtil.add(ONE, NINE, null, null));
    }

    @Test
    public void sub() {
        BigDecimal NEG1 = new BigDecimal("-1");
        assertEquals(ONE, BigDecimalUtil.sub(ONE, null, null, null));
        assertEquals(ZERO, BigDecimalUtil.sub(ONE, ONE, null, null));
        assertEquals(NEG1, BigDecimalUtil.sub(ONE, ONE, ONE));
        assertEquals(NEG1, BigDecimalUtil.sub(ZERO, null, ONE));
    }

    @Test
    public void mul() {
        assertNull(BigDecimalUtil.mulNull(null, null, null, null));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, null, null, null));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, ONE, null, null));
        assertEquals(ONE, BigDecimalUtil.mul(null, ONE, ONE));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, null, ONE));
        assertEquals(TEN, BigDecimalUtil.mul(ONE, "2", "5"));
    }

    @Test
    public void div() {
        assertEquals(TEN, BigDecimalUtil.div(TEN, ONE, null, null));
        assertEquals(ONE, BigDecimalUtil.div(ONE, ONE, ONE));
        assertEquals(ONE, BigDecimalUtil.div(TEN, "5", "2"));
        assertEquals(ONE, BigDecimalUtil.div(TEN, "5", null, "2"));
    }

    @Test
    public void string() {
        assertEquals("0", BigDecimalUtil.string(ZERO, 0));
        assertEquals("0.0", BigDecimalUtil.string(ZERO, 1));
        assertEquals("0.00", BigDecimalUtil.string(ZERO, 2));
        assertEquals("1.00", BigDecimalUtil.string(ONE, 2));
        assertEquals("10", BigDecimalUtil.string(ONE, -1));
        assertEquals("1", BigDecimalUtil.string(new BigDecimal("1.00"), 1, true));
    }

    @Test
    public void testObject() {
        assertEquals(new BigDecimal("0"), BigDecimalUtil.object("0"));
        assertEquals(new BigDecimal("0.0"), BigDecimalUtil.object("0.0"));
        assertEquals(new BigDecimal("0.00"), BigDecimalUtil.object("0.00"));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.object("1.00"));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object("10"));
    }

    @Test
    public void avg() {
        assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(2, "3", 5L).setScale(2, RoundingMode.FLOOR));
        assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(
                new BigDecimal("2"), null,
                new BigDecimal("3"), null,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
    }

    @Test
    public void w() {
        BigDecimal n1 = BigDecimalUtil.w(ZERO)
                                      .add(ZERO, null, ONE, ONE, null)
                                      .sub(ONE, ONE, ONE, null)
                                      .subIf(true, null, null)
                                      .subIf(true, ONE, null)
                                      .addIf(true, ONE, null)
                                      .subIf(false, ONE, null)
                                      .addIf(false, ONE, null)
                                      .resultFloor(2);
        assertEquals(new BigDecimal("-1.00"), n1);

        BigDecimal n2 = BigDecimalUtil.w(TEN, 2)
                                      .add(ONE)
                                      .mul("2.0", null, null, ONE, ONE)
                                      .div(ONE, ONE)
                                      .pow(2)
                                      .resultFloor();

        assertEquals(new BigDecimal("484.00"), n2);
    }

    @Test
    public void unitUp() {
        final BigDecimal unit = new BigDecimal("0.50");
        final BigDecimal down = new BigDecimal("0.10");

        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp("1.09", unit, down));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp("1.1001", unit, down));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp("1.10", unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp("1.11", unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp("1.20", unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp("1.51", unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp("1.59", unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp("1.60", unit, down));
        assertEquals(new BigDecimal("2.00"), BigDecimalUtil.unitUp("1.61", unit, down));
    }

    public static class Ins {
        private String dec;

        public Ins(String dec) {
            this.dec = dec;
        }

        public String getDec() {
            return dec;
        }

        public void setDec(String dec) {
            this.dec = dec;
        }
    }

    @Test
    public void example() {
        List<Ins> c1 = new ArrayList<>(10);
        List<String> c2 = new ArrayList<>(10);
        int total = 0;
        for (int i = 0; i < 10; i++) {
            final String s = String.valueOf(i);
            c1.add(new Ins(s));
            c2.add(s);
            total += i;
        }

        final BigDecimal min1 = BigDecimalUtil.minMap(c1, Ins::getDec);
        final BigDecimal min2 = BigDecimalUtil.minMap(c2);
        assertEquals(new BigDecimal("0"), min1);
        assertEquals(new BigDecimal("0"), min2);


        final BigDecimal max1 = BigDecimalUtil.maxMap(c1, Ins::getDec);
        final BigDecimal max2 = BigDecimalUtil.maxMap(c2);
        assertEquals(new BigDecimal("9"), max1);
        assertEquals(new BigDecimal("9"), max2);

        final BigDecimal t1 = BigDecimalUtil.w("0")
                                            .addMap(c1, Ins::getDec)
                                            .resultRaw();
        final BigDecimal t2 = BigDecimalUtil.w(0)
                                            .addMap(c2)
                                            .resultRaw();
        assertEquals(new BigDecimal(total), t1);
        assertEquals(new BigDecimal(total), t2);

        final BigDecimal s1 = BigDecimalUtil.w(total)
                                            .subMap(c1, Ins::getDec)
                                            .resultRaw();
        final BigDecimal s2 = BigDecimalUtil.w(String.valueOf(total))
                                            .subMap(c2)
                                            .resultRaw();
        assertEquals(ZERO, s1);
        assertEquals(ZERO, s2);
    }
}
