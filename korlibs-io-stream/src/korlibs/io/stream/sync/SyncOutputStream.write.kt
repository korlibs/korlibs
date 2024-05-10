@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.io.lang.*
import korlibs.memory.*

fun SyncOutputStream.writeBytes(data: ByteArray): Unit = write(data, 0, data.size)
fun SyncOutputStream.writeBytes(data: ByteArray, position: Int, length: Int): Unit = write(data, position, length)

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

fun SyncOutputStream.writeStream(source: SyncInputStream): Unit = source.copyTo(this)

private fun SyncOutputStream.writeTempBytes(size: Int, set: (ByteArray) -> Unit) {
    writeBytes(ByteArray(size).also(set) )
}

fun SyncOutputStream.writeCharArrayLE(array: CharArray) = writeTempBytes(array.size * 2) { it.setArrayLE(0, array) }
fun SyncOutputStream.writeShortArrayLE(array: ShortArray) = writeTempBytes(array.size * 2) { it.setArrayLE(0, array) }
fun SyncOutputStream.writeIntArrayLE(array: IntArray) = writeTempBytes(array.size * 4) { it.setArrayLE(0, array) }
fun SyncOutputStream.writeLongArrayLE(array: LongArray) = writeTempBytes(array.size * 8) { it.setArrayLE(0, array) }
fun SyncOutputStream.writeFloatArrayLE(array: FloatArray) = writeTempBytes(array.size * 4) { it.setArrayLE(0, array) }
fun SyncOutputStream.writeDoubleArrayLE(array: DoubleArray) = writeTempBytes(array.size * 8) { it.setArrayLE(0, array) }
fun SyncOutputStream.writeCharArrayBE(array: CharArray) = writeTempBytes(array.size * 2) { it.setArrayBE(0, array) }
fun SyncOutputStream.writeShortArrayBE(array: ShortArray) = writeTempBytes(array.size * 2) { it.setArrayBE(0, array) }
fun SyncOutputStream.writeIntArrayBE(array: IntArray) = writeTempBytes(array.size * 4) { it.setArrayBE(0, array) }
fun SyncOutputStream.writeLongArrayBE(array: LongArray) = writeTempBytes(array.size * 8) { it.setArrayBE(0, array) }
fun SyncOutputStream.writeFloatArrayBE(array: FloatArray) = writeTempBytes(array.size * 4) { it.setArrayBE(0, array) }
fun SyncOutputStream.writeDoubleArrayBE(array: DoubleArray) = writeTempBytes(array.size * 8) { it.setArrayBE(0, array) }

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

fun SyncOutputStream.writeString(string: String, charset: Charset = UTF8): Unit = writeBytes(string.toByteArray(charset))
fun SyncOutputStream.writeStringz(str: String, charset: Charset = UTF8) = this.writeBytes(str.toBytez(charset))
fun SyncOutputStream.writeStringz(str: String, len: Int, charset: Charset = UTF8) = this.writeBytes(str.toBytez(len, charset))
fun SyncOutputStream.writeStringVL(str: String, charset: Charset = UTF8) {
    str.toByteArray(charset).also {
        writeU_VL(it.size)
        writeBytes(it)
    }
}