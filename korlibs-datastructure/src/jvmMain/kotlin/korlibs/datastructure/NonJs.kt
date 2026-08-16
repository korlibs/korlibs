package korlibs.datastructure

@Suppress("UNCHECKED_CAST")
actual inline fun <T> Any?.fastCastTo(): T = this as T

////////////

actual typealias FastIntMap<T> = IntMap<T>

actual inline fun <T> FastIntMap(): FastIntMap<T> = IntMap()
actual val <T> FastIntMap<T>.size: Int get() = this.size
actual fun <T> FastIntMap<T>.keys(): List<Int> = this.keys.toList()
actual inline operator fun <T> FastIntMap<T>.get(key: Int): T? = this[key]
actual inline operator fun <T> FastIntMap<T>.set(key: Int, value: T) {
    this[key] = value
}
actual inline operator fun <T> FastIntMap<T>.contains(key: Int): Boolean = this.contains(key)
actual inline fun <T> FastIntMap<T>.remove(key: Int) { this.remove(key) }
actual inline fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int) = this.removeRange(src, dst)
actual inline fun <T> FastIntMap<T>.clear() = this.clear()
actual inline fun <T> FastIntMap<T>.fastKeyForEach(callback: (key: Int) -> Unit) {
    this.fastKeyForEach(callback)
}

///////////

actual class FastStringMap<T>(val dummy: Boolean) {
    //val map = LinkedHashMap<String, T>()
    val map = HashMap<String, T>()
}

actual inline fun <T> FastStringMap(): FastStringMap<T> = FastStringMap(true)
actual val <T> FastStringMap<T>.size: Int get() = map.size
actual inline operator fun <T> FastStringMap<T>.get(key: String): T? = map[key]
actual inline operator fun <T> FastStringMap<T>.set(key: String, value: T) {
    map[key] = value
}
actual inline operator fun <T> FastStringMap<T>.contains(key: String): Boolean = map.contains(key)
actual inline fun <T> FastStringMap<T>.remove(key: String) { map.remove(key) }
actual inline fun <T> FastStringMap<T>.clear() = map.clear()
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
