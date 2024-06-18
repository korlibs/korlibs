package korlibs.math.geom

import korlibs.math.geom.bezier.*
import korlibs.math.geom.shape.*
import korlibs.math.geom.vector.*
import kotlin.math.*

data class Polygon(val points: PointList) : AbstractShape2D() {
    override val lazyVectorPath: VectorPath by lazy { buildVectorPath { polygon(points, close = true) }  }
}

data class Polyline(val points: PointList) : AbstractShape2D() {
    override val lazyVectorPath: VectorPath by lazy { buildVectorPath { polygon(points, close = false) }  }
}

data class RoundRectangle(val rect: Rectangle, val corners: RectCorners) : AbstractShape2D() {
    private fun areaQuarter(radius: Double): Double = Arc.length(radius, Angle.QUARTER)
    private fun areaComplementaryQuarter(radius: Double): Double = (radius * radius) - areaQuarter(radius)
    override val lazyVectorPath: VectorPath by lazy { buildVectorPath { roundRect(this@RoundRectangle) } }

    override val area: Double get() = rect.area - (
        areaComplementaryQuarter(corners.topLeft) +
            areaComplementaryQuarter(corners.topRight) +
            areaComplementaryQuarter(corners.bottomLeft) +
            areaComplementaryQuarter(corners.bottomRight)
        )
}

fun Rectangle.toVectorPath(): VectorPath = buildVectorPath { rect(this@toVectorPath) }
fun Line.toVectorPath(): VectorPath = buildVectorPath { moveTo(a); lineTo(b) }
fun Circle.toVectorPath(): VectorPath = buildVectorPath { circle(this@toVectorPath.center, this@toVectorPath.radius) }
fun Ellipse.toVectorPath(): VectorPath = buildVectorPath { ellipse(this@toVectorPath.center, this@toVectorPath.radius) }
