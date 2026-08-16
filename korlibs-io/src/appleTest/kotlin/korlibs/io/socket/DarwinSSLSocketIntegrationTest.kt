package korlibs.io.socket

import kotlinx.coroutines.test.runTest
import platform.Security.SSLSetSessionOption
import kotlin.test.AfterTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Security.kSSLSessionOptionBreakOnServerAuth

/**
 * Integration tests for [DarwinSSLSocket] against a local, in-process TLS
 * server (see [TestTlsServer]) rather than any external endpoint.
 *
 * Requires the SSLEnsure() fix (calling SSLHandshake again on kSSLHandshake
 * state) - without it these will hang rather than fail, since the current
 * dummy zero-byte SSLWrite doesn't reliably drive the handshake forward.
 *
 * The test server uses a self-signed certificate, so the client explicitly
 * opts out of chain verification via kSSLSessionOptionBreakOnServerAuth -
 * this is only acceptable for a local test fixture, never for real traffic.
 */
@OptIn(ExperimentalForeignApi::class)
class DarwinSSLSocketIntegrationTest {

    private val testPort = 28443
    private var server: TestTlsServer? = null

    @AfterTest
    fun tearDown() {
        server?.stop()
        server = null
    }

    @Test
    fun `connect performs a TCP handshake and records the endpoint`() = runTest {
        server = TestTlsServer(testPort).apply { start() }

        val socket = DarwinSSLSocket()
        socket.connect("127.0.0.1", testPort)

        assertTrue(socket.sockfd >= 0, "expected a valid socket fd after connect")
        assertEquals(testPort, socket.endpoint.port)
        assertEquals("127.0.0.1", socket.endpoint.ip.str)

        socket.close()
    }

    /**
     * This test is working, but requires self-signed certificates being added to keychain via prompt,
     * so they are not working on CI environment.
     */
    @Test
    @Ignore
    fun `write then read echoes data back over TLS`() = runTest {
        server = TestTlsServer(testPort).apply { start() }

        val socket = DarwinSSLSocket()
        socket.connect("127.0.0.1", testPort)
        acceptSelfSignedCertForTest(socket)

        val payload = "hello from korge.org".encodeToByteArray()
        socket.write(payload)
        val echoed = socket.read(payload.size)

        assertEquals(payload.decodeToString(), echoed.decodeToString())

        socket.close()
    }

    /**
     * This test is working, but requires self-signed certificates being added to keychain via prompt,
     * so they are not working on CI environment.
     */
    @Test
    @Ignore
    fun `connected is false before connect and after close`() = runTest {
        val socket = DarwinSSLSocket()
        assertTrue(!socket.connected, "should not be connected before connect() is called")

        server = TestTlsServer(testPort).apply { start() }
        socket.connect("127.0.0.1", testPort)
        acceptSelfSignedCertForTest(socket)
        socket.write("ping".encodeToByteArray())
        socket.read(4)

        socket.close()
        assertTrue(!socket.connected, "should not be connected after close()")
    }

    @Test
    fun `connect to a closed port fails`() = runTest {
        // Nothing is listening on this port.
        val socket = DarwinSSLSocket()
        assertFailsWith<Throwable> {
            socket.connect("127.0.0.1", 1)
        }
    }

    /**
     * Opts this socket's SSL context out of certificate chain verification
     * so it will accept the test server's self-signed certificate.
     * TEST-ONLY - never do this against a real endpoint.
     */
    private fun acceptSelfSignedCertForTest(socket: DarwinSSLSocket) {
        SSLSetSessionOption(socket.ctx, kSSLSessionOptionBreakOnServerAuth, true)
    }
}