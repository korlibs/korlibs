package korlibs.io.file

import korlibs.io.net.*

/**
 * /path\to/file.1.jpg -> MimeType("image/jpeg", listOf("jpg", "jpeg"))
 */
val PathInfo.mimeTypeByExtension get() = MimeType.getByExtension(extensionLC)

val Path.mimeTypeByExtension: MimeType get() = pathInfo.mimeTypeByExtension
