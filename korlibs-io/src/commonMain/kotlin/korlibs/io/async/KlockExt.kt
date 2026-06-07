package korlibs.io.async

import korlibs.time.isNil
import korlibs.time.millisecondsLong
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withTimeout

suspend fun <T> withTimeoutNullable(time: Duration?, block: suspend CoroutineScope.() -> T): T {
	return if (time == null || time.isNil) {
		block(CoroutineScope(coroutineContext))
	} else {
		withTimeout(time.millisecondsLong, block)
	}
}
