@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.io.socket

import cnames.structs.SSLContext
import kotlinx.cinterop.Arena
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.UByteVarOf
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.plus
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import platform.CoreFoundation.CFStringGetCString
import platform.CoreFoundation.CFStringGetLength
import platform.CoreFoundation.CFStringGetMaximumSizeForEncoding
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.kCFStringEncodingUTF8
import platform.Security.SSLClose
import platform.Security.SSLConnectionRef
import platform.Security.SSLConnectionType
import platform.Security.SSLContextRef
import platform.Security.SSLCreateContext
import platform.Security.SSLGetSessionState
import platform.Security.SSLHandshake
import platform.Security.SSLProtocolSide
import platform.Security.SSLRead
import platform.Security.SSLSessionState
import platform.Security.SSLSetConnection
import platform.Security.SSLSetIOFuncs
import platform.Security.SSLSetPeerDomainName
import platform.Security.SSLSetSessionOption
import platform.Security.SSLWrite
import platform.Security.SecCopyErrorMessageString
import platform.Security.errSSLClosedGraceful
import platform.Security.errSSLServerAuthCompleted
import platform.Security.errSSLWouldBlock
import platform.Security.kSSLSessionOptionBreakOnServerAuth
import platform.darwin.OSStatus
import platform.darwin.inet_addr
import platform.darwin.noErr
import platform.posix.AF_INET
import platform.posix.EAGAIN
import platform.posix.EINPROGRESS
import platform.posix.EWOULDBLOCK
import platform.posix.F_SETFL
import platform.posix.O_NONBLOCK
import platform.posix.POLLOUT
import platform.posix.SOCK_STREAM
import platform.posix.SOL_SOCKET
import platform.posix.SO_ERROR
import platform.posix.SO_RCVTIMEO
import platform.posix.SO_SNDTIMEO
import platform.posix.close
import platform.posix.connect
import platform.posix.errno
import platform.posix.fcntl
import platform.posix.gethostbyname
import platform.posix.getsockopt
import platform.posix.poll
import platform.posix.pollfd
import platform.posix.recv
import platform.posix.send
import platform.posix.setsockopt
import platform.posix.size_tVar
import platform.posix.sockaddr_in
import platform.posix.socket
import platform.posix.socklen_tVar
import platform.posix.timeval

class DarwinSSLSocket {
    val arena = Arena()
    var sockfd: Int = -1
    var ctx: CPointer<SSLContext>? = null
    var endpoint: NativeSocket.Endpoint = NativeSocket.Endpoint(NativeSocket.IP(0, 0, 0, 0), 0); private set

    suspend fun connect(host: String, port: Int, timeoutMs: Int = 10_000) {
        close()
        val socketVar = arena.alloc<LongVar>()
        ctx = SSLCreateContext(null, SSLProtocolSide.kSSLClientSide, SSLConnectionType.kSSLStreamType)

        withContext(Dispatchers.IO) {
            memScoped {
                val sockfd = socket(AF_INET, SOCK_STREAM, 0)
                val timeout = alloc<timeval>()
                timeout.tv_sec = 10     // seconds
                timeout.tv_usec = 500000 // micro seconds ( 0.5 seconds)
                setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, timeout.ptr, sizeOf<timeval>().convert())
                setsockopt(sockfd, SOL_SOCKET, SO_SNDTIMEO, timeout.ptr, sizeOf<timeval>().convert())
                fcntl(sockfd, F_SETFL, O_NONBLOCK)

                socketVar.value = sockfd.convert()
                SSLSetConnection(ctx, socketVar.ptr)

                SSLSetIOFuncs(ctx, staticCFunction(::SSL_recv_callback), staticCFunction(::SSL_send_callback))
                SSLSetPeerDomainName(ctx, host)

                //println("Socket...")
                val hname = gethostbyname(host)
                //println("hname=$hname")
                val inetaddr: CPointer<UByteVarOf<UByte>> = hname!!.pointed.h_addr_list!![0]!!.reinterpret()

                val endpoint = NativeSocket.Endpoint(
                    NativeSocket.IP(inetaddr[0].toInt(), inetaddr[1].toInt(), inetaddr[2].toInt(), inetaddr[3].toInt()),
                    port
                )

                val servaddr = alloc<sockaddr_in>()
                servaddr.sin_family = AF_INET.convert()
                //println("addr=$addr")
                servaddr.sin_addr.s_addr = inet_addr(endpoint.ip.str)
                servaddr.sin_port = swapBytes(endpoint.port.toUShort()).convert()

                val result = connect(sockfd, servaddr.ptr.reinterpret(), sizeOf<sockaddr_in>().convert())

                if (result != 0) {
                    if (errno != EINPROGRESS) {
                        error("Error connecting to socket errno=$errno, sockfd=$sockfd")
                    }
                    memScoped {
                        val pfd = alloc<pollfd>()
                        pfd.fd = sockfd
                        pfd.events = POLLOUT.convert()
                        val pollResult = poll(pfd.ptr, 1u, timeoutMs)
                        if (pollResult <= 0) {
                            error("Timed out connecting to socket, sockfd=$sockfd")
                        }
                        val soError = alloc<IntVar>()
                        val len = alloc<socklen_tVar>()
                        len.value = sizeOf<IntVar>().convert()
                        getsockopt(sockfd, SOL_SOCKET, SO_ERROR, soError.ptr, len.ptr)
                        if (soError.value != 0) {
                            error("Error connecting to socket, SO_ERROR=${soError.value}, sockfd=$sockfd")
                        }
                    }
                }

                this@DarwinSSLSocket.sockfd = sockfd
                this@DarwinSSLSocket.endpoint = endpoint
            }
        }
    }

    val connected: Boolean get() {
        if (sockfd < 0 || ctx == null) return false
        return when (SSLGetSessionState(ctx)) {
            SSLSessionState.kSSLClosed, SSLSessionState.kSSLAborted -> false
            else -> ioctlSocketFionRead(sockfd) >= 0
        }
    }

    suspend fun write(data: ByteArray, offset: Int = 0, size: Int = data.size - offset) {
        SSLWrite(ctx, data, offset, size)
    }

    suspend fun read(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int {
        return SSLRead(ctx, data, offset, size)
    }

    suspend fun read(size: Int): ByteArray {
        val out = ByteArray(size)
        return out.copyOf(read(out))
    }

    fun close() {
        if (ctx != null) SSLClose(ctx)
        if (sockfd >= 0) close(sockfd)
        ctx = null
        sockfd = -1
        arena.clear()
    }

    companion object {
        private fun SSLSetPeerDomainName(ctx: SSLContextRef?, name: String) {
            val status = SSLSetPeerDomainName(ctx, name, name.length.convert())
            //println("SSLSetPeerDomainName: " + SecCopyErrorMessageString(status, null)?.toKString())
        }

        private fun SSLGetSessionState(ctx: SSLContextRef?): SSLSessionState = memScoped {
            val state = alloc<SSLSessionState.Var>()
            SSLGetSessionState(ctx, state.ptr)
            state.value
        }

        internal fun swapBytes(v: UShort): UShort =
            (((v.toInt() and 0xFF) shl 8) or ((v.toInt() ushr 8) and 0xFF)).toUShort()

        private suspend fun SSLEnsure(ctx: SSLContextRef?): Boolean {
            while (true) {
                val state = SSLGetSessionState(ctx)
                when (state) {
                    SSLSessionState.kSSLIdle, SSLSessionState.kSSLHandshake -> {
                        memScoped {
                            val data = allocArray<ByteVar>(0)
                            val processed = alloc<size_tVar>()
                            SSLWrite(ctx, data, 0.convert(), processed.ptr)
                        }
                        val status = SSLHandshake(ctx)
                        println("client: SSLHandshake status=$status")
                        when (status) {
                            0 -> Unit // progressed or just completed; re-check state next loop
                            errSSLWouldBlock -> delay(timeMillis = 1)
                            errSSLServerAuthCompleted -> {
                                // We're not evaluating the peer cert ourselves (test-only, via
                                // kSSLSessionOptionBreakOnServerAuth). Turn the break option back
                                // off so the *next* SSLHandshake call proceeds past this point
                                // instead of pausing here again.
                                SSLSetSessionOption(ctx, kSSLSessionOptionBreakOnServerAuth, false)
                            }
                            else -> error("SSLHandshake failed: $status")
                        }
                    }
                    SSLSessionState.kSSLClosed -> return false
                    SSLSessionState.kSSLAborted -> return false
                    SSLSessionState.kSSLConnected -> break
                    else -> Unit
                }
            }
            return true
        }

        private suspend fun SSLRead(
            ctx: SSLContextRef?,
            data: ByteArray,
            offset: Int = 0,
            size: Int = data.size - offset
        ): Int {
            if (data.isEmpty() || size == 0) return 0
            if (!SSLEnsure(ctx)) return -1

            memScoped {
                val processed = alloc<size_tVar>()

                while (true) {
                    val result = data.usePinned { dataPin ->
                        SSLRead(ctx, dataPin.addressOf(offset), size.convert(), processed.ptr)
                    }

                    return when (result) {
                        0 -> {
                            processed.value.toInt()
                        }

                        errSSLWouldBlock -> {
                            delay(timeMillis = 1)
                            continue
                        }

                        errSSLClosedGraceful -> {
                            0
                        }

                        else -> {
                            error("SSLRead: ${SecCopyErrorMessageString(result, null)?.toKString()}")
                        }
                    }
                }
            }
        }

        private suspend fun SSLWrite(
            ctx: SSLContextRef?,
            data: ByteArray,
            offset: Int = 0,
            size: Int = data.size - offset
        ) {
            if (data.isEmpty() || size == 0) return
            if (!SSLEnsure(ctx)) return

            memScoped {
                val processed = alloc<size_tVar>()
                data.usePinned { dataPin ->
                    val result = SSLWrite(ctx, dataPin.addressOf(offset), size.convert(), processed.ptr)

                    //println("SSLWrite.result=$result, resultString=$resultString")
                    //println("SSLWrite.processed=${processed.value}")
                    if (result != 0) error("SSLWrite: ${SecCopyErrorMessageString(result, null)?.toKString()}")
                }
            }
        }

        internal fun CFStringRef.toKString(): String {
            val length = CFStringGetLength(this)
            // CFStringGetLength returns UTF-16 code units, not UTF-8 bytes — a single
            // character can expand to up to 4 bytes in UTF-8, so size the buffer properly
            val maxBytes = CFStringGetMaximumSizeForEncoding(length, kCFStringEncodingUTF8) + 1
            val data = ByteArray(maxBytes.toInt())
            val ok = data.usePinned {
                CFStringGetCString(this@toKString, it.addressOf(0), maxBytes, kCFStringEncodingUTF8)
            }
            check(ok) { "CFStringGetCString failed to convert CFString to UTF-8" }
            val nullTerminatorIndex = data.indexOf(0.toByte()).let { if (it < 0) data.size else it }
            return data.decodeToString(0, nullTerminatorIndex)
        }
    }
}

private val ioErr: OSStatus = (-36).convert()

/*
 * https://github.com/karosLi/offlineH5/blob/0bf84d9baea37016d73fab70e3005ef0e3453975/node_modules/.0.19.0%40nodegit/vendor/libgit2/src/stransport_stream.c#L170
 *
 * Contrary to typical network IO callbacks, Secure Transport read callback is
 * expected to read *exactly* the requested number of bytes, not just as much
 * as it can, and any other case would be considered a failure.
 *
 * This behavior is actually not specified in the Apple documentation, but is
 * required for things to work correctly (and incidentally, that's also how
 * Apple implements it in its projects at opensource.apple.com).
 */
private fun SSL_recv_callback(
    connection: SSLConnectionRef?,
    ptr: COpaquePointer?,
    size: CPointer<size_tVar>?
): OSStatus {
    val sockfd = connection?.reinterpret<LongVar>()?.get(0) ?: error("No socket provided")
    //println("SSL_recv_callback: sockfd=$sockfd, size=${size?.get(0)}")
    val readSize = size?.get(0)?.toInt() ?: 0
    size?.set(0, 0.convert())
    var currentPtr = ptr?.reinterpret<ByteVar>()
    var pendingSize: Int = readSize
    var totalReadSize = 0
    var error: OSStatus = noErr.convert()

    memScoped {
        //val availableRead = alloc<size_tVar>()
        val availableRead: Int = ioctlSocketFionRead(sockfd.convert()).convert()
        //println("ioctlResult=$ioctlResult, availableRead.value=${availableRead.value}")
        //if (ioctlResult != 0) return errSSLWouldBlock
        //if (availableRead.value < pendingSize.convert()) return errSSLWouldBlock
        if (availableRead < pendingSize) return errSSLWouldBlock
    }

    while (pendingSize > 0) {
        val recvBytes = recv(sockfd.convert(), currentPtr, pendingSize.convert(), 0).toInt()
        if (recvBytes < 0) {
            // EAGAIN/EWOULDBLOCK means genuinely nothing available yet — anything else is a real error
            return if (errno == EAGAIN || errno == EWOULDBLOCK) errSSLWouldBlock else ioErr.convert()
        }
        if (recvBytes == 0) {
            return errSSLClosedGraceful
        }

        currentPtr += recvBytes
        pendingSize -= recvBytes
        totalReadSize += recvBytes
        //println("  --> $recvBytes")
    }
    size?.set(0, totalReadSize.convert())
    return error.convert()
}

private fun SSL_send_callback(
    connection: SSLConnectionRef?,
    ptr: COpaquePointer?,
    size: CPointer<size_tVar>?
): OSStatus {
    val sockfd = connection?.reinterpret<LongVar>()?.get(0) ?: error("No socket provided")
    val requested = (size?.get(0) ?: 0.convert()).toInt()
    var currentPtr = ptr?.reinterpret<ByteVar>()
    var pendingSize = requested
    var totalSent = 0
    size?.set(0, 0.convert())

    while (pendingSize > 0) {
        val sentBytes = send(sockfd.convert(), currentPtr, pendingSize.convert(), 0).toInt()
        if (sentBytes < 0) {
            size?.set(0, totalSent.convert())
            return if (errno == EAGAIN || errno == EWOULDBLOCK) errSSLWouldBlock else ioErr.convert()
        }
        currentPtr = currentPtr?.plus(sentBytes)
        pendingSize -= sentBytes
        totalSent += sentBytes
    }
    size?.set(0, totalSent.convert())
    return noErr.convert()
}
