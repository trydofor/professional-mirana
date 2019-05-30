package pro.fessional.mirana.id

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

/**
 * @author trydofor
 * @since 2019-05-21
 */
class LightIdTest {

    @Test
    fun testAll() {
        val zero = LightId(0, 0)
        assertEquals(LightId.ZERO, zero)

        val (b, s) = zero
        assertEquals(0, b)
        assertEquals(0, s)
    }

    @Test
    fun testCode() {
        for (i in 1..10000) {
            val seq = Random.nextLong() and LightId.MAX_SEQUENCE
            val id = LightId(1, seq)
            assertTrue(LightIdUtil.valid(id))
            val di = id.toLong()
            val id1 = LightIdUtil.toLightId(di)
            assertEquals(id, id1)
        }
    }

    @Test
    fun testHash() {
        val n1 = LightId(0, -1)
        val n2 = LightId(-1, -2)
        assertEquals(n1, n2)
        assertEquals(n2, n1)
        assertEquals(LightId.NONE, n2)
        assertEquals(LightId.NONE, n1)
        assertEquals(n1, LightId.NONE)
        assertEquals(n2, LightId.NONE)

        assertEquals(n1.hashCode(), n2.hashCode())
        assertEquals(LightId.NONE.hashCode(), n2.hashCode())
    }
}