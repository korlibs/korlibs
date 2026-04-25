package korlibs.io.async

import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.time.*

suspend fun <T> withTimeoutNullable(time: Duration?, block: suspend CoroutineScope.() -> T): T {
	return if (time == null || time.isNil) {
		block(CoroutineScope(coroutineContext))
	} else {
		withTimeout(time.millisecondsLong, block)
	}
}
