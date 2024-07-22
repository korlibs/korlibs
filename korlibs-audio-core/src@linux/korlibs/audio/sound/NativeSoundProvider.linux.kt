package korlibs.audio.sound

import kotlinx.cinterop.*
import kotlinx.coroutines.delay
import platform.posix.RTLD_LAZY
import platform.posix.dlopen
import platform.posix.dlsym
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KProperty

actual val nativeSoundProvider: NativeSoundProvider by lazy {
    try {
        ALSANativeSoundProvider
    } catch (e: Throwable) {
        e.printStackTrace()
        DummyNativeSoundProvider
    }
}

@OptIn(ExperimentalForeignApi::class)
object ALSANativeSoundProvider : NativeSoundProvider() {
    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(channels: Int, frequency: Int, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        val pcm = A2.snd_pcm_open("default", A2.SND_PCM_STREAM_PLAYBACK, 0)
            ?: error("Can't initialize ALSA")

        //running = false
        //return@nativeThread
        //println("ALSANativeSoundProvider.createPlatformAudioOutput(freq=$freq)")
        return AudioPlatformOutput.simple(this, channels, frequency, gen) {
            AudioPlatformOutputSimple(
                init = {
                    //val latency = 8 * 4096
                    val latency = 32 * 4096
                    A2.snd_pcm_set_params(pcm, A2.SND_PCM_FORMAT_S16_LE, A2.SND_PCM_ACCESS_RW_INTERLEAVED, channels, frequency, 1, latency)
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

    @OptIn(ExperimentalForeignApi::class)
    internal object A2 {
        private val LIB = dlopen("libasound.so.2", RTLD_LAZY)
        //private val LIB = dlopen("/usr/lib/x86_64-linux-gnu/libasound.so.2", RTLD_LAZY)
        private class func<T : Function<*>> {
            inline operator fun getValue(obj: Any?, property: KProperty<*>): CPointer<CFunction<T>> {
                val symbol = dlsym(LIB, property.name)
                    ?: dlsym(LIB, "_${property.name}")
                    ?: dlsym(LIB, "__${property.name}")
                    ?: error("Can't load symbol ${property.name} from lib=$LIB")
                return symbol.reinterpret()
            }
        }
        fun snd_pcm_open(name: String, stream: Int, mode: Int): FFIPointer? {
            memScoped {
                val ptrs = alloc<CPointerVar<*>>()
                if (snd_pcm_open(ptrs.ptr, name.cstr, stream ,mode) != 0) return null
                return ptrs.value
            }
        }

        val snd_pcm_open by func<(pcmPtr: CPointer<CPointerVar<*>>?, name: CValues<ByteVar>?, stream: Int, mode: Int) -> Int>()
        fun snd_pcm_writei(pcm: FFIPointer?, buffer: ShortArray, offset: Int, size: Int, nframes: Int): Int =
            buffer.usePinned { snd_pcm_writei(pcm, it.addressOf(offset), nframes) }
        val snd_pcm_writei by func<(pcm: FFIPointer?, buffer: CPointer<ShortVar>, size: Int) -> Int>()
        val snd_pcm_drain by func<(pcm: FFIPointer?) -> Int>()
        val snd_pcm_close by func<(pcm: FFIPointer?) -> Int>()
        val snd_pcm_set_params by func<(pcm: FFIPointer?, format: Int, access: Int, channels: Int, rate: Int, softResample: Int, latency: Int) -> Int>()
        val snd_pcm_wait by func<(pcm: FFIPointer?, timeout: Int) -> Int>()
        val snd_pcm_recover by func<(pcm: FFIPointer?, err: Int, silent: Int) -> Int>()
        const val EPIPE = 32	// Broken pipe
        const val SND_PCM_STREAM_PLAYBACK = 0
        const val SND_PCM_ACCESS_RW_INTERLEAVED = 3 // snd_pcm_readi/snd_pcm_writei access
        const val SND_PCM_FORMAT_S16_LE = 2
    }
}

@OptIn(ExperimentalForeignApi::class)
typealias FFIPointer = COpaquePointer

