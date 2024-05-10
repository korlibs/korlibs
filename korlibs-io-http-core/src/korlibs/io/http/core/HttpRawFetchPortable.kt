package korlibs.io.http.core

import korlibs.io.socket.*
import korlibs.io.stream.*

internal suspend fun httpRawFetchPortable(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream? = null, socketGen: suspend (Boolean) -> AsyncSocket = { AsyncSocket(it) }): HttpRawFetchResult {
    val socket = socketGen(secure)
    socket.connect(host, port)
    val buffered = BufferedAsyncInputStream(socket)

    socket.write(computeHeader(method, path, headers).encodeToByteArray())
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

    return HttpRawFetchResult(responseCode, responseMessage, outputHeaders, buffered)
}

private fun computeHeader(method: String, path: String, headers: List<Pair<String, String>>): String = buildString {
    val EOL = "\r\n"
    append("$method $path HTTP/1.1$EOL")
    for (header in headers) append("${header.first}: ${header.second}$EOL")
    append(EOL)
}
