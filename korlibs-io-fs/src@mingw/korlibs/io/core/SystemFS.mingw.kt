@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.posix.*

actual val defaultSyncSystemFS: SyncSystemFS = MingwSyncSystemFS
actual val defaultSystemFS: SystemFS by lazy { SyncSystemFS.toAsync(Dispatchers.IO) }

object MingwSyncSystemFS : SyncSystemFSNativeBase() {
    override val fileSeparatorChar: Char = '\\'
    override val pathSeparatorChar: Char = ';'

    override fun getcwd(): String = getExecutableDirectory()

    override fun mkdir(path: String): Boolean = platform.posix.mkdir(path) == 0

    fun getExecutablePath(): String = kotlinx.cinterop.memScoped {
        val maxSize = 4096
        val data = allocArray<kotlinx.cinterop.UShortVar>(maxSize + 1)
        platform.windows.GetModuleFileNameW(null, data.reinterpret(), maxSize.convert())
        data.toKString()
    }.replace('\\', '/')

    fun getExecutableDirectory(): String = getExecutablePath().substringBeforeLast('/')

    override fun createSyncFileSystemFS(file: CPointer<FILE>?): SyncFileSystemFSNativeBase = object : SyncFileSystemFSNativeBase(file) {
        override fun ftruncate64(len: Long) { ftruncate64(fd, len) }
        override fun ftell64(): Long = _ftelli64(file)
        override fun fseek64(pos: Long, origin: Int): Long {
            _fseeki64(file, pos, origin)
            return _ftelli64(file)
        }
    }

}
