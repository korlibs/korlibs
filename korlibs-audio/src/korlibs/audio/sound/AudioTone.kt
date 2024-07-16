package korlibs.audio.sound

import korlibs.time.*
import kotlin.math.*
import kotlin.time.*

@Deprecated("")
object AudioTone {
    /**
     * Generates a tone with the specified [frequency] and [length].
     *
     * The []frequency] For example, 440.0 is the A4 note.
     * The audible frequency range for humans is typically given as being between about 20 Hz and 20,000 Hz (20 kHz),
     * although the upper hearing limit decreases with age.
     */
    fun generate(length: Duration, frequency: Double, rate: Int = 44100, volume: Double = 1.0): AudioData {
        val nsamples = (rate * length.seconds).toInt()
        val samples = AudioSamples(1, nsamples)
        val samples0 = samples[0]
        val volumef = volume.toFloat()
        for (n in 0 until nsamples) {
            val ratio = (n.toDouble() * frequency) / rate
            val sample = sin(ratio * PI * 2)
            samples0[n] = AudioSample(sample.toFloat() * volumef)
        }
        return AudioData(rate, samples)
    }
}
