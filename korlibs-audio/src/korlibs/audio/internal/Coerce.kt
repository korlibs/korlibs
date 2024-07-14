package korlibs.audio.internal

internal fun Int.coerceToShort(): Short = this.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
