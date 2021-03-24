package pro.fessional.mirana.data;

import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2021-03-24
 */
class VTest {

    @Test
    void parse() {
        V.error("error message");
        V.error("userid={}",1);
        V.message("message");
        final String rs1 = V.finish();
        System.out.println(rs1);
    }
}
