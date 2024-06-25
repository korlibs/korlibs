package korlibs.memory

import korlibs.platform.*
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
}