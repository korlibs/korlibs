package korlibs.io.async

import kotlinx.coroutines.*
import kotlin.contracts.*
import kotlin.coroutines.*

@OptIn(ExperimentalContracts::class)
suspend fun <T> withContextNullable(
    coroutineContext: CoroutineContext?, block: suspend () -> T
): T {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return if (coroutineContext != null) {
        withContext(coroutineContext) { block() }
    } else {
        block()
    }
}
