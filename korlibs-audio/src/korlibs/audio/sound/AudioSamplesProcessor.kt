package korlibs.audio.sound

import kotlin.math.*

@Deprecated("")
class AudioSamplesProcessor(val channels: Int, val totalSamples: Int, val data: Array<FloatArray> = Array(channels) { FloatArray(totalSamples) })  {
    fun reset(): AudioSamplesProcessor {
        for (ch in 0 until channels) data[ch].fill(0f)
        return this
    }
    fun add(samples: AudioSamples, scale: Float = 1f): AudioSamplesProcessor {
        for (ch in 0 until min(channels, samples.channels)) {
            val odata = this.data[ch]
            val idata = samples.data[ch]
            for (n in 0 until samples.totalSamples) {
                odata[n] += idata[n].float * scale
            }
        }
        return this
    }
    fun normalize(): AudioSamplesProcessor {
        for (ch in 0 until channels) {
            val odata = this.data[ch]
            var maxAbs = 0f
            for (n in 0 until totalSamples) {
                maxAbs = max(maxAbs, odata[n].absoluteValue)
            }
            if (maxAbs > 1f) {
                val invMaxAbs = 1f / maxAbs
                for (n in 0 until totalSamples) {
                    odata[n] *= invMaxAbs
                }
            }
        }
        return this
    }
    fun copyTo(samples: AudioSamples) {
        for (ch in 0 until min(channels, samples.channels)) {
            val idata = this.data[ch]
            val odata = samples.data[ch]
            for (n in 0 until samples.totalSamples) {
                odata[n] = AudioSample(idata[n])
            }
        }
    }
}