package korlibs.math.geom

import korlibs.math.interpolation.*

fun Ratio.interpolateAngle(l: Angle, r: Angle, minimizeAngle: Boolean): Angle = _interpolateAngleAny(this, l, r, minimizeAngle)
fun Ratio.interpolateAngle(l: Angle, r: Angle): Angle = interpolateAngle(l, r, minimizeAngle = true)
fun Ratio.interpolateAngleNormalized(l: Angle, r: Angle): Angle = interpolateAngle(l, r, minimizeAngle = true)
fun Ratio.interpolateAngleDenormalized(l: Angle, r: Angle): Angle = interpolateAngle(l, r, minimizeAngle = false)

private fun _interpolateAngleAny(ratio: Ratio, l: Angle, r: Angle, minimizeAngle: Boolean = true): Angle {
    if (!minimizeAngle) return Angle.fromRatio(ratio.interpolate(l.ratio, r.ratio))
    val ln = l.normalized
    val rn = r.normalized
    return when {
        (rn - ln).absoluteValue <= Angle.HALF -> Angle.fromRadians(ratio.interpolate(ln.radians, rn.radians))
        ln < rn -> Angle.fromRadians(ratio.interpolate((ln + Angle.FULL).radians, rn.radians)).normalized
        else -> Angle.fromRadians(ratio.interpolate(ln.radians, (rn + Angle.FULL).radians)).normalized
    }
}
