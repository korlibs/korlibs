package korlibs.audio.core

inline class AudioSample(private val raw: Short) {
    constructor(value: Int) : this(value.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    constructor(value: Float) : this((value * Short.MAX_VALUE.toFloat()).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    /** Sample as short in the range [Short.MIN_VALUE, Short.MAX_VALUE] .. [] */
    val short: Short get() = raw
    val shortInt: Int get() = short.toInt()
    val float: Float get() = (raw.toFloat() / Short.MAX_VALUE)
    //fun toAudioSampleF(): AudioSampleF = AudioSampleF(float)

    operator fun times(scale: Float): AudioSample = AudioSample((shortInt * scale).toInt())
    operator fun div(scale: Float): AudioSample = AudioSample((shortInt / scale).toInt())
    // combine
    //operator fun plus(other: AudioSample): AudioSample = AudioSample((this.shortInt + other.shortInt) / 2)
}

class AudioBuffer(val samples: SeparatedAudioSamples, val rate: Int) {
    constructor(nchannels: Int, nsamples: Int, rate: Int) : this(SeparatedAudioSamples(nchannels, nsamples), rate)
    val nsamples get() = samples.nsamples
    val nchannels get() = samples.nchannels
}

inline class SeparatedAudioSamples private constructor(val data: Array<AudioSamples>) {
    constructor(data: Array<AudioSamples>, unit: Unit = Unit) : this(data.also { check(it.all { it.size == data[0].size}) })
    constructor(samples: AudioSamples) : this(arrayOf(samples))
    constructor(samplesL: AudioSamples, samplesR: AudioSamples) : this(arrayOf(samplesL, samplesR), Unit)
    constructor(nchannels: Int, nsamples: Int) : this(Array(nchannels) { AudioSamples(nsamples) })
    val nchannels: Int get() = data.size
    val nsamples: Int get() = data[0].size
    operator fun get(channel: Int): AudioSamples = data[channel]
    operator fun get(channel: Int, sample: Int): AudioSample = data[channel][sample]
    fun copyInto(destination: SeparatedAudioSamples, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = nsamples): SeparatedAudioSamples {
        for (c in 0 until nchannels) data[c].copyInto(destination.data[c], destinationOffset, startIndex, endIndex)
        return destination
    }

    inline fun forEachChannel(block: (AudioSamples) -> Unit) {
        for (n in 0 until nchannels) block(this[n])
    }
}

inline class AudioSamples(private val data: ShortArray) {
    constructor(size: Int) : this(ShortArray(size))
    constructor(size: Int, gen: (Int) -> AudioSample) : this(ShortArray(size) { gen(it).short })
    val size: Int get() = data.size
    operator fun get(index: Int): AudioSample = AudioSample(data[index])
    operator fun set(index: Int, value: AudioSample) { data[index] = value.short }
    fun asShortArray(): ShortArray = data
    fun toFloatArray(out: FloatArray = FloatArray(size)): FloatArray {
        for (n in 0 until size) out[n] = this[n].float
        return out
    }
    fun copyInto(destination: AudioSamples, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = size): AudioSamples {
        return AudioSamples(data.copyInto(destination.data, destinationOffset, startIndex, endIndex))
    }
}

fun SeparatedAudioSamples.interleaved(out: AudioSamples = AudioSamples(nchannels * nsamples)): AudioSamples {
    val nchannels = this.nchannels
    val nsamples = this.nsamples
    for (c in 0 until nchannels) {
        val data = this[c]
        for (n in 0 until nsamples)  {
            out[n * nchannels + c] = data[n]
        }
    }
    return out
}

public fun arraycopy(src: AudioSamples, srcPos: Int, dst: AudioSamples, dstPos: Int, size: Int) {
    src.asShortArray().copyInto(dst.asShortArray(), dstPos, srcPos, srcPos + size)
}


/*
inline class AudioSampleF(private val raw: Float) {
    val float: Float get() = raw
    fun clamped(): AudioSampleF = AudioSampleF(float.clamp01())
    fun toAudioSample(): AudioSample = AudioSample(float)
}

inline class AudioSampleFArray(private val data: FloatArray) {
    constructor(size: Int) : this(FloatArray(size))
    constructor(size: Int, gen: (Int) -> AudioSampleF) : this(FloatArray(size) { gen(it).float })
    val size: Int get() = data.size
    operator fun get(index: Int): AudioSampleF = AudioSampleF(data[index])
    operator fun set(index: Int, value: AudioSampleF) { data[index] = value.float }
    fun asFloatArray(): FloatArray = data
}

public fun arraycopy(src: AudioSampleFArray, srcPos: Int, dst: AudioSampleFArray, dstPos: Int, size: Int) {
    src.asFloatArray().copyInto(dst.asFloatArray(), dstPos, srcPos, srcPos + size)
}
*/
