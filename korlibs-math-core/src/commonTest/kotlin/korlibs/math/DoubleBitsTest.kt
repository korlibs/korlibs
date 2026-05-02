package korlibs.math

import korlibs.memory.*
import kotlin.random.*
import kotlin.test.*

class DoubleBitsTest {
    @Test
    fun test() {
        Double.fromLowHigh(0x3FFFFFF, 0x7654321).getLowHighBits { low, high ->
            assertEquals(0x3FFFFFF, low)
            assertEquals(0x7654321, high)
        }
        Double.fromLowHigh(0x3FFFFFF, 0x7654321).also {
            assertEquals(0x3FFFFFF, it.lowBits)
            assertEquals(0x7654321, it.highBits)
        }
        1.0.also {
            assertEquals(0, it.lowBits)
            assertEquals(1072693248, it.highBits)
        }
        assertEquals(1.0, Double.fromLowHigh(0, 1072693248))

        //println(double)
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
            assertEquals(invalidLong.low, invalidDouble.lowBits)
            assertEquals(invalidLong.high, invalidDouble.highBits)
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
            assertEquals(low, double.lowBits)
            assertEquals(high, double.highBits)
            /*
            val double = long.reinterpretAsDouble()
            val nlong = double.reinterpretAsLong()
            assertEquals(long, nlong)
            */
        }
    }
}