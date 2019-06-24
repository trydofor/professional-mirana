package pro.fessional.mirana.text;

import org.junit.Test;

import static org.junit.Assert.*;

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