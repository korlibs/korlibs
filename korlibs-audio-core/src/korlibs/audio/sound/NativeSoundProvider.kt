package korlibs.audio.sound

import korlibs.datastructure.pauseable.*
import korlibs.math.geom.*

expect val nativeSoundProvider: NativeSoundProvider

open class DummyNativeSoundProvider : NativeSoundProvider() {
    companion object : DummyNativeSoundProvider()
}

open class NativeSoundProvider() : AutoCloseable, Pauseable, SoundListenerProps {
    open val target: String = "unknown"

    override var paused: Boolean = false

    override var listenerPosition: Vector3 = Vector3.ZERO
    override var listenerOrientationAt: Vector3 = Vector3.FORWARD // Look vector
    override var listenerOrientationUp: Vector3 = Vector3.UP
    // @TODO: Should this be estimated automatically from position samples?
    override var listenerSpeed: Vector3 = Vector3.ZERO

    @ExperimentalStdlibApi
    open fun createNewPlatformAudioOutput(channels: Int, frequency: Int = 44100, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        //println("createNewPlatformAudioOutput: ${this::class}")
        return AudioPlatformOutput(this, channels, frequency, gen)
    }

    //open fun playData(data: AudioData) {
    //}

    override fun close() {
    }
}
