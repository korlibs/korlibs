package korlibs.audio.core.impl

import korlibs.audio.core.*
import korlibs.datastructure.closeable.*
import korlibs.ffi.*
import korlibs.memory.*
import korlibs.time.*

internal object Win32AudioSystem : AudioSystem() {
    override fun createPlayer(device: AudioDevice): AudioPlayer = Win32WaveOutSoftAudioPlayer(device)

    override val devices: List<AudioDevice> by lazy {
        ffiScoped {
            val ndevs = WINMM.waveOutGetNumDevs()
            val MEM_SIZE = 132
            val mem = allocBytes(MEM_SIZE).typed<Int>()
            List(ndevs) { n ->
                WINMM.waveOutGetDevCapsW(n, mem.pointer, MEM_SIZE)
                // typedef struct tagWAVEOUTCAPS2W {
                //   WORD wMid;
                //   WORD wPid;
                //   MMVERSION vDriverVersion;
                //   WCHAR szPname[MAXPNAMELEN];
                //   DWORD dwFormats;
                //   WORD wChannels;
                //   WORD wReserved1;
                //   DWORD dwSupport;
                //   GUID ManufacturerGuid;
                //   GUID ProductGuid;
                //   GUID NameGuid;
                // } WAVEOUTCAPS2W, *PWAVEOUTCAPS2W, *NPWAVEOUTCAPS2W, *LPWAVEOUTCAPS2W;
                //println()
                val name = mem.pointer.withOffset(8)?.getWideStringz() ?: "unknown"
                AudioDevice(name, isDefault = n == 0, id = n.toLong())
            }
        }
    }

    class Win32WaveOutSoftAudioPlayer(override val device: AudioDevice) : SoftAudioSystem(WaveOutAudioStreamPlayer(device)) {
        companion object {
            val DEVICE_DEFAULT: AudioDevice = AudioDevice("default", isDefault = true)
            val DEVICE_ALL: List<AudioDevice> = listOf(DEVICE_DEFAULT)
        }

        class WaveOutAudioStreamPlayer(val device: AudioDevice) : AudioStreamPlayer {
            @OptIn(ExperimentalStdlibApi::class)
            override fun playStream(rate: Int, channels: Int, gen: (position: Long, data: Array<AudioSampleArray>) -> Int): Closeable {
                var running = true
                val nativeThread = korlibs.datastructure.thread.NativeThread {
                    ffiScoped {
                        val arena = this
                        val handlePtr = allocBytes(8).typed<FFIPointer?>()
                        val freq = rate
                        val blockAlign = (channels * Short.SIZE_BYTES)
                        val format = WAVEFORMATEX(allocBytes(WAVEFORMATEX().size)).also { format ->
                            format.wFormatTag = WINMM.WAVE_FORMAT_PCM.toShort()
                            format.nChannels = channels.toShort() // 2?
                            format.nSamplesPerSec = freq.toInt()
                            format.wBitsPerSample = Short.SIZE_BITS.toShort() // 16
                            format.nBlockAlign = ((channels * Short.SIZE_BYTES).toShort())
                            format.nAvgBytesPerSec = freq * blockAlign
                            format.cbSize = format.size.toShort()
                        }
                        //WINMM.waveOutOpen(handlePtr.pointer, WINMM.WAVE_MAPPER, format.ptr, null, null, 0).also {
                        WINMM.waveOutOpen(handlePtr.pointer, device.id.toInt(), format.ptr, null, null, 0).also {
                            if (it != 0) println("WINMM.waveOutOpen: $it")
                        }
                        var handle = handlePtr[0]
                        //println("handle=$handle")

                        var headers = Array(4) { WaveHeader(it, handle, 1024, channels, arena) }
                        var position = 0L

                        try {
                            while (running) {
                                var queued = 0
                                for (header in headers) {
                                    if (!header.hdr.isInQueue) {
                                        position += gen(position, header.samples)
                                        header.prepareAndWrite()
                                        queued++
                                        //println("Sending running=$running, availableRead=$availableRead, header=${header}")
                                    }
                                }
                                if (queued == 0) blockingSleep(1.milliseconds)
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
                return Closeable {
                    running = false
                }
            }
        }
    }
}


private class WaveHeader(
    val id: Int,
    val handle: FFIPointer?,
    val totalSamples: Int,
    val channels: Int,
    val arena: FFIArena,
) {
    val samples = Array(channels) { AudioSampleArray(totalSamples) }

    val totalBytes = (totalSamples * channels * Short.SIZE_BYTES)
    val dataMem = arena.allocBytes(totalBytes).typed<Short>()
    val hdr = WAVEHDR(arena.allocBytes(WAVEHDR().size)).also { hdr ->
        hdr.lpData = dataMem.reinterpret()
        hdr.dwBufferLength = totalBytes
        hdr.dwFlags = 0
    }

    fun prepareAndWrite(totalSamples: Int = this.totalSamples) {
        //println(data[0].toList())

        val channels = this.channels
        hdr.dwBufferLength = (totalSamples * channels * Short.SIZE_BYTES)

        var n = 0
        val stride = channels
        for (ch in 0 until channels) {
            val a = samples[ch]
            for (s in 0 until totalSamples) {
                dataMem[ch + stride * s] = a[s].short
            }
        }
        //if (hdr.isPrepared) dispose()
        if (!hdr.isPrepared) {
            //println("-> prepare")
            WINMM.waveOutPrepareHeader(handle, hdr.ptr, hdr.size)
        }
        WINMM.waveOutWrite(handle, hdr.ptr, hdr.size)
    }

    fun dispose() {
        WINMM.waveOutUnprepareHeader(handle, hdr.ptr, hdr.size)
    }

    override fun toString(): String = "WaveHeader(id=$id, totalSamples=$totalSamples, nchannels=$channels, hdr=$hdr)"
}

private typealias LPHWAVEOUT = FFIPointer
private typealias HWAVEOUT = FFIPointer
private typealias LPCWAVEFORMATEX = FFIPointer
private typealias LPWAVEHDR = FFIPointer

private class WAVEHDR(pointer: FFIPointer? = null) : FFIStructure(pointer) {
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

private class WAVEFORMATEX(pointer: FFIPointer? = null) : FFIStructure(pointer) {
    var wFormatTag by short()
    var nChannels by short()
    var nSamplesPerSec by int()
    var nAvgBytesPerSec by int()
    var nBlockAlign by short()
    var wBitsPerSample by short()
    var cbSize by short()
}

private typealias LPMMTIME = FFIPointer?
private class MMTIME(pointer: FFIPointer? = null) : FFIStructure(pointer) {
    var wType by int()
    var values by int()
}

private object WINMM : FFILib("winmm.dll") {
    //val waveOutOpen by func<(phwo: LPHWAVEOUT?, uDeviceID: Int, pwfx: LPCWAVEFORMATEX?, dwCallback: Callback?, dwInstance: Pointer?, fdwOpen: Int) -> Int>()
    val waveOutOpen by func<(phwo: LPHWAVEOUT?, uDeviceID: Int, pwfx: LPCWAVEFORMATEX?, dwCallback: FFIPointer?, dwInstance: FFIPointer?, fdwOpen: Int) -> Int>()
    val waveOutClose by func<(hwo: HWAVEOUT?) -> Int>()
    val waveOutReset by func<(hwo: HWAVEOUT?) -> Int>()
    val waveOutPrepareHeader by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: Int) -> Int>()
    val waveOutWrite by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: Int) -> Int>()
    val waveOutUnprepareHeader by func<(hwo: HWAVEOUT?, pwh: LPWAVEHDR?, cbwh: Int) -> Int>()
    val waveOutGetPosition by func<(hwo: HWAVEOUT?, pmmt: LPMMTIME?, cbmmt: Int) -> Int>()
    val waveOutGetNumDevs by func<() -> Int>()
    val waveOutGetDevCapsW by func<(uDeviceID: Int, pwoc: FFIPointer, cbwoc: Int) -> Int>()

    fun waveOutGetPositionInSamples(hwo: HWAVEOUT?): Long {
        ffiScoped {
            val mem = allocBytes(16).typed<Int>()
            mem[0] = TIME_SAMPLES
            val res = waveOutGetPosition(hwo, mem.pointer, 16)
            val wType = mem[0]
            val value = mem[1]
            //println("waveOutGetPosition: res=$res, wType=$wType, value=$value")
            return value.toLong()
        }
    }

    const val WAVE_MAPPER = -1
    const val WAVE_FORMAT_PCM = 1

    const val WHDR_DONE = 0x00000001
    const val WHDR_PREPARED = 0x00000002
    const val WHDR_BEGINLOOP = 0x00000004
    const val WHDR_ENDLOOP = 0x00000008
    const val WHDR_INQUEUE = 0x00000010

    const val TIME_MS = 1
    const val TIME_SAMPLES = 2
    const val TIME_BYTES = 4
    const val TIME_SMPTE = 8
    const val TIME_MIDI =16
    const val TIME_TICKS =32
}
