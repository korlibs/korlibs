@file:OptIn(ExperimentalStdlibApi::class)

package korlibs.io.lang

import korlibs.io.async.*
import kotlin.contracts.*
import kotlin.coroutines.cancellation.*

@OptIn(ExperimentalStdlibApi::class)
object DummyAutoCloseable : AutoCloseable {
    override fun close() = Unit
}

val DummyCloseable get() = DummyAutoCloseable

@OptIn(ExperimentalStdlibApi::class)
interface OptionalAutoCloseable : AutoCloseable {
    override fun close() = Unit
}

// Since this is not a functional interface
fun AutoCloseable(block: () -> Unit): AutoCloseable = object : AutoCloseable {
    override fun close() = block()
}

fun Closeable(block: () -> Unit): AutoCloseable = AutoCloseable(block)

fun interface Cancellable {
    fun cancel(e: Throwable): Unit
    //fun close() = this.cancel(CancellationException(""))

    companion object {
        operator fun invoke(callback: (Throwable) -> Unit) = Cancellable { e -> callback(e) }
        operator fun invoke(cancellables: List<Cancellable>) = Cancellable { e -> cancellables.fastForEach { it.cancel(e) } }
    }
}

fun Cancellable.cancel() = cancel(CancellationException(""))

fun Iterable<Cancellable>.cancel(e: Throwable = CancellationException("")): Unit =
    run { for (c in this) c.cancel(e) }

fun Iterable<Cancellable>.cancellable() = Cancellable { this.cancel() }

fun Iterable<AutoCloseable>.close() { for (c in this) c.close() }
fun Iterable<AutoCloseable>.closeable() = AutoCloseable { this.close() }

fun AutoCloseable.cancellable() = Cancellable { this.close() }
fun Cancellable.closeable(e: () -> Throwable = { CancellationException("") }) =
    AutoCloseable { this.cancel(e()) }

@OptIn(ExperimentalStdlibApi::class)
interface CloseableCancellable : AutoCloseable, Cancellable {
    companion object {
        operator fun invoke(callback: (Throwable?) -> Unit) = object : CloseableCancellable {
            override fun close() = callback(null)
            override fun cancel(e: Throwable) = callback(e)
        }
    }

    override fun cancel(e: Throwable) = close()
}

@OptIn(ExperimentalStdlibApi::class)
class CancellableGroup() : CloseableCancellable {
    private val cancellables = arrayListOf<Cancellable>()

    constructor(vararg items: Cancellable) : this() { items.fastForEach { this += it } }
    constructor(items: Iterable<Cancellable>) : this() { for (it in items) this += it }

    operator fun plusAssign(c: CloseableCancellable) { cancellables += c }
    operator fun plusAssign(c: Cancellable) { cancellables += c }
    operator fun plusAssign(c: AutoCloseable) { cancellables += c.cancellable() }
    fun addCancellable(c: Cancellable) { cancellables += c }
    fun addCloseable(c: AutoCloseable) { cancellables += c.cancellable() }
    override fun close() { cancel(kotlin.coroutines.cancellation.CancellationException()) }
    override fun cancel(e: Throwable) { cancellables.cancel(e) }

    companion object {
        inline operator fun <T> invoke(callback: (CancellableGroup) -> T): T {
            val group = CancellableGroup()
            try {
                return callback(group)
            } finally {
                group.cancel()
            }
        }
    }
}

private inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

private inline fun <T> Array<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}


@OptIn(ExperimentalContracts::class)
@Deprecated("", ReplaceWith("use(block)"))
inline fun <T : AutoCloseable?, TR> T.useIt(block: (T) -> TR): TR {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return use(block)
}

@OptIn(ExperimentalContracts::class)
inline fun <T : AutoCloseable?, TR> T.useThis(block: T.() -> TR): TR {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return use(block)
}
