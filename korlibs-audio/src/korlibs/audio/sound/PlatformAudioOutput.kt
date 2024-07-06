package korlibs.audio.sound

import korlibs.math.geom.*
import korlibs.time.*
import kotlinx.atomicfu.locks.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

open class JobBasedPlatformAudioOutput(
    coroutineContext: CoroutineContext,
    channels: Int,
    frequency: Int,
    val chunkSize: Int = 4 * 1024,
    private val gen: NewPlatformAudioOutput.(AudioSamplesInterleaved) -> Int,
) : NewPlatformAudioOutput(coroutineContext, channels, frequency, gen) {
    private var job: Job? = null

    protected open suspend fun process(buffer: AudioSamplesInterleaved, generated: Int) {
        delay(4.milliseconds)
    }
    protected open fun started(buffer: AudioSamplesInterleaved) = Unit
    protected open fun stopped() = Unit

    final override fun internalStart() {
        job = CoroutineScope(coroutineContext).launch {
            val buffer = AudioSamplesInterleaved(channels, chunkSize)
            started(buffer)
            while (true) {
                process(buffer, genSafe(buffer))
            }
        }
    }

    final override fun internalStop() {
        job?.cancel()
        job = null
        stopped()
    }
}

abstract class NewPlatformAudioOutput(
    val coroutineContext: CoroutineContext,
    val channels: Int,
    val frequency: Int,
    /**
     * Function that will populate the provided [AudioSamplesInterleaved].
     * If completed, the rest should be filled with zeroes.
     * It will return the number of samples generated.
     **/
    private val gen: NewPlatformAudioOutput.(AudioSamplesInterleaved) -> Int,
) : AutoCloseable, SoundProps {
    var paused: Boolean = false

    private val lock = reentrantLock()
    fun genSafe(buffer: AudioSamplesInterleaved): Int {
        return lock.withLock {
            try {
                gen(buffer).also { applyPropsTo(buffer) }
            } catch (e: Throwable) {
                e.printStackTrace()
                0
            }
        }
    }

    override var pitch: Double = 1.0
    override var volume: Double = 1.0
    override var panning: Double = 0.0
    override var position: Vector3 = Vector3.ZERO

    protected abstract fun internalStart()
    protected abstract fun internalStop()

    var playing: Boolean = false
        private set

    fun start() {
        stop()
        playing = true
        internalStart()
    }
    fun stop() {
        playing = false
        internalStop()
    }
    final override fun close() = stop()
}
