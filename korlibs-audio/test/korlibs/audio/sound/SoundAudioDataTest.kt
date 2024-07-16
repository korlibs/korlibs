package korlibs.audio.sound

import korlibs.io.async.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.test.*

class SoundAudioDataTest {
    @Test
    @Ignore
    fun testPlay() = suspendTest {
        //val AUDIO = FixedPoolNativeThreadDispatcher(16, "AUDIO", isDaemon = true)
        //val log = LogNativeSoundProvider()
        val log = nativeSoundProvider

        //val audioData2 = SoundAudioData(coroutineContext, AudioData(44100, AudioSamples(2, 44100)), log)
        //audioData2.play(coroutineContext).await()
        //val data = resourcesVfs["line_ok.mp3"].readAudioData(MP3Decoder)
        println("DECODING...")
        //val data = resourcesVfs["monkey_drama.mp3"].readAudioData(MP3Decoder)
        val data = AudioTone.generate(1.0.seconds, 440.0, volume = 1.0)
        println("DECODED...")

        val audioData = SoundAudioData(coroutineContext, data, log)
        audioData.play(coroutineContext).await()

        repeat(10) {
            delay(0.5.seconds)
            audioData.play(coroutineContext).await()
        }
    }
}
