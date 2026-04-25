package korlibs.math

import kotlin.test.*

class ClampTest {
    @Test
    fun testClampDouble() {
        assertEquals(0.0, (-1.0).clamp(0.0, 10.0))
        assertEquals(0.0, 0.0.clamp(0.0, 10.0))
        assertEquals(5.0, 5.0.clamp(0.0, 10.0))
        assertEquals(10.0, 10.0.clamp(0.0, 10.0))
        assertEquals(10.0, 11.0.clamp(0.0, 10.0))
    }

    @Test
    fun testClampInt() {
        assertEquals(0, (-1).clamp(0, 10))
        assertEquals(0, 0.clamp(0, 10))
        assertEquals(5, 5.clamp(0, 10))
        assertEquals(10, 10.clamp(0, 10))
        assertEquals(10, 11.clamp(0, 10))
    }

    @Test
    fun testClampLong() {
        assertEquals(0L, (-1L).clamp(0L, 10L))
        assertEquals(0L, 0L.clamp(0L, 10L))
        assertEquals(5L, 5L.clamp(0L, 10L))
        assertEquals(10L, 10L.clamp(0L, 10L))
        assertEquals(10L, 11L.clamp(0L, 10L))
    }

    @Test
    fun testClampFloat() {
        assertEquals(0f, (-1f).clamp(0f, 10f))
        assertEquals(0f, 0f.clamp(0f, 10f))
        assertEquals(5f, 5f.clamp(0f, 10f))
        assertEquals(10f, 10f.clamp(0f, 10f))
        assertEquals(10f, 11f.clamp(0f, 10f))
    }

    @Test
    fun testClamp01() {
        assertEquals(0.0, (-1.0).clamp01())
        assertEquals(0.0, 0.0.clamp01())
        assertEquals(0.5, 0.5.clamp01())
        assertEquals(1.0, 1.0.clamp01())
        assertEquals(1.0, 2.0.clamp01())
    }

    @Test
    fun testClamp01f() {
        assertEquals(0f, (-1f).clamp01())
        assertEquals(0f, 0f.clamp01())
        assertEquals(0.5f, 0.5f.clamp01())
        assertEquals(1f, 1f.clamp01())
        assertEquals(1f, 2f.clamp01())
    }

    @Test
    fun testToIntClamp() {
        assertEquals(0, (-1L).toIntClamp(0, 10))
        assertEquals(0, 0L.toIntClamp(0, 10))
        assertEquals(5, 5L.toIntClamp(0, 10))
        assertEquals(10, 10L.toIntClamp(0, 10))
        assertEquals(10, 11L.toIntClamp(0, 10))
    }

    @Test
    fun testToUintClamp() {
        assertEquals(0, (-1L).toUintClamp(0, 10))
        assertEquals(0, 0L.toUintClamp(0, 10))
        assertEquals(5, 5L.toUintClamp(0, 10))
        assertEquals(10, 10L.toUintClamp(0, 10))
        assertEquals(10, 11L.toUintClamp(0, 10))
    }

    @Test
    fun testClampUByte() {
        assertEquals(0, (-1).clampUByte())
        assertEquals(0, 0.clampUByte())
        assertEquals(5, 5.clampUByte())
        assertEquals(255, 255.clampUByte())
        assertEquals(255, 256.clampUByte())
    }

    @Test
    fun testClampUShort() {
        assertEquals(0, (-1).clampUShort())
        assertEquals(0, 0.clampUShort())
        assertEquals(5, 5.clampUShort())
        assertEquals(65535, 65535.clampUShort())
        assertEquals(65535, 65536.clampUShort())
    }

    @Test
    fun testToShortClamped() {
        assertEquals(Short.MIN_VALUE, Int.MIN_VALUE.toShortClamped())
        assertEquals(Short.MAX_VALUE, Int.MAX_VALUE.toShortClamped())
        assertEquals(0.toShort(), 0.toShortClamped())
    }

    @Test
    fun testToByteClamped() {
        assertEquals(Byte.MIN_VALUE, Int.MIN_VALUE.toByteClamped())
        assertEquals(Byte.MAX_VALUE, Int.MAX_VALUE.toByteClamped())
        assertEquals(0.toByte(), 0.toByteClamped())
    }
}