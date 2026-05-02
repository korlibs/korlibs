package korlibs.audio.sound.backend

import korlibs.audio.sound.*
import korlibs.ffi.*
import kotlinx.coroutines.*

object FFIALSANativeSoundProvider : NativeSoundProvider() {
    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(channels: Int, frequency: Int, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        val pcm = A2.snd_pcm_open("default", A2.SND_PCM_STREAM_PLAYBACK, 0)
        if (pcm.address == 0L) {
            error("Can't initialize ALSA")
            //running = false
            //return@nativeThread
        }
        //println("ALSANativeSoundProvider.createPlatformAudioOutput(freq=$freq)")
        return AudioPlatformOutput.simple(this, channels, frequency, gen) {
            AudioPlatformOutputSimple(
                init = {
                    //val latency = 8 * 4096
                    val latency = 32 * 4096
                    A2.snd_pcm_set_params(
                        pcm, A2.SND_PCM_FORMAT_S16_LE, A2.SND_PCM_ACCESS_RW_INTERLEAVED,
                        channels, frequency, 1, latency
                    )
                },
                output = { buffer ->
                    while (true) {
                        val written =
                            A2.snd_pcm_writei(pcm, buffer.data.asShortArray(), 0, buffer.totalSamples * channels, buffer.totalSamples)
                        //println("offset=$offset, pending=$pending, written=$written")
                        if (written == -A2.EPIPE) {
                            //println("ALSA: EPIPE error")
                            //A2.snd_pcm_prepare(pcm)
                            A2.snd_pcm_recover(pcm, written, 0)
                            continue
                            //blockingSleep(1.milliseconds)
                        } else if (written < 0) {
                            println("ALSA: OTHER error: $written")
                            delay(1L)
                        } else {
                            break
                        }
                    }
                },
                close = {
                    //println("!!COMPLETED : pcm=$pcm")
                    A2.snd_pcm_wait(pcm, 1000)
                    A2.snd_pcm_drain(pcm)
                    A2.snd_pcm_close(pcm)
                    //println("!!CLOSED = $pcm")
                },
            )
        }
    }


    object A2 : FFILib("libasound.so.2"){
        fun snd_pcm_open(name: String, stream: Int, mode: Int): FFIPointer? {
            val ptrs = FFIPointerArray(1)
            if (snd_pcm_open(ptrs, name, stream ,mode) != 0) return null
            return ptrs[0]
        }
        val snd_pcm_open: (pcmPtr: FFIPointerArray, name: String?, stream: Int, mode: Int) -> Int by func()
        fun snd_pcm_writei(pcm: FFIPointer?, buffer: ShortArray, offset: Int, size: Int, nframes: Int): Int = snd_pcm_writei(pcm, buffer.copyOfRange(offset, offset + size), nframes)

        val snd_pcm_writei: (pcm: FFIPointer?, buffer: ShortArray, size: Int) -> Int by func()
        val snd_pcm_drain: (pcm: FFIPointer?) -> Int by func()
        val snd_pcm_close: (pcm: FFIPointer?) -> Int by func()
        val snd_pcm_set_params: (pcm: FFIPointer?, format: Int, access: Int, channels: Int, rate: Int, softResample: Int, latency: Int) -> Int by func()

        val snd_pcm_wait: (pcm: FFIPointer?, timeout: Int) -> Int by func()
        val snd_pcm_recover: (pcm: FFIPointer?, err: Int, silent: Int) -> Int by func()

        const val EPIPE = 32	// Broken pipe
        const val SND_PCM_STREAM_PLAYBACK = 0
        const val SND_PCM_ACCESS_RW_INTERLEAVED = 3 // snd_pcm_readi/snd_pcm_writei access
        const val SND_PCM_FORMAT_S16_LE = 2
    }
}
