package korlibs.io.lang.internal

@PublishedApi internal fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS
@PublishedApi internal fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()
@PublishedApi internal fun Int.extract(offset: Int, count: Int): Int = (this ushr offset) and count.mask()
@PublishedApi internal fun Int.mask(): Int = (1 shl this) - 1
@PublishedApi internal fun Int.insert(value: Int, offset: Int, count: Int): Int {
    val mask = count.mask() shl offset
    val ovalue = (value shl offset) and mask
    return (this and mask.inv()) or ovalue
}
@PublishedApi internal fun ByteArray.getS16(offset: Int, littleEndian: Boolean): Int = if (littleEndian) getS16LE(offset) else getS16BE(offset)
@PublishedApi internal fun ByteArray.getS16LE(offset: Int): Int = get16LE(offset).signExtend(16)
@PublishedApi internal inline fun ByteArray.get16LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8)
@PublishedApi internal fun ByteArray.set16(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set16LE(offset, value) else set16BE(offset, value) }
@PublishedApi internal fun ByteArray.set16LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8) }
@PublishedApi internal fun ByteArray.set16BE(offset: Int, value: Int) { this[offset + 1] = value.extractByte(0); this[offset + 0] = value.extractByte(8) }
@PublishedApi internal fun ByteArray.getS16BE(offset: Int): Int = get16BE(offset).signExtend(16)
@PublishedApi internal inline fun ByteArray.get16BE(offset: Int): Int = (u8(offset + 1) shl 0) or (u8(offset + 0) shl 8)
@PublishedApi internal fun ByteArray.u8(offset: Int): Int = this[offset].toInt() and 0xFF
