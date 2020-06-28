package pro.fessional.mirana.math;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * @author trydofor
 * @since 2020-06-28
 */
public class BigDecimalUtilTest {

    @Test
    public void string() {
        Assert.assertEquals("0", BigDecimalUtil.string(BigDecimal.ZERO, 0));
        Assert.assertEquals("0.0", BigDecimalUtil.string(BigDecimal.ZERO, 1));
        Assert.assertEquals("0.00", BigDecimalUtil.string(BigDecimal.ZERO, 2));
        Assert.assertEquals("1.00", BigDecimalUtil.string(BigDecimal.ONE, 2));
        Assert.assertEquals("10", BigDecimalUtil.string(BigDecimal.ONE, -1));
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
                new BigDecimal("2"), null,
                new BigDecimal("3"), null,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("3.33"), BigDecimalUtil.avg(true,
                new BigDecimal("2"), null,
                new BigDecimal("3"), null,
                new BigDecimal("5")
        ).setScale(2, RoundingMode.FLOOR));
    }

    @Test
    public void w() {
        BigDecimal v = null;
        BigDecimal n1 = BigDecimalUtil.w(v, 2)
                                      .add(BigDecimal.ZERO)
                                      .sub(BigDecimal.ONE)
                                      .resultFloor();
        Assert.assertEquals(new BigDecimal("-1.00"), n1);

        BigDecimal n2 = BigDecimalUtil.w(2, null, BigDecimal.TEN)
                                              .add(BigDecimal.ONE)
                                              .mul(new BigDecimal("2.0"))
                                              .pow(2)
                                              .resultFloor();

        Assert.assertEquals(new BigDecimal("484.00"), n2);
    }
}