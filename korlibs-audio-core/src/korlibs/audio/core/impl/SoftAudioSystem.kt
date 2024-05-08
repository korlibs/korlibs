@file:OptIn(ExperimentalStdlibApi::class)

package korlibs.audio.core.impl

import korlibs.audio.core.*
import korlibs.datastructure.closeable.*

/**
 * Implements spatial audio with the simplest audio output possible ([AudioStreamPlayer]).
 */
abstract class SoftAudioSystem(val streamPlayer: AudioStreamPlayer) : AudioPlayer() {
    companion object {
        val DEVICE_DEFAULT: AudioDevice = AudioDevice("default", isDefault = true)
        val DEVICE_ALL: List<AudioDevice> = listOf(DEVICE_DEFAULT)
    }

    class SoftAudioSource(override val player: SoftAudioSystem) : AudioSource() {
        var stream: AutoCloseable? = null

        override fun _play() {
            stream?.close()
            stream = player.streamPlayer.playStream(dataRate, nchannels) { _, data ->
                when {
                    this.dataProvider != null -> this.dataProvider!!.invoke(this, samplesPosition, data).also {
                        samplesPosition += it
                    }
                    this.data != null -> {
                        val inDataAll = this.data!!
                        val inPos = samplesPosition.toInt()
                        val inTotal = samplesTotal.toInt()
                        val inAvailable = inTotal - inPos
                        val readCount = minOf(inAvailable, data.size)
                        for (ch in data.indices) {
                            arraycopy(inDataAll[ch % inDataAll.size], inPos, data[ch], 0, readCount)
                        }
                        samplesPosition += readCount
                        readCount
                    }
                    else -> {
                        0
                    }
                }.also {
                    // @TODO: Emulate audio DSP: pitch shifting, doppler, panning, etc.
                }
            }
        }

        override fun _stop() {
            stream?.close()
        }
    }

    override fun createSource(): AudioSource = SoftAudioSource(this)
}
