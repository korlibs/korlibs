package korlibs.datastructure

import kotlin.reflect.*

class Computed<K : Computed.WithParent<K>, T>(val prop: KProperty1<K, T?>, val default: () -> T) {
    interface WithParent<T> {
        val parent: T?
    }

    operator fun getValue(thisRef: K?, p: KProperty<*>): T {
        var current: K? = thisRef
        while (current != null) {
            val result = prop.get(current)
            if (result != null) return result
            current = current.parent
        }

        return default()
    }
}

class WeakProperty<V>(val gen: () -> V) {
    val map = WeakMap<Any, V>()

    operator fun getValue(obj: Any, property: KProperty<*>): V = map.getOrPut(obj) { gen() }
    operator fun setValue(obj: Any, property: KProperty<*>, value: V) { map[obj] = value }
}

class WeakPropertyThis<T : Any, V>(val gen: T.() -> V) {
    val map = WeakMap<T, V>()

    operator fun getValue(obj: T, property: KProperty<*>): V = map.getOrPut(obj) { gen(obj) }
    operator fun setValue(obj: T, property: KProperty<*>, value: V) { map[obj] = value }
}

class Observable<T>(val initial: T, val before: (T) -> Unit = {}, val after: (T) -> Unit = {}) {
    var currentValue = initial

    operator fun getValue(obj: Any, prop: KProperty<*>): T = currentValue

    operator fun setValue(obj: Any, prop: KProperty<*>, value: T) {
        before(value)
        currentValue = value
        after(value)
    }
}

class LazyDelegate<T>(val propRef: () -> KMutableProperty0<T>) {
    val cachedPropRef by lazy { propRef() }
    operator fun getValue(obj: Any, prop: KProperty<*>): T = cachedPropRef.get()
    operator fun setValue(obj: Any, prop: KProperty<*>, value: T) = cachedPropRef.set(value)
}
