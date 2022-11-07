package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-25
 */
class Aes256Test {

    @Test
    void encode16() {
        String json = "{id:1234567890,name='trydofor'}";
        String key = "4201051989080418";
        Aes256 aes256 = new Aes256(key);
        final String en = aes256.encode16(json);
        System.out.println("Aes256.encode16=" + en);
        aes256 = new Aes256(key);
        final String de = aes256.decode16(en);
        assertEquals(de, json);
    }

    @Test
    void encode64() {
        String json = "{id:1234567890,name='trydofor'}";
        String key = "420105198908100418";
        Aes256 aes256 = new Aes256(key);
        final String en = aes256.encode64(json);
        System.out.println("Aes256.encode64=" + en);
        aes256 = new Aes256(key);
        final String de = aes256.decode64(en);
        assertEquals(de, json);
    }

    @Test
    void empty() {
        String json = "";
        String key = "420105198908100418";
        Aes256 aes256 = new Aes256(key);
        final String en = aes256.encode64(json);
        System.out.println("Aes256.empty=" + en);
        aes256 = new Aes256(key);
        final String de = aes256.decode64(en);
        assertEquals(de, json);
    }
}
