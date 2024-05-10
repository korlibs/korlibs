@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.io.lang.*
import korlibs.io.util.*
import korlibs.memory.*
import korlibs.platform.*
import kotlin.collections.indexOf

//val READ_SMALL_TEMP by threadLocal { ByteArray(8) }
//suspend private fun AsyncInputStream.readSmallTempExact(len: Int, temp: ByteArray): ByteArray = temp.apply { readExact(temp, 0, len) }
//suspend private fun AsyncInputStream.readSmallTempExact(len: Int): ByteArray = readSmallTempExact(len, READ_SMALL_TEMP)

@PublishedApi
internal suspend inline fun <R> AsyncInputStream.readSmallTempExact(size: Int, callback: ByteArray.() -> R): R {
    val it = ByteArray(size)
    val read = read(it, 0, size)
    if (read != size) error("Couldn't read exact size=$size but read=$read")
    return callback(it)
}


suspend fun AsyncInputStream.readBytesUpToFirst(len: Int): ByteArray {
    val out = ByteArray(len)
    val read = read(out, 0, len)
    if (read <= 0) return EMPTY_BYTE_ARRAY
    return out.copyOf(read)
}

suspend fun AsyncInputStream.read(data: ByteArray): Int = read(data, 0, data.size)
suspend fun AsyncInputStream.read(data: UByteArray): Int = read(data.asByteArray(), 0, data.size)

//suspend fun AsyncInputStream.readU8(): Int = readBytesExact(1).readU8(0)
suspend fun AsyncInputStream.readU8(): Int = read()

suspend fun AsyncInputStream.readS8(): Int = read().toByte().toInt()
suspend fun AsyncInputStream.readU16LE(): Int = readSmallTempExact(2) { getU16LE(0) }
suspend fun AsyncInputStream.readU24LE(): Int = readSmallTempExact(3) { getU24LE(0) }
suspend fun AsyncInputStream.readU32LE(): Long = readSmallTempExact(4) { getU32LE(0) }
suspend fun AsyncInputStream.readS16LE(): Int = readSmallTempExact(2) { getS16LE(0) }
suspend fun AsyncInputStream.readS24LE(): Int = readSmallTempExact(3) { getS24LE(0) }
suspend fun AsyncInputStream.readS32LE(): Int = readSmallTempExact(4) { getS32LE(0) }
suspend fun AsyncInputStream.readS64LE(): Long = readSmallTempExact(8) { getS64LE(0) }
suspend fun AsyncInputStream.readF32LE(): Float = readSmallTempExact(4) { getF32LE(0) }
suspend fun AsyncInputStream.readF64LE(): Double = readSmallTempExact(8) { getF64LE(0) }
suspend fun AsyncInputStream.readU16BE(): Int = readSmallTempExact(2) { getU16BE(0) }
suspend fun AsyncInputStream.readU24BE(): Int = readSmallTempExact(3) { getU24BE(0) }
suspend fun AsyncInputStream.readU32BE(): Long = readSmallTempExact(4) { getU32BE(0) }
suspend fun AsyncInputStream.readS16BE(): Int = readSmallTempExact(2) { getS16BE(0) }
suspend fun AsyncInputStream.readS24BE(): Int = readSmallTempExact(3) { getS24BE(0) }
suspend fun AsyncInputStream.readS32BE(): Int = readSmallTempExact(4) { getS32BE(0) }
suspend fun AsyncInputStream.readS64BE(): Long = readSmallTempExact(8) { getS64BE(0) }
suspend fun AsyncInputStream.readF32BE(): Float = readSmallTempExact(4) { getF32BE(0) }
suspend fun AsyncInputStream.readF64BE(): Double = readSmallTempExact(8) { getF64BE(0) }

suspend fun AsyncInputStream.readU16(endian: Endian): Int = if (endian == Endian.LITTLE_ENDIAN) readU16LE() else readU16BE()
suspend fun AsyncInputStream.readU24(endian: Endian): Int = if (endian == Endian.LITTLE_ENDIAN) readU24LE() else readU24BE()
suspend fun AsyncInputStream.readU32(endian: Endian): Long = if (endian == Endian.LITTLE_ENDIAN) readU32LE() else readU32BE()
suspend fun AsyncInputStream.readS16(endian: Endian): Int = if (endian == Endian.LITTLE_ENDIAN) readS16LE() else readS16BE()
suspend fun AsyncInputStream.readS24(endian: Endian): Int = if (endian == Endian.LITTLE_ENDIAN) readS24LE() else readS24BE()
suspend fun AsyncInputStream.readS32(endian: Endian): Int = if (endian == Endian.LITTLE_ENDIAN) readS32LE() else readS32BE()
suspend fun AsyncInputStream.readS64(endian: Endian): Long = if (endian == Endian.LITTLE_ENDIAN) readS64LE() else readS64BE()
suspend fun AsyncInputStream.readF32(endian: Endian): Float = if (endian == Endian.LITTLE_ENDIAN) readF32LE() else readF32BE()
suspend fun AsyncInputStream.readF64(endian: Endian): Double = if (endian == Endian.LITTLE_ENDIAN) readF64LE() else readF64BE()

suspend fun AsyncInputStream.readUByteArray(count: Int): UByteArray = readBytesExact(count).asUByteArray()
suspend fun AsyncInputStream.readShortArrayLE(count: Int): ShortArray = readBytesExact(count * 2).getS16ArrayLE(0, count)
suspend fun AsyncInputStream.readShortArrayBE(count: Int): ShortArray = readBytesExact(count * 2).getS16ArrayBE(0, count)
suspend fun AsyncInputStream.readCharArrayLE(count: Int): CharArray = readBytesExact(count * 2).getU16ArrayLE(0, count)
suspend fun AsyncInputStream.readCharArrayBE(count: Int): CharArray = readBytesExact(count * 2).getU16ArrayBE(0, count)
suspend fun AsyncInputStream.readIntArrayLE(count: Int): IntArray = readBytesExact(count * 4).getS32ArrayLE(0, count)
suspend fun AsyncInputStream.readIntArrayBE(count: Int): IntArray = readBytesExact(count * 4).getS32ArrayBE(0, count)
suspend fun AsyncInputStream.readLongArrayLE(count: Int): LongArray = readBytesExact(count * 8).getS64ArrayLE(0, count)
suspend fun AsyncInputStream.readLongArrayBE(count: Int): LongArray = readBytesExact(count * 8).getS64ArrayLE(0, count)
suspend fun AsyncInputStream.readFloatArrayLE(count: Int): FloatArray = readBytesExact(count * 4).getF32ArrayLE(0, count)
suspend fun AsyncInputStream.readFloatArrayBE(count: Int): FloatArray = readBytesExact(count * 4).getF32ArrayBE(0, count)
suspend fun AsyncInputStream.readDoubleArrayLE(count: Int): DoubleArray = readBytesExact(count * 8).getF64ArrayLE(0, count)
suspend fun AsyncInputStream.readDoubleArrayBE(count: Int): DoubleArray = readBytesExact(count * 8).getF64ArrayBE(0, count)

suspend fun AsyncInputStream.readShortArray(count: Int, endian: Endian): ShortArray = if (endian.isLittle) readShortArrayLE(count) else readShortArrayBE(count)
suspend fun AsyncInputStream.readCharArray(count: Int, endian: Endian): CharArray = if (endian.isLittle) readCharArrayLE(count) else readCharArrayBE(count)
suspend fun AsyncInputStream.readIntArray(count: Int, endian: Endian): IntArray = if (endian.isLittle) readIntArrayLE(count) else readIntArrayBE(count)
suspend fun AsyncInputStream.readLongArray(count: Int, endian: Endian): LongArray = if (endian.isLittle) readLongArrayLE(count) else readLongArrayBE(count)
suspend fun AsyncInputStream.readFloatArray(count: Int, endian: Endian): FloatArray = if (endian.isLittle) readFloatArrayLE(count) else readFloatArrayBE(count)
suspend fun AsyncInputStream.readDoubleArray(count: Int, endian: Endian): DoubleArray = if (endian.isLittle) readDoubleArrayLE(count) else readDoubleArrayBE(count)

suspend fun AsyncInputStream.readUntil(endByte: Byte, limit: Int = Int.MAX_VALUE, initialCapacity: Int = 4096, includeEnd: Boolean = false, temp: ByteArray = ByteArray(1)): ByteArray {
    val out = ByteArrayBuilder(initialCapacity)
    try {
        while (true) {
            readExact(temp, 0, 1)
            val c = temp[0]
            //val c = readS8().toByte()
            if (c == endByte) {
                if (includeEnd) out.append(c)
                break
            }
            out.append(c)
            if (out.size >= limit) break
        }
    } catch (e: EOFException) {
    }
    //println("AsyncInputStream.readUntil: '${out.toString(UTF8).replace('\r', ';').replace('\n', '.')}'")
    return out.toByteArray()
}

suspend fun AsyncInputStream.readLine(eol: Char = '\n', charset: Charset = UTF8, limit: Int = Int.MAX_VALUE, initialCapacity: Int = 4096, includeEnd: Boolean = false): String {
    return readUntil(eol.code.toByte(), limit, initialCapacity, includeEnd).toString(charset)
}

suspend fun AsyncBufferedInputStream.readBufferedLine(limit: Int = 0x1000, charset: Charset = UTF8) =
    readUntil('\n'.code.toByte(), including = false, limit = limit).toString(charset)

suspend fun AsyncInputStream.readStringz(charset: Charset = UTF8): String {
    val buf = ByteArrayBuilder()
    val temp = ByteArray(1)
    while (true) {
        val read = read(temp, 0, 1)
        if (read <= 0) break
        if (temp[0] == 0.toByte()) break
        buf.append(temp[0])
    }
    return buf.toByteArray().toString(charset)
}

suspend fun AsyncInputStream.readStringz(len: Int, charset: Charset = UTF8): String {
    val res = readBytesExact(len)
    val index = res.indexOf(0.toByte())
    return res.copyOf(if (index < 0) len else index).toString(charset)
}

suspend fun AsyncInputStream.readString(len: Int, charset: Charset = UTF8): String = readBytesExact(len).toString(charset)
