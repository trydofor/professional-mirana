package pro.fessional.mirana.data;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author trydofor
 * @since 2019-10-29
 */
public class ZTest {

    @Test
    public void testUniq() {
        List<U.Two<Integer, String>> list = Arrays.asList(
                U.of(1, "A"),
                U.of(2, "B"),
                U.of(1, "A"),
                U.of(2, "B")
        );
        List<U.Two<Integer, String>> uniq = Z.uniq(list, U.Two::one, U.Two::two);
        Assert.assertEquals(uniq, Arrays.asList(
                U.of(1, "A"),
                U.of(2, "B")));
    }

    @Test
    public void testFind() {
        U.Two<Integer, String> u = Z.find(it -> it.one() == 1, U.of(1, "A"),
                U.of(2, "B"),
                U.of(1, "A"));
        Assert.assertEquals(u, U.of(1, "A"));
    }

    @Test
    public void testMake() {
        BigDecimal d = Z.make(BigDecimal::new, "null", null, "1");
        Assert.assertEquals(d, BigDecimal.ONE);
    }
}
