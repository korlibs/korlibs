package korlibs.math

fun Double.isAlmostZero(): Boolean = kotlin.math.abs(this) <= 1e-19
fun Float.isAlmostZero(): Boolean = kotlin.math.abs(this) <= 1e-6
