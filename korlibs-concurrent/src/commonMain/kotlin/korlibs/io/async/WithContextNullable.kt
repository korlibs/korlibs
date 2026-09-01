package korlibs.io.async

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

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
