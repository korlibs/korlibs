package korlibs.io.hash

import korlibs.crypto.MD5
import korlibs.crypto.SHA1
import korlibs.crypto.md5
import korlibs.crypto.sha1
import kotlin.test.Test
import kotlin.test.assertEquals

class HashJvmExtTest {
    @Test
    fun testInputStream() {
        byteArrayOf(1, 2, 3).also {
            assertEquals(it.md5(), it.inputStream().hash(MD5))
        }
        ByteArray(0x30000) { (it * 318083817907).toByte() }.also {
            assertEquals(it.sha1(), it.inputStream().hash(SHA1))
        }
    }
}
