@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.io.lang.*
import korlibs.memory.*

private inline fun <T> smallBytesAlloc(size: Int = 16, block: (ByteArray) -> T): T = ByteArray(size).let(block)

suspend fun AsyncOutputStream.writeStream(source: AsyncInputStream): Long = source.copyTo(this)

suspend inline fun AsyncOutputStream.writeTempBytes(size: Int, block: ByteArray.() -> Unit) = write(ByteArray(size).apply(block))

suspend fun AsyncOutputStream.writeBytes(data: ByteArray): Unit = write(data, 0, data.size)
suspend fun AsyncOutputStream.writeBytes(data: ByteArray, position: Int, length: Int): Unit = write(data, position, length)
suspend fun AsyncOutputStream.write8(v: Int): Unit = write(v)
suspend fun AsyncOutputStream.write16LE(v: Int): Unit = smallBytesAlloc { it.set16LE(0, v); write(it, 0, 2) }
suspend fun AsyncOutputStream.write24LE(v: Int): Unit = smallBytesAlloc { it.set24LE(0, v); write(it, 0, 3) }
suspend fun AsyncOutputStream.write32LE(v: Int): Unit = smallBytesAlloc { it.set32LE(0, v); write(it, 0, 4) }
suspend fun AsyncOutputStream.write32LE(v: Long): Unit = smallBytesAlloc { it.set32LE(0, v); write(it, 0, 4) }
suspend fun AsyncOutputStream.write64LE(v: Long): Unit = smallBytesAlloc { it.set64LE(0, v); write(it, 0, 8) }
suspend fun AsyncOutputStream.writeF32LE(v: Float): Unit = smallBytesAlloc { it.setF32LE(0, v); write(it, 0, 4) }
suspend fun AsyncOutputStream.writeF64LE(v: Double): Unit = smallBytesAlloc { it.setF64LE(0, v); write(it, 0, 8) }

suspend fun AsyncOutputStream.write16BE(v: Int): Unit = smallBytesAlloc { it.set16BE(0, v); write(it, 0, 2) }
suspend fun AsyncOutputStream.write24BE(v: Int): Unit = smallBytesAlloc { it.set24BE(0, v); write(it, 0, 3) }
suspend fun AsyncOutputStream.write32BE(v: Int): Unit = smallBytesAlloc { it.set32BE(0, v); write(it, 0, 4) }
suspend fun AsyncOutputStream.write32BE(v: Long): Unit = smallBytesAlloc { it.set32BE(0, v); write(it, 0, 4) }
suspend fun AsyncOutputStream.write64BE(v: Long): Unit = smallBytesAlloc { it.set64BE(0, v); write(it, 0, 8) }
suspend fun AsyncOutputStream.writeF32BE(v: Float): Unit = smallBytesAlloc { it.setF32BE(0, v); write(it, 0, 4) }
suspend fun AsyncOutputStream.writeF64BE(v: Double): Unit = smallBytesAlloc { it.setF64BE(0, v); write(it, 0, 8) }

suspend fun AsyncOutputStream.writeCharArrayLE(array: CharArray): Unit = writeTempBytes(array.size * 2) { setArrayLE(0, array) }
suspend fun AsyncOutputStream.writeShortArrayLE(array: ShortArray): Unit = writeTempBytes(array.size * 2) { setArrayLE(0, array) }
suspend fun AsyncOutputStream.writeIntArrayLE(array: IntArray) = writeTempBytes(array.size * 4) { setArrayLE(0, array) }
suspend fun AsyncOutputStream.writeLongArrayLE(array: LongArray) = writeTempBytes(array.size * 8) { setArrayLE(0, array) }
suspend fun AsyncOutputStream.writeFloatArrayLE(array: FloatArray) = writeTempBytes(array.size * 4) { setArrayLE(0, array) }
suspend fun AsyncOutputStream.writeDoubleArrayLE(array: DoubleArray) = writeTempBytes(array.size * 8) { setArrayLE(0, array) }
suspend fun AsyncOutputStream.writeCharArrayBE(array: CharArray) = writeTempBytes(array.size * 2) { setArrayBE(0, array) }
suspend fun AsyncOutputStream.writeShortArrayBE(array: ShortArray) = writeTempBytes(array.size * 2) { setArrayBE(0, array) }
suspend fun AsyncOutputStream.writeIntArrayBE(array: IntArray) = writeTempBytes(array.size * 4) { setArrayBE(0, array) }
suspend fun AsyncOutputStream.writeLongArrayBE(array: LongArray) = writeTempBytes(array.size * 8) { setArrayBE(0, array) }
suspend fun AsyncOutputStream.writeFloatArrayBE(array: FloatArray) = writeTempBytes(array.size * 4) { setArrayBE(0, array) }
suspend fun AsyncOutputStream.writeDoubleArrayBE(array: DoubleArray) = writeTempBytes(array.size * 8) { setArrayBE(0, array) }

suspend fun AsyncOutputStream.writeU_VL(value: Int) = writeBytes(MemorySyncStreamToByteArray { writeU_VL(value) })

suspend fun AsyncOutputStream.writeStringz(str: String, charset: Charset = UTF8) = this.writeBytes(str.toBytez(charset))
suspend fun AsyncOutputStream.writeStringz(str: String, len: Int, charset: Charset = UTF8) = this.writeBytes(str.toBytez(len, charset))
suspend fun AsyncOutputStream.writeString(string: String, charset: Charset = UTF8): Unit = writeBytes(string.toByteArray(charset))
suspend fun AsyncOutputStream.writeStringVL(str: String, charset: Charset = UTF8) =
    this.apply { writeBytes(MemorySyncStreamToByteArray { writeStringVL(str, charset) }) }
