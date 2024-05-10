package korlibs.io.async

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.*

suspend fun <T1, T2> Signal2<T1, T2>.waitOne() = waitOneBase()

class Signal2<T1, T2>(onRegister: () -> Unit = {}) : BaseSignal2<T1, T2>(onRegister) {
    fun once(handler: (T1, T2) -> Unit): AutoCloseable = _add(true, handler)
    fun add(handler: (T1, T2) -> Unit): AutoCloseable = _add(false, handler)
    operator fun invoke(handler: (T1, T2) -> Unit): AutoCloseable = add(handler)
    operator fun invoke(value1: T1, value2: T2) = iterateCallbacks { it(value1, value2) }
    override suspend fun waitOneBase(): Pair<T1, T2> = suspendCancellableCoroutine { c ->
        var close: AutoCloseable? = null
        close = once { i1, i2 ->
            close?.close()
            c.resume(Pair(i1, i2))
        }
        c.invokeOnCancellation {
            close?.close()
        }
    }
}

abstract class BaseSignal2<T1, T2>(val onRegister: () -> Unit = {}) {
    inner class Node(val once: Boolean, val item: (T1, T2) -> Unit) : AutoCloseable {
        override fun close() {
            if (iterating > 0) {
                handlersToRemove.add(this)
            } else {
                handlers.remove(this)
            }
        }
    }

    protected var handlers = ArrayList<Node>()
    protected var handlersToRemove = ArrayList<Node>()
    val listenerCount: Int get() = handlers.size
    fun clear() = handlers.clear()

    // @TODO: This breaks binary compatibility
    //fun once(handler: THandler): AutoCloseable = _add(true, handler)
    //fun add(handler: THandler): AutoCloseable = _add(false, handler)
    //operator fun invoke(handler: THandler): AutoCloseable = add(handler)

    protected fun _add(once: Boolean, handler: (T1, T2) -> Unit): AutoCloseable {
        onRegister()
        val node = Node(once, handler)
        handlers.add(node)
        return node
    }
    protected var iterating: Int = 0
    protected inline fun iterateCallbacks(callback: ((T1, T2) -> Unit) -> Unit) {
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
    suspend fun listen(): ReceiveChannel<Pair<T1, T2>> = CoroutineScope(coroutineContext).produce {
        while (true) send(waitOneBase())
    }
    abstract suspend fun waitOneBase(): Pair<T1, T2>
}

