@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.io.lang.*
import korlibs.memory.*

fun SyncInputStream.readExact(out: ByteArray, offset: Int, len: Int) {
    var ooffset = offset
    var remaining = len
    while (remaining > 0) {
        val read = read(out, ooffset, remaining)
        if (read <= 0) {
            throw RuntimeException("EOF")
        }
        remaining -= read
        ooffset += read
    }
}

fun SyncInputStream.read(data: ByteArray): Int = read(data, 0, data.size)
fun SyncInputStream.read(data: UByteArray): Int = read(data.asByteArray(), 0, data.size)

fun SyncInputStream.readBytesExact(len: Int): ByteArray = ByteArray(len).apply { readExact(this, 0, len) }

fun SyncInputStream.readU8(): Int = read()
fun SyncInputStream.readS8(): Int = read().toByte().toInt()

fun SyncInputStream.readU16LE(): Int = (readU8()) or (readU8() shl 8)
fun SyncInputStream.readU24LE(): Int = (readU8()) or (readU8() shl 8) or (readU8() shl 16)
fun SyncInputStream.readU32LE(): Long = ((readU8()) or (readU8() shl 8) or (readU8() shl 16) or (readU8() shl 24)).toLong() and 0xFFFFFFFFL

fun SyncInputStream.readS16LE(): Int = readU16LE().signExtend(16)
fun SyncInputStream.readS24LE(): Int = readU24LE().signExtend(24)
fun SyncInputStream.readS32LE(): Int = (readU8()) or (readU8() shl 8) or (readU8() shl 16) or (readU8() shl 24)
fun SyncInputStream.readS64LE(): Long = readU32LE() or (readU32LE() shl 32)

fun SyncInputStream.readF32LE(): Float = readS32LE().reinterpretAsFloat()
fun SyncInputStream.readF64LE(): Double = readS64LE().reinterpretAsDouble()

fun SyncInputStream.readU16BE(): Int = (readU8() shl 8) or (readU8())
fun SyncInputStream.readU24BE(): Int = (readU8() shl 16) or (readU8() shl 8) or (readU8())
fun SyncInputStream.readU32BE(): Long = ((readU8() shl 24) or (readU8() shl 16) or (readU8() shl 8) or (readU8())).toLong() and 0xFFFFFFFFL

fun SyncInputStream.readS16BE(): Int = readU16BE().signExtend(16)
fun SyncInputStream.readS24BE(): Int = readU24BE().signExtend(24)
fun SyncInputStream.readS32BE(): Int = (readU8() shl 24) or (readU8() shl 16) or (readU8() shl 8) or (readU8())
fun SyncInputStream.readS64BE(): Long = (readU32BE() shl 32) or (readU32BE())

fun SyncInputStream.readF32BE(): Float = readS32BE().reinterpretAsFloat()
fun SyncInputStream.readF64BE(): Double = readS64BE().reinterpretAsDouble()

fun SyncInputStream.readUByteArray(count: Int): UByteArray = readBytesExact(count).asUByteArray()

fun SyncInputStream.readShortArrayLE(count: Int): ShortArray = readBytesExact(count * 2).getS16ArrayLE(0, count)
fun SyncInputStream.readShortArrayBE(count: Int): ShortArray = readBytesExact(count * 2).getS16ArrayBE(0, count)

fun SyncInputStream.readCharArrayLE(count: Int): CharArray = readBytesExact(count * 2).getU16ArrayLE(0, count)
fun SyncInputStream.readCharArrayBE(count: Int): CharArray = readBytesExact(count * 2).getU16ArrayBE(0, count)

fun SyncInputStream.readIntArrayLE(count: Int): IntArray = readBytesExact(count * 4).getS32ArrayLE(0, count)
fun SyncInputStream.readIntArrayBE(count: Int): IntArray = readBytesExact(count * 4).getS32ArrayBE(0, count)

fun SyncInputStream.readLongArrayLE(count: Int): LongArray = readBytesExact(count * 8).getS64ArrayLE(0, count)
fun SyncInputStream.readLongArrayBE(count: Int): LongArray = readBytesExact(count * 8).getS64ArrayBE(0, count)

fun SyncInputStream.readFloatArrayLE(count: Int): FloatArray = readBytesExact(count * 4).getF32ArrayLE(0, count)
fun SyncInputStream.readFloatArrayBE(count: Int): FloatArray = readBytesExact(count * 4).getF32ArrayBE(0, count)

fun SyncInputStream.readDoubleArrayLE(count: Int): DoubleArray = readBytesExact(count * 8).getF64ArrayLE(0, count)
fun SyncInputStream.readDoubleArrayBE(count: Int): DoubleArray = readBytesExact(count * 8).getF64ArrayBE(0, count)

fun SyncInputStream.copyTo(target: SyncOutputStream) {
    val chunk = ByteArray(0x10000)
    while (true) {
        val count = this.read(chunk)
        if (count <= 0) break
        target.write(chunk, 0, count)
    }
}

fun SyncInputStream.readExactTo(buffer: ByteArray, offset: Int = 0, length: Int = buffer.size - offset): Int {
    val end = offset + length
    var pos = offset
    while (true) {
        val read = this.read(buffer, pos, end - pos)
        if (read <= 0) break
        pos += read
    }
    return pos - offset
}

fun SyncInputStream.readU_VL(): Int {
    var result = readU8()
    if ((result and 0x80) == 0) return result
    result = (result and 0x7f) or (readU8() shl 7)
    if ((result and 0x4000) == 0) return result
    result = (result and 0x3fff) or (readU8() shl 14)
    if ((result and 0x200000) == 0) return result
    result = (result and 0x1fffff) or (readU8() shl 21)
    if ((result and 0x10000000) == 0) return result
    result = (result and 0xfffffff) or (readU8() shl 28)
    return result
}

fun SyncInputStream.readS_VL(): Int {
    val v = readU_VL()
    val sign = ((v and 1) != 0)
    val uvalue = v ushr 1
    return if (sign) -uvalue - 1 else uvalue
}

fun SyncInputStream.markable(): MarkableSyncInputStream = MarkableSyncStream(this)

fun SyncInputStream.readStringz(len: Int): String {
    val res = readBytes(len)
    val index = res.indexOf(0.toByte())
    return res.copyOf(if (index < 0) len else index).decodeToString()
}

fun SyncInputStream.readString(len: Int): String = readBytes(len).decodeToString()

fun SyncInputStream.readBytes(len: Int): ByteArray {
    val bytes = ByteArray(len)
    val out = read(bytes, 0, len)
    return if (out != len) bytes.copyOf(out) else bytes
}


fun SyncInputStream.readStringz(charset: Charset = UTF8, zero: Byte = 0): String {
    val buf = ByteArrayBuilder()
    val temp = ByteArray(0x1000)
    while (true) {
        val read = read(temp, 0, 1)
        if (read <= 0) break
        if (temp[0] == zero) break
        buf.append(temp[0])
    }
    return buf.toByteArray().toString(charset)
}

fun SyncInputStream.readStringz(len: Int, charset: Charset = UTF8): String {
    val res = readBytes(len)
    val index = res.indexOf(0.toByte())
    return res.copyOf(if (index < 0) len else index).toString(charset)
}
fun SyncInputStream.readString(len: Int, charset: Charset = UTF8): String = readBytes(len).toString(charset)

fun String.openSync(charset: Charset = UTF8): SyncStream = toByteArray(charset).openSync("r")
fun String.openAsync(charset: Charset = UTF8): AsyncStream = toByteArray(charset).openSync("r").toAsync()

fun SyncStream.readStringVL(charset: Charset = UTF8): String {
    val bytes = ByteArray(readU_VL())
    readExact(bytes, 0, bytes.size)
    return bytes.toString(charset)
}
