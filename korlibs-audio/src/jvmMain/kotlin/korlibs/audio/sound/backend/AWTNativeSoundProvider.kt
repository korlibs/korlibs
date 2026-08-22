package korlibs.audio.sound.backend

import korlibs.audio.sound.*
import korlibs.memory.*
import javax.sound.sampled.*

private val mixer by lazy { AudioSystem.getMixer(null) }

object AWTNativeSoundProvider : NativeSoundProvider() {
    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(
        channels: Int,
        frequency: Int,
        gen: AudioPlatformOutputGen
    ): AudioPlatformOutput = AudioPlatformOutput.simple(this, channels, frequency, gen) { buffer ->
        val format = AudioFormat(frequency.toFloat(), Short.SIZE_BITS, buffer.channels, true, false)
        val line = (mixer.getLine(DataLine.Info(SourceDataLine::class.java, format)) as SourceDataLine)
        val bytes = ByteArray(buffer.totalSamples * buffer.channels * Short.SIZE_BYTES)
        line.open()

        AudioPlatformOutputSimple(
            init = {
                line.start()
            },
            output = {
                bytes.setArrayLE(0, it.data.asShortArray())
                line.write(bytes, 0, bytes.size)
            },
            close = {
                line.drain()
                line.stop()
                line.close()
            },
        )
    }
}
