package pro.fessional.mirana.cond;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author trydofor
 * @since 2020-04-01
 */
public class ContainsTest {

    @Test
    public void hasVal() {
        Assert.assertTrue(Contains.eqVal(1, 1, 2, 3));
        Assert.assertTrue(Contains.eqVal(1, Arrays.asList(1, 2, 3)));
        Assert.assertTrue(Contains.eqVal(1, new Long(1), new Long(2), new Integer(3)));
        Assert.assertTrue(Contains.eqVal(1, new Integer(1), new Long(2), new Integer(3)));
        Assert.assertTrue(Contains.eqVal(1, new BigDecimal(1), new Long(2), new Integer(3)));
        Assert.assertTrue(Contains.eqVal(new BigDecimal("1.0"), new BigDecimal(1), new Long(2), new Integer(3)));
    }

    @Test
    public void testStr() {
        Assert.assertTrue(Contains.eqCase("1", "1", "2"));
        Assert.assertTrue(Contains.eqCase("1", Arrays.asList("1", "2")));
        Assert.assertTrue(Contains.igCase("a", "A", "2"));
        Assert.assertTrue(Contains.igCase("a", Arrays.asList("A", "2")));
    }

}