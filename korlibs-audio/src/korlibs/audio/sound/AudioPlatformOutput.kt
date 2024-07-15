package korlibs.audio.sound

import korlibs.audio.sound.backend.*
import korlibs.math.geom.*
import kotlinx.atomicfu.locks.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

typealias AudioPlatformOutputGen = (AudioSamplesInterleaved) -> Unit

class AudioPlatformOutput(
    val coroutineContext: CoroutineContext,
    val channels: Int,
    val frequency: Int,
    private val gen: AudioPlatformOutputGen,
    val dispatcher: CoroutineDispatcher = Dispatchers.AUDIO,
    val block: suspend AudioPlatformOutput.() -> Unit = {
        val buffer = AudioSamplesInterleaved(channels, 1024)
        while (running) {
            genSafe(buffer)
            delay(1L)
        }
    }
) : AutoCloseable, SoundProps {
    var paused: Boolean = false

    private val lock = reentrantLock()
    fun genSafe(buffer: AudioSamplesInterleaved) {
        lock.withLock {
            try {
                gen(buffer)
                applyPropsTo(buffer)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    override var pitch: Double = 1.0
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
        running = true
        job?.cancel()
        job = CoroutineScope(dispatcher).launch {
            try {
                withContext(coroutineContext) {
                    block()
                }
            } catch (e: CancellationException) {
                Unit
            } finally {
                running = false
            }
        }

    }
    fun stop() {
        if (!running) return
        job?.cancel()
        job = null
    }

    final override fun close() = stop()

    companion object {
        fun simple(
            coroutineContext: CoroutineContext,
            nchannels: Int,
            freq: Int,
            gen: AudioPlatformOutputGen,
            dispatcher: CoroutineDispatcher = Dispatchers.AUDIO,
            build: (AudioSamplesInterleaved) -> AudioPlatformOutputSimple,
        ) = AudioPlatformOutput(coroutineContext, nchannels, freq, gen, dispatcher) {
            val samples = AudioSamplesInterleaved(nchannels, 2048)
            val gen = build(samples)
            var init = false
            try {
                while (running) {
                    if (paused) {
                        delay(10L)
                    } else {
                        genSafe(samples)
                        println(samples.data.toList())
                        if (!init) {
                            init = true
                            gen.init(samples)
                        }
                        gen.output(samples)
                        yield()
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
    unit: Unit = Unit
)
