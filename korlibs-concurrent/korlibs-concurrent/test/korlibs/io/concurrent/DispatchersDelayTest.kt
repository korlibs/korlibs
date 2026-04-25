package korlibs.io.concurrent

import korlibs.concurrent.thread.*
import korlibs.io.async.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*
import kotlin.time.*

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
