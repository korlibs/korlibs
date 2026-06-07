package kotlinx.coroutines

import korlibs.time.FastDuration

suspend fun delay(time: FastDuration) {
    delay(time.milliseconds.toLong())
}
