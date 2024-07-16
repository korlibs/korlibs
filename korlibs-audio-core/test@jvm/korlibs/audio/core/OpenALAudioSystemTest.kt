package korlibs.audio.core

import korlibs.audio.core.impl.*
import korlibs.audio.sound.*
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
        source.setData(44100, 1, arrayOf(AudioSampleArray(44100 * 2) {
            AudioSample(kotlin.math.cos(it.toFloat() / 40f))
        }))
        source.position = Vector3.RIGHT
        source.play()
        //var pan = 1.0f
        while (source.isPlaying) {
            //println("STATE: ${source.state}, pos=${source.samplesPosition}")
            NativeThread.sleep(0.1.seconds)
            //source.position = Vector3(pan, 0f, -sqrt(1f - pan*pan))
            //println(source.position)
            //source.gain -= 0.1f
            //pan -= 0.1f
        }
    }
}