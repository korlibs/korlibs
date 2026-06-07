package korlibs.io.serialization.xml

import korlibs.io.file.VfsFile

suspend fun VfsFile.readXml(): Xml = Xml(this.readString())
