package korlibs.datastructure

internal inline fun <T> indexOfCheck(value: T, start: Int, end: Int, get: (Int) -> T): Int {
    for (n in start until end) if (get(n) == value) return n
    return -1
}

internal inline fun <T> lastIndexOfCheck(value: T, start: Int, end: Int, get: (Int) -> T): Int {
    for (n in end - 1 downTo  start) if (get(n) == value) return n
    return -1
}

internal fun <T> containsCheck(collection: Collection<T>, element: T): Boolean {
    for (v in collection) if (v == element) return true
    return false
}

internal fun <T> containsAllCheck(collection: Collection<T>, elements: Collection<T>): Boolean {
    val elementsMap = elements.toMutableSet()
    for (v in collection) elementsMap.remove(v)
    return elementsMap.isEmpty()
}

internal inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

internal inline fun <T> Array<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}
