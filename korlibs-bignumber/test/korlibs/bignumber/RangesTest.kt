package korlibs.bignumber

import korlibs.bignumber.ranges.*
import kotlin.test.*

class RangesTest {
    @Test
    fun progressionTest() {
        assertEquals((1.bi..4.bi).toList().size, 4)
        assertEquals((1.bi..4.bi step 2.bi).toList().size, 2)
        assertEquals((1.bi..4.bi step 5.bi).toList().size, 1)
    }

    @Test
    fun containsTest() {
        assertTrue(BigInt.ZERO in BigInt("-99999999999999999999999999999999999999999999999")..BigInt("99999999999999999999999999999999999999999999999"))
        assertTrue(BigNum.ZERO in BigNum("-99999999999999999999999999999999999999999999999")..BigNum("99999999999999999999999999999999999999999999999"))
        assertTrue(BigNum.ZERO in BigNum("-0.000000000000000000000000000000000000000000001")..BigNum("0.000000000000000000000000000000000000000000001"))
    }

    @Test
    fun closedBigNumRangeContracts() {
        val range = ClosedBigNumRange("0.1".bn, "0.2".bn)
        val sameRange = ClosedBigNumRange("0.10".bn, "0.20".bn)
        val emptyA = ClosedBigNumRange("2".bn, "1".bn)
        val emptyB = ClosedBigNumRange("10".bn, "1".bn)

        assertEquals("0.1..0.2", range.toString())
        assertTrue("0.15".bn in range)
        assertFalse("0.3".bn in range)

        assertEquals(range, sameRange)
        assertEquals(range.hashCode(), ClosedBigNumRange("0.1".bn, "0.2".bn).hashCode())

        assertTrue(emptyA.isEmpty())
        assertEquals(emptyA, emptyB)
        assertEquals(-1, emptyA.hashCode())
    }
}
