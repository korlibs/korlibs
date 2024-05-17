package korlibs.io.net.http

internal actual val httpFactory: HttpFactory by lazy {
	object : HttpFactory {
		override fun createClient(): HttpClient = HttpClient
		override fun createServer(): HttpServer = SocketHttp.createServer()
	}
}
