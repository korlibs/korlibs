package korlibs.io.http.core

import korlibs.io.socket.*
import korlibs.io.stream.*

internal fun SocketHttpFetch(socketGen: suspend (Boolean) -> AsyncSocket = { AsyncSocket(it) }): HttpFetch {
    return object : HttpFetch {
        override suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpFetchResult {
            val socket = socketGen(secure)
            socket.connect(host, port)
            val buffered = BufferedAsyncInputStream(socket)

            socket.write(computeHeader(method, path, host, headers).encodeToByteArray())
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

/*

class HttpClientJvm : HttpClient() {
	companion object {
		var lastId = 0
	}

	val clientId = lastId++
	var lastRequestId = 0

	class FakeHttpsTrustManager : X509TrustManager {
		override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf<X509Certificate>()
		override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) = Unit
		override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) = Unit
		fun isClientTrusted(chain: Array<X509Certificate>): Boolean = true
		fun isServerTrusted(chain: Array<X509Certificate>): Boolean = true
	}

	override suspend fun requestInternal(
		method: Http.Method,
		url: String,
		headers: Http.Headers,
		content: AsyncInputStreamWithLength?
	): HttpClient.Response {
		val result = doIo {
			val requestId = lastRequestId++
			val id = "request[$clientId,$requestId]"

			//println("url[$id] thread=$currentThreadId: $url ")
			val aurl = URL(url)
			HttpURLConnection.setFollowRedirects(false)
			val con = aurl.openConnection() as HttpURLConnection
			if (ignoreSslCertificates && (con is HttpsURLConnection)) {
				con.hostnameVerifier = HostnameVerifier { _, _ -> true }
				val context = SSLContext.getInstance("TLS")
				context!!.init(null, arrayOf<TrustManager>(FakeHttpsTrustManager()), SecureRandom())
				con.sslSocketFactory = context.socketFactory
			}

			con.requestMethod = method.name
			//println(" --> [$id]${method.name}")
			//println("URL:$url")
			//println("METHOD:${method.name}")
			for (header in headers) {
				//println("HEADER:$header")
				con.addRequestProperty(header.first, header.second)
				//println(" --> [$id]${header.first} - ${header.second}")
			}
			//println(" --> [$id]content=${content?.size()}")
			if (content != null) {
				con.doOutput = true

				//val ccontent = content.sliceStart()
				val len = content.getAvailable()
				var left = len
				val temp = ByteArray(1024)
				//println("HEADER:content-length, $len")

				while (true) {
					try {
						con.connect()
						HttpStats.connections.incrementAndGet()
						break
					} catch (e: BindException) {
						// Potentially no more ports available. Too many pending connections.
						e.printStackTrace()
						delay(1000)
						continue
					}
				}

				val os = con.outputStream
				while (left > 0) {
					val read = content.read(temp, 0, min(temp.size, left.toUintClamp()))
					if (read <= 0) invalidOp("Problem reading")
					left -= read
					os.write(temp, 0, read)
				}
				os.flush()
				os.close()
                content.close()
			} else {
				con.connect()
			}

			val channel = Channel<ByteArray>(4)

			val pheaders = Http.Headers.fromListMap(con.headerFields)
			val length = pheaders[Http.Headers.ContentLength]?.toLongOrNull()

			launchImmediately(newSingleThreadContext("HttpRequest: $method: $url")) {
				val syncStream = runIgnoringExceptions { con.inputStream } ?: runIgnoringExceptions { con.errorStream }
				try {
					if (syncStream != null) {
						//val stream = syncStream.toAsync(length).toAsyncStream()
						val stream = syncStream
						val temp = ByteArray(0x1000)
						loop@ while (true) {
							// @TODO: Totally cancel reading if nobody is consuming this. Think about the best way of doing this.
							// node.js pause equivalent?
							//val chunkStartTime = DateTime.now()
							//while (produceConsumer.isFull > 4) { // Prevent filling the memory if nobody is consuming data
							//	//println("PREVENT!")
							//	delay(10)
							//	val chunkCurrentTime = DateTime.now()
							//	if ((chunkCurrentTime - chunkStartTime) >= 2.seconds) {
							//		System.err.println("[$id] thread=$currentThreadId Two seconds passed without anyone reading data (available=${produceConsumer.availableCount}) from $url. Closing...")
							//		break@loop
							//	}
							//}
							val read = stream.read(temp)
							//println(" --- [$id][D] thread=$currentThreadId : $read")
							if (read <= 0) break
							channel.send(temp.copyOf(read))
						}
					}
				} finally {
					runIgnoringExceptions { syncStream?.close() }
					runIgnoringExceptions { channel.close() }
					runIgnoringExceptions { con.disconnect() }
					HttpStats.disconnections.incrementAndGet()
				}
			}

			//Response(
			//		status = con.responseCode,
			//		statusText = con.responseMessage,
			//		headers = Http.Headers.fromListMap(con.headerFields),
			//		content = if (con.responseCode < 400) con.inputStream.readBytes().openAsync() else con.errorStream.toAsync().toAsyncStream()
			//)

			val acontent = channel.toAsyncInputStream()
			Response(
				status = con.responseCode,
				statusText = con.responseMessage,
				headers = pheaders,
				rawContent = if (length != null) acontent.withLength(length) else acontent
			)
		}
		return result
	}
}

 */