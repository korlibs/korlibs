package korlibs.audio.core

import korlibs.audio.core.impl.*
import korlibs.audio.core.node.*
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
        //var panning = PannerAudioNode(-1f)

        source.setNode(GenerateAudioNode(durationSamples = 44100) { position, data ->
            for (ch in 0 until data.samples.nchannels) {
                for (n in 0 until data.samples.nsamples) {
                    data.samples[ch][n] = AudioSample(kotlin.math.cos((position + n).toFloat() / 40f))
                }
            }
            data.nsamples
        })
        //source.setBuffer(AudioBuffer(SeparatedAudioSamples(AudioSamples(44100 * 2) {
        //    AudioSample(kotlin.math.cos(it.toFloat() / 40f))
        //}), rate = 44100))
        source.position = Vector3.ZERO
        source.play()
        //source.pitch = 2f
        //var pan = 1.0f
        var n = 0
        while (source.isPlaying) {
            //println("STATE: ${source.state}, pos=${source.samplesPosition}")
            //source.volume -= 0.01f
            source.position -= Vector3(.01f, 0f, 0f)
            //panning.panning += 0.1f
            NativeThread.sleep(0.01.seconds)
            if (n++ >= 100) {
                //source.stop()
            }
            //source.position = Vector3(pan, 0f, -sqrt(1f - pan*pan))
            //println(source.position)
            //source.gain -= 0.1f
            //pan -= 0.1f
        }
    }
}