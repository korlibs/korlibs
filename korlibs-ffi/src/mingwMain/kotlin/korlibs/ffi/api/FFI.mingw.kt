@file:OptIn(ExperimentalForeignApi::class)

package korlibs.ffi.api

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.usePinned
import platform.windows.FreeLibrary
import platform.windows.GetProcAddress
import platform.windows.LoadLibraryW

actual fun FFIDLOpen(name: String): COpaquePointer? = LoadLibraryW(name)?.reinterpret()

actual fun FFIDLClose(lib: COpaquePointer?) { FreeLibrary(lib?.reinterpret()) }

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
