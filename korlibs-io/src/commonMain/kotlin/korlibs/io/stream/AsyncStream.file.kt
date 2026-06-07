@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package korlibs.io.stream

import korlibs.io.file.VfsFile
import korlibs.io.file.VfsOpenMode
import korlibs.io.file.std.MemoryVfs

fun AsyncStream.asVfsFile(name: String = "unknown.bin"): VfsFile = MemoryVfs(mapOf(name to this))[name]
suspend fun AsyncOutputStream.writeFile(source: VfsFile): Long = source.openUse(VfsOpenMode.READ) { this@writeFile.writeStream(this) }
