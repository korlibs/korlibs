@file:Suppress("PackageDirectoryMismatch")

package korlibs.memory.internal

/** Returns the float representation of [this] memory bits */
internal inline fun Int.reinterpretAsFloat(): Float = Float.fromBits(this)
/** Returns the float representation of [this] memory bits */
internal inline fun Long.reinterpretAsDouble(): Double = Double.fromBits(this)

/** Returns the bits in memory of [this] float */
internal inline fun Float.reinterpretAsInt(): Int = this.toRawBits()
/** Returns the bits in memory of [this] float */
internal inline fun Double.reinterpretAsLong(): Long = this.toRawBits()

/** Extracts [count] bits at [offset] from [this] [Int] */
internal fun Int.extract(offset: Int, count: Int): Int = (this ushr offset) and ((1 shl count) - 1)
/** Extracts 4 bits at [offset] from [this] [Int] */
internal inline fun Int.extract4(offset: Int): Int = (this ushr offset) and 0b1111
/** Extracts 8 bits at [offset] from [this] [Int] as [Byte] */
internal fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()

/** Takes n[bits] of [this] [Int], and extends the last bit, creating a plain [Int] in one's complement */
internal fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS

/** Reverses the bytes of [this] [Short]: AABB -> BBAA */
internal fun Short.reverseBytes(): Short {
    val low = ((this.toInt() ushr 0) and 0xFF)
    val high = ((this.toInt() ushr 8) and 0xFF)
    return ((high and 0xFF) or (low shl 8)).toShort()
}

/** Reverses the bytes of [this] [Int]: AABBCCDD -> DDCCBBAA */
internal fun Int.reverseBytes(): Int {
    val v0 = ((this ushr 0) and 0xFF)
    val v1 = ((this ushr 8) and 0xFF)
    val v2 = ((this ushr 16) and 0xFF)
    val v3 = ((this ushr 24) and 0xFF)
    return (v0 shl 24) or (v1 shl 16) or (v2 shl 8) or (v3 shl 0)
}

/** Reverses the bytes of [this] [Long]: AABBCCDDEEFFGGHH -> HHGGFFEEDDCCBBAA */
internal fun Long.reverseBytes(): Long {
    val v0 = (this ushr 0).toInt().reverseBytes().toLong() and 0xFFFFFFFFL
    val v1 = (this ushr 32).toInt().reverseBytes().toLong() and 0xFFFFFFFFL
    return (v0 shl 32) or (v1 shl 0)
}

