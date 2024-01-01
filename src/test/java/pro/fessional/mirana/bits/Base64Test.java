package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.code.RandCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-24
 */
class Base64Test {

    @Test
    void encode() {
        for (int i = 0; i < 10; i++) {
            String en = RandCode.human(10 + i);
            final String b64 = Base64.encode(en);
            final String de = Base64.de2str(b64);
            assertEquals(en, de);
        }
    }


    @Test
    void padding() {
        final java.util.Base64.Encoder ue = java.util.Base64.getUrlEncoder();
        for (int i = 0; i < 100; i++) {
            String en = RandCode.human(10 + i);
            final String b64 = Base64.encode(en);
            final String de = Base64.de2str(b64);
            final String ob = ue.encodeToString(en.getBytes());
            final String nb = Base64.pad(b64);
            assertEquals(en, de);
            assertEquals(ob, nb);
        }
    }

    @Test
    void print() {
        String en = "1234567890";
        String de = "MTIzNDU2Nzg5MA==";
        final String b64 = Base64.encode(en);
        String str = Base64.de2str(de);
        SystemOut.println(b64);
        SystemOut.println(str);
        Assertions.assertTrue(de.contains(b64));
        Assertions.assertEquals(en,str);
    }
}
