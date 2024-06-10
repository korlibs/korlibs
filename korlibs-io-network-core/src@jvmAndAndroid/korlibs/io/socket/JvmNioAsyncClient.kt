package korlibs.io.socket

import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.util.concurrent.TimeUnit

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import java.nio.channels.AsynchronousCloseException
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.CompletionHandler
import kotlin.coroutines.*
import kotlin.coroutines.cancellation.CancellationException

class JvmNioAsyncClient(private var client: AsynchronousSocketChannel? = null) : AsyncSocket {
    private val readQueue = Mutex()
    private val writeQueue = Mutex()

    override val address: AsyncSocketAddress get() = client?.remoteAddress.toAsyncAddress()

    override suspend fun connect(host: String, port: Int) {
        val oldClient = this.client
        CoroutineScope(Dispatchers.IO).launch { oldClient?.close() }
        val client = withContext(Dispatchers.IO) { AsynchronousSocketChannel.open(
            //AsynchronousChannelGroup.withThreadPool(EventLoopExecutorService(coroutineContext))
        ) }
        this.client = client
        nioSuspendCompletion<Void> { client.connect(InetSocketAddress(host, port), Unit, it) }
    }

    override val connected: Boolean get() = client?.isOpen ?: false

    private val clientSure: AsynchronousSocketChannel get() = client ?: throw IOException("Not connected")

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = readQueue.withLock {
        nioSuspendCompletion<Int> {
            clientSure.read(ByteBuffer.wrap(buffer, offset, len), 0L, TimeUnit.MILLISECONDS, Unit, it)
        }.toInt()
    }

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int): Unit = writeQueue.withLock {
        val b = ByteBuffer.wrap(buffer, offset, len)
        while (b.hasRemaining()) writePartial(b)
    }

    private suspend fun writePartial(buffer: ByteBuffer): Int {
        AsyncSocket.Stats.writeCountStart.incrementAndGet()
        try {
            return nioSuspendCompletion<Int> {
                clientSure.write(buffer, 0L, TimeUnit.MILLISECONDS, Unit, it)
            }.also {
                AsyncSocket.Stats.writeCountEnd.incrementAndGet()
            }
        } catch (e: Throwable) {
            AsyncSocket.Stats.writeCountError.incrementAndGet()
            throw e
        }
    }

    override suspend fun close() {
        CoroutineScope(Dispatchers.IO).launch {
            client?.close()
            client = null
        }
    }
}

/*
class JvmAsyncClientAsynchronousSocketChannel(private var sc: AsynchronousSocketChannel? = null) : AsyncClient {
    private val readQueue = AsyncThread2()
    private val writeQueue = AsyncThread2()

    override val address: AsyncAddress get() = sc?.remoteAddress?.toAsyncAddress() ?: super.address

    //suspend override fun connect(host: String, port: Int): Unit = suspendCoroutineEL { c ->
    override suspend fun connect(host: String, port: Int): Unit = suspendCancellableCoroutine { c ->
        sc?.close()
        sc = AsynchronousSocketChannel.open(
            AsynchronousChannelGroup.withThreadPool(EventLoopExecutorService(c.context))
        )
        sc?.connect(InetSocketAddress(host, port), this, object : CompletionHandler<Void, AsyncClient> {
            override fun completed(result: Void?, attachment: AsyncClient) { c.resume(Unit) }
            override fun failed(exc: Throwable, attachment: AsyncClient) { c.resumeWithException(exc) }
        })
    }

    override val connected: Boolean get() = sc?.isOpen ?: false

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = readQueue { _read(buffer, offset, len) }
    //suspend override fun read(buffer: ByteArray, offset: Int, len: Int): Int = _read(buffer, offset, len)

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int): Unit = writeQueue {
        _write(buffer, offset, len)
    }

    //suspend private fun _read(buffer: ByteArray, offset: Int, len: Int): Int = suspendCoroutineEL { c ->
    private suspend fun _read(buffer: ByteArray, offset: Int, len: Int): Int = suspendCancellableCoroutine { c ->
        if (sc == null) {
            c.resumeWithException(IOException("Not connected"))
        }
        try {
            val bb = ByteBuffer.wrap(buffer, offset, len)
            sc!!.read(bb, this, object : CompletionHandler<Int, AsyncClient> {
                override fun completed(result: Int, attachment: AsyncClient): Unit = c.resume(result)
                override fun failed(exc: Throwable, attachment: AsyncClient): Unit = c.resumeWithException(exc)
            })
        } catch (e: Throwable) {
            c.resumeWithException(e)
        }
    }

    private suspend fun _write(buffer: ByteArray, offset: Int, len: Int) {
        _writeBufferFull(ByteBuffer.wrap(buffer, offset, len))
    }

    private suspend fun _writeBufferFull(bb: ByteBuffer) {
        while (bb.hasRemaining()) {
            _writeBufferPartial(bb)
        }
    }


    private suspend fun _writeBufferPartial(bb: ByteBuffer): Int = suspendCancellableCoroutine { c ->
        if (sc == null) {
            c.resumeWithException(IOException("Not connected"))
        }
        try {
            AsyncClient.Stats.writeCountStart.incrementAndGet()
            sc!!.write(bb, this, object : CompletionHandler<Int, AsyncClient> {
                override fun completed(result: Int, attachment: AsyncClient) {
                    AsyncClient.Stats.writeCountEnd.incrementAndGet()
                    c.resume(result)
                }

                override fun failed(exc: Throwable, attachment: AsyncClient) {
                    //println("write failed")
                    AsyncClient.Stats.writeCountError.incrementAndGet()
                    c.resumeWithException(exc)
                }
            })
        } catch (e: Throwable) {
            c.resumeWithException(e)
        }
    }

    override suspend fun close() {
        sc?.close()
        sc = null
    }
}
*/

class JvmNioAsyncServer(override val requestPort: Int, override val host: String, override val backlog: Int = -1) :
    AsyncServerSocket {
    val ssc: AsynchronousServerSocketChannel = AsynchronousServerSocketChannel.open()

    suspend fun init() {
        ssc.bind(InetSocketAddress(host, requestPort), backlog)
        for (n in 0 until 100) {
            if (ssc.isOpen) break
            delay(50)
        }
    }

    override val port: Int get() = (ssc.localAddress as? InetSocketAddress)?.port ?: -1

    override suspend fun accept(): AsyncSocket {
        try {
            return JvmNioAsyncClient(nioSuspendCompletion<AsynchronousSocketChannel> { ssc.accept(Unit, it) })
        } catch (e: CancellationException) {
            @Suppress("BlockingMethodInNonBlockingContext")
            ssc.close()
            throw e
        }
    }

    override suspend fun close() {
        try {
            @Suppress("BlockingMethodInNonBlockingContext")
            ssc.close()
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            if (e is AsynchronousCloseException) return
            e.printStackTrace()
        }
    }
}

private fun <T> CancellableContinuation<T>.getCompletionHandler(): CompletionHandler<T, Unit> {
    val continuation = this
    var cancelled = false
    invokeOnCancellation {
        cancelled = true
    }
    //println("JvmNioAsyncClient.Started in thread ${Thread.currentThread().id}")

    return object : CompletionHandler<T, Unit> {
        override fun completed(result: T, attachment: Unit?) {
            //println("JvmNioAsyncClient.Completed result=$result in thread ${Thread.currentThread().id}")
            if (!cancelled) continuation.resume(result)
        }
        override fun failed(exc: Throwable, attachment: Unit?) { if (!cancelled) continuation.resumeWithException(exc) }
    }
}

private suspend fun <T> nioSuspendCompletion(
    block: (CompletionHandler<T, Unit>) -> Unit
): T = suspendCancellableCoroutine {
    block(it.getCompletionHandler())
}
