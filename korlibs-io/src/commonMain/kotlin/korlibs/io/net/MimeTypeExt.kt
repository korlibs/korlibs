package korlibs.io.net

import korlibs.io.file.VfsFile
import korlibs.io.file.extensionLC

fun VfsFile.mimeType() = MimeType.getByExtension(this.extensionLC)
