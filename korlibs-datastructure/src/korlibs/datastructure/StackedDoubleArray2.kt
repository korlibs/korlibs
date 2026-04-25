package korlibs.datastructure

import korlibs.datastructure.internal.memory.Memory.arraycopy
import korlibs.datastructure.iterators.*
import korlibs.math.geom.*

interface IStackedDoubleArray2 : IStackedArray2<Double> {
    /** The [empty] value that will be returned if the specified cell it out of bounds, or empty */
    val empty: Double

    /** Duplicates the contents of this [IStackedDoubleArray2] keeping its contents data */
    fun clone(): IStackedDoubleArray2

    /** Sets the [value] at [x], [y] at [level], [startX] and [startY] are NOT used here so 0,0 means the top-left element */
    operator fun set(x: Int, y: Int, level: Int, value: Double)
    /** Gets the value at [x], [y] at [level], [startX] and [startY] are NOT used here so 0,0 means the top-left element */
    operator fun get(x: Int, y: Int, level: Int): Double

    /** Adds a new [value] on top of [x], [y] */
    fun push(x: Int, y: Int, value: Double) {
        set(x, y, getStackLevel(x, y), value)
    }

    /** Removes and returns the latest value on top of [x], [y] */
    fun pop(x: Int, y: Int): Double = getLast(x, y).also { removeLast(x, y) }

    /** Set the first [value] of a stack in the cell [x], [y] */
    fun setFirst(x: Int, y: Int, value: Double) = set(x, y, 0, value)

    /** Gets the first value of the stack in the cell [x], [y] */
    fun getFirst(x: Int, y: Int): Double {
        if (!inside(x, y, 0)) return empty
        return get(x, y, 0)
    }

    /** Gets the last value of the stack in the cell [x], [y] */
    fun getLast(x: Int, y: Int): Double {
        val level = getStackLevel(x, y)
        if (!inside(x, y, level - 1)) return empty
        return get(x, y, level - 1)
    }

    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) {
        this[x0, y0, level0] = this[x1, y1, level1]
    }
}

fun IStackedDoubleArray2.removeAt(p: PointInt, level: Int) = removeAt(p.x, p.y, level)
fun IStackedDoubleArray2.removeFirst(p: PointInt) = removeFirst(p.x, p.y)
fun IStackedDoubleArray2.removeLast(p: PointInt) = removeLast(p.x, p.y)
fun IStackedDoubleArray2.removeAll(p: PointInt) = removeAll(p.x, p.y)
fun IStackedDoubleArray2.getLast(p: PointInt): Double = getLast(p.x, p.y)
fun IStackedDoubleArray2.getStackLevel(p: PointInt): Int = getStackLevel(p.x, p.y)
fun IStackedDoubleArray2.get(p: PointInt, level: Int): Double = get(p.x, p.y, level)
fun IStackedDoubleArray2.set(p: PointInt, level: Int, value: Double) { set(p.x, p.y, level, value) }
fun IStackedDoubleArray2.push(p: PointInt, value: Double) { push(p.x, p.y, value) }
fun IStackedDoubleArray2.pop(p: PointInt) = pop(p.x, p.y)

/** Shortcut for [IStackedDoubleArray2.startX] + [IStackedDoubleArray2.width] */
val IStackedDoubleArray2.endX: Int get() = startX + width
/** Shortcut for [IStackedDoubleArray2.startY] + [IStackedDoubleArray2.height] */
val IStackedDoubleArray2.endY: Int get() = startY + height

class StackedDoubleArray2(
    override val width: Int,
    override val height: Int,
    override val empty: Double = EMPTY,
    override val startX: Int = 0,
    override val startY: Int = 0,
) : IStackedDoubleArray2 {
    override var contentVersion: Int = 0 ; private set

    override fun toString(): String = "StackedDoubleArray2(($width, $height), empty=$empty, startXY=($startX,$startY))"

    override fun clone(): StackedDoubleArray2 {
        return StackedDoubleArray2(width, height, empty, startX, startY).also { out ->
            arraycopy(this.level.data, 0, out.level.data, 0, out.level.data.size)
            out.data.addAll(this.data.map { it.clone() })
        }
    }

    val level = IntArray2(width, height, 0)
    val data = fastArrayListOf<DoubleArray2>()

    override val maxLevel: Int get() = data.size

    companion object {
        const val EMPTY = Double.NaN

        operator fun invoke(
            vararg layers: DoubleArray2,
            width: Int = layers.first().width,
            height: Int = layers.first().height,
            empty: Double = EMPTY,
            startX: Int = 0,
            startY: Int = 0,
        ): StackedDoubleArray2 {
            val stacked = StackedDoubleArray2(width, height, empty, startX = startX, startY = startY)
            stacked.level.fill { layers.size }
            stacked.data.addAll(layers)
            return stacked
        }
    }

    fun ensureLevel(level: Int) {
        while (level >= data.size) data.add(DoubleArray2(width, height, empty))
    }

    fun setLayer(level: Int, data: DoubleArray2) {
        ensureLevel(level)
        this.data[level] = data
        contentVersion++
    }

    override operator fun set(x: Int, y: Int, level: Int, value: Double) {
        if (!inside(x, y)) return
        ensureLevel(level)
        data[level][x, y] = value
        this.level[x, y] = maxOf(this.level[x, y], level + 1)
        contentVersion++
    }

    override operator fun get(x: Int, y: Int, level: Int): Double {
        if (!inside(x, y)) return empty
        if (level > this.level[x, y]) return empty
        return data[level][x, y]
    }

    override fun getStackLevel(x: Int, y: Int): Int {
        if (!inside(x, y)) return 0
        return this.level[x, y]
    }

    override fun IStackedArray2Base.Internal.setStackLevelInternal(x: Int, y: Int, levels: Int): Boolean {
        if (!inside(x, y)) return false
        this@StackedDoubleArray2.level[x, y] = levels
        return true
    }
}

fun DoubleArray2.toStacked(): StackedDoubleArray2 = StackedDoubleArray2(this)

open class SparseChunkedStackedDoubleArray2(override var empty: Double = StackedDoubleArray2.EMPTY) : SparseChunkedStackedArray2<IStackedDoubleArray2>(), IStackedDoubleArray2 {
    constructor(vararg layers: IStackedDoubleArray2, empty: Double = StackedDoubleArray2.EMPTY) : this(empty) {
        layers.fastForEach { putChunk(it) }
    }

    override fun setEmptyFromChunk(chunk: IStackedDoubleArray2) {
        empty = chunk.empty
    }

    override fun set(x: Int, y: Int, level: Int, value: Double) {
        val chunk = getChunkAt(x, y, create = true) ?: return
        chunk[chunk.chunkX(x), chunk.chunkY(y), level] = value
        contentVersion++
    }

    override fun get(x: Int, y: Int, level: Int): Double {
        val chunk = getChunkAt(x, y) ?: return empty
        return chunk[chunk.chunkX(x), chunk.chunkY(y), level]
    }

    //override fun removeAt(x: Int, y: Int, level: Int): Boolean {
    //    val chunk = getChunkAt(x, y) ?: return false
    //    if (!chunk.removeAt(chunk.chunkX(x), chunk.chunkX(y), level)) return false
    //    contentVersion++
    //    return true
    //}

    override fun clone(): SparseChunkedStackedDoubleArray2 = SparseChunkedStackedDoubleArray2(empty).also { sparse ->
        findAllChunks().fastForEach {
            sparse.putChunk(it.clone())
        }
    }
}

/** A [SparseChunkedStackedDoubleArray2] that uses a [grid] size to create new chunks as required */
class InfiniteGridStackedDoubleArray2(val grid: SizeInt = SizeInt(16, 16), override var empty: Double = StackedDoubleArray2.EMPTY) : SparseChunkedStackedDoubleArray2() {
    fun getGridXFor(x: Int) = idiv(x, grid.width)
    fun getGridYFor(y: Int) = idiv(y, grid.height)

    override fun getChunkAt(x: Int, y: Int, create: Boolean): IStackedDoubleArray2? {
        val gridX = getGridXFor(x)
        val gridY = getGridYFor(y)

        var res = super.getChunkAt(x, y, false)
        if (res == null && create) {
            res = putChunk(StackedDoubleArray2(grid.width, grid.height, empty = empty, startX = gridX * grid.width, startY = gridY * grid.height))
        }
        return res
    }
}
