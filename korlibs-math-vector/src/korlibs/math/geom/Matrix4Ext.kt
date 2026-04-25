package korlibs.math.geom

fun Matrix4.Companion.ortho(rect: Rectangle, near: Float = 0f, far: Float = 1f): Matrix4 = Matrix4.ortho(rect.left, rect.right, rect.bottom, rect.top, near.toDouble(), far.toDouble())
fun Matrix4.Companion.ortho(rect: Rectangle, near: Double = 0.0, far: Double = 1.0): Matrix4 = ortho(rect, near.toFloat(), far.toFloat())
fun Matrix4.Companion.ortho(rect: Rectangle, near: Int = 0, far: Int = 1): Matrix4 = ortho(rect, near.toFloat(), far.toFloat())

fun Matrix4.Companion.frustum(rect: Rectangle, zNear: Float = 0f, zFar: Float = 1f): Matrix4 = Matrix4.frustum(rect.left, rect.right, rect.bottom, rect.top, zNear.toDouble(), zFar.toDouble())
fun Matrix4.Companion.frustum(rect: Rectangle, zNear: Double = 0.0, zFar: Double = 1.0): Matrix4 = frustum(rect, zNear.toFloat(), zFar.toFloat())
fun Matrix4.Companion.frustum(rect: Rectangle, zNear: Int = 0, zFar: Int = 1): Matrix4 = frustum(rect, zNear.toFloat(), zFar.toFloat())
