package korlibs.time.internal

internal infix fun Int.umod(other: Int): Int {
    val rm = this % other
    val remainder = if (rm == -0) 0 else rm
    return when {
        remainder < 0 -> remainder + other
        else -> remainder
    }
}
internal infix fun Double.umod(other: Double): Double {
    val rm = this % other
    val remainder = if (rm == -0.0) 0.0 else rm
    return when {
        remainder < 0.0 -> remainder + other
        else -> remainder
    }
}
