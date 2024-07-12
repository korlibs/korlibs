package korlibs.audio.core.impl

import korlibs.audio.core.*
import korlibs.concurrent.thread.*
import korlibs.ffi.*
import korlibs.time.*

internal object ALSAAudioSystem : AudioSystem() {
    override fun createPlayer(device: AudioDevice): AudioPlayer = SoftAudioPlayer(device, ALSAAudioStreamPlayer)

    override val devices: List<AudioDevice> by lazy {
        // @TODO: Implement this
        super.devices
    }

    object ALSAAudioStreamPlayer : AudioStreamPlayer {
        override fun playStream(device: AudioDevice, rate: Int, channels: Int, gen: (position: Long, data: SeparatedAudioSamples) -> Int): AudioSimpleStream {
            var paused = false
            val nativeThread = nativeThread(start = true, isDaemon = true) { thread ->
                val buffer = SeparatedAudioSamples(channels, 1024)
                val interleaved = buffer.interleaved()
                val pcm = A2.snd_pcm_open("default", A2.SND_PCM_STREAM_PLAYBACK, 0)
                if (pcm.address == 0L) {
                    error("Can't initialize ALSA")
                    //running = false
                    //return@nativeThread
                }

                //val latency = 8 * 4096
                val latency = 32 * 4096
                A2.snd_pcm_set_params(pcm, A2.SND_PCM_FORMAT_S16_LE, A2.SND_PCM_ACCESS_RW_INTERLEAVED, channels, rate, 1, latency)
                var position = 0L
                try {
                    while (thread.threadSuggestRunning) {
                        if (paused) {
                            NativeThread.sleep(1.milliseconds)
                            continue
                        }

                        gen(position, buffer)
                        buffer.interleaved(interleaved)
                        val written = A2.snd_pcm_writei(pcm, interleaved.asShortArray(), 0, buffer.nsamples * buffer.nchannels, buffer.nsamples)
                        //println("offset=$offset, pending=$pending, written=$written")
                        if (written == -A2.EPIPE) {
                            //println("ALSA: EPIPE error")
                            //A2.snd_pcm_prepare(pcm)
                            A2.snd_pcm_recover(pcm, written, 0)
                            continue
                            //blockingSleep(1.milliseconds)
                        } else if (written < 0) {
                            println("ALSA: OTHER error: $written")
                            //delay(1.milliseconds)
                            NativeThread.sleep(1.milliseconds)
                            break
                        }
                    }
                } finally {
                    //println("!!COMPLETED : pcm=$pcm")
                    A2.snd_pcm_wait(pcm, 1000)
                    A2.snd_pcm_drain(pcm)
                    A2.snd_pcm_close(pcm)
                    //println("!!CLOSED = $pcm")
                }
            }

            return AudioSimpleStream(
                onPausedChange = { paused = it },
                onClosed = { nativeThread.threadSuggestRunning = false }
            )
        }
    }
}

object A2 : FFILib("libasound.so.2"){
    fun snd_pcm_open(name: String, stream: Int, mode: Int): FFIPointer? {
        val ptrs = FFIPointerArray(1)
        if (snd_pcm_open(ptrs, name, stream ,mode) != 0) return null
        return ptrs[0]
    }

    val snd_pcm_open: (pcmPtr: FFIPointerArray, name: String?, stream: Int, mode: Int) -> Int by func()
    val snd_pcm_hw_params_any: (pcm: FFIPointer?, params: FFIPointer?) -> Int by func()
    val snd_pcm_hw_params_set_access: (pcm: FFIPointer?, params: FFIPointer?, access: Int) -> Int by func()
    val snd_pcm_hw_params_set_format: (pcm: FFIPointer?, params: FFIPointer?, format: Int) -> Int by func()
    val snd_pcm_hw_params_set_channels: (pcm: FFIPointer?, params: FFIPointer?, channels: Int) -> Int by func()
    val snd_pcm_hw_params_set_rate: (pcm: FFIPointer?, params: FFIPointer?, rate: Int, dir: Int) -> Int by func()
    val snd_pcm_hw_params: (pcm: FFIPointer?, params: FFIPointer?) -> Int by func()
    val snd_pcm_name: (pcm: FFIPointer?) -> String? by func()
    val snd_pcm_state: (pcm: FFIPointer?) -> Int by func()
    val snd_pcm_state_name: (state: Int) -> String? by func()
    val snd_pcm_hw_params_get_channels: (params: FFIPointer?, out: FFIPointer?) -> Int by func()
    val snd_pcm_hw_params_get_rate: (params: FFIPointer?, value: FFIPointer?, dir: FFIPointer?) -> Int by func()
    val snd_pcm_hw_params_get_period_size: (params: FFIPointer?, value: FFIPointer?, dir: FFIPointer?) -> Int by func()
    val snd_pcm_hw_params_get_period_time: (params: FFIPointer?, value: FFIPointer?, dir: FFIPointer?) -> Int by func()

    fun snd_pcm_writei(pcm: FFIPointer?, buffer: ShortArray, offset: Int, size: Int, nframes: Int): Int {
        //println("PCM=$pcm, buffer=$buffer, offset=$offset, size=$size")
        //if (size == 0) return 0
        return snd_pcm_writei(pcm, buffer.copyOfRange(offset, offset + size), nframes)
        //val mem = Memory((buffer.size * 2).toLong()).also { it.clear() }
        //for (n in 0 until size) mem.setShort((n * 2).toLong(), buffer[offset + n])
        ////A2.snd_pcm_wait(pcm.toCPointer(), 100)
        //return A2.snd_pcm_writei(pcm.toCPointer(), mem, nframes)
    }

    val snd_pcm_writei: (pcm: FFIPointer?, buffer: ShortArray, size: Int) -> Int by func()
    val snd_pcm_prepare: (pcm: FFIPointer?) -> Int by func()
    val snd_pcm_drain: (pcm: FFIPointer?) -> Int by func()
    val snd_pcm_drop: (pcm: FFIPointer?) -> Int by func()
    val snd_pcm_delay: (pcm: FFIPointer?, delay: FFIPointer?) -> Int by func()
    val snd_pcm_close: (pcm: FFIPointer?) -> Int by func()
    val snd_pcm_set_params: (
        pcm: FFIPointer?,
        format: Int,
        access: Int,
        channels: Int,
        rate: Int,
        softResample: Int,
        latency: Int
    ) -> Int by func()

    val snd_pcm_wait: (pcm: FFIPointer?, timeout: Int) -> Int by func()
    val snd_pcm_recover: (pcm: FFIPointer?, err: Int, silent: Int) -> Int by func()

    const val ERROR = -1

    const val EPIPE = 32	// Broken pipe
    const val EBADFD = 77	// File descriptor in bad state
    const val ESTRPIPE = 86	// Streams pipe error

    const val SND_PCM_STREAM_PLAYBACK = 0
    const val SND_PCM_STREAM_CAPTURE = 1

    const val SND_PCM_ACCESS_MMAP_INTERLEAVED = 0 // mmap access with simple interleaved channels
    const val SND_PCM_ACCESS_MMAP_NONINTERLEAVED = 1 // mmap access with simple non interleaved channels
    const val SND_PCM_ACCESS_MMAP_COMPLEX = 2 // mmap access with complex placement
    const val SND_PCM_ACCESS_RW_INTERLEAVED = 3 // snd_pcm_readi/snd_pcm_writei access
    const val SND_PCM_ACCESS_RW_NONINTERLEAVED = 4 // /snd_pcm_writen access

    const val SND_PCM_FORMAT_S16_LE = 2

    const val SND_PCM_STATE_OPEN = 0 // Open
    const val SND_PCM_STATE_SETUP = 1 // Setup installed
    const val SND_PCM_STATE_PREPARED = 2 // Ready to start
    const val SND_PCM_STATE_RUNNING = 3 // Running
    const val SND_PCM_STATE_XRUN = 4 // Stopped: underrun (playback) or overrun (capture) detected
    const val SND_PCM_STATE_DRAINING = 5 // Draining: running (playback) or stopped (capture)
    const val SND_PCM_STATE_PAUSED = 6 // Paused
    const val SND_PCM_STATE_SUSPENDED = 7 // Hardware is suspended
    const val SND_PCM_STATE_DISCONNECTED = 8 // Hardware is disconnected

    val SND_PCM_NONBLOCK: Int get() = TODO()
}