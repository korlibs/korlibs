package korlibs.image.format

import kotlin.test.Test
import kotlin.test.assertEquals

class ImageFormatsMutableTest {
    @Test
    fun test() {
        val mut = ImageFormatsMutable()
        mut.register(mut)
        assertEquals("ImageFormats(0)[]", mut.toString())
    }
}
