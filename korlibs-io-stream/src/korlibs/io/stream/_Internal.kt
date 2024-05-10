package korlibs.io.stream

import korlibs.io.lang.*

internal fun Long.toIntSafe(): Int = if (this in Int.MIN_VALUE.toLong()..Int.MAX_VALUE.toLong()) this.toInt() else throw IllegalArgumentException("Long doesn't fit Integer")

internal fun Int.nextAlignedTo(align: Int): Int = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)
internal fun Int.prevAlignedTo(align: Int): Int = if (this.isAlignedTo(align)) this else nextAlignedTo(align) - align
internal fun Int.isAlignedTo(alignment: Int): Boolean = alignment == 0 || (this % alignment) == 0

internal fun Long.nextAlignedTo(align: Long): Long = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)
internal fun Long.prevAlignedTo(align: Long): Long = if (this.isAlignedTo(align)) this else nextAlignedTo(align) - align
internal fun Long.isAlignedTo(alignment: Long): Boolean = alignment == 0L || (this % alignment) == 0L

internal fun String.toBytez(len: Int, charset: Charset = UTF8): ByteArray = this.toByteArray(charset).copyOf(len)
internal fun String.toBytez(charset: Charset = UTF8): ByteArray = this.toByteArray(charset).let { it.copyOf(it.size + 1) }

internal fun Long.toIntClamp(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int {
    if (this < min) return min
    if (this > max) return max
    return this.toInt()
}
