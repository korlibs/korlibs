package korlibs.audio.sound

import doIOTest
import korlibs.concurrent.thread.*
import korlibs.io.async.*
import korlibs.io.file.std.*
import korlibs.io.lang.*
import korlibs.logger.*
import korlibs.platform.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.test.*

class SoundAudioStreamTest {
    val logger = Logger("SoundAudioStreamTest")

    @Test
    fun testPlaySeveralTimes() = suspendTest({ doIOTest }) {
        if (Platform.isWasm) { // !! WASM skipping SoundAudioStreamTest.testPlaySeveralTimes
            //println("!! WASM skipping SoundAudioStreamTest.testPlaySeveralTimes")
            return@suspendTest
        }

        val soundProvider = LogNativeSoundProvider()

        val sound = soundProvider.createSound(resourcesVfs["click.mp3"], streaming = true)
        val data = sound.toAudioData()
        sound.playAndWait(2.playbackTimes)
        //assertEquals(2, soundProvider.log.size)
        val dataOut = soundProvider.log[0]
        val dataOut2 = dataOut.toSound().toAudioData()

        //WAV.encodeToByteArray(dataOut).writeToFile("/tmp/demo.wav")
        //dataOut.toSound().toData().toSound().toData().toSound().toData().toSound().playAndWait()

        assertEquals("468/1", "${data.totalSamples}/${data.channels}")
        //assertEquals("936/2", "${stream.data.availableRead}/${stream.data.channels}")
        //assertEquals("936/2", "${dataOut.totalSamples}/${dataOut.channels}")
        //assertEquals("936/2", "${dataOut2.totalSamples}/${dataOut2.channels}")
    }

    @Test
    fun testChannelCurrentLength() = suspendTest({ doIOTest }) {
        val soundProvider = LogNativeSoundProvider()
        for (fileName in listOf("click.wav", "click.mp3")) {
            val sound2 = soundProvider.createSound(resourcesVfs[fileName], streaming = true)
            logger.debug { "currentThreadId:${NativeThread.currentThreadId}" }
            val channel = sound2.play()
            assertEquals("0s/58.5ms", "${channel.current}/${channel.total}")
            channel.await()
            assertEquals("58.5ms/58.5ms", "${channel.current}/${channel.total}")
        }
    }
}
