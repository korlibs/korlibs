package korlibs.audio.sound.backend

import korlibs.audio.sound.playAndWait
import korlibs.audio.sound.readMusic
import korlibs.audio.sound.toStream
import korlibs.io.file.std.resourcesVfs
import kotlinx.coroutines.runBlocking

class JvmWaveOutImplTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            val audioData = resourcesVfs["Snowland.mp3"].readMusic().toAudioData()
            FFIJVMWaveOutNativeSoundProvider.playAndWait(audioData.toStream())
        }
    }
}
