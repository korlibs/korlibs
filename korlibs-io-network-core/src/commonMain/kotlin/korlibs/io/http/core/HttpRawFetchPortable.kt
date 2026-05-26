package korlibs.io.http.core

import korlibs.io.socket.AsyncSocket
import korlibs.io.socket.invoke
import korlibs.io.stream.AsyncInputStream
import korlibs.io.stream.BufferedAsyncInputStream
import korlibs.io.stream.copyTo

internal fun SocketHttpFetch(socketGen: suspend (Boolean) -> AsyncSocket = { AsyncSocket(it) }): HttpFetch {
    return object : HttpFetch {
        override suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpFetchResult {
            val socket = socketGen(secure)
            socket.connect(host, port)
            val buffered = BufferedAsyncInputStream(socket)

            socket.write(computeHeader(method, if (path.isBlank()) "/" else path, host, headers).encodeToByteArray())
            body?.copyTo(socket)

            val firstLine = buffered.readLine()
            val responseInfo = Regex("HTTP/1.\\d+\\s+(\\d+)\\s+(.*)").find(firstLine)
                ?: error("Invalid HTTP response $firstLine")

            //println("FIRST LINE: ${firstLine.trim()}")

            val responseCode = responseInfo.groupValues[1].toInt()
            val responseMessage = responseInfo.groupValues[2]

            val outputHeaders = arrayListOf<Pair<String, String>>()
            while (true) {
                val line = buffered.readLine().trim()
                //println("line: $line")
                if (line.isEmpty()) break
                val parts = line.split(":", limit = 2).map { it.trim() }
                outputHeaders += parts.first() to parts.last()
            }

            return HttpFetchResult(responseCode, responseMessage, outputHeaders, buffered)
        }

        private fun computeHeader(method: String, path: String, host: String, headers: List<Pair<String, String>>): String = buildString {
            val EOL = "\r\n"
            append("$method $path HTTP/1.1$EOL")
            var hasHost = false
            var hasConnection = false
            for (header in headers) {
                if (header.first.equals("host", ignoreCase = true)) hasHost = true
                if (header.first.equals("connection", ignoreCase = true)) hasConnection = true
                append("${header.first}: ${header.second}$EOL")
            }
            if (!hasHost) append("Host: $host$EOL")
            if (!hasConnection) append("Connection: close$EOL")
            append(EOL)
        }
    }
}
