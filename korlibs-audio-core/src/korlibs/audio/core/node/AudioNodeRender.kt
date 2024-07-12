package korlibs.audio.core.node

import korlibs.audio.core.*
import korlibs.math.*

fun AudioNode.render(out: AudioBuffer = AudioBuffer(nchannels, durationSamples.toIntClamp(), rate)): AudioBuffer {
    this.currentSamples = 0L
    this.process(out)
    return out
}
