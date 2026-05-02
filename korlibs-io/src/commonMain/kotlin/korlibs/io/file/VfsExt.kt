package korlibs.io.file

import korlibs.io.file.std.*

suspend fun ByteArray.writeToFile(path: String) = localVfs(path).write(this)
suspend fun ByteArray.writeToFile(file: VfsFile) = file.write(this)
