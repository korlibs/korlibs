package korlibs.image.tiles

import korlibs.math.geom.slice.*

/**
 * Creates a [Tile] with the specified [tile] number, [orientation], [offsetX], and [offsetY].
 *
 * @param tile The tile number.
 * @param orientation The orientation of the tile. Defaults to [SliceOrientation.NORMAL].
 * @param offsetX The X offset of the tile. Defaults to 0.
 * @param offsetY The Y offset of the tile. Defaults to 0.
 * @return A new [Tile] with the specified properties.
 */
fun Tile(tile: Int, orientation: SliceOrientation = SliceOrientation.NORMAL, offsetX: Int = 0, offsetY: Int = 0): Tile =
    Tile(tile, offsetX, offsetY, orientation.tileFlipX, orientation.tileFlipY, orientation.tileRot)

/**
 * Returns a new [Tile] with the specified [orientation].
 *
 * @param orientation The new orientation for the tile.
 * @return A new [Tile] with the specified orientation.
 */
fun Tile.withOrientation(orientation: SliceOrientation): Tile = Tile(tile, orientation, offsetX, offsetY)

/**
 * Returns a new [Tile] that is flipped horizontally.
 *
 * @return A new [Tile] that is flipped horizontally.
 */
fun Tile.flippedX(): Tile = withOrientation(orientation.flippedX())

/**
 * Returns a new [Tile] that is flipped vertically.
 *
 * @return A new [Tile] that is flipped vertically.
 */
fun Tile.flippedY(): Tile = withOrientation(orientation.flippedY())

/**
 * Returns a new [Tile] that is rotated to the right by the specified [offset].
 *
 * @param offset The number of 90-degree rotations to the right. Defaults to 1.
 * @return A new [Tile] that is rotated to the right by the specified offset.
 */
fun Tile.rotatedRight(offset: Int = 1): Tile = withOrientation(orientation.rotatedRight(offset))

/**
 * Returns a new [Tile] that is rotated to the left by the specified [offset].
 *
 * @param offset The number of 90-degree rotations to the left. Defaults to 1.
 * @return A new [Tile] that is rotated to the left by the specified offset.
 */
fun Tile.rotatedLeft(offset: Int = 1): Tile = withOrientation(orientation.rotatedLeft(offset))

/**
 * Converts the [Tile] to a string representation.
 *
 * @return A string representation of the tile.
 */
fun Tile.toOrientationString(): String = "Tile($tile, $orientation, $offsetX, $offsetY)"

/**
 * Extension property to determine if the [SliceOrientation] represents a horizontal flip.
 */
val SliceOrientation.tileFlipX: Boolean
    get() = raw == 1 || raw == 2 || raw == 4 || raw == 5

/**
 * Extension property to determine if the [SliceOrientation] represents a vertical flip.
 */
val SliceOrientation.tileFlipY: Boolean
    get() = raw == 2 || raw == 3 || raw == 5 || raw == 6

/**
 * Extension property to determine if the [SliceOrientation] represents a rotation.
 */
val SliceOrientation.tileRot: Boolean
    get() = raw % 2 == 1

/**
 * Extension property to get the [SliceOrientation] of the [Tile].
 */
val Tile.orientation: SliceOrientation
    get() = when {
        flipY -> when {
            flipX -> if (rotate) SliceOrientation.MIRROR_HORIZONTAL_ROTATE_90 else SliceOrientation.ROTATE_180
            else -> if (rotate) SliceOrientation.ROTATE_270 else SliceOrientation.MIRROR_HORIZONTAL_ROTATE_180
        }
        else -> when {
            flipX -> if (rotate) SliceOrientation.ROTATE_90 else SliceOrientation.MIRROR_HORIZONTAL_ROTATE_0
            else -> if (rotate) SliceOrientation.MIRROR_HORIZONTAL_ROTATE_270 else SliceOrientation.ROTATE_0
        }
    }