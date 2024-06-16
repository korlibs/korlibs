package korlibs.time.fast

import korlibs.time.*
import korlibs.time.core.*
import kotlin.time.*

/** Analogous to [Duration] but doesn't allocate on the JS target. Important when used intensively in JS code. For example in KorGE. */
inline class FastDuration(private val ms: Double) : Comparable<FastDuration> {
    companion object {
        val ZERO get() = FastDuration(0.0)
        val POSITIVE_INFINITY get() = FastDuration(Double.POSITIVE_INFINITY)
        val NEGATIVE_INFINITY get() = FastDuration(Double.NEGATIVE_INFINITY)
        val NaN get() = FastDuration(Double.NaN)
        val NIL get() = NaN

        @OptIn(CoreTimeInternalApi::class)
        fun now(): FastDuration = CoreTime.currentTimeMillisDouble().fastMilliseconds
    }
    val isNil get() = this == NIL

    val seconds: Double get() = ms / 1_000
    val milliseconds: Double get() = ms
    val microseconds: Double get() = ms * 1_000
    val nanoseconds: Double get() = ms * 1_000_000

    val fastSeconds: Double get() = seconds
    val fastMilliseconds: Double get() = milliseconds
    val fastMicroseconds: Double get() = microseconds
    val fastNanoseconds: Double get() = nanoseconds

    operator fun unaryPlus(): FastDuration = this
    operator fun unaryMinus(): FastDuration = FastDuration(-ms)
    operator fun plus(that: FastDuration): FastDuration = FastDuration(this.ms + that.ms)
    operator fun minus(that: FastDuration): FastDuration = FastDuration(this.ms - that.ms)
    operator fun div(other: FastDuration): Double = this.ms / other.ms

    operator fun times(scale: Double): FastDuration = FastDuration(this.ms * scale)
    operator fun div(scale: Double): FastDuration = FastDuration(this.ms / scale)
    inline operator fun times(scale: Number): FastDuration = times(scale.toDouble())
    inline operator fun div(scale: Number): FastDuration = div(scale.toDouble())

    operator fun plus(other: Duration): FastDuration = FastDuration(this.milliseconds + other.milliseconds)
    operator fun minus(other: Duration): FastDuration = FastDuration(this.milliseconds - other.milliseconds)
    operator fun times(other: Duration): FastDuration = FastDuration(this.milliseconds - other.milliseconds)
    operator fun div(other: Duration): Double = this.milliseconds / other.milliseconds

    // Comparable
    override fun compareTo(other: FastDuration): Int = this.ms.compareTo(other.ms)
    operator fun compareTo(other: Duration): Int = this.ms.compareTo(other.milliseconds)
}

operator fun Duration.compareTo(other: FastDuration): Int = this.milliseconds.compareTo(other.milliseconds)
operator fun Duration.plus(other: FastDuration): FastDuration = FastDuration(this.milliseconds + other.milliseconds)
operator fun Duration.minus(other: FastDuration): FastDuration = FastDuration(this.milliseconds - other.milliseconds)
operator fun Duration.times(other: FastDuration): FastDuration = FastDuration(this.milliseconds - other.milliseconds)
operator fun Duration.div(other: FastDuration): Double = this.milliseconds / other.milliseconds

val Duration.fast: FastDuration get() = if (this.isNil) FastDuration.NIL else this.milliseconds.fastMilliseconds
fun Duration.toFastDuration(): FastDuration = this.fast

val FastDuration.slow: Duration get() = if (this.isNil) Duration.NIL else this.seconds.seconds
fun FastDuration.toDuration(): Duration = this.slow

val Double.fastSeconds: FastDuration get() = FastDuration(this * 1_000)
val Int.fastSeconds: FastDuration get() = this.toDouble().fastSeconds
val Float.fastSeconds: FastDuration get() = this.toDouble().fastSeconds
inline val Number.fastSeconds: FastDuration get() = this.toDouble().fastSeconds

val Double.fastMilliseconds: FastDuration get() = FastDuration(this)
val Int.fastMilliseconds: FastDuration get() = this.toDouble().fastMilliseconds
val Float.fastMilliseconds: FastDuration get() = this.toDouble().fastMilliseconds
inline val Number.fastMilliseconds: FastDuration get() = this.toDouble().fastMilliseconds

val Double.fastMicroseconds: FastDuration get() = FastDuration(this / 1_000)
val Int.fastMicroseconds: FastDuration get() = this.toDouble().fastMilliseconds
val Float.fastMicroseconds: FastDuration get() = this.toDouble().fastMilliseconds
inline val Number.fastMicroseconds: FastDuration get() = this.toDouble().fastMilliseconds

val Double.fastNanoseconds: FastDuration get() = FastDuration(this / 1_000_000)
val Int.fastNanoseconds: FastDuration get() = this.toDouble().fastMilliseconds
val Float.fastNanoseconds: FastDuration get() = this.toDouble().fastMilliseconds
inline val Number.fastNanoseconds: FastDuration get() = this.toDouble().fastMilliseconds

/** Return true if [Duration.NIL] */
inline val FastDuration.isNil: Boolean get() = this == FastDuration.NIL

fun FastDuration.roundMilliseconds(): FastDuration = kotlin.math.round(milliseconds).fastMilliseconds
fun max(a: FastDuration, b: FastDuration): FastDuration = kotlin.math.max(a.milliseconds, b.milliseconds).fastMilliseconds
fun min(a: FastDuration, b: FastDuration): FastDuration = kotlin.math.min(a.milliseconds, b.milliseconds).fastMilliseconds
fun FastDuration.clamp(min: FastDuration, max: FastDuration): FastDuration = when {
    this < min -> min
    this > max -> max
    else -> this
}
inline fun FastDuration.coalesce(block: () -> FastDuration): FastDuration = if (this.isNil) block() else this
