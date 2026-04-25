package korlibs.math.geom.shape

import korlibs.math.geom.*

interface SimpleShape2D {
    val closed: Boolean
    val area: Double
    val perimeter: Double
    val center: Point
    fun distance(p: Point): Double = projectedPoint(p).distanceTo(p)
    fun normalVectorAt(p: Point): Vector2D = (p - projectedPoint(p)).normalized
    fun projectedPoint(p: Point): Point
    fun containsPoint(p: Point): Boolean
    fun getBounds(): Rectangle
}