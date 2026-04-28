package korlibs.audio.sound

import korlibs.audio.format.*
import korlibs.datastructure.*
import korlibs.datastructure.pauseable.*
import korlibs.io.async.*
import korlibs.io.file.*
import korlibs.io.lang.*
import korlibs.io.stream.*
import korlibs.math.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.time.*
import kotlin.coroutines.coroutineContext as coroutineContextKt

open class LogNativeSoundProvider(
    val onGen: (AudioData) -> Unit = { }
) : NativeSoundProvider() {
    val log = arrayListOf<AudioData>()

    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(channels: Int, frequency: Int, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        check(channels in 1..8)
        return AudioPlatformOutput(this, channels, frequency, gen) {
            val buffer = AudioSamplesInterleaved(channels, 1024)
            while (running) {
                genSafe(buffer)
                log += AudioData(frequency, buffer.copyOf().separated()).also(onGen)
                delay(2L)
            }
        }
    }
}

class DummySoundChannel(sound: Sound, val data: AudioData? = null) : SoundChannel(sound) {
    private var timeStart = DateTime.now()
    override var current: Duration
        get() = DateTime.now() - timeStart
        set(value) = Unit
    override val total: Duration get() = data?.totalTime ?: 0.seconds

    override fun stop() {
        timeStart = DateTime.now() + total
    }
}

fun SoundProps.copySoundPropsFrom(other: ReadonlySoundProps) {
    this.volume = other.volume
    this.pitch = other.pitch
    this.panning = other.panning
}

fun SoundProps.volumeForChannel(channel: Int): Float {
    return when (channel) {
        0 -> panning.toFloat().convertRangeClamped(-1f, 0f, 0f, 1f)
        else -> 1f - panning.toFloat().convertRangeClamped(0f, 1f, 0f, 1f)
    }
}

fun SoundProps.applyPropsTo(samples: AudioSamplesInterleaved) {
    for (ch in 0 until samples.channels) {
        val volume01 = volumeForChannel(ch) * this.volume.toFloat()
        //println("PROPS: volume=${this.volume}, volumeForChannel(ch=$ch)=$volume01")
        for (n in 0 until samples.totalSamples) {
            var sample = samples[ch, n]
            sample *= volume01
            samples[ch, n] = sample
        }
    }
}

fun SoundProps.applyPropsTo(samples: AudioSamples) {
    for (ch in 0 until samples.channels) {
        val volume01 = volumeForChannel(ch)
        for (n in 0 until samples.totalSamples) {
            var sample = samples[ch, n]
            sample = (sample * volume01)
            samples[ch, n] = sample
        }
    }
}

fun SoundProps.copySoundPropsFromCombined(l: ReadonlySoundProps, r: ReadonlySoundProps) {
    this.volume = l.volume * r.volume
    this.pitch = l.pitch * r.pitch
    //this.panning = l.panning + r.panning
    this.panning = r.panning
}

class SoundChannelGroup(volume: Double = 1.0, pitch: Double = 1.0, panning: Double = 0.0) : SoundChannelBase, SoundChannelPlay, Extra by Extra.Mixin() {
    private val channels = arrayListOf<SoundChannelBase>()

    override val state: SoundChannelState get() = when {
        channels.any { it.playing } -> SoundChannelState.PLAYING
        channels.any { it.paused } -> SoundChannelState.PAUSED
        else -> SoundChannelState.STOPPED
    }

    override var volume: Double = 1.0
        set(value) {
            field = value
            all { it.volume = value }
        }
    override var pitch: Double = 1.0
        set(value) {
            field = value
            all { it.pitch = value }
        }
    override var panning: Double = 0.0
        set(value) {
            field = value
            all { it.panning = value }
        }
    override var position: Vector3 = Vector3.ZERO
        set(value) {
            field = value
            all { it.position = value }
        }

    init {
        this.volume = volume
        this.pitch = pitch
        this.panning = panning
    }

    @Suppress("DEPRECATION")
    fun register(channel: SoundChannelBase, coroutineContext: CoroutineContext) {
        add(channel)
        channel.onCompleted(coroutineContext) { remove(channel) }
    }

    @Deprecated("Use register instead of play")
    fun add(channel: SoundChannelBase) {
        channels.add(channel)
        setProps(channel)
    }
    @Deprecated("Use register instead of play")
    fun remove(channel: SoundChannelBase) {
        channels.remove(channel)
    }

    private fun setProps(channel: SoundChannelBase) {
        channel.volume = this.volume
        channel.pitch = this.pitch
        channel.panning = this.panning
        channel.position = this.position
    }

    @PublishedApi
    internal fun prune() {
        channels.removeAll { !it.playing }
    }

    private inline fun all(callback: (SoundChannelBase) -> Unit) {
        for (channel in channels) callback(channel)
        prune()
    }

    override fun reset() = all { it.reset() }
    override fun stop() = all { it.stop() }
    override fun resume() = all { it.resume() }
    override fun pause() = all { it.pause() }

    override fun play(coroutineContext: CoroutineContext, sound: Sound, params: PlaybackParameters): SoundChannel {
        return sound.play(
            coroutineContext,
            params.copy(
                volume = this.volume * params.volume,
                pitch = this.pitch * params.pitch,
                panning = this.panning * params.panning,
            )
        ).also { register(it, coroutineContext) }
    }
}

enum class SoundChannelState {
    INITIAL, PAUSED, PLAYING, STOPPED;

    val playing get() = this == PLAYING
    val paused get() = this == PAUSED
    val playingOrPaused get() = this == PAUSED || this == PLAYING
}

interface SoundChannelBase : SoundProps, Extra {
    val state: SoundChannelState
    fun reset(): Unit
    fun stop(): Unit
    fun resume(): Unit
    fun pause(): Unit

    fun onCompleted(coroutineContext: CoroutineContext, block: () -> Unit) {
        var blockOnce: (() -> Unit)? = null
        blockOnce = {
            blockOnce = null
            block()
        }

        coroutineContext.onCancel({ playingOrPaused }) {
            blockOnce?.invoke()
        }
        CoroutineScope(coroutineContext).launch {
            try {
                while (state.playing) delay(10.milliseconds)
            } finally {
                blockOnce?.invoke()
            }
        }
    }
}

suspend fun SoundChannelBase.await() {
    while (playingOrPaused) delay(1.milliseconds)
}

val SoundChannelBase.playing: Boolean get() = state.playing
val SoundChannelBase.paused: Boolean get() = state.paused
val SoundChannelBase.playingOrPaused: Boolean get() = state.playingOrPaused

@Deprecated("Use channel.play() instead")
fun <T : SoundChannelBase> T.attachTo(group: SoundChannelGroup): T = this.apply { group.add(this) }

abstract class SoundChannel(val sound: Sound) : SoundChannelBase, Extra by Extra.Mixin() {
    private var startTime = DateTime.now()
    override var volume = 1.0
    override var pitch = 1.0
    override var panning = 0.0 // -1.0 left, +1.0 right
    override var position: Vector3 = Vector3.ZERO

    // @TODO: Rename to position
    open var current: Duration
        get() = DateTime.now() - startTime
        set(value) {
            startTime = DateTime.now() - value
        }
    open val total: Duration get() = sound.length
    override val state: SoundChannelState
        get() = when {
            current < total -> SoundChannelState.PLAYING
            else -> SoundChannelState.STOPPED
        }

    final override fun reset() {
        current = 0.seconds
    }

    abstract override fun stop(): Unit

    override fun pause(): Unit = unsupported()
    override fun resume(): Unit = unsupported()
    fun togglePaused(): Unit = if (paused) resume() else pause()
}

@OptIn(ExperimentalStdlibApi::class)
suspend fun SoundChannel.await(progress: SoundChannel.(current: Duration, total: Duration) -> Unit = { current, total -> }) {
	try {
		while (playingOrPaused) {
			if (!paused) progress(current, total)
			delay(4.milliseconds)
		}
		progress(total, total)
	} catch (e: CancellationException) {
		stop()
	}
}

interface SoundChannelPlay {
    fun play(coroutineContext: CoroutineContext, sound: Sound, params: PlaybackParameters = PlaybackParameters.DEFAULT): SoundChannel
    fun play(coroutineContext: CoroutineContext, sound: Sound, times: PlaybackTimes, startTime: Duration = 0.seconds): SoundChannel = play(coroutineContext, sound, PlaybackParameters(times, startTime))
    fun playForever(coroutineContext: CoroutineContext, sound: Sound, startTime: Duration = 0.seconds): SoundChannel = play(coroutineContext, sound, infinitePlaybackTimes, startTime)
    suspend fun play(sound: Sound, params: PlaybackParameters = PlaybackParameters.DEFAULT): SoundChannel = play(coroutineContextKt, sound, params)
    suspend fun play(sound: Sound, times: PlaybackTimes, startTime: Duration = 0.seconds): SoundChannel = play(coroutineContextKt, sound, times, startTime)
    suspend fun playForever(sound: Sound, startTime: Duration = 0.seconds): SoundChannel = playForever(coroutineContextKt, sound, startTime)
    suspend fun playAndWait(sound: Sound, params: PlaybackParameters, progress: SoundChannel.(current: Duration, total: Duration) -> Unit = { current, total -> }): Unit = play(sound, params).await(progress)
    suspend fun playAndWait(
        sound: Sound,
        times: PlaybackTimes = 1.playbackTimes,
        startTime: Duration = 0.seconds,
        progress: SoundChannel.(current: Duration, total: Duration) -> Unit = { current, total -> }
    ): Unit = play(sound, times, startTime).await(progress)
}

interface SoundPlay {
    fun play(coroutineContext: CoroutineContext, params: PlaybackParameters = PlaybackParameters.DEFAULT): SoundChannel
    fun play(coroutineContext: CoroutineContext, times: PlaybackTimes, startTime: Duration = 0.seconds): SoundChannel = play(coroutineContext, PlaybackParameters(times, startTime))
    fun playForever(coroutineContext: CoroutineContext, startTime: Duration = 0.seconds): SoundChannel = play(coroutineContext, infinitePlaybackTimes, startTime)
    suspend fun play(params: PlaybackParameters = PlaybackParameters.DEFAULT): SoundChannel = play(coroutineContextKt, params)
    suspend fun play(times: PlaybackTimes, startTime: Duration = 0.seconds): SoundChannel = play(coroutineContextKt, times, startTime)
    suspend fun playForever(startTime: Duration = 0.seconds): SoundChannel = playForever(coroutineContextKt, startTime)
    suspend fun playAndWait(params: PlaybackParameters, progress: SoundChannel.(current: Duration, total: Duration) -> Unit = { current, total -> }): Unit = play(params).await(progress)
    suspend fun playAndWait(times: PlaybackTimes = 1.playbackTimes, startTime: Duration = 0.seconds, progress: SoundChannel.(current: Duration, total: Duration) -> Unit = { current, total -> }): Unit = play(times, startTime).await(progress)
}

abstract class Sound(val creationCoroutineContext: CoroutineContext) : SoundProps, SoundPlay, AudioStreamable, Extra by Extra.Mixin() {
    var defaultCoroutineContext = creationCoroutineContext

    open val name: String = "UnknownNativeSound"
    override var volume: Double = 1.0
    override var panning: Double = 0.0
    override var pitch: Double = 1.0
    override var position: Vector3 = Vector3.ZERO
	open val length: Duration = 0.seconds
    open val nchannels: Int get() = 1

    fun playNoCancel(times: PlaybackTimes = PlaybackTimes.ONE, startTime: Duration = 0.seconds): SoundChannel = play(creationCoroutineContext + SupervisorJob(), times, startTime)
    fun playNoCancelForever(startTime: Duration = 0.seconds): SoundChannel = play(creationCoroutineContext + SupervisorJob(), infinitePlaybackTimes, startTime)

    override fun play(coroutineContext: CoroutineContext, params: PlaybackParameters): SoundChannel = TODO()

    abstract suspend fun decode(maxSamples: Int = DEFAULT_MAX_SAMPLES): AudioData
    suspend fun toAudioData(maxSamples: Int = DEFAULT_MAX_SAMPLES): AudioData = decode(maxSamples)
    override suspend fun toStream(): AudioStream = decode().toStream()
    override fun toString(): String = "NativeSound('$name')"
}

suspend fun VfsFile.readSound(props: AudioDecodingProps = AudioDecodingProps.DEFAULT, streaming: Boolean = false): Sound = nativeSoundProvider.createSound(this, streaming, props)
suspend fun ByteArray.readSound(props: AudioDecodingProps = AudioDecodingProps.DEFAULT, streaming: Boolean = false): Sound = nativeSoundProvider.createSound(this, streaming, props)

suspend fun ByteArray.readMusic(props: AudioDecodingProps = AudioDecodingProps.DEFAULT): Sound = readSound(streaming = true, props = props)
suspend fun VfsFile.readMusic(props: AudioDecodingProps = AudioDecodingProps.DEFAULT): Sound = readSound(streaming = true, props = props)
