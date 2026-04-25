package korlibs.ffi.api

import com.sun.jna.Pointer

actual val isSupportedFFI: Boolean = true

actual fun FFIPointer.getF64(offset: Int): Double = Pointer(address).getDouble(offset.toLong())
actual fun FFIPointer.getF32(offset: Int): Float = Pointer(address).getFloat(offset.toLong())
actual fun FFIPointer.getI64(offset: Int): Long = Pointer(address).getLong(offset.toLong())
actual fun FFIPointer.getI32(offset: Int): Int = Pointer(address).getInt(offset.toLong())
actual fun FFIPointer.getI16(offset: Int): Short = Pointer(address).getShort(offset.toLong())
actual fun FFIPointer.getI8(offset: Int): Byte = Pointer(address).getByte(offset.toLong())

actual fun FFIPointer.setF64(offset: Int, value: Double) = Pointer(address).setDouble(offset.toLong(), value)
actual fun FFIPointer.setF32(offset: Int, value: Float) = Pointer(address).setFloat(offset.toLong(), value)
actual fun FFIPointer.setI64(offset: Int, value: Long) = Pointer(address).setLong(offset.toLong(), value)
actual fun FFIPointer.setI32(offset: Int, value: Int) = Pointer(address).setInt(offset.toLong(), value)
actual fun FFIPointer.setI16(offset: Int, value: Short) = Pointer(address).setShort(offset.toLong(), value)
actual fun FFIPointer.setI8(offset: Int, value: Byte) = Pointer(address).setByte(offset.toLong(), value)

internal actual fun transferMemory(address: Long, data: ByteArray, offset: Int, size: Int, toPointer: Boolean) {
    if (toPointer) {
        Pointer(address).write(0, data, offset, size)
    } else {
        Pointer(address).read(0, data, offset, size)
    }
}