package korlibs.io.core

import kotlinx.coroutines.*

actual val defaultSyncSystemIo: SyncSystemIo = LinuxSyncSystemIo
actual val defaultSystemIo: SystemIo = defaultSyncSystemIo.toAsync(Dispatchers.IO)

object LinuxSyncSystemIo : SyncSystemIoPosixBase() {
}
