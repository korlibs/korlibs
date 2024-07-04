package korlibs.ffi

import korlibs.platform.*
import kotlin.test.*

class FFITest {
    @Test
    fun test() {
        if (!FFILib.isFFISupported) return
        LIBC().use { libc ->
            assertEquals(1f, libc.cosf(0f))
            val ptr = libc.malloc(100)
            ptr?.set32(100, 0)
            assertEquals(100, ptr?.getS32(0))
            libc.free(ptr)
        }
    }
}

class LIBC : FFILib(
    when {
        Platform.isWindows -> "msvcrt.dll"
        Platform.isMac -> "libSystem.dylib"
        else -> "libc.so.6"
    }
) {
    val cosf by func<(Float) -> Float>()
    val malloc by func<(Int) -> FFIPointer?>()
    val free by func<(FFIPointer?) -> Unit>()
}
