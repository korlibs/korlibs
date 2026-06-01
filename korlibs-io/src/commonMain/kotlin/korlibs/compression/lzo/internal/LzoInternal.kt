package korlibs.compression.lzo.internal

private val Byte.unsigned: Int get() = this.toInt() and 0xFF
private fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS
private inline val Int.unsigned: Long get() = this.toLong() and 0xFFFFFFFFL

private fun ByteArray.u8(offset: Int): Int = this[offset].toInt() and 0xFF

internal inline fun ByteArray.get16LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8)
internal inline fun ByteArray.get24LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8) or (u8(offset + 2) shl 16)
internal inline fun ByteArray.get32LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8) or (u8(offset + 2) shl 16) or (u8(offset + 3) shl 24)
internal inline fun ByteArray.get64LE(offset: Int): Long = (get32LE(offset + 0).unsigned shl 0) or (get32LE(offset + 4).unsigned shl 32)

internal fun ByteArray.getU16LE(offset: Int): Int = get16LE(offset)
internal fun ByteArray.getU24LE(offset: Int): Int = get24LE(offset)
internal fun ByteArray.getU32LE(offset: Int): Long = get32LE(offset).unsigned

internal fun ByteArray.getS8(offset: Int): Int = this[offset].toInt()
internal fun ByteArray.getS16LE(offset: Int): Int = get16LE(offset).signExtend(16)
internal fun ByteArray.getS24LE(offset: Int): Int = get24LE(offset).signExtend(24)
internal fun ByteArray.getS32LE(offset: Int): Int = get32LE(offset)
internal fun ByteArray.getS64LE(offset: Int): Long = get64LE(offset)

internal fun ByteArray.set8(offset: Int, value: Int) { this[offset] = value.toByte() }
internal fun ByteArray.set8(offset: Int, value: Long) { this[offset] = value.toByte() }
internal fun ByteArray.set16LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8) }
internal fun ByteArray.set24LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 2] = value.extractByte(16) }
internal fun ByteArray.set32LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 2] = value.extractByte(16); this[offset + 3] = value.extractByte(24) }
public fun ByteArray.set32LE(offset: Int, value: Long) { set32LE(offset, value.toInt()) }
internal fun ByteArray.set64LE(offset: Int, value: Long) { set32LE(offset + 0, (value ushr 0).toInt()); set32LE(offset + 4, (value ushr 32).toInt()) }

// Unsigned
internal fun ByteArray.getU8(offset: Int): Int = this[offset].toInt() and 0xFF

internal fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()

internal fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int) {
    src.copyInto(dst, dstPos, srcPos, srcPos + size)
}
