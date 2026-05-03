package korlis.audio.sound

import korlibs.audio.sound.AudioPlatformOutput
import korlibs.audio.sound.AudioSamplesInterleaved
import korlibs.audio.sound.SoundListenerProps
import kotlin.test.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class AudioPlatformOutputTest {

    @Test
    fun testGenSafeIsThreadSafe() = runTest {
        val output = AudioPlatformOutput(
            listener = object : SoundListenerProps {},
            channels = 2,
            frequency = 44100,
            gen = {},
            scope = CoroutineScope(UnconfinedTestDispatcher(testScheduler)),
        )
        val buffer = AudioSamplesInterleaved(2, 256)
        val threads = (1..8).map {
            Thread { repeat(50) { output.genSafe(buffer) } }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
    }
}
