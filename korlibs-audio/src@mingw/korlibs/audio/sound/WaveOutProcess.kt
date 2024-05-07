@file:OptIn(ExperimentalForeignApi::class)

package korlibs.audio.sound

import korlibs.datastructure.concurrent.*
import korlibs.memory.*
import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.windows.*
import kotlin.reflect.*

internal object WINMM {
    private val LIB = LoadLibraryA("winmm.dll")

    private class func<T : Function<*>> {
        inline operator fun getValue(obj: Any?, property: KProperty<*>): CPointer<CFunction<T>> {
            return GetProcAddress(LIB, property.name)!!.reinterpret()
        }
    }

    val waveOutOpen by func<(phwo: LPHWAVEOUT?, uDeviceID: UINT, pwfx: LPCWAVEFORMATEX?, dwCallback: DWORD_PTR, dwInstance: DWORD_PTR, fdwOpen: DWORD) -> MMRESULT>()
    val waveOutClose by func<(hwo: HWAVEOUT?) -> MMRESULT>()
    val waveOutReset by func<(hwo: HWAVEOUT?) -> MMRESULT>()
    val waveOutPrepareHeader by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: UINT) -> MMRESULT>()
    val waveOutWrite by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: UINT) -> MMRESULT>()
    val waveOutUnprepareHeader by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: UINT) -> MMRESULT>()
}

/*
internal object WINMM : FFILib("winmm.dll") {
    val waveOutOpenExt by func<(phwo: LPHWAVEOUT?, uDeviceID: UINT, pwfx: LPCWAVEFORMATEX?, dwCallback: DWORD_PTR, dwInstance: DWORD_PTR, fdwOpen: DWORD) -> MMRESULT>()
    val waveOutCloseExt by func<(hwo: HWAVEOUT?) -> MMRESULT>()
    val waveOutResetExt by func<(hwo: HWAVEOUT?) -> MMRESULT>()
    val waveOutPrepareHeaderExt by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: UINT) -> MMRESULT>()
    val waveOutWriteExt by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: UINT) -> MMRESULT>()
    val waveOutUnprepareHeaderExt by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: UINT) -> MMRESULT>()
}
*/

private interface WaveOutPart

private object WaveOutEnd : WaveOutPart
private object WaveOutFlush : WaveOutPart

private class WaveOutReopen(val freq: Int) : WaveOutPart {
}

private interface WaveOutDataBase : WaveOutPart {
    fun computeData(volume: Double, panning: Double, pitch: Double): ShortArray
}

//private class WaveOutData(val data: ShortArray) : WaveOutDataBase {
//    override fun computeData(): ShortArray = data
//}

internal class AtomicDouble(value: Double) {
    val atomic = kotlin.concurrent.AtomicLong(value.toRawBits())

    var value: Double
        get() = Double.fromBits(atomic.value)
        set(value) {
            atomic.value = value.toRawBits()
        }
}

internal class WaveOutDataEx(
    val adata: Array<ShortArray>,
    val freq: Int
) : WaveOutDataBase {
    override fun computeData(volume: Double, panning: Double, pitch: Double): ShortArray =
        AudioSamples(2, adata[0].size, Array(2) { adata[it % adata.size] })
            //.resampleIfRequired(freq, 44100)
            .interleaved()
            .applyProps(pitch, panning, volume)
            .data
}
class WaveOutProcess(val freq: Int, val nchannels: Int) {
    private val sPosition = kotlin.concurrent.AtomicLong(0L)
    private val sLength = kotlin.concurrent.AtomicLong(0L)
    private val completed = kotlin.concurrent.AtomicLong(0L)
    internal val volume = AtomicDouble(1.0)
    internal val pitch = AtomicDouble(1.0)
    internal val panning = AtomicDouble(0.0)
    private val numPendingChunks = kotlin.concurrent.AtomicLong(0L)
    private val deque = ConcurrentDeque<WaveOutPart>()
    private val info = kotlin.concurrent.AtomicReference<Job?>(null)

    val position get() = sPosition.value
    val length get() = sLength.value

    //val isCompleted get() = completed.value != 0L
    val pendingAudio get() = numPendingChunks.value != 0L || deque.size > 0
    val pendingCommands get() = deque.size

    //fun addData(data: ShortArray) {
    //    sLength.addAndGet(data.size / nchannels)
    //    deque.add(WaveOutData(data))
    //}

    fun addData(samples: AudioSamples, offset: Int, size: Int, freq: Int) {
        sLength.addAndGet(size.convert())
        deque.add(WaveOutDataEx(Array(samples.channels) { samples.data[it].copyOfRange(offset, offset + size) }, freq))
    }

    fun stop() {
        deque.add(WaveOutEnd)
    }

    fun reopen(freq: Int) {
        sPosition.value = 0L
        sLength.value = 0L
        completed.value = 0L
        deque.add(WaveOutReopen(freq))
    }

    fun stopAndWait() {
        stop()
        runBlocking { info?.value?.join() }
    }

    fun start(_worker: CoroutineDispatcher): WaveOutProcess {
        info.value = CoroutineScope(_worker).launch {
            val info = this@WaveOutProcess
            memScoped {
                val nchannels = info.nchannels // 2
                val hWaveOut = alloc<HWAVEOUTVar>()
                val pendingChunks = ArrayDeque<WaveOutChunk>()

                fun clearCompletedChunks() {
                    while (pendingChunks.isNotEmpty() && pendingChunks.first().completed) {
                        val chunk = pendingChunks.removeFirst()
                        WINMM.waveOutUnprepareHeader(hWaveOut.value, chunk.hdr.ptr, sizeOf<WAVEHDR>().convert())
                        info.sPosition.addAndGet((chunk.data.size / nchannels).toLong())
                        chunk.dispose()
                    }
                }

                fun waveReset() {
                    clearCompletedChunks()
                    while (pendingChunks.isNotEmpty()) {
                        Sleep(5.convert())
                        clearCompletedChunks()
                    }
                    WINMM.waveOutReset(hWaveOut.value)
                    info.sPosition.value = 0L
                }

                fun waveClose() {
                    waveReset()
                    WINMM.waveOutClose(hWaveOut.value)
                }

                var openedFreq = 0

                fun waveOpen(freq: Int) {
                    openedFreq = freq
                    memScoped {
                        val format = alloc<WAVEFORMATEX>().apply {
                            this.wFormatTag = WAVE_FORMAT_PCM.convert()
                            this.nChannels = nchannels.convert() // 2?
                            this.nSamplesPerSec = freq.convert()
                            this.wBitsPerSample = Short.SIZE_BITS.convert() // 16
                            this.nBlockAlign = (info.nchannels * Short.SIZE_BYTES).convert()
                            this.nAvgBytesPerSec = this.nSamplesPerSec * this.nBlockAlign
                            this.cbSize = sizeOf<WAVEFORMATEX>().convert()
                            //this.cbSize = 0.convert()
                        }

                        WINMM.waveOutOpen(hWaveOut.ptr, WAVE_MAPPER, format.ptr, 0.convert(), 0.convert(), CALLBACK_NULL.convert())
                    }
                }

                waveOpen(info.freq)

                try {
                    process@ while (true) {
                        clearCompletedChunks()
                        while (true) {
                            val it = info.deque.consume() ?: break
                            //println("CONSUME: $item")
                            when (it) {
                                is WaveOutReopen -> {
                                    if (it.freq != openedFreq) {
                                        waveClose()
                                        waveOpen(it.freq)
                                    }
                                }

                                is WaveOutEnd -> break@process
                                is WaveOutDataBase -> {
                                    val chunk = WaveOutChunk(it.computeData(info.volume.value, info.panning.value, info.pitch.value))
                                    //info.sLength.addAndGet(chunk.data.size / info.nchannels)
                                    pendingChunks.add(chunk)
                                    WINMM.waveOutPrepareHeader(hWaveOut.value, chunk.hdr.ptr, sizeOf<WAVEHDR>().convert())
                                    WINMM.waveOutWrite(hWaveOut.value, chunk.hdr.ptr, sizeOf<WAVEHDR>().convert())
                                }

                                is WaveOutFlush -> {
                                    waveReset()
                                }
                            }
                        }
                        Sleep(5.convert())
                    }
                } finally {
                    //println("finalizing...")
                    waveClose()
                    info.completed.value = 1L
                }
            }
        }
        return this
    }
}

private class WaveOutChunk(val data: ShortArray) {
    val scope = Arena()
    val dataPin = data.pin()
    val hdr = scope.alloc<WAVEHDR>().apply {
        //println(samplesInterleaved.data.toList())
        this.lpData = dataPin.startAddressOf.reinterpret()
        this.dwBufferLength = (data.size * Short.SIZE_BYTES).convert()
        this.dwFlags = 0.convert()
    }
    val completed: Boolean get() = (hdr.dwFlags.toInt() and WHDR_DONE.toInt()) != 0

    fun dispose() {
        dataPin.unpin()
        scope.clear()
    }
}