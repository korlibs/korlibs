package korlibs.math

import kotlin.test.*

class IsAlmostEqualsTest {
    @Test
    fun testIsAlmostEqualsDouble() {
        assertTrue(0.0.isAlmostEquals(0.0))
        assertTrue(1.0.isAlmostEquals(1.000005, epsilon = 0.00001))
        assertTrue(1.0.isAlmostEquals(1.00000001, epsilon = 0.00001))
        assertFalse(1.0.isAlmostEquals(1.0001, epsilon = 0.00001))
    }

    @Test
    fun testIsAlmostEqualsFloat() {
        assertTrue(0f.isAlmostEquals(0f))
        assertTrue(1f.isAlmostEquals(1.000005f, epsilon = 0.00001f))
        assertTrue(1f.isAlmostEquals(1.0000001f, epsilon = 0.00001f))
        assertFalse(1f.isAlmostEquals(1.0001f, epsilon = 0.00001f))
    }

    data class Vector2(val x: Double, val y: Double) : IsAlmostEquals<Vector2> {
        override fun isAlmostEquals(other: Vector2, epsilon: Double): Boolean =
            x.isAlmostEquals(other.x, epsilon) && y.isAlmostEquals(other.y, epsilon)
    }

    data class Vector2F(val x: Float, val y: Float) : IsAlmostEqualsF<Vector2F> {
        override fun isAlmostEquals(other: Vector2F, epsilon: Float): Boolean =
            x.isAlmostEquals(other.x, epsilon) && y.isAlmostEquals(other.y, epsilon)
    }

    @Test
    fun testIsAlmostEqualsVector2() {
        assertTrue(Vector2(0.0, 0.0).isAlmostEquals(Vector2(0.0, 0.0)))
        assertTrue(Vector2(1.0, 1.0).isAlmostEquals(Vector2(1.000005, 1.000005), epsilon = 0.00001))
        assertTrue(Vector2(1.0, 1.0).isAlmostEquals(Vector2(1.00000001, 1.00000001), epsilon = 0.00001))
        assertFalse(Vector2(1.0, 1.0).isAlmostEquals(Vector2(1.0001, 1.0001), epsilon = 0.00001))
    }

    @Test
    fun testIsAlmostEqualsVector2F() {
        assertTrue(Vector2F(0f, 0f).isAlmostEquals(Vector2F(0f, 0f)))
        assertTrue(Vector2F(1f, 1f).isAlmostEquals(Vector2F(1.000005f, 1.000005f), epsilon = 0.00001f))
        assertTrue(Vector2F(1f, 1f).isAlmostEquals(Vector2F(1.0000001f, 1.0000001f), epsilon = 0.00001f))
        assertFalse(Vector2F(1f, 1f).isAlmostEquals(Vector2F(1.0001f, 1.0001f), epsilon = 0.00001f))
    }
}