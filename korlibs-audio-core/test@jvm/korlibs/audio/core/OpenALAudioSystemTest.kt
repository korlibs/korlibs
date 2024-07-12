package korlibs.audio.core

import korlibs.audio.core.impl.*
import korlibs.concurrent.thread.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.test.*

class OpenALAudioSystemTest {
    @Test
    @Ignore
    fun test() {
        //println(nativeOpenALLibraryPath)
        //println(AL.alGetInteger(AL.AL_VERSION))
        println(OpenALAudioSystem.devices)
        val player = OpenALAudioSystem.createPlayer()
        val source = player.createSource()
        println(player.device)
        source.setBuffer(AudioBuffer(SeparatedAudioSamples(AudioSamples(44100 * 2) {
            AudioSample(kotlin.math.cos(it.toFloat() / 40f))
        }), rate = 44100))
        source.position = Vector3.ZERO
        source.play()
        source.pitch = 1f
        //var pan = 1.0f
        while (source.isPlaying) {
            //println("STATE: ${source.state}, pos=${source.samplesPosition}")
            //source.volume -= 0.01f
            //source.position -= Vector3(.01f, 0f, 0f)
            NativeThread.sleep(0.01.seconds)
            //source.position = Vector3(pan, 0f, -sqrt(1f - pan*pan))
            //println(source.position)
            //source.gain -= 0.1f
            //pan -= 0.1f
        }
    }
}