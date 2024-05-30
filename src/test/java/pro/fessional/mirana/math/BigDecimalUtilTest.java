package pro.fessional.mirana.math;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertEquals(ZERO, BigDecimalUtil.add(ONE, -1));
        assertEquals(ZERO, BigDecimalUtil.add(ONE, -1L));
        assertEquals(ZERO, BigDecimalUtil.round(BigDecimalUtil.add(ONE, -1D), 0));

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
        assertEquals(ZERO, BigDecimalUtil.sub(ONE, 1));
        assertEquals(ZERO, BigDecimalUtil.sub(ONE, 1L));
        assertEquals(new BigDecimal("0.0"), BigDecimalUtil.sub(ONE, 1D));
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
        assertEquals(ONE, BigDecimalUtil.mul(ONE, 1));
        assertEquals(ONE, BigDecimalUtil.mul(ONE, 1L));
        assertEquals(new BigDecimal("1.0"), BigDecimalUtil.mul(ONE, 1D));
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
        assertEquals(ONE, BigDecimalUtil.div(ONE, 1));
        assertEquals(ONE, BigDecimalUtil.div(ONE, 1L));
        assertEquals(ONE, BigDecimalUtil.div(ONE, 1D));
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

    Object getZeroStr = (Supplier<String>) () -> "0";


    Object getZeroInt = (IntSupplier) () -> 0;

    Object getZeroLong = (LongSupplier) () -> 0L;

    Object getZeroDouble = (DoubleSupplier) () -> 0D;

    Object object = new Object() {
        @Override public String toString() {
            return "0";
        }
    };

    @Test
    public void testObject() {
        assertEquals(ZERO, BigDecimalUtil.object(object));
        assertEquals(ZERO, BigDecimalUtil.object("0"));
        assertEquals(ZERO, BigDecimalUtil.object((Object) null, ZERO));
        assertEquals(ZERO, BigDecimalUtil.object((Object) null, () -> ZERO));
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
        assertEquals(ZERO, BigDecimalUtil.object(getZeroStr));
        assertEquals(ZERO, BigDecimalUtil.object(getZeroInt));
        assertEquals(ZERO, BigDecimalUtil.object(getZeroLong));
        assertEquals(ZERO, BigDecimalUtil.object(getZeroDouble).setScale(0, RoundingMode.FLOOR));

        assertEquals(ZERO, BigDecimalUtil.object("BAD", () -> ZERO, false));
        try {
            assertEquals(ZERO, BigDecimalUtil.object("BAD", () -> ZERO, true));
            Assertions.fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof NumberFormatException);
        }
    }

    @Test
    public void notNull() {
        assertEquals(TEN, BigDecimalUtil.notNull(null, "10"));
        assertEquals(TEN, BigDecimalUtil.ifElse(false, null, "10"));
        assertEquals(TEN, BigDecimalUtil.ifElse(false, () -> null, () -> "10"));
        assertEquals(ZERO, BigDecimalUtil.ifElse(false, null, getZeroStr));

        assertEquals(0, BigDecimalUtil.compareTo(10, "10"));
        assertEquals(0, BigDecimalUtil.compareTo(10, "10.01", 1, RoundingMode.FLOOR));
        assertTrue(BigDecimalUtil.equalsValue(10, "10.00"));
        assertTrue(BigDecimalUtil.equalsValue(10, "10.01", 1, RoundingMode.FLOOR));
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

        BigDecimalUtil.W w0 = BigDecimalUtil.w(ZERO);
        w0.setValue("2.1").ceil(0);
        assertEquals(new BigDecimal("3"), w0.resultRaw());
        assertEquals(new BigDecimal("3.00"), w0.result(2, RoundingMode.FLOOR));
        w0.setValue("2.1");
        assertEquals(new BigDecimal("3"), w0.resultCeil(0));

        w0.setValue("2.1").floor(0);
        assertEquals(new BigDecimal("2"), w0.resultRaw());
        w0.setValue("2.1").round(0);
        assertEquals(new BigDecimal("2"), w0.resultRaw());
        w0.setValue("2.6").round(0);
        assertEquals(new BigDecimal("3"), w0.resultRaw());

        final BigDecimal unit = new BigDecimal("0.50");
        final BigDecimal down = new BigDecimal("0.10");
        final BigDecimal upto = new BigDecimal("0.10");
        w0.setValue("1.09");
        assertEquals(new BigDecimal("1.00"), w0.resultUnitUp(unit, down));
        assertEquals(new BigDecimal("1.00"), w0.resultUnitDown(unit, upto));
        w0.setValue("1.1001");
        assertEquals(new BigDecimal("1.00"), w0.resultUnitUp(unit, down));
        w0.setValue("1.4001");
        assertEquals(new BigDecimal("1.00"), w0.resultUnitDown(unit, upto));


        BigDecimalUtil.W w1 = BigDecimalUtil.w(ZERO)
                                            .add(ZERO, null, ONE, ONE, null)
                                            .sub(ONE, ONE, ONE, null)
                                            .subIf(true, null, null)
                                            .subIf(true, () -> null, () -> null)
                                            .subIf(true, ONE, null)
                                            .addIf(true, ONE, null)
                                            .subIf(false, ONE, null)
                                            .addIf(false, ONE, null);

        assertEquals(new BigDecimal("-1"), w1.resultFloor());
        assertEquals(new BigDecimal("-1.00"), w1.resultFloor(2));
        assertEquals(new BigDecimal("-1"), w1.result(RoundingMode.FLOOR));
        assertEquals(new BigDecimal("-1"), w1.resultCeil());
        assertEquals(new BigDecimal("-1.00"), w1.resultCeil(2));
        assertEquals(new BigDecimal("-1"), w1.resultRound());
        assertEquals(new BigDecimal("-1.00"), w1.resultRound(2));

        w1.setValue("2.0");
        assertEquals(new BigDecimal("2.0"), w1.resultRaw());

        w1.add(1);
        w1.add(1L);
        w1.add(1D);
        w1.add("1");
        w1.addIf(true, () -> 1L, () -> -1L);
        assertEquals(new BigDecimal("7.0"), w1.resultRaw());

        w1.sub(1);
        w1.sub(1L);
        w1.sub(1D);
        w1.sub("1");
        w1.subIf(true, () -> 1L, () -> -1L);
        assertEquals(new BigDecimal("2.0"), w1.resultRaw());

        w1.mul(1);
        w1.mul(1L);
        w1.mul(1D); // double scale
        w1.mul("1");
        w1.mulIf(true, () -> 1L, () -> -1L);
        assertEquals(new BigDecimal("2.00"), w1.resultRaw());

        w1.div(1);
        w1.div(1L);
        w1.div(1D);
        w1.div("1");
        w1.divIf(true, () -> 1L, () -> -1L);
        assertEquals(new BigDecimal("2.0"), w1.resultRaw());

        BigDecimalUtil.W w2 = BigDecimalUtil.w(TEN, 2)
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
                                            .pow(2);
        assertEquals(new BigDecimal("484.00"), w2.resultFloor());
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
        assertEquals(ZERO, min1);
        assertEquals(ZERO, min2);


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

        assertEquals(new BigDecimal("0.0"), BigDecimalUtil.round(null, 1));
    }
}
