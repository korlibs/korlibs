package korlibs.io

import korlibs.io.async.asyncEntryPoint
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope

fun Korio(entry: suspend CoroutineScope.() -> Unit) = asyncEntryPoint { entry(CoroutineScope(coroutineContext)) }
