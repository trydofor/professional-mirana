package pro.fessional.mirana.mass;

import pro.fessional.mirana.code.LeapCode;

import java.util.Random;

/**
 * @author trydofor
 * @since 2019-05-27
 */
public class TestRandomSpeed {
    public static void main(String[] args) {

        long s0 = System.nanoTime();
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            long test = System.nanoTime();
        }
        long s1 = System.nanoTime();
        System.out.printf("\n%d, System.nanoTime", (s1 - s0));

        long s2 = System.nanoTime();
        for (int i = 0; i < times; i++) {
            long test = System.currentTimeMillis();
        }
        long s3 = System.nanoTime();
        System.out.printf("\n%d, System.currentTimeMillis", (s3 - s2));

        Random random = new Random();
        long s4 = System.nanoTime();
        for (int i = 0; i < times; i++) {
            long test = random.nextLong();
        }
        long s5 = System.nanoTime();
        System.out.printf("\n%d, Random.nextLong", (s5 - s4));

        LeapCode leapCode = new LeapCode();
        long s6 = System.nanoTime();
        for (int i = 0; i < times; i++) {
            String test = leapCode.encode32(i);
        }
        long s7 = System.nanoTime();
        System.out.printf("\n%d, LeapCode", (s7 - s6));


        long s8 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            String test = leapCode.encode32(i);
        }
        long s9 = System.currentTimeMillis();
        System.out.printf("\n%d ms, LeapCode, %.2f/ms", (s9 - s8), times * 1.0 / (s9 - s8));
    }
}
