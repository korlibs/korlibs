package korlibs.math.geom

typealias RectangleI = RectangleInt

//@KormaValueApi
data class RectangleInt(
    val x: Int, val y: Int,
    val width: Int, val height: Int,
) {
    constructor() : this(0, 0, 0, 0)

    val position: Vector2I get() = Vector2I(x, y)

    val area: Int get() = width * height
    val isEmpty: Boolean get() = width == 0 && height == 0
    val isNotEmpty: Boolean get() = !isEmpty

    val left: Int get() = x
    val top: Int get() = y
    val right: Int get() = x + width
    val bottom: Int get() = y + height

    val topLeft: Vector2I get() = Vector2I(left, top)
    val topRight: Vector2I get() = Vector2I(right, top)
    val bottomLeft: Vector2I get() = Vector2I(left, bottom)
    val bottomRight: Vector2I get() = Vector2I(right, bottom)

    val centerX: Int get() = ((right + left) * 0.5f).toInt()
    val centerY: Int get() = ((bottom + top) * 0.5f).toInt()
    val center: Vector2I get() = Vector2I(centerX, centerY)

    operator fun times(scale: Double): RectangleInt = RectangleInt(
        (x * scale).toInt(), (y * scale).toInt(),
        (width * scale).toInt(), (height * scale).toInt()
    )
    operator fun times(scale: Float): RectangleInt = this * scale.toDouble()
    operator fun times(scale: Int): RectangleInt = this * scale.toDouble()

    operator fun div(scale: Float): RectangleInt = RectangleInt(
        (x / scale).toInt(), (y / scale).toInt(),
        (width / scale).toInt(), (height / scale).toInt()
    )

    operator fun div(scale: Double): RectangleInt = this / scale.toFloat()
    operator fun div(scale: Int): RectangleInt = this / scale.toFloat()

    operator fun contains(that: Point): Boolean = contains(that.x, that.y)
    operator fun contains(that: Vector2I): Boolean = contains(that.x, that.y)
    fun contains(x: Float, y: Float): Boolean = (x >= left && x < right) && (y >= top && y < bottom)
    fun contains(x: Double, y: Double): Boolean = contains(x.toFloat(), y.toFloat())
    fun contains(x: Int, y: Int): Boolean = contains(x.toFloat(), y.toFloat())

    fun sliceWithBounds(left: Int, top: Int, right: Int, bottom: Int, clamped: Boolean = true): RectangleInt {
        val left = if (!clamped) left else left.coerceIn(0, this.width)
        val right = if (!clamped) right else right.coerceIn(0, this.width)
        val top = if (!clamped) top else top.coerceIn(0, this.height)
        val bottom = if (!clamped) bottom else bottom.coerceIn(0, this.height)
        return fromBounds(this.x + left, this.y + top, this.x + right, this.y + bottom)
    }

    fun sliceWithSize(x: Int, y: Int, width: Int, height: Int, clamped: Boolean = true): RectangleInt =
        sliceWithBounds(x, y, x + width, y + height, clamped)

    override fun toString(): String = "Rectangle(x=${x}, y=${y}, width=${width}, height=${height})"

    companion object {
        fun union(a: RectangleInt, b: RectangleInt): RectangleInt = fromBounds(
            kotlin.math.min(a.left, b.left),
            kotlin.math.min(a.top, b.top),
            kotlin.math.max(a.right, b.right),
            kotlin.math.max(a.bottom, b.bottom)
        )

        fun fromBounds(topLeft: Vector2I, bottomRight: Vector2I): RectangleInt {
            val size = (bottomRight - topLeft)
            return RectangleInt(topLeft.x, topLeft.y, size.x, size.y)
        }
        fun fromBounds(left: Int, top: Int, right: Int, bottom: Int): RectangleInt = fromBounds(Vector2I(left, top), Vector2I(right, bottom))
    }
}
