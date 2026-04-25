package korlibs.image.tiles

import korlibs.math.geom.slice.*
import kotlin.test.*

class TileTest {
    @Test
    @Ignore
    fun testFromRaw() {
        Tile.fromRaw(-1, -1).also { tile ->
            assertEquals(-1, tile.rawLow)
            assertEquals(-1, tile.rawHigh)
            assertEquals(-1, tile.data)
        }
        Tile.fromRaw(-1234567, 78912345).also { tile ->
            assertEquals(-1234567, tile.rawLow)
            assertEquals(78912345, tile.rawHigh)
            assertEquals(-1234567, tile.data)
        }
    }

    @Test
    fun testFromConstructor() {
        assertEquals(
            "Tile(tile=12345, offsetX=-16384, offsetY=16383, flipX=true, flipY=false, rotate=true)",
            Tile(12345, offsetX = -16384, offsetY = 16383, flipX = true, flipY = false, rotate = true).toStringInfo()
        )
        assertEquals(
            "Tile(tile=524287, offsetX=16383, offsetY=-16384, flipX=false, flipY=true, rotate=false)",
            Tile(524287, offsetX = 16383, offsetY = -16384, flipX = false, flipY = true, rotate = false).toStringInfo()
        )
    }

    @Test
    fun testTileValid() {
        assertEquals(true, Tile(1, SliceOrientation.VALUES[0], offsetY = -4).isValid)
        assertEquals(false, Tile.INVALID.isValid)

        assertEquals(false, Tile(1, SliceOrientation.VALUES[0], offsetY = -4).isInvalid)
        assertEquals(true, Tile.INVALID.isInvalid)
    }
}
