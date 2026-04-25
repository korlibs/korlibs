package korlibs.util

interface SimpleStrReader {
    val pos: Int
    val hasMore: Boolean
    fun readChar(): Char
    fun peekChar(): Char
    fun skip(count: Int = 1): SimpleStrReader { repeat(count) { readChar() }; return this }
    fun clone(): SimpleStrReader
    fun toStringContext(): String = "SimpleStrReader(pos=$pos, peek='${peekChar()}')"

    companion object {
        private class Impl(val str: String, override var pos: Int) : SimpleStrReader {
            override val hasMore: Boolean get() = pos < str.length
            override fun readChar(): Char = peekChar().also { pos++ }
            override fun peekChar(): Char = str.getOrElse(pos) { '\u0000' }
            override fun skip(count: Int): SimpleStrReader = this.also { pos += count }
            override fun clone(): SimpleStrReader = Impl(str, pos)
            override fun toString(): String = "Impl(str='$str', pos=$pos)"
            override fun toStringContext(): String = "SimpleStrReader(pos=$pos, around='${str.substring(pos.coerceAtMost(str.length), (pos + 10).coerceAtMost(str.length))}')"
        }
        operator fun invoke(str: String, pos: Int = 0): SimpleStrReader = Impl(str, pos)
    }
}
