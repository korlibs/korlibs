package korlibs.audio.core

import korlibs.math.*

inline class AudioSample(private val raw: Short) {
    constructor(value: Int) : this(value.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    constructor(value: Float) : this((value * Short.MAX_VALUE.toFloat()).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    /** Sample as short in the range [Short.MIN_VALUE, Short.MAX_VALUE] .. [] */
    val short: Short get() = raw
    val float: Float get() = (raw.toFloat() / Short.MAX_VALUE)
    fun toAudioSampleF(): AudioSampleF = AudioSampleF(float)
}

inline class AudioSampleArray(private val data: ShortArray) {
    constructor(size: Int) : this(ShortArray(size))
    constructor(size: Int, gen: (Int) -> AudioSample) : this(ShortArray(size) { gen(it).short })
    val size: Int get() = data.size
    operator fun get(index: Int): AudioSample = AudioSample(data[index])
    operator fun set(index: Int, value: AudioSample) { data[index] = value.short }
    fun asShortArray(): ShortArray = data
}

fun Array<AudioSampleArray>.combined(): AudioSampleArray = AudioSampleArray(this.sumOf { it.size }).also { out ->
    var outPos = 0
    for (src in this) {
        arraycopy(src, 0, out, outPos, src.size)
        outPos += src.size
    }
}

public fun arraycopy(src: AudioSampleArray, srcPos: Int, dst: AudioSampleArray, dstPos: Int, size: Int) {
    src.asShortArray().copyInto(dst.asShortArray(), dstPos, srcPos, srcPos + size)
}



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
