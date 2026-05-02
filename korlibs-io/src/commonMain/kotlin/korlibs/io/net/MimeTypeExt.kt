package korlibs.io.net

import korlibs.io.file.*

fun VfsFile.mimeType() = MimeType.getByExtension(this.extensionLC)
