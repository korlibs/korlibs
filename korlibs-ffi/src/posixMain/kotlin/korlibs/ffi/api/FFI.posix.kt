@file:OptIn(ExperimentalForeignApi::class)

package korlibs.ffi.api

import kotlinx.cinterop.*
import platform.posix.*

actual fun FFIDLOpen(name: String): COpaquePointer? = platform.posix.dlopen(name, platform.posix.RTLD_LAZY or 1)
actual fun FFIDLClose(lib: COpaquePointer?): Unit { platform.posix.dlclose(lib) }
actual fun FFIDLSym(lib: COpaquePointer?, name: String): COpaquePointer? = platform.posix.dlsym(lib, name)

internal actual fun transferMemory(address: Long, data: ByteArray, offset: Int, size: Int, toPointer: Boolean) {
    if (size == 0) return
    // Use byte-by-byte copy instead of memcpy to avoid platform-dependent size_t bit-width
    // issues when commonizing posixMain across 32-bit (watchosArm32) and 64-bit targets.
    data.usePinned {
        val dataPtr: CPointer<ByteVar> = it.addressOf(offset)
        val memPtr: CPointer<ByteVar> = address.toCPointer()!!
        if (toPointer) {
            for (i in 0 until size) dataPtr[i] = memPtr[i]
        } else {
            for (i in 0 until size) memPtr[i] = dataPtr[i]
        }
    }
}
