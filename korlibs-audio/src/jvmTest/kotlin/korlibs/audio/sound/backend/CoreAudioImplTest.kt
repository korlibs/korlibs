package korlibs.audio.sound.backend

import korlibs.audio.sound.playAndWait
import korlibs.audio.sound.readSound
import korlibs.audio.sound.toStream
import korlibs.io.file.std.resourcesVfs
import korlibs.time.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class CoreAudioImplTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            println("[1]")
            val sound = resourcesVfs["Snowland.mp3"].readSound().toAudioData()
            println("[2]")
            JVMCoreAudioNativeSoundProvider.playAndWait(sound.toStream())
            println("[3]")
            //CoreFoundation.CFRunLoopRun()
            //CoreAudioImpl2.AudioComponentInstanceNew()
            while (true) {
                delay(0.5.seconds)
            }
        }
    }
}
