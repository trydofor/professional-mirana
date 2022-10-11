package pro.fessional.mirana.code;


import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-05-27
 */
public class LeapCodeTest {


    private final LeapCode codeSystem = new LeapCode();
    private final LeapCode codeCustom = new LeapCode();

    private final Random random = new Random();

    private void checkRandom(int base, LeapCode code) {
        for (int i = 0; i < 1000_000; i++) {
            String e = "--|" + code.encode(base, i, 30);
            long d = code.decode(e, 3, e.length());
            assertEquals(i, d);
        }
        for (int i = 0; i < 1000_000; i++) {
            long n = random.nextLong() & Crc8Long.MAX_NUMBER;
            String e = "--|" + code.encode(base, n, 30);
            long d = code.decode(e, 3, e.length());
            assertEquals(n, d);
        }
    }

    private void checkBound(int base, LeapCode code) {
        String e2 = "--|" + code.encode(base, LeapCode.MAX_NUMBER, 30);
        long d2 = code.decode(e2, 3, e2.length());
        assertEquals(LeapCode.MAX_NUMBER, d2);

        String e1 = "--|" + code.encode(base, LeapCode.MIN_NUMBER, 30);
        long d1 = code.decode(e1, 3, e1.length());
        assertEquals(LeapCode.MIN_NUMBER, d1);
    }

    @Test
    public void testRandom() {
        checkRandom(26, codeSystem);
        checkRandom(26, codeCustom);
        checkRandom(32, codeSystem);
        checkRandom(32, codeCustom);
    }

    @Test
    public void testBound() {
        checkBound(26, codeSystem);
        checkBound(26, codeCustom);
        checkBound(32, codeSystem);
        checkBound(32, codeCustom);
    }

    @Test
    public void printRandom() {
        for (int i = 0; i < 1000; i++) {
            String e = codeSystem.encode(26, i, 20);
            System.out.println("e=" + e + ",i=" + i);
        }
        for (int i = 0; i < 1000; i++) {
            String e = codeSystem.encode(32, i, 20);
            System.out.println("e=" + e + ",i=" + i);
        }
    }

    @Test
    public void printBound() {
        System.out.println("max32=" + codeSystem.encode32(LeapCode.MAX_NUMBER));
        System.out.println("min32=" + codeSystem.encode32(LeapCode.MIN_NUMBER));
        System.out.println("max26=" + codeSystem.encode26(LeapCode.MAX_NUMBER));
        System.out.println("min26=" + codeSystem.encode26(LeapCode.MIN_NUMBER));
    }

}
