package korlibs.io.core

import kotlinx.coroutines.*

actual val defaultSyncSystemFs: SyncSystemFs = LinuxSyncSystemFs
actual val defaultSystemFs: SystemFs = SyncSystemFs.toAsync(Dispatchers.IO)

object LinuxSyncSystemFs : SyncSystemFsPosixBase() {
    override fun getcwd(): String = getCurrentExeFolder() ?: posixRealpath(".") ?: "."
    fun getCurrentExe(): String? = posixReadlink("/proc/self/exe") ?: posixReadlink("/proc/curproc/file") ?: posixReadlink("/proc/self/path/a.out")
    fun getCurrentExeFolder(): String? = getCurrentExe()?.substringBeforeLast('/')
}
