package korlibs.io.serialization.xml

import korlibs.io.file.*

suspend fun VfsFile.readXml(): Xml = Xml(this.readString())
