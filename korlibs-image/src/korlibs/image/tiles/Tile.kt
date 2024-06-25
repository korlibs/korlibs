package korlibs.image.tiles

import korlibs.memory.*

inline class Tile(val raw: Int64) {
    val rawLow: Int get() = raw.low
    val rawHigh: Int get() = raw.high

    @Deprecated("Use raw, rawLow or rawHigh")
    val data: Int get() = rawLow

    val isValid: Boolean get() = raw != INVALID_VALUE
    val isInvalid: Boolean get() = raw == INVALID_VALUE

    val tile: Int get() = rawLow.extract(0, 26)
    //val tile: Int get() = rawLow.extract(0, 18)

    //val extra: Int get() = rawLow.extract10(18)
    //val offsetX: Int get() = rawLow.extract5(18)
    //val offsetY: Int get() = rawLow.extract5(23)

    val rotate: Boolean get() = rawLow.extract(29)
    val flipY: Boolean get() = rawLow.extract(30)
    val flipX: Boolean get() = rawLow.extract(31)
    val offsetX: Int get() = rawHigh.extract16Signed(0)
    val offsetY: Int get() = rawHigh.extract16Signed(16)

    fun toStringInfo(): String = "Tile(tile=$tile, offsetX=$offsetX, offsetY=$offsetY, flipX=$flipX, flipY=$flipY, rotate=$rotate)"

    companion object {
        val INVALID_VALUE = Int64(-1, -1)
        val ZERO = Tile(Int64.ZERO)
        val INVALID = Tile(INVALID_VALUE)

        fun fromRaw(raw: Int64): Tile = Tile(raw)
        fun fromRaw(raw: Double): Tile = Tile(Int64.fromRaw(raw))
        fun fromRaw(raw: Long): Tile = Tile(raw.toInt64())
        fun fromRaw(low: Int, high: Int): Tile = Tile(Int64(low, high))

        operator fun invoke(tile: Int, offsetX: Int = 0, offsetY: Int = 0, flipX: Boolean = false, flipY: Boolean = false, rotate: Boolean = false): Tile = fromRaw(
            0.insert(tile, 0, 26).insert(rotate, 29).insert(flipY, 30).insert(flipX, 31),
            0.insert16(offsetX, 0).insert16(offsetY, 16)
        )
    }
}
