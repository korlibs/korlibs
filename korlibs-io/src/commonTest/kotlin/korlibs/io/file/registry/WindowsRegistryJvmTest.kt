package korlibs.io.file.registry

import korlibs.io.async.suspendTest
import kotlin.test.Test

class WindowsRegistryJvmTest {
    @Test
    fun testRegistry() = suspendTest({ WindowsRegistry.isSupported }) {
    }
}
