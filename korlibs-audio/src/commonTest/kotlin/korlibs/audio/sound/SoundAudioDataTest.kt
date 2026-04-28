package korlibs.audio.sound

import korlibs.audio.format.*
import korlibs.io.async.*
import korlibs.io.file.std.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.test.*

class SoundAudioDataTest {
    @Test
    fun testPlayLog() = suspendTest {
        val log = LogNativeSoundProvider()
        val sound = AudioTone.generate(0.5.seconds, 440.0, volume = 1.0).toSound(log)
        sound.play().await()
    }

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
        val sound = resourcesVfs["monkey_drama.mp3"].readAudioData(MP3Decoder).toSound(log)
        val sound2 = AudioTone.generate(1.0.seconds, 440.0, volume = 1.0).toSound(log)
        println("DECODED...")

        val channel1 = sound.play()
        delay(0.5.seconds)
        sound2.play()
        channel1.await()

        repeat(10) {
            delay(0.5.seconds)
            sound2.play(coroutineContext).await()
        }
    }
}
