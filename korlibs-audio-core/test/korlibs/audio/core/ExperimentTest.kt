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
        source.setData(44100, 1, arrayOf(AudioSampleArray(44100) {
            AudioSample(kotlin.math.cos(it.toFloat() / 40f))
        }))
        source.play()
        NativeThread.sleep(10.seconds)
    }
}