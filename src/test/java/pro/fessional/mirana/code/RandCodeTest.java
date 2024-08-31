package pro.fessional.mirana.code;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

/**
 * @author trydofor
 * @since 2019-12-12
 */
public class RandCodeTest {

    @Test
    public void make() {
        Testing.println("number=" + RandCode.number(10));
        Testing.println("lower =" + RandCode.lower(10));
        Testing.println("upper =" + RandCode.upper(10));
        Testing.println("letter=" + RandCode.letter(10));
        Testing.println("numlet=" + RandCode.numlet(10));
        Testing.println("strong=" + RandCode.strong(10));
        Testing.println("human =" + RandCode.human(10));
        Testing.println("cjk =" + RandCode.cjk(10));
        Testing.println("mix =" + RandCode.mix(10));
        Testing.println("sur =" + RandCode.sur(1));
    }
}
