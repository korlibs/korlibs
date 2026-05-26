package korlibs.io.net.ws

import korlibs.io.async.Signal
import korlibs.io.async.suspendTest
import korlibs.io.async.waitOne
import korlibs.io.lang.portableSimpleName
import korlibs.io.net.http.createHttpServer
import korlibs.logger.Logger
import kotlin.test.Test
import kotlin.test.assertEquals

class WebSocketServerTest {
    val logger = Logger(this::class.portableSimpleName)

    @Test
    fun test() = suspendTest {
        val server = createHttpServer()
        val log = arrayListOf<String>()
        val onCloseReceived = Signal<Unit>()
        server.websocketHandler { req ->
            //println(req.headers)
            req.onBinaryMessage {
                log += "server:bytes(${it.size})"
                logger.debug { "$log" }
            }
            req.onStringMessage {
                log += "server:$it"
                req.send("Received $it")
                logger.debug { "$log" }
            }
            req.onClose {
                log += "server:$it"
                logger.debug { "$log" }
                onCloseReceived(Unit)
            }
        }
        server.listen()

        val client = WebSocketClient("ws://127.0.0.1:${server.actualPort}/demo")
        client.send("HELLO WORLD!")
        client.close()

        //Console.error("[1]")
        onCloseReceived.waitOne()
        //Console.error("[2]")

        server.close()

        //Console.error("[3]")

        //println("log.joinToString(\",\")=${log.joinToString(",")}")

        assertEquals(
            "server:HELLO WORLD!,server:WsCloseInfo(code=1000, reason=Normal close)",
            log.joinToString(",")
        )

        //Console.error("[4]")

        //cancel()
    }
}
