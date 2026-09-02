package korlibs.image.format

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlin.test.Test
import kotlin.test.assertEquals

class KRAJvmTest {
    @Test
    fun test() = suspendTest {
        val output = resourcesVfs["krita.kra"].readImageData(ImageDecodingProps(format = KRA).also {
            //it.kritaPartialImageLayers = true
            it.kritaPartialImageLayers = false
            it.kritaLoadLayers = true
        })
        assertEquals(4, output.frames.size)
        //output.showImagesAndWait()
    }
}
