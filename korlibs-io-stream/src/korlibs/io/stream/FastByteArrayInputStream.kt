@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.io.lang.*
import korlibs.io.util.*
import korlibs.memory.*
import kotlin.collections.indexOf

@OptIn(ExperimentalStdlibApi::class)
class FastByteArrayInputStream(val ba: ByteArray, offset: Int = 0, val start: Int = 0, val end: Int = ba.size) {
    @ExperimentalStdlibApi
    var offset = offset + start

    var position: Int
        get() = offset - start
        set(value) {
            offset = start + value
        }
    val length: Int get() = end - start
    val available: Int get() = end - offset
    val hasMore: Boolean get() = available > 0
    val eof: Boolean get() = !hasMore

    fun Int.coerceRange() = this.coerceIn(start, end)

    fun extractBytes(offset: Int, length: Int): ByteArray {
        val start = this.start + offset
        val end = start + length
        return ba.copyOfRange(start, end)
    }

    fun extractString(offset: Int, length: Int): String {
        //return extractBytes(offset, length).toString(charset)
        val start = this.start + offset
        val end = start + length
        return ba.copyOfRange(start, end).decodeToString()
    }

    fun readSlice(size: Int): FastByteArrayInputStream {
        val out = sliceWithSize(position, size)
        offset += size
        return out
    }

    fun sliceStart(offset: Int = 0) = FastByteArrayInputStream(ba, 0, (start + offset).coerceRange(), end)
    fun clone() = FastByteArrayInputStream(ba, position, start, end)
    fun sliceWithSize(offset: Int, len: Int) =
        FastByteArrayInputStream(ba, 0, (start + offset).coerceRange(), (start + offset + len).coerceRange())

    private fun offset(count: Int): Int {
        val out = offset
        offset += count
        return out
    }

    // Skipping
    fun skip(count: Int) {
        offset = (offset + count).coerceIn(start, end)
    }

    fun unread(count: Int): Unit = skip(-count)

    fun skipToAlign(count: Int) {
        val nextPosition = offset.nextAlignedTo(count)
        skip(nextPosition - offset)
    }

    fun readBytesExact(len: Int): ByteArray {
        val out = ba.copyOfRange(offset, offset + len)
        offset += len
        return out
    }

    fun readAll(): ByteArray = readBytesExact(available)

    // 8 bit
    fun readS8(): Int = ba.getS8(offset(1))
    fun readU8(): Int = ba.getU8(offset(1))

    // 16 bits
    fun readS16LE(): Int = ba.getS16LE(offset(2))

    fun readS16BE(): Int = ba.getS16BE(offset(2))
    fun readU16LE(): Int = ba.getU16LE(offset(2))
    fun readU16BE(): Int = ba.getU16BE(offset(2))

    // 24 bits
    fun readS24LE(): Int = ba.getS24LE(offset(3))
    fun readS24BE(): Int = ba.getS24BE(offset(3))
    fun readU24LE(): Int = ba.getU24LE(offset(3))
    fun readU24BE(): Int = ba.getU24BE(offset(3))

    // 32 bits
    fun readS32LE(): Int = ba.getS32LE(offset(4))
    fun readS32BE(): Int = ba.getS32BE(offset(4))
    fun readU32LE(): Long = ba.getU32LE(offset(4))
    fun readU32BE(): Long = ba.getU32BE(offset(4))

    // 32 bits FLOAT
    fun readF32LE(): Float = ba.getF32LE(offset(4))
    fun readF32BE(): Float = ba.getF32BE(offset(4))

    // 64 bits FLOAT
    fun readF64LE(): Double = ba.getF64LE(offset(8))
    fun readF64BE(): Double = ba.getF64BE(offset(8))

    // 64 bits Long
    fun readS64LE(): Long = ba.getS64LE(offset(8))
    fun readS64BE(): Long = ba.getS64BE(offset(8))

    // Bytes
    fun read(data: ByteArray, offset: Int = 0, count: Int = data.size - offset): Int {
        val readCount = count.coerceAtMost(available)
        arraycopy(this.ba, this.offset, data, offset, readCount)
        this.offset += count
        return readCount
    }

    fun readBytes(count: Int) = ba.getS8Array(offset(count), count)

    // Arrays
    fun readShortArrayLE(count: Int): ShortArray = ba.getS16ArrayLE(offset(count * 2), count)
    fun readShortArrayBE(count: Int): ShortArray = ba.getS16ArrayBE(offset(count * 2), count)

    fun readCharArrayLE(count: Int): CharArray = ba.getU16ArrayLE(offset(count * 2), count)
    fun readCharArrayBE(count: Int): CharArray = ba.getU16ArrayBE(offset(count * 2), count)

    fun readIntArrayLE(count: Int): IntArray = ba.getS32ArrayLE(offset(count * 4), count)
    fun readIntArrayBE(count: Int): IntArray = ba.getS32ArrayBE(offset(count * 4), count)

    fun readLongArrayLE(count: Int): LongArray = ba.getS64ArrayLE(offset(count * 8), count)
    fun readLongArrayBE(count: Int): LongArray = ba.getS64ArrayBE(offset(count * 8), count)

    fun readFloatArrayLE(count: Int): FloatArray = ba.getF32ArrayLE(offset(count * 4), count)
    fun readFloatArrayBE(count: Int): FloatArray = ba.getF32ArrayBE(offset(count * 4), count)

    fun readDoubleArrayLE(count: Int): DoubleArray = ba.getF64ArrayLE(offset(count * 8), count)
    fun readDoubleArrayBE(count: Int): DoubleArray = ba.getF64ArrayBE(offset(count * 8), count)

    // Variable Length
    fun readU_VL(): Int {
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

    fun readS_VL(): Int {
        val v = readU_VL()
        val sign = ((v and 1) != 0)
        val uvalue = v ushr 1
        return if (sign) -uvalue - 1 else uvalue
    }

    // String
    fun extractString(offset: Int, length: Int, charset: Charset = UTF8): String {
        //return extractBytes(offset, length).toString(charset)
        val start = this.start + offset
        val end = start + length
        return ba.toString(charset, start, end)
    }

    fun readString(len: Int, charset: Charset = UTF8) = readBytes(len).toString(charset)

    fun readStringz(len: Int, charset: Charset = UTF8): String {
        val res = readBytes(len)
        val index = res.indexOf(0.toByte())
        return res.copyOf(if (index < 0) len else index).toString(charset)
    }

    fun readStringz(charset: Charset = UTF8): String {
        val startOffset = offset
        val index = ba.indexOf(0.toByte(), offset)
        val end = if (index >= 0) index else ba.size
        val str = ba.copyOfRange(startOffset, end).toString(charset)
        offset = if (index >= 0) end + 1 else end
        return str
    }

    fun readStringVL(charset: Charset = UTF8): String = readString(readU_VL(), charset)


    fun getAllBytes(): ByteArray = ba.copyOfRange(start, end)
    fun getBackingArrayUnsafe(): ByteArray = ba
    //fun toByteArray(): ByteArray = ba.copyOfRange(start, end)
}

fun ByteArray.openFastStream(offset: Int = 0): FastByteArrayInputStream = FastByteArrayInputStream(this, offset)
