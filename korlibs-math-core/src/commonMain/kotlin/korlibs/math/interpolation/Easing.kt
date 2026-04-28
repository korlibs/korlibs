package korlibs.math.interpolation

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
        fun cubic(f: (t: Float, b: Float, c: Float, d: Float) -> Float): Easing = Easing { f(it, 0f, 1f, 1f) }
        fun combine(start: Easing, end: Easing): Easing = Easing { combine(it, start, end) }
        inline fun combine(it: Float, start: Easing, end: Easing): Float =
            if (it < .5f) .5f * start(it * 2f) else .5f * end((it - .5f) * 2f) + .5f

        val LINEAR = Easing { it }
        val SMOOTH = Easing { it * it * (3 - 2 * it) }
    }
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
