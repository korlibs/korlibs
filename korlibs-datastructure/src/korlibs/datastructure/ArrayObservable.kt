package korlibs.datastructure

import korlibs.math.geom.*
import kotlinx.atomicfu.*

fun <T> Array<T>.observe(updated: ObservableArray<T>.(IntRange) -> Unit): ObservableArray<T> = ObservableArray<T>(this, updated)
fun IntArray.observe(updated: ObservableIntArray.(IntRange) -> Unit): ObservableIntArray = ObservableIntArray(this, updated)

class ObservableArray<T>(val base: Array<T>, updated: ObservableArray<T>.(IntRange) -> Unit) : BaseObservableArray1<ObservableArray<T>, Int>(updated) {
    override val size: Int get() = base.size

    operator fun get(index: Int): T = base[index]
    operator fun set(index: Int, element: T) {
        if (base[index] == element) return
        base[index] = element
        modified(index)
    }

    operator fun set(range: IntRange, element: T) {
        val r = coercedRange(range)
        for (n in r) base[n] = element
        modified(r)
    }
}

class ObservableIntArray(val base: IntArray, updated: ObservableIntArray.(IntRange) -> Unit) : BaseObservableArray1<ObservableIntArray, Int>(updated) {
    override val size: Int get() = base.size

    operator fun get(index: Int): Int = base[index]
    operator fun iterator(): IntIterator = base.iterator()
    operator fun set(index: Int, value: Int) {
        if (base[index] == value) return
        base[index] = value
        modified(index)
    }
    operator fun set(range: IntRange, element: Int) {
        val r = coercedRange(range)
        for (n in r) base[n] = element
        modified(r)
    }
}

fun IntIArray2.observe(updated: ObservableIntArray2.(RectangleInt) -> Unit): ObservableIntArray2 = ObservableIntArray2(this, updated)
fun DoubleIArray2.observe(updated: ObservableDoubleArray2.(RectangleInt) -> Unit): ObservableDoubleArray2 = ObservableDoubleArray2(this, updated)
fun LongIArray2.observe(updated: ObservableLongArray2.(RectangleInt) -> Unit): ObservableLongArray2 = ObservableLongArray2(this, updated)
fun <T> IArray2<T>.observe(updated: ObservableArray2<T>.(RectangleInt) -> Unit): ObservableArray2<T> = ObservableArray2<T>(this, updated)

open class ObservableArray2<T>(override val base: IArray2<T>, updated: ObservableArray2<T>.(RectangleInt) -> Unit) : BaseObservableArray2<ObservableArray2<T>, T>(base, updated), IArray2<T> {
    override fun setAt(idx: Int, value: T) {
        if (getAt(idx) == value) return
        base.setAt(idx, value)
        modified(revIndexX(idx), revIndexY(idx))
    }

    override fun getAt(idx: Int): T = base.getAt(idx)

    override fun setAt(rect: RectangleInt, value: T) {
        base.setAt(rect, value)
        modified(rect)
    }

    operator fun set(x: Int, y: Int, value: T) { setAt(x, y, value) }
    operator fun get(x: Int, y: Int): T = getAt(x, y)
    operator fun set(p: PointInt, value: T) { setAt(p, value) }
    operator fun get(p: PointInt): T = getAt(p)
    operator fun set(r: RectangleInt, value: T) { setAt(r, value) }
}

open class ObservableIntArray2(override val base: IntIArray2, updated: ObservableIntArray2.(RectangleInt) -> Unit) : BaseObservableArray2<ObservableIntArray2, Int>(base, updated), IntIArray2 {
    override fun getFast(idx: Int): Int = base.getFast(idx)
    override fun setFast(idx: Int, value: Int) {
        if (getFast(idx) == value) return
        base.setFast(idx, value)
        modified(revIndexX(idx), revIndexY(idx))
    }
    override fun set(rect: RectangleInt, value: Int) {
        base[rect] = value
        modified(rect)
    }
}

open class ObservableDoubleArray2(override val base: DoubleIArray2, updated: ObservableDoubleArray2.(RectangleInt) -> Unit) : BaseObservableArray2<ObservableDoubleArray2, Double>(base, updated), DoubleIArray2 {
    override fun getFast(idx: Int): Double = base.getFast(idx)
    override fun setFast(idx: Int, value: Double) {
        if (getFast(idx) == value) return
        base.setFast(idx, value)
        modified(revIndexX(idx), revIndexY(idx))
    }

    override fun set(rect: RectangleInt, value: Double) {
        base[rect] = value
        modified(rect)
    }
}

open class ObservableLongArray2(override val base: LongIArray2, updated: ObservableLongArray2.(RectangleInt) -> Unit) : BaseObservableArray2<ObservableLongArray2, Long>(base, updated), LongIArray2 {
    override fun getFast(idx: Int): Long = base.getFast(idx)
    override fun setFast(idx: Int, value: Long) {
        if (getFast(idx) == value) return
        base.setFast(idx, value)
        modified(revIndexX(idx), revIndexY(idx))
    }

    override fun set(rect: RectangleInt, value: Long) {
        base[rect] = value
        modified(rect)
    }
}

abstract class BaseObservableArray2<T, E>(open val base: IArray2<E>, val updated: T.(RectangleInt) -> Unit) : BaseObservableArray<E>(), IArray2<E> {
    private var xmin = 0
    private var xmax = 0
    private var ymin = 0
    private var ymax = 0

    private val isEmpty get() = xmin > xmax

    init {
        reset()
    }

    protected fun reset() {
        xmin = Int.MAX_VALUE
        xmax = Int.MIN_VALUE
        ymin = Int.MAX_VALUE
        ymax = Int.MIN_VALUE
    }

    override fun flush() {
        if (locked.value == 0 && !isEmpty) {
            _version.incrementAndGet()
            updated(this as T, RectangleInt.fromBounds(xmin, ymin, xmax + 1, ymax + 1))
            reset()
        }
    }

    private fun _mark(x: Int, y: Int) {
        //println("modified: $x,$y")
        xmin = minOf(xmin, x).coerceIn(0, width - 1)
        ymin = minOf(ymin, y).coerceIn(0, height - 1)
        xmax = maxOf(xmax, x).coerceIn(0, width - 1)
        ymax = maxOf(ymax, y).coerceIn(0, height - 1)
    }

    protected fun modified(x: Int, y: Int) {
        _mark(x, y)
        flush()
    }
    protected fun modified(rect: RectangleInt) {
        _mark(rect.left, rect.top)
        _mark(rect.right - 1, rect.bottom - 1)
        flush()
    }
    override val width: Int get() = base.width
    override val height: Int get() = base.height
    override fun iterator(): Iterator<E> = base.iterator()
}

abstract class BaseObservableArray1<T, E>(val updated: T.(IntRange) -> Unit) : BaseObservableArray<E>() {
    private var imin = 0
    private var imax = 0

    private val isEmpty get() = imin > imax

    abstract val size: Int

    protected fun coercedRange(r: IntRange): IntRange = r.first.coerceIn(0, size - 1) .. r.last.coerceIn(0, size - 1)

    init {
        reset()
    }

    protected fun reset() {
        imin = Int.MAX_VALUE
        imax = Int.MIN_VALUE
    }

    override fun flush() {
        if (locked.value == 0 && !isEmpty) {
            _version.incrementAndGet()
            updated(this as T, imin..imax)
            reset()
        }
    }
    private fun _modified(idx: Int) {
        imin = minOf(imin, idx)
        imax = maxOf(imax, idx)
    }
    protected fun modified(idx: Int) {
        _modified(idx)
        flush()
    }
    protected fun modified(range: IntRange) {
        _modified(range.first)
        _modified(range.last)
        flush()
    }
}

abstract class BaseObservableArray<E>() {
    val version: Int get() = _version.value
    protected var _version = atomic(0)

    protected abstract fun flush()

    protected var locked = atomic(0)
    inline fun <T> lock(block: () -> T): T {
        lock()
        try {
            return block()
        } finally {
            unlock()
        }
    }

    @PublishedApi internal fun lock() {
        locked.incrementAndGet()
    }
    @PublishedApi internal fun unlock() {
        if (locked.decrementAndGet() <= 0) {
            flush()
        }
    }
}
