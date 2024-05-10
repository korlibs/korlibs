package korlibs.io.stream

import korlibs.datastructure.*
import korlibs.io.internal.*
import korlibs.io.lang.*
import korlibs.math.*
import korlibs.memory.*
import kotlin.math.*

open class MarkableSyncStream(val inp: SyncInputStream) : MarkableSyncInputStream {
	private var markTemp = ByteArrayDeque(8)
	private var markLimit = 0
	private var doReset = false

	override fun mark(readlimit: Int) {
		markTemp.clear()
		markLimit = readlimit
	}

	override fun reset() {
		doReset = true
	}

	override fun read(buffer: ByteArray, offset: Int, len: Int): Int {
		if (doReset) {
			return markTemp.read(buffer, offset, len).also {
				if (markTemp.availableRead <= 0) {
					doReset = false
				}
			}
		}
		val out = inp.read(buffer, offset, len)
		if (markLimit > 0) {
			val markRead = kotlin.math.min(markLimit, out)
			markLimit -= markRead
			markTemp.write(buffer, offset, markRead)
		}
		return out
	}
}

fun SyncInputStream.markable(): MarkableSyncInputStream = MarkableSyncStream(this)

inline fun <T> SyncStream.keepPosition(callback: () -> T): T {
	val old = this.position
	try {
		return callback()
	} finally {
		this.position = old
	}
}

class SequenceSyncStreamBase(val sequence: Sequence<ByteArray>) : SyncStreamBase() {
    override val seekable: Boolean = false
    val iterator = sequence.iterator()
    val deque = ByteArrayDeque()

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        while (deque.availableRead < len) {
            if (!iterator.hasNext()) break
            deque.write(iterator.next())
        }
        return deque.read(buffer, offset, len)
    }
}

fun sequenceSyncStream(block: suspend SequenceScope<ByteArray>.() -> Unit): SyncStream {
    return SequenceSyncStreamBase(sequence { block() }).toSyncStream()
}


class FillSyncStreamBase(val fill: Byte, override var length: Long) : SyncStreamBase() {
	override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
		val end = min(length, position + len)
		val actualLen = (end - position).toIntSafe()
		buffer.fill(fill, offset, offset + actualLen)
		return actualLen
	}

	override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) = Unit

	override fun close() = Unit
}

fun FillSyncStream(fillByte: Int = 0, length: Long = Long.MAX_VALUE) =
	FillSyncStreamBase(fillByte.toByte(), length).toSyncStream()

inline fun MemorySyncStreamToByteArray(initialCapacity: Int = 4096, callback: SyncStream.() -> Unit): ByteArray {
	val buffer = ByteArrayBuilder(initialCapacity)
	val s = MemorySyncStream(buffer)
	callback(s)
	return buffer.toByteArray()
}

class ReadonlySyncStreamBase(var data: ByteArray, val offset: Int = 0, val size: Int = data.size - offset) : SyncStreamBase() {
    val ilength: Int get() = size

    override var length: Long
        get() = data.size.toLong()
        set(value) = unsupported()

    fun checkPosition(position: Long) { if (position < 0) invalidOp("Invalid position $position") }

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        checkPosition(position)
        val ipos = position.toInt()
        //if (position !in 0 until ilength) return -1
        if (position !in 0 until ilength) return 0
        val end = min(this.ilength, ipos + len)
        val actualLen = max((end - ipos), 0)
        arraycopy(this.data, ipos + this.offset, buffer, offset, actualLen)
        return actualLen
    }

    override fun close() = Unit

    override fun toString(): String = "ReadonlySyncStreamBase(${data.size})"
}

fun SyncStream.readFastByteArrayInputStream(length: Int): FastByteArrayInputStream
    = FastByteArrayInputStream(readBytes(length))

fun SyncInputStream.readStringz(charset: Charset = UTF8, zero: Byte = 0): String {
	val buf = ByteArrayBuilder()
	return bytesTempPool.alloc { temp ->
		while (true) {
			val read = read(temp, 0, 1)
			if (read <= 0) break
			if (temp[0] == zero.toByte()) break
			buf.append(temp[0].toByte())
		}
		buf.toByteArray().toString(charset)
	}
}

fun SyncInputStream.readStringz(len: Int, charset: Charset = UTF8): String {
	val res = readBytes(len)
	val index = res.indexOf(0.toByte())
	return res.copyOf(if (index < 0) len else index).toString(charset)
}

fun SyncInputStream.readString(len: Int, charset: Charset = UTF8): String = readBytes(len).toString(charset)
fun SyncOutputStream.writeString(string: String, charset: Charset = UTF8): Unit =
	writeBytes(string.toByteArray(charset))

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

fun SyncOutputStream.writeStringz(str: String, charset: Charset = UTF8) =
	this.writeBytes(str.toBytez(charset))

fun SyncOutputStream.writeStringz(str: String, len: Int, charset: Charset = UTF8) =
	this.writeBytes(str.toBytez(len, charset))

fun SyncOutputStream.writeBytes(data: ByteArray): Unit = write(data, 0, data.size)
fun SyncOutputStream.writeBytes(data: ByteArray, position: Int, length: Int): Unit = write(data, position, length)

val SyncStream.eof: Boolean get () = this.available <= 0L
val SyncStream.hasMore: Boolean get () = this.available > 0L

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

fun SyncOutputStream.write8(v: Int): Unit = write(v)

fun SyncOutputStream.write16LE(v: Int) { write8(v and 0xFF); write8((v ushr 8) and 0xFF) }
fun SyncOutputStream.write24LE(v: Int) { write8(v and 0xFF); write8((v ushr 8) and 0xFF); write8((v ushr 16) and 0xFF) }
fun SyncOutputStream.write32LE(v: Int) { write8(v and 0xFF); write8((v ushr 8) and 0xFF); write8((v ushr 16) and 0xFF); write8((v ushr 24) and 0xFF) }
fun SyncOutputStream.write32LE(v: Long): Unit = write32LE(v.toInt())
fun SyncOutputStream.write64LE(v: Long) { write32LE(v.toInt()); write32LE((v ushr 32).toInt()) }
fun SyncOutputStream.writeF32LE(v: Float): Unit = write32LE(v.reinterpretAsInt())
fun SyncOutputStream.writeF64LE(v: Double): Unit = write64LE(v.reinterpretAsLong())

fun SyncOutputStream.write16BE(v: Int) { write8((v ushr 8) and 0xFF); write8(v and 0xFF) }
fun SyncOutputStream.write24BE(v: Int) { write8((v ushr 16) and 0xFF); write8((v ushr 8) and 0xFF); write8(v and 0xFF) }
fun SyncOutputStream.write32BE(v: Int) { write8((v ushr 24) and 0xFF); write8((v ushr 16) and 0xFF); write8((v ushr 8) and 0xFF); write8(v and 0xFF) }
fun SyncOutputStream.write32BE(v: Long): Unit = write32BE(v.toInt())
fun SyncOutputStream.write64BE(v: Long) { write32BE((v ushr 32).toInt()); write32BE(v.toInt()) }
fun SyncOutputStream.writeF32BE(v: Float): Unit = write32BE(v.reinterpretAsInt())
fun SyncOutputStream.writeF64BE(v: Double): Unit = write64BE(v.reinterpretAsLong())

fun String.openSync(charset: Charset = UTF8): SyncStream = toByteArray(charset).openSync("r")
fun String.openAsync(charset: Charset = UTF8): AsyncStream = toByteArray(charset).openSync("r").toAsync()

fun SyncOutputStream.writeStream(source: SyncInputStream): Unit = source.copyTo(this)

fun SyncInputStream.copyTo(target: SyncOutputStream) {
	bytesTempPool.alloc { chunk ->
		while (true) {
			val count = this.read(chunk)
			if (count <= 0) break
			target.write(chunk, 0, count)
		}
	}
}


fun SyncStream.writeToAlign(alignment: Int, value: Int = 0) {
	val nextPosition = position.nextAlignedTo(alignment.toLong())
	val data = ByteArray((nextPosition - position).toInt())
	data.fill(value.toByte())
	writeBytes(data)
}

fun SyncStream.skip(count: Int): SyncStream {
	position += count
	return this
}

fun SyncStream.skipToAlign(alignment: Int) {
	val nextPosition = position.nextAlignedTo(alignment.toLong())
	readBytes((nextPosition - position).toInt())
}

fun SyncStream.truncate() { length = position }

fun SyncOutputStream.writeCharArrayLE(array: CharArray) =
	writeBytes(ByteArray(array.size * 2).apply { setArrayLE(0, array) })

fun SyncOutputStream.writeShortArrayLE(array: ShortArray) =
	writeBytes(ByteArray(array.size * 2).apply { setArrayLE(0, array) })

fun SyncOutputStream.writeIntArrayLE(array: IntArray) =
	writeBytes(ByteArray(array.size * 4).apply { setArrayLE(0, array) })

fun SyncOutputStream.writeLongArrayLE(array: LongArray) =
	writeBytes(ByteArray(array.size * 8).apply { setArrayLE(0, array) })

fun SyncOutputStream.writeFloatArrayLE(array: FloatArray) =
	writeBytes(ByteArray(array.size * 4).apply { setArrayLE(0, array) })

fun SyncOutputStream.writeDoubleArrayLE(array: DoubleArray) =
	writeBytes(ByteArray(array.size * 8).apply { setArrayLE(0, array) })

fun SyncOutputStream.writeCharArrayBE(array: CharArray) =
	writeBytes(ByteArray(array.size * 2).apply { setArrayBE(0, array) })

fun SyncOutputStream.writeShortArrayBE(array: ShortArray) =
	writeBytes(ByteArray(array.size * 2).apply { setArrayBE(0, array) })

fun SyncOutputStream.writeIntArrayBE(array: IntArray) =
	writeBytes(ByteArray(array.size * 4).apply { setArrayBE(0, array) })

fun SyncOutputStream.writeLongArrayBE(array: LongArray) =
	writeBytes(ByteArray(array.size * 8).apply { setArrayBE(0, array) })

fun SyncOutputStream.writeFloatArrayBE(array: FloatArray) =
	writeBytes(ByteArray(array.size * 4).apply { setArrayBE(0, array) })

fun SyncOutputStream.writeDoubleArrayBE(array: DoubleArray) =
	writeBytes(ByteArray(array.size * 8).apply { setArrayBE(0, array) })

// Variable Length

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

fun SyncOutputStream.writeU_VL(v: Int) {
	var value = v
	while (true) {
		val c = value and 0x7f
		value = value ushr 7
		if (value == 0) {
			write8(c)
			break
		}
		write8(c or 0x80)
	}
}

fun SyncOutputStream.writeS_VL(v: Int) {
	val sign = if (v < 0) 1 else 0
	writeU_VL(sign or ((if (v < 0) -v - 1 else v) shl 1))
}

fun SyncOutputStream.writeStringVL(str: String, charset: Charset = UTF8) {
	val bytes = str.toByteArray(charset)
	writeU_VL(bytes.size)
	writeBytes(bytes)
}

fun SyncStream.readStringVL(charset: Charset = UTF8): String {
	val bytes = ByteArray(readU_VL())
	readExact(bytes, 0, bytes.size)
	return bytes.toString(charset)
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
