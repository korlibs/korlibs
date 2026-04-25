@file:Suppress("DuplicatedCode")

package korlibs.datastructure

import korlibs.datastructure.IArray2.Companion.forEachPosRect
import korlibs.math.geom.*
import korlibs.memory.*
import korlibs.number.*

inline fun <TGen : Any, RGen : Any> IArray2<TGen>.map2(gen: (x: Int, y: Int, v: TGen) -> RGen) =
    Array2<RGen>(width, height) {
        val x = it % width
        val y = it / width
        gen(x, y, this.getAt(x, y))
    }

inline fun IntArray2.map2(gen: (x: Int, y: Int, v: Int) -> Int): IntArray2 =
    IntArray2(width, height) {
        val x = it % width
        val y = it / width
        gen(x, y, this[x, y])
    }

inline fun FloatArray2.map2(gen: (x: Int, y: Int, v: Float) -> Float): FloatArray2 =
    FloatArray2(width, height) {
        val x = it % width
        val y = it / width
        gen(x, y, this[x, y])
    }

inline fun DoubleArray2.map2(gen: (x: Int, y: Int, v: Double) -> Double): DoubleArray2 =
    DoubleArray2(width, height) {
        val x = it % width
        val y = it / width
        gen(x, y, this[x, y])
    }

// typealias BitIArray2 = IArray2<Bit>
interface BooleanIArray2 : IArray2<Boolean> {
    fun setFast(idx: Int, value: Boolean)
    fun getFast(idx: Int): Boolean
    operator fun get(x: Int, y: Int): Boolean = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Boolean) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Boolean) = setFast(idx, value)
    override fun getAt(idx: Int): Boolean = getFast(idx)

    operator fun get(p: PointInt): Boolean = get(p.x, p.y)
    operator fun set(p: PointInt, value: Boolean) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Boolean) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface ByteIArray2 : IArray2<Byte> {
    fun setFast(idx: Int, value: Byte)
    fun getFast(idx: Int): Byte
    operator fun get(x: Int, y: Int): Byte = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Byte) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Byte) = setFast(idx, value)
    override fun getAt(idx: Int): Byte = getFast(idx)

    operator fun get(p: PointInt): Byte = get(p.x, p.y)
    operator fun set(p: PointInt, value: Byte) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Byte) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface ShortIArray2 : IArray2<Short> {
    fun setFast(idx: Int, value: Short)
    fun getFast(idx: Int): Short
    operator fun get(x: Int, y: Int): Short = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Short) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Short) = setFast(idx, value)
    override fun getAt(idx: Int): Short = getFast(idx)

    operator fun get(p: PointInt): Short = get(p.x, p.y)
    operator fun set(p: PointInt, value: Short) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Short) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface CharIArray2 : IArray2<Char> {
    fun setFast(idx: Int, value: Char)
    fun getFast(idx: Int): Char
    operator fun get(x: Int, y: Int): Char = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Char) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Char) = setFast(idx, value)
    override fun getAt(idx: Int): Char = getFast(idx)

    operator fun get(p: PointInt): Char = get(p.x, p.y)
    operator fun set(p: PointInt, value: Char) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Char) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface IntIArray2 : IArray2<Int> {
    fun setFast(idx: Int, value: Int)
    fun getFast(idx: Int): Int
    operator fun get(x: Int, y: Int): Int = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Int) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Int) = setFast(idx, value)
    override fun getAt(idx: Int): Int = getFast(idx)

    operator fun get(p: PointInt): Int = get(p.x, p.y)
    operator fun set(p: PointInt, value: Int) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Int) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface LongIArray2 : IArray2<Long> {
    fun setFast(idx: Int, value: Long)
    fun getFast(idx: Int): Long
    operator fun get(x: Int, y: Int): Long = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Long) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Long) = setFast(idx, value)
    override fun getAt(idx: Int): Long = getFast(idx)

    operator fun get(p: PointInt): Long = get(p.x, p.y)
    operator fun set(p: PointInt, value: Long) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Long) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface FloatIArray2 : IArray2<Float> {
    fun setFast(idx: Int, value: Float)
    fun getFast(idx: Int): Float
    operator fun get(x: Int, y: Int): Float = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Float) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Float) = setFast(idx, value)
    override fun getAt(idx: Int): Float = getFast(idx)

    operator fun get(p: PointInt): Float = get(p.x, p.y)
    operator fun set(p: PointInt, value: Float) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Float) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface DoubleIArray2 : IArray2<Double> {
    fun setFast(idx: Int, value: Double)
    fun getFast(idx: Int): Double
    operator fun get(x: Int, y: Int): Double = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Double) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Double) = setFast(idx, value)
    override fun getAt(idx: Int): Double = getFast(idx)

    operator fun get(p: PointInt): Double = get(p.x, p.y)
    operator fun set(p: PointInt, value: Double) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Double) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface Int64IArray2 : IArray2<Int64> {
    fun setFast(idx: Int, value: Int64)
    fun getFast(idx: Int): Int64
    operator fun get(x: Int, y: Int): Int64 = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Int64) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Int64) = setFast(idx, value)
    override fun getAt(idx: Int): Int64 = getFast(idx)

    operator fun get(p: PointInt): Int64 = get(p.x, p.y)
    operator fun set(p: PointInt, value: Int64) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Int64) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}
interface Int53IArray2 : IArray2<Int53> {
    fun setFast(idx: Int, value: Int53)
    fun getFast(idx: Int): Int53
    operator fun get(x: Int, y: Int): Int53 = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: Int53) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: Int53) = setFast(idx, value)
    override fun getAt(idx: Int): Int53 = getFast(idx)

    operator fun get(p: PointInt): Int53 = get(p.x, p.y)
    operator fun set(p: PointInt, value: Int53) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: Int53) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}

interface TGenIArray2<TGen> : IArray2<TGen> {
    fun setFast(idx: Int, value: TGen)
    fun getFast(idx: Int): TGen
    operator fun get(x: Int, y: Int): TGen = getFast(indexOr(x, y))
    operator fun set(x: Int, y: Int, value: TGen) = setFast(indexOr(x, y), value)
    override fun setAt(idx: Int, value: TGen) = setFast(idx, value)
    override fun getAt(idx: Int): TGen = getFast(idx)

    operator fun get(p: PointInt): TGen = get(p.x, p.y)
    operator fun set(p: PointInt, value: TGen) = set(p.x, p.y, value)
    operator fun set(rect: RectangleInt, value: TGen) = forEachPosRect(this, rect) { x, y -> this[x, y] = value }
}

// @NOTE: AUTOGENERATED: ONLY MODIFY FROM  GENERIC TEMPLATE to END OF GENERIC TEMPLATE
// Then use ./gradlew generate to regenerate the rest of the file.

// GENERIC TEMPLATE //////////////////////////////////////////

@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class Array2<TGen>(override val width: Int, override val height: Int, val data: Array<TGen>) : TGenIArray2<TGen> {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun <TGen : Any> invoke(width: Int, height: Int, fill: TGen): Array2<TGen> =
            Array2<TGen>(width, height, Array<Any>(width * height) { fill } as Array<TGen>)

        inline operator fun <TGen : Any> invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> TGen
        ): Array2<TGen> =
            Array2<TGen>(width, height, Array<Any>(width * height) { gen(it) } as Array<TGen>)

        inline fun <TGen : Any> withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> TGen
        ): Array2<TGen> =
            Array2<TGen>(
                width,
                height,
                Array<Any>(width * height) { gen(it % width, it / width) } as Array<TGen>)

        inline operator fun <TGen : Any> invoke(rows: List<List<TGen>>): Array2<TGen> {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (Array2<TGen>(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun <TGen : Any> invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> TGen
        ): Array2<TGen> {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return Array2<TGen>(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun <TGen : Any> invoke(
            map: String,
            default: TGen,
            transform: Map<Char, TGen>
        ): Array2<TGen> {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun <TGen : Any> fromString(
            maps: Map<Char, TGen>,
            default: TGen,
            code: String,
            marginChar: Char = '\u0000'
        ): Array2<TGen> {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun getFast(idx: Int): TGen = data.getOrElse(idx) { data[0] }
    override fun setFast(idx: Int, value: TGen) { if (idx in data.indices) data[idx] = value }
    override fun printAt(idx: Int) = print(this.data[idx])
    override fun equals(other: Any?): Boolean = (other is Array2<*/*TGen*/>) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)
    override fun hashCode(): Int = width + height + data.contentHashCode()
    fun clone() = Array2<TGen>(width, height, data.copyOf())
    override fun iterator(): Iterator<TGen> = data.iterator()
    override fun toString(): String = asString()
}

// END OF GENERIC TEMPLATE ///////////////////////////////////

// AUTOGENERATED: DO NOT MODIFY MANUALLY STARTING FROM HERE!

// Int


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class IntArray2(override val width: Int, override val height: Int, val data: IntArray) : IntIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Int): IntArray2 =
            IntArray2(width, height, IntArray(width * height) { fill } as IntArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Int
        ): IntArray2 =
            IntArray2(width, height, IntArray(width * height) { gen(it) } as IntArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Int
        ): IntArray2 =
            IntArray2(
                width,
                height,
                IntArray(width * height) { gen(it % width, it / width) } as IntArray)

        inline operator fun  invoke(rows: List<List<Int>>): IntArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (IntArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Int
        ): IntArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return IntArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Int,
            transform: Map<Char, Int>
        ): IntArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Int>,
            default: Int,
            code: String,
            marginChar: Char = '\u0000'
        ): IntArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun printAt(idx: Int) = print(this.data[idx])
    override fun equalsAt(idx: Int, value: Int): Boolean = this.data[idx]?.equals(value) ?: false

    override fun equals(other: Any?): Boolean = (other is IntArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)

    override fun getFast(idx: Int): Int = data.getOrElse(idx) { data[0] }
    override fun setFast(idx: Int, value: Int) { if (idx in data.indices) data[idx] = value }

    override fun hashCode(): Int = width + height + data.contentHashCode()

    fun clone() = IntArray2(width, height, data.copyOf())

    override fun iterator(): Iterator<Int> = data.iterator()

    override fun toString(): String = asString()
}



// Double


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class DoubleArray2(override val width: Int, override val height: Int, val data: DoubleArray) : DoubleIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Double): DoubleArray2 =
            DoubleArray2(width, height, DoubleArray(width * height) { fill } as DoubleArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Double
        ): DoubleArray2 =
            DoubleArray2(width, height, DoubleArray(width * height) { gen(it) } as DoubleArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Double
        ): DoubleArray2 =
            DoubleArray2(
                width,
                height,
                DoubleArray(width * height) { gen(it % width, it / width) } as DoubleArray)

        inline operator fun  invoke(rows: List<List<Double>>): DoubleArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (DoubleArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Double
        ): DoubleArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return DoubleArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Double,
            transform: Map<Char, Double>
        ): DoubleArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Double>,
            default: Double,
            code: String,
            marginChar: Char = '\u0000'
        ): DoubleArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is DoubleArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(
            other.data
        )
    }

    override fun getFast(idx: Int): Double = data.getOrElse(idx) { 0.0 }
    override fun setFast(idx: Int, value: Double) { if (idx in data.indices) data[idx] = value }
    override fun hashCode(): Int = width + height + data.contentHashCode()
    fun clone() = DoubleArray2(width, height, data.copyOf())
    override fun iterator(): Iterator<Double> = data.iterator()
    override fun toString(): String = asString()
}

// Int64


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class Int64Array2(override val width: Int, override val height: Int, val data: Int64Array) : Int64IArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Int64): Int64Array2 =
            Int64Array2(width, height, Int64Array(width * height) { fill } as Int64Array)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Int64
        ): Int64Array2 =
            Int64Array2(width, height, Int64Array(width * height) { gen(it) } as Int64Array)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Int64
        ): Int64Array2 =
            Int64Array2(
                width,
                height,
                Int64Array(width * height) { gen(it % width, it / width) } as Int64Array)

        inline operator fun  invoke(rows: List<List<Int64>>): Int64Array2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (Int64Array2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Int64
        ): Int64Array2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return Int64Array2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Int64,
            transform: Map<Char, Int64>
        ): Int64Array2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Int64>,
            default: Int64,
            code: String,
            marginChar: Char = '\u0000'
        ): Int64Array2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is Int64Array2) && this.width == other.width && this.height == other.height && this.data.contentEquals(
            other.data
        )
    }

    override fun getFast(idx: Int): Int64 = data.getOrElse(idx) { Int64.ZERO }
    override fun setFast(idx: Int, value: Int64) { if (idx in 0 until data.size) data[idx] = value }
    override fun hashCode(): Int = width + height + data.contentHashCode()
    fun clone() = Int64Array2(width, height, data.copyOf())
    override fun iterator(): Iterator<Int64> = data.iterator()
    override fun toString(): String = asString()
}

// Int53


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class Int53Array2(override val width: Int, override val height: Int, val data: Int53Array) : Int53IArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Int53): Int53Array2 =
            Int53Array2(width, height, Int53Array(width * height) { fill } as Int53Array)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Int53
        ): Int53Array2 =
            Int53Array2(width, height, Int53Array(width * height) { gen(it) } as Int53Array)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Int53
        ): Int53Array2 =
            Int53Array2(
                width,
                height,
                Int53Array(width * height) { gen(it % width, it / width) } as Int53Array)

        inline operator fun  invoke(rows: List<List<Int53>>): Int53Array2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (Int53Array2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Int53
        ): Int53Array2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return Int53Array2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Int53,
            transform: Map<Char, Int53>
        ): Int53Array2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Int53>,
            default: Int53,
            code: String,
            marginChar: Char = '\u0000'
        ): Int53Array2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is Int53Array2) && this.width == other.width && this.height == other.height && this.data.contentEquals(
            other.data
        )
    }

    override fun getFast(idx: Int): Int53 = data.getOrElse(idx) { Int53.ZERO }
    override fun setFast(idx: Int, value: Int53) { if (idx in 0 until data.size) data[idx] = value }
    override fun hashCode(): Int = width + height + data.contentHashCode()
    fun clone() = Int53Array2(width, height, data.copyOf())
    override fun iterator(): Iterator<Int53> = data.iterator()
    override fun toString(): String = asString()
}

// Float


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class FloatArray2(override val width: Int, override val height: Int, val data: FloatArray) : FloatIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Float): FloatArray2 =
            FloatArray2(width, height, FloatArray(width * height) { fill } as FloatArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Float
        ): FloatArray2 =
            FloatArray2(width, height, FloatArray(width * height) { gen(it) } as FloatArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Float
        ): FloatArray2 =
            FloatArray2(
                width,
                height,
                FloatArray(width * height) { gen(it % width, it / width) } as FloatArray)

        inline operator fun  invoke(rows: List<List<Float>>): FloatArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (FloatArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Float
        ): FloatArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return FloatArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Float,
            transform: Map<Char, Float>
        ): FloatArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Float>,
            default: Float,
            code: String,
            marginChar: Char = '\u0000'
        ): FloatArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun equals(other: Any?): Boolean = (other is FloatArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)

    override fun getFast(idx: Int): Float = data.getOrElse(idx) { 0f }
    override fun setFast(idx: Int, value: Float) { if (idx in data.indices) data[idx] = value }
    override fun hashCode(): Int = width + height + data.contentHashCode()
    fun clone() = FloatArray2(width, height, data.copyOf())
    override fun iterator(): Iterator<Float> = data.iterator()
    override fun toString(): String = asString()
}



// Byte


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class ByteArray2(override val width: Int, override val height: Int, val data: ByteArray) : ByteIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Byte): ByteArray2 =
            ByteArray2(width, height, ByteArray(width * height) { fill } as ByteArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Byte
        ): ByteArray2 =
            ByteArray2(width, height, ByteArray(width * height) { gen(it) } as ByteArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Byte
        ): ByteArray2 =
            ByteArray2(
                width,
                height,
                ByteArray(width * height) { gen(it % width, it / width) } as ByteArray)

        inline operator fun  invoke(rows: List<List<Byte>>): ByteArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (ByteArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Byte
        ): ByteArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return ByteArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Byte,
            transform: Map<Char, Byte>
        ): ByteArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Byte>,
            default: Byte,
            code: String,
            marginChar: Char = '\u0000'
        ): ByteArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun printAt(idx: Int) = print(this.data[idx])
    override fun equalsAt(idx: Int, value: Byte): Boolean = this.data[idx]?.equals(value) ?: false
    override fun equals(other: Any?): Boolean = (other is ByteArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)

    override fun getFast(idx: Int): Byte = data.getOrElse(idx) { 0 }
    override fun setFast(idx: Int, value: Byte) { if (idx in data.indices) data[idx] = value }

    override fun hashCode(): Int = width + height + data.contentHashCode()

    fun clone() = ByteArray2(width, height, data.copyOf())

    override fun iterator(): Iterator<Byte> = data.iterator()

    override fun toString(): String = asString()
}



// Char


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class CharArray2(override val width: Int, override val height: Int, val data: CharArray) : CharIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Char): CharArray2 =
            CharArray2(width, height, CharArray(width * height) { fill } as CharArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Char
        ): CharArray2 =
            CharArray2(width, height, CharArray(width * height) { gen(it) } as CharArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Char
        ): CharArray2 =
            CharArray2(
                width,
                height,
                CharArray(width * height) { gen(it % width, it / width) } as CharArray)

        inline operator fun  invoke(rows: List<List<Char>>): CharArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (CharArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Char
        ): CharArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return CharArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Char,
            transform: Map<Char, Char>
        ): CharArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Char>,
            default: Char,
            code: String,
            marginChar: Char = '\u0000'
        ): CharArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun printAt(idx: Int) = print(this.data[idx])

    override fun equalsAt(idx: Int, value: Char): Boolean = (this.data[idx] == value) ?: false

    override fun equals(other: Any?): Boolean = (other is CharArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)

    override fun getFast(idx: Int): Char = data.getOrElse(idx) { data[0] }
    override fun setFast(idx: Int, value: Char) { if (idx in data.indices) data[idx] = value }

    override fun hashCode(): Int = width + height + data.contentHashCode()

    fun clone() = CharArray2(width, height, data.copyOf())

    override fun iterator(): Iterator<Char> = data.iterator()

    override fun toString(): String = asString()
}



// Short


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class ShortArray2(override val width: Int, override val height: Int, val data: ShortArray) : ShortIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Short): ShortArray2 =
            ShortArray2(width, height, ShortArray(width * height) { fill } as ShortArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Short
        ): ShortArray2 =
            ShortArray2(width, height, ShortArray(width * height) { gen(it) } as ShortArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Short
        ): ShortArray2 =
            ShortArray2(
                width,
                height,
                ShortArray(width * height) { gen(it % width, it / width) } as ShortArray)

        inline operator fun  invoke(rows: List<List<Short>>): ShortArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (ShortArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Short
        ): ShortArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return ShortArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Short,
            transform: Map<Char, Short>
        ): ShortArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Short>,
            default: Short,
            code: String,
            marginChar: Char = '\u0000'
        ): ShortArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun printAt(idx: Int) = print(this.data[idx])
    override fun equalsAt(idx: Int, value: Short): Boolean = this.data[idx]?.equals(value) ?: false

    override fun equals(other: Any?): Boolean {
        return (other is ShortArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(
            other.data
        )
    }

    override fun getFast(idx: Int): Short = data.getOrElse(idx) { 0 }
    override fun setFast(idx: Int, value: Short) { if (idx in data.indices) data[idx] = value }

    override fun hashCode(): Int = width + height + data.contentHashCode()

    fun clone() = ShortArray2(width, height, data.copyOf())

    override fun iterator(): Iterator<Short> = data.iterator()

    override fun toString(): String = asString()
}



// Long


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class LongArray2(override val width: Int, override val height: Int, val data: LongArray) : LongIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Long): LongArray2 =
            LongArray2(width, height, LongArray(width * height) { fill } as LongArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Long
        ): LongArray2 =
            LongArray2(width, height, LongArray(width * height) { gen(it) } as LongArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Long
        ): LongArray2 =
            LongArray2(
                width,
                height,
                LongArray(width * height) { gen(it % width, it / width) } as LongArray)

        inline operator fun  invoke(rows: List<List<Long>>): LongArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (LongArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Long
        ): LongArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return LongArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Long,
            transform: Map<Char, Long>
        ): LongArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Long>,
            default: Long,
            code: String,
            marginChar: Char = '\u0000'
        ): LongArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun equals(other: Any?): Boolean = (other is LongArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)

    override fun getFast(idx: Int): Long = data.getOrElse(idx) { 0L }
    override fun setFast(idx: Int, value: Long) { if (idx in data.indices) data[idx] = value }

    override fun hashCode(): Int = width + height + data.contentHashCode()

    fun clone() = LongArray2(width, height, data.copyOf())

    override fun iterator(): Iterator<Long> = data.iterator()

    override fun toString(): String = asString()
}



// Boolean


@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
open class BooleanArray2(override val width: Int, override val height: Int, val data: BooleanArray) : BooleanIArray2 {
    init {
        IArray2.checkArraySize(width, height, data.size)
    }
    companion object {
        inline operator fun  invoke(width: Int, height: Int, fill: Boolean): BooleanArray2 =
            BooleanArray2(width, height, BooleanArray(width * height) { fill } as BooleanArray)

        inline operator fun  invoke(
            width: Int,
            height: Int,
            gen: (n: Int) -> Boolean
        ): BooleanArray2 =
            BooleanArray2(width, height, BooleanArray(width * height) { gen(it) } as BooleanArray)

        inline fun  withGen(
            width: Int,
            height: Int,
            gen: (x: Int, y: Int) -> Boolean
        ): BooleanArray2 =
            BooleanArray2(
                width,
                height,
                BooleanArray(width * height) { gen(it % width, it / width) } as BooleanArray)

        inline operator fun  invoke(rows: List<List<Boolean>>): BooleanArray2 {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (BooleanArray2(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun  invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> Boolean
        ): BooleanArray2 {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.maxOrNull() ?: 0
            val height = lines.size

            return BooleanArray2(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun  invoke(
            map: String,
            default: Boolean,
            transform: Map<Char, Boolean>
        ): BooleanArray2 {
            return invoke(map) { c, _, _ -> transform[c] ?: default }
        }

        inline fun  fromString(
            maps: Map<Char, Boolean>,
            default: Boolean,
            code: String,
            marginChar: Char = '\u0000'
        ): BooleanArray2 {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    override fun printAt(idx: Int) = print(this.data[idx])
    override fun equalsAt(idx: Int, value: Boolean): Boolean = this.data[idx]?.equals(value) ?: false
    override fun equals(other: Any?): Boolean = (other is BooleanArray2) && this.width == other.width && this.height == other.height && this.data.contentEquals(other.data)

    override fun getFast(idx: Int): Boolean = data.getOrElse(idx) { false }
    override fun setFast(idx: Int, value: Boolean) { if (idx in data.indices) data[idx] = value }
    override fun hashCode(): Int = width + height + data.contentHashCode()
    fun clone() = BooleanArray2(width, height, data.copyOf())
    override fun iterator(): Iterator<Boolean> = data.iterator()
    override fun toString(): String = asString()
}
