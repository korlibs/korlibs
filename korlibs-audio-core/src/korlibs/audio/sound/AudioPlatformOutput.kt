package korlibs.audio.sound

import korlibs.datastructure.*
import korlibs.math.geom.*
import kotlinx.atomicfu.locks.*
import kotlinx.coroutines.*

/** Function might be called from different threads, so code must be thread-safe. */
@ExperimentalStdlibApi
typealias AudioPlatformOutputGen = (AudioSamplesInterleaved) -> Unit

@ExperimentalStdlibApi
class AudioPlatformOutput(
    val listener: SoundListenerProps,
    //val coroutineContext: CoroutineContext,
    val channels: Int,
    val frequency: Int,
    private val gen: AudioPlatformOutputGen,
    val dispatcher: CoroutineDispatcher = Dispatchers.AUDIO,
    val block: suspend AudioPlatformOutput.() -> Unit = {
        val buffer = AudioSamplesInterleaved(channels, DEFAULT_BLOCK_SIZE)
        while (running) {
            genSafe(buffer)
            delay(1L)
        }
    }
) : AutoCloseable, SoundProps, Extra by Extra.Mixin() {
    var paused: Boolean = false

    private val lock = reentrantLock()
    fun genSafe(buffer: AudioSamplesInterleaved) {
        lock.withLock {
            try {
                buffer.data.fill(AudioSample.ZERO)
                gen(buffer)
                applyPropsTo(listener, buffer)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    //var pitch: Double = 1.0
    override var volume: Double = 1.0
    override var panning: Double = 0.0
    override var position: Vector3 = Vector3.ZERO
    var running = false

    suspend fun suspendWhileRunning() {
        while (running) delay(10L)
    }

    private var job: Job? = null

    fun start() {
        if (running) return
        stop()
        stopping = false
        running = true
        job?.cancel()
        job = CoroutineScope(dispatcher + SupervisorJob()).launch {
            try {
                block()
            } catch (e: CancellationException) {
                Unit
            } finally {
                running = false
            }
        }
    }

    private var stopping = false

    fun stop() {
        if (!running && !stopping) return
        stopping = true
        job?.let {
            job = null
            it.cancel()
        }
    }

    final override fun close() = stop()

    companion object {
        val DEFAULT_BLOCK_SIZE = 2048

        fun simple(
            listener: SoundListenerProps,
            //coroutineContext: CoroutineContext,
            nchannels: Int,
            freq: Int,
            gen: AudioPlatformOutputGen,
            dispatcher: CoroutineDispatcher = Dispatchers.AUDIO,
            build: (AudioSamplesInterleaved) -> AudioPlatformOutputSimple,
        ) = AudioPlatformOutput(listener, nchannels, freq, gen, dispatcher) {
            val samples = AudioSamplesInterleaved(nchannels, DEFAULT_BLOCK_SIZE)
            val gen = build(samples)
            var init = false
            var lastPaused: Boolean? = null
            try {
                while (running) {
                    // AudioPlatformOutput.simple.coroutineContext=[PreferSyncIo(preferSyncIo=false), kotlinx.coroutines.UndispatchedMarker@7b82edfa, korlibs.inject.InjectorContext@58
                    // 44a3c8, JobImpl{Cancelled}@1a65eecd, GameWindowCoroutineDispatcher(setNow=setNow, fast=false)]
                    //println("AudioPlatformOutput.simple.coroutineContext=$coroutineContext")
                    if (lastPaused != paused) {
                        lastPaused = paused
                        gen.paused(paused)
                    }
                    if (paused) {
                        delay(10L)
                    } else {
                        genSafe(samples)
                        //println(samples.data.toList())
                        if (!init) {
                            init = true
                            gen.init(samples)
                        }
                        gen.output(samples)
                        delay(1L)
                    }
                }
            } finally {
                gen.close(samples)
            }
        }
    }
}

class AudioPlatformOutputSimple(
    val init: suspend (AudioSamplesInterleaved) -> Unit = { },
    val output: suspend (AudioSamplesInterleaved) -> Unit = { },
    val close: suspend (AudioSamplesInterleaved) -> Unit = { },
    val paused: (paused: Boolean) -> Unit = { },
    unit: Unit = Unit
)
