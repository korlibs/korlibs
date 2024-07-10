package korlibs.audio.core

import korlibs.math.geom.*

data class AudioDevice(val name: String, val isDefault: Boolean = true, val id: Long = -1L, val extra: Any? = null) {
    companion object { }
}

val defaultAudioPlayer by lazy { AudioPlayer() }

fun AudioPlayer(device: AudioDevice = AudioDevice.default()): AudioPlayer = defaultAudioSystem.createPlayer(device)
fun AudioDevice.Companion.default(): AudioDevice = defaultAudioSystem.defaultDevice
fun AudioDevice.Companion.list(): List<AudioDevice> = defaultAudioSystem.devices

interface AudioStreamPlayer {
    fun playStream(device: AudioDevice, rate: Int, channels: Int, gen: (position: Long, data: Array<AudioSampleArray>) -> Int): AutoCloseable
}

expect val defaultAudioSystem: AudioSystem

abstract class AudioSystem {
    abstract fun createPlayer(device: AudioDevice = defaultDevice): AudioPlayer
    val defaultDevice: AudioDevice by lazy { devices.firstOrNull { it.isDefault } ?: devices.firstOrNull() ?: error("Can't find audio devices") }
    open val devices: List<AudioDevice> by lazy { listOf(AudioDevice("default", isDefault = true)) }
}

abstract class AudioPlayer protected constructor(unit: Unit = Unit) : AutoCloseable {
    abstract val device: AudioDevice

    companion object { }

    open var listenerGain: Float = 1f
    open var listenerSpeed: Vector3F = Vector3F(0f, 0f, 0f)
    open var listenerPosition: Vector3F = Vector3F(0f, 0f, 0f)
    open var listenerOrientation: AudioOrientation = AudioOrientation()

    val listener by lazy { AudioListener(this) }

    abstract fun createSource(): AudioSource

    override fun close() {
    }
}

data class AudioOrientation(val at: Vector3F = Vector3F(0f, 1f, 0f), val up: Vector3F = Vector3F(0f, 0f, -1f)) {
    val forward get() = at
}

class AudioListener(val player: AudioPlayer) {
    var gain: Float by player::listenerGain
    var speed: Vector3F by player::listenerSpeed
    var position by player::listenerPosition
    var orientation by player::listenerOrientation
}

enum class AudioSourceState {
    INITIAL, PLAYING, PAUSED, STOPPED
}

abstract class AudioSource : AutoCloseable {
    abstract val player: AudioPlayer

    open var name: String? = null
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

    open var dataRate: Int = 44100
    open var nchannels: Int = 1
    open var samplesPosition: Long = 0L
    open var samplesTotal: Long = -1L
    open var data: Array<AudioSampleArray>? = null
    open var dataProvider: (AudioSource.(position: Long, chunk: Array<AudioSampleArray>) -> Int)? = null
    open val state: AudioSourceState = AudioSourceState.INITIAL
    val isPlaying: Boolean get() = state == AudioSourceState.PLAYING
    val isPlayingOrPaused: Boolean get() = state.let { it == AudioSourceState.PLAYING || it == AudioSourceState.PAUSED }

    open fun setData(rate: Int, nchannels: Int, data: Array<AudioSampleArray>) {
        this.dataRate = rate
        this.nchannels = nchannels
        this.samplesTotal = data.maxOfOrNull { it.size.toLong() } ?: 0L
        this.data = data
    }

    open fun setProvider(samplesTotal: Long, rate: Int, nchannels: Int, dataProvider: AudioSource.(position: Long, data: Array<AudioSampleArray>) -> Int) {
        this.dataRate = rate
        this.nchannels = nchannels
        this.samplesTotal = samplesTotal
        this.dataProvider = dataProvider
    }

    fun play(position: Long = 0L): Unit {
        samplesPosition = position
        _play()
    }
    fun stop() {
        _stop()
    }

    protected open fun _play(): Unit { }
    protected open fun _stop(): Unit { }

    override fun close() {
    }
}
