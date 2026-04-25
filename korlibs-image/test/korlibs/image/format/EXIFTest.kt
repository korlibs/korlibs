package korlibs.image.format

import korlibs.image.*
import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlinx.coroutines.test.*
import kotlin.test.Test
import kotlin.test.assertEquals

class EXIFTest {
    @Test
    fun test() = doTest {
        val exif = EXIF.readExifFromJpeg(resourcesVfs["Portrait_3.jpg"])
        assertEquals(ImageOrientation.ROTATE_180, exif.orientation)
    }
}
