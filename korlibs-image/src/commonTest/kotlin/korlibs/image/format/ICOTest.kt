package korlibs.image.format

import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class ICOTest {
    @Test
    fun test() = runTest {
        //rootLocalVfs["/tmp/demo.ico"].writeBytes(Bitmap32(32, 32, Colors.RED, premultiplied = false).encode(ICO))
    }
}
