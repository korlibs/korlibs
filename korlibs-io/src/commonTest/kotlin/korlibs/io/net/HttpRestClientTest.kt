package korlibs.io.net

import korlibs.io.async.suspendTest
import korlibs.io.net.http.FakeHttpClient
import korlibs.io.net.http.rest
import korlibs.io.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class HttpRestClientTest {
    @Test
    fun test() = suspendTest {
        val client = FakeHttpClient()
        val rest = client.rest("http://example.com/api/")
        rest.post("method", Json.stringify(mapOf<String, Any?>()))
        assertEquals(
            listOf("POST, http://example.com/api/method, Headers((Content-Length, [2]), (Content-Type, [application/json])), {}"),
            client.log
        )
    }
}
