package korlibs.audio.sound.backend

import korlibs.audio.sound.*
import korlibs.concurrent.thread.*
import korlibs.memory.*
import kotlinx.coroutines.*
import javax.sound.sampled.*
import kotlin.coroutines.*

private val mixer by lazy { AudioSystem.getMixer(null) }

object AwtNativeSoundProvider : NativeSoundProvider() {
    override fun createNewPlatformAudioOutput(
        coroutineContext: CoroutineContext,
        nchannels: Int,
        freq: Int,
        gen: NewPlatformAudioOutputGen
    ): NewPlatformAudioOutput = NewPlatformAudioOutput(coroutineContext, nchannels, freq, gen) {
        var nativeThread: NativeThread? = null

        val BYTES_PER_SAMPLE = nchannels * Short.SIZE_BYTES
        fun bytesToSamples(bytes: Int): Int = bytes / BYTES_PER_SAMPLE
        fun samplesToBytes(samples: Int): Int = samples * BYTES_PER_SAMPLE

        val nchannels = this.channels
        val format = AudioFormat(frequency.toFloat(), Short.SIZE_BITS, nchannels, true, false)
        //val format = AudioFormat(44100.toFloat(), Short.SIZE_BITS, nchannels, true, false)
        //val line = AudioSystem.getSourceDataLine(format)
        val line = (mixer.getLine(DataLine.Info(SourceDataLine::class.java, format)) as SourceDataLine)
        line.open()
        line.start()
        try {
            val info = AudioSamplesInterleaved(nchannels, 1024)
            val bytes = ByteArray(samplesToBytes(1024))
            while (running) {
                if (paused) {
                    delay(10L)
                } else {
                    genSafe(info)
                    bytes.setArrayLE(0, info.data)
                    //println(bytes.count { it == 0.toByte() })
                    line.write(bytes, 0, bytes.size)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            line.drain()
            line.stop()
            line.close()
        }
    }
}
