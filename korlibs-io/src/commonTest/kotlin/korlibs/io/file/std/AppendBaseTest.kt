package korlibs.io.file.std

import korlibs.io.file.VfsFile
import korlibs.io.file.VfsOpenMode
import korlibs.io.stream.writeString
import kotlin.test.assertEquals

class AppendBaseTest {
    companion object {
        suspend fun _testAppendVfs(file: VfsFile) {
            file.delete()
            try {
                file.openUse(VfsOpenMode.APPEND) {
                    writeString("hello")
                }
                file.openUse(VfsOpenMode.APPEND) {
                    writeString(" world")
                }
                assertEquals("hello world", file.readString())
            } finally {
                file.delete()
            }
        }
    }
}
