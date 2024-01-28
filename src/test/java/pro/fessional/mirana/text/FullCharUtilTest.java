package pro.fessional.mirana.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2024-01-28
 */
class FullCharUtilTest {

    @Test
    void leftCut() {
        assertEquals("", FullCharUtil.leftCut("我爱你ivy", 0));
        assertEquals("", FullCharUtil.leftCut("我爱你ivy", 1));
        assertEquals("我", FullCharUtil.leftCut("我爱你ivy", 2));
        assertEquals("我", FullCharUtil.leftCut("我爱你ivy", 3));
        assertEquals("我爱", FullCharUtil.leftCut("我爱你ivy", 4));
        assertEquals("我爱", FullCharUtil.leftCut("我爱你ivy", 5));
        assertEquals("我爱你", FullCharUtil.leftCut("我爱你ivy", 6));
        assertEquals("我爱你i", FullCharUtil.leftCut("我爱你ivy", 7));
        assertEquals("我爱你iv", FullCharUtil.leftCut("我爱你ivy", 8));
        assertEquals("我爱你ivy", FullCharUtil.leftCut("我爱你ivy", 9));
        assertEquals("我爱你ivy", FullCharUtil.leftCut("我爱你ivy", 10));
    }

    @Test
    void rightCut() {
        assertEquals("", FullCharUtil.rightCut("我爱你ivy", 0));
        assertEquals("y", FullCharUtil.rightCut("我爱你ivy", 1));
        assertEquals("vy", FullCharUtil.rightCut("我爱你ivy", 2));
        assertEquals("ivy", FullCharUtil.rightCut("我爱你ivy", 3));
        assertEquals("ivy", FullCharUtil.rightCut("我爱你ivy", 4));
        assertEquals("你ivy", FullCharUtil.rightCut("我爱你ivy", 5));
        assertEquals("你ivy", FullCharUtil.rightCut("我爱你ivy", 6));
        assertEquals("爱你ivy", FullCharUtil.rightCut("我爱你ivy", 7));
        assertEquals("爱你ivy", FullCharUtil.rightCut("我爱你ivy", 8));
        assertEquals("我爱你ivy", FullCharUtil.rightCut("我爱你ivy", 9));
        assertEquals("我爱你ivy", FullCharUtil.rightCut("我爱你ivy", 10));
    }

    @Test
    void countHalf() {
        assertEquals(9, FullCharUtil.countHalf("我爱你ivy"));
    }
}