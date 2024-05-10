package korlibs.io.file

import korlibs.io.file.std.*
import korlibs.io.stream.*

fun VfsFile.jail(): VfsFile = JailVfs(this)
fun VfsFile.jailParent(): VfsFile = JailVfs(parent)[this.baseName]
suspend fun VfsFile.readAsSyncStream(): SyncStream = read().openSync()
