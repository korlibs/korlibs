package korlibs.audio.core.impl

import korlibs.audio.core.*

object NullAudioSystem : AudioSystem() {
    override fun createPlayer(device: AudioDevice): AudioPlayer = NullAudioPlayer(device)

    class NullAudioPlayer(override val device: AudioDevice) : AudioPlayer() {
        companion object {
            val DEVICE_DEFAULT: AudioDevice = AudioDevice("default", isDefault = true)
            val DEVICE_ALL: List<AudioDevice> = listOf(DEVICE_DEFAULT)
        }

        class NullAudioSource(override val player: NullAudioPlayer) : AudioSource() {
        }

        override fun createSource(): AudioSource = NullAudioSource(this)
    }
}
