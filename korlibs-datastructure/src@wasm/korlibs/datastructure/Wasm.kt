@file:Suppress("NOTHING_TO_INLINE")

package korlibs.datastructure

import korlibs.datastructure.internal.memory.Memory.arraycopy

actual inline fun <T> Any?.fastCastTo(): T = this as T

//@JsName("Map")
//private external class JsMap { }
//@JsName("Array")
//@PublishedApi
//internal external class JsArray<T> {
//    var length: Int
//    //@nativeGetter operator fun get(index: Int): T = definedExternally
//    //@nativeSetter operator fun set(index: Int, value: T): T = definedExternally
//    fun concat(vararg arrays: JsArray<T>): JsArray<T>
//    fun indexOf(e: T): Int
//    fun lastIndexOf(e: T): Int
//    fun splice(start: Int, deleteCount: Int, vararg items: T): JsArray<T>
//    fun unshift(vararg items: T)
//    fun push(vararg items: T)
//    fun shift(): T
//    fun pop(): T
//    companion object {
//        fun from(value: dynamic): Array<dynamic>
//    }
//}

actual typealias FastIntMap<T> = IntMap<T>

actual inline fun <T> FastIntMap(): FastIntMap<T> = IntMap()
actual val <T> FastIntMap<T>.size: Int get() = (this as IntMap<T>).size
actual fun <T> FastIntMap<T>.keys(): List<Int> = (this as IntMap<T>).keys.toList()
actual inline operator fun <T> FastIntMap<T>.get(key: Int): T? = (this as IntMap<T>).get(key)
actual inline operator fun <T> FastIntMap<T>.set(key: Int, value: T) { (this as IntMap<T>).set(key, value) }
actual inline operator fun <T> FastIntMap<T>.contains(key: Int): Boolean = (this as IntMap<T>).contains(key)
actual inline fun <T> FastIntMap<T>.remove(key: Int) { (this as IntMap<T>).remove(key) }
actual inline fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int) = (this as IntMap<T>).removeRange(src, dst)
actual inline fun <T> FastIntMap<T>.clear() = (this as IntMap<T>).clear()
actual inline fun <T> FastIntMap<T>.fastKeyForEach(callback: (key: Int) -> Unit) {
    (this as IntMap<T>).fastKeyForEach(callback)
}

///////////

actual class FastStringMap<T>(val dummy: Boolean) {
    //val map = LinkedHashMap<String, T>()
    val map = HashMap<String, T>()
}

actual inline fun <T> FastStringMap(): FastStringMap<T> = FastStringMap(true)
actual val <T> FastStringMap<T>.size: Int get() = (this.map).size
actual inline operator fun <T> FastStringMap<T>.get(key: String): T? = (this.map).get(key)
actual inline operator fun <T> FastStringMap<T>.set(key: String, value: T) { (this.map).set(key, value) }
actual inline operator fun <T> FastStringMap<T>.contains(key: String): Boolean = (this.map).contains(key)
actual inline fun <T> FastStringMap<T>.remove(key: String) { (this.map).remove(key) }
actual inline fun <T> FastStringMap<T>.clear() = (this.map).clear()
actual fun <T> FastStringMap<T>.keys(): List<String> = map.keys.toList()
actual fun <T> FastStringMap<T>.putAll(other: FastStringMap<T>) {
    val that = this as FastStringMap<T?>
    for (key in other.keys) {
        that[key] = other[key]
    }
}

actual inline fun <T> FastStringMap<T>.fastKeyForEach(callback: (key: String) -> Unit) {
    for (key in this.keys()) {
        callback(key)
    }
}

///////////

actual class FastIdentityMap<K, V>(dummy: Boolean) {
    val map = SlowIdentityHashMap<K, V>()
    val size get() = map.size
}
actual fun <K, V> FastIdentityMap(): FastIdentityMap<K, V> = FastIdentityMap(true)
actual val <K, V> FastIdentityMap<K, V>.size: Int get() = this.map.size
actual fun <K, V> FastIdentityMap<K, V>.keys(): List<K> = this.map.keys.toList()
actual operator fun <K, V> FastIdentityMap<K, V>.get(key: K): V? = this.map[key]
actual operator fun <K, V> FastIdentityMap<K, V>.set(key: K, value: V) { this.map[key] = value }
actual operator fun <K, V> FastIdentityMap<K, V>.contains(key: K): Boolean = this.map.containsKey(key)
actual fun <K, V> FastIdentityMap<K, V>.remove(key: K) { this.map.remove(key) }
actual fun <K, V> FastIdentityMap<K, V>.clear() { this.map.clear() }
actual inline fun <K, V> FastIdentityMap<K, V>.fastKeyForEach(callback: (key: K) -> Unit) {
    this.map.buckets.fastForEach { _, bucket ->
        bucket.keys.fastForEach {
            callback(it)
        }
    }
}

//////////////

//@JsName("WeakMap")
//external class JsWeakMap {
//}
//
//@JsFun("(map, k) => { return map.has(k); }")
//external fun JsWeakMap_has(map: JsWeakMap, k: Any?): Boolean
//
//@JsFun("(map, k) => { map.delete(k); }")
//external fun JsWeakMap_delete(map: JsWeakMap, k: Any?)
//
//@JsFun("(map, k) => { return map.has(k); }")
//external fun JsWeakMap_get(map: JsWeakMap, k: Any?): Any?
//
//@JsFun("(map, k, v) => { map.set(k, v); }")
//external fun JsWeakMap_set(map: JsWeakMap, k: Any?, v: Any?): Boolean
//actual class WeakMap<K : Any, V> {
//    val wm = JsWeakMap()
//
//    actual operator fun contains(key: K): Boolean = JsWeakMap_has(wm, key)
//    actual operator fun set(key: K, value: V) {
//        if (key is String) error("Can't use String as WeakMap keys")
//        JsWeakMap_set(wm, key, value)
//    }
//
//    actual operator fun get(key: K): V? = JsWeakMap_get(wm, key).fastCastTo<V?>()
//    actual fun remove(key: K) {
//        JsWeakMap_delete(wm, key)
//    }
//
//}

// @TODO:
actual class WeakMap<K : Any, V> {
    val wm = HashMap<K, V>()

    init {
        println("WARNING! WeakMap not implemented in WASM just yet!")
    }

    actual operator fun contains(key: K): Boolean = wm.contains(key)
    actual operator fun set(key: K, value: V) {
        if (key is String) error("Can't use String as WeakMap keys")
        wm[key] = value
    }

    actual operator fun get(key: K): V? = wm[key]
    actual fun remove(key: K) {
        wm.remove(key)
    }

}

////////////
