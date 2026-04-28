package korlibs.ffi

import korlibs.memory.*
import korlibs.platform.*
import kotlin.test.*

class FFITest {
    @Test
    fun test() {
        if (!FFILib.isFFISupported) return
        if (Platform.isLinux) return // Disable linux for now. libc is not loaded correctly
        LIBC().use { libc ->
            assertEquals(1f, libc.cosf(0f))
            val ptr = libc.malloc(100)
            ptr?.set32(100, 0)
            assertEquals(100, ptr?.getS32(0))
            libc.free(ptr)

            val b1 = Buffer(5, true)
            b1.setArray(0, byteArrayOf(10, 20, 30, 40, 50))
            val b2 = Buffer(5, true)
            val b3 = Buffer(5, true)
            libc.memcpy(b2.pointer, b1.pointer, 5)
            arraycopy(b2.pointer, 0, b3.pointer, 0, 5)

            assertEquals(10, b2.getS8(0))
            assertEquals(20, b2.getS8(1))
            assertEquals(30, b2.getS8(2))
            assertEquals(40, b2.getS8(3))
            assertEquals(50, b2.getS8(4))

            assertEquals(10, b3.getS8(0))
            assertEquals(20, b3.getS8(1))
            assertEquals(30, b3.getS8(2))
            assertEquals(40, b3.getS8(3))
            assertEquals(50, b3.getS8(4))
        }
    }
}

class LIBC : FFILib(
    when {
        Platform.isWindows -> "msvcrt.dll"
        Platform.isMac -> "libSystem.dylib"
        else -> "libc"
    }
) {
    val cosf by func<(Float) -> Float>()
    val malloc by func<(Int) -> FFIPointer?>()
    val memcpy by func<(dst: FFIPointer?, src: FFIPointer?, size: Int) -> FFIPointer?>()
    val free by func<(FFIPointer?) -> Unit>()
}
