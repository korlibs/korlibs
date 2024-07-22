package korlibs.audio.sound.node

import korlibs.audio.sound.*

// @TODO: Enable this later
internal class AudioDataNode(
    val data: AudioSamples,
) : AudioNode {
    var pos = 0

    override fun generate(out: AudioData): Int {
        val available = data.totalSamples - pos
        val copy = minOf(available, out.totalSamples)

        for (ch in 0 until out.channels) {
            val samples = out.samples[ch]
            for (n in 0 until copy) {
                samples[n] = data[ch][pos + n]
            }
        }
        pos += copy
        return copy
    }
}
