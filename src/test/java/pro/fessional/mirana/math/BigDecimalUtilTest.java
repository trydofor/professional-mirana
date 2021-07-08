package pro.fessional.mirana.math;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-06-28
 */
public class BigDecimalUtilTest {
    static BigDecimal NINE = new BigDecimal("9");
    static BigDecimal NULL = null;

    @Test
    public void add() {
        assertEquals(ZERO, BigDecimalUtil.add(NULL, NULL, NULL));
        assertEquals(ONE, BigDecimalUtil.add(NULL, ONE, NULL));
        assertEquals(ONE, BigDecimalUtil.add(NULL, NULL, ONE));
        assertEquals(ONE, BigDecimalUtil.add(NULL, ONE, ONE));

        assertEquals(ONE, BigDecimalUtil.add(ONE, NULL, NULL));
        assertEquals(TEN, BigDecimalUtil.add(ONE, NULL, NINE));
        assertEquals(TEN, BigDecimalUtil.add(ONE, NINE, NULL));
        assertEquals(TEN, BigDecimalUtil.add(ONE, NINE, ONE));
    }

    @Test
    public void sub() {
        BigDecimal NEG1 = new BigDecimal("-1");
        assertEquals(ZERO, BigDecimalUtil.sub(NULL, NULL, NULL));
        assertEquals(NEG1, BigDecimalUtil.sub(NULL, ONE, NULL));
        assertEquals(NEG1, BigDecimalUtil.sub(NULL, NULL, ONE));
        assertEquals(NEG1, BigDecimalUtil.sub(NULL, ONE, ONE));

        assertEquals(ONE, BigDecimalUtil.sub(ONE, NULL, NULL));
        assertEquals(ONE, BigDecimalUtil.sub(TEN, NULL, NINE));
        assertEquals(ONE, BigDecimalUtil.sub(TEN, NINE, NULL));
        assertEquals(ONE, BigDecimalUtil.sub(TEN, NINE, ONE));
    }

    @Test
    public void mul() {
        assertEquals(ZERO, BigDecimalUtil.mul(NULL, NULL, NULL));
        assertEquals(ZERO, BigDecimalUtil.mul(NULL, ONE, NULL));
        assertEquals(ZERO, BigDecimalUtil.mul(NULL, NULL, ONE));
        assertEquals(ZERO, BigDecimalUtil.mul(NULL, ONE, ONE));

        assertEquals(ZERO, BigDecimalUtil.mul(ONE, NULL, NULL));
        assertEquals(TEN, BigDecimalUtil.mul(TEN, NULL, ONE));
        assertEquals(TEN, BigDecimalUtil.mul(TEN, ONE, NULL));
        assertEquals(TEN, BigDecimalUtil.mul(TEN, ONE, NINE));
    }

    @Test
    public void div() {
        assertEquals(ZERO, BigDecimalUtil.div(NULL, ONE, NULL));
        assertEquals(ZERO, BigDecimalUtil.div(NULL, NULL, ONE));
        assertEquals(ZERO, BigDecimalUtil.div(NULL, ONE, ONE));

        assertEquals(TEN, BigDecimalUtil.div(TEN, NULL, ONE));
        assertEquals(TEN, BigDecimalUtil.div(TEN, ONE, NULL));
        assertEquals(TEN, BigDecimalUtil.div(TEN, ONE, NINE));
    }

    @Test
    public void string() {
        assertEquals("0", BigDecimalUtil.string(ZERO, 0));
        assertEquals("0.0", BigDecimalUtil.string(ZERO, 1));
        assertEquals("0.00", BigDecimalUtil.string(ZERO, 2));
        assertEquals("1.00", BigDecimalUtil.string(ONE, 2));
        assertEquals("10", BigDecimalUtil.string(ONE, -1));
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
        assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(
                new BigDecimal("2"),
                new BigDecimal("3"),
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
        assertEquals(new BigDecimal("2.00"), BigDecimalUtil.avg(
                new BigDecimal("2"), NULL,
                new BigDecimal("3"), NULL,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
        assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(true,
                new BigDecimal("2"), NULL,
                new BigDecimal("3"), NULL,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
    }

    @Test
    public void w() {
        BigDecimal v = NULL;
        BigDecimal n1 = BigDecimalUtil.w(v, 2)
                                      .add(ZERO)
                                      .sub(ONE)
                                      .resultFloor();
        assertEquals(new BigDecimal("-1.00"), n1);

        BigDecimal n2 = BigDecimalUtil.w(2, NULL, TEN)
                                      .add(ONE)
                                      .mul(new BigDecimal("2.0"))
                                      .pow(2)
                                      .resultFloor();

        assertEquals(new BigDecimal("484.00"), n2);
    }

    @Test
    public void unitUp() {
        final BigDecimal unit = new BigDecimal("0.50");
        final BigDecimal down = new BigDecimal("0.10");

        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp(new BigDecimal("1.09"), unit, down));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp(new BigDecimal("1.1001"), unit, down));
        assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp(new BigDecimal("1.10"), unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.11"), unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.20"), unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.51"), unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.59"), unit, down));
        assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.60"), unit, down));
        assertEquals(new BigDecimal("2.00"), BigDecimalUtil.unitUp(new BigDecimal("1.61"), unit, down));
    }
}
