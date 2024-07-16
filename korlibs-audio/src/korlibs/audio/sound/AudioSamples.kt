package korlibs.audio.sound

import korlibs.math.*
import kotlin.math.*

//inline class AudioAmplitude(val value: Short)

internal fun AudioSamples.resample(scale: Double, totalSamples: Int = (this.totalSamples * scale).toInt(), out: AudioSamples = AudioSamples(channels, totalSamples)): AudioSamples {
    val iscale = 1.0 / scale
    for (c in 0 until channels) {
        val inpc = this[c]
        val outc = out[c]
        for (n in 0 until totalSamples) {
            // @TODO: Increase quality
            outc[n] = inpc[(n * iscale).toInt()]
        }
    }
    return out
}

fun AudioSamples.resample(srcFreq: Int, dstFreq: Int): AudioSamples =
    resample(dstFreq.toDouble() / srcFreq.toDouble())

fun AudioSamples.resampleIfRequired(srcFreq: Int, dstFreq: Int): AudioSamples =
    if (srcFreq == dstFreq) this else resample(srcFreq, dstFreq)

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

class AudioSamplesInterleaved(
    val channels: Int,
    val totalSamples: Int,
    val data: AudioSampleArray = AudioSampleArray(totalSamples * channels),
) {
    init {
        check(channels in 1..8)
    }

    //val separared by lazy { separated() }
    private fun index(channel: Int, sample: Int): Int = (sample * channels) + channel
    operator fun get(channel: Int, sample: Int): AudioSample = data[index(channel, sample)]
    operator fun set(channel: Int, sample: Int, value: AudioSample) { data[index(channel, sample)] = value }

    fun copyOf(size: Int = totalSamples): AudioSamplesInterleaved = copyOfRange(0, size)
    fun copyOfRange(fromIndex: Int, toIndex: Int): AudioSamplesInterleaved = AudioSamplesInterleaved(channels, toIndex - fromIndex, data.copyOfRange(fromIndex * channels, toIndex * channels))

    fun scaleVolume(scale: Float): AudioSamplesInterleaved {
        for (n in 0 until data.size) data[n] = data[n] * scale
        return this
    }
    fun scaleVolume(channelScales: FloatArray): AudioSamplesInterleaved {
        for (ch in 0 until channels) {
            val chVolume = channelScales[ch]
            for (n in 0 until totalSamples) {
                val i = n * channels + ch
                data[i] = data[i] * chVolume
            }
        }
        return this
    }

    override fun toString(): String = "AudioSamplesInterleaved(channels=$channels, totalSamples=$totalSamples)"
}

fun AudioSamples.copyOfRange(start: Int, end: Int): AudioSamples {
    val out = AudioSamples(channels, end - start)
    for (n in 0 until channels) {
        arraycopy(this[n], start, out[n], 0, end - start)
    }
    return out
}

fun AudioSamples.interleaved(out: AudioSamplesInterleaved = AudioSamplesInterleaved(channels, totalSamples)): AudioSamplesInterleaved {
    check(out.data.size >= totalSamples * channels)
    when (channels) {
        1 -> arraycopy(this.data[0], 0, out.data, 0, totalSamples)
        2 -> arrayinterleave(
            out.data, 0,
            this.data[0], 0,
            this.data[1], 0,
            totalSamples,
        )
        else -> {
            val outData = out.data
            val channels = channels
            for (c in 0 until channels) {
                var m = c
                for (n in 0 until totalSamples) {
                    outData[m] = this[c, n]
                    m += channels
                }
            }
        }
    }
    return out
}

fun AudioSamplesInterleaved.applyProps(speed: Double, panning: Double, volume: Double): AudioSamplesInterleaved {
    if (speed == 1.0 && panning == 0.0 && volume == 1.0) return this
    val speedf = speed.toFloat()
    val ispeedf = (1.0 / speed).toFloat()
    val out = AudioSamplesInterleaved(channels, (totalSamples * ispeedf).toInt())

    val rratio = ((((panning + 1.0) / 2.0).clamp01()) * volume).toFloat()
    val lratio = ((1.0 - rratio) * volume).toFloat()

    val outData = out.data
    var m = 0
    if (channels == 2) {
        for (n in 0 until out.totalSamples) {
            outData[m++] = outData[(n * speedf).toInt() * 2 + 0] * lratio
            outData[m++] = outData[(n * speedf).toInt() * 2 + 1] * rratio
        }
    } else {
        for (n in 0 until out.data.size) {
            outData[m++] = outData[(n * speedf).toInt()] * lratio
        }
    }

    return out
}

fun AudioSamplesInterleaved.ensureTwoChannels(): AudioSamplesInterleaved {
    return when (channels) {
        2 -> this
        else -> {
            AudioSamplesInterleaved(2, this.totalSamples).also { out ->
                val inp = this@ensureTwoChannels
                var m = 0
                val ichannels = inp.channels
                val odata = out.data
                val idata = inp.data
                for (n in 0 until out.totalSamples) {
                    val v = idata[n * ichannels]
                    odata[m++] = v
                    odata[m++] = v
                }
            }
        }
    }
}

fun AudioSamplesInterleaved.separated(out: AudioSamples = AudioSamples(channels, totalSamples)): AudioSamples {
    for (n in 0 until totalSamples) for (c in 0 until channels) out[c, n] = this[c, n]
    return out
}

private fun arraycopy(src: AudioSampleArray, srcPos: Int, dst: AudioSampleArray, dstPos: Int, size: Int) {
    src.copyInto(dst, dstPos, srcPos, srcPos + size)
}

private fun arrayinterleave(
    out: AudioSampleArray, outPos: Int,
    array1: AudioSampleArray, array1Pos: Int,
    array2: AudioSampleArray, array2Pos: Int,
    size: Int,
) {
    var m = outPos
    for (n in 0 until size) {
        out[m++] = array1[array1Pos + n]
        out[m++] = array2[array2Pos + n]
    }
}
