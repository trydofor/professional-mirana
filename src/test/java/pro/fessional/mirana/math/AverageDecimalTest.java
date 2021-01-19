package pro.fessional.mirana.math;

import org.junit.jupiter.api.Test;

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
            System.out.println(avg);
            System.out.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (int i = 0; i < avg.size(); i++) {
                BigDecimal v = avg.get(i);
                sum = sum.add(v);
                System.out.print(v);
                System.out.print(" + ");
            }
            System.out.println("] = "+sum + " :" + (sum.compareTo(number) == 0));
            assertEquals(0, sum.compareTo(number));
        }

        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j, 0);
            System.out.println(avg);
            System.out.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal v : avg) {
                sum = sum.add(v);
                System.out.print(v);
                System.out.print(" + ");
            }
            System.out.println("] = "+sum+" :" + (sum.compareTo(number)==0));
            assertEquals(0, sum.compareTo(number));
        }

    }
}
