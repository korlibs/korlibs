package korlibs.util

import korlibs.io.util.*

val SimpleStrReader.eof: Boolean get() = !hasMore

fun SimpleStrReader.readUntilBuilder(char: Char, out: StringBuilder, included: Boolean = false): StringBuilder =
    readUntilBuilder(included, out) { it == char }

inline fun SimpleStrReader.readWhileBuilder(included: Boolean = false, out: StringBuilder, cond: (Char) -> Boolean): StringBuilder =
    readUntilBuilder(included, out) { !cond(it) }

inline fun SimpleStrReader.readUntilBuilder(included: Boolean = false, out: StringBuilder, cond: (Char) -> Boolean): StringBuilder {
    while (hasMore) {
        val c = peekChar()
        if (cond(c)) {
            if (included) {
                readChar()
                out.append(c)
            }
            break
        }
        readChar()
        out.append(c)
    }
    return out
}

fun SimpleStrReader.skipWhile(cond: (Char) -> Boolean): SimpleStrReader {
    while (hasMore) {
        val c = peekChar()
        if (!cond(c)) {
            return this
        }
        readChar()
    }
    return this
}

fun SimpleStrReader.skipExpect(expected: Char) {
    val readed = this.readChar()
    if (readed != expected) {
        throw IllegalArgumentException("Expected '$expected' but found '$readed' at $pos")
    }
}

fun SimpleStrReader.tryExpect(char: Char, consume: Boolean = true): Boolean {
    val read = peekChar()
    val isExpected = read == char
    if (consume && isExpected) readChar()
    return isExpected
}

fun SimpleStrReader.read(count: Int): String {
    val out = StringBuilder(count)
    for (n in 0 until count) out.append(readChar())
    return out.toString()
}

fun SimpleStrReader.skipSpaces(): SimpleStrReader {
    this.skipWhile { it.isWhitespaceFast() }
    return this
}

fun SimpleStrReader.matchIdentifier(out: StringBuilder): StringBuilder? = readWhileBuilder(out = out) { it.isLetterDigitOrUnderscore() || it == '-' || it == '~' || it == ':' }.takeIf { it.isNotEmpty() }

fun SimpleStrReader.matchSingleOrDoubleQuoteString(out: StringBuilder): StringBuilder? = when (this.peekChar()) {
    '\'', '"' -> {
        val quoteType = this.readChar()
        out.append(quoteType)
        this.readUntilBuilder(quoteType, out, included = true)
    }
    else -> null
}
