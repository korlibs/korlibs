package korlibs.simple

class SimpleList : List<Int> {
    override val size: Int = 1
    override fun get(index: Int): Int = 0
    override fun isEmpty(): Boolean = false
    override fun iterator(): Iterator<Int> = listIterator(0)
    override fun listIterator(): ListIterator<Int> = listIterator(0)
    override fun listIterator(index: Int): ListIterator<Int> = List(1) { 0 }.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<Int> = SimpleList()
    override fun lastIndexOf(element: Int): Int = if (element == 0) 0 else -1
    override fun indexOf(element: Int): Int = if (element == 0) 0 else -1
    override fun containsAll(elements: Collection<Int>): Boolean {
        elements.toSet().forEach { if (!contains(it)) return false }
        return true
    }
    override fun contains(element: Int): Boolean = (0 until size).any { this[it] == element }
}