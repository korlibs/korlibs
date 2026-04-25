package korlibs.math.geom

import korlibs.math.interpolation.*

data class RoundRectangle(val rect: Rectangle, val corners: RectCorners) {
    companion object {
        private fun areaQuarter(radius: Double): Double = Arc_length(radius, Angle.QUARTER)
        private fun areaComplementaryQuarter(radius: Double): Double = (radius * radius) - areaQuarter(radius)
        private fun Arc_length(radius: Double, angle: Angle): Double = PI2 * radius * angle.ratio
    }

    val area: Double get() = rect.area - (
        areaComplementaryQuarter(corners.topLeft) +
            areaComplementaryQuarter(corners.topRight) +
            areaComplementaryQuarter(corners.bottomLeft) +
            areaComplementaryQuarter(corners.bottomRight)
        )
}
