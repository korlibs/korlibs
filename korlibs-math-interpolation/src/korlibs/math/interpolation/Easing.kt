@file:Suppress("PackageDirectoryMismatch")

package korlibs.math.interpolation

import kotlin.math.*

private inline fun combine(it: Float, start: Easing, end: Easing) =
    if (it < .5f) .5f * start(it * 2f) else .5f * end((it - .5f) * 2f) + .5f

private const val BOUNCE_FACTOR = 1.70158f
private const val HALF_PI = PI.toFloat() / 2f

@Suppress("unused")
fun interface Easing {
    operator fun invoke(it: Float): Float
    operator fun invoke(it: Double): Double = invoke(it.toFloat()).toDouble()
    operator fun invoke(it: Ratio): Ratio = Ratio(invoke(it.toFloat()).toDouble())

    companion object {
        operator fun invoke(name: () -> String, block: (Float) -> Float): Easing {
            return object : Easing {
                override fun invoke(it: Float): Float = block(it)
                override fun toString(): String = name()
            }
        }

        fun steps(steps: Int, easing: Easing): Easing = Easing({ "steps($steps, $easing)" }) {
            easing((it * steps).toInt().toFloat() / steps)
        }
        fun cubic(x1: Float, y1: Float, x2: Float, y2: Float, name: String? = null): Easing = EasingCubic(x1, y1, x2, y2, name)
        fun cubic(x1: Double, y1: Double, x2: Double, y2: Double, name: String? = null): Easing = EasingCubic(x1, y1, x2, y2, name)

        fun cubic(f: (t: Float, b: Float, c: Float, d: Float) -> Float): Easing = Easing { f(it, 0f, 1f, 1f) }
        fun combine(start: Easing, end: Easing) = Easing { combine(it, start, end) }

        private val _ALL_LIST: List<Easings> by lazy(LazyThreadSafetyMode.PUBLICATION) { Easings.entries }
        private val _ALL: Map<String, Easings> by lazy(LazyThreadSafetyMode.PUBLICATION) { _ALL_LIST.associateBy { it.name } }

        val ALL_LIST: List<Easing> get() = _ALL_LIST

        /**
         * Retrieves a mapping of all standard easings defined directly in [Easing], for example "SMOOTH" -> Easing.SMOOTH.
         */
        val ALL: Map<String, Easing> get() = _ALL

        // Author's note:
        // 1. Make sure new standard easings are added both here and in the Easings enum class
        // 2. Make sure the name is the same, otherwise [ALL] will return confusing results

        val SMOOTH: Easing get() = Easings.SMOOTH
        val EASE_IN_ELASTIC: Easing get() = Easings.EASE_IN_ELASTIC
        val EASE_OUT_ELASTIC: Easing get() = Easings.EASE_OUT_ELASTIC
        val EASE_OUT_BOUNCE: Easing get() = Easings.EASE_OUT_BOUNCE
        val LINEAR: Easing get() = Easings.LINEAR
        val EASE: Easing get() = Easings.EASE
        val EASE_IN: Easing get() = Easings.EASE_IN
        val EASE_OUT: Easing get() = Easings.EASE_OUT
        val EASE_IN_OUT: Easing get() = Easings.EASE_IN_OUT
        val EASE_IN_OLD: Easing get() = Easings.EASE_IN_OLD
        val EASE_OUT_OLD: Easing get() = Easings.EASE_OUT_OLD
        val EASE_IN_OUT_OLD: Easing get() = Easings.EASE_IN_OUT_OLD
        val EASE_OUT_IN_OLD: Easing get() = Easings.EASE_OUT_IN_OLD
        val EASE_IN_BACK: Easing get() = Easings.EASE_IN_BACK
        val EASE_OUT_BACK: Easing get() = Easings.EASE_OUT_BACK
        val EASE_IN_OUT_BACK: Easing get() = Easings.EASE_IN_OUT_BACK
        val EASE_OUT_IN_BACK: Easing get() = Easings.EASE_OUT_IN_BACK
        val EASE_IN_OUT_ELASTIC: Easing get() = Easings.EASE_IN_OUT_ELASTIC
        val EASE_OUT_IN_ELASTIC: Easing get() = Easings.EASE_OUT_IN_ELASTIC
        val EASE_IN_BOUNCE: Easing get() = Easings.EASE_IN_BOUNCE
        val EASE_IN_OUT_BOUNCE: Easing get() = Easings.EASE_IN_OUT_BOUNCE
        val EASE_OUT_IN_BOUNCE: Easing get() = Easings.EASE_OUT_IN_BOUNCE
        val EASE_IN_QUAD: Easing get() = Easings.EASE_IN_QUAD
        val EASE_OUT_QUAD: Easing get() = Easings.EASE_OUT_QUAD
        val EASE_IN_OUT_QUAD: Easing get() = Easings.EASE_IN_OUT_QUAD
        val EASE_SINE: Easing get() = Easings.EASE_SINE
        val EASE_CLAMP_START: Easing get() = Easings.EASE_CLAMP_START
        val EASE_CLAMP_END: Easing get() = Easings.EASE_CLAMP_END
        val EASE_CLAMP_MIDDLE: Easing get() = Easings.EASE_CLAMP_MIDDLE
    }
}

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
        override fun invoke(it: Float): Float =
            (it - 1f).let { inv ->
                inv * inv * inv + 1
            }
    },
    EASE_IN_OUT_OLD {
        override fun invoke(it: Float): Float = combine(it, EASE_IN_OLD, EASE_OUT_OLD)
    },
    EASE_OUT_IN_OLD {
        override fun invoke(it: Float): Float = combine(it, EASE_OUT_OLD, EASE_IN_OLD)
    },
    EASE_IN_BACK {
        override fun invoke(it: Float): Float = it.pow(2f) * ((BOUNCE_FACTOR + 1f) * it - BOUNCE_FACTOR)
    },
    EASE_OUT_BACK {
        override fun invoke(it: Float): Float =
            (it - 1f).let { inv ->
                inv.pow(2f) * ((BOUNCE_FACTOR + 1f) * inv + BOUNCE_FACTOR) + 1f
            }
    },
    EASE_IN_OUT_BACK {
        override fun invoke(it: Float): Float = combine(it, EASE_IN_BACK, EASE_OUT_BACK)
    },
    EASE_OUT_IN_BACK {
        override fun invoke(it: Float): Float = combine(it, EASE_OUT_BACK, EASE_IN_BACK)
    },
    EASE_IN_OUT_ELASTIC {
        override fun invoke(it: Float): Float = combine(it, EASE_IN_ELASTIC, EASE_OUT_ELASTIC)
    },
    EASE_OUT_IN_ELASTIC {
        override fun invoke(it: Float): Float = combine(it, EASE_OUT_ELASTIC, EASE_IN_ELASTIC)
    },
    EASE_IN_BOUNCE {
        override fun invoke(it: Float): Float = 1f - EASE_OUT_BOUNCE(1f - it)
    },
    EASE_IN_OUT_BOUNCE {
        override fun invoke(it: Float): Float = combine(it, EASE_IN_BOUNCE, EASE_OUT_BOUNCE)
    },
    EASE_OUT_IN_BOUNCE {
        override fun invoke(it: Float): Float = combine(it, EASE_OUT_BOUNCE, EASE_IN_BOUNCE)
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



interface Interpolable<T> {
    fun interpolateWith(ratio: Ratio, other: T): T
}

interface MutableInterpolable<T> {
    fun setToInterpolated(ratio: Ratio, l: T, r: T): T
}

fun Ratio.interpolate(l: Float, r: Float): Float = (l + (r - l) * this.toFloat())
fun Ratio.interpolate(l: Double, r: Double): Double = (l + (r - l) * this.toDouble())
fun Ratio.interpolate(l: Ratio, r: Ratio): Ratio = (l + (r - l) * this)
fun Ratio.interpolate(l: Int, r: Int): Int = (l + (r - l) * this.toDouble()).toInt()
fun Ratio.interpolate(l: Long, r: Long): Long = (l + (r - l) * this.toDouble()).toLong()
fun <T : Interpolable<T>> Ratio.interpolate(l: T, r: T): T = l.interpolateWith(this, r)

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
