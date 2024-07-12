@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package korlibs.io.async

import korlibs.datastructure.*
import korlibs.io.lang.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.coroutines.*
import kotlin.time.*

abstract class BaseSignal<T, THandler>(val onRegister: () -> Unit = {}) {
    inner class Node(val once: Boolean, val item: THandler) : AutoCloseable {
        override fun close() {
            if (iterating > 0) {
                handlersToRemove.add(this)
            } else {
                handlers.remove(this)
            }
        }
    }

    protected var handlers: MutableList<Node> = FastArrayList<Node>()
    protected var handlersToRemove: MutableList<Node> = FastArrayList<Node>()
    val listenerCount: Int get() = handlers.size
    val hasListeners get() = listenerCount > 0
    fun clear() = handlers.clear()

    // @TODO: This breaks binary compatibility
    //fun once(handler: THandler): AutoCloseable = _add(true, handler)
    //fun add(handler: THandler): AutoCloseable = _add(false, handler)
    //operator fun invoke(handler: THandler): AutoCloseable = add(handler)

    protected fun _add(once: Boolean, handler: THandler): AutoCloseable {
        onRegister()
        val node = Node(once, handler)
        handlers.add(node)
        return node
    }
    protected var iterating: Int = 0
    protected inline fun iterateCallbacks(callback: (THandler) -> Unit) {
        if (handlers.isEmpty()) return
        try {
            iterating++
            handlers.fastIterateRemove { node ->
                val remove = node.once
                callback(node.item)
                remove
            }
        } finally {
            iterating--
            if (handlersToRemove.isNotEmpty()) {
                handlersToRemove.fastIterateRemove {
                    handlers.remove(it)
                    true
                }
            }
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun listen(): ReceiveChannel<T> = CoroutineScope(coroutineContext).produce {
        while (true) send(waitOneBase())
    }
    abstract suspend fun waitOneBase(): T
}

class AsyncSignal<T>(onRegister: () -> Unit = {}) : BaseSignal<T, suspend (T) -> Unit>(onRegister) {
    fun once(handler: suspend (T) -> Unit): AutoCloseable = _add(true, handler)
    fun add(handler: suspend (T) -> Unit): AutoCloseable = _add(false, handler)
    operator fun invoke(handler: suspend (T) -> Unit): AutoCloseable = add(handler)

    suspend operator fun invoke(value: T) = iterateCallbacks{ it(value) }
    override suspend fun waitOneBase(): T = suspendCancellableCoroutine { c ->
        var close: AutoCloseable? = null
        close = once {
            close?.close()
            c.resume(it)
        }
        c.invokeOnCancellation {
            close.close()
        }
    }
}

class Signal<T>(onRegister: () -> Unit = {}) : BaseSignal<T, (T) -> Unit>(onRegister) {
    fun once(handler: (T) -> Unit): AutoCloseable = _add(true, handler)
    fun add(handler: (T) -> Unit): AutoCloseable = _add(false, handler)
    operator fun invoke(handler: (T) -> Unit): AutoCloseable = add(handler)
    operator fun invoke(value: T) = iterateCallbacks { it(value) }
    override suspend fun waitOneBase(): T= suspendCancellableCoroutine { c ->
        var close: AutoCloseable? = null
        close = once {
            close?.close()
            c.resume(it)
        }
        c.invokeOnCancellation {
            close?.close()
        }
    }
}

suspend fun <T> AsyncSignal<T>.waitOne() = waitOneBase()
suspend fun <T> Signal<T>.waitOne() = waitOneBase()

fun <TI, TO> AsyncSignal<TI>.mapSignal(transform: (TI) -> TO): AsyncSignal<TO> {
    val out = AsyncSignal<TO>()
    this.add { out(transform(it)) }
    return out
}

suspend operator fun AsyncSignal<Unit>.invoke() = invoke(Unit)

//////////////////////////////////


//class AsyncSignal<T>(context: CoroutineContext) {

//}

fun <TI, TO> Signal<TI>.mapSignal(transform: (TI) -> TO): Signal<TO> {
    val out = Signal<TO>()
    this.add { out(transform(it)) }
    return out
}

operator fun Signal<Unit>.invoke() = invoke(Unit)

suspend fun Iterable<Signal<*>>.waitOne(): Any? = suspendCancellableCoroutine { c ->
    val closes = FastArrayList<AutoCloseable>()
    for (signal in this) {
        closes += signal.once {
            closes.close()
            c.resume(it)
        }
    }

    c.invokeOnCancellation {
        closes.close()
    }
}

fun <T> Signal<T>.waitOneAsync(): Deferred<T> {
    val deferred = CompletableDeferred<T>(Job())
    var close: AutoCloseable? = null
    close = once {
        close?.close()
        deferred.complete(it)
    }
    deferred.invokeOnCompletion {
        close.close()
    }
    return deferred
}

suspend fun <T> Signal<T>.addSuspend(handler: suspend (T) -> Unit): AutoCloseable {
    val cc = coroutineContext
    return this@addSuspend { value ->
        CoroutineScope(cc).launch {
            handler(value)
        }
    }
}

fun <T> Signal<T>.addSuspend(context: CoroutineContext, handler: suspend (T) -> Unit): AutoCloseable =
    this@addSuspend { value ->
        CoroutineScope(context).launch {
            handler(value)
        }
    }


suspend fun <T> Signal<T>.waitOne(timeout: Duration): T? = kotlinx.coroutines.suspendCancellableCoroutine { c ->
    var close: AutoCloseable? = null
    var running = true

    fun closeAll() {
        running = false
        close?.close()
    }

    CoroutineScope(c.context).launch {
        delay(timeout)
        if (running) {
            closeAll()
            c.resume(null)
        }
    }

    close = once {
        closeAll()
        c.resume(it)
    }

    c.invokeOnCancellation {
        closeAll()
    }
}

suspend fun <T> Signal<T>.waitOneOpt(timeout: Duration?): T? = when {
    timeout != null -> waitOne(timeout)
    else -> waitOneBase()
}

suspend inline fun <T> Map<Signal<Unit>, T>.executeAndWaitAnySignal(callback: () -> Unit): T {
    val deferred = CompletableDeferred<T>()
    val closeables = this.map { pair -> pair.key.once { deferred.complete(pair.value) } }
    try {
        callback()
        return deferred.await()
    } finally {
        closeables.close()
    }
}

suspend inline fun <T> Iterable<Signal<T>>.executeAndWaitAnySignal(callback: () -> Unit): Pair<Signal<T>, T> {
    val deferred = CompletableDeferred<Pair<Signal<T>, T>>()
    val closeables = this.map { signal -> signal.once { deferred.complete(signal to it) } }
    try {
        callback()
        return deferred.await()
    } finally {
        closeables.close()
    }
}

suspend inline fun <T> Signal<T>.executeAndWaitSignal(callback: () -> Unit): T {
    val deferred = CompletableDeferred<T>()
    val closeable = this.once { deferred.complete(it) }
    try {
        callback()
        return deferred.await()
    } finally {
        closeable.close()
    }
}

fun <T> Signal<T>.addCallInit(initial: T, handler: (T) -> Unit): AutoCloseable {
    handler(initial)
    return add(handler)
}

fun Signal<Unit>.addCallInit(handler: (Unit) -> Unit): AutoCloseable = addCallInit(Unit, handler)

@PublishedApi internal inline fun <T> MutableList<T>.fastIterateRemove(callback: (T) -> Boolean): MutableList<T> {
    var n = 0
    var m = 0
    while (n < size) {
        if (m >= 0 && m != n) this[m] = this[n]
        if (callback(this[n])) m--
        n++
        m++
    }
    while (this.size > m) this.removeAt(this.size - 1)
    return this
}
