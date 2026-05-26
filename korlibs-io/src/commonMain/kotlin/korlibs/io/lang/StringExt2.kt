package korlibs.io.lang

import korlibs.io.util.quote

val String.quoted: String get() = this.quote()
