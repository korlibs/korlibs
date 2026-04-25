package korlibs.image.tiles

import korlibs.datastructure.*
import korlibs.image.bitmap.*
import korlibs.math.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.math.*
import kotlin.time.*

data class TileSetAnimationFrame(
    val tileId: Int,
    val fastDuration: FastDuration,
) : Extra by Extra.Mixin() {
    val duration get() = fastDuration.toDuration()

    companion object {
        @Deprecated("", ReplaceWith("TileSetAnimationFrame(tileId, duration.fast)", "korlibs.image.tiles.TileSetAnimationFrame", "korlibs.time.fast"))
        operator fun invoke(tileId: Int, duration: Duration): TileSetAnimationFrame = TileSetAnimationFrame(tileId, duration.fast)
    }
}

data class TileSetTileInfo(
    val id: Int,
    val slice: BmpSlice,
    val frames: List<TileSetAnimationFrame> = emptyList(),
    val collision: TileShapeInfo? = null,
) : Extra by Extra.Mixin() {
    val width get() = slice.width
    val height get() = slice.height
    val name: String? get() = slice.name
}

/**
 * A set of [BmpSlice] where all share a [width] and [height].
 * For performance’s sake, ideally, all the slices should be part of the same [Bitmap].
 *
 * Used along [TileMapData] to represent tiles.
 */
class TileSet private constructor(
    val tilesMap: IntMap<TileSetTileInfo>,
	//val textures: List<BmpSlice?>,

    /** [width] of each tile */
    val width: Int = if (tilesMap.size == 0) 0 else tilesMap.firstValue().slice.width,
    /** [height] of each tile */
    val height: Int = if (tilesMap.size == 0) 0 else tilesMap.firstValue().slice.height,
    unit: Unit
) {
    val tileSize: SizeInt get() = SizeInt(width, height)

    override fun toString(): String = "TileSet(size=${width}x$height, tiles=${tilesMap.keys.toList()})"

    val base: Bitmap by lazy { if (tilesMap.size == 0) Bitmaps.transparent.bmp else tilesMap.firstValue().slice.bmp }
    val hasMultipleBaseBitmaps by lazy { tilesMap.values.any { it !== null && it.slice.bmp !== base } }
    val infos by lazy { Array<TileSetTileInfo?>(tilesMap.keys.maxOrNull()?.plus(1) ?: 0) { tilesMap[it] } }
    val textures by lazy { Array<BitmapCoords?>(tilesMap.keys.maxOrNull()?.plus(1) ?: 0) { tilesMap[it]?.slice } }
	//init { if (hasMultipleBaseBitmaps) throw RuntimeException("All tiles in the set must have the same base texture") }

    //init {
    //    println("texturesMap: ${texturesMap.toMap()}")
    //    println("textures: ${textures.size}")
    //}

    fun getInfo(index: Int): TileSetTileInfo? = infos.getOrNull(index)
    fun getSlice(index: Int): BmpSlice? = getInfo(index)?.slice
	operator fun get(index: Int): BmpSlice? = getSlice(index)

    fun clone(): TileSet = TileSet(this.tilesMap.clone(), this.width, this.height, Unit)

	companion object {
        val EMPTY = TileSet(IntMap(), unit = Unit)

        operator fun invoke(
            tilesMap: IntMap<TileSetTileInfo>,
            width: Int = if (tilesMap.size == 0) 0 else tilesMap.firstValue().slice.width,
            height: Int = if (tilesMap.size == 0) 0 else tilesMap.firstValue().slice.height,
            border: Int = 1,
            mipmaps: Boolean = false,
        ): TileSet {
            if (border == 0 && !mipmaps) return TileSet(tilesMap, width, height, unit = Unit)
            return fromTileSetTileInfo(width, height, tilesMap.values.mapNotNull { it }, border = border, mipmaps = mipmaps)
        }

        operator fun invoke(
            tiles: List<TileSetTileInfo>,
            width: Int = tiles.first().slice.width,
            height: Int = tiles.first().slice.height,
            border: Int = 1,
            mipmaps: Boolean = false,
        ): TileSet = fromTileSetTileInfo(width, height, tiles, border, mipmaps = mipmaps)

        operator fun invoke(
            vararg tiles: TileSetTileInfo,
            width: Int = tiles.first().slice.width,
            height: Int = tiles.first().slice.height,
            border: Int = 1,
            mipmaps: Boolean = false,
        ): TileSet = invoke(tiles.toList(), width, height, border, mipmaps)

        operator fun invoke(
            base: Bitmap,
            tileWidth: Int = base.width,
            tileHeight: Int = base.height,
            columns: Int = -1,
            totalTiles: Int = -1,
            idOffset: Int = 0,
            border: Int = 1,
            mipmaps: Boolean = false,
        ): TileSet = invoke(base.slice(), tileWidth, tileHeight, columns, totalTiles, idOffset, border, mipmaps)

		operator fun invoke(
            base: BmpSlice,
            tileWidth: Int = base.width,
            tileHeight: Int = base.height,
            columns: Int = -1,
            totalTiles: Int = -1,
            idOffset: Int = 0,
            border: Int = 1,
            mipmaps: Boolean = false,
		): TileSet {
			val out = arrayListOf<TileSetTileInfo>()
			val rows = base.height / tileHeight
			val actualColumns = if (columns < 0) base.width / tileWidth else columns
			val actualTotalTiles = if (totalTiles < 0) rows * actualColumns else totalTiles

			complete@ for (y in 0 until rows) {
				for (x in 0 until actualColumns) {
					out += TileSetTileInfo(out.size + idOffset, base.sliceWithSize(x * tileWidth, y * tileHeight, tileWidth, tileHeight))
					if (out.size >= actualTotalTiles) break@complete
				}
			}

			return fromTileSetTileInfo(tileWidth, tileHeight, out, border = border, mipmaps = mipmaps)
		}

        fun fromBitmaps(
            tilewidth: Int,
            tileheight: Int,
            bitmaps: List<Bitmap>,
            border: Int = 1,
            mipmaps: Boolean = false,
            idOffset: Int = 0,
        ): TileSet {
            return fromBitmapSlices(tilewidth, tileheight, bitmaps.map { it.slice() }, border = border, mipmaps = mipmaps, idOffset = idOffset)
        }

        fun fromBitmapSlices(
            tilewidth: Int,
            tileheight: Int,
            bmpSlices: List<BmpSlice>,
            border: Int = 1,
            mipmaps: Boolean = false,
            idOffset: Int = 0,
        ): TileSet = fromTileSetTileInfo(tilewidth, tileheight, bmpSlices.withIndex().map { (index, it) -> TileSetTileInfo(index + idOffset, it) }, border, mipmaps)

		fun fromTileSetTileInfo(
            tilewidth: Int,
            tileheight: Int,
            bmpSlices: List<TileSetTileInfo>,
            border: Int = 1,
            mipmaps: Boolean = false,
        ): TileSet {
			//check(bmpSlices.all { it.width == tilewidth && it.height == tileheight })

			if (bmpSlices.isEmpty()) return TileSet(IntMap(), tilewidth, tileheight, unit = Unit)

            // NO-Border
            if (border == 0) {
                return TileSet(
                    tilesMap = IntMap<TileSetTileInfo>(bmpSlices.size).also { for (slice in bmpSlices) it[slice.id] = slice },
                    width = tilewidth,
                    height = tileheight,
                    unit = Unit
                )
            }

			//sqrt(bitmaps.size.toDouble()).toIntCeil() * tilewidth

			val border2 = border * 2
			val btilewidth = tilewidth + border2
			val btileheight = tileheight + border2
            val columns = sqrt(bmpSlices.size.toDouble()).toIntCeil()
            val rows = (bmpSlices.size.toDouble() / columns).toIntCeil()
            val minWidth = columns * (tilewidth + border) + border
            val minHeight = rows * (tileheight + border) + border
            val potSize = maxOf(minWidth.nextPowerOfTwo, minHeight.nextPowerOfTwo)

            val premultiplied = bmpSlices.any { it.slice.base.premultiplied }

			val out = Bitmap32(potSize, potSize, premultiplied = premultiplied).mipmaps(mipmaps)
			val texs = IntMap<TileSetTileInfo>()

            val bmps = FastIdentityMap<Bitmap, Bitmap32>()

            fun BmpSlice.ensureBMP32(): BmpSlice32 {
                val base2 = bmps.getOrPut(this.base) { base.toBMP32IfRequired() }
                if (base === base2) return this as BmpSlice32
                return BitmapSlice(base2, this.rect, orientation, padding, name)
            }

			//val tex = views.texture(out, mipmaps = mipmaps)
            for (n in bmpSlices.indices) {
                val y = n / columns
                val x = n % columns
                val px = x * btilewidth + border
                val py = y * btileheight + border
                //out.putSliceWithBorder(px, py, bmpSlices[n], border)
                val origSlice = bmpSlices[n]
                out.put(origSlice.slice.ensureBMP32(), px, py)
                //println("putSliceWithBorder=${bmpSlices[n]}")

                val tileInfo = origSlice.copy(slice = out.sliceWithSize(px, py, origSlice.width, origSlice.height, name = origSlice.name),)
                //println("tileInfo=$tileInfo")
                texs[tileInfo.id] = tileInfo
            }

            // Create borders Columns
            for (x in 0 until columns) {
                val px = x * btilewidth + border
                for (b in 0 until border) {
                    Bitmap32.copyRect(out, px, 0, out, px - 1 - b, 0, 1, out.height)
                    Bitmap32.copyRect(out, px + tilewidth - 1, 0, out, px + tilewidth + b, 0, 1, out.height)
                }
            }
            // Create borders Rows
            for (y in 0 until rows) {
                val py = y * btileheight + border
                for (b in 0 until border) {
                    Bitmap32.copyRect(out, 0, py, out, 0, py - 1 - b, out.width, 1)
                    Bitmap32.copyRect(out, 0, py + tileheight - 1, out, 0, py + tileheight + b, out.width, 1)
                }
            }

			return TileSet(texs, tilewidth, tileheight, unit = Unit)
		}

        // EXTRACT

        fun extractBitmaps(
            bmp: Bitmap32,
            tilewidth: Int,
            tileheight: Int,
            columns: Int,
            tilecount: Int,
            spacing: Int,
            margin :Int
        ): List<Bitmap32> = extractBmpSlices(bmp, tilewidth, tileheight, columns, tilecount, spacing, margin).map { it.extract() }

        fun extractBmpSlices(
            bmp: Bitmap32,
            tilewidth: Int,
            tileheight: Int,
            columns: Int,
            tilecount: Int,
            spacing: Int,
            margin :Int
        ): List<BmpSlice32> {
            return ArrayList<BmpSlice32>().apply {
                loop@ for (y in 0 until bmp.height / tileheight) {
                    for (x in 0 until columns) {
                        add(bmp.sliceWithSize(
                            margin + x * (tilewidth + spacing),
                            margin + y * (tileheight + spacing),
                            tilewidth, tileheight
                        ))
                        if (this.size >= tilecount) break@loop
                    }
                }
            }
        }

    }
}
