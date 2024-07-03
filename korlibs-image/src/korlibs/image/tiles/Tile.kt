package korlibs.image.tiles

import korlibs.memory.*
import korlibs.number.*

inline class Tile(val raw: Int53) {
    val rawHigh get() = raw.high
    val rawLow get() = raw.low

    @Deprecated("Use raw, rawLow or rawHigh")
    val data: Int get() = raw.toInt()

    val isValid: Boolean get() = this != INVALID
    val isInvalid: Boolean get() = this == INVALID

    val tile: Int get() = raw.extract(0, 19)

    val rotate: Boolean get() = raw.extract(19)
    val flipY: Boolean get() = raw.extract(20)
    val flipX: Boolean get() = raw.extract(21)
    val offsetX: Int get() = raw.extractSigned(22, 15)
    val offsetY: Int get() = raw.extractSigned(37, 15)

    fun toStringInfo(): String = "Tile(tile=$tile, offsetX=$offsetX, offsetY=$offsetY, flipX=$flipX, flipY=$flipY, rotate=$rotate)"

    companion object {
        val INVALID_VALUE = Int53.MAX_VALUE
        val ZERO = Tile(Int53.ZERO)
        val INVALID = Tile(INVALID_VALUE)

        fun fromRaw(raw: Int64): Tile = Tile(raw.toLong().toInt53())
        fun fromRaw(raw: Int53): Tile = Tile(raw)
        fun fromRaw(raw: Double): Tile = Tile(raw.toInt53())
        fun fromRaw(raw: Long): Tile = Tile(raw.toInt53())
        fun fromRaw(low: Int, high: Int): Tile = Tile(Int53.fromLowHigh(low, high))

        operator fun invoke(tile: Int, offsetX: Int = 0, offsetY: Int = 0, flipX: Boolean = false, flipY: Boolean = false, rotate: Boolean = false): Tile = Tile(
            Int53.ZERO
                .insertNoClear(tile, 0, 19)
                .insertNoClear(rotate, 19)
                .insertNoClear(flipY, 20)
                .insertNoClear(flipX, 21)
                .insertNoClear(offsetX, 22, 15)
                .insertNoClear(offsetY, 37, 15)
        )
    }
}
