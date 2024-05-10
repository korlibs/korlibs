package korlibs.datastructure

import kotlin.math.*

interface FloatList : Collection<Float> {
    operator fun get(index: Int): Float
    fun getAt(index: Int): Float = this[index]
    fun indexOf(value: Float, start: Int = 0, end: Int = this.size): Int = indexOfCheck(value, start, end) { this[it] }
    fun lastIndexOf(value: Float, start: Int = 0, end: Int = this.size): Int = lastIndexOfCheck(value, start, end) { this[it] }
    fun toFloatArray(): FloatArray = FloatArray(size) { this[it] }
    fun indexOf(element: Float): Int = indexOf(element, 0, size)
    fun lastIndexOf(element: Float): Int = lastIndexOf(element, 0, size)
    fun listIterator(): ListIterator<Float> = listIterator(0)
    fun listIterator(index: Int): ListIterator<Float> = subList(index, size).listIterator()
    fun subList(fromIndex: Int, toIndex: Int): List<Float> = SimpleSubList(fromIndex, toIndex) { this[it] }
    fun clone(): FloatList
    fun isAlmostEquals(other: FloatList, epsilon: Float): Boolean {
        if (this.size != other.size) return false
        for (n in indices) if (!this.getAt(n).isAlmostEquals(other.getAt(n), epsilon)) return false
        return true
    }
    override fun contains(element: Float): Boolean = containsCheck(this, element)
    override fun containsAll(elements: Collection<Float>): Boolean = containsAllCheck(this, elements)
    override fun isEmpty(): Boolean = size == 0
    override fun iterator(): Iterator<Float> = listIterator()
}

private fun Float.isAlmostEquals(other: Float, epsilon: Float = 0.000001f): Boolean = (this - other).absoluteValue < epsilon

fun FloatArray.toImmutableFloatList(): FloatList = object : FloatList {
    override val size: Int get() = this@toImmutableFloatList.size
    override fun get(index: Int): Float = this@toImmutableFloatList[index]
    override fun clone(): FloatList = toFloatArray().toImmutableFloatList()
}
