package korlibs.io.file.std

import korlibs.io.async.suspendTest
import korlibs.platform.Platform
import kotlin.test.Test

class LocalVfsAppendTest {
    @Test
    fun test() = suspendTest(cond = { localVfs(StandardPaths.temp).vfs is LocalVfs }) {
        AppendBaseTest._testAppendVfs(localVfs(StandardPaths.temp)["file.append.${Platform.rawPlatformName}.txt"])
    }
}
