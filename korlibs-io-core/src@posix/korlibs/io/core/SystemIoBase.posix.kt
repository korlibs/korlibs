package korlibs.io.core

import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
open class SyncSystemIoPosixBase : SyncSystemIoNativeBase() {
    override fun mkdir(path: String): Boolean = platform.posix.mkdir(path, 511.convert()) == 0
}
