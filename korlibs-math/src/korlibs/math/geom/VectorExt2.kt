package korlibs.math.geom

fun PointList.getPolylineLength(): Double = getPolylineLength(size) { get(it) }
fun List<Point>.getPolylineLength(): Double = getPolylineLength(size) { get(it) }

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
