package kotlinx.coroutines

import korlibs.time.*

suspend fun delay(time: FastDuration) {
    delay(timeMillis = time.milliseconds.toLong())
}
