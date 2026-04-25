package korlibs.math

import kotlin.test.*

class MathTest {
    @Test
    fun testIntNumberOfDigits() {
        assertEquals(1, 0.numberOfDigits())
        assertEquals(1, 7.numberOfDigits())
        assertEquals(2, 42.numberOfDigits())
        assertEquals(3, 123.numberOfDigits())
        assertEquals(3, 999.numberOfDigits())
        assertEquals(4, 1000.numberOfDigits())
        assertEquals(4, 1001.numberOfDigits())
        assertEquals(6, 424242.numberOfDigits())
        assertEquals(10, Int.MAX_VALUE.numberOfDigits())

        assertEquals(1, 15.numberOfDigits(16))
        assertEquals(2, 42.numberOfDigits(16))
        assertEquals(2, 255.numberOfDigits(16))
        assertEquals(3, 256.numberOfDigits(16))
        assertEquals(8, Int.MAX_VALUE.numberOfDigits(16))

        assertEquals(4, 15.numberOfDigits(2))
        assertEquals(5, 16.numberOfDigits(2))
        assertEquals(31, Int.MAX_VALUE.numberOfDigits(2)) // 31 because fist bit is sign bit and it is 0

        //Negative
        assertEquals(1, (-1).numberOfDigits())
        assertEquals(1, (-7).numberOfDigits())
        assertEquals(2, (-42).numberOfDigits())
        assertEquals(3, (-123).numberOfDigits())
        assertEquals(3, (-999).numberOfDigits())
        assertEquals(4, (-1000).numberOfDigits())
        assertEquals(4, (-1001).numberOfDigits())
        assertEquals(6, (-424242).numberOfDigits())
        assertEquals(10, Int.MIN_VALUE.numberOfDigits())

        assertEquals(1, (-15).numberOfDigits(16))
        assertEquals(2, (-42).numberOfDigits(16))
        assertEquals(2, (-255).numberOfDigits(16))
        assertEquals(3, (-256).numberOfDigits(16))
        assertEquals(8, Int.MIN_VALUE.numberOfDigits(16))

        assertEquals(4, (-15).numberOfDigits(2))
        assertEquals(5, (-16).numberOfDigits(2))
        assertEquals(32, Int.MIN_VALUE.numberOfDigits(2)) // 32 because fist bit is sign bit and it is 1
    }

    @Test
    fun testLongNumberOfDigits() {
        assertEquals(1, 0L.numberOfDigits())
        assertEquals(1, 7L.numberOfDigits())
        assertEquals(2, 42L.numberOfDigits())
        assertEquals(3, 123L.numberOfDigits())
        assertEquals(3, 999L.numberOfDigits())
        assertEquals(4, 1000L.numberOfDigits())
        assertEquals(4, 1001L.numberOfDigits())
        assertEquals(6, 424242L.numberOfDigits())
        assertEquals(19, Long.MAX_VALUE.numberOfDigits())

        assertEquals(1, 15L.numberOfDigits(16))
        assertEquals(2, 42L.numberOfDigits(16))
        assertEquals(2, 255L.numberOfDigits(16))
        assertEquals(3, 256L.numberOfDigits(16))
        assertEquals(16, Long.MAX_VALUE.numberOfDigits(16))

        assertEquals(4, 15L.numberOfDigits(2))
        assertEquals(5, 16L.numberOfDigits(2))
        assertEquals(63, Long.MAX_VALUE.numberOfDigits(2)) // 63 because fist bit is sign bit and it is 0

        //Negative
        assertEquals(1, (-1L).numberOfDigits())
        assertEquals(1, (-7L).numberOfDigits())
        assertEquals(2, (-42L).numberOfDigits())
        assertEquals(3, (-123L).numberOfDigits())
        assertEquals(3, (-999L).numberOfDigits())
        assertEquals(4, (-1000L).numberOfDigits())
        assertEquals(4, (-1001L).numberOfDigits())
        assertEquals(6, (-424242L).numberOfDigits())
        assertEquals(19, Long.MIN_VALUE.numberOfDigits())

        assertEquals(1, (-15L).numberOfDigits(16))
        assertEquals(2, (-42L).numberOfDigits(16))
        assertEquals(2, (-255L).numberOfDigits(16))
        assertEquals(3, (-256L).numberOfDigits(16))
        assertEquals(16, Long.MIN_VALUE.numberOfDigits(16))

        assertEquals(4, (-15L).numberOfDigits(2))
        assertEquals(5, (-16L).numberOfDigits(2))
        assertEquals(64, Long.MIN_VALUE.numberOfDigits(2)) // 64 because fist bit is sign bit and it is 1
    }

    @Test
    fun testLog2() {
        assertEquals(0, log2(1))
        assertEquals(1, log2(2))
        assertEquals(1, log2(3))
        assertEquals(2, log2(4))
        assertEquals(2, log2(7))
        assertEquals(3, log2(8))
        assertEquals(4, log2(16))
        assertEquals(4, log2(31))
        assertEquals(5, log2(32))
        assertEquals(10, log2(1024))

        assertEquals(Int.MIN_VALUE, log2(0))
        assertEquals(0, log2(-1))
    }

    @Test
    fun testLog10() {
        assertEquals(0, log10(1))
        assertEquals(0, log10(9))
        assertEquals(1, log10(10))
        assertEquals(1, log10(99))
        assertEquals(2, log10(100))
        assertEquals(2, log10(999))
        assertEquals(3, log10(1000))

        assertEquals(Int.MIN_VALUE, log10(0))
        assertEquals(0, log10(-1))
    }

    @Test
    fun testAlmostEquals() {
        // Float version
        assertTrue(almostEquals(0f, 0f))
        assertTrue(almostEquals(1f, 1f))
        assertFalse(almostEquals(1f, 1.001f))
        assertFalse(almostEquals(1f, 0.998f))
        
        // Custom epsilon
        assertTrue(almostEquals(1f, 1.01f, 0.1f))
        assertFalse(almostEquals(1f, 1.2f, 0.1f))
        
        // Double version
        assertTrue(almostEquals(0.0, 0.0))
        assertTrue(almostEquals(1.0, 1.0))
        assertTrue(almostEquals(1.0, 1.0000000001))
        assertTrue(almostEquals(1.0, 0.9999999999))
        assertFalse(almostEquals(1.0, 1.001))
        assertFalse(almostEquals(1.0, 0.998))
        
        // Custom epsilon
        assertTrue(almostEquals(1.0, 1.01, 0.1))
        assertFalse(almostEquals(1.0, 1.2, 0.1))
    }

    @Test
    fun testAlmostZero() {
        // Float version
        assertTrue(almostZero(0f))
        assertTrue(almostZero(0.0000000f))
        assertTrue(almostZero(0.00000001f))
        assertTrue(almostZero(-0.00000001f))
        assertFalse(almostZero(0.001f))
        assertFalse(almostZero(-0.001f))
        
        // Custom epsilon
        assertTrue(almostZero(0.05f, 0.1f))
        assertFalse(almostZero(0.15f, 0.1f))
        
        // Double version
        assertTrue(almostZero(0.0))
        assertTrue(almostZero(0.0000000))
        assertTrue(almostZero(0.0000000001))
        assertTrue(almostZero(-0.0000000001))
        assertFalse(almostZero(0.001))
        assertFalse(almostZero(-0.001))
        
        // Custom epsilon
        assertTrue(almostZero(0.05, 0.1))
        assertFalse(almostZero(0.15, 0.1))
    }
}