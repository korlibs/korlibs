package korlibs.audio.sound

import korlibs.audio.sound.backend.*
import korlibs.math.geom.*
import kotlinx.atomicfu.locks.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

typealias NewPlatformAudioOutputGen = (AudioSamplesInterleaved) -> Unit

class NewPlatformAudioOutput(
    val coroutineContext: CoroutineContext,
    val channels: Int,
    val frequency: Int,
    private val gen: NewPlatformAudioOutputGen,
    val dispatcher: CoroutineDispatcher = Dispatchers.AUDIO,
    val block: suspend NewPlatformAudioOutput.() -> Unit = {
        val buffer = AudioSamplesInterleaved(channels, 1024)
        while (running) {
            genSafe(buffer)
            delay(1L)
        }
    }
) : AutoCloseable, SoundProps {
    //var onCancel: AutoCloseable? = null
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
        //onCancel = coroutineContext.onCancel { stop() }
        job?.cancel()
        job = CoroutineScope(dispatcher).launch {
            withContext(coroutineContext) {
                block()
            }
        }

    }
    fun stop() {
        if (!running) return
        running = false
        //onCancel?.close()
        //onCancel = null
        job?.cancel()
    }
    final override fun close() = stop()
}
