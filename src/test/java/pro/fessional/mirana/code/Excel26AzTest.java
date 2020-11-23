package pro.fessional.mirana.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-05
 */
public class Excel26AzTest {

    @Test
    public void test1() {
        String[] c26 = ("A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z," +
                "AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,AO,AP,AQ,AR,AS,AT,AU,AV,AW,AX,AY,AZ," +
                "BA,BB,BC,BD,BE,BF,BG,BH,BI,BJ,BK,BL,BM,BN,BO,BP,BQ,BR,BS,BT,BU,BV,BW,BX,BY,BZ").split(",");

        for (int i = 0; i < c26.length; i++) {
            String col = c26[i];
            int num = Excel26Az.number(col);
            int n = i + 1;
            String e = Excel26Az.number(n);
            assertEquals(n, num);
            assertEquals(col, e);
        }
    }

    @Test
    public void test2() {
        for (int i = 1; i < 300; i++) {
            String ns0 = Excel26Az.number(i);
            int ni1 = Excel26Az.number(ns0);
            String ns2 = Excel26Az.number(ni1);
            int ni3 = Excel26Az.number(ns2);

            assertEquals(ns0, ns2);
            assertEquals(ni1, ni3);
            assertEquals(i, ni3);

            int ii1 = Excel26Az.index(ns0);
            String is2 = Excel26Az.index(ii1);
            int ii3 = Excel26Az.index(is2);

            assertEquals(ns0, is2);
            assertEquals(ii1, ii3);
            assertEquals(ii1, i - 1);
        }
    }
}