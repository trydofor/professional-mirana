package pro.fessional.mirana.anti;

import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2021-03-24
 */
class LTest {

    @Test
    void parse() {
        L.error("error message");
        L.error("userid={}",1);
        L.message("message");
        final String rs1 = L.finish();
        System.out.println(rs1);
    }
}
