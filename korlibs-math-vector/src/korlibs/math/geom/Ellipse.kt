package korlibs.math.geom

import korlibs.math.geom.shape.*
import kotlin.math.*

data class Ellipse(override val center: Point, val radius: Size) : SimpleShape2D {
    override val area: Double get() = (PI * radius.width * radius.height)
    override val perimeter: Double get() {
        if (radius.width == radius.height) return (PI * 2.0 * radius.width) // Circle formula
        val (a, b) = radius
        val h = ((a - b) * (a - b)) / ((a + b) * (a + b))
        return (PI * (a + b) * (1 + ((3 * h) / (10 + sqrt(4 - (3 * h))))))
    }

    override fun distance(p: Point): Double {
        val p = p - center
        val scaledPoint = Vector2D(p.x / radius.width, p.y / radius.height)
        val length = scaledPoint.length
        return (length - 1) * min(radius.width, radius.height)
    }

    override fun normalVectorAt(p: Point): Vector2D {
        val pointOnEllipse = p - center
        val (a, b) = radius
        val normal = Vector2D(pointOnEllipse.x / (a * a), pointOnEllipse.y / (b * b))
        return normal.normalized
        //val d = p - center
        //val r2 = radius.toVector() * radius.toVector()
        //return (d / r2).normalized
    }

    override fun projectedPoint(p: Point): Point {
        val angle = Angle.between(center, p)
        return center + Point(radius.width * angle.cosine, radius.height * angle.sine)

        //val k = (radius.width * radius.height) / sqrt()
        //return projectPointOntoEllipse(p, center, radius.toVector())
    }

    override fun containsPoint(p: Point): Boolean {
        if (radius.isEmpty()) return false
        // Check if the point is inside the ellipse using the ellipse equation:
        // (x - centerX)^2 / radiusX^2 + (y - centerY)^2 / radiusY^2 <= 1
        return ((p.x - center.x).pow(2) / radius.width.pow(2)) + ((p.y - center.y).pow(2) / radius.height.pow(2)) <= 1
    }

    override val closed: Boolean get() = true
    override fun getBounds(): Rectangle = Rectangle.fromBounds(center.x - radius.width, center.y - radius.height, center.x + radius.width, center.y + radius.height)

    companion object {
        private fun projectPointOntoEllipse(point: Vector2F, center: Vector2F, radius: Vector2F, tolerance: Double = 1e-6, maxIterations: Int = 100): Vector2F {
            var currentPoint = point
            var i = 0

            while (i < maxIterations) {
                val dx = currentPoint.x - center.x
                val dy = currentPoint.y - center.y
                val rx2 = radius.x * radius.x
                val ry2 = radius.y * radius.y

                val f = Vector2F(
                    (dx * rx2 - dy * dx * dy) / (rx2 * ry2),
                    (dy * ry2 - dx * dy * dx) / (rx2 * ry2)
                )

                val df = Vector2F(
                    (ry2 - 2.0 * dy * dy) / (rx2 * ry2),
                    (rx2 - 2.0 * dx * dx) / (rx2 * ry2)
                )

                val nextPoint = currentPoint - f / df
                val dist = (nextPoint - currentPoint).length

                if (dist < tolerance) return nextPoint

                currentPoint = nextPoint
                i++
            }

            return currentPoint
        }
    }
}
