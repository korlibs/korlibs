package korlibs.io.lang

import korlibs.io.file.VfsFile

suspend fun VfsFile.readProperties(charset: Charset = Charsets.UTF8) = Properties.parseString(readString(charset))
