package korlibs.util

interface SimpleStrReader {
    val pos: Int
    val hasMore: Boolean
    fun readChar(): Char
    fun peekChar(): Char
    //fun mark(readLimit: Int)
    //fun reset()

    companion object {
        private class Impl(val str: String, override var pos: Int) : SimpleStrReader {
            override val hasMore: Boolean get() = pos < str.length
            //private var mark = 0
            //override fun mark(readLimit: Int) { mark = pos }
            //override fun reset() { pos = mark }
            override fun readChar(): Char = peekChar().also { pos++ }
            override fun peekChar(): Char = str.getOrElse(pos) { '\u0000' }

            override fun toString(): String = "Impl(str='$str', pos=$pos)"
        }
        operator fun invoke(str: String, pos: Int = 0): SimpleStrReader = Impl(str, pos)
    }
}
