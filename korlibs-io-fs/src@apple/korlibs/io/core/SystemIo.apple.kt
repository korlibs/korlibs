@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.posix.*

actual val defaultSyncSystemIo: SyncSystemIo = AppleSyncSystemIo
actual val defaultSystemIo: SystemIo = SyncSystemIo.toAsync(Dispatchers.IO)

object AppleSyncSystemIo : SyncSystemIoPosixBase() {
    override fun getcwd(): String = platform.Foundation.NSBundle.mainBundle.resourcePath ?: posixRealpath(".") ?: "."
}
