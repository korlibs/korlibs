package kotlinx.coroutines

import korlibs.time.FastDuration

suspend fun delay(time: FastDuration) {
    delay(timeMillis = time.milliseconds.toLong())
}
