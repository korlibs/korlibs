package korlibs.io.core

import korlibs.io.lang.*
import korlibs.io.stream.*
import korlibs.js.*
import korlibs.memory.*
import korlibs.platform.JsObject
import kotlinx.browser.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.khronos.webgl.*
import org.w3c.fetch.*

actual val defaultSyncSystemFs: SyncSystemFs = DenoSyncSystemFs()
actual val defaultSystemFs: SystemFs = DenoSystemFs()

class DenoSystemFs : SystemFs {
    override suspend fun open(path: String, write: Boolean): FileSystemIo? = TODO()
    override suspend fun listdir(path: String): Flow<String> = Deno.readDir(path).toFlow().map { it.name }
    override suspend fun mkdir(path: String, mode: Int): Boolean = runCatching { Deno.mkdir(path, jsObjectOf("mode" to mode)).await() }.isSuccess
    override suspend fun rmdir(path: String): Boolean = runCatching { Deno.remove(path).await() }.isSuccess
    override suspend fun unlink(path: String): Boolean = runCatching { Deno.remove(path).await() }.isSuccess
    override suspend fun stat(path: String): FileSystemIoStat? = Deno.stat(path).await().toFileSystemIoStat()
    override suspend fun realpath(path: String): String = Deno.realPath(path).await()
    override suspend fun readlink(path: String): String? = Deno.readLink(path).await()
    override suspend fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SystemIoProcess = TODO("Not yet implemented")
}

class DenoSyncSystemFs : SyncSystemFs {
    override fun open(path: String, write: Boolean): SyncFileSystemIo? = TODO("Not yet implemented")
    override fun listdir(path: String): Sequence<String> = Deno.readDirSync(path).toList().map { it.name }.asSequence()
    override fun mkdir(path: String): Boolean = runCatching { Deno.mkdirSync(path) }.isSuccess
    override fun rmdir(path: String): Boolean = runCatching { Deno.removeSync(path) }.isSuccess
    override fun unlink(path: String): Boolean = runCatching { Deno.removeSync(path) }.isSuccess
    override fun stat(path: String): FileSystemIoStat? = Deno.statSync(path).toFileSystemIoStat()
    override fun realpath(path: String): String = Deno.realPathSync(path)
    override fun readlink(path: String): String? = Deno.readLinkSync(path)
}

private fun DenoFileInfo.toFileSystemIoStat(): FileSystemIoStat? {
    TODO()
}
