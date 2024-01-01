package pro.fessional.mirana.math;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-19
 */
class AverageDecimalTest {

    @Test
    void of() {
        BigDecimal number = new BigDecimal(100);

        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j);
            SystemOut.println(avg);
            SystemOut.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (int i = 0; i < avg.size(); i++) {
                BigDecimal v = avg.get(i);
                sum = sum.add(v);
                SystemOut.print(v);
                SystemOut.print(" + ");
            }
            SystemOut.println("] = " + sum + " :" + (sum.compareTo(number) == 0));
            assertEquals(0, sum.compareTo(number));
        }

        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j, 0);
            SystemOut.println(avg);
            SystemOut.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal v : avg) {
                sum = sum.add(v);
                SystemOut.print(v);
                SystemOut.print(" + ");
            }
            SystemOut.println("] = " + sum + " :" + (sum.compareTo(number) == 0));
            assertEquals(0, sum.compareTo(number));
        }

    }
}
