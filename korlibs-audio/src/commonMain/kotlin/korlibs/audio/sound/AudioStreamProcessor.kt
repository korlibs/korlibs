package korlibs.audio.sound

import kotlin.time.*

@Deprecated("Use nodes")
fun AudioStream.withProcessor(block: suspend (inp: AudioStream, outp: AudioSamplesDeque) -> Unit): AudioStream {
    val inp = this
    return object : AudioStream(inp.rate, inp.channels) {
        override val finished: Boolean get() = inp.finished
        override val totalLengthInSamples: Long? get() = inp.totalLengthInSamples
        override val currentPositionInSamples: Long get() = inp.currentPositionInSamples

        val buffer = AudioSamplesDeque(inp.channels)

        override suspend fun seek(position: Duration) {
            inp.seek(position)
        }

        override suspend fun read(out: AudioSamples, offset: Int, length: Int): Int {
            if (buffer.availableRead < 1) {
                block(inp, buffer)
            }
            return buffer.read(out, offset, length)
        }

        override fun close() {
            inp.close()
        }

        override suspend fun clone(): AudioStream = this
    }
}

@Deprecated("Use nodes")
suspend fun AudioStreamable.withProcessor(block: suspend (inp: AudioStream, outp: AudioSamplesDeque) -> Unit): Sound =
    this.toStream().withProcessor(block).toSound()
