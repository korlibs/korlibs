package korlibs.memory

import korlibs.memory.internal.*

/////////////////////////////////////////
/////////////////////////////////////////
/////////////////////////////////////////

private val Int.unsigned: Long get() = this.toLong() and 0xFFFFFFFFL
private fun ByteArray.u8(offset: Int): Int = this[offset].toInt() and 0xFF

private fun ByteArray.get16LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8)
private fun ByteArray.get24LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8) or (u8(offset + 2) shl 16)
private fun ByteArray.get32LE(offset: Int): Int = (u8(offset + 0) shl 0) or (u8(offset + 1) shl 8) or (u8(offset + 2) shl 16) or (u8(offset + 3) shl 24)
private fun ByteArray.get64LE(offset: Int): Long = (get32LE(offset + 0).unsigned shl 0) or (get32LE(offset + 4).unsigned shl 32)

private fun ByteArray.get16BE(offset: Int): Int = (u8(offset + 1) shl 0) or (u8(offset + 0) shl 8)
private fun ByteArray.get24BE(offset: Int): Int = (u8(offset + 2) shl 0) or (u8(offset + 1) shl 8) or (u8(offset + 0) shl 16)
private fun ByteArray.get32BE(offset: Int): Int = (u8(offset + 3) shl 0) or (u8(offset + 2) shl 8) or (u8(offset + 1) shl 16) or (u8(offset + 0) shl 24)
private fun ByteArray.get64BE(offset: Int): Long = (get32BE(offset + 4).unsigned shl 0) or (get32BE(offset + 0).unsigned shl 32)

// Unsigned
fun ByteArray.getU8(offset: Int): Int = this[offset].toInt() and 0xFF
fun ByteArray.getU16LE(offset: Int): Int = get16LE(offset)
fun ByteArray.getU24LE(offset: Int): Int = get24LE(offset)
fun ByteArray.getU32LE(offset: Int): Long = get32LE(offset).unsigned
fun ByteArray.getU16BE(offset: Int): Int = get16BE(offset)
fun ByteArray.getU24BE(offset: Int): Int = get24BE(offset)
fun ByteArray.getU32BE(offset: Int): Long = get32BE(offset).unsigned

// Signed
fun ByteArray.getS8(offset: Int): Int = this[offset].toInt()
fun ByteArray.getS16LE(offset: Int): Int = get16LE(offset).signExtend(16)
fun ByteArray.getS24LE(offset: Int): Int = get24LE(offset).signExtend(24)
fun ByteArray.getS32LE(offset: Int): Int = get32LE(offset)
fun ByteArray.getS64LE(offset: Int): Long = get64LE(offset)
fun ByteArray.getF32LE(offset: Int): Float = Float.fromBits(get32LE(offset))
fun ByteArray.getF64LE(offset: Int): Double = Double.fromBits(get64LE(offset))
fun ByteArray.getS16BE(offset: Int): Int = get16BE(offset).signExtend(16)
fun ByteArray.getS24BE(offset: Int): Int = get24BE(offset).signExtend(24)
fun ByteArray.getS32BE(offset: Int): Int = get32BE(offset)
fun ByteArray.getS64BE(offset: Int): Long = get64BE(offset)
fun ByteArray.getF32BE(offset: Int): Float = Float.fromBits(get32BE(offset))
fun ByteArray.getF64BE(offset: Int): Double = Double.fromBits(get64BE(offset))

// Custom Endian
fun ByteArray.getU16(offset: Int, littleEndian: Boolean): Int = if (littleEndian) getU16LE(offset) else getU16BE(offset)
fun ByteArray.getU24(offset: Int, littleEndian: Boolean): Int = if (littleEndian) getU24LE(offset) else getU24BE(offset)
fun ByteArray.getU32(offset: Int, littleEndian: Boolean): Long = if (littleEndian) getU32LE(offset) else getU32BE(offset)
fun ByteArray.getS16(offset: Int, littleEndian: Boolean): Int = if (littleEndian) getS16LE(offset) else getS16BE(offset)
fun ByteArray.getS24(offset: Int, littleEndian: Boolean): Int = if (littleEndian) getS24LE(offset) else getS24BE(offset)
fun ByteArray.getS32(offset: Int, littleEndian: Boolean): Int = if (littleEndian) getS32LE(offset) else getS32BE(offset)
fun ByteArray.getS64(offset: Int, littleEndian: Boolean): Long = if (littleEndian) getS64LE(offset) else getS64BE(offset)
fun ByteArray.getF32(offset: Int, littleEndian: Boolean): Float = if (littleEndian) getF32LE(offset) else getF32BE(offset)
fun ByteArray.getF64(offset: Int, littleEndian: Boolean): Double = if (littleEndian) getF64LE(offset) else getF64BE(offset)

private inline fun <T> ByteArray.getTypedArray(offset: Int, count: Int, elementSize: Int, array: T, crossinline get: ByteArray.(array: T, n: Int, pos: Int) -> Unit): T = array.also {
    for (n in 0 until count) get(this, array, n, offset + n * elementSize)
}

fun ByteArray.getS8Array(offset: Int, count: Int): ByteArray = this.copyOfRange(offset, offset + count)
fun ByteArray.getS16ArrayLE(offset: Int, count: Int): ShortArray = this.getTypedArray(offset, count, 2, ShortArray(count)) { array, n, pos -> array[n] = getS16LE(pos).toShort() }
fun ByteArray.getU16ArrayLE(offset: Int, count: Int): CharArray = this.getTypedArray(offset, count, 2, kotlin.CharArray(count)) { array, n, pos -> array[n] = getS16LE(pos).toChar() }
fun ByteArray.getS32ArrayLE(offset: Int, count: Int): IntArray = this.getTypedArray(offset, count, 4, IntArray(count)) { array, n, pos -> array[n] = getS32LE(pos) }
fun ByteArray.getS64ArrayLE(offset: Int, count: Int): LongArray = this.getTypedArray(offset, count, 8, LongArray(count)) { array, n, pos -> array[n] = getS64LE(pos) }
fun ByteArray.getF32ArrayLE(offset: Int, count: Int): FloatArray = this.getTypedArray(offset, count, 4, FloatArray(count)) { array, n, pos -> array[n] = getF32LE(pos) }
fun ByteArray.getF64ArrayLE(offset: Int, count: Int): DoubleArray = this.getTypedArray(offset, count, 8, DoubleArray(count)) { array, n, pos -> array[n] = getF64LE(pos) }
fun ByteArray.getS16ArrayBE(offset: Int, count: Int): ShortArray = this.getTypedArray(offset, count, 2, ShortArray(count)) { array, n, pos -> array[n] = getS16BE(pos).toShort() }
fun ByteArray.getU16ArrayBE(offset: Int, count: Int): CharArray = this.getTypedArray(offset, count, 2, kotlin.CharArray(count)) { array, n, pos -> array[n] = getS16BE(pos).toChar() }
fun ByteArray.getS32ArrayBE(offset: Int, count: Int): IntArray = this.getTypedArray(offset, count, 4, IntArray(count)) { array, n, pos -> array[n] = getS32BE(pos) }
fun ByteArray.getS64ArrayBE(offset: Int, count: Int): LongArray = this.getTypedArray(offset, count, 8, LongArray(count)) { array, n, pos -> array[n] = getS64BE(pos) }
fun ByteArray.getF32ArrayBE(offset: Int, count: Int): FloatArray = this.getTypedArray(offset, count, 4, FloatArray(count)) { array, n, pos -> array[n] = getF32BE(pos) }
fun ByteArray.getF64ArrayBE(offset: Int, count: Int): DoubleArray = this.getTypedArray(offset, count, 8, DoubleArray(count)) { array, n, pos -> array[n] = getF64BE(pos) }

fun ByteArray.getS16Array(offset: Int, count: Int, littleEndian: Boolean): ShortArray = if (littleEndian) getS16ArrayLE(offset, count) else getS16ArrayBE(offset, count)
fun ByteArray.getU16Array(offset: Int, count: Int, littleEndian: Boolean): CharArray = if (littleEndian) getU16ArrayLE(offset, count) else getU16ArrayBE(offset, count)
fun ByteArray.getS32Array(offset: Int, count: Int, littleEndian: Boolean): IntArray = if (littleEndian) getS32ArrayLE(offset, count) else getS32ArrayBE(offset, count)
fun ByteArray.getS64Array(offset: Int, count: Int, littleEndian: Boolean): LongArray = if (littleEndian) getS64ArrayLE(offset, count) else getS64ArrayBE(offset, count)
fun ByteArray.getF32Array(offset: Int, count: Int, littleEndian: Boolean): FloatArray = if (littleEndian) getF32ArrayLE(offset, count) else getF32ArrayBE(offset, count)
fun ByteArray.getF64Array(offset: Int, count: Int, littleEndian: Boolean): DoubleArray = if (littleEndian) getF64ArrayLE(offset, count) else getF64ArrayBE(offset, count)

/////////////////////////////////////////
/////////////////////////////////////////
/////////////////////////////////////////

fun ByteArray.set8(offset: Int, value: Int) { this[offset] = value.toByte() }
fun ByteArray.set8(offset: Int, value: Long) { this[offset] = value.toByte() }
fun ByteArray.set16(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set16LE(offset, value) else set16BE(offset, value) }
fun ByteArray.set24(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set24LE(offset, value) else set24BE(offset, value) }
fun ByteArray.set32(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set32LE(offset, value) else set32BE(offset, value) }
fun ByteArray.set64(offset: Int, value: Long, littleEndian: Boolean) { if (littleEndian) set64LE(offset, value) else set64BE(offset, value) }
fun ByteArray.setF32(offset: Int, value: Float, littleEndian: Boolean) { if (littleEndian) setF32LE(offset, value) else setF32BE(offset, value) }
fun ByteArray.setF64(offset: Int, value: Double, littleEndian: Boolean) { if (littleEndian) setF64LE(offset, value) else setF64BE(offset, value) }

fun ByteArray.set16LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8) }
fun ByteArray.set24LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 2] = value.extractByte(16) }
fun ByteArray.set32LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 2] = value.extractByte(16); this[offset + 3] = value.extractByte(24) }
fun ByteArray.set32LE(offset: Int, value: Long) { set32LE(offset, value.toInt()) }
fun ByteArray.set64LE(offset: Int, value: Long) { set32LE(offset + 0, (value ushr 0).toInt()); set32LE(offset + 4, (value ushr 32).toInt()) }
fun ByteArray.setF32LE(offset: Int, value: Float) { set32LE(offset + 0, value.toRawBits()) }
fun ByteArray.setF64LE(offset: Int, value: Double) { set64LE(offset + 0, value.toRawBits()) }

fun ByteArray.set16BE(offset: Int, value: Int) { this[offset + 1] = value.extractByte(0); this[offset + 0] = value.extractByte(8) }
fun ByteArray.set24BE(offset: Int, value: Int) { this[offset + 2] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 0] = value.extractByte(16) }
fun ByteArray.set32BE(offset: Int, value: Int) { this[offset + 3] = value.extractByte(0); this[offset + 2] = value.extractByte(8); this[offset + 1] = value.extractByte(16); this[offset + 0] = value.extractByte(24) }
fun ByteArray.set32BE(offset: Int, value: Long) { set32BE(offset, value.toInt()) }
fun ByteArray.set64BE(offset: Int, value: Long) { set32BE(offset + 0, (value ushr 32).toInt()); set32BE(offset + 4, (value ushr 0).toInt()) }
fun ByteArray.setF32BE(offset: Int, value: Float) { set32BE(offset + 0, value.toRawBits()) }
fun ByteArray.setF64BE(offset: Int, value: Double) { set64BE(offset + 0, value.toRawBits()) }

fun ByteArray.setBytes(offset: Int, bytes: ByteArray): Unit = arraycopy(bytes, 0, this, offset, bytes.size)

private inline fun wa(offset: Int, elementSize: Int, size: Int, set: (p: Int, n: Int) -> Unit) { for (n in 0 until size) set(offset + n * elementSize, n) }

fun ByteArray.setArray(offset: Int, bytes: ByteArray): Unit = arraycopy(bytes, 0, this, offset, bytes.size)
fun ByteArray.setArrayLE(offset: Int, array: CharArray): Unit = wa(offset, 2, array.size) { p, n -> set16LE(p,
    array[n].code
) }
fun ByteArray.setArrayLE(offset: Int, array: ShortArray): Unit = wa(offset, 2, array.size) { p, n -> set16LE(p, array[n].toInt()) }
fun ByteArray.setArrayLE(offset: Int, array: IntArray): Unit = wa(offset, 4, array.size) { p, n -> set32LE(p, array[n]) }
fun ByteArray.setArrayLE(offset: Int, array: LongArray): Unit = wa(offset, 8, array.size) { p, n -> set64LE(p, array[n]) }
fun ByteArray.setArrayLE(offset: Int, array: FloatArray): Unit = wa(offset, 4, array.size) { p, n -> setF32LE(p, array[n]) }
fun ByteArray.setArrayLE(offset: Int, array: DoubleArray): Unit = wa(offset, 8, array.size) { p, n -> setF64LE(p, array[n]) }

fun ByteArray.setArrayBE(offset: Int, array: CharArray): Unit = wa(offset, 2, array.size) { p, n -> set16BE(p,
    array[n].code
) }
fun ByteArray.setArrayBE(offset: Int, array: ShortArray): Unit = wa(offset, 2, array.size) { p, n -> set16BE(p, array[n].toInt()) }
fun ByteArray.setArrayBE(offset: Int, array: IntArray): Unit = wa(offset, 4, array.size) { p, n -> set32BE(p, array[n]) }
fun ByteArray.setArrayBE(offset: Int, array: LongArray): Unit = wa(offset, 8, array.size) { p, n -> set64BE(p, array[n]) }
fun ByteArray.setArrayBE(offset: Int, array: FloatArray): Unit = wa(offset, 4, array.size) { p, n -> setF32BE(p, array[n]) }
fun ByteArray.setArrayBE(offset: Int, array: DoubleArray): Unit = wa(offset, 8, array.size) { p, n -> setF64BE(p, array[n]) }
