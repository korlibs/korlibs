package korlibs.io.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*

@OptIn(ExperimentalForeignApi::class)
abstract class SyncSystemIoNativeBase : SyncSystemIo() {
    override fun stat(path: String): FileSystemIoStat? = TODO()
    override fun unlink(path: String): Boolean = platform.posix.unlink(path) == 0
    override fun rmdir(path: String): Boolean = platform.posix.rmdir(path) == 0
    override fun listdir(path: String): Sequence<String> = TODO()
    override fun open(path: String, write: Boolean): SyncFileSystemIo? = TODO()
}
