package kotlinx.coroutines

import korlibs.time.fast.*

suspend fun delay(time: FastDuration) {
    delay(time.milliseconds.toLong())
}
