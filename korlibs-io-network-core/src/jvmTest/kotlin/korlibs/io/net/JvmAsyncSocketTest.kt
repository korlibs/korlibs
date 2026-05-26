package korlibs.io.net

import java.io.File
import korlibs.io.async.use
import korlibs.io.socket.AsyncServerSocket
import korlibs.io.socket.AsyncSocket
import korlibs.io.socket.unix
import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class JvmAsyncSocketTest {
    @Test
    fun test() {
        // Skip in CI on windows --     java.net.SocketException: Network is down: bind
        if (System.getenv("CI") != null && Platform.isWindows) return
        if (getJavaVersion() < 17) return

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

    private fun getJavaVersion(): Int {
        var version = System.getProperty("java.version")
        if (version.startsWith("1.")) {
            version = version.substring(2, 3)
        } else {
            val dot = version.indexOf(".")
            if (dot != -1) {
                version = version.substring(0, dot)
            }
        }
        return version.toInt()
    }
}
