package korlibs.io.file.std

import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class PreferSyncIoTest {
    @Test
    fun testSyncIO() = korlibs.io.async.suspendTest({ Platform.isJvm }, preferSyncIo = true) {
        val exception = assertFails { resourcesVfs["unexistant.unk"].readBytes() }
        assertTrue("All stack entries available because executed synchronously") {
            "korlibs.io.file.std.BaseLocalVfsJvm" in exception.stackTraceToString()
        }
    }

    @Test
    fun testAsyncIO() = korlibs.io.async.suspendTest({ Platform.isJvm }, preferSyncIo = false) {
        val exception = assertFails { resourcesVfs["unexistant.unk"].readBytes() }
        assertTrue("Some stack entries lost in the async stacktrace") {
            "korlibs.io.file.std.BaseLocalVfsJvm" !in exception.stackTraceToString()
        }
    }
}
