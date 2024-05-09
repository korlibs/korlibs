package korlibs.io.socket

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket {
    TODO("AsyncSocket not implemented in WASM")
}

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket {
    TODO("AsyncServerSocket not implemented in WASM")
}
