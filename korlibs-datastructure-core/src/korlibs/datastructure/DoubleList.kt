package korlibs.datastructure

import kotlin.math.*

interface DoubleList : Collection<Double> {
    operator fun get(index: Int): Double
    fun getAt(index: Int): Double = this[index]
    fun indexOf(value: Double, start: Int = 0, end: Int = this.size): Int = indexOfCheck(value, start, end) { this[it] }
    fun lastIndexOf(value: Double, start: Int = 0, end: Int = this.size): Int = lastIndexOfCheck(value, start, end) { this[it] }
    fun toDoubleArray(): DoubleArray = DoubleArray(size) { this[it] }
    fun indexOf(element: Double): Int = indexOf(element, 0, size)
    fun lastIndexOf(element: Double): Int = lastIndexOf(element, 0, size)
    fun listIterator(): ListIterator<Double> = listIterator(0)
    fun listIterator(index: Int): ListIterator<Double> = subList(index, size).listIterator()
    fun subList(fromIndex: Int, toIndex: Int): List<Double> = SimpleSubList(fromIndex, toIndex) { this[it] }
    fun clone(): DoubleList
    fun isAlmostEquals(other: DoubleList, epsilon: Double): Boolean {
        if (this.size != other.size) return false
        for (n in indices) if (!this.getAt(n).isAlmostEquals(other.getAt(n), epsilon)) return false
        return true
    }
    override fun contains(element: Double): Boolean = containsCheck(this, element)
    override fun containsAll(elements: Collection<Double>): Boolean = containsAllCheck(this, elements)
    override fun isEmpty(): Boolean = size == 0
    override fun iterator(): Iterator<Double> = listIterator()
}

private fun Double.isAlmostEquals(other: Double, epsilon: Double = 0.000001): Boolean = (this - other).absoluteValue < epsilon

fun DoubleArray.toImmutableDoubleList(): DoubleList = object : DoubleList {
    override val size: Int get() = this@toImmutableDoubleList.size
    override fun get(index: Int): Double = this@toImmutableDoubleList[index]
    override fun clone(): DoubleList = toDoubleArray().toImmutableDoubleList()
}
