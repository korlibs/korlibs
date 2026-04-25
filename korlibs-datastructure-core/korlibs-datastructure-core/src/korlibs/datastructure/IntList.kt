package korlibs.datastructure

import korlibs.datastructure.internal.*

interface IntList : Collection<Int> {
    operator fun get(index: Int): Int
    fun getAt(index: Int): Int = this[index]
    fun indexOf(value: Int, start: Int = 0, end: Int = this.size): Int = indexOfCheck(value, start, end) { this[it] }
    fun lastIndexOf(value: Int, start: Int = 0, end: Int = this.size): Int = lastIndexOfCheck(value, start, end) { this[it] }
    fun toIntArray(): IntArray = IntArray(size) { this[it] }
    fun indexOf(element: Int): Int = indexOf(element, 0, size)
    fun lastIndexOf(element: Int): Int = lastIndexOf(element, 0, size)
    fun listIterator(): ListIterator<Int> = listIterator(0)
    fun listIterator(index: Int): ListIterator<Int> = subList(index, size).listIterator()
    fun subList(fromIndex: Int, toIndex: Int): List<Int> = SimpleSubList(fromIndex, toIndex) { this[it] }
    fun clone(): IntList

    override fun contains(element: Int): Boolean = containsCheck(this, element)
    override fun containsAll(elements: Collection<Int>): Boolean = containsAllCheck(this, elements)
    override fun isEmpty(): Boolean = size == 0
    override fun iterator(): Iterator<Int> = listIterator()
}

fun IntArray.toImmutableIntList(): IntList = object : IntList {
    override val size: Int get() = this@toImmutableIntList.size
    override fun get(index: Int): Int = this@toImmutableIntList[index]
    override fun clone(): IntList = toIntArray().toImmutableIntList()
}
