package korlibs.io.net.http

internal actual val httpFactory: HttpFactory by lazy {
	object : HttpFactory {
		init {
			System.setProperty("http.keepAlive", "false")
		}

		//override fun createClient(): HttpClient = HttpClientJvm()
		override fun createClient(): HttpClient = HttpClient
		override fun createServer(): HttpServer = SocketHttp.createServer()
	}
}
