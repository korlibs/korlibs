package korlibs.math.interpolation

import korlibs.math.geom.*
import kotlin.test.Test
import kotlin.test.assertEquals

class InterpolationTest {
    @Test
    fun test() {
        assertEquals(100, 0.0.toRatio().interpolate(100, 200))
        assertEquals(150, 0.5.toRatio().interpolate(100, 200))
        assertEquals(200, 1.0.toRatio().interpolate(100, 200))

        assertEquals(100L, 0.0.toRatio().interpolate(100L, 200L))
        assertEquals(150L, 0.5.toRatio().interpolate(100L, 200L))
        assertEquals(200L, 1.0.toRatio().interpolate(100L, 200L))

        assertEquals(100f, 0.0.toRatio().interpolate(100f, 200f))
        assertEquals(150f, 0.5.toRatio().interpolate(100f, 200f))
        assertEquals(200f, 1.0.toRatio().interpolate(100f, 200f))

        assertEquals(100.0, 0.0.toRatio().interpolate(100.0, 200.0))
        assertEquals(150.0, 0.5.toRatio().interpolate(100.0, 200.0))
        assertEquals(200.0, 1.0.toRatio().interpolate(100.0, 200.0))
    }

    @Test
    fun testInterpolateSize() {
        val result = 0.5.toRatio().interpolate(Size(100f, 200f), Size(200f, 400f))
        assertEquals(Size(150f, 300f), result)
        assertEquals(Size(100f, 200f), 0.0.toRatio().interpolate(Size(100f, 200f), Size(200f, 400f)))
        assertEquals(Size(200f, 400f), 1.0.toRatio().interpolate(Size(100f, 200f), Size(200f, 400f)))
    }

    @Test
    fun testInterpolateScale() {
        val result = 0.5.toRatio().interpolate(Scale(1f, 2f), Scale(3f, 4f))
        assertEquals(Scale(2f, 3f), result)
        assertEquals(Scale(1f, 2f), 0.0.toRatio().interpolate(Scale(1f, 2f), Scale(3f, 4f)))
        assertEquals(Scale(3f, 4f), 1.0.toRatio().interpolate(Scale(1f, 2f), Scale(3f, 4f)))
    }

    @Test
    fun testInterpolateMatrix() {
        val m1 = Matrix(1f, 0f, 0f, 1f, 0f, 0f)
        val m2 = Matrix(1f, 0f, 0f, 1f, 100f, 200f)
        val result = 0.5.toRatio().interpolate(m1, m2)
        assertEquals(50.0, result.tx.toDouble(), 0.01)
        assertEquals(100.0, result.ty.toDouble(), 0.01)
        assertEquals(m1.tx.toDouble(), 0.0.toRatio().interpolate(m1, m2).tx.toDouble(), 0.01)
        assertEquals(m2.tx.toDouble(), 1.0.toRatio().interpolate(m1, m2).tx.toDouble(), 0.01)
    }

    @Test
    fun testInterpolateMatrixTransform() {
        val t1 = MatrixTransform(x = 0.0, y = 0.0)
        val t2 = MatrixTransform(x = 100.0, y = 200.0)
        val result = 0.5.toRatio().interpolate(t1, t2)
        assertEquals(50.0, result.x, 0.01)
        assertEquals(100.0, result.y, 0.01)
        assertEquals(t1.x, 0.0.toRatio().interpolate(t1, t2).x, 0.01)
        assertEquals(t2.x, 1.0.toRatio().interpolate(t1, t2).x, 0.01)
    }

    @Test
    fun testInterpolateRectangle() {
        val r1 = Rectangle(0f, 0f, 100f, 100f)
        val r2 = Rectangle(100f, 200f, 300f, 400f)
        val result = 0.5.toRatio().interpolate(r1, r2)
        assertEquals(50.0, result.x.toDouble(), 0.01)
        assertEquals(100.0, result.y.toDouble(), 0.01)
        assertEquals(200.0, result.width.toDouble(), 0.01)
        assertEquals(250.0, result.height.toDouble(), 0.01)
        assertEquals(r1, 0.0.toRatio().interpolate(r1, r2))
        assertEquals(r2, 1.0.toRatio().interpolate(r1, r2))
    }

    @Test
    fun testInterpolateInterpolable() {
        // Anchor2D implements Interpolable<Anchor>
        val a1 = Anchor2D(0.0, 0.0)
        val a2 = Anchor2D(1.0, 1.0)
        val result = 0.5.toRatio().interpolate(a1 as Interpolable<Anchor>, a2 as Interpolable<Anchor>)
        assertEquals(0.5, (result as Anchor2D).sx, 0.01)
        assertEquals(0.5, (result as Anchor2D).sy, 0.01)
    }

    @Test
    fun testRatioNiceStr() {
        assertEquals("0", 0.0.toRatio().niceStr)
        assertEquals("0.5", 0.5.toRatio().niceStr)
        assertEquals("1", 1.0.toRatio().niceStr)
    }

    @Test
    fun testRatioNiceStrDecimalPlaces() {
        // zeroSuffix only appends ".0" when the value is an integer
        assertEquals("0.5", 0.5.toRatio().niceStr(2, true))
        assertEquals("0.5", 0.5.toRatio().niceStr(2, false))
        assertEquals("0.333", (1.0 / 3.0).toRatio().niceStr(3, false))
        // 1.0 is integer, so zeroSuffix appends ".0"
        assertEquals("1.0", 1.0.toRatio().niceStr(3, true))
        assertEquals("1", 1.0.toRatio().niceStr(3, false))
    }
}
