package korlibs.io.core

import kotlinx.coroutines.*

actual val defaultSyncSystemIo: SyncSystemIo = MingwSyncSystemIo
actual val defaultSystemIo: SystemIo = defaultSyncSystemIo.toAsync(Dispatchers.IO)

object MingwSyncSystemIo : SyncSystemIoNativeBase() {
    override fun mkdir(path: String): Boolean = platform.posix.mkdir(path) == 0
}
