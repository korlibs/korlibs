package korlibs.audio.sound

import korlibs.datastructure.ShortArrayDeque
import kotlin.math.min

class AudioSamplesDeque(val channels: Int) {
    companion object {
        operator fun invoke(samples: AudioSamples): AudioSamplesDeque = AudioSamplesDeque(samples.channels).also { it.write(samples) }
        operator fun invoke(samples: AudioSamplesInterleaved): AudioSamplesDeque = AudioSamplesDeque(samples.channels).also { it.write(samples) }
    }

    val buffer = Array(channels) { ShortArrayDeque() }
    val availableRead get() = buffer.getOrNull(0)?.availableRead ?: 0
    val availableReadMax: Int get() = buffer.maxOfOrNull { it.availableRead } ?: 0

    fun createTempSamples(size: Int = 1024) = AudioSamples(channels, size)

    fun copyTo(out: AudioSamplesDeque, temp: AudioSamples = createTempSamples()) {
        while (true) {
            val read = read(temp)
            if (read <= 0) return
            out.write(temp, 0, read)
        }
    }

    // Individual samples
    fun read(channel: Int): AudioSample = AudioSample(buffer[channel].readOne())
    fun write(channel: Int, sample: AudioSample) { buffer[channel].writeOne(sample.short) }

    fun readFloat(channel: Int): Float = read(channel).float
    fun writeFloat(channel: Int, sample: Float) = write(channel, AudioSample(sample))

    // Write samples
    fun write(samples: AudioSamples, offset: Int = 0, len: Int = samples.totalSamples - offset) {
        for (channel in 0 until this.channels) write(channel, samples[channel % samples.channels], offset, len)
    }

    fun write(samples: AudioSamplesInterleaved, offset: Int = 0, len: Int = samples.totalSamples - offset) {
        writeInterleaved(samples.data, offset, len, samples.channels)
    }

    // Write raw
    fun write(channel: Int, data: AudioSampleArray, offset: Int = 0, len: Int = data.size - offset) {
        buffer[channel].write(data.asShortArray(), offset, len)
    }

    fun write(channel: Int, data: FloatArray, offset: Int = 0, len: Int = data.size - offset) {
        for (n in 0 until len) write(channel, AudioSample(data[offset + n]))
    }

    fun writeInterleaved(data: AudioSampleArray, offset: Int, len: Int = data.size - offset, channels: Int = this.channels) {
        when (channels) {
            1 -> {
                for (n in 0 until this.channels) buffer[n].write(data.asShortArray(), offset, len)
            }
            2 -> {
                for (n in 0 until len / 2) write(0, data[n * 2 + 0])
                for (n in 0 until len / 2) write(1, data[n * 2 + 1])
            }
            else -> {
                for (c in 0 until channels) for (n in 0 until len / channels) write(c, data[n * channels + c])
            }
        }
    }

    fun read(out: AudioSamples, offset: Int = 0, len: Int = out.totalSamples - offset): Int {
        val rlen = min(len, availableRead)
        for (channel in 0 until out.channels) this.buffer[channel % this.channels].read(out[channel].asShortArray(), offset, rlen)
        return rlen
    }

    fun read(out: AudioSamplesInterleaved, offset: Int = 0, len: Int = out.totalSamples - offset): Int {
        val rlen = min(len, availableRead)
        val channels = this.channels
        for (channel in 0 until out.channels) {
            val inChannel = channel % channels
            for (n in 0 until rlen) out[channel, offset + n] = this.read(inChannel)
        }
        return rlen
    }

    fun clear() {
        for (c in buffer.indices) buffer[c].clear()
    }

    fun clone(): AudioSamplesDeque {
        return AudioSamplesDeque(channels).also {
            for (n in buffer.indices) it.buffer[n] = buffer[n].clone()
        }
    }
    override fun toString(): String = "AudioSamplesDeque(channels=$channels, availableRead=$availableRead)"
}

// @TODO: Cloning...
fun AudioSamplesDeque.consumeToData(rate: Int): AudioData {
    val samples = AudioSamples(channels, availableRead)
    read(samples)
    return AudioData(rate, samples)
}

fun AudioSamplesDeque.toData(rate: Int): AudioData = clone().consumeToData(rate)
