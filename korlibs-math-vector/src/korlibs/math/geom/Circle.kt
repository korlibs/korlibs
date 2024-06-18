package korlibs.math.geom

import korlibs.math.geom.shape.*
import kotlin.math.*

data class Circle(override val center: Point, val radius: Double) : SimpleShape2D {
    companion object {
        inline operator fun invoke(center: Point, radius: Number) = Circle(center, radius.toDouble())
        inline operator fun invoke(x: Number, y: Number, radius: Number) = Circle(Point(x.toDouble(), y.toDouble()), radius.toDouble())
    }

    override val closed: Boolean get() = true

    override val area: Double get() = (PI * radius * radius)
    override val perimeter: Double get() = (PI * 2.0 * radius)
    override fun distance(p: Point): Double = (p - center).length - radius
    override fun normalVectorAt(p: Point): Vector2D = (p - center).normalized

    val radiusSquared: Double get() = radius * radius

    fun distanceToCenterSquared(p: Point): Double = Point.distanceSquared(p, center)
    // @TODO: Check if inside the circle
    fun distanceClosestSquared(p: Point): Double = distanceToCenterSquared(p) - radiusSquared
    // @TODO: Check if inside the circle
    fun distanceFarthestSquared(p: Point): Double = distanceToCenterSquared(p) + radiusSquared
    override fun projectedPoint(p: Point): Point = Point.polar(center, Angle.between(center, p), radius)
    override fun containsPoint(p: Point): Boolean = (p - center).length <= radius
    override fun getBounds(): Rectangle = Rectangle.fromBounds(center.x - radius, center.y - radius, center.x + radius, center.y + radius,)
}
