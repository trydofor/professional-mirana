package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-25
 */
class Aes128Test {

    @Test
    void encode16() {
        String json = "{id:1234567890,name='trydofor'}";
        String key = "420105198908100418";
        Aes128 aes128 = new Aes128(key);
        final String en = aes128.encode16(json);
        System.out.println(en);
        final String de = aes128.decode16(en);
        assertEquals(de, json);
    }

    @Test
    void encode64() {
        String json = "{id:1234567890,name='trydofor'}";
        String key = "420105198908100418";
        Aes128 aes128 = new Aes128(key);
        final String en = aes128.encode64(json);
        System.out.println(en);
        final String de = aes128.decode64(en);
        assertEquals(de, json);
    }

    @Test
    void empty() {
        String json = "";
        String key = "420105198908100418";
        Aes128 aes128 = new Aes128(key);
        final String en = aes128.encode64(json);
        System.out.println(en);
        final String de = aes128.decode64(en);
        assertEquals(de, json);
    }
}
