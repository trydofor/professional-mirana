package pro.fessional.mirana.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class HalfCharUtilTest {

    @Test
    public void half() {
        String full = "０１２３４５６７８９ｑｗｅｒｔｙｕｉｏｐ［］";
        String half = "0123456789qwertyuiop[]";
        assertEquals(half, HalfCharUtil.half(full));
    }
}