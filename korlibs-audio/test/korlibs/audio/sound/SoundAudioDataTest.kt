package korlibs.audio.sound
import korlibs.io.async.*
import kotlin.test.*

class SoundAudioDataTest {
    @Test
    fun testPlay() = suspendTest {
        val log = LogNativeSoundProvider()
        val audioData = SoundAudioData(coroutineContext, AudioData(44100, AudioSamples(2, 1024)), log)
        //audioData.play(coroutineContext)//.await()
        //assertEquals(
        //    listOf(
        //        "AddInfo(samples=AudioSamples(channels=2, totalSamples=4096), offset=0, size=1024)"
        //    ),
        //    log.chunks.map { it.toString() }
        //)
    }
}
