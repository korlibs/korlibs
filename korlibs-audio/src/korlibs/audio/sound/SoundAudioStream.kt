package korlibs.audio.sound

import korlibs.datastructure.*
import korlibs.io.concurrent.*
import korlibs.platform.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.*

@OptIn(ExperimentalCoroutinesApi::class)
class SoundAudioStream(
    coroutineContext: CoroutineContext,
    val stream: AudioStream,
    var soundProvider: NativeSoundProvider,
    val closeStream: Boolean = false,
    override val name: String = "Unknown",
    val onComplete: (suspend () -> Unit)? = null
) : Sound(coroutineContext) {
    val nativeSound = this
    override val length: Duration get() = stream.totalLength
    override suspend fun decode(maxSamples: Int): AudioData = stream.toData(maxSamples)
    override suspend fun toStream(): AudioStream = stream.clone()
    override val nchannels: Int get() = stream.channels

    companion object {
        private val ID_POOL = ConcurrentPool<Int> { it }
    }

    override fun play(coroutineContext: CoroutineContext, params: PlaybackParameters): SoundChannel {
        val deque = AudioSamplesDeque(nchannels)
        val nas = soundProvider.createNewPlatformAudioOutput(coroutineContext, nchannels, stream.rate) {
            it.data.fill(0)
            deque.read(it)
        }
        nas.copySoundPropsFromCombined(params, this)
        var playing = true
        var paused = false
        var newStream: AudioStream? = null
        val channelId = ID_POOL.alloc()
        val dispatcherName = "SoundChannel-SoundAudioStream-$channelId"
        //println("dispatcher[a]=$dispatcher, thread=${currentThreadName}:${currentThreadId}")
        val job = CoroutineScope(coroutineContext).launch {
            val dispatcher = when {
                //Platform.runtime.isNative -> Dispatchers.createRedirectedDispatcher(dispatcherName, coroutineContext[CoroutineDispatcher.Key] ?: Dispatchers.Default)
                Platform.runtime.isNative -> null // @TODO: In MacOS audio is not working. Check why.
                else -> Dispatchers.createSingleThreadedDispatcher(dispatcherName)
            }
            try {
                withContext(dispatcher ?: EmptyCoroutineContext) {
                    //println("dispatcher[b]=$dispatcher, thread=${currentThreadName}:${currentThreadId}")
                    val stream = stream.clone()
                    newStream = stream
                    stream.currentTime = params.startTime
                    playing = true
                    //println("STREAM.START")
                    var times = params.times
                    try {
                        val temp = AudioSamples(stream.channels, 1024)
                        val minBuf = (stream.rate * params.bufferTime.seconds).toInt()
                        var started = false
                        while (times.hasMore) {
                            stream.currentPositionInSamples = 0L
                            while (true) {
                                //println("STREAM")
                                while (paused) {
                                    delay(2.milliseconds)
                                    //println("PAUSED")
                                }
                                val read = stream.read(temp, 0, temp.totalSamples)
                                deque.write(temp, 0, read)
                                if (stream.finished || deque.availableRead >= minBuf) {
                                    if (!started) {
                                        started = true
                                        nas.start()
                                    } else if (deque.availableRead >= minBuf * 2) {
                                        delay(1.milliseconds)
                                    }
                                }
                                if (stream.finished) break
                            }
                            times = times.oneLess
                        }
                    } catch (e: CancellationException) {
                        // Do nothing
                        nas.stop()
                        params.onCancel?.invoke()
                    } finally {
                        while (deque.availableRead > 0) delay(1L)
                        nas.stop()
                        if (closeStream) {
                            stream.close()
                        }
                        playing = false
                        params.onFinish?.invoke()
                        onComplete?.invoke()
                    }
                }
            } finally {
                ID_POOL.free(channelId)
                when (dispatcher) {
                    is CloseableCoroutineDispatcher -> dispatcher.close()
                    is AutoCloseable -> dispatcher.close()
                }
            }
        }

        fun close() {
            job.cancel()
        }
        return object : SoundChannel(nativeSound) {
            override var volume: Double by nas::volume
            override var pitch: Double by nas::pitch
            override var panning: Double by nas::panning
            override var current: Duration
                get() = newStream?.currentTime ?: 0.milliseconds
                set(value) {
                    newStream?.currentTime = value
                }
            override val total: Duration get() = newStream?.totalLength ?: stream.totalLength
            override val state: SoundChannelState
                get() = when {
                    paused -> SoundChannelState.PAUSED
                    playing -> SoundChannelState.PLAYING
                    else -> SoundChannelState.STOPPED
                }

            override fun pause() {
                paused = true
            }

            override fun resume() {
                paused = false
            }

            override fun stop() = close()
        }
    }
}
