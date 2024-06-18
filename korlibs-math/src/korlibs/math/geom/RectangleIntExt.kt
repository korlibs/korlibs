package korlibs.math.geom

val RectangleInt.float: Rectangle get() = Rectangle(x, y, width, height)
val RectangleInt.size: SizeInt get() = SizeInt(width, height)
fun RectangleInt.toFloat(): Rectangle = Rectangle(position.toDouble(), size.toDouble())
operator fun RectangleInt.Companion.invoke(position: PointInt, size: SizeInt): RectangleInt = RectangleInt(position.x, position.y, size.width, size.height)
fun RectangleInt.expanded(border: MarginInt): RectangleInt =
    RectangleInt.fromBounds(left - border.left, top - border.top, right + border.right, bottom + border.bottom)
