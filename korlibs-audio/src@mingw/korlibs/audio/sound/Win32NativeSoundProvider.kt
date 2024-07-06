package korlibs.audio.sound

import korlibs.datastructure.*
import korlibs.io.async.*
import korlibs.io.concurrent.*
import korlibs.io.lang.*
import korlibs.logger.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.native.concurrent.*

actual val nativeSoundProvider: NativeSoundProvider = Win32NativeSoundProvider

@ThreadLocal
private val Win32NativeSoundProvider_workerPool = Pool<CoroutineDispatcher> {
    Dispatchers.createSingleThreadedDispatcher("Win32NativeSoundProvider$it")
}

@ThreadLocal
private val Win32NativeSoundProvider_WaveOutProcess = Pool<WaveOutProcess> {
    WaveOutProcess(44100, 2).start(Win32NativeSoundProvider_workerPool.alloc())
}

@OptIn(ExperimentalStdlibApi::class)
object Win32NativeSoundProvider : NativeSoundProvider(), AutoCloseable {

    //val workerPool get() = Win32NativeSoundProvider_workerPool
    val workerPool get() = Win32NativeSoundProvider_WaveOutProcess

    override fun createPlatformAudioOutput(coroutineContext: CoroutineContext, channels: Int, frequency: Int, gen: NewPlatformAudioOutput.(AudioSamplesInterleaved) -> Int): NewPlatformAudioOutput {
        return Win32BasedPlatformAudioOutput(this, coroutineContext, channels, frequency, gen)
    }

    override fun close() {
        while (Win32NativeSoundProvider_workerPool.itemsInPool > 0) {
            Win32NativeSoundProvider_workerPool.alloc().cancel()
        }
    }
}

class Win32BasedPlatformAudioOutput(
    val provider: Win32NativeSoundProvider,
    coroutineContext: CoroutineContext,
    channels: Int,
    frequency: Int,
    gen: NewPlatformAudioOutput.(AudioSamplesInterleaved) -> Int,
) : JobBasedPlatformAudioOutput(coroutineContext, channels, frequency, gen = gen) {
    private var process: WaveOutProcess? = null

    override var pitch: Double = 1.0
        set(value) {
            field = value
            process?.pitch?.value = value
        }
    override var volume: Double = 1.0
        set(value) {
            field = value
            process?.volume?.value = value
        }
    override var panning: Double = 0.0
        set(value) {
            field = value
            process?.panning?.value = value
        }

    override suspend fun process(buffer: AudioSamplesInterleaved, generated: Int) {
        process?.addDataSuspend(buffer.separated(), 0, generated, frequency)
    }

    override fun started(buffer: AudioSamplesInterleaved) {
        process = provider.workerPool.alloc()
            .also { it.reopen(frequency) }
        process!!.volume.value = volume
        process!!.pitch.value = pitch
        process!!.panning.value = panning
    }

    override fun stopped() {
        if (process != null) {
            provider.workerPool.free(process!!)
            process = null
        }
    }
}
