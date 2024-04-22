package korlibs.io.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.posix.*

actual val defaultSyncSystemIo: SyncSystemIo = LinuxSyncSystemIo
actual val defaultSystemIo: SystemIo = defaultSyncSystemIo.toAsync(Dispatchers.IO)

object LinuxSyncSystemIo : SyncSystemIoPosixBase() {
    override fun getcwd(): String = getCurrentExeFolder() ?: posixRealpath(".") ?: "."

    fun getCurrentExe(): String? = posixReadlink("/proc/self/exe")
        ?: posixReadlink("/proc/curproc/file")
        ?: posixReadlink("/proc/self/path/a.out")

    fun getCurrentExeFolder() = getCurrentExe()?.substringBeforeLast('/')
}
