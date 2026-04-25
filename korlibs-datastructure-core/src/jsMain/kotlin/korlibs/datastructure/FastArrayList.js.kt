package korlibs.datastructure

public actual open class FastArrayList<E> internal constructor(@PublishedApi internal val __array: Array<E>) : AbstractMutableList<E>(),
    MutableListEx<E>, RandomAccess {
    @PublishedApi inline internal val jsArray: JsArray<E> get() = __array.unsafeCast<JsArray<E>>()
    public actual constructor() : this(emptyArray())
    @Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
    public actual constructor(initialCapacity: Int) : this(emptyArray())
    public actual constructor(elements: Collection<E>) : this(elements.toTypedArray<Any?>().unsafeCast<Array<E>>()) {}
    public actual fun trimToSize() {}
    public actual fun ensureCapacity(minCapacity: Int) {}

    actual override val size: Int get() = jsArray.length
    @Suppress("UNCHECKED_CAST")
    actual override fun get(index: Int): E = jsArray[rangeCheck(index)]
    actual override fun set(index: Int, element: E): E {
        rangeCheck(index)
        @Suppress("UNCHECKED_CAST")
        return jsArray[index].apply { jsArray[index] = element }
    }

    actual override fun add(element: E): Boolean {
        jsArray.push(element)
        modCount++
        return true
    }

    actual override fun add(index: Int, element: E) {
        jsArray.splice(insertionRangeCheck(index), 0, element)
        modCount++
    }

    private fun _addAll(elements: Array<E>): Boolean {
        if (elements.isEmpty()) return false
        jsArray.push(*elements)
        modCount++
        return true
    }

    override fun addAll(elements: FastArrayList<E>): Boolean = _addAll(elements.jsArray.unsafeCast<Array<E>>())
    actual override fun addAll(elements: Collection<E>): Boolean = _addAll(elements.toTypedArray())

    actual override fun addAll(index: Int, elements: Collection<E>): Boolean {
        insertionRangeCheck(index)
        if (elements.isEmpty()) return false
        jsArray.splice(index, 0, *elements.toTypedArray())
        modCount++
        return true
    }

    actual override fun removeAt(index: Int): E {
        rangeCheck(index)
        modCount++
        return when (index) {
            0 -> jsArray.shift()
            lastIndex -> jsArray.pop()
            else -> jsArray.splice(index, 1).unsafeCast<Array<E>>()[0]
        }
    }

    actual override fun remove(element: E): Boolean {
        val array = this.jsArray
        for (index in 0 until array.length) {
            if (array[index] == element) {
                array.splice(index, 1)
                modCount++
                return true
            }
        }
        return false
    }

    actual override fun removeRange(fromIndex: Int, toIndex: Int) {
        jsArray.splice(fromIndex, toIndex - fromIndex)
        modCount++
    }

    actual override fun clear() {
        jsArray.length = 0
        modCount++
    }

    actual override fun indexOf(element: E): Int = jsArray.indexOf(element)

    actual override fun lastIndexOf(element: E): Int = jsArray.lastIndexOf(element)

    override fun toString() = "[" + jsArray.unsafeCast<Array<E>>().joinToString(", ") + "]"
    override fun toArray(): Array<Any?> = jsArray.concat().unsafeCast<Array<Any?>>()

    actual inline fun fastForEach(callback: (E) -> Unit) {
        val array = this.jsArray
        var n = 0
        while (n < array.length) {
            callback(array[n++].unsafeCast<E>())
        }
    }

    actual inline fun fastForEachWithIndex(callback: (index: Int, value: E) -> Unit) {
        val array = this.jsArray
        var n = 0
        while (n < array.length) {
            callback(n, array[n].unsafeCast<E>())
            n++
        }
    }

    actual inline fun fastForEachReverse(callback: (E) -> Unit) {
        val array = this.jsArray
        var n = 0
        while (n < array.length) {
            callback(array[size - n - 1].unsafeCast<E>())
            n++
        }
    }

    actual inline fun fastForEachReverseWithIndex(callback: (index: Int, value: E) -> Unit) {
        val array = this.jsArray
        var n = 0
        while (n < array.length) {
            val index = array.length - n - 1
            callback(index, array[index].unsafeCast<E>())
            n++
        }
    }

    private fun rangeCheck(index: Int): Int {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("index: $index, size: $size")
        return index
    }

    private fun insertionRangeCheck(index: Int): Int {
        if (index < 0 || index > size) throw IndexOutOfBoundsException("index: $index, size: $size")
        return index
    }
}

@JsName("Array")
@PublishedApi internal external class JsArray<T> {
    var length: Int
    //@nativeGetter operator fun get(index: Int): T = definedExternally
    //@nativeSetter operator fun set(index: Int, value: T): T = definedExternally
    fun concat(vararg arrays: JsArray<T>): JsArray<T>
    fun indexOf(e: T): Int
    fun lastIndexOf(e: T): Int
    fun splice(start: Int, deleteCount: Int, vararg items: T): JsArray<T>
    fun push(vararg items: T)
    fun shift(): T
    fun pop(): T

    companion object {
        fun from(value: dynamic): Array<dynamic>
    }
}

@PublishedApi internal fun Array_from(value: dynamic): Array<dynamic> = JsArray.from(value)
@PublishedApi internal inline operator fun <T> JsArray<T>.get(index: Int): T = asDynamic()[index]
@PublishedApi internal inline operator fun <T> JsArray<T>.set(index: Int, value: T) {
    asDynamic()[index] = value
}
