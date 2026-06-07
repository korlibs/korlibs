package korlibs.io.file.std

import korlibs.io.lang.Environment
import korlibs.io.lang.tempPath
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalVfsNativeTest {

    @Test
    fun testUserHomeVfsValue() {
        val expectedResult = when {
            Environment["HOMEDRIVE"] != null && Environment["HOMEPATH"] != null -> "${Environment["HOMEDRIVE"]}${Environment["HOMEPATH"]}"
            else -> Environment["HOMEPATH"] ?: Environment["HOME"] ?: Environment.tempPath
        }
        assertEquals(
            expectedResult.replace("\\", "/").trimEnd('/'),
            userHomeVfs.absolutePath.replace("\\", "/").trimEnd('/')
        )
    }
}
