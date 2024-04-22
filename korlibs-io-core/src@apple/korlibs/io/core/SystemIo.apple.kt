package korlibs.io.core

import kotlinx.coroutines.*

actual val defaultSyncSystemIo: SyncSystemIo = NullSyncSystemIo
actual val defaultSystemIo: SystemIo = defaultSyncSystemIo.toAsync(Dispatchers.IO)
