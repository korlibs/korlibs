package korlibs.audio.sound

import korlibs.math.geom.*
import korlibs.time.*
import kotlin.time.*

data class PlaybackParameters(
    val times: PlaybackTimes = 1.playbackTimes,
    val startTime: Duration = 0.seconds,
    val bufferTime: Duration = 0.25.seconds,
    override val volume: Double = 1.0,
    override val pitch: Double = 1.0,
    override val panning: Double = 0.0,
    override val position: Vector3 = Vector3.ZERO,
    val onCancel: (() -> Unit)? = null,
    val onFinish: (() -> Unit)? = null,
) : ReadonlySoundProps {
    companion object {
        val DEFAULT = PlaybackParameters(1.playbackTimes, 0.seconds)
    }
}

val infinitePlaybackTimes get() = PlaybackTimes.INFINITE
inline val Int.playbackTimes get() = PlaybackTimes(this)

inline class PlaybackTimes(val count: Int) {
    companion object {
        val ZERO = PlaybackTimes(0)
        val ONE = PlaybackTimes(1)
        val INFINITE = PlaybackTimes(-1)
    }
    val hasMore get() = this != ZERO
    val oneLess get() = if (this == INFINITE) INFINITE else PlaybackTimes(count - 1)
    override fun toString(): String = if (count >= 0) "$count times" else "Infinite times"
}
