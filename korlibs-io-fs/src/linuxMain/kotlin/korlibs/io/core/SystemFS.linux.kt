package korlibs.io.core

import kotlinx.coroutines.*

actual val defaultSyncSystemFS: SyncSystemFS = LinuxSyncSystemFS
actual val defaultSystemFS: SystemFS by lazy { SyncSystemFS.toAsync(Dispatchers.IO) }

object LinuxSyncSystemFS : SyncSystemFSPosixBase() {
    override fun getcwd(): String = getCurrentExeFolder() ?: posixRealpath(".") ?: "."
    fun getCurrentExe(): String? = posixReadlink("/proc/self/exe") ?: posixReadlink("/proc/curproc/file") ?: posixReadlink("/proc/self/path/a.out")
    fun getCurrentExeFolder(): String? = getCurrentExe()?.substringBeforeLast('/')
}
