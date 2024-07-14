package korlibs.audio.sound
import korlibs.io.async.*
import korlibs.time.*
import kotlin.test.*

class SoundAudioDataTest {
    @Test
    fun testPlay() = suspendTest {
        val log = LogNativeSoundProvider()
        //val log = nativeSoundProvider

        val audioData = SoundAudioData(coroutineContext, AudioTone.generate(1.seconds, 400.0), log)
        audioData.play(coroutineContext).await()
    }
}
