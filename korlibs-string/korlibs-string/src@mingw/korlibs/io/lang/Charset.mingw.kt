package korlibs.io.lang

internal actual val platformCharsetProvider: CharsetProvider = CharsetProvider { normalizedName, _ -> null }
