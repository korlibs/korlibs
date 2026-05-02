package korlibs.io.net.http

import korlibs.io.file.*
import korlibs.io.stream.*

suspend fun HttpServer.Request.end(file: VfsFile) {
    file.openUse { end(this) }
}

suspend fun HttpServer.Request.end(file: VfsFile, range: LongRange) {
    file.openUse { end(this.slice(range)) }
}

