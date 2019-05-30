package pro.fessional.mirana.code;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author trydofor
 * @since 2019-06-05
 */
public class Excel26UtilTest {

    @Test
    public void test10() {
        String[] c26 = ("A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z," +
                "AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,AO,AP,AQ,AR,AS,AT,AU,AV,AW,AX,AY,AZ," +
                "BA,BB,BC,BD,BE,BF,BG,BH,BI,BJ,BK,BL,BM,BN,BO,BP,BQ,BR,BS,BT,BU,BV,BW,BX,BY,BZ").split(",");

        for (int i = 0; i < c26.length; i++) {
            int d = Excel26Util.decode(c26[i]);
            String e = Excel26Util.encode(i + 1);
            assertEquals(i + 1, d);
            assertEquals(c26[i], e);
        }
    }
}