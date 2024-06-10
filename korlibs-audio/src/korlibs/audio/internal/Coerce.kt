package korlibs.audio.internal

internal fun Float.toSampleShort(): Short = (this * Short.MAX_VALUE).coerceToShort()
internal fun Float.coerceToShort(): Short = this.toInt().coerceToShort()
internal fun Int.coerceToShort(): Short = this.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
