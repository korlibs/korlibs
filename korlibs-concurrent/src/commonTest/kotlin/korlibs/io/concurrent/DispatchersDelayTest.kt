package korlibs.io.concurrent

import korlibs.concurrent.thread.FixedPoolNativeThreadDispatcher
import korlibs.concurrent.thread.NativeThread
import korlibs.concurrent.thread.NativeThreadPriority
import korlibs.io.async.CIO
import korlibs.time.milliseconds
import kotlin.test.Test
import kotlin.time.measureTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

class DispatchersDelayTest {
    @Test
    fun test() = runTest {
        if (!NativeThread.isSupported) return@runTest
        // Dispatchers.IO on the JVM time 10..15, Dispatchers.AUDIO on the JVM time 1..2
        // Dispatchers.IO on MingwX64 time 13.18, Dispatchers.AUDIO on MingwX64 time 1..1.1
        //NativeThreadDispatcher("AUDIO", NativeThreadPriority.HIGHER).use { AUDIO ->
        FixedPoolNativeThreadDispatcher(8, "AUDIO", NativeThreadPriority.HIGHER).use { AUDIO ->
            repeat(10) {
                withContext(Dispatchers.CIO) { println("CIO: " + measureTime { delay(1.milliseconds) }) }
                withContext(AUDIO) { println("AUDIO: " + measureTime { delay(1.milliseconds) }) }
            }
        }
    }
}
