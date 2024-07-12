package korlibs.audio.core.impl

import korlibs.audio.core.*

/**
 * Implements spatial audio with the simplest audio output possible ([AudioStreamPlayer]).
 */
open class SoftAudioPlayer(override val device: AudioDevice, val streamPlayer: AudioStreamPlayer) : AudioPlayer() {
    companion object {
        val DEVICE_DEFAULT: AudioDevice = AudioDevice("default", isDefault = true)
        val DEVICE_ALL: List<AudioDevice> = listOf(DEVICE_DEFAULT)
    }

    class SoftAudioSource(override val player: SoftAudioPlayer) : AudioSource() {
        var stream: AutoCloseable? = null

        override fun _play() {
            stream?.close()
            stream = player.streamPlayer.playStream(player.device, dataRate, nchannels) { _, data ->
                when {
                    this.dataProvider != null -> this.dataProvider!!.invoke(this, samplesPosition, data).also {
                        samplesPosition += it
                    }
                    this.data != null -> {
                        val inDataAll = this.data!!
                        val inPos = samplesPosition.toInt()
                        val inTotal = samplesTotal.toInt()
                        val inAvailable = inTotal - inPos
                        val readCount = minOf(inAvailable, data.nsamples)
                        for (ch in 0 until data.nchannels) {
                            arraycopy(inDataAll[ch % inDataAll.nchannels], inPos, data[ch], 0, readCount)
                        }
                        samplesPosition += readCount
                        readCount
                    }
                    else -> {
                        0
                    }
                }.also {
                    val gain = this.gain
                    if (gain != 1f) {
                        data.forEachChannel { chData -> for (n in 0 until chData.size) chData[n] = chData[n] * gain }
                    }
                    // @TODO: Emulate audio DSP: pitch shifting, doppler, panning, etc.
                }
            }
        }

        override fun _stop() {
            stream?.close()
        }
    }

    final override fun createSource(): AudioSource = SoftAudioSource(this)
}
