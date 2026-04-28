package korlibs.datastructure

fun <T> SimpleSubList(fromIndex: Int, toIndex: Int, get: (index: Int) -> T): List<T> = object : AbstractList<T>() {
    override val size: Int get() = toIndex - fromIndex
    override fun get(index: Int): T {
        if (index !in 0 until size) throw IndexOutOfBoundsException()
        return get(index + fromIndex)
    }
}
