package korlibs.math

fun fract(value: Float): Float = value - value.toIntFloor()
fun fract(value: Double): Double = value - value.toIntFloor()
