package korlibs.audio.sound

import korlibs.memory.*

inline class AudioSample(private val raw: Short) {
    companion object {
        val ZERO: AudioSample = AudioSample(0)

        /** In [Short.MIN_VALUE], [Short.MAX_VALUE] range */
        fun fromShort(value: Int): AudioSample = AudioSample(value)
        /** In [-1, 1] range range */
        fun fromFloat(value: Float): AudioSample = AudioSample(value)
    }
    constructor(value: Int) : this(value.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    constructor(value: Float) : this((value * Short.MAX_VALUE.toFloat()).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    /** Sample as short in the range [Short.MIN_VALUE, Short.MAX_VALUE] .. [] */
    val short: Short get() = raw
    val shortInt: Int get() = short.toInt()
    val float: Float get() = (raw.toFloat() / Short.MAX_VALUE)
    //fun toAudioSampleF(): AudioSampleF = AudioSampleF(float)
    fun toShort(): Short = short
    fun toInt(): Int = shortInt
    fun toFloat(): Float = float

    operator fun times(scale: Float): AudioSample = AudioSample((shortInt * scale).toInt())
    operator fun div(scale: Float): AudioSample = AudioSample((shortInt / scale).toInt())
    operator fun times(scale: Double): AudioSample = AudioSample((shortInt * scale).toInt())
    operator fun div(scale: Double): AudioSample = AudioSample((shortInt / scale).toInt())
    // combine
    //operator fun plus(other: AudioSample): AudioSample = AudioSample((this.shortInt + other.shortInt) / 2)
}

/*
class AudioSamples(
    val channels: Int,
    val totalSamples: Int,
    val data: Array<AudioSampleArray> = Array(channels) { AudioSampleArray(totalSamples) }
) {
    init { check(data.all { it.size == totalSamples }) }

    constructor(data: Array<AudioSampleArray>) : this(data.size, data[0].size, data)

    @Deprecated("", ReplaceWith("channels"))
    val nchannels get() = channels
    @Deprecated("", ReplaceWith("totalSamples"))
    val nsamples get() = totalSamples

    operator fun get(channel: Int): AudioSampleArray = data[channel]
    operator fun get(channel: Int, index: Int): AudioSample = data[channel][index]
}
*/

//@Deprecated("")
//typealias PerChannelAudioSamples = Array<AudioSampleArray>
//
//@Deprecated("")
//fun PerChannelAudioSamples(nchannels: Int, nsamples: Int): PerChannelAudioSamples = Array(nchannels) { AudioSampleArray(nsamples) }
//
//val PerChannelAudioSamples.nchannels get() = this.size
//val PerChannelAudioSamples.nsamples get() = this[0].size

inline class AudioSampleArray(private val data: ShortArray) : Collection<AudioSample> {
    constructor(size: Int) : this(ShortArray(size))
    constructor(size: Int, gen: (Int) -> AudioSample) : this(ShortArray(size) { gen(it).short })
    override val size: Int get() = data.size
    override fun isEmpty(): Boolean = data.isEmpty()
    override fun iterator(): Iterator<AudioSample> {
        val shortIterator = data.iterator()
        return object : Iterator<AudioSample> {
            override fun hasNext(): Boolean = shortIterator.hasNext()
            override fun next(): AudioSample = nextSample()
            fun nextSample(): AudioSample = AudioSample(shortIterator.nextShort())
        }
    }

    override fun containsAll(elements: Collection<AudioSample>): Boolean = elements.all { contains(it) }
    override fun contains(element: AudioSample): Boolean = data.contains(element.short)

    //val indices get() = data.indices
    operator fun get(index: Int): AudioSample = AudioSample(data[index])
    operator fun set(index: Int, value: AudioSample) { data[index] = value.short }
    fun asShortArray(): ShortArray = data
    fun toShortArray(out: ShortArray = ShortArray(size)): ShortArray {
        for (n in 0 until size) out[n] = this[n].short
        return out
    }
    fun toFloatArray(out: FloatArray = FloatArray(size)): FloatArray {
        for (n in 0 until size) out[n] = this[n].float
        return out
    }
    fun copyInto(destination: AudioSampleArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = size): AudioSampleArray {
        return AudioSampleArray(data.copyInto(destination.data, destinationOffset, startIndex, endIndex))
    }
    fun copyOf(newSize: Int = this.size): AudioSampleArray = AudioSampleArray(data.copyOf(newSize))
    fun copyOfRange(fromIndex: Int, toIndex: Int): AudioSampleArray = AudioSampleArray(data.copyOfRange(fromIndex, toIndex))
    fun fill(element: AudioSample, fromIndex: Int = 0, toIndex: Int = size) {
        data.fill(element.short, fromIndex, toIndex)
    }
    fun contentEquals(other: AudioSampleArray?): Boolean = data.contentEquals(other?.data)
    fun contentHashCode(): Int = data.contentHashCode()
    fun contentToString(): String = data.contentToString()
    fun toList(): List<AudioSample> = data.map { AudioSample(it) }
}

fun <T : AudioSample> audioSampleArrayOf(vararg values: T): AudioSampleArray = AudioSampleArray(values.size) { values[it] }

//fun PerChannelAudioSamples.interleaved(out: AudioSampleArray = AudioSampleArray(nchannels * nsamples)): AudioSampleArray {
//    val nchannels = this.nchannels
//    val nsamples = this.nsamples
//    check(this.all { it.size == nsamples })
//    for (c in 0 until nchannels) {
//        val data = this[c]
//        for (n in 0 until nsamples)  {
//            out[n * nchannels + c] = data[n]
//        }
//    }
//    return out
//}

public fun arraycopy(src: AudioSampleArray, srcPos: Int, dst: AudioSampleArray, dstPos: Int, size: Int) {
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

fun AudioSampleArray.getSampled(index: Float): AudioSample = AudioSample(asShortArray().getSampled(index))
fun AudioSamples.getSampled(channel: Int, index: Float): AudioSample = getSampledGeneric(index, { this[channel, it] }, { sample, scale -> sample.float * scale }, { AudioSample(it) })
fun AudioSamplesInterleaved.getSampled(channel: Int, index: Float): AudioSample = getSampledGeneric(index, { this[channel, it] }, { sample, scale -> sample.float * scale }, { AudioSample(it) })
