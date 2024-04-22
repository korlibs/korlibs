@file:OptIn(ExperimentalStdlibApi::class)

package korlibs.audio.core

import korlibs.math.geom.*

open class AudioDevice(val name: String) {
    companion object
}

val defaultAudioPlayer by lazy { AudioPlayer() }

operator fun AudioPlayer.Companion.invoke(): AudioPlayer = AudioPlayer(AudioDevice.default())
expect fun AudioPlayer(device: AudioDevice): AudioPlayer
expect fun AudioDevice.Companion.default(): AudioDevice
expect fun AudioDevice.Companion.list(): List<AudioDevice>

open class AudioPlayer protected constructor(unit: Unit) : AutoCloseable {
    companion object { }

    open var gain: Float = 1f
    open var speed = Vector3F(0f, 0f, 0f)
    open var position = Vector3F(0f, 0f, 0f)
    open var orientation = Vector3F(0f, 0f, 0f)

    open fun createSource(): AudioSource = AudioSource()

    override fun close() {
    }
}

open class AudioSource : AutoCloseable {
    open var looping: Boolean = false
    open var pitch: Float = 1f
    open var gain: Float = 1f
    open var maxDistance: Float = 1f
    open var rollOffFactor: Float = 1f
    open var coneInnerGain: Float = 1f
    open var coneOuterGain: Float = 1f
    open var referenceDistance: Float = 1f
    open var position: Vector3 = Vector3(0f, 0f, 0f)
    open var velocity: Vector3 = Vector3(0f, 0f, 0f)
    open var direction: Vector3 = Vector3(0f, 0f, 1f)

    open fun play(): Unit { }
    open fun stop(): Unit { }

    override fun close() {
    }
}
