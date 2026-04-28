package korlibs.image.tiles

import korlibs.datastructure.*
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.math.geom.*
import korlibs.math.geom.collider.*
import korlibs.math.geom.shape.*
import korlibs.time.*
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

    @Test
    fun testPreserveFramesAndCollision() {
        val frames = listOf(TileSetAnimationFrame(0, 0.1.fastSeconds))
        val collision = TileShapeInfoImpl(HitTestDirectionFlags.NONE, Rectangle(0, 0, 16, 16).toShape2D(), Matrix())
        for (border in listOf(0, 1)) {
            val tileSet = TileSet(intMapOf(
                1 to TileSetTileInfo(1, Bitmap32(64, 24, Colors.RED.premultiplied).slice(), frames = frames, collision = collision),
                2 to TileSetTileInfo(2, Bitmap32(16, 16, Colors.BLUE.premultiplied).slice()),
            ), border = border)

            //println(tileSet.getInfo(0))
            //println(tileSet.getInfo(1))
            //println(tileSet.getInfo(2))

            assertEquals(null, tileSet.getInfo(0))

            tileSet.getInfo(1).also {
                assertEquals(1, it?.id)
                assertEquals("64x24", it?.slice?.sizeString)
                assertEquals(frames, it?.frames)
                assertEquals(collision, it?.collision)
            }

            tileSet.getInfo(2).also {
                assertEquals(2, it?.id)
                assertEquals("16x16", it?.slice?.sizeString)
                assertEquals(emptyList(), it?.frames)
                assertEquals(null, it?.collision)
            }
        }
    }
}