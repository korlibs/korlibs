package korlibs.audio.core.node

import korlibs.audio.core.*
import korlibs.math.*

class BufferAudioNode(
    val buffer: AudioBuffer,
) : AudioNodeWithCurrentAndDuration {
    override var currentSamples = 0L
    override val rate: Int get() = buffer.rate
    override val durationSamples: Long get() = buffer.nsamples.toLong()

    override fun seek(position: Long) {
        currentSamples = position
    }

    override fun processSize(nsamples: Int): Int = minOf(remainingSamples.toIntClamp(), nsamples)

    override fun process(data: AudioBuffer) {
        val startPos = currentSamples.toIntSafe()
        val copySamples = processSize(data.nsamples)
        buffer.samples.copyInto(data.samples, 0, startPos, startPos + copySamples)
        currentSamples += copySamples
    }
}
