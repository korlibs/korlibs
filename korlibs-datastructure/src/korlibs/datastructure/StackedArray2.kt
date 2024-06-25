package korlibs.datastructure

interface IStackedArray2<T> : IStackedArray2Base

interface IStackedArray2Base {
    object Internal

    /** Version of the data. Each change increments this. */
    val contentVersion: Int

    /** Annotation of where in [startX] this stack would be placed in a bigger container, not used for set or get methods */
    val startX: Int
    /** Annotation of where in [startY] this stack would be placed in a bigger container, not used for set or get methods */
    val startY: Int

    /** [width] of the data available here, get and set methods use values in the range x=0 until [width] */
    val width: Int
    /** [height] of the data available here, get and set methods use values in the range y=0 until [height] */
    val height: Int

    /** The maximum level of layers available on the whole stack */
    val maxLevel: Int

    /** Shortcut for [IStackedArray2Base.startX] + [IStackedArray2Base.width] */
    val endX: Int get() = startX + width
    /** Shortcut for [IStackedArray2Base.startY] + [IStackedArray2Base.height] */
    val endY: Int get() = startY + height

    /** Number of values available at this [x], [y] */
    fun getStackLevel(x: Int, y: Int): Int

    /** Number of values available at this [x], [y] */
    fun Internal.setStackLevelInternal(x: Int, y: Int, levels: Int): Boolean

    /** Removes the last value at [x], [y] in the specified [level] */
    fun removeAt(x: Int, y: Int, level: Int): Boolean {
        if (!inside(x, y, level)) return false
        val levels = getStackLevel(x, y)
        if (level < 0 || level >= levels) return false
        for (n in level until levels - 1) setToFrom(x, y, n, x, y, n + 1)
        IStackedArray2Base.Internal.setStackLevelInternal(x, y, (levels - 1).coerceAtLeast(0))
        return true
    }

    /** Copies the value at [x1][y1][level1] into [x0][y0][level0]. Equivalent to this[x0,y0,level0] = this[x1,y1,level1] */
    fun setToFrom(x0: Int, y0: Int, level0: Int, x1: Int, y1: Int, level1: Int)

    /** Removes the last value at [x], [y] */
    fun removeLast(x: Int, y: Int): Boolean = removeAt(x, y, getStackLevel(x, y) - 1)

    /** Removes the first value at [x], [y] */
    fun removeFirst(x: Int, y: Int): Boolean = removeAt(x, y, 0)

    /** Removes all levels at [x], [y] */
    fun removeAll(x: Int, y: Int): Boolean {
        if (!inside(x, y)) return false
        while (getStackLevel(x, y) > 0) removeLast(x, y)
        return true
    }

    /** Checks if [x] and [y] are inside this array in the range x=0 until [width] and y=0 until [height] ignoring startX and startY */
    fun inside(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < width && y < height

    fun inside(x: Int, y: Int, level: Int): Boolean = inside(x, y) && (level in 0 until getStackLevel(x, y))

    ///** Duplicates the contents of this [IStackedArray2] keeping its contents data */
    //fun clone(): IStackedArray2<T>
    ///** The [emptyGeneric] value that will be returned if the specified cell it out of bounds, or empty */
    //val emptyGeneric: T
    ///** Sets the [value] at [x], [y] at [level], [startX] and [startY] are NOT used here so 0,0 means the top-left element */
    //fun setGeneric(x: Int, y: Int, level: Int, value: T)
    ///** Gets the value at [x], [y] at [level], [startX] and [startY] are NOT used here so 0,0 means the top-left element */
    //fun getGeneric(x: Int, y: Int, level: Int): T

    fun eachPosition(block: (x: Int, y: Int) -> Unit) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                block(x, y)
            }
        }
    }
}
