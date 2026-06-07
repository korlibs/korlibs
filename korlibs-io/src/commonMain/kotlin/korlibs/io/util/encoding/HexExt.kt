package korlibs.io.util.encoding

import korlibs.encoding.Hex
import korlibs.memory.ByteArrayBuilder

fun Hex.decode(src: String, dst: ByteArrayBuilder) = decode(src) { n, byte -> dst.append(byte) }
