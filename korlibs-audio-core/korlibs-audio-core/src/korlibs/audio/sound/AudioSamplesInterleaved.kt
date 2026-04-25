package korlibs.audio.sound

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