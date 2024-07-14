package korlibs.audio.core.node

import korlibs.audio.core.*

class GainAudioNode(var scale: Float = 1f) : AudioNode {
    override fun process(data: AudioBuffer) {
        val scale = this.scale
        data.samples.forEachChannel { for (n in 0 until it.size) it[n] = it[n] * scale }
    }
}
