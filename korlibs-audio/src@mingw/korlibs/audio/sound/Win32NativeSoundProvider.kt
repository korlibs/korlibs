@file:OptIn(ExperimentalForeignApi::class)

package korlibs.audio.sound

import korlibs.concurrent.thread.*
import korlibs.memory.*
import korlibs.time.*
import kotlinx.cinterop.*
import platform.windows.*
import kotlin.coroutines.*
import kotlin.reflect.*

actual val nativeSoundProvider: NativeSoundProvider = Win32WaveOutNativeSoundProvider

object Win32WaveOutNativeSoundProvider : NativeSoundProvider() {
    override fun createNewPlatformAudioOutput(
        coroutineContext: CoroutineContext,
        channels: Int,
        frequency: Int,
        gen: NewPlatformAudioOutputGen
    ): NewPlatformAudioOutput = Win32WaveOutNewPlatformAudioOutput(coroutineContext, channels, frequency, gen)
}

class Win32WaveOutNewPlatformAudioOutput(
    coroutineContext: CoroutineContext,
    nchannels: Int,
    freq: Int,
    gen: NewPlatformAudioOutputGen
) : NewPlatformAudioOutput(coroutineContext, nchannels, freq, gen) {
    var nativeThread: NativeThread? = null
    var running = false

    private var handle: HWAVEOUT? = null
    private var headers = emptyArray<WaveHeader>()

    var positionSamples = 0L

    override fun internalStart() {
        //println("TRYING TO START")
        if (running) return
        //println("STARTED")
        running = true
        nativeThread = NativeThread {
            memScoped {
                val arena = this
                val handlePtr = alloc<HWAVEOUTVar>()
                val freq = frequency
                val blockAlign = (channels * Short.SIZE_BYTES)

                val format = alloc<tWAVEFORMATEX>().also { format ->
                    format.wFormatTag = WAVE_FORMAT_PCM.convert()
                    format.nChannels = channels.convert() // 2?
                    format.nSamplesPerSec = freq.convert()
                    format.wBitsPerSample = Short.SIZE_BITS.convert() // 16
                    format.nBlockAlign = ((channels * Short.SIZE_BYTES).convert())
                    format.nAvgBytesPerSec = (freq * blockAlign).convert()
                    format.cbSize = sizeOf<tWAVEFORMATEX>().convert()
                }
                WINMM.waveOutOpen(handlePtr.ptr, WAVE_MAPPER, format.ptr, 0.convert(), 0.convert(), 0.convert()).also {
                    if (it.toInt() != 0) println("waveOutOpen: $it")
                }
                handle = handlePtr.value
                //println("handle=$handle")

                headers = Array(4) { WaveHeader(it, handle, 1024, channels, arena) }

                try {
                    loop@while (running) {
                        var queued = 0
                        for (header in headers) {
                            if (!header.hdr.isInQueue) {
                                val read = genSafe(header.samples)
                                if (read <= 0) {
                                    break@loop
                                }
                                positionSamples += read
                                header.prepareAndWrite()
                                queued++
                                //println("Sending running=$running, availableRead=$availableRead, header=${header}")
                            }
                        }
                        if (queued == 0) NativeThread.sleep(1.milliseconds)
                    }
                } finally {
                    for (header in headers) header.dispose()
                    //runBlockingNoJs {
                    //    wait()
                    //}
                    WINMM.waveOutReset(handle)
                    WINMM.waveOutClose(handle)
                    handle = null
                    //println("CLOSED")
                }
            }
        }.also {
            it.isDaemon = true
            it.start()
        }
    }

    override fun internalStop() {
        running = false
        //println("STOPPING")
    }
}

private class WaveHeader(
    val id: Int,
    val handle: HWAVEOUT?,
    val totalSamples: Int,
    val channels: Int,
    val arena: MemScope,
) {
    val samples = AudioSamplesInterleaved(channels, totalSamples)

    val totalShorts = (totalSamples * channels)
    val totalBytes = (totalShorts * Short.SIZE_BYTES)
    val dataMem = arena.allocArray<ShortVar>(totalShorts)
    val hdr = arena.alloc<wavehdr_tag>().also { hdr ->
        hdr.lpData = dataMem.reinterpret()
        hdr.dwBufferLength = totalBytes.convert()
        hdr.dwFlags = 0.convert()
    }

    fun prepareAndWrite(totalSamples: Int = this.totalSamples) {
        //println(data[0].toList())

        val channels = this.channels
        hdr.dwBufferLength = (totalSamples * channels * Short.SIZE_BYTES).convert()

        val samplesData = samples.data
        for (n in 0 until channels * totalSamples) {
            dataMem[n] = samplesData[n]
        }
        //if (hdr.isPrepared) dispose()
        if (!hdr.isPrepared) {
            //println("-> prepare")
            WINMM.waveOutPrepareHeader(handle, hdr.ptr, sizeOf<wavehdr_tag>().convert())
        }
        WINMM.waveOutWrite(handle, hdr.ptr, sizeOf<wavehdr_tag>().convert())
    }

    fun dispose() {
        WINMM.waveOutUnprepareHeader(handle, hdr.ptr, sizeOf<wavehdr_tag>().convert())
    }

    override fun toString(): String = "WaveHeader(id=$id, totalSamples=$totalSamples, nchannels=$channels, hdr=$hdr)"
}

val wavehdr_tag.isDone: Boolean get() = dwFlags.toInt().hasFlags(WHDR_DONE)
val wavehdr_tag.isPrepared: Boolean get() = dwFlags.toInt().hasFlags(WHDR_PREPARED)
val wavehdr_tag.isBeginLoop: Boolean get() = dwFlags.toInt().hasFlags(WHDR_BEGINLOOP)
val wavehdr_tag.isEndLoop: Boolean get() = dwFlags.toInt().hasFlags(WHDR_ENDLOOP)
val wavehdr_tag.isInQueue: Boolean get() = dwFlags.toInt().hasFlags(WHDR_INQUEUE)

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