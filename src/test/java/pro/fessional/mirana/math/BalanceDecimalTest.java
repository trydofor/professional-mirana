package pro.fessional.mirana.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-19
 */
class BalanceDecimalTest {

    @Test
    void testBig() {
        BigDecimal total = new BigDecimal("1175.00");
        List<BigDecimal> items = new ArrayList<>(20);
        items.add(new BigDecimal("10.94"));
        items.add(new BigDecimal("8.5"));
        items.add(new BigDecimal("9.18"));
        items.add(new BigDecimal("6.2"));
        items.add(new BigDecimal("9"));
        items.add(new BigDecimal("23.56"));
        items.add(new BigDecimal("9.3"));
        items.add(new BigDecimal("8.5"));
        items.add(new BigDecimal("25.08"));
        items.add(new BigDecimal("15.19"));
        items.add(new BigDecimal("16.58"));
        items.add(new BigDecimal("8.5"));
        items.add(new BigDecimal("19.76"));

        BalanceDecimal avg = BalanceDecimal.of(total, items);
        BigDecimal sumBal = BigDecimal.ZERO;
        for (BigDecimal v : avg) {
            sumBal = sumBal.add(v);
            SystemOut.println(v);
        }

        assertEquals(0, total.compareTo(sumBal));
    }

    @Test
    void testTen() {
        BigDecimal total = new BigDecimal("10.00");
        List<BigDecimal> items = Arrays.asList(
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1")
        );
        BalanceDecimal avg = BalanceDecimal.of(total, items, 2);
        final BigDecimal v1 = new BigDecimal("3.34");
        final BigDecimal v2 = new BigDecimal("3.33");
        for (int i = 0; i < 3; i++) {
            BigDecimal v = avg.get(i);
            assertTrue(v1.equals(v) || v2.equals(v));
        }

        Assertions.assertEquals(3, avg.size());
        Assertions.assertEquals(total, avg.total());
        Assertions.assertEquals(2, avg.scale());
        Assertions.assertEquals(new BigDecimal("0.01"), avg.getPrecision());
    }

    @Test
    void testTwn() {
        BigDecimal total = new BigDecimal("20.00");
        List<BigDecimal> items = Arrays.asList(
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1")
        );
        BalanceDecimal avg = BalanceDecimal.of(total, items, 3);
        final BigDecimal v1 = new BigDecimal("3.334");
        final BigDecimal v2 = new BigDecimal("3.333");
        for (BigDecimal v : avg) {
            assertTrue(v1.equals(v) || v2.equals(v));
        }
    }
}
