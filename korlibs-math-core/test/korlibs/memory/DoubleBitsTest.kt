package korlibs.memory

import korlibs.platform.*
import kotlin.random.*
import kotlin.test.*
import kotlin.time.*

class DoubleBitsTest {
    @Test
    fun test() {
        Double.fromLowHigh(0x3FFFFFF, 0x7654321).getLowHighBits { low, high ->
            assertEquals(0x3FFFFFF, low)
            assertEquals(0x7654321, high)
        }
        Double.fromLowHigh(0x3FFFFFF, 0x7654321).also {
            assertEquals(0x3FFFFFF, it.low)
            assertEquals(0x7654321, it.high)
        }
        1.0.also {
            assertEquals(0, it.low)
            assertEquals(1072693248, it.high)
        }
        assertEquals(1.0, Double.fromLowHigh(0, 1072693248))

        //println(double)
    }

    @Test
    @Ignore
    fun testBenchmark() {
        // jvm-MACOSX-ARM64-RELEASE[9] : DOUBLE: 4.018583ms, LONG: 3.976833ms
        // wasm-web[9] : DOUBLE: 9.5ms, LONG: 10.4ms
        // native-MACOSX-ARM64-DEBUG[9] : DOUBLE: 26.941875ms, LONG: 95.055084ms
        // js-web[9] : DOUBLE: 175.4ms, LONG: 573.6ms <--- with DataView
        // js-web[9] : DOUBLE: 42.8ms, LONG: 598.2ms <--- with Float64Array + Int32Array

        fun doubleLoop(data: DoubleArray, m: Int) {
            for (n in data.indices) {
                data[n] = Double.fromLowHigh((n + m) * 77777, (n + m) * 99999)
            }
        }
        fun longLoop(data: LongArray, m: Int) {
            for (n in data.indices) {
                data[n] = Long.fromLowHigh((n + m) * 77777, (n + m) * 99999)
            }
        }
        val data = DoubleArray(10000000)
        val ldata = LongArray(10000000)

        repeat(20) { M ->
            val doubleTime = measureTime { doubleLoop(data, M) }
            val longTime = measureTime { longLoop(ldata, M) }

            println("${Platform.rawPlatformName}[$M] : DOUBLE: $doubleTime, LONG: $longTime")
            //println("${Platform.rawPlatformName}[$M] : DOUBLE: $doubleTime")
        }
    }

    @Test
    fun testDoubleParts() {
        assertEquals(1024.0, Double.fromParts(sign=0, exponent=0b10000001001, 0b0000000000000000000000000000000000000000000000000000))
        assertEquals(-1024.0, Double.fromParts(sign=1, exponent=0b10000001001, 0b0000000000000000000000000000000000000000000000000000))
        assertEquals(1536.0, Double.fromParts(sign=0, exponent=0b10000001001, mantissa=0b1000000000000000000000000000000000000000000000000000))
        assertEquals(0.0, Double.fromParts(sign=0, exponent=0b00000000000, mantissa=0b0000000000000000000000000000000000000000000000000000))
        assertEquals(1.0, Double.fromParts(sign=0, exponent=0b01111111111, mantissa=0b0000000000000000000000000000000000000000000000000000))
        assertEquals(3.0, Double.fromParts(sign=0, exponent=0b10000000000, mantissa=0b1000000000000000000000000000000000000000000000000000))
        assertEquals(7.76421, Double.fromParts(sign=0, exponent=0b10000000001, mantissa=0b1111000011101000110100010000111101010001101011001010))
        assertEquals(Double.NEGATIVE_INFINITY, Double.fromParts(1, -1, 0L))
        assertEquals(Double.POSITIVE_INFINITY, Double.fromParts(0, -1, 0L))
        assertEquals(Double.NaN, Double.fromParts(1, -1, 1L))
        assertEquals(Double.NaN, Double.fromParts(0, -1, 1L))
    }

    @Test
    fun testEdgeNanValues() {
        val random = Random(0L)

        0xfffa9138acdc4aa6uL.toLong().also { invalidLong ->
            val invalidDouble = Double.fromBits(invalidLong)
            assertEquals(invalidLong.low, invalidDouble.low)
            assertEquals(invalidLong.high, invalidDouble.high)
        }

        repeat(100000) {
            val sign = 0
            val exponent = 0b01111111111
            val mantissaLow = random.nextBits(32)
            val mantissaHigh = random.nextBits(20)
            val double = Double.fromParts(sign, exponent, mantissaLow, mantissaHigh)
            assertEquals(sign, double.bitsSign)
            assertEquals(exponent, double.bitsExponent)
            assertEquals(mantissaLow, double.bitsMantissaLow)
            assertEquals(mantissaHigh, double.bitsMantissaHigh)
        }
        repeat(100000) {
            val long = random.nextLong()
            val low = long.low
            val high = long.high
            val double = Double.fromLowHigh(low, high)
            //println("long=0x${long.toULong().toString(16).padStart(16, '0')}, double=$double")
            assertEquals(low, double.low)
            assertEquals(high, double.high)
            /*
            val double = long.reinterpretAsDouble()
            val nlong = double.reinterpretAsLong()
            assertEquals(long, nlong)
            */
        }
    }
}