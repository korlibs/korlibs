package korlibs.io.concurrent.atomic

import kotlinx.atomicfu.*
import kotlin.js.*

@JsName("korAtomicRef")
@Deprecated("", ReplaceWith("atomic(initial)", "kotlinx.atomicfu.atomic"))
fun <T> korAtomic(initial: T): KorAtomicRef<T> = atomic(initial)
@JsName("korAtomicBoolean")
@Deprecated("", ReplaceWith("atomic(initial)", "kotlinx.atomicfu.atomic"))
fun korAtomic(initial: Boolean): KorAtomicBoolean = atomic(initial)
@JsName("korAtomicInt")
@Deprecated("", ReplaceWith("atomic(initial)", "kotlinx.atomicfu.atomic"))
fun korAtomic(initial: Int): KorAtomicInt = atomic(initial)
@JsName("korAtomicLong")
@Deprecated("", ReplaceWith("atomic(initial)", "kotlinx.atomicfu.atomic"))
fun korAtomic(initial: Long): KorAtomicLong = atomic(initial)

@Deprecated("kotlinx.atomicfu.AtomicRef<T>")
typealias KorAtomicRef<T> = AtomicRef<T>
@Deprecated("kotlinx.atomicfu.AtomicBoolean")
typealias KorAtomicBoolean = AtomicBoolean
@Deprecated("kotlinx.atomicfu.AtomicInt")
typealias KorAtomicInt = AtomicInt
@Deprecated("kotlinx.atomicfu.AtomicLong")
typealias KorAtomicLong = AtomicLong

@Deprecated("", ReplaceWith("updateAndGet(transform)", "kotlinx.atomicfu.updateAndGet"))
inline fun <T> KorAtomicRef<T>.update(transform: (T) -> T): T = updateAndGet(transform)
@Deprecated("", ReplaceWith("updateAndGet(transform)", "kotlinx.atomicfu.updateAndGet"))
inline fun KorAtomicInt.update(transform: (Int) -> Int): Int = updateAndGet(transform)
@Deprecated("", ReplaceWith("updateAndGet(transform)", "kotlinx.atomicfu.updateAndGet"))
inline fun KorAtomicLong.update(transform: (Long) -> Long): Long = updateAndGet(transform)
@Deprecated("", ReplaceWith("updateAndGet(transform)", "kotlinx.atomicfu.updateAndGet"))
inline fun KorAtomicBoolean.update(transform: (Boolean) -> Boolean): Boolean = updateAndGet(transform)

/*
@Deprecated("", ReplaceWith("atomic(value)", "kotlinx.atomicfu.atomic"))
inline fun <T> KorAtomicRef(value: T): AtomicRef<T> = atomic(value)

@Deprecated("", ReplaceWith("atomic(value)", "kotlinx.atomicfu.atomic"))
inline fun KorAtomicInt(value: Int): AtomicInt = atomic(value)

@Deprecated("", ReplaceWith("atomic(value)", "kotlinx.atomicfu.atomic"))
inline fun KorAtomicLong(value: Long): AtomicLong = atomic(value)
 */

//fun KorAtomicInt.getAndAdd(delta: Int): Int = addAndGet(delta) - delta
//fun KorAtomicLong.getAndAdd(delta: Long): Long = addAndGet(delta) - delta
//fun KorAtomicInt.incrementAndGet() = addAndGet(1)
//fun KorAtomicLong.incrementAndGet() = addAndGet(1)
//fun KorAtomicInt.getAndIncrement() = getAndAdd(1)
//fun KorAtomicLong.getAndIncrement() = getAndAdd(1)
//inline operator fun <T> KorAtomicRef<T>.getValue(obj: Any, prop: KProperty<Any?>): T = this.value
//inline operator fun <T> KorAtomicRef<T>.setValue(obj: Any, prop: KProperty<Any?>, v: T) { this.value = v }
//inline operator fun KorAtomicBoolean.getValue(obj: Any, prop: KProperty<Any?>): Boolean = this.value
//inline operator fun KorAtomicBoolean.setValue(obj: Any, prop: KProperty<Any?>, v: Boolean) { this.value = v }
//inline operator fun KorAtomicInt.getValue(obj: Any, prop: KProperty<Any?>): Int = this.value
//inline operator fun KorAtomicInt.setValue(obj: Any, prop: KProperty<Any?>, v: Int) { this.value = v }
//inline operator fun KorAtomicLong.getValue(obj: Any, prop: KProperty<Any?>): Long = this.value
//inline operator fun KorAtomicLong.setValue(obj: Any, prop: KProperty<Any?>, v: Long) { this.value = v }

//fun AtomicInt.addAndGetMod(delta: Int, modulo: Int): Int = updateAndGet { (it + delta) % modulo }
//fun AtomicRef<Float>.addAndGetMod(delta: Float, modulo: Float): Float = updateAndGet { (it + delta) % modulo }
//fun AtomicRef<Double>.addAndGetMod(delta: Double, modulo: Double): Double = updateAndGet { (it + delta) % modulo }
