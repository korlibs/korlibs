@file:OptIn(ExperimentalForeignApi::class)

package korlibs.audio.sound

import korlibs.memory.hasFlags
import kotlin.reflect.KProperty
import kotlinx.cinterop.Arena
import kotlinx.cinterop.ArenaBase
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ShortVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.value
import kotlinx.coroutines.delay
import platform.windows.DWORD
import platform.windows.DWORD_PTR
import platform.windows.GetProcAddress
import platform.windows.HWAVEOUT
import platform.windows.HWAVEOUTVar
import platform.windows.LPCWAVEFORMATEX
import platform.windows.LPHWAVEOUT
import platform.windows.LPWAVEHDR
import platform.windows.LoadLibraryA
import platform.windows.MMRESULT
import platform.windows.UINT
import platform.windows.WAVEFORMATEX
import platform.windows.WAVEHDR
import platform.windows.WAVE_FORMAT_PCM
import platform.windows.WAVE_MAPPER
import platform.windows.WHDR_BEGINLOOP
import platform.windows.WHDR_DONE
import platform.windows.WHDR_ENDLOOP
import platform.windows.WHDR_INQUEUE
import platform.windows.WHDR_PREPARED
import platform.windows.wavehdr_tag

actual val nativeSoundProvider: NativeSoundProvider = Win32WaveOutNativeSoundProvider

object Win32WaveOutNativeSoundProvider : NativeSoundProvider() {
    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(
        channels: Int,
        frequency: Int,
        gen: AudioPlatformOutputGen
    ): AudioPlatformOutput = AudioPlatformOutput.simple(this, channels, frequency, gen) { buffer ->
        val arena = Arena()

        val handlePtr = arena.alloc<HWAVEOUTVar>()
        val freq = frequency
        val blockAlign = (channels * Short.SIZE_BYTES)
        val format = arena.alloc<WAVEFORMATEX>().also { format ->
            format.wFormatTag = WAVE_FORMAT_PCM.convert()
            format.nChannels = channels.convert() // 2?
            format.nSamplesPerSec = freq.convert()
            format.wBitsPerSample = Short.SIZE_BITS.convert() // 16
            format.nBlockAlign = (channels * Short.SIZE_BYTES).convert()
            format.nAvgBytesPerSec = (freq * blockAlign).convert()
            format.cbSize = sizeOf<WAVEFORMATEX>().convert()
        }

        val SPLIT = 4
        val headerSamples = buffer.totalSamples / SPLIT
        val headers = Array(8) { WaveHeader(it, headerSamples, buffer.channels, arena) }

        //println("handle=$handle")

        AudioPlatformOutputSimple(
            init = {
                WINMM.waveOutOpen(handlePtr.ptr, WAVE_MAPPER, format.ptr, 0u, 0u, 0u).also {
                    if (it != 0u) println("WINMM.waveOutOpen: $it")
                }
                for (header in headers) header.prepare(handlePtr.value)
            },
            output = {
                var position = 0
                while (true) {
                    val header = headers.firstOrNull { !it.hdr.isInQueue }
                    if (header != null) {
                        //println("READY")
                        header.write(handlePtr.value, it, position)
                        position += headerSamples
                        //println("position=$position")
                        if (position >= it.totalSamples) break
                    } else {
                        //println("ALL QUEUED")
                        delay(timeMillis = 1)
                    }
                }
            },
            close = {
                while (headers.any { it.hdr.isInQueue }) delay(timeMillis = 1)
                //println("CLOSE")
                for (header in headers) header.dispose(handlePtr.value)
                //runBlockingNoJs {
                //    wait()
                //}
                WINMM.waveOutClose(handlePtr.value)
                arena.clear()
            },
        )
    }

    private class WaveHeader(
        val id: Int,
        val totalSamples: Int,
        val channels: Int,
        val arena: ArenaBase,
    ) {
        val totalBytes = (totalSamples * channels * Short.SIZE_BYTES)
        val dataMem = arena.allocArray<ShortVar>(totalBytes / 2)
        val hdr = arena.alloc<WAVEHDR>().also { hdr ->
            hdr.lpData = dataMem.reinterpret()
            hdr.dwBufferLength = totalBytes.convert()
            hdr.dwFlags = 0.convert()
        }

        fun prepare(handle: HWAVEOUT?) {
            //println(data[0].toList())

            val channels = this.channels
            hdr.dwBufferLength = (totalSamples * channels * Short.SIZE_BYTES).convert()

            //if (hdr.isPrepared) dispose()
            if (!hdr.isPrepared) {
                //println("-> prepare")
                WINMM.waveOutPrepareHeader(handle, hdr.ptr, sizeOf<WAVEHDR>().convert())
            }
        }

        fun write(handle: HWAVEOUT?, samples: AudioSamplesInterleaved, position: Int) {
            val samplesData = samples.data
            for (n in 0 until channels * totalSamples) dataMem[n] = samplesData[n + position * channels].short
            WINMM.waveOutWrite(handle, hdr.ptr, sizeOf<WAVEHDR>().convert())
        }

        fun dispose(handle: HWAVEOUT?) {
            for (n in 0 until channels * totalSamples) dataMem[n] = 0
            WINMM.waveOutUnprepareHeader(handle, hdr.ptr, sizeOf<WAVEHDR>().convert())
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
            operator fun getValue(obj: Any?, property: KProperty<*>): CPointer<CFunction<T>> {
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

}
