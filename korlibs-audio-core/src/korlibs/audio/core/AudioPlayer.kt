package korlibs.audio.core

import korlibs.audio.core.node.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.coroutines.*

data class AudioDevice(val name: String, val isDefault: Boolean = true, val id: Long = -1L, val extra: Any? = null) {
    companion object { }
}

val defaultAudioPlayer by lazy { AudioPlayer() }

fun AudioPlayer(device: AudioDevice = AudioDevice.default()): AudioPlayer = defaultAudioSystem.createPlayer(device)
fun AudioDevice.Companion.default(): AudioDevice = defaultAudioSystem.defaultDevice
fun AudioDevice.Companion.list(): List<AudioDevice> = defaultAudioSystem.devices

expect val defaultAudioSystem: AudioSystem

abstract class AudioSystem {
    private var initialized = false
    fun initializeOnce(context: CoroutineContext) {
        if (initialized) return
        initialized = true
        initialize(context)
    }
    protected open fun initialize(context: CoroutineContext) {
    }
    abstract fun createPlayer(device: AudioDevice = defaultDevice): AudioPlayer
    val defaultDevice: AudioDevice by lazy { devices.firstOrNull { it.isDefault } ?: devices.firstOrNull() ?: error("Can't find audio devices") }
    open val devices: List<AudioDevice> by lazy { listOf(AudioDevice("default", isDefault = true)) }

    // If decoding is supported
    open fun decodeAudioData(data: ByteArray): AudioBuffer? = TODO()
}

abstract class AudioPlayer protected constructor(unit: Unit = Unit) : AutoCloseable {
    abstract val device: AudioDevice

    companion object { }

    open var listenerGain: Float = 1f
    open var listenerSpeed: Vector3 = Vector3(0f, 0f, 0f)
    open var listenerPosition: Vector3 = Vector3(0f, 0f, 0f)
    open var listenerOrientation: AudioOrientation = AudioOrientation()

    val listener by lazy { AudioListener(this) }

    abstract fun createSource(): AudioSource

    override fun close() {
    }
}

data class AudioOrientation(val at: Vector3 = Vector3(0f, 1f, 0f), val up: Vector3 = Vector3(0f, 0f, -1f)) {
    val forward get() = at
}

class AudioListener(val player: AudioPlayer) {
    var gain: Float by player::listenerGain
    var speed: Vector3 by player::listenerSpeed
    var position by player::listenerPosition
    var orientation by player::listenerOrientation
}

enum class AudioSourceState {
    INITIAL, PLAYING, PAUSED, STOPPED
}


abstract class AudioSource : AutoCloseable {
    abstract val player: AudioPlayer
    val device: AudioDevice get() = player.device

    open var name: String? = null
    open var looping: Boolean = false
    @Deprecated("Only implemented in OpenAL for now")
    open var pitch: Float = 1f
    open var volume: Float = 1f
    open var maxDistance: Float = 1f
    open var rollOffFactor: Float = 1f
    open var coneInnerGain: Float = 0f
    open var coneOuterGain: Float = 0f
    open var referenceDistance: Float = 1f
    open var position: Vector3 = Vector3.ZERO
    open var velocity: Vector3 = Vector3.ZERO
    open var direction: Vector3 = Vector3.RIGHT

    open var rate: Int = 44100
    open var nchannels: Int = 1
    open var samplesPosition: Long = 0L
    open var samplesTotal: Long = -1L
    var buffer: AudioBuffer? = null
        private set
    var node: AudioNode? = null
        private set

    fun samplesToTime(samples: Long): FastDuration = (samples.toDouble() / rate.toDouble()).fastSeconds
    fun timeToSamples(time: FastDuration): Long = (time.seconds * rate).toLong()

    open var state: AudioSourceState = AudioSourceState.INITIAL

    val isPlaying: Boolean get() = state == AudioSourceState.PLAYING
    val isPlayingOrPaused: Boolean get() = state.let { it == AudioSourceState.PLAYING || it == AudioSourceState.PAUSED }

    open fun setBuffer(buffer: AudioBuffer) {
        this.setNode(buffer.nsamples.toLong(), buffer.rate, buffer.nchannels, BufferAudioNode(buffer))
        this.buffer = buffer
    }

    open fun setNode(samplesTotal: Long, rate: Int, nchannels: Int, node: AudioNode) {
        this.rate = rate
        this.nchannels = nchannels
        this.samplesTotal = samplesTotal
        this.node = node
    }

    fun play(position: Long = 0L): Unit {
        samplesPosition = position
        state = AudioSourceState.PLAYING
    }
    fun resume() {
        state = AudioSourceState.PLAYING
    }
    fun pause() {
        state = AudioSourceState.PAUSED
    }
    fun stop() {
        state = AudioSourceState.STOPPED
    }

    protected open fun _play(): Unit { }
    protected open fun _resume(): Unit { }
    protected open fun _pause(): Unit { }
    protected open fun _stop(): Unit { }

    override fun close() {
    }
}
