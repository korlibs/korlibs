package korlibs.image.tiles

import korlibs.image.bitmap.*
import korlibs.image.color.*
import kotlin.test.*

class TileSetTest {
    @Test
    fun test() {
        val bmp = Bitmap32(32, 32).context2d {
            fill(Colors.RED) { rect(0, 0, 16, 16) }
            fill(Colors.BLUE) { rect(16, 0, 16, 16) }
        }

        TileSet(bmp, 16, 16, border = 0).also {
            assertEquals(4, it.tilesMap.size)
            assertEquals(Colors.RED, it.tilesMap[0]?.slice?.getRgba(0, 0))
            assertEquals(Colors.BLUE, it.tilesMap[1]?.slice?.getRgba(0, 0))

            assertEquals("32x32", it.tilesMap[0]?.slice?.base?.size?.toString())
            assertEquals("RectSlice(null:Rectangle(x=0, y=0, width=16, height=16))", it.tilesMap[0]?.slice?.toString())
            assertEquals("RectSlice(null:Rectangle(x=16, y=0, width=16, height=16))", it.tilesMap[1]?.slice?.toString())
        }

        TileSet(bmp.slice(), 16, 16, border = 1).also {
            assertEquals(4, it.tilesMap.size)
            assertEquals(Colors.RED, it.tilesMap[0]?.slice?.getRgba(0, 0))
            assertEquals(Colors.BLUE, it.tilesMap[1]?.slice?.getRgba(0, 0))

            assertEquals("64x64", it.tilesMap[0]?.slice?.base?.size?.toString())
            assertEquals("RectSlice(null:Rectangle(x=1, y=1, width=16, height=16))", it.tilesMap[0]?.slice?.toString())
            assertEquals("RectSlice(null:Rectangle(x=19, y=1, width=16, height=16))", it.tilesMap[1]?.slice?.toString())
        }
    }
}