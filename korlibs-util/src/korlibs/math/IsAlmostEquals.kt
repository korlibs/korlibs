package korlibs.math

import kotlin.math.*

interface IsAlmostEquals<T> {
    fun isAlmostEquals(other: T, epsilon: Double = 0.000001): Boolean
}

interface IsAlmostEqualsF<T> {
    fun isAlmostEquals(other: T, epsilon: Float = 0.0001f): Boolean
}

fun Double.isAlmostEquals(other: Double, epsilon: Double = 0.000001): Boolean = (this - other).absoluteValue < epsilon
fun Float.isAlmostEquals(other: Float, epsilon: Float = 0.000001f): Boolean = (this - other).absoluteValue < epsilon
//fun Double.isAlmostEquals(other: Double, epsilon: Double = 0.0001): Boolean = (this - other).absoluteValue < epsilon
fun Double.isAlmostZero(): Boolean = kotlin.math.abs(this) <= 1e-19
fun Float.isAlmostZero(): Boolean = kotlin.math.abs(this) <= 1e-6
