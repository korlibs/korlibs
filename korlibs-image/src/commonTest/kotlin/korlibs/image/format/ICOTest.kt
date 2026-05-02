package korlibs.image.format

import korlibs.io.async.suspendTest
import kotlinx.coroutines.test.*
import kotlin.test.Test

class ICOTest {
    @Test
    fun test() = runTest {
        //rootLocalVfs["/tmp/demo.ico"].writeBytes(Bitmap32(32, 32, Colors.RED, premultiplied = false).encode(ICO))
    }
}
