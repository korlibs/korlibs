package korlibs.audio.sound

import korlibs.datastructure.*
import korlibs.time.*
import kotlin.time.*

class AudioData(
    val rate: Int,
    val samples: AudioSamples,
    val name: String? = null,
) : Extra by Extra.Mixin() {
    inline val frequency: Int get() = rate

    companion object {
        val DUMMY by lazy { AudioData(44100, AudioSamples(2, 0)) }
    }

    val stereo: Boolean get() = channels > 1
    val channels: Int get() = samples.channels
    val totalSamples: Int get() = samples.totalSamples
    val totalTime: Duration get() = timeAtSample(totalSamples)
    fun timeAtSample(sample: Int): Duration = ((sample).toDouble() / rate.toDouble()).seconds
    fun sampleAtTime(time: Duration): Int = (time.seconds * rate.toDouble()).toInt()

    operator fun get(channel: Int): AudioSampleArray = samples.data[channel]
    operator fun get(channel: Int, sample: Int): AudioSample = samples.data[channel][sample]

    operator fun set(channel: Int, sample: Int, value: AudioSample) {
        samples.data[channel][sample] = value
    }

    override fun toString(): String = "AudioData(rate=$rate, channels=$channels, samples=$totalSamples)"
}
