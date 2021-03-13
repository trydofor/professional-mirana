package pro.fessional.mirana.code;

import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2019-12-12
 */
public class RandCodeTest {

    @Test
    public void make() {
        System.out.println("number=" + RandCode.number(10));
        System.out.println("lower =" + RandCode.lower(10));
        System.out.println("upper =" + RandCode.upper(10));
        System.out.println("letter=" + RandCode.letter(10));
        System.out.println("numlet=" + RandCode.numlet(10));
        System.out.println("strong=" + RandCode.strong(10));
        System.out.println("human =" + RandCode.human(10));
        System.out.println("cjk =" + RandCode.cjk(10));
        System.out.println("mix =" + RandCode.mix(10));
        System.out.println("sur =" + RandCode.sur(1));
    }
}
