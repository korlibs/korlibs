package korlibs.audio.sound.backend

import korlibs.audio.sound.*
import korlibs.ffi.*
import korlibs.memory.*
import kotlinx.coroutines.*

object FFIJVMWaveOutNativeSoundProvider : NativeSoundProvider() {
    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(
        channels: Int,
        frequency: Int,
        gen: AudioPlatformOutputGen
    ): AudioPlatformOutput = AudioPlatformOutput.simple(this, channels, frequency, gen) { buffer ->
        val arena = FFIArena()

        val handlePtr = arena.allocBytes(8).typed<FFIPointer?>()
        val freq = frequency
        val blockAlign = (channels * Short.SIZE_BYTES)
        val format = WAVEFORMATEX(arena.allocBytes(WAVEFORMATEX().size)).also { format ->
            format.wFormatTag = WINMM.WAVE_FORMAT_PCM.toShort()
            format.nChannels = channels.toShort() // 2?
            format.nSamplesPerSec = freq.toInt()
            format.wBitsPerSample = Short.SIZE_BITS.toShort() // 16
            format.nBlockAlign = ((channels * Short.SIZE_BYTES).toShort())
            format.nAvgBytesPerSec = freq * blockAlign
            format.cbSize = format.size.toShort()
        }

        val SPLIT = 4
        val headerSamples = buffer.totalSamples / SPLIT
        val headers = Array(8) { WaveHeader(it, headerSamples, buffer.channels, arena) }

        //println("handle=$handle")

        AudioPlatformOutputSimple(
            init = {
                WINMM.waveOutOpen(handlePtr.pointer, WINMM.WAVE_MAPPER, format.ptr, null, null, 0).also {
                    if (it != 0) println("WINMM.waveOutOpen: $it")
                }
                for (header in headers) header.prepare(handlePtr[0])
            },
            output = {
                var position = 0
                while (true) {
                    val header = headers.firstOrNull { !it.hdr.isInQueue }
                    if (header != null) {
                        //println("READY")
                        header.write(handlePtr[0], it, position)
                        position += headerSamples
                        //println("position=$position")
                        if (position >= it.totalSamples) break
                    } else {
                        //println("ALL QUEUED")
                        delay(1L)
                    }
                }
            },
            close = {
                while (headers.any { it.hdr.isInQueue }) delay(1L)
                //println("CLOSE")
                for (header in headers) header.dispose(handlePtr[0])
                //runBlockingNoJs {
                //    wait()
                //}
                WINMM.waveOutClose(handlePtr[0])
                arena.clear()
            },
        )
    }


    private class WaveHeader(
        val id: Int,
        val totalSamples: Int,
        val channels: Int,
        val arena: FFIArena,
    ) {
        val totalBytes = (totalSamples * channels * Short.SIZE_BYTES)
        val dataMem = arena.allocBytes(totalBytes).typed<Short>()
        val hdr = WAVEHDR(arena.allocBytes(WAVEHDR().size)).also { hdr ->
            hdr.lpData = dataMem.reinterpret()
            hdr.dwBufferLength = totalBytes
            hdr.dwFlags = 0
        }

        fun prepare(handle: FFIPointer?) {
            //println(data[0].toList())

            val channels = this.channels
            hdr.dwBufferLength = (totalSamples * channels * Short.SIZE_BYTES)

            //if (hdr.isPrepared) dispose()
            if (!hdr.isPrepared) {
                //println("-> prepare")
                WINMM.waveOutPrepareHeader(handle, hdr.ptr, hdr.size)
            }
        }

        fun write(handle: FFIPointer?, samples: AudioSamplesInterleaved, position: Int) {
            val samplesData = samples.data
            for (n in 0 until channels * totalSamples) dataMem[n] = samplesData[n + position * channels].short
            WINMM.waveOutWrite(handle, hdr.ptr, hdr.size)
        }

        fun dispose(handle: FFIPointer?) {
            for (n in 0 until channels * totalSamples) dataMem[n] = 0
            WINMM.waveOutUnprepareHeader(handle, hdr.ptr, hdr.size)
        }

        override fun toString(): String = "WaveHeader(id=$id, totalSamples=$totalSamples, nchannels=$channels, hdr=$hdr)"
    }

    internal class WAVEHDR(pointer: FFIPointer? = null) : FFIStructure(pointer) {
        var lpData by pointer<Byte>()
        var dwBufferLength by int()
        var dwBytesRecorded by int()
        var dwUser by pointer<Byte>()
        var dwFlags by int()
        var dwLoops by int()
        var lpNext by pointer<Byte>()
        var reserved by pointer<Byte>()

        val isDone: Boolean get() = dwFlags.hasFlags(WINMM.WHDR_DONE)
        val isPrepared: Boolean get() = dwFlags.hasFlags(WINMM.WHDR_PREPARED)
        val isBeginLoop: Boolean get() = dwFlags.hasFlags(WINMM.WHDR_BEGINLOOP)
        val isEndLoop: Boolean get() = dwFlags.hasFlags(WINMM.WHDR_ENDLOOP)
        val isInQueue: Boolean get() = dwFlags.hasFlags(WINMM.WHDR_INQUEUE)

        override fun toString(): String = "WAVEHDR(dwBufferLength=$dwBufferLength, isDone=$isDone, isPrepared=$isPrepared, isInQueue=$isInQueue, flags=$dwFlags)"
    }

    internal class WAVEFORMATEX(pointer: FFIPointer? = null) : FFIStructure(pointer) {
        var wFormatTag by short()
        var nChannels by short()
        var nSamplesPerSec by int()
        var nAvgBytesPerSec by int()
        var nBlockAlign by short()
        var wBitsPerSample by short()
        var cbSize by short()
    }

    internal object WINMM : FFILib("winmm.dll") {
        val waveOutOpen by func<(phwo: LPHWAVEOUT?, uDeviceID: Int, pwfx: LPCWAVEFORMATEX?, dwCallback: FFIPointer?, dwInstance: FFIPointer?, fdwOpen: Int) -> Int>()
        val waveOutClose by func<(hwo: HWAVEOUT?) -> Int>()
        val waveOutReset by func<(hwo: HWAVEOUT?) -> Int>()
        val waveOutPrepareHeader by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: Int) -> Int>()
        val waveOutWrite by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: Int) -> Int>()
        val waveOutUnprepareHeader by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: Int) -> Int>()
        val waveOutGetPosition by func<(hwo: HWAVEOUT?, pmmt: LPMMTIME?, cbmmt: Int) -> Int>()
        const val WAVE_MAPPER = -1
        const val WAVE_FORMAT_PCM = 1
        const val WHDR_DONE = 0x00000001
        const val WHDR_PREPARED = 0x00000002
        const val WHDR_BEGINLOOP = 0x00000004
        const val WHDR_ENDLOOP = 0x00000008
        const val WHDR_INQUEUE = 0x00000010
        const val TIME_SAMPLES = 2
    }
}

internal typealias LPHWAVEOUT = FFIPointer
internal typealias HWAVEOUT = FFIPointer
internal typealias LPCWAVEFORMATEX = FFIPointer
internal typealias LPWAVEHDR = FFIPointer
internal typealias LPMMTIME = FFIPointer?
