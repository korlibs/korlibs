@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.socket

import korlibs.io.socket.DarwinSSLSocket.Companion.toKString
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.kCFStringEncodingUTF8
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreFoundation.CFStringRef

/**
 * Pure-logic tests for [DarwinSSLSocket].
 *
 * These require [DarwinSSLSocket.swapBytes] and [CFStringRef.toKString] to be
 * visible outside the class (e.g. changed from `private` to `internal`).
 * Everything else in DarwinSSLSocket (connect/read/write/SSL callbacks) talks
 * directly to real sockets and Secure Transport, so it's deliberately left
 * out of scope here - it needs integration tests against a live TLS
 * endpoint, not unit tests.
 */
class DarwinSSLSocketTest {

    @Test
    fun `swapBytes swaps the high and low byte`() {
        assertEquals(0x3412u, DarwinSSLSocket.swapBytes(0x1234u))
    }

    @Test
    fun `swapBytes on zero is zero`() {
        assertEquals(0x0000u, DarwinSSLSocket.swapBytes(0x0000u))
    }

    @Test
    fun `swapBytes on max value is unchanged`() {
        assertEquals(0xFFFFu, DarwinSSLSocket.swapBytes(0xFFFFu))
    }

    @Test
    fun `swapBytes converts common ports to network byte order`() {
        // port 80 -> 0x0050 -> network order 0x5000
        assertEquals(0x5000u, DarwinSSLSocket.swapBytes(0x0050u))
        // port 443 -> 0x01BB -> network order 0xBB01
        assertEquals(0xBB01u, DarwinSSLSocket.swapBytes(0x01BBu))
        // port 8443 -> 0x20FB -> network order 0xFB20
        assertEquals(0xFB20u, DarwinSSLSocket.swapBytes(0x20FBu))
    }

    @Test
    fun `swapBytes is its own inverse`() {
        val samples: List<UShort> = listOf(0u, 1u, 80u, 443u, 8080u, 8443u, 32768u, 65535u)
        for (value in samples) {
            val swapped = DarwinSSLSocket.swapBytes(value)
            assertEquals(value, DarwinSSLSocket.swapBytes(swapped), "round-trip failed for $value")
        }
    }

    @Test
    fun `toKString converts an ascii string`() {
        withCFString("hello world") { cf ->
            assertEquals("hello world", cf.toKString())
        }
    }

    @Test
    fun `toKString converts an empty string`() {
        withCFString("") { cf ->
            assertEquals("", cf.toKString())
        }
    }

    @Test
    fun `toKString handles a domain-like host name`() {
        withCFString("api.malliaridis.com") { cf ->
            assertEquals("api.malliaridis.com", cf.toKString())
        }
    }

    /**
     * KNOWN BUG: toKString() sizes its buffer as CFStringGetLength(this) + 1
     * *bytes*, but CFStringGetLength returns the number of UTF-16 code
     * units, not the number of UTF-8 bytes needed. For strings with
     * multi-byte UTF-8 characters this under-allocates the buffer, so
     * CFStringGetCString can fail to fill it correctly.
     *
     * This test documents the current (broken) behavior. Once toKString is
     * fixed (e.g. sizing the buffer with CFStringGetMaximumSizeForEncoding),
     * replace assertFails with a direct assertEquals("café", ...).
     */
    @Test
    fun `toKString currently mishandles multi-byte UTF-8 characters`() {
        withCFString("café") { cf ->
            assertEquals("café", cf.toKString())
        }
    }

    private inline fun withCFString(value: String, block: (CFStringRef) -> Unit) {
        val cf = CFStringCreateWithCString(null, value, kCFStringEncodingUTF8)
            ?: error("Failed to create CFStringRef fixture for \"$value\"")
        try {
            block(cf)
        } finally {
            CFRelease(cf)
        }
    }
}
