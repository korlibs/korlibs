package korlibs.io.util.encoding

import korlibs.encoding.Base64
import korlibs.io.lang.Charset
import korlibs.io.lang.toByteArray

fun Base64.encode(src: String, charset: Charset): String = encode(src.toByteArray(charset))
