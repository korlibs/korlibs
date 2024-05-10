@file:Suppress("NOTHING_TO_INLINE")

package korlibs.datastructure

import Array_from
import JsArray
import JsMap
import JsWeakMap
import get
import set

actual inline fun <T> Any?.fastCastTo(): T = this.unsafeCast<T>()

actual class FastIntMap<T>(dummy: Boolean)


actual fun <T> FastIntMap(): FastIntMap<T> = JsMap().asDynamic()
actual val <T> FastIntMap<T>.size: Int get() = (this.asDynamic()).size
actual fun <T> FastIntMap<T>.keys(): List<Int> = Array_from((this.asDynamic()).keys()).unsafeCast<Array<Int>>().toList()
actual inline operator fun <T> FastIntMap<T>.get(key: Int): T? = (this.asDynamic()).get(key)
actual inline operator fun <T> FastIntMap<T>.set(key: Int, value: T) { (this.asDynamic()).set(key, value) }
actual inline operator fun <T> FastIntMap<T>.contains(key: Int): Boolean = (this.asDynamic()).contains(key) != undefined
actual inline fun <T> FastIntMap<T>.remove(key: Int) { (this.asDynamic()).delete(key) }
actual inline fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int) {
    //@Suppress("UNUSED_VARIABLE") val obj = this.asDynamic()
    //js("for (var key in obj.keys()) if (key >= src && key <= dst) obj.delete(key);")
    for (key in keys) if (key in src..dst) remove(key)
}

actual inline fun <T> FastIntMap<T>.clear() {
    (this.asDynamic()).clear()
}

@Suppress("UnsafeCastFromDynamic")
actual inline fun <T> FastIntMap<T>.fastKeyForEach(callback: (key: Int) -> Unit) {
    //println("FastStringMap<T>.fastKeyForEach")
    val mapIterator = this.asDynamic().keys()
    //console.log(mapIterator)
    while (true) {
        val v = mapIterator.next()
        //console.log(v)
        if (v.done) break
        callback(v.value)
    }
}

/////////////////

actual class FastStringMap<T>(dummy: Boolean)
//actual typealias FastStringMap<T> = Any<T>

actual fun <T> FastStringMap(): FastStringMap<T> = JsMap().asDynamic()
actual val <T> FastStringMap<T>.size: Int get() = this.asDynamic().size
actual fun <T> FastStringMap<T>.keys(): List<String> =
    Array_from((this.asDynamic()).keys()).unsafeCast<Array<String>>().toList()

actual inline operator fun <T> FastStringMap<T>.get(key: String): T? = (this.asDynamic()).get(key)
actual inline operator fun <T> FastStringMap<T>.set(key: String, value: T): Unit =
    run { (this.asDynamic()).set(key, value) }

actual inline operator fun <T> FastStringMap<T>.contains(key: String): Boolean = (this.asDynamic()).has(key)
actual inline fun <T> FastStringMap<T>.remove(key: String) { (this.asDynamic()).delete(key) }
actual inline fun <T> FastStringMap<T>.clear() { (this.asDynamic()).clear() }
actual fun <T> FastStringMap<T>.putAll(other: FastStringMap<T>) {
    for (key in other.keys) {
        this[key] = other[key].asDynamic()
    }
}
@Suppress("UnsafeCastFromDynamic")
actual inline fun <T> FastStringMap<T>.fastKeyForEach(callback: (key: String) -> Unit) {
    //println("FastStringMap<T>.fastKeyForEach")
    val mapIterator = this.asDynamic().keys()
    //console.log(mapIterator)
    while (true) {
        val v = mapIterator.next()
        //console.log(v)
        if (v.done) break
        callback(v.value)
    }
}

///////////////

actual class FastIdentityMap<K, V>(dummy: Boolean)

actual fun <K, V> FastIdentityMap(): FastIdentityMap<K, V> = JsMap().asDynamic()
actual val <K, V> FastIdentityMap<K, V>.size: Int get() = this.asDynamic().size
actual fun <K, V> FastIdentityMap<K, V>.keys(): List<K> = Array_from((this.asDynamic()).keys()).unsafeCast<Array<K>>().toList()
actual operator fun <K, V> FastIdentityMap<K, V>.get(key: K): V? = (this.asDynamic()).get(key)
actual operator fun <K, V> FastIdentityMap<K, V>.set(key: K, value: V) { (this.asDynamic()).set(key, value) }
actual operator fun <K, V> FastIdentityMap<K, V>.contains(key: K): Boolean = (this.asDynamic()).has(key)
actual fun <K, V> FastIdentityMap<K, V>.remove(key: K) { (this.asDynamic()).delete(key) }
actual fun <K, V> FastIdentityMap<K, V>.clear() { (this.asDynamic()).clear() }
actual inline fun <K, V> FastIdentityMap<K, V>.fastKeyForEach(callback: (key: K) -> Unit) {
    //println("FastStringMap<T>.fastKeyForEach")
    val mapIterator = this.asDynamic().keys()
    //console.log(mapIterator)
    while (true) {
        val v = mapIterator.next()
        //console.log(v)
        if (v.done) break
        callback(v.value)
    }
}

//////////////

actual class WeakMap<K : Any, V> {
    val wm = JsWeakMap()

    actual operator fun contains(key: K): Boolean = wm.has(key)
    actual operator fun set(key: K, value: V) {
        if (key is String) error("Can't use String as WeakMap keys")
        wm.set(key, value)
    }

    actual operator fun get(key: K): V? = wm.get(key).unsafeCast<V?>()
    actual fun remove(key: K) {
        wm.delete(key)
    }

}
