package korlibs.io.net.ssl

import korlibs.io.async.suspendTest
import korlibs.io.net.http.Http
import korlibs.io.net.http.createHttpClient
import kotlin.test.Test
import kotlin.test.assertEquals

class SSLTest {
    @Test
    fun testDownloadHttpsFile() = suspendTest {
        val client = createHttpClient()
        val result = client.requestAsString(Http.Method.GET, "https://docs.korge.org/ssltest.txt")

        assertEquals("file used for SSL tests\n", result.content)
    }
}
