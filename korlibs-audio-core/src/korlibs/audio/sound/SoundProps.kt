package korlibs.audio.sound

import korlibs.datastructure.*
import korlibs.math.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.time.*

interface SoundListenerProps {
    val listenerPosition: Vector3 get() = Vector3.ZERO
    val listenerOrientationAt: Vector3 get() = Vector3.ZERO
    val listenerOrientationUp: Vector3 get() = Vector3.ZERO
    val listenerSpeed: Vector3 get() = Vector3.ZERO
}

interface ReadonlySoundProps {
    val volume: Double
    @Deprecated("")
    val pitch: Double get() = 1.0
    val panning: Double
    val position: Vector3
}

interface SoundProps : Extra, ReadonlySoundProps {
    override var volume: Double
    @Deprecated("")
    override var pitch: Double
        get() = 1.0
        set(value) = Unit
    override var panning: Double
    override var position: Vector3

    object Dummy : SoundProps, Extra by Extra.Mixin() {
        override var volume: Double
            get() = 1.0
            set(v) = Unit
        override var pitch: Double
            get() = 1.0
            set(v) = Unit
        override var panning: Double
            get() = 0.0
            set(v) = Unit
        override var position: Vector3 = Vector3.ZERO
    }
}

fun ReadonlySoundProps.volumeForChannel(listener: SoundListenerProps, channel: Int): Float {
    // @TODO: Calculate volume based on distance and panning
    val distance = listener.listenerPosition.distanceTo(position)
    return when (channel) {
        0 -> panning.toFloat().convertRangeClamped(-1f, 0f, 0f, 1f)
        else -> 1f - panning.toFloat().convertRangeClamped(0f, 1f, 0f, 1f)
    }
}

fun ReadonlySoundProps.applyPropsTo(listener: SoundListenerProps, samples: AudioSamplesInterleaved) {
    for (ch in 0 until samples.channels) {
        val volume01 = volumeForChannel(listener, ch) * this.volume.toFloat()
        //println("PROPS: volume=${this.volume}, volumeForChannel(ch=$ch)=$volume01")
        for (n in 0 until samples.totalSamples) {
            var sample = samples[ch, n]
            sample *= volume01
            samples[ch, n] = sample
        }
    }
}
