package korlibs.audio.sound

import korlibs.math.*

//inline class AudioAmplitude(val value: Short)

internal fun AudioSamples.resample(scale: Double, totalSamples: Int = (this.totalSamples * scale).toInt(), out: AudioSamples = AudioSamples(channels, totalSamples)): AudioSamples {
    val iscale = 1.0 / scale
    for (c in 0 until channels) {
        val inpc = this[c]
        val outc = out[c]
        for (n in 0 until totalSamples) {
            // @TODO: Increase quality
            outc[n] = inpc[(n * iscale).toInt()]
        }
    }
    return out
}

fun AudioSamples.resample(srcFreq: Int, dstFreq: Int): AudioSamples =
    resample(dstFreq.toDouble() / srcFreq.toDouble())

fun AudioSamples.resampleIfRequired(srcFreq: Int, dstFreq: Int): AudioSamples =
    if (srcFreq == dstFreq) this else resample(srcFreq, dstFreq)


fun AudioSamples.interleaved(out: AudioSamplesInterleaved = AudioSamplesInterleaved(channels, totalSamples)): AudioSamplesInterleaved {
    check(out.data.size >= totalSamples * channels)
    when (channels) {
        1 -> arraycopy(this.data[0], 0, out.data, 0, totalSamples)
        2 -> arrayinterleave(
            out.data, 0,
            this.data[0], 0,
            this.data[1], 0,
            totalSamples,
        )
        else -> {
            val outData = out.data
            val channels = channels
            for (c in 0 until channels) {
                var m = c
                for (n in 0 until totalSamples) {
                    outData[m] = this[c, n]
                    m += channels
                }
            }
        }
    }
    return out
}

fun AudioSamplesInterleaved.applyProps(speed: Double, panning: Double, volume: Double): AudioSamplesInterleaved {
    if (speed == 1.0 && panning == 0.0 && volume == 1.0) return this
    val speedf = speed.toFloat()
    val ispeedf = (1.0 / speed).toFloat()
    val out = AudioSamplesInterleaved(channels, (totalSamples * ispeedf).toInt())

    val rratio = ((((panning + 1.0) / 2.0).clamp01()) * volume).toFloat()
    val lratio = ((1.0 - rratio) * volume).toFloat()

    val outData = out.data
    var m = 0
    if (channels == 2) {
        for (n in 0 until out.totalSamples) {
            outData[m++] = outData[(n * speedf).toInt() * 2 + 0] * lratio
            outData[m++] = outData[(n * speedf).toInt() * 2 + 1] * rratio
        }
    } else {
        for (n in 0 until out.data.size) {
            outData[m++] = outData[(n * speedf).toInt()] * lratio
        }
    }

    return out
}

fun AudioSamplesInterleaved.ensureTwoChannels(): AudioSamplesInterleaved {
    return when (channels) {
        2 -> this
        else -> {
            AudioSamplesInterleaved(2, this.totalSamples).also { out ->
                val inp = this@ensureTwoChannels
                var m = 0
                val ichannels = inp.channels
                val odata = out.data
                val idata = inp.data
                for (n in 0 until out.totalSamples) {
                    val v = idata[n * ichannels]
                    odata[m++] = v
                    odata[m++] = v
                }
            }
        }
    }
}

fun AudioSamplesInterleaved.separated(out: AudioSamples = AudioSamples(channels, totalSamples)): AudioSamples {
    for (n in 0 until totalSamples) for (c in 0 until channels) out[c, n] = this[c, n]
    return out
}

private fun arrayinterleave(
    out: AudioSampleArray, outPos: Int,
    array1: AudioSampleArray, array1Pos: Int,
    array2: AudioSampleArray, array2Pos: Int,
    size: Int,
) {
    var m = outPos
    for (n in 0 until size) {
        out[m++] = array1[array1Pos + n]
        out[m++] = array2[array2Pos + n]
    }
}
