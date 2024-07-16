package korlibs.audio.sound

import kotlin.math.*

class AudioSamples(
    val channels: Int,
    val totalSamples: Int,
    val data: Array<AudioSampleArray> = Array(channels) { AudioSampleArray(totalSamples) }
) {
    operator fun get(channel: Int): AudioSampleArray = data[channel]

    operator fun get(channel: Int, sample: Int): AudioSample = data[channel][sample]
    operator fun set(channel: Int, sample: Int, value: AudioSample) { data[channel][sample] = value }

    fun copyOf(size: Int = totalSamples): AudioSamples = copyOfRange(0, size)
    fun copyOfRange(fromIndex: Int, toIndex: Int): AudioSamples = AudioSamples(channels, toIndex - fromIndex, Array(data.size) { data[it].copyOfRange(fromIndex * channels, toIndex * channels) })

    fun setStereo(sample: Int, valueLeft: AudioSample, valueRight: AudioSample) {
        this[0, sample] = valueLeft
        this[1, sample] = valueRight
    }

    fun scaleVolume(scale: Float): AudioSamples {
        for (channel in data) {
            for (n in 0 until channel.size) {
                channel[n] = channel[n] * scale
            }
        }
        return this
    }
    fun scaleVolume(channelScales: FloatArray): AudioSamples {
        for (ch in data.indices) {
            val channel = data[ch]
            for (n in 0 until channel.size) {
                channel[n] = channel[n] * channelScales[ch]
            }
        }
        return this
    }

    fun setTo(that: AudioSamples): AudioSamples {
        that.copyTo(this)
        return this
    }

    fun copyTo(that: AudioSamples) {
        for (ch in 0 until min(channels, that.channels)) {
            arraycopy(this.data[ch], 0, that.data[ch], 0, min(totalSamples, that.totalSamples))
        }
    }

    fun clone(out: AudioSamples = AudioSamples(channels, totalSamples, Array(data.size) { AudioSampleArray(data[0].size) })) : AudioSamples {
        this.copyTo(out)
        return out
    }

    override fun hashCode(): Int = channels + totalSamples * 32 + data.contentDeepHashCode() * 64
    override fun equals(other: Any?): Boolean = (other is AudioSamples) && this.channels == other.channels && this.totalSamples == other.totalSamples && this.data.contentDeepEquals(other.data)

    override fun toString(): String = "AudioSamples(channels=$channels, totalSamples=$totalSamples)"
}