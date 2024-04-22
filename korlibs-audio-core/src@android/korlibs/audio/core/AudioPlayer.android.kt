package korlibs.audio.core

actual val defaultAudioSystem: AudioSystem = AndroidAudioSystem

object AndroidAudioSystem : AudioSystem() {
    override fun createPlayer(device: AudioDevice): AudioPlayer = AndroidAudioPlayer(device)

    class AndroidAudioPlayer(val device: AudioDevice) : AudioPlayer(Unit) {
        override fun createSource(): AudioSource = AndroidAudioSource(this)

        class AndroidAudioSource(override val player: AndroidAudioPlayer) : AudioSource() {
        }
    }
}
