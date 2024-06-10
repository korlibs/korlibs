package korlibs.io.stream.internal

import korlibs.io.lang.*

internal fun String.toBytez(len: Int, charset: Charset = UTF8): ByteArray = this.toByteArray(charset).copyOf(len)
internal fun String.toBytez(charset: Charset = UTF8): ByteArray = this.toByteArray(charset).let { it.copyOf(it.size + 1) }
