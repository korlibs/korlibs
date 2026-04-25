package korlibs.audio.sound.backend

import korlibs.audio.sound.*
import korlibs.io.file.std.*
import kotlinx.coroutines.*

class JvmWaveOutImplTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            val audioData = resourcesVfs["Snowland.mp3"].readMusic().toAudioData()
            FFIJVMWaveOutNativeSoundProvider!!.playAndWait(audioData.toStream())
        }
    }
}
