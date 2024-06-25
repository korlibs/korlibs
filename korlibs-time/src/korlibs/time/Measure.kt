package korlibs.time

import kotlin.contracts.*
import kotlin.time.*

///**
// * Executes a [callback] and measure the time it takes to complete.
// */
//@OptIn(ExperimentalContracts::class)
//public inline fun measureTime(block: () -> Unit): Duration {
//    contract {
//        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
//    }
//    return kotlin.time.measureTime(block)
//}

/**
 * Executes a [callback] and measure the time it takes to complete.
 */
@OptIn(ExperimentalContracts::class)
public inline fun fastMeasureTime(block: () -> Unit): FastDuration {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val stopwatch = Stopwatch()
    block()
    return stopwatch.fastElapsed
}

@OptIn(ExperimentalContracts::class)
inline fun <T> measureTime(callback: () -> T, handleTime: (Duration) -> Unit): T {
    contract {
        callsInPlace(callback, InvocationKind.EXACTLY_ONCE)
        callsInPlace(handleTime, InvocationKind.EXACTLY_ONCE)
    }
    val result: T
    handleTime(measureTime {
        result = callback()
    })
    return result
}

@OptIn(ExperimentalContracts::class)
inline fun <T> fastMeasureTime(callback: () -> T, handleTime: (FastDuration) -> Unit): T {
    contract {
        callsInPlace(callback, InvocationKind.EXACTLY_ONCE)
        callsInPlace(handleTime, InvocationKind.EXACTLY_ONCE)
    }
    val result: T
    handleTime(fastMeasureTime {
        result = callback()
    })
    return result
}

/**
 * Executes the [callback] measuring the time it takes to complete.
 * Returns a [TimedResult] with the time and the return value of the callback.
 */
@Deprecated("", ReplaceWith("measureTimedValue(callback)", "kotlin.time.measureTimedValue"))
@OptIn(ExperimentalContracts::class)
inline fun <T> measureTimeWithResult(callback: () -> T): TimedValue<T> {
    contract {
        callsInPlace(callback, InvocationKind.EXACTLY_ONCE)
    }
    return measureTimedValue(callback)
}

typealias TimedResult<T> = TimedValue<T>

@Deprecated("", ReplaceWith("value"))
val <T> TimedValue<T>.result: T get() = value
@Deprecated("", ReplaceWith("duration"))
val <T> TimedValue<T>.time: Duration get() = duration

public data class FastTimedValue<T>(val value: T, val duration: FastDuration)

@OptIn(ExperimentalContracts::class)
inline fun <T> fastMeasureTimedValue(callback: () -> T): FastTimedValue<T> {
    contract {
        callsInPlace(callback, InvocationKind.EXACTLY_ONCE)
    }
    val stopwatch = Stopwatch()
    val value = callback()
    return FastTimedValue(value, stopwatch.fastElapsed)
}
