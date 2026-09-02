package korlibs.math.interpolation

import korlibs.math.geom.Vector2D
import korlibs.math.geom.Vector2F

fun Ratio.interpolate(l: Vector2D, r: Vector2D): Vector2D = Vector2D(interpolate(l.x, r.x), interpolate(l.y, r.y))
fun Ratio.interpolate(l: Vector2F, r: Vector2F): Vector2F = Vector2F(interpolate(l.x, r.x), interpolate(l.y, r.y))
