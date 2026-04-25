package korlibs.math.interpolation

import korlibs.datastructure.*
import korlibs.math.geom.*
import korlibs.number.*

fun Ratio.interpolate(l: Size, r: Size): Size = Size(interpolate(l.width, r.width), interpolate(l.height, r.height))
fun Ratio.interpolate(l: Scale, r: Scale): Scale = Scale(interpolate(l.scaleX, r.scaleX), interpolate(l.scaleY, r.scaleY))
fun Ratio.interpolate(l: Matrix, r: Matrix): Matrix = Matrix.interpolated(l, r, this)
fun Ratio.interpolate(l: MatrixTransform, r: MatrixTransform): MatrixTransform = MatrixTransform.interpolated(l, r, this)
fun Ratio.interpolate(l: Rectangle, r: Rectangle): Rectangle = Rectangle.interpolated(l, r, this)

fun <T> Ratio.interpolate(l: Interpolable<T>, r: Interpolable<T>): T = l.interpolateWith(this, r.fastCastTo<T>())
val Ratio.niceStr: String get() = this.toDouble().niceStr
fun Ratio.niceStr(decimalPlaces: Int, zeroSuffix: Boolean = false): String = this.toDouble().niceStr(decimalPlaces, zeroSuffix)
