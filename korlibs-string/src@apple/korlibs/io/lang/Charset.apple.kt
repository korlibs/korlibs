package korlibs.io.lang

internal actual val platformCharsetProvider: CharsetProvider =
    CharsetProvider { normalizedName, name -> if (CharsetApple.isSupported(name.uppercase())) CharsetApple(name) else null }
