package korlibs.inject

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.withContext

suspend fun <T> withInjector(injector: Injector, block: suspend () -> T): T =
    withContext(InjectorContext(injector)) {
        block()
    }

suspend fun injector(): Injector =
    coroutineContext[InjectorContext]?.injector
        ?: error("Injector not in the context, please call withInjector function")

class InjectorContext(val injector: Injector) : CoroutineContext.Element {
    companion object : CoroutineContext.Key<InjectorContext>

    override val key get() = InjectorContext
}
