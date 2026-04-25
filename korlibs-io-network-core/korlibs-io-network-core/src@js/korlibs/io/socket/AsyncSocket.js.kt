package korlibs.io.socket

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket {
    TODO("AsyncSocket not implemented in JS")
}

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket {
    TODO("AsyncServerSocket not implemented in JS")
}

actual suspend fun AsyncSocket.Companion.unix(path: String): AsyncSocket {
    TODO()
}

actual suspend fun AsyncServerSocket.Companion.unix(path: String, backlog: Int): AsyncServerSocket {
    TODO()
}
