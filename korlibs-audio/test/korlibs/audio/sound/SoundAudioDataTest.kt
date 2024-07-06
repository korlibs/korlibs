package korlibs.audio.sound

import korlibs.io.async.*
import kotlin.test.*

class SoundAudioDataTest {
    @Test
    fun testPlay() = suspendTest {
        val audioData = SoundAudioData(
            coroutineContext,
            AudioData(44100, AudioSamples(2, 1000)),
            DummyNativeSoundProvider()
        )

        audioData.play(coroutineContext, PlaybackParameters()).await()
    }
}