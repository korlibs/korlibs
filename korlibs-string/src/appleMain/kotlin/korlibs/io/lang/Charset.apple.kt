package korlibs.io.lang

internal actual val platformCharsetProvider: CharsetProvider =
    CharsetProvider { normalizedName, name -> runCatching { CharsetApple(name) }.getOrNull() }
