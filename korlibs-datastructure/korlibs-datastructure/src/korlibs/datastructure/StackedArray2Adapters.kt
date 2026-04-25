package korlibs.datastructure

import korlibs.memory.*
import korlibs.number.*

open class BaseDelegatedStackedArray2(val other: IStackedArray2Base) : IStackedArray2Base by other

class StackedLongArray2FromIStackedIntArray2(val data: IStackedIntArray2) : BaseDelegatedStackedArray2(data), IStackedLongArray2 {
    override val empty: Long get() = StackedLongArray2.EMPTY
    override fun clone(): IStackedLongArray2 = StackedLongArray2FromIStackedIntArray2(data.clone())
    override fun set(x: Int, y: Int, level: Int, value: Long) { data[x, y, level] = value.toInt() }
    override fun get(x: Int, y: Int, level: Int): Long = data[x, y, level].toUInt().toLong()
    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) { this[x0, y0, level0] = this[x1, y1, level1] }
}
class StackedIntArray2FromIStackedLongArray2(val data: IStackedLongArray2) : BaseDelegatedStackedArray2(data), IStackedIntArray2 {
    override val empty: Int get() = StackedIntArray2.EMPTY
    override fun clone(): IStackedIntArray2 = StackedIntArray2FromIStackedLongArray2(data.clone())
    override fun set(x: Int, y: Int, level: Int, value: Int) { data[x, y, level] = value.toLong() }
    override fun get(x: Int, y: Int, level: Int): Int = data[x, y, level].toInt()
    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) { this[x0, y0, level0] = this[x1, y1, level1] }
}
fun IStackedIntArray2.asLong(): IStackedLongArray2 = StackedLongArray2FromIStackedIntArray2(this)
fun IStackedLongArray2.asInt(): IStackedIntArray2 = StackedIntArray2FromIStackedLongArray2(this)

/////////////////////

class StackedInt64Array2FromIStackedIntArray2(val data: IStackedIntArray2) : BaseDelegatedStackedArray2(data), IStackedInt64Array2 {
    override val empty: Int64 get() = StackedInt64Array2.EMPTY
    override fun clone(): IStackedInt64Array2 = StackedInt64Array2FromIStackedIntArray2(data.clone())
    override fun set(x: Int, y: Int, level: Int, value: Int64) { data[x, y, level] = value.toInt() }
    override fun get(x: Int, y: Int, level: Int): Int64 = data[x, y, level].toInt64()
    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) { this[x0, y0, level0] = this[x1, y1, level1] }
}
class StackedIntArray2FromIStackedInt64Array2(val data: IStackedInt64Array2) : BaseDelegatedStackedArray2(data), IStackedIntArray2 {
    override val empty: Int get() = StackedIntArray2.EMPTY
    override fun clone(): IStackedIntArray2 = StackedIntArray2FromIStackedInt64Array2(data.clone())
    override fun set(x: Int, y: Int, level: Int, value: Int) { data[x, y, level] = value.toInt64() }
    override fun get(x: Int, y: Int, level: Int): Int = data[x, y, level].low
    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) { this[x0, y0, level0] = this[x1, y1, level1] }
}
fun IStackedIntArray2.asInt64(): IStackedInt64Array2 = StackedInt64Array2FromIStackedIntArray2(this)
fun IStackedInt64Array2.asInt(): IStackedIntArray2 = StackedIntArray2FromIStackedInt64Array2(this)

/////////////////////

class StackedInt53Array2FromIStackedIntArray2(val data: IStackedIntArray2) : BaseDelegatedStackedArray2(data), IStackedInt53Array2 {
    override val empty: Int53 get() = StackedInt53Array2.EMPTY
    override fun clone(): IStackedInt53Array2 = StackedInt53Array2FromIStackedIntArray2(data.clone())
    override fun set(x: Int, y: Int, level: Int, value: Int53) { data[x, y, level] = value.toInt() }
    override fun get(x: Int, y: Int, level: Int): Int53 = data[x, y, level].toInt53()
    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) { this[x0, y0, level0] = this[x1, y1, level1] }
}
class StackedIntArray2FromIStackedInt53Array2(val data: IStackedInt53Array2) : BaseDelegatedStackedArray2(data), IStackedIntArray2 {
    override val empty: Int get() = StackedIntArray2.EMPTY
    override fun clone(): IStackedIntArray2 = StackedIntArray2FromIStackedInt53Array2(data.clone())
    override fun set(x: Int, y: Int, level: Int, value: Int) { data[x, y, level] = value.toInt53() }
    override fun get(x: Int, y: Int, level: Int): Int = data[x, y, level].low
    override fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int) { this[x0, y0, level0] = this[x1, y1, level1] }
}
fun IStackedIntArray2.asInt53(): IStackedInt53Array2 = StackedInt53Array2FromIStackedIntArray2(this)
fun IStackedInt53Array2.asInt(): IStackedIntArray2 = StackedIntArray2FromIStackedInt53Array2(this)
