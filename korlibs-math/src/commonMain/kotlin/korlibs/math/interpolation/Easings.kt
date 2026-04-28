@file:Suppress("PackageDirectoryMismatch")

package korlibs.math.interpolation

import korlibs.math.*
import kotlin.math.*

private const val BOUNCE_FACTOR = 1.70158f
private const val HALF_PI = PI.toFloat() / 2f

fun Easing.Companion.cubic(x1: Float, y1: Float, x2: Float, y2: Float, name: String? = null): Easing = EasingCubic(x1, y1, x2, y2, name)
fun Easing.Companion.cubic(x1: Double, y1: Double, x2: Double, y2: Double, name: String? = null): Easing = EasingCubic(x1, y1, x2, y2, name)

private val _ALL_LIST: List<Easings> by lazy(LazyThreadSafetyMode.PUBLICATION) { Easings.entries }
private val _ALL: Map<String, Easings> by lazy(LazyThreadSafetyMode.PUBLICATION) { _ALL_LIST.associateBy { it.name } }

val Easing.Companion.ALL_LIST: List<Easing> get() = _ALL_LIST

/**
 * Retrieves a mapping of all standard easings defined directly in [Easing], for example "SMOOTH" -> Easing.SMOOTH.
 */
val Easing.Companion.ALL: Map<String, Easing> get() = _ALL

// Author's note:
// 1. Make sure new standard easings are added both here and in the Easings enum class
// 2. Make sure the name is the same, otherwise [ALL] will return confusing results

val Easing.Companion.EASE_IN_ELASTIC: Easing get() = Easings.EASE_IN_ELASTIC
val Easing.Companion.EASE_OUT_ELASTIC: Easing get() = Easings.EASE_OUT_ELASTIC
val Easing.Companion.EASE_OUT_BOUNCE: Easing get() = Easings.EASE_OUT_BOUNCE
val Easing.Companion.EASE: Easing get() = Easings.EASE
val Easing.Companion.EASE_IN: Easing get() = Easings.EASE_IN
val Easing.Companion.EASE_OUT: Easing get() = Easings.EASE_OUT
val Easing.Companion.EASE_IN_OUT: Easing get() = Easings.EASE_IN_OUT
val Easing.Companion.EASE_IN_OLD: Easing get() = Easings.EASE_IN_OLD
val Easing.Companion.EASE_OUT_OLD: Easing get() = Easings.EASE_OUT_OLD
val Easing.Companion.EASE_IN_OUT_OLD: Easing get() = Easings.EASE_IN_OUT_OLD
val Easing.Companion.EASE_OUT_IN_OLD: Easing get() = Easings.EASE_OUT_IN_OLD
val Easing.Companion.EASE_IN_BACK: Easing get() = Easings.EASE_IN_BACK
val Easing.Companion.EASE_OUT_BACK: Easing get() = Easings.EASE_OUT_BACK
val Easing.Companion.EASE_IN_OUT_BACK: Easing get() = Easings.EASE_IN_OUT_BACK
val Easing.Companion.EASE_OUT_IN_BACK: Easing get() = Easings.EASE_OUT_IN_BACK
val Easing.Companion.EASE_IN_OUT_ELASTIC: Easing get() = Easings.EASE_IN_OUT_ELASTIC
val Easing.Companion.EASE_OUT_IN_ELASTIC: Easing get() = Easings.EASE_OUT_IN_ELASTIC
val Easing.Companion.EASE_IN_BOUNCE: Easing get() = Easings.EASE_IN_BOUNCE
val Easing.Companion.EASE_IN_OUT_BOUNCE: Easing get() = Easings.EASE_IN_OUT_BOUNCE
val Easing.Companion.EASE_OUT_IN_BOUNCE: Easing get() = Easings.EASE_OUT_IN_BOUNCE
val Easing.Companion.EASE_IN_QUAD: Easing get() = Easings.EASE_IN_QUAD
val Easing.Companion.EASE_OUT_QUAD: Easing get() = Easings.EASE_OUT_QUAD
val Easing.Companion.EASE_IN_OUT_QUAD: Easing get() = Easings.EASE_IN_OUT_QUAD
val Easing.Companion.EASE_SINE: Easing get() = Easings.EASE_SINE
val Easing.Companion.EASE_CLAMP_START: Easing get() = Easings.EASE_CLAMP_START
val Easing.Companion.EASE_CLAMP_END: Easing get() = Easings.EASE_CLAMP_END
val Easing.Companion.EASE_CLAMP_MIDDLE: Easing get() = Easings.EASE_CLAMP_MIDDLE

private enum class Easings : Easing {
    SMOOTH {
        override fun invoke(it: Float): Float = it * it * (3 - 2 * it)
    },
    EASE_IN_ELASTIC {
        override fun invoke(it: Float): Float =
            if (it == 0f || it == 1f) {
                it
            } else {
                val p = 0.3f
                val s = p / 4.0f
                val inv = it - 1
                -1f * 2f.pow(10f * inv) * sin((inv - s) * (2f * PI.toFloat()) / p)
            }
    },
    EASE_OUT_ELASTIC {
        override fun invoke(it: Float): Float =
            if (it == 0f || it == 1f) {
                it
            } else {
                val p = 0.3f
                val s = p / 4.0f
                2.0f.pow(-10.0f * it) * sin((it - s) * (2.0f * PI.toFloat()) / p) + 1
            }
    },
    EASE_OUT_BOUNCE {
        override fun invoke(it: Float): Float {
            val s = 7.5625f
            val p = 2.75f
            return when {
                it < 1f / p -> s * it.pow(2f)
                it < 2f / p -> s * (it - 1.5f / p).pow(2.0f) + 0.75f
                it < 2.5f / p -> s * (it - 2.25f / p).pow(2.0f) + 0.9375f
                else -> s * (it - 2.625f / p).pow(2.0f) + 0.984375f
            }
        }
    },
    //Easing.cubic(0.0, 0.0, 1.0, 1.0, "linear"),
    LINEAR {
        override fun invoke(it: Float): Float = it
    },
    // https://developer.mozilla.org/en-US/docs/Web/CSS/animation-timing-function
    EASE {
        val easing = EasingCubic(0.25f, 0.1f, 0.25f, 1.0f, "ease")
        override fun invoke(it: Float): Float = easing.invoke(it)
    },
    EASE_IN {
        val easing = EasingCubic(0.42f, 0.0f, 1.0f, 1.0f, "ease-in")
        override fun invoke(it: Float): Float = easing.invoke(it)
    },
    EASE_OUT {
        val easing = EasingCubic(0.0f, 0.0f, 0.58f, 1.0f, "ease-out")
        override fun invoke(it: Float): Float = easing.invoke(it)
    },
    EASE_IN_OUT {
        val easing = EasingCubic(0.42f, 0.0f, 0.58f, 1.0f, "ease-in-out")
        override fun invoke(it: Float): Float = easing.invoke(it)
    },
    //EASE_OUT_IN {
    //    val easing = EasingCubic(-, "ease-out-in")
    //    override fun invoke(it: Double): Double = easing.invoke(it)
    //},
    EASE_IN_OLD {
        override fun invoke(it: Float): Float = it * it * it
    },
    EASE_OUT_OLD {
        override fun invoke(it: Float): Float = (it - 1f).let { inv -> inv * inv * inv + 1 }
    },
    EASE_IN_OUT_OLD {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_IN_OLD, EASE_OUT_OLD)
    },
    EASE_OUT_IN_OLD {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_OUT_OLD, EASE_IN_OLD)
    },
    EASE_IN_BACK {
        override fun invoke(it: Float): Float = it.pow(2f) * ((BOUNCE_FACTOR + 1f) * it - BOUNCE_FACTOR)
    },
    EASE_OUT_BACK {
        override fun invoke(it: Float): Float = (it - 1f).let { inv -> inv.pow(2f) * ((BOUNCE_FACTOR + 1f) * inv + BOUNCE_FACTOR) + 1f }
    },
    EASE_IN_OUT_BACK {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_IN_BACK, EASE_OUT_BACK)
    },
    EASE_OUT_IN_BACK {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_OUT_BACK, EASE_IN_BACK)
    },
    EASE_IN_OUT_ELASTIC {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_IN_ELASTIC, EASE_OUT_ELASTIC)
    },
    EASE_OUT_IN_ELASTIC {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_OUT_ELASTIC, EASE_IN_ELASTIC)
    },
    EASE_IN_BOUNCE {
        override fun invoke(it: Float): Float = 1f - EASE_OUT_BOUNCE(1f - it)
    },
    EASE_IN_OUT_BOUNCE {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_IN_BOUNCE, EASE_OUT_BOUNCE)
    },
    EASE_OUT_IN_BOUNCE {
        override fun invoke(it: Float): Float = Easing.combine(it, EASE_OUT_BOUNCE, EASE_IN_BOUNCE)
    },
    EASE_IN_QUAD {
        override fun invoke(it: Float): Float = 1f * it * it
    },
    EASE_OUT_QUAD {
        override fun invoke(it: Float): Float = -1f * it * (it - 2)
    },
    EASE_IN_OUT_QUAD {
        override fun invoke(it: Float): Float =
            (it * 2f).let { t ->
                if (t < 1) {
                    +1f / 2 * t * t
                } else {
                    -1f / 2 * ((t - 1) * ((t - 1) - 2) - 1)
                }
            }
    },
    EASE_SINE {
        override fun invoke(it: Float): Float = sin(it * HALF_PI)
    },
    EASE_CLAMP_START {
        override fun invoke(it: Float): Float = if (it <= 0f) 0f else 1f
    },
    EASE_CLAMP_END {
        override fun invoke(it: Float): Float = if (it < 1f) 0f else 1f
    },
    EASE_CLAMP_MIDDLE {
        override fun invoke(it: Float): Float = if (it < 0.5f) 0f else 1f
    };

    override fun toString(): String = super.toString().replace('_', '-').lowercase()
}

// @TODO: We need to heavily optimize this. If we can have a formula instead of doing a bisect, this would be much faster.
class EasingCubic(val x1: Float, val y1: Float, val x2: Float, val y2: Float, val name: String? = null) : Easing {
    constructor(x1: Double, y1: Double, x2: Double, y2: Double, name: String? = null) : this(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), name)
    data class P(val x: Double, val y: Double) {
        constructor(x: Float, y: Float) : this(x.toDouble(), y.toDouble())
        operator fun times(that: Double): P = P(x * that, y * that)
        operator fun times(that: P): P = P(x * that.x, y * that.y)
        operator fun plus(that: P): P = P(x + that.x, y + that.y)
    }
    val points = arrayOf(P(0f, 0f), P(x1.clamp(0f, 1f), y1), P(x2.clamp(0f, 1f), y2), P(1f, 1f))
    override fun toString(): String = name ?: "cubic-bezier($x1, $y1, $x2, $y2)"

    //// @TODO: this doesn't work properly for `it` outside range [0, 1], and not in constant time
    override fun invoke(it: Float): Float {
        var pivotLeft = if (it < 0f) it * 10f else 0f
        var pivotRight = if (it > 1f) it * 10f else 1f
        //var pivot = (pivotLeft + pivotRight) * 0.5
        var pivot = it
        //println(" - x=$x, time=$time, pivotLeft=$pivotLeft, pivotRight=$pivotRight, pivot=$pivot")
        var lastX = 0f
        var lastY = 0f
        var steps = 0
        for (n in 0 until 50) {
            steps++
            val res = calc(pivot)
            lastX = res.x.toFloat()
            lastY = res.y.toFloat()
            if ((lastX - it).absoluteValue < 0.001) break
            if (it < lastX) {
                pivotRight = pivot
                pivot = (pivotLeft + pivot) * 0.5f
            } else if (it > lastX) {
                pivotLeft = pivot
                pivot = (pivotRight + pivot) * 0.5f
            } else {
                break
            }
        }
        //println("Requested steps=$steps, deviation=${(lastX - x).absoluteValue} requestedX=$x, lastX=$lastX, pivot=$pivot, pivotLeft=$pivotLeft, pivotRight=$pivotRight, lastY=$lastY")
        return lastY
    }

    private fun calc(it: Float): P {
        val t = it.toRatio()
        val p = points
        val order = p.size - 1
        if (t == Ratio.ZERO) return p[0]
        if (t == Ratio.ONE) return p[order]
        if (order == 0) return p[0]
        val mt = (Ratio.ONE - t).toDouble()
        val mt2 = mt * mt
        val t2 = (t * t).toDouble()
        val a = mt2 * mt
        val b = mt2 * t * 3
        val c = mt * t2 * 3
        val d = t * t2
        return (p[0] * a) + (p[1] * b) + (p[2] * c) + (p[3] * d)
    }
}
