package korlibs.math.geom

import kotlin.math.*

fun Matrix.scaled(scale: Scale): Matrix = scaled(scale.scaleX, scale.scaleY)
fun Matrix.prescaled(scale: Scale): Matrix = prescaled(scale.scaleX, scale.scaleY)

val MatrixTransform.scale: Scale get() = Scale(scaleX, scaleY)

@Suppress("DuplicatedCode")
fun Matrix.transformRectangle(rectangle: Rectangle, delta: Boolean = false): Rectangle {
    val a = this.a
    val b = this.b
    val c = this.c
    val d = this.d
    val tx = if (delta) 0.0 else this.tx
    val ty = if (delta) 0.0 else this.ty

    val x = rectangle.x
    val y = rectangle.y
    val xMax = x + rectangle.width
    val yMax = y + rectangle.height

    var x0 = a * x + c * y + tx
    var y0 = b * x + d * y + ty
    var x1 = a * xMax + c * y + tx
    var y1 = b * xMax + d * y + ty
    var x2 = a * xMax + c * yMax + tx
    var y2 = b * xMax + d * yMax + ty
    var x3 = a * x + c * yMax + tx
    var y3 = b * x + d * yMax + ty

    var tmp = 0.0

    if (x0 > x1) {
        tmp = x0
        x0 = x1
        x1 = tmp
    }
    if (x2 > x3) {
        tmp = x2
        x2 = x3
        x3 = tmp
    }

    val rx = floor(if (x0 < x2) x0 else x2)
    val rw = ceil((if (x1 > x3) x1 else x3) - rectangle.x)

    if (y0 > y1) {
        tmp = y0
        y0 = y1
        y1 = tmp
    }
    if (y2 > y3) {
        tmp = y2
        y2 = y3
        y3 = tmp
    }

    val ry = floor(if (y0 < y2) y0 else y2)
    val rh = ceil((if (y1 > y3) y1 else y3) - rectangle.y)

    return Rectangle(rx, ry, rw, rh)
}
