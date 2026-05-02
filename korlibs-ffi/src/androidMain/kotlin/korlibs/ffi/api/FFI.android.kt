package korlibs.ffi.api

actual val isSupportedFFI: Boolean = false

actual fun FFIPointer.getF64(offset: Int): Double = TODO()
actual fun FFIPointer.getF32(offset: Int): Float = TODO()
actual fun FFIPointer.getI64(offset: Int): Long = TODO()
actual fun FFIPointer.getI32(offset: Int): Int = TODO()
actual fun FFIPointer.getI16(offset: Int): Short = TODO()
actual fun FFIPointer.getI8(offset: Int): Byte = TODO()

actual fun FFIPointer.setF64(offset: Int, value: Double): Unit = TODO()
actual fun FFIPointer.setF32(offset: Int, value: Float): Unit = TODO()
actual fun FFIPointer.setI64(offset: Int, value: Long): Unit = TODO()
actual fun FFIPointer.setI32(offset: Int, value: Int): Unit = TODO()
actual fun FFIPointer.setI16(offset: Int, value: Short): Unit = TODO()
actual fun FFIPointer.setI8(offset: Int, value: Byte): Unit = TODO()

internal actual fun transferMemory(address: Long, data: ByteArray, offset: Int, size: Int, toPointer: Boolean) {
    TODO()
}