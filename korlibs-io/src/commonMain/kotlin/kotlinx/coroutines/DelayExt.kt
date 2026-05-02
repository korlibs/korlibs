package kotlinx.coroutines

import korlibs.time.*

suspend fun delay(time: FastDuration) {
    delay(time.milliseconds.toLong())
}
