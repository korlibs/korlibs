package korlibs.audio.sound.node

import korlibs.audio.sound.*
import kotlin.math.*

// @TODO: Enable this later
internal class ToneNode(
    var frequency: Int,
    var volume: Float = 1f,
) : AudioNode {
    private var spos = 0L

    override fun seek(pos: Long) { spos = pos }

    override fun generate(audioData: AudioData): Int {
        val pos = spos.toInt()
        val volumef = 1f
        for (ch in 0 until audioData.channels) {
            val samples = audioData.samples[ch]
            for (n in 0 until audioData.totalSamples) {
                val ratio = ((pos + n).toDouble() * frequency) / audioData.frequency
                val sample = sin(ratio * PI * 2)
                samples[n] = AudioSample(sample.toFloat() * volumef)
            }
        }
        spos += audioData.totalSamples
        return audioData.totalSamples
    }
}