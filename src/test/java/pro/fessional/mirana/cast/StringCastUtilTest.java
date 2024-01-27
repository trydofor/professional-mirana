package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author trydofor
 * @since 2024-01-27
 */
class StringCastUtilTest {

    @Test
    void asTrue() {
        assertTrue(StringCastUtil.asTrue("True"));
        assertTrue(StringCastUtil.asTrue("T"));
        assertTrue(StringCastUtil.asTrue("Yes"));
        assertTrue(StringCastUtil.asTrue("Y"));
        assertFalse(StringCastUtil.asTrue(""));
        assertFalse(StringCastUtil.asTrue(null));
    }

    @Test
    void asFalse() {
        assertTrue(StringCastUtil.asFalse("false"));
        assertTrue(StringCastUtil.asFalse("F"));
        assertTrue(StringCastUtil.asFalse("No"));
        assertTrue(StringCastUtil.asFalse("N"));
        assertTrue(StringCastUtil.asFalse(""));
        assertTrue(StringCastUtil.asFalse(null));
    }

    @Test
    void asLong() {
        assertEquals(1L, StringCastUtil.asLong("1", 0));
        assertEquals(0L, StringCastUtil.asLong("", 0));
        assertEquals(0L, StringCastUtil.asLong(null, 0));
    }

    @Test
    void asInt() {
        assertEquals(1, StringCastUtil.asInt("1", 0));
        assertEquals(0, StringCastUtil.asInt("", 0));
        assertEquals(0, StringCastUtil.asInt(null, 0));
    }

    @Test
    void asFloat() {
        assertEquals(1F, StringCastUtil.asFloat("1F", 0F));
        assertEquals(0F, StringCastUtil.asFloat("", 0F));
        assertEquals(0F, StringCastUtil.asFloat(null, 0F));
    }

    @Test
    void asDouble() {
        assertEquals(1D, StringCastUtil.asDouble("1D", 0D));
        assertEquals(0D, StringCastUtil.asDouble("", 0D));
        assertEquals(0D, StringCastUtil.asDouble(null, 0D));
    }

    @Test
    void asDecimal() {
        assertEquals(BigDecimal.ONE, StringCastUtil.asDecimal("1", BigDecimal.ZERO));
        assertEquals(BigDecimal.ZERO, StringCastUtil.asDecimal("", BigDecimal.ZERO));
        assertEquals(BigDecimal.ZERO, StringCastUtil.asDecimal(null, BigDecimal.ZERO));
    }

    @Test
    void string() {
        assertEquals("0", StringCastUtil.string(BigDecimal.ZERO));
        assertEquals("0.10", StringCastUtil.string(new BigDecimal("0.10")));
        BigDecimal vd = null;
        assertEquals("0", StringCastUtil.string(vd, "0"));
        String vo = null;
        assertEquals("0", StringCastUtil.string(vo, "0"));
    }
}