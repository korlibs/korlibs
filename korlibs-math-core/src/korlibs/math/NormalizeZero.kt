package korlibs.math

//fun Double.normalizeZero(): Double = if (this.isAlmostZero()) 0.0 else this
private val MINUS_ZERO_D = -0.0
private val MINUS_ZERO_F = -0.0f
fun Double.normalizeZero(): Double = if (this == MINUS_ZERO_D) 0.0 else this
fun Float.normalizeZero(): Float = if (this == MINUS_ZERO_F) 0f else this
