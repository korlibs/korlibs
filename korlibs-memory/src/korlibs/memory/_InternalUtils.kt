package korlibs.memory

internal fun Int.clampUByte(): Int {
    val n = this and -(if (this >= 0) 1 else 0)
    return (n or (0xFF - n shr 31)) and 0xFF
}

internal fun Int.clampUShort(): Int {
    val n = this and -(if (this >= 0) 1 else 0)
    return (n or (0xFFFF - n shr 31)) and 0xFFFF
}

internal inline val Byte.unsigned: Int get() = this.toInt() and 0xFF
internal inline val Short.unsigned: Int get() = this.toInt() and 0xFFFF
internal inline val Int.unsigned: Long get() = this.toLong() and 0xFFFFFFFFL
internal inline fun Boolean.toInt(): Int = if (this) 1 else 0

internal fun Int.isAlignedTo(alignment: Int): Boolean = alignment == 0 || (this % alignment) == 0
internal fun Int.nextAlignedTo(align: Int): Int = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)
internal fun ilog2(v: Int): Int = if (v == 0) (-1) else (31 - v.countLeadingZeroBits())
