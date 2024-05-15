@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.posix.*

actual val defaultSyncSystemFs: SyncSystemFs = AppleSyncSystemIo
actual val defaultSystemFs: SystemFs = SyncSystemFs.toAsync(Dispatchers.IO)

object AppleSyncSystemIo : SyncSystemFsPosixBase() {
    override fun getcwd(): String = platform.Foundation.NSBundle.mainBundle.resourcePath ?: posixRealpath(".") ?: "."
}
