package korlibs.audio.core.node

import korlibs.audio.core.*
import korlibs.math.*

/**
 * Panner Audio Node
 *
 * [panning] should have values between [-1f, +1f]
 */
class PannerAudioNode(var panning: Float = 0f) : AudioNode {
    private val scales = FloatArray(2)

    override fun process(data: AudioBuffer) {
        val panning01 = ((panning + 1f) / 2f).clamp01()
        scales[0] = 1f - panning01
        scales[1] = panning01
        for (ch in 0 until data.nchannels) {
            val scale = scales[ch % 2]
            val it = data.samples[ch]
            for (n in 0 until it.size) it[n] = it[n] * scale
        }
    }
}
