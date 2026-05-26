@file:OptIn(UnsafeNumber::class)

package korlibs.io.core

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.FILE
import platform.posix.PATH_MAX
import platform.posix.fseek
import platform.posix.ftell
import platform.posix.ftruncate
import platform.posix.readlink
import platform.posix.realpath

@OptIn(ExperimentalForeignApi::class)
open class SyncSystemFSPosixBase : SyncSystemFSNativeBase() {
    override fun mkdir(path: String): Boolean = platform.posix.mkdir(path, 511.convert()) == 0

    protected fun posixReadlink(path: String): String? = memScoped {
        val addr = allocArray<ByteVar>(PATH_MAX)
        val finalSize = readlink(path, addr, PATH_MAX.convert()).toInt()
        if (finalSize < 0) null else addr.toKString()
    }

    protected fun posixRealpath(path: String): String = memScoped {
        val temp = allocArray<ByteVar>(PATH_MAX)
        realpath(path, temp)
        temp.toKString()
    }

    override fun createSyncFileSystemFS(file: CPointer<FILE>?): SyncFileSystemFSNativeBase = object : SyncFileSystemFSNativeBase(file) {
        override fun ftruncate64(len: Long) { ftruncate(fd, len) }
        override fun ftell64(): Long = ftell(file).toLong()
        override fun fseek64(pos: Long, origin: Int): Long {
            fseek(file, pos.convert(), origin)
            return ftell(file).toLong()
        }
    }
}
