package korlibs.audio.core

inline class AudioSample(private val raw: Short) {
    constructor(value: Int) : this(value.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    constructor(value: Float) : this((value * Short.MAX_VALUE.toFloat()).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort())
    /** Sample as short in the range [Short.MIN_VALUE, Short.MAX_VALUE] .. [] */
    val short: Short get() = raw
    val float: Float get() = (raw.toFloat() / Short.MAX_VALUE)
}

inline class AudioSampleArray(private val data: ShortArray) {
    constructor(size: Int) : this(ShortArray(size))
    constructor(size: Int, gen: (Int) -> AudioSample) : this(ShortArray(size) { gen(it).short })
    val size: Int get() = data.size
    operator fun get(index: Int): AudioSample = AudioSample(data[index])
    operator fun set(index: Int, value: AudioSample) { data[index] = value.short }
    fun asShortArray(): ShortArray = data
}

public fun arraycopy(src: AudioSampleArray, srcPos: Int, dst: AudioSampleArray, dstPos: Int, size: Int) {
    src.asShortArray().copyInto(dst.asShortArray(), dstPos, srcPos, srcPos + size)
}
