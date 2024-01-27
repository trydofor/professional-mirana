package pro.fessional.mirana.anti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2024-01-26
 */
class GTest {

    @Test
    void globalTest() {
        G.globalPut("1", "attr1");
        Assertions.assertEquals("attr1", G.globalGet("1"));
        G.globalDel("1");
        Assertions.assertNull(G.globalGet("1"));
    }

    @Test
    void threadTest() {
        G.threadPut("1", "attr1");
        Assertions.assertEquals("attr1", G.threadGet("1"));
        G.threadDel("1");
        Assertions.assertNull(G.threadGet("1"));
    }
}