package korlibs.math.geom

import korlibs.math.interpolation.*

inline fun Vector2F.deltaTransformed(m: Matrix): Vector2F = m.deltaTransform(this)
inline fun Vector2F.transformed(m: Matrix): Vector2F = m.transform(this)
fun Vector2F.transformX(m: Matrix): Float = m.transform(this).x
fun Vector2F.transformY(m: Matrix): Float = m.transform(this).y
inline fun Vector2F.transformedNullable(m: Matrix?): Vector2F = if (m != null && m.isNotNIL) m.transform(this) else this
fun Vector2F.transformNullableX(m: Matrix?): Float = if (m != null && m.isNotNIL) m.transform(this).x else x
fun Vector2F.transformNullableY(m: Matrix?): Float = if (m != null && m.isNotNIL) m.transform(this).y else y

inline fun Vector2D.deltaTransformed(m: Matrix): Vector2D = m.deltaTransform(this)
inline fun Vector2D.transformed(m: Matrix): Vector2D = m.transform(this)
fun Vector2D.transformX(m: Matrix): Double = m.transform(this).x
fun Vector2D.transformY(m: Matrix): Double = m.transform(this).y
inline fun Vector2D.transformedNullable(m: Matrix?): Vector2D = if (m != null && m.isNotNIL) m.transform(this) else this
fun Vector2D.transformNullableX(m: Matrix?): Double = if (m != null && m.isNotNIL) m.transform(this).x else x
fun Vector2D.transformNullableY(m: Matrix?): Double = if (m != null && m.isNotNIL) m.transform(this).y else y


fun PointList.getPolylineLength(): Double = getPolylineLength(size) { get(it) }
fun List<Point>.getPolylineLength(): Double = getPolylineLength(size) { get(it) }

fun List<Point>.bounds(): Rectangle = BoundsBuilder(size) { this + get(it) }.bounds
fun Iterable<Point>.bounds(): Rectangle {
    var bb = BoundsBuilder()
    for (p in this) bb += p
    return bb.bounds
}

internal inline fun getPolylineLength(size: Int, crossinline get: (n: Int) -> Point): Double {
    var out = 0.0
    var prev = Point.ZERO
    for (n in 0 until size) {
        val p = get(n)
        if (n > 0) out += Point.distance(prev, p)
        prev = p
    }
    return out
}

//inline operator fun Vector2F.plus(that: Size): Vector2F = Vector2F(x + that.width, y + that.height)
//inline operator fun Vector2F.minus(that: Size): Vector2F = Vector2F(x - that.width, y - that.height)
//inline operator fun Vector2F.times(that: Size): Vector2F = Vector2F(x * that.width, y * that.height)
//inline operator fun Vector2F.times(that: Scale): Vector2F = Vector2F(x * that.scaleX, y * that.scaleY)
//inline operator fun Vector2F.div(that: Size): Vector2F = Vector2F(x / that.width, y / that.height)
//inline operator fun Vector2F.rem(that: Size): Vector2F = Vector2F(x % that.width, y % that.height)

@Deprecated("", ReplaceWith("ratio.interpolate(this, other)", "korlibs.math.interpolation.interpolate"))
fun Vector2F.interpolateWith(ratio: Ratio, other: Vector2F): Vector2F = ratio.interpolate(this, other)

// inline operator fun Vector2D.plus(that: Size): Vector2D = Vector2D(x + that.width, y + that.height)
// inline operator fun Vector2D.minus(that: Size): Vector2D = Vector2D(x - that.width, y - that.height)
// inline operator fun Vector2D.times(that: Size): Vector2D = Vector2D(x * that.width, y * that.height)
// inline operator fun Vector2D.times(that: Scale): Vector2D = Vector2D(x * that.scaleX, y * that.scaleY)
// inline operator fun Vector2D.div(that: Size): Vector2D = Vector2D(x / that.width, y / that.height)
// inline operator fun Vector2D.rem(that: Size): Vector2D = Vector2D(x % that.width, y % that.height)

@Deprecated("", ReplaceWith("ratio.interpolate(this, other)", "korlibs.math.interpolation.interpolate"))
fun Vector2D.interpolateWith(ratio: Ratio, other: Vector2D): Vector2D = ratio.interpolate(this, other)
