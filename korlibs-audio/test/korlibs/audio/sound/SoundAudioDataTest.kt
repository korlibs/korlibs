package korlibs.audio.sound
import korlibs.io.async.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.test.*

class SoundAudioDataTest {
    @Test
    @Ignore
    fun testPlay() = suspendTest {
        //val log = LogNativeSoundProvider()
        val log = nativeSoundProvider

        //val audioData2 = SoundAudioData(coroutineContext, AudioData(44100, AudioSamples(2, 44100)), log)
        //audioData2.play(coroutineContext).await()

        run {
            val audioData = SoundAudioData(coroutineContext, AudioTone.generate(0.5.seconds, 1.0, volume = 0.0), log)
            audioData.volume = 0.0
            audioData.play(coroutineContext).await()
        }

        val audioData = SoundAudioData(coroutineContext, AudioTone.generate(1.seconds, 400.0), log)
        audioData.play(coroutineContext).await()

        repeat(10) {
            delay(0.5.seconds)
            audioData.play(coroutineContext).await()
        }
    }
}
