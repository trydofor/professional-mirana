package pro.fessional.mirana.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-19
 */
class AverageDecimalTest {

    @Test
    void avg() {
        AverageDecimal avg0 = AverageDecimal.of(BigDecimal.TEN, 5,1);
        Assertions.assertEquals(new BigDecimal("2.0"), avg0.getAvgValue());
        Assertions.assertEquals(BigDecimal.TEN, avg0.total());
        Assertions.assertEquals(1, avg0.scale());
        Assertions.assertEquals(5, avg0.size());
        Assertions.assertEquals(new BigDecimal("0.1"), avg0.getPrecision());

        AverageDecimal avg1 = AverageDecimal.of(BigDecimal.TEN, 3,1);
        Assertions.assertEquals(new BigDecimal("3.3"), avg1.getAvgValue());
        Assertions.assertEquals(BigDecimal.TEN, avg1.total());
        Assertions.assertEquals(1, avg1.scale());
        Assertions.assertEquals(3, avg1.size());
        Assertions.assertEquals(new BigDecimal("0.1"), avg1.getPrecision());
        Assertions.assertEquals(new BigDecimal("3.4"), avg1.getFixValue());
        Assertions.assertEquals(1, avg1.getFixCount());
    }

    @Test
    void of() {
        BigDecimal number = new BigDecimal(100);

        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j);
            Testing.println(avg);
            Testing.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (int i = 0; i < avg.size(); i++) {
                BigDecimal v = avg.get(i);
                sum = sum.add(v);
                Testing.print(v);
                Testing.print(" + ");
            }
            Testing.println("] = " + sum + " :" + (sum.compareTo(number) == 0));
            assertEquals(0, sum.compareTo(number));
        }

        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j, 0);
            Testing.println(avg);
            Testing.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal v : avg) {
                sum = sum.add(v);
                Testing.print(v);
                Testing.print(" + ");
            }
            Testing.println("] = " + sum + " :" + (sum.compareTo(number) == 0));
            assertEquals(0, sum.compareTo(number));
        }
    }
}
