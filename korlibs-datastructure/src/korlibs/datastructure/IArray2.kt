package korlibs.datastructure

import korlibs.math.geom.*

// Note: Due to autoboxing, the get()/set() methods are implemented in the typed implementations
// without an override (meaning we don't require the interface to have get/set methods).
//
// https://discuss.kotlinlang.org/t/performance-question-related-to-boxing-and-interface-implementation/17387
interface IArray2<E> : Iterable<E> {
    companion object {
        fun checkArraySize(width: Int, height: Int, arraySize: Int) {
            check(arraySize >= width * height) { "backing array of size=$arraySize, has less elements than $width * $height" }
        }

        inline fun <E> forEachPosRect(array: IArray2<E>, rect: RectangleInt, block: (x: Int, y: Int) -> Unit) {
            val l = rect.left.coerceIn(0, array.width)
            val r = rect.right.coerceIn(0, array.width)
            val u = rect.top.coerceIn(0, array.height)
            val d = rect.bottom.coerceIn(0, array.height)
            for (x in l until r) {
                for (y in u until d) {
                    block(x, y)
                }
            }
        }
    }
    val width: Int
    val height: Int

    val size: Int
        get() = width * height

    fun inside(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < width && y < height

    // Prints the value at the given index.
    fun printAt(idx: Int) = print(getAt(idx))

    fun printAt(x: Int, y: Int) = printAt(indexOr(x, y))

    fun setAt(idx: Int, value: E)
    fun equalsAt(idx: Int, value: E): Boolean = getAt(idx) == value // Returns true if the value at `idx` equals the `value`.
    fun getAt(idx: Int): E

    fun tryGet(x: Int, y: Int): E? = if (inside(x, y)) getAt(x, y) else null
    fun trySet(x: Int, y: Int, value: E) {
        if (inside(x, y)) setAt(x, y, value)
    }

    fun getAt(x: Int, y: Int): E = if (inside(x, y)) getAt(indexOr(x, y)) else getAt(0)

    fun setAt(x: Int, y: Int, value: E) {
        setAt(indexOr(x, y), value)
    }

    fun set(rows: List<List<E>>) {
        var n = 0
        for (y in rows.indices) {
            val row = rows[y]
            for (x in row.indices) {
                setAt(n++, row[x])
            }
        }
    }

    fun setAt(rect: RectangleInt, value: E) = forEachPosRect(this, rect) { x, y -> this.setAt(x, y, value) }

    operator fun contains(v: E): Boolean = this.iterator().asSequence().any { it == v }

    fun getPositionsWithValue(value: E) =
        (0 until size).filter { equalsAt(it, value) }.map { Pair(it % width, it / width) }

    fun dump() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                printAt(x, y)
            }
            println()
        }
    }

    override fun iterator(): Iterator<E> = (0 until width * height).map { getAt(it) }.listIterator()
    //override fun iterator(): kotlin.collections.Iterator<E> = TODO()

    fun toStringList(charMap: (E) -> Char, margin: String = ""): List<String> = (0 until height).map { y ->
        margin + CharArray(width) { x -> charMap(getAt(x, y)) }.concatToString()
    }

    fun asString(margin: String = "", charMap: (E) -> Char): String =
        toStringList(charMap, margin = margin).joinToString("\n")

    fun asString(map: Map<E, Char>, margin: String = ""): String =
        asString(margin = margin) { map[it] ?: ' ' }

    fun asString(): String = (0 until height)
        .joinToString("\n") { y ->
            (0 until width).map { x -> getAt(x, y) }.joinToString(", ")
        }

}

fun <E> IArray2<E>.getAt(p: PointInt): E = this.getAt(p.x, p.y)
fun <E> IArray2<E>.setAt(p: PointInt, v: E) { this.setAt(p.x, p.y, v) }

inline fun <E> IArray2<E>.fill(gen: (old: E) -> E) {
    var n = 0
    for (y in 0 until height) {
        for (x in 0 until width) {
            setAt(n, gen(getAt(n)))
            n++
        }
    }
}

inline fun <E> IArray2<E>.each(callback: (x: Int, y: Int, v: E) -> Unit) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            callback(x, y, getAt(x, y))
        }
    }
}

fun <E> IArray2<E>.indexOrThrow(x: Int, y: Int): Int {
    if ((x !in 0 until width) || (y !in 0 until height)) throw IndexOutOfBoundsException()
    return y * width + x
}

@Deprecated("", ReplaceWith("indexOrThrow(x, y)"))
fun <E> IArray2<E>.index(x: Int, y: Int): Int = indexOrThrow(x, y)

fun <E> IArray2<E>.indexOr(x: Int, y: Int, invalid: Int = -1): Int {
    if ((x !in 0 until width) || (y !in 0 until height)) return invalid
    return y * width + x
}

fun <E> IArray2<E>.revIndexX(index: Int): Int = index % width
fun <E> IArray2<E>.revIndexY(index: Int): Int = index / width
