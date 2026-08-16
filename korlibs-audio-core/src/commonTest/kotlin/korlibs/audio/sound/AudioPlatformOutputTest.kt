package korlibs.audio.sound

import korlibs.math.geom.Vector3
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class AudioPlatformOutputTest {

    private fun makeFakeListener() = object : SoundListenerProps {}

    private fun TestScope.makeOutput(
        channels: Int = 2,
        frequency: Int = 44100,
        gen: AudioPlatformOutputGen = { },
    ) = AudioPlatformOutput(
        listener = makeFakeListener(),
        channels = channels,
        frequency = frequency,
        gen = gen,
        scope = CoroutineScope(UnconfinedTestDispatcher(testScheduler)),
    )

    @Test
    fun testInitialState() = runTest {
        val output = makeOutput()
        assertFalse(actual = output.running, message = "should not be running when initialized")
        assertFalse(actual = output.paused, message = "should not eb paused when initialized")
        assertEquals(
            expected = 1.0,
            actual = output.volume,
            message = "volume should be 1.0 when initialized",
        )
        assertEquals(
            expected = 0.0,
            actual = output.panning,
            message = "panning should be 0.0 when initialized",
        )
        assertEquals(
            expected = Vector3.ZERO,
            actual = output.position,
            message = "position should be [0,0,0] when initialized",
        )
    }

    @Test
    fun testStartSetsRunning() = runTest {
        val output = makeOutput()
        output.start()
        assertTrue(
            actual = output.running,
            message = "should be in running state after start()",
        )
        output.stop()
    }

    @Test
    fun testDoubleStartIsIdempotent() = runTest {
        val output = makeOutput()
        output.start()
        output.start()
        assertTrue(
            actual = output.running,
            message = "should remain in running state after calling start() twice",
        )
        output.stop()
    }

    @Test
    fun testStopClearsRunning() = runTest {
        val output = makeOutput()
        output.start()
        output.stop()
        advanceUntilIdle()
        assertFalse(
            actual = output.running,
            message = "should not be in running state after stop()",
        )
    }

    @Test
    fun testCloseStopsOutput() = runTest {
        val output = makeOutput()
        output.start()
        output.close()
        advanceUntilIdle()
        assertFalse(
            actual = output.running,
            message = "should not be in running state after close()",
        )
    }

    @Test
    fun testStopWhenNotRunningDoesNotThrow() = runTest {
        val output = makeOutput()
        assertFalse(output.running)
        output.stop() // should be a no-op
    }

    @Test
    fun testSimpleDefaultCallbacksDoNotThrow() = runTest {
        val simple = AudioPlatformOutputSimple()
        val buffer = AudioSamplesInterleaved(channels = 2, totalSamples = 512)
        simple.init(buffer)
        simple.output(buffer)
        simple.close(buffer)
        simple.paused(true)
        simple.paused(false)
    }

    @Test
    fun testSimpleCallbacksAreInvoked() = runTest {
        var initCalled = false
        var outputCalled = false
        var closeCalled = false
        var pausedValue: Boolean? = null

        val simple = AudioPlatformOutputSimple(
            init = { initCalled = true },
            output = { outputCalled = true },
            close = { closeCalled = true },
            paused = { pausedValue = it }
        )

        val buffer = AudioSamplesInterleaved(2, 512)
        simple.init(buffer)
        simple.output(buffer)
        simple.close(buffer)
        simple.paused(false)

        assertTrue(initCalled)
        assertTrue(outputCalled)
        assertTrue(closeCalled)
        assertEquals(false, pausedValue)
    }

    @Test
    fun testSuspendWhileRunningReturnsImmediatelyWhenNotRunning() = runTest {
        val output = makeOutput()
        // running == false, should return without needing any time advance
        output.suspendWhileRunning()
        assertFalse(output.running)
    }

    @Test
    fun testSuspendWhileRunningExitsAfterStop() = runTest {
        val output = makeOutput()
        output.start()
        // Stop after a virtual 50ms — no real time passes
        launch {
            advanceTimeBy(delayTime = 50.milliseconds)
            output.stop()
        }
        output.suspendWhileRunning()
        assertFalse(output.running)
    }

    @Test
    fun testGenSafeZeroFillsBufferBeforeGen() = runTest {
        var sawNonZeroOnEntry = false
        val output = makeOutput(gen = { buf ->
            sawNonZeroOnEntry = buf.data.any { it != AudioSample.ZERO }
        })
        val buffer = AudioSamplesInterleaved(1, 64)
        buffer.data.fill(AudioSample(0x7FFF.toShort()))
        output.genSafe(buffer)
        assertFalse(sawNonZeroOnEntry)
    }

    @Test
    fun testGenSafeSwallowsExceptions() = runTest {
        val output = makeOutput(gen = { throw RuntimeException("boom") })
        val buffer = AudioSamplesInterleaved(2, 512)
        // Should not propagate
        output.genSafe(buffer)
    }

//    @Test
//    fun testGenSafeIsThreadSafe() {
//        // Needs real threads — do not use runTest here
//        val output = AudioPlatformOutput(
//            listener = makeFakeListener(),
//            channels = 2,
//            frequency = 44100,
//            gen = { },
//            scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
//        )
//        val buffer = AudioSamplesInterleaved(2, 256)
//        val threads = (1..8).map {
//            Thread { repeat(50) { output.genSafe(buffer) } }
//        }
//        threads.forEach { it.start() }
//        threads.forEach { it.join() }
//    }

    @Test
    fun testVolumeAndPanningCanBeSet() = runTest {
        val output = makeOutput()
        output.volume = 0.5
        output.panning = -1.0
        assertEquals(0.5, output.volume)
        assertEquals(-1.0, output.panning)
    }

    @Test
    fun testGenIsCalledDuringLoop() = runTest {
        var genCallCount = 0
        val output = makeOutput(gen = { genCallCount++ })
        output.start()
        advanceTimeBy(10.milliseconds)
        output.stop()
        advanceUntilIdle()
        assertTrue(genCallCount > 0, "Expected gen to be called at least once, was $genCallCount")
    }
}
