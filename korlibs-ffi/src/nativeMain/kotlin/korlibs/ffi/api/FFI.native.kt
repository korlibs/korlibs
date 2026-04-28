@file:OptIn(ExperimentalForeignApi::class)

package korlibs.ffi.api

import kotlinx.cinterop.*
import kotlin.experimental.*

actual val isSupportedFFI: Boolean = true

actual fun FFIPointer.getF64(offset: Int): Double = (this.address + offset).toCPointer<DoubleVar>()!![0]
actual fun FFIPointer.getF32(offset: Int): Float = (this.address + offset).toCPointer<FloatVar>()!![0]
actual fun FFIPointer.getI64(offset: Int): Long = (this.address + offset).toCPointer<LongVar>()!![0]
actual fun FFIPointer.getI32(offset: Int): Int = (this.address + offset).toCPointer<IntVar>()!![0]
actual fun FFIPointer.getI16(offset: Int): Short = (this.address + offset).toCPointer<ShortVar>()!![0]
actual fun FFIPointer.getI8(offset: Int): Byte = (this.address + offset).toCPointer<ByteVar>()!![0]

actual fun FFIPointer.setF64(offset: Int, value: Double) { (this.address + offset).toCPointer<DoubleVar>()!![0] = value }
actual fun FFIPointer.setF32(offset: Int, value: Float) { (this.address + offset).toCPointer<FloatVar>()!![0] = value }
actual fun FFIPointer.setI64(offset: Int, value: Long) { (this.address + offset).toCPointer<LongVar>()!![0] = value }
actual fun FFIPointer.setI32(offset: Int, value: Int) { (this.address + offset).toCPointer<IntVar>()!![0] = value }
actual fun FFIPointer.setI16(offset: Int, value: Short) { (this.address + offset).toCPointer<ShortVar>()!![0] = value }
actual fun FFIPointer.setI8(offset: Int, value: Byte) { (this.address + offset).toCPointer<ByteVar>()!![0] = value }

expect fun FFIDLOpen(name: String): COpaquePointer?
expect fun FFIDLClose(lib: COpaquePointer?): Unit
expect fun FFIDLSym(lib: COpaquePointer?, name: String): COpaquePointer?
@OptIn(ExperimentalNativeApi::class)
fun FFIDLOpenPlatform(
    common: String? = null,
    linux: String? = null,
    macos: String? = null,
    windows: String? = null,
): COpaquePointer? = FFIDLOpen(when (Platform.osFamily) {
    OsFamily.MACOSX, OsFamily.IOS, OsFamily.TVOS, OsFamily.WATCHOS -> macos ?: common
    OsFamily.LINUX -> linux ?: common
    OsFamily.WINDOWS -> windows ?: common
    OsFamily.WASM, OsFamily.UNKNOWN, OsFamily.ANDROID -> common
} ?: error("Can't find library name"))
