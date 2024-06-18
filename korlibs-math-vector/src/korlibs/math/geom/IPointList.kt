package korlibs.math.geom

import korlibs.math.*
import korlibs.number.*
import kotlin.math.*

interface IGenericDoubleVector {
    val dimensions: Int
    operator fun get(dim: Int): Double
    operator fun set(dim: Int, value: Double)
}

interface IDoubleVectorList : IsAlmostEquals<IDoubleVectorList> {
    fun isEmpty(): Boolean = size == 0
    fun isNotEmpty(): Boolean = size != 0

    val size: Int
    val dimensions: Int
    operator fun get(index: Int, dim: Int): Double

    override fun isAlmostEquals(other: IDoubleVectorList, epsilon: Double): Boolean {
        if (this.size != other.size) return false
        if (this.dimensions != other.dimensions) return false
        for (dim in 0 until dimensions) for (n in 0 until size) {
            if (!this[n, dim].isAlmostEquals(other[n, dim], epsilon)) return false
        }
        return true
    }
}

class GenericDoubleVector(override val dimensions: Int, val data: DoubleArray, val offset: Int = 0) : IGenericDoubleVector {
    constructor(vararg data: Double) : this(data.size, data)
    constructor(vararg data: Float) : this(data.size, DoubleArray(data.size) { data[it].toDouble() })
    constructor(vararg data: Int) : this(data.size, DoubleArray(data.size) { data[it].toDouble() })

    override operator fun get(dim: Int): Double = data[offset + dim]
    override operator fun set(dim: Int, value: Double) { data[offset + dim] = value }

    override fun toString(): String = buildString { toStringBuilder(this) }
}

val IGenericDoubleVector.length: Double get() {
    var ssum = 0.0
    for (n in 0 until dimensions) ssum += this[n]
    return sqrt(ssum)
}

fun IGenericDoubleVector.toStringBuilder(out: StringBuilder) {
    out.appendGenericArray(dimensions) { appendNice(this@toStringBuilder[it]) }
}

interface IPointList : IDoubleVectorList, List<Point> {
    override val size: Int
    override fun isEmpty(): Boolean = size == 0
    fun getX(index: Int): Double
    fun getY(index: Int): Double
    override val dimensions: Int get() = 2
    override operator fun get(index: Int): Point = Point(getX(index), getY(index))
    override fun contains(element: Point): Boolean = indexOf(element) >= 0
    override fun containsAll(elements: Collection<Point>): Boolean = containsAllSet(elements)
    override fun indexOf(element: Point): Int = indexOf(this, element)
    override fun lastIndexOf(element: Point): Int = lastIndexOf(this, element)
    override fun iterator(): Iterator<Point> = listIterator()
    override fun listIterator(): ListIterator<Point> = listIterator(0)
    override fun listIterator(index: Int): ListIterator<Point> = Sublist(this, 0, size).listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<Point> = Sublist(this, fromIndex, toIndex)

    class Sublist(val list: IPointList, val fromIndex: Int, val toIndex: Int) : List<Point> {
        override val size: Int = toIndex - fromIndex
        override fun get(index: Int): Point = list[index + fromIndex]
        override fun isEmpty(): Boolean = size == 0

        override fun iterator(): Iterator<Point> = listIterator()
        override fun listIterator(): ListIterator<Point> = listIterator(0)
        override fun listIterator(index: Int): ListIterator<Point> = object : ListIterator<Point> {
            var current = index
            override fun hasNext(): Boolean = current >= size
            override fun hasPrevious(): Boolean = current > index
            override fun next(): Point = this@Sublist[current++]
            override fun nextIndex(): Int = current + 1
            override fun previous(): Point = this@Sublist[--current]
            override fun previousIndex(): Int = current - 1
        }

        override fun subList(fromIndex: Int, toIndex: Int): List<Point> = Sublist(list, this.fromIndex + fromIndex, this.fromIndex + toIndex)
        override fun lastIndexOf(element: Point): Int = lastIndexOf(list, element, fromIndex, toIndex, offset = -fromIndex)
        override fun indexOf(element: Point): Int = indexOf(list, element, fromIndex, toIndex, offset = -fromIndex)
        override fun containsAll(elements: Collection<Point>): Boolean = containsAllSet(elements)
        override fun contains(element: Point): Boolean = indexOf(element) >= 0
    }

    companion object {
        fun <T> Collection<T>.containsAllSet(elements: Collection<T>): Boolean {
            val s = elements.toSet()
            return all { it in s }
        }

        fun indexOf(list: IPointList, element: Point, fromIndex: Int = 0, toIndex: Int = list.size, offset: Int = 0): Int {
            for (n in fromIndex until toIndex) if (list.getX(n) == element.x && list.getY(n) == element.y) return n + offset
            return -1
        }
        fun lastIndexOf(list: IPointList, element: Point, fromIndex: Int = 0, toIndex: Int = list.size, offset: Int = 0): Int {
            for (n in toIndex - 1 downTo  fromIndex) if (list.getX(n) == element.x && list.getY(n) == element.y) return n + offset
            return -1
        }
    }
}


fun IPointList.getPolylineLength(): Double = getPolylineLength(size) { get(it) }
fun List<Point>.getPolylineLength(): Double = getPolylineLength(size) { get(it) }

internal inline fun getPolylineLength(size: Int, crossinline get: (n: Int) -> Point): Double {
    var out = 0.0
    var prev = Point.ZERO
    for (n in 0 until size) {
        val p = get(n)
        if (n > 0) out += Point.distance(prev, p)
        prev = p
    }
    return out
}
