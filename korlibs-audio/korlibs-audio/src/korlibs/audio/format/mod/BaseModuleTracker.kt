package korlibs.audio.format.mod

import korlibs.audio.format.*
import korlibs.audio.sound.*
import korlibs.io.file.*
import korlibs.io.stream.*
import korlibs.logger.*
import korlibs.memory.*
import korlibs.time.*
import kotlin.math.*
import kotlin.time.*

abstract class BaseModuleTracker {
    companion object {
        val LOGGER = Logger("BaseModuleTracker")
    }

    abstract class Format(vararg exts: String) : AudioFormat(*exts) {
        abstract fun createTracker(): BaseModuleTracker
        open suspend fun fastValidate(data: AsyncStream): Boolean = true

        override suspend fun tryReadInfo(data: AsyncStream, props: AudioDecodingProps): Info? {
            try {
                if (!fastValidate(data)) return null
                val time: Duration? = when (props.exactTimings) {
                    true -> {
                        val mod = createTracker()
                        if (!mod.parse(data.readAll().toNBufferUInt8())) return null
                        mod.totalLengthInSamples?.let { samples -> (samples.toDouble() / mod.samplerate.toDouble()).seconds }
                    }
                    else -> null
                }
                return Info(duration = time, channels = 2)
            } catch (e: Throwable) {
                e.printStackTrace()
                return null
            }
        }

        override suspend fun decodeStreamInternal(data: AsyncStream, props: AudioDecodingProps): AudioStream? {
            val mod = createTracker()
            if (!mod.parse(data.readAll().toNBufferUInt8())) return null
            return mod.createAudioStream()
        }
    }

    var samplerate = 44100
    var playing = false
    var endofsong = false

    abstract fun initialize()
    abstract fun parse(buffer: Uint8Buffer): Boolean
    open fun skip(samples: Int) {
        mix(null, samples)
    }
    abstract fun mix(bufs: Array<FloatArray>?, buflen: Int = bufs?.get(0)?.size ?: 1024)

    fun parseAndInit(buffer: Uint8Buffer) {
        parse(buffer)
        initialize()
        playing = true
    }

    var totalLengthInSamples: Long? = null

    suspend fun createSoundFromFile(file: VfsFile, soundProvider: NativeSoundProvider = nativeSoundProvider): Sound {
        parseAndInit(file.readBytes().toNBufferUInt8())
        return createSound(soundProvider)
    }

    suspend fun createSound(soundProvider: NativeSoundProvider = nativeSoundProvider): Sound {
        return soundProvider.createStreamingSound(createAudioStream())
    }

    fun createAudioStream(): AudioStream {
        playing = true
        var fch = Array(2) { FloatArray(1024) }
        return object : AudioStream(samplerate, 2) {
            override val finished: Boolean get() = endofsong

            // @TODO: we should figure out how to compute the length in samples/time
            override val totalLengthInSamples: Long?
                get() = this@BaseModuleTracker.totalLengthInSamples

            var _currentPositionInSamples: Long = 0L

            private fun skipUntil(newPosition: Long) {
                val startPosition = _currentPositionInSamples
                val seekingTime = measureTime {
                    while (_currentPositionInSamples < newPosition) {
                        val available = newPosition - _currentPositionInSamples
                        val skip = min(available.toInt(), fch[0].size)
                        skip(skip)
                        _currentPositionInSamples += skip
                    }
                }
                LOGGER.warn { "SEEKING from startPosition=$startPosition to newPosition=$newPosition (totalSamples=${newPosition - startPosition}) in $seekingTime" }
            }

            override val currentPositionInSamples: Long get() = _currentPositionInSamples

            override suspend fun seek(position: Duration) {
                val value = estimateSamplesFromTime(position)
                if (_currentPositionInSamples == value) return
                if (value > _currentPositionInSamples) {
                    skipUntil(value)
                } else {
                    //if (value != 0L) error("only supported rewind in MOD value=$value")
                    _currentPositionInSamples = 0L
                    initialize()
                    if (value != 0L) {
                        skipUntil(value)
                    }
                }
            }

            override suspend fun read(out: AudioSamples, offset: Int, length: Int): Int {
                if (fch[0].size < length) fch = Array(2) { FloatArray(length) }
                mix(fch, length)
                _currentPositionInSamples += length
                val l = fch[0]
                val r = fch[1]
                for (n in 0 until length) out.setStereo(offset + n, AudioSample(l[n]), AudioSample(r[n]))
                return length
            }

            override suspend fun clone(): AudioStream {
                return createAudioStream()
            }

        }
    }
}
