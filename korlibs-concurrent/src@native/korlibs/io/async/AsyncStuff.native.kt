@file:OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)

package korlibs.io.async

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.posix.*
import kotlin.coroutines.*
import kotlin.experimental.*

@Suppress("ACTUAL_WITHOUT_EXPECT", "ACTUAL_TYPE_ALIAS_TO_CLASS_WITH_DECLARATION_SITE_VARIANCE")
actual typealias AsyncEntryPointResult = Unit

actual fun asyncEntryPoint(callback: suspend () -> Unit) = runBlocking {
    if (Platform.osFamily == OsFamily.WINDOWS) {
        setlocale(LC_ALL, ".UTF-8")
    }
    callback()
}

actual fun asyncTestEntryPoint(callback: suspend () -> Unit) = asyncEntryPoint(callback)

actual fun <T> runBlockingNoJs(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T =
    runBlocking(runBlockingNoJs_transformContext(context)) { block() }
