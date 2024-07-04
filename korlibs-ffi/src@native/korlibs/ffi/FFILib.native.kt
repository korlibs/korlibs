@file:OptIn(ExperimentalForeignApi::class)

package korlibs.ffi

import korlibs.memory.*
import kotlinx.cinterop.*
import kotlin.reflect.*

actual fun FFILibSym(lib: FFILib): FFILibSym {
    return object : FFILibSym {
    }
}

actual fun <T> FFICreateProxyFunction(type: KType, handler: (args: Array<Any?>) -> Any?): T {
    TODO()
}
//actual typealias FFIPointer = kotlin.native.internal.NativePtr
actual class FFIPointer(val ptr: NativePtr) {
    val long: Long get() = ptr.toLong()
}

actual typealias FFIMemory = ByteArray

//actual val FFI_SUPPORTED: Boolean = true
actual val FFI_SUPPORTED: Boolean = false

actual fun CreateFFIMemory(size: Int): FFIMemory = ByteArray(size)
actual fun CreateFFIMemory(bytes: ByteArray): FFIMemory = bytes

actual inline fun <T> FFIMemory.usePointer(block: (pointer: FFIPointer) -> T): T = this@usePointer.usePinned { block(FFIPointer(it.startAddressOf.rawValue)) }
actual inline fun <T> Buffer.usePointer(block: (pointer: FFIPointer) -> T): T = this@usePointer.data.usePinned { block(FFIPointer(it.addressOf(this@usePointer.size).rawValue)) }

actual val FFIMemory.pointer: FFIPointer get() = TODO()
actual val Buffer.pointer: FFIPointer get() = TODO()

actual fun arraycopy(src: FFIPointer, srcPos: Int, dst: FFIPointer, dstPos: Int, length: Int) {
    arraycopySlow(src, srcPos, dst, dstPos, length)
}

actual fun CreateFFIPointer(ptr: Long): FFIPointer? = ptr.toCPointer<ByteVar>().rawValue.let { FFIPointer(it) }

actual val FFI_POINTER_SIZE: Int get() = sizeOf<COpaquePointerVar>().toInt()

actual val FFIPointer?.address: Long get() = this?.long ?: 0L

actual fun FFIPointer.getStringz(): String = _getStringz()
actual fun FFIPointer.getWideStringz(): String = _getWideStringz()

actual val FFIPointer?.str: String get() = this.toString()

actual fun FFIPointer.getIntArray(size: Int, byteOffset: Int): IntArray = _getIntArray(size, byteOffset)

actual fun <T> FFIPointer.castToFunc(type: KType, config: FFIFuncConfig): T {
    TODO("castToFunc not implemented yet")
}

private inline fun <T : CPointed> FFIPointer.offset(byteOffset: Int): CPointer<T> = (this.long.toCPointer<ByteVar>() + byteOffset)!!.reinterpret<T>()

actual fun FFIPointer.getS8(byteOffset: Int): Byte = this.offset<ByteVar>(byteOffset)[0]
actual fun FFIPointer.getS16(byteOffset: Int): Short = this.offset<ShortVar>(byteOffset)[0]
actual fun FFIPointer.getS32(byteOffset: Int): Int = this.offset<IntVar>(byteOffset)[0]
actual fun FFIPointer.getS64(byteOffset: Int): Long = this.offset<LongVar>(byteOffset)[0]
actual fun FFIPointer.getF32(byteOffset: Int): Float = this.offset<FloatVar>(byteOffset)[0]
actual fun FFIPointer.getF64(byteOffset: Int): Double = this.offset<DoubleVar>(byteOffset)[0]

actual fun FFIPointer.set8(value: Byte, byteOffset: Int): Unit { this.offset<ByteVar>(byteOffset)[0] = value }
actual fun FFIPointer.set16(value: Short, byteOffset: Int): Unit { this.offset<ShortVar>(byteOffset)[0] = value }
actual fun FFIPointer.set32(value: Int, byteOffset: Int): Unit { this.offset<IntVar>(byteOffset)[0] = value }
actual fun FFIPointer.set64(value: Long, byteOffset: Int): Unit { this.offset<LongVar>(byteOffset)[0] = value }
actual fun FFIPointer.setF32(value: Float, byteOffset: Int): Unit { this.offset<FloatVar>(byteOffset)[0] = value }
actual fun FFIPointer.setF64(value: Double, byteOffset: Int): Unit { this.offset<DoubleVar>(byteOffset)[0] = value }

actual class FFIArena actual constructor() {
    val arena = Arena()

    actual fun allocBytes(size: Int): FFIPointer = FFIPointer(arena.alloc(size, 16).rawPtr)
    actual fun clear(): Unit = arena.clear()
}
