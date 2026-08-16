@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.io.socket

import kotlinx.cinterop.Arena
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVarOf
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.CoreFoundation.CFArrayCreate
import platform.CoreFoundation.CFArrayGetValueAtIndex
import platform.CoreFoundation.CFArrayRefVar
import platform.CoreFoundation.CFDataCreate
import platform.CoreFoundation.CFDictionaryCreate
import platform.CoreFoundation.CFDictionaryGetValue
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFStringEncodingUTF8
import platform.Security.SSLClose
import platform.Security.SSLConnectionRef
import platform.Security.SSLConnectionType
import platform.Security.SSLCreateContext
import platform.Security.SSLGetSessionState
import platform.Security.SSLHandshake
import platform.Security.SSLProtocolSide
import platform.Security.SSLRead
import platform.Security.SSLSessionState
import platform.Security.SSLSetCertificate
import platform.Security.SSLSetConnection
import platform.Security.SSLSetIOFuncs
import platform.Security.SSLSetSessionOption
import platform.Security.SSLWrite
import platform.Security.SecIdentityRef
import platform.Security.SecPKCS12Import
import platform.Security.errSSLClosedGraceful
import platform.Security.errSSLServerAuthCompleted
import platform.Security.errSSLWouldBlock
import platform.Security.kSSLSessionOptionBreakOnServerAuth
import platform.Security.kSecImportExportPassphrase
import platform.Security.kSecImportItemIdentity
import platform.darwin.OSStatus
import platform.darwin.inet_addr
import platform.darwin.noErr
import platform.posix.AF_INET
import platform.posix.EAGAIN
import platform.posix.EWOULDBLOCK
import platform.posix.SOCK_STREAM
import platform.posix.SOL_SOCKET
import platform.posix.SO_REUSEADDR
import platform.posix.accept
import platform.posix.bind
import platform.posix.close
import platform.posix.errno
import platform.posix.listen
import platform.posix.recv
import platform.posix.send
import platform.posix.setsockopt
import platform.posix.size_tVar
import platform.posix.sockaddr_in
import platform.posix.socket

/**
 * A minimal single-connection, single-request TLS echo server, used ONLY to
 * give integration tests a local TLS endpoint to connect [DarwinSSLSocket] to.
 *
 * This is deliberately not production-quality: one accept, one handshake,
 * one echo, then it tears down. It exists purely so tests don't depend on
 * any external network resource.
 */
class TestTlsServer(private val port: Int) {

    private var listenFd: Int = -1
    private var job: Job? = null
    private val connectionArena = Arena() // lives for the connection, not just one call

    /** Starts listening and handling exactly one connection in the background. */
    fun start(scope: CoroutineScope = CoroutineScope(Dispatchers.Default)) {
        listenFd = socket(AF_INET, SOCK_STREAM, 0)
        check(listenFd >= 0) { "Failed to create listening socket, errno=$errno" }

        memScoped {
            val reuse = alloc<IntVar>()
            reuse.value = 1
            setsockopt(listenFd, SOL_SOCKET, SO_REUSEADDR, reuse.ptr, sizeOf<IntVar>().convert())

            val addr = alloc<sockaddr_in>()
            addr.sin_family = AF_INET.convert()
            addr.sin_port = swapBytesForServer(port.toUShort()).convert()
            addr.sin_addr.s_addr = inet_addr("127.0.0.1")

            val bindResult = bind(listenFd, addr.ptr.reinterpret(), sizeOf<sockaddr_in>().convert())
            check(bindResult == 0) { "bind() failed, errno=$errno" }
        }

        check(listen(listenFd, 1) == 0) { "listen() failed, errno=$errno" }

        job = scope.launch {
            try {
                acceptAndServeOnce()
            } catch (e: Throwable) {
                println("TestTlsServer failed: $e")
            }
        }
    }

    fun stop() {
        job?.cancel()
        if (listenFd >= 0) close(listenFd)
        listenFd = -1
    }

    private suspend fun acceptAndServeOnce() {
        val clientFd = accept(listenFd, null, null)
        if (clientFd < 0) return

        val identity = loadTestIdentity()
        val ctx = SSLCreateContext(null, SSLProtocolSide.kSSLServerSide, SSLConnectionType.kSSLStreamType)
        try {
            val fdVar = connectionArena.alloc<LongVar>()
            fdVar.value = clientFd.toLong()
            SSLSetConnection(ctx, fdVar.ptr)

            SSLSetIOFuncs(ctx, staticCFunction(::testServerRecv), staticCFunction(::testServerSend))

            memScoped {
                val certArray = CFArrayCreate(null, cValuesOf(identity) as CValuesRef<CPointerVarOf<CPointer<out CPointed>>>, 1, null)
                val status = SSLSetCertificate(ctx, certArray)
                check(status == 0) { "SSLSetCertificate failed: $status" }
            }

            while (true) {
                val state = memScoped {
                    val s = alloc<SSLSessionState.Var>()
                    SSLGetSessionState(ctx, s.ptr)
                    s.value
                }
                when (state) {
                    SSLSessionState.kSSLIdle, SSLSessionState.kSSLHandshake -> {
                        when (val status = SSLHandshake(ctx)) {
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
                    SSLSessionState.kSSLClosed -> return
                    SSLSessionState.kSSLAborted -> return
                    SSLSessionState.kSSLConnected -> break
                    else -> Unit
                }
            }

            // Echo loop: read whatever the client sends, write it straight back.
            memScoped {
                val buffer = allocArray<ByteVar>(4096)
                val processed = alloc<size_tVar>()
                val readStatus = SSLRead(ctx, buffer, 4096.convert(), processed.ptr)
                if (readStatus == 0 && processed.value.toInt() > 0) {
                    val writtenVar = alloc<size_tVar>()
                    SSLWrite(ctx, buffer, processed.value, writtenVar.ptr)
                }
            }
        } finally {
            SSLClose(ctx)
            close(clientFd)
            connectionArena.clear()
        }
    }

    private fun loadTestIdentity(): SecIdentityRef = memScoped {
        val p12Bytes = base64Decode(TEST_SERVER_P12_BASE64)
        val p12Data = p12Bytes.usePinned { pinned ->
            CFDataCreate(null, pinned.addressOf(0).reinterpret(), p12Bytes.size.convert())
        } ?: error("Failed to create CFData for test certificate")

        val passwordRef = CFStringCreateWithCString(null, TEST_SERVER_P12_PASSWORD, kCFStringEncodingUTF8)
        val keys = allocArray<CFTypeRefVar>(1)
        val values = allocArray<CFTypeRefVar>(1)

        keys[0] = kSecImportExportPassphrase
        values[0] = passwordRef

        val options = CFDictionaryCreate(null, keys.reinterpret(), values.reinterpret(), 1, null, null)

        val itemsVar = alloc<CFArrayRefVar>()
        val status = SecPKCS12Import(p12Data, options, itemsVar.ptr)
        check(status == 0 && itemsVar.value != null) { "SecPKCS12Import failed: $status" }

        val itemDict: CFDictionaryRef = CFArrayGetValueAtIndex(itemsVar.value, 0)!!.reinterpret()
        val identityPtr = CFDictionaryGetValue(itemDict, kSecImportItemIdentity)
            ?: error("PKCS12 import result had no identity")
        identityPtr.reinterpret()
    }

    companion object {
        private fun swapBytesForServer(v: UShort): UShort =
            (((v.toInt() and 0xFF) shl 8) or ((v.toInt() ushr 8) and 0xFF)).toUShort()
    }
}

private fun testServerRecv(
    connection: SSLConnectionRef?,
    ptr: COpaquePointer?,
    size: CPointer<size_tVar>?
): OSStatus {
    val fd = connection?.reinterpret<LongVar>()?.get(0) ?: return (-36).convert()
    val requested = size?.get(0)?.toInt() ?: 0
    var currentPtr = ptr?.reinterpret<ByteVar>()
    var pendingSize = requested
    var totalRead = 0
    size?.set(0, 0.convert())

    while (pendingSize > 0) {
        val receivedBytes = recv(fd.convert(), currentPtr, pendingSize.convert(), 0).toInt()
        if (receivedBytes < 0) {
            size?.set(0, totalRead.convert())
            return if (errno == EAGAIN || errno == EWOULDBLOCK) errSSLWouldBlock else (-36).convert()
        }
        if (receivedBytes == 0) {
            size?.set(0, totalRead.convert())
            return errSSLClosedGraceful
        }
        currentPtr = currentPtr?.plus(receivedBytes)
        pendingSize -= receivedBytes
        totalRead += receivedBytes
    }
    size?.set(0, totalRead.convert())
    return noErr.convert()
}

private fun testServerSend(
    connection: SSLConnectionRef?,
    ptr: COpaquePointer?,
    size: CPointer<size_tVar>?
): OSStatus {
    val fd = connection?.reinterpret<LongVar>()?.get(0) ?: return (-36).convert()
    val requested = (size?.get(0) ?: 0.convert()).toInt()
    var currentPtr = ptr?.reinterpret<ByteVar>()
    var pendingSize = requested
    var totalSent = 0
    size?.set(0, 0.convert())

    while (pendingSize > 0) {
        val sentBytes = send(fd.convert(), currentPtr, pendingSize.convert(), 0).toInt()
        if (sentBytes < 0) {
            size?.set(0, totalSent.convert())
            return if (errno == EAGAIN || errno == EWOULDBLOCK) errSSLWouldBlock else (-36).convert()
        }
        currentPtr = currentPtr?.plus(sentBytes)
        pendingSize -= sentBytes
        totalSent += sentBytes
    }
    size?.set(0, totalSent.convert())
    return noErr.convert()
}

// --- Test fixture certificate (self-signed, CN=localhost, 10y validity) ---
// Regenerate with:
//   openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days 3650 -nodes -subj "/CN=localhost"
//   openssl pkcs12 -export -out test-server.p12 -inkey key.pem -in cert.pem -passout pass:testpass
//   base64 -w0 test-server.p12
private const val TEST_SERVER_P12_PASSWORD = "testpass"
private const val TEST_SERVER_P12_BASE64 =
    "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"

private fun base64Decode(input: String): ByteArray {
    val table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    val clean = input.filter { it != '\n' && it != '\r' }
    val output = ArrayList<Byte>(clean.length * 3 / 4)
    var buffer = 0
    var bitsCollected = 0
    for (c in clean) {
        if (c == '=') break
        val value = table.indexOf(c)
        check(value >= 0) { "Invalid base64 character: $c" }
        buffer = (buffer shl 6) or value
        bitsCollected += 6
        if (bitsCollected >= 8) {
            bitsCollected -= 8
            output.add(((buffer shr bitsCollected) and 0xFF).toByte())
        }
    }
    return output.toByteArray()
}