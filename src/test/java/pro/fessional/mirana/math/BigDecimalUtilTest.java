package pro.fessional.mirana.math;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.*;

/**
 * @author trydofor
 * @since 2020-06-28
 */
public class BigDecimalUtilTest {
    static BigDecimal NINE = new BigDecimal("9");
    static BigDecimal NULL = null;

    @Test
    public void add() {
        Assert.assertEquals(ZERO, BigDecimalUtil.add(NULL, NULL, NULL));
        Assert.assertEquals(ONE, BigDecimalUtil.add(NULL, ONE, NULL));
        Assert.assertEquals(ONE, BigDecimalUtil.add(NULL, NULL, ONE));
        Assert.assertEquals(ONE, BigDecimalUtil.add(NULL, ONE, ONE));

        Assert.assertEquals(ONE, BigDecimalUtil.add(ONE, NULL, NULL));
        Assert.assertEquals(TEN, BigDecimalUtil.add(ONE, NULL, NINE));
        Assert.assertEquals(TEN, BigDecimalUtil.add(ONE, NINE, NULL));
        Assert.assertEquals(TEN, BigDecimalUtil.add(ONE, NINE, ONE));
    }

    @Test
    public void sub() {
        BigDecimal NEG1 = new BigDecimal("-1");
        Assert.assertEquals(ZERO, BigDecimalUtil.sub(NULL, NULL, NULL));
        Assert.assertEquals(NEG1, BigDecimalUtil.sub(NULL, ONE, NULL));
        Assert.assertEquals(NEG1, BigDecimalUtil.sub(NULL, NULL, ONE));
        Assert.assertEquals(NEG1, BigDecimalUtil.sub(NULL, ONE, ONE));

        Assert.assertEquals(ONE, BigDecimalUtil.sub(ONE, NULL, NULL));
        Assert.assertEquals(ONE, BigDecimalUtil.sub(TEN, NULL, NINE));
        Assert.assertEquals(ONE, BigDecimalUtil.sub(TEN, NINE, NULL));
        Assert.assertEquals(ONE, BigDecimalUtil.sub(TEN, NINE, ONE));
    }

    @Test
    public void mul() {
        Assert.assertEquals(ZERO, BigDecimalUtil.mul(NULL, NULL, NULL));
        Assert.assertEquals(ZERO, BigDecimalUtil.mul(NULL, ONE, NULL));
        Assert.assertEquals(ZERO, BigDecimalUtil.mul(NULL, NULL, ONE));
        Assert.assertEquals(ZERO, BigDecimalUtil.mul(NULL, ONE, ONE));

        Assert.assertEquals(ZERO, BigDecimalUtil.mul(ONE, NULL, NULL));
        Assert.assertEquals(TEN, BigDecimalUtil.mul(TEN, NULL, ONE));
        Assert.assertEquals(TEN, BigDecimalUtil.mul(TEN, ONE, NULL));
        Assert.assertEquals(TEN, BigDecimalUtil.mul(TEN, ONE, NINE));
    }

    @Test
    public void div() {
        Assert.assertEquals(ZERO, BigDecimalUtil.div(NULL, ONE, NULL));
        Assert.assertEquals(ZERO, BigDecimalUtil.div(NULL, NULL, ONE));
        Assert.assertEquals(ZERO, BigDecimalUtil.div(NULL, ONE, ONE));

        Assert.assertEquals(TEN, BigDecimalUtil.div(TEN, NULL, ONE));
        Assert.assertEquals(TEN, BigDecimalUtil.div(TEN, ONE, NULL));
        Assert.assertEquals(TEN, BigDecimalUtil.div(TEN, ONE, NINE));
    }

    @Test
    public void string() {
        Assert.assertEquals("0", BigDecimalUtil.string(ZERO, 0));
        Assert.assertEquals("0.0", BigDecimalUtil.string(ZERO, 1));
        Assert.assertEquals("0.00", BigDecimalUtil.string(ZERO, 2));
        Assert.assertEquals("1.00", BigDecimalUtil.string(ONE, 2));
        Assert.assertEquals("10", BigDecimalUtil.string(ONE, -1));
    }

    @Test
    public void testObject() {
        Assert.assertEquals(new BigDecimal("0"), BigDecimalUtil.object("0"));
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.object("0.0"));
        Assert.assertEquals(new BigDecimal("0.00"), BigDecimalUtil.object("0.00"));
        Assert.assertEquals(new BigDecimal("1.00"), BigDecimalUtil.object("1.00"));
        Assert.assertEquals(new BigDecimal("10"), BigDecimalUtil.object("10"));
    }

    @Test
    public void avg() {
        Assert.assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(
                new BigDecimal("2"),
                new BigDecimal("3"),
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("2.00"), BigDecimalUtil.avg(
                new BigDecimal("2"), NULL,
                new BigDecimal("3"), NULL,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(true,
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
        Assert.assertEquals(new BigDecimal("-1.00"), n1);

        BigDecimal n2 = BigDecimalUtil.w(2, NULL, TEN)
                                      .add(ONE)
                                      .mul(new BigDecimal("2.0"))
                                      .pow(2)
                                      .resultFloor();

        Assert.assertEquals(new BigDecimal("484.00"), n2);
    }

    @Test
    public void unitUp(){
        final BigDecimal unit = new BigDecimal("0.50");
        final BigDecimal down = new BigDecimal("0.10");

        Assert.assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp(new BigDecimal("1.09"), unit, down));
        Assert.assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp(new BigDecimal("1.1001"), unit, down));
        Assert.assertEquals(new BigDecimal("1.00"), BigDecimalUtil.unitUp(new BigDecimal("1.10"), unit, down));
        Assert.assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.11"), unit, down));
        Assert.assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.20"), unit, down));
        Assert.assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.51"), unit, down));
        Assert.assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.59"), unit, down));
        Assert.assertEquals(new BigDecimal("1.50"), BigDecimalUtil.unitUp(new BigDecimal("1.60"), unit, down));
        Assert.assertEquals(new BigDecimal("2.00"), BigDecimalUtil.unitUp(new BigDecimal("1.61"), unit, down));
    }
}