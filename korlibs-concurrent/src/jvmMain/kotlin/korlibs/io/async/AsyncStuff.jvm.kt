package korlibs.io.async

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@Suppress("ACTUAL_WITHOUT_EXPECT", "ACTUAL_TYPE_ALIAS_TO_CLASS_WITH_DECLARATION_SITE_VARIANCE")
actual typealias AsyncEntryPointResult = Unit

actual fun asyncEntryPoint(callback: suspend () -> Unit) = runBlocking { callback() }
actual fun asyncTestEntryPoint(callback: suspend () -> Unit) = runBlocking { callback() }

actual fun <T> runBlockingNoJs(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T =
    runBlocking(runBlockingNoJs_transformContext(context)) { block() }
