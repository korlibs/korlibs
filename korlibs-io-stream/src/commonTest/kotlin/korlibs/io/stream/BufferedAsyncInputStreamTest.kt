package korlibs.io.stream

import kotlinx.coroutines.test.*
import kotlin.test.*

class BufferedAsyncInputStreamTest {
    @Test
    fun testLineEnding() = runTest {
        "hello world\r\nthis is a test\r\n\r\ntest".encodeToByteArray().openAsync().also { stream ->
            val buffered = BufferedAsyncInputStream(stream)
            assertEquals("hello world\r\n", buffered.readLine())
            assertEquals("this is a test\r\n", buffered.readLine())
            assertEquals("\r\n", buffered.readLine())
            assertEquals("test", buffered.readLine())
            assertEquals("", buffered.readLine())
        }
    }

    @Test
    fun testNoLineEnding() = runTest {
        "hello world\r\nthis is a test\r\n\r\ntest".encodeToByteArray().openAsync().also { stream ->
            val buffered = BufferedAsyncInputStream(stream)
            assertEquals("hello world\r", buffered.readLine(includeLineEnding = false))
            assertEquals("this is a test\r", buffered.readLine(includeLineEnding = false))
            assertEquals("\r", buffered.readLine(includeLineEnding = false))
            assertEquals("test", buffered.readLine(includeLineEnding = false))
            assertEquals("", buffered.readLine(includeLineEnding = false))
        }
    }
}