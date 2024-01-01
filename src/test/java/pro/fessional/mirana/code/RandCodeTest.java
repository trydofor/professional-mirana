package pro.fessional.mirana.code;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

/**
 * @author trydofor
 * @since 2019-12-12
 */
public class RandCodeTest {

    @Test
    public void make() {
        SystemOut.println("number=" + RandCode.number(10));
        SystemOut.println("lower =" + RandCode.lower(10));
        SystemOut.println("upper =" + RandCode.upper(10));
        SystemOut.println("letter=" + RandCode.letter(10));
        SystemOut.println("numlet=" + RandCode.numlet(10));
        SystemOut.println("strong=" + RandCode.strong(10));
        SystemOut.println("human =" + RandCode.human(10));
        SystemOut.println("cjk =" + RandCode.cjk(10));
        SystemOut.println("mix =" + RandCode.mix(10));
        SystemOut.println("sur =" + RandCode.sur(1));
    }
}
