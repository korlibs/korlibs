package korlibs.audio.core.node

import korlibs.audio.core.*
import korlibs.math.*

class GenerateAudioNode(
    override val rate: Int = 44100, // @TODO: resampling
    override val durationSamples: Long = Long.MAX_VALUE,
    val gen: (position: Long, data: AudioBuffer) -> Int
) : AudioNode {
    override var currentSamples: Long = 0L

    override fun processSize(nsamples: Int): Int {
        return minOf((durationSamples - currentSamples).toIntClamp(), nsamples).also {
            //println("processSize=$nsamples, currentSamples=$currentSamples, durationSamples=$durationSamples, $it")
        }
    }

    override fun process(data: AudioBuffer) {
        currentSamples += gen(currentSamples, data)
    }
}