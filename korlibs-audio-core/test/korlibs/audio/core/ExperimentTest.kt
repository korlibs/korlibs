package korlibs.audio.core

import korlibs.concurrent.thread.*
import korlibs.time.*
import kotlin.test.*

class ExperimentTest {
    @Test
    @Ignore
    fun test() {
        println(defaultAudioSystem.devices)
        val source = defaultAudioPlayer.createSource()
        println(defaultAudioPlayer.device)
        source.setContent(AudioBuffer(SeparatedAudioSamples(AudioSamples(44100 * 2) {
            AudioSample(kotlin.math.cos(it.toFloat() / 40f))
        }), rate = 44100))
        source.play()
        NativeThread.sleep(10.seconds)
    }
}