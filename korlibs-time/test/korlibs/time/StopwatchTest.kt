package korlibs.time

import kotlin.test.*

class StopwatchTest {
    @Test
    fun test() {
        var nanos = 107.0
        val stopwatch = Stopwatch { nanos }

        // It starts stopped, and elapsed doesn't move
        fun check() = stopwatch.elapsedNanoseconds
        assertEquals(0.0, check()).also { nanos++ }
        assertEquals(0.0, check()).also { nanos++ }
        assertEquals(0.0, check())

        // then we start it, and elapsed starts moving
        stopwatch.start()
        assertEquals(0.0, check()).also { nanos++ }
        assertEquals(1.0, check()).also { nanos++ }
        assertEquals(2.0, check())

        // then we stop it, and elapsed stops moving
        stopwatch.stop()
        assertEquals(2.0, check()).also { nanos++ }
        assertEquals(2.0, check())

        // then we resume it, and elapsed continues moving from where it stopped
        stopwatch.resume()
        assertEquals(2.0, check()).also { nanos++ }
        assertEquals(3.0, check()).also { nanos++ }
        assertEquals(4.0, check())

        // then we restart it, and elapsed starts from zero
        stopwatch.start()
        assertEquals(0.0, check()).also { nanos++ }
        assertEquals(1.0, check()).also { nanos++ }

        // then we arbitrarily set the elapsed time
        stopwatch.elapsed = 15.nanoseconds
        assertEquals(15.0, check()).also { nanos++ }
        assertEquals(16.0, check())

        // then we stop it, and set arbitrarily elapsed time in stopped state
        stopwatch.stop()
        stopwatch.elapsed = 17.nanoseconds
        assertEquals(17.0, check()).also { nanos++ }
        assertEquals(17.0, check())
    }

    @Test
    @Ignore
    @OptIn(ExperimentalStdlibApi::class)
    fun testRealtime() {
        val stopwatch = Stopwatch().start()
        println("STARTED")
        println(stopwatch.elapsed)
        blockingSleep(200.milliseconds)
        println(stopwatch.elapsed)
        stopwatch.stop()
        println("STOPPED")
        println(stopwatch.elapsed)
        blockingSleep(150.milliseconds)
        println(stopwatch.elapsed)
        stopwatch.resume()
        println("RESUMED (~200)")
        println(stopwatch.elapsed)
        blockingSleep(75.milliseconds)
        println(stopwatch.elapsed)
    }
}
