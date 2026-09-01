package korlibs.image.format

import korlibs.image.atlas.AtlasPacker
import korlibs.image.atlas.MutableAtlasUnit
import korlibs.image.bitmap.bounds
import korlibs.image.doTest
import korlibs.io.file.std.resourcesVfs
import korlibs.math.geom.SizeInt
import kotlin.test.Test
import kotlin.test.assertEquals

class BmpSliceTest {
    val props = ImageDecodingProps(format = ImageFormats(PNG))

    @Test
    fun testName() = doTest {
        val slice = resourcesVfs["rgba.png"].readBitmapSlice(name = "hello", props = props)
        assertEquals("hello", slice.name)
        assertEquals(SizeInt(4, 1), slice.bounds.size)
    }

    @Test
    fun testPacking() = doTest {
        val atlas = AtlasPacker.pack(listOf(
            resourcesVfs["rgba.png"].readBitmapSlice(name = "hello", props = props)
        ))
        val slice = atlas["hello"]
        assertEquals("hello", slice.name)
        assertEquals(SizeInt(4, 1), slice.bounds.size)
    }

    @Test
    fun testPackingMutable() = doTest {
        val atlas = MutableAtlasUnit()
        resourcesVfs["rgba.png"].readBitmapSlice(atlas = atlas, name = "hello", props = props)
        val slice = atlas["hello"]
        assertEquals("hello", slice.name)
        assertEquals(SizeInt(4, 1), slice.bounds.size)
    }
}
