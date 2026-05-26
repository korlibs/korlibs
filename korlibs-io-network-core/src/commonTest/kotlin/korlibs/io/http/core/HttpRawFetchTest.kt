package korlibs.io.http.core

import korlibs.io.socket.PairAsyncSocket
import korlibs.io.stream.readAll
import korlibs.platform.Platform
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class HttpRawFetchTest {
    @Test
    fun testFetch() = runTest {
        if (Platform.isNative) return@runTest
        val result = HttpFetch.fetch("GET", "docs.korge.org", 443, "/ssltest.txt", secure = true, headers = listOf())
        assertEquals("file used for SSL tests\n", result.bodyRaw.readAll().decodeToString())
    }

    @Test
    fun test() = runTest {
        val socket = PairAsyncSocket()

        socket.readDeque.write(
            "HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: 1\r\nConnection: close\r\nDate: Tue, 20 Apr 2021 06:57:22 GMT\r\nServer: ECSF (dcb/7F83)\r\n\r\n.".encodeToByteArray()
        )


        val result = SocketHttpFetch { socket }.fetch("GET", "hello.world", 80, "/demo", false, listOf())

        assertEquals(200, result.status)
        assertEquals("OK", result.statusText)
        assertEquals(listOf(
            "Content-Type" to "text/html",
            "Content-Length" to "1",
            "Connection" to "close",
            "Date" to "Tue, 20 Apr 2021 06:57:22 GMT",
            "Server" to "ECSF (dcb/7F83)",
        ), result.headers)
        assertEquals(".", result.bodyRaw.readAll().decodeToString())
    }

    @Test
    @Ignore
    fun test2() = runTest {
        val result = HttpFetch.fetch("GET", "google.es", 443, "/", true, listOf())
        assertEquals(200, result.status)
        assertEquals("OK", result.statusText)
        assertEquals(".", result.bodyRaw.readAll().decodeToString())
    }
}
