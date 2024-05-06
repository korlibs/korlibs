@file:Suppress("PackageDirectoryMismatch")

package korlibs.datastructure.iterators

import korlibs.concurrent.thread.*
import korlibs.datastructure.DoubleArrayList
import korlibs.datastructure.FastArrayList
import korlibs.datastructure.FloatArrayList
import korlibs.datastructure.IntArrayList
import korlibs.datastructure.toFastList
import korlibs.io.async.*
import korlibs.io.concurrent.*
import kotlinx.atomicfu.*
import kotlinx.coroutines.*
import kotlin.math.*

@Deprecated("", ReplaceWith("Dispatchers.ConcurrencyLevel", "kotlinx.coroutines.Dispatchers", "korlibs.io.async.ConcurrencyLevel"))
val CONCURRENCY_COUNT: Int get() = Dispatchers.ConcurrencyLevel

@PublishedApi internal val exec by lazy {
    Dispatchers.createFixedThreadDispatcher("parallel", Dispatchers.ConcurrencyLevel)
}

fun parallelForeach(count: Int, dispatcher: CoroutineDispatcher = exec, block: (n: Int) -> Unit): Unit {
    if (count == 0) return

    if (Dispatchers.ConcurrencyLevel == 1) {
        for (n in 0 until count) {
            block(n)
        }
        return
    }

    //val futures = arrayListOf<Future<*>>()
    val countPerChunk = max(1, (count / Dispatchers.ConcurrencyLevel) + 1)

    val execCount = atomic(0)
    var m = 0
    val scope = CoroutineScope(dispatcher)
    for (start in 0 until count step countPerChunk) {
        val end = minOf(count, start + countPerChunk)
        m++
        scope.launch {
            try {
                for (n in start until end) block(n)
            } finally {
                execCount.incrementAndGet()
            }
        }
    }

    NativeThread.spinWhile { execCount.value != m }
}

@Suppress("UNCHECKED_CAST")
inline fun <T, reified R> List<T>.parallelMap(crossinline transform: (T) -> R): List<R> = arrayOfNulls<R>(size).also { out ->
    parallelForeach(size) { out[it] = transform(this[it]) }
}.toList() as List<R>

inline fun <T> List<T>.parallelMapInt(crossinline transform: (T) -> Int): IntArray = IntArray(size).also { out ->
    parallelForeach(size) { out[it] = transform(this[it]) }
}

inline fun IntArray.parallelMapInt(crossinline transform: (Int) -> Int): IntArray = IntArray(size).also { out ->
    parallelForeach(size) { out[it] = transform(this[it]) }
}

inline fun IntArrayList.parallelMapInt(crossinline transform: (Int) -> Int): IntArray = IntArray(size).also { out ->
    parallelForeach(size) { out[it] = transform(this[it]) }
}

inline fun IntRange.parallelMapInt(crossinline transform: (Int) -> Int): IntArray {
    val size = ((this.last - this.first) + 1) / step
    return IntArray(size.coerceAtLeast(0)).also { out ->
        parallelForeach(size) {
            out[it] = transform(this.first + this.step * it)
        }
    }
}

inline fun <T> FastArrayList<T>.fastForEachWithTemp(temp: FastArrayList<T>, callback: (T) -> Unit) {
    this.toFastList(temp)
    try {
        temp.fastForEach(callback)
    } finally {
        temp.clear()
    }
}

inline fun <T> List<T>.fastForEachWithTemp(temp: FastArrayList<T>, callback: (T) -> Unit) {
    this.toFastList(temp)
    try {
        temp.fastForEach(callback)
    } finally {
        temp.clear()
    }
}

inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
	var n = 0
	while (n < size) callback(this[n++])
}

inline fun <T> Array<T>.fastForEach(callback: (T) -> Unit) {
	var n = 0
	while (n < size) callback(this[n++])
}

inline fun IntArray.fastForEach(callback: (Int) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun FloatArray.fastForEach(callback: (Float) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun DoubleArray.fastForEach(callback: (Double) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun IntArrayList.fastForEach(callback: (Int) -> Unit) {
    var n = 0
    while (n < size) {
        callback(this.getAt(n++))
    }
}

inline fun FloatArrayList.fastForEach(callback: (Float) -> Unit) {
    var n = 0
    while (n < size) {
        callback(this.getAt(n++))
    }
}

inline fun DoubleArrayList.fastForEach(callback: (Double) -> Unit) {
    var n = 0
    while (n < size) {
        callback(this.getAt(n++))
    }
}

inline fun <T> List<T>.fastForEachWithIndex(callback: (index: Int, value: T) -> Unit) {
	var n = 0
	while (n < size) {
		callback(n, this[n])
		n++
	}
}

inline fun <T> Array<T>.fastForEachWithIndex(callback: (index: Int, value: T) -> Unit) {
	var n = 0
	while (n < size) {
		callback(n, this[n])
		n++
	}
}

inline fun IntArrayList.fastForEachWithIndex(callback: (index: Int, value: Int) -> Unit) {
    var n = 0
    while (n < size) {
        callback(n, this.getAt(n))
        n++
    }
}

inline fun FloatArrayList.fastForEachWithIndex(callback: (index: Int, value: Float) -> Unit) {
    var n = 0
    while (n < size) {
        callback(n, this.getAt(n))
        n++
    }
}

inline fun DoubleArrayList.fastForEachWithIndex(callback: (index: Int, value: Double) -> Unit) {
    var n = 0
    while (n < size) {
        callback(n, this.getAt(n))
        n++
    }
}

inline fun <T> List<T>.fastForEachReverse(callback: (T) -> Unit) {
	var n = 0
	while (n < size) {
		callback(this[size - n - 1])
		n++
	}
}

inline fun <T> MutableList<T>.fastIterateRemove(callback: (T) -> Boolean): MutableList<T> {
	var n = 0
	var m = 0
	while (n < size) {
		if (m >= 0 && m != n) this[m] = this[n]
		if (callback(this[n])) m--
		n++
		m++
	}
	while (this.size > m) this.removeAt(this.size - 1)
	return this
}
