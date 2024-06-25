package korlibs.io.net

import korlibs.io.async.*
import korlibs.io.socket.*
import korlibs.platform.*
import kotlinx.coroutines.*
import java.io.*
import kotlin.test.*

class JvmAsyncSocketTest {
    @Test
    fun test() {
        // Skip in CI on windows --     java.net.SocketException: Network is down: bind
        if (System.getenv("CI") != null && Platform.isWindows) return

        runBlocking {
            val sockPath = "/tmp/test.sock"
            File(sockPath).delete()
            try {
                AsyncServerSocket.unix(sockPath).use { unixServer ->
                    val unixServerClientAsync = async { unixServer.accept() }
                    AsyncSocket.unix(sockPath).use { unixClient ->
                        unixClient.write(77)
                        val unixServerClient = unixServerClientAsync.await()
                        assertEquals(77, unixServerClient.read())
                        unixServerClient.write(88)
                        assertEquals(88, unixClient.read())
                    }
                }
            } finally {
                File(sockPath).delete()
            }
        }
    }
}