@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.core

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val defaultSyncSystemFS: SyncSystemFS = AppleSyncSystemFS
actual val defaultSystemFS: SystemFS by lazy { SyncSystemFS.toAsync(Dispatchers.IO) }

object AppleSyncSystemFS : SyncSystemFSPosixBase() {
    override fun getcwd(): String = platform.Foundation.NSBundle.mainBundle.resourcePath ?: posixRealpath(".") ?: "."
}
