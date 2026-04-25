@file:OptIn(ExperimentalForeignApi::class)

package korlibs.ffi.api

import kotlinx.cinterop.*
import platform.windows.*

actual fun FFIDLOpen(name: String): COpaquePointer? = LoadLibraryW(name)?.reinterpret()
actual fun FFIDLClose(lib: COpaquePointer?): Unit { FreeLibrary(lib?.reinterpret()) }
actual fun FFIDLSym(lib: COpaquePointer?, name: String): COpaquePointer? = GetProcAddress(lib?.reinterpret(), name)?.reinterpret()

internal actual fun transferMemory(address: Long, data: ByteArray, offset: Int, size: Int, toPointer: Boolean) {
    if (size == 0) return
    // Use byte-by-byte copy to avoid platform-dependent size_t bit-width commonization errors.
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
