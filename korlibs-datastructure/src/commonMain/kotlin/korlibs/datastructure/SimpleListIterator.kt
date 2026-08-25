package korlibs.datastructure

fun <T> SimpleListIterator(fromIndex: Int, toIndex: Int, get: (index: Int) -> T) : ListIterator<T> =
    SimpleSubList(fromIndex, toIndex, get).listIterator()
