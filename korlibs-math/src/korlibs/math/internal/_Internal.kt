package korlibs.math.internal

import korlibs.math.*
import kotlin.math.*

@PublishedApi
internal fun floorCeil(v: Double): Double = if (v < 0.0) ceil(v) else floor(v)

// @TODO: Check
internal fun Double.toInt2(): Int = if (this < 0.0) floor(this).toInt() else this.toInt()
internal fun Double.toIntMod(mod: Int): Int = (this umod mod.toDouble()).toInt2()

internal infix fun Int.div2(other: Int): Int = when {
    this < 0 || this % other == 0 -> this / other
    else -> (this / other) - 1
}
