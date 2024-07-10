package korlibs.audio.core.impl

import korlibs.audio.core.*
import korlibs.ffi.*
import korlibs.math.geom.*
import korlibs.memory.*

// https://openal.org/documentation/OpenAL_Programmers_Guide.pdf
internal object OpenALAudioSystem : AudioSystem() {
    val available: Boolean by lazy {
        runCatching { AL.alcGetInteger(null, AL.ALC_MAJOR_VERSION) }.getOrNull() != null
    }

    override val devices: List<AudioDevice> by lazy {
        val strings = AL.alcGetStringAsPointer(null, AL.ALC_ALL_DEVICES_SPECIFIER).toStringzList().takeIf { it.isNotEmpty() }
            ?: listOf(AL.alcGetString(null, AL.ALC_DEVICE_SPECIFIER))
        strings.mapIndexed { index, s ->
            AudioDevice(s ?: "", isDefault = index == 0, id = index.toLong())
        }
    }

    fun FFIPointer?.toStringzList(): List<String> = buildList {
        if (this@toStringzList == null) return@buildList

        var ptr = this@toStringzList
        while (ptr.getS8() != 0.toByte()) {
            val len = ptr.strlen()
            add(ptr.getStringz())
            ptr += len + 1
        }
    }

    override fun createPlayer(device: AudioDevice): AudioPlayer = OpenALAudioPlayer(device)

    class OpenALAudioPlayer(override val device: AudioDevice) : AudioPlayer() {
        val dev = AL.alcOpenDevice(device.name.takeIf { it.isNotBlank() }).also {
            println("openal.dev=$it")
        }
        val context = dev?.let { AL.alcCreateContext(it, null).also {
            AL.alcMakeContextCurrent(it)
            ffiScoped {
                AL.alListenerf(AL.AL_GAIN, 1f)
                AL.alListener3f(AL.AL_POSITION, 0f, 0f, 1.0f)
                AL.alListener3f(AL.AL_VELOCITY, 0f, 0f, 0f)
                AL.alListenerfv(AL.AL_ORIENTATION, floatArrayOf(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f))
            }
        } }

        override var listenerGain: Float = 1f
            set(value) { field = value; AL.alListenerf(AL.AL_GAIN, value) }

        override var listenerSpeed: Vector3F = Vector3F.ZERO
            set(value) { field = value; AL.alListener3f(AL.AL_VELOCITY, value.x, value.y, value.z) }
        override var listenerPosition: Vector3F = Vector3F.ZERO
            set(value) { field = value; AL.alListener3f(AL.AL_POSITION, value.x, value.y, value.z) }
        override var listenerOrientation: AudioOrientation = AudioOrientation()
            set(value) {  field = value; AL.alListenerfv(AL.AL_ORIENTATION, floatArrayOf(value.at.x, value.at.y, value.at.z, value.up.x, value.up.y, value.up.z))  }

        override fun createSource(): AudioSource = OpenALAudioSource(this)

        override fun close() {
            AL.alcCloseDevice(dev)
        }

        fun makeCurrent() {
            AL.alcMakeContextCurrent(context)
        }
    }

    // https://github.com/korlibs/korlibs4/blob/backup/korau/src/nativeMain/kotlin/korlibs/audio/sound/backends/OpenAL.kt
    class OpenALAudioSource(override val player: OpenALAudioPlayer) : AudioSource() {
        val buffer = AL.alGenBuffer()
        val source = AL.alGenSource()

        override var pitch: Float = 1f
            set(value) { field = value; al { AL.alSourcef(source, AL.AL_PITCH, value) } }
        override var gain: Float = 1f
            set(value) {
                val value = maxOf(value, 0f)
                field = value; al { AL.alSourcef(source, AL.AL_GAIN, value) } }
        override var dataRate: Int = 44100
            //set(value) { field = value; al { AL.alSourcei(source, AL.AL_FREQUENCY, value) } }
        override var nchannels: Int = 1
            //set(value) { field = value; al { AL.alSourcei(source, AL.AL_CHANNELS, value) } }
        override var position: Vector3 = Vector3.ZERO
            set(value) { field = value; al { AL.alSource3f(source, AL.AL_POSITION, value.x, value.y, value.z) } }
        override var velocity: Vector3 = Vector3.ZERO
            set(value) { field = value; al { AL.alSource3f(source, AL.AL_VELOCITY, value.x, value.y, value.z) } }
        override var direction: Vector3 = Vector3.ZERO
            set(value) { field = value; al { AL.alSource3f(source, AL.AL_DIRECTION, value.x, value.y, value.z) } }
        override var looping: Boolean = false
            set(value) { field = value; al { AL.alSourcei(source, AL.AL_LOOPING, if (value) AL.AL_TRUE else AL.AL_FALSE) } }

        override var samplesPosition: Long
            get() = AL.alGetSourcei(source, AL.AL_SAMPLE_OFFSET).toLong()
            set(value) {
                al { AL.alSourcei(source, AL.AL_SAMPLE_OFFSET, value.toInt()) }
            }
        override var data: Array<AudioSampleArray>? = null
            set(value) {
                field = value
                val data = value?.combined() ?: return
                val format = if (nchannels == 1) AL.AL_FORMAT_MONO16 else AL.AL_FORMAT_STEREO16
                val dataSizeBytes = data.size * 2
                //println(data.asShortArray().toList())
                //val dataRate = 48000
                //println("buffer=$buffer")
                //println("source=$source")
                //println("dataRate=$dataRate")
                //println("bytesSize=${data.size * 2}")
                //val bufferData = Buffer(data.size * 2, direct = true)
                //bufferData.setArrayLE(0, data.asShortArray())
                //al { AL.alBufferData(buffer, format, bufferData, data.size * 2, dataRate) }
                ffiScoped {
                    val mem = CreateFFIMemory(dataSizeBytes)
                    val pointer = mem.pointer

                    for (n in 0 until data.size) {
                        pointer.set16(data[n].short, n * 2)
                    }

                    al { AL.alBufferData(buffer, format, pointer, dataSizeBytes, dataRate) }
                    al { AL.alSourcei(source, AL.AL_BUFFER, buffer) }
                }
            }

        override val state: AudioSourceState
            get() {
                val result = AL.alGetSourceState(source)
                return when (result) {
                    AL.AL_INITIAL -> AudioSourceState.INITIAL
                    AL.AL_PLAYING -> AudioSourceState.PLAYING
                    AL.AL_PAUSED -> AudioSourceState.PAUSED
                    AL.AL_STOPPED -> AudioSourceState.STOPPED
                    else -> error("Invalid state: $result")
                }
            }

        private fun updateProps() {
            pitch = pitch
            gain = gain
            dataRate = dataRate
            nchannels = nchannels
            position = position
            velocity = velocity
            direction = direction
            looping = looping

            al { AL.alSourcef(source, AL.AL_ROLLOFF_FACTOR, 0.0f) }
            al { AL.alSourcei(source, AL.AL_SOURCE_RELATIVE, 1) }
        }

        override fun _play() {
            //AL.alSourcef(source, AL.AL_SEC_OFFSET, 0f)
            al { AL.alSourcePlay(source) }
            //println("PLAY")
        }

        private fun _pause() {
            al { AL.alSourcePause(source) }
            //println("PAUSE")
        }

        override fun _stop() {
            al { AL.alSourceStop(source) }
            //println("STOP")
        }

        override fun close() {
            al { AL.alDeleteBuffer(buffer) }
            al { AL.alDeleteSource(source) }
            //println("CLOSE")
        }

        inline fun <T> al(block: () -> T): T? {
            return runCatchingAl {
                player.makeCurrent()
                AL.alGetError()
                val result = block()
                val error = AL.alGetError()
                if (error != AL.AL_NO_ERROR) {
                    Error("AL error: ${AL.alGetErrorName(error)} :: $error").printStackTrace()
                }
                result
            }
        }

        init {
            updateProps()
        }
    }
}