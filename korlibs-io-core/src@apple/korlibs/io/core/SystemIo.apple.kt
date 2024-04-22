package korlibs.io.core

import kotlinx.coroutines.*

actual val defaultSyncSystemIo: SyncSystemIo = AppleSyncSystemIo
actual val defaultSystemIo: SystemIo = defaultSyncSystemIo.toAsync(Dispatchers.IO)

object AppleSyncSystemIo : SyncSystemIoPosixBase() {
}
