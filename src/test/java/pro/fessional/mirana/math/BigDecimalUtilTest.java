package pro.fessional.mirana.math;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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

        assertEquals(new BigDecimal("16"), BigDecimalUtil.add(1, "10", 5L));
        List<Object> list = Arrays.asList(1, "10", 5L);
        assertEquals(new BigDecimal("16"), BigDecimalUtil.addMap(list));
        assertEquals(new BigDecimal("16"), BigDecimalUtil.addMap(list, Object::toString));

        assertEquals(TEN, BigDecimalUtil.addElse(TEN, null, null));
        assertEquals(ZERO, BigDecimalUtil.addElse(TEN, null, ZERO));
    }

    @Test
    public void sub() {
        BigDecimal NEG1 = new BigDecimal("-1");
        assertEquals(ONE, BigDecimalUtil.sub(ONE, null, null, null));
        assertEquals(ZERO, BigDecimalUtil.sub(ONE, ONE, null, null));
        assertEquals(NEG1, BigDecimalUtil.sub(ONE, ONE, ONE));
        assertEquals(NEG1, BigDecimalUtil.sub(ZERO, null, ONE));
        List<Object> lst = Arrays.asList(1, "2", 3L);
        assertEquals(new BigDecimal("4"), BigDecimalUtil.subMap("10", lst));
        assertEquals(new BigDecimal("4"), BigDecimalUtil.subMap("10", lst, Object::toString));
    }

    @Test
    public void mul() {
        assertNull(BigDecimalUtil.mulNull(null, null, null, null));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, null));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, null, null, null));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, ONE, null, null));
        assertEquals(ONE, BigDecimalUtil.mul(null, ONE, ONE));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, null, ONE));
        assertEquals(TEN, BigDecimalUtil.mul(ONE, "2", "5"));
        assertEquals(TEN, BigDecimalUtil.mulElse(ONE, "2", "5"));
        assertEquals(TEN, BigDecimalUtil.mulElse(TEN, null, null));

        List<Object> lst = Arrays.asList(ONE, "2", 5);
        assertEquals(TEN, BigDecimalUtil.prd(ONE, "2", "5"));
        assertEquals(TEN, BigDecimalUtil.mulMap(lst));
        assertEquals(TEN, BigDecimalUtil.mulMap(lst, Object::toString));
        assertEquals(TEN, BigDecimalUtil.prdMap(lst));
        assertEquals(TEN, BigDecimalUtil.prdMap(lst, Object::toString));
    }

    @Test
    public void div() {
        assertEquals(TEN, BigDecimalUtil.div(TEN, ONE, null, null));
        assertEquals(ONE, BigDecimalUtil.div(ONE, ONE, ONE));
        assertEquals(ONE, BigDecimalUtil.div(TEN, "5", "2"));
        assertEquals(ONE, BigDecimalUtil.div(TEN, "5", null, "2"));

        Iterable<?> list = Arrays.asList("5", null, "2");
        assertEquals(ONE, BigDecimalUtil.divMap(TEN, list));
        assertEquals(ONE, BigDecimalUtil.divMap(TEN, list, it -> it));
    }

    @Test
    public void powNeg() {
        assertEquals(TEN, BigDecimalUtil.pow(TEN, 1));
        assertNull(BigDecimalUtil.powNull(null, 1));
        assertEquals(TEN, BigDecimalUtil.neg("-10"));
        assertNull(BigDecimalUtil.negNull(null));
    }

    @Test
    public void string() {
        assertEquals("0", BigDecimalUtil.string(ZERO));
        assertEquals("0", BigDecimalUtil.string(ZERO, 0));
        assertEquals("0", BigDecimalUtil.string(null, "0"));
        assertEquals("0.0", BigDecimalUtil.string(ZERO, 1));
        assertEquals("0.00", BigDecimalUtil.string(ZERO, 2));
        assertEquals("1.00", BigDecimalUtil.string(ONE, 2));
        assertEquals("10", BigDecimalUtil.string(ONE, -1));
        assertEquals("1", BigDecimalUtil.string(new BigDecimal("1.00"), true));
        assertEquals("1", BigDecimalUtil.string(new BigDecimal("1.00"), 1, true));
    }

    public String getZeroStr() {
        return "0";
    }

    public int getZeroInt() {
        return 0;
    }

    public long getZeroLong() {
        return 0L;
    }

    public double getZeroDouble() {
        return 0D;
    }

    @Test
    public void testObject() {
        assertEquals(new BigDecimal("0"), BigDecimalUtil.object("0"));
        assertEquals(new BigDecimal("0.0"), BigDecimalUtil.object("0.0"));
        assertEquals(new BigDecimal("0.00"), BigDecimalUtil.object("0.00"));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.object("1.00"));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object("10"));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object(10));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object(10L));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object(10F).setScale(0, RoundingMode.FLOOR));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object(10D).setScale(0, RoundingMode.FLOOR));
        assertEquals(new BigDecimal("10"), BigDecimalUtil.object(BigInteger.TEN));

        BigDecimal[] arr = BigDecimalUtil.objects(TEN, 1, null, 0L);
        assertArrayEquals(new BigDecimal[]{ONE, TEN, ZERO}, arr);

        // Object is not a functional interface
        assertEquals(new BigDecimal("0"), BigDecimalUtil.object(this::getZeroStr));
        assertEquals(new BigDecimal("0"), BigDecimalUtil.object(this::getZeroInt));
        assertEquals(new BigDecimal("0"), BigDecimalUtil.object(this::getZeroLong));
        assertEquals(new BigDecimal("0"), BigDecimalUtil.object(this::getZeroDouble).setScale(0, RoundingMode.FLOOR));
    }

    @Test
    public void notNull() {
        assertEquals(TEN, BigDecimalUtil.notNull(null, "10"));
        assertEquals(TEN, BigDecimalUtil.ifElse(false, null, "10"));
        assertEquals(0, BigDecimalUtil.compareTo(10, "10"));

        assertEquals(ZERO, BigDecimalUtil.ifElse(false, null, this::getZeroStr));
    }

    @Test
    public void avg() {
        BigDecimal d33 = new BigDecimal("3.33");
        assertEquals(d33, BigDecimalUtil.avg(2, "3", 5L).setScale(2, RoundingMode.FLOOR));
        assertEquals(d33, BigDecimalUtil.avg(
                new BigDecimal("2"), null,
                new BigDecimal("3"), null,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));

        List<Object> av1 = Arrays.asList(2, "3", 5L);
        assertEquals(d33, BigDecimalUtil.avgMap(av1).setScale(2, RoundingMode.FLOOR));
        assertEquals(d33, BigDecimalUtil.avgMap(av1, Object::toString).setScale(2, RoundingMode.FLOOR));
        assertEquals(d33, BigDecimalUtil.avgMapNull(av1, Object::toString).setScale(2, RoundingMode.FLOOR));
    }

    @Test
    public void max() {
        assertEquals(TEN, BigDecimalUtil.max(2, "10", 5L));
        assertEquals(TEN, BigDecimalUtil.maxNull(2, "10", 5L));
    }

    @Test
    public void min() {
        assertEquals(ONE, BigDecimalUtil.min(1, "10", 5L));
        assertEquals(ONE, BigDecimalUtil.min(1, "10", 5L));
    }

    @Test
    public void sum() {
        assertEquals(new BigDecimal("16"), BigDecimalUtil.sum(1, "10", 5L));
        List<Object> list = Arrays.asList(1, "10", 5L);
        assertEquals(new BigDecimal("16"), BigDecimalUtil.sumMap(list));
        assertEquals(new BigDecimal("16"), BigDecimalUtil.sumMap(list, Object::toString));
    }

    @Test
    public void w() {
        BigDecimal n1 = BigDecimalUtil.w(ZERO)
                                      .add(ZERO, null, ONE, ONE, null)
                                      .sub(ONE, ONE, ONE, null)
                                      .subIf(true, null, null)
                                      .subIf(true, () -> null, () -> null)
                                      .subIf(true, ONE, null)
                                      .addIf(true, ONE, null)
                                      .subIf(false, ONE, null)
                                      .addIf(false, ONE, null)
                                      .resultFloor(2);
        assertEquals(new BigDecimal("-1.00"), n1);

        BigDecimal n2 = BigDecimalUtil.w(TEN, 2)
                                      .add(ONE)
                                      .mul(ONE)
                                      .mul("2.0", null, null, ONE, ONE)
                                      .mulIf(false, ZERO, ONE)
                                      .mulIf(false, () -> ZERO, () -> ONE)
                                      .mulMap(Arrays.asList(null, null, ONE, ONE))
                                      .mulMap(Arrays.asList(ONE, ONE), Objects::toString)
                                      .div(ONE)
                                      .div(ONE, ONE)
                                      .divIf(true, ONE, ZERO)
                                      .divIf(true, () -> ONE, () -> ZERO)
                                      .divMap(Arrays.asList(ONE, ONE))
                                      .divMap(Arrays.asList(ONE, ONE), Objects::toString)
                                      .neg()
                                      .neg()
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

    @Test
    public void unitDown() {
        final BigDecimal unit = new BigDecimal("0.50");
        final BigDecimal upto = new BigDecimal("0.10");

        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitDown("1.09", unit, upto));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitDown("1.4001", unit, upto));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitDown("1.40", unit, upto));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitDown("1.41", unit, upto));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitDown("1.51", unit, upto));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitDown("1.59", unit, upto));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitDown("1.90", unit, upto));
        assertEquals(new BigDecimal("2.00"), BigDecimalUtil.unitDown("1.91", unit, upto));
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
