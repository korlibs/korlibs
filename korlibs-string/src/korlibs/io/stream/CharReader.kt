package korlibs.io.stream

interface CharReader {
    fun read(out: StringBuilder, count: Int): Int
    fun clone(): CharReader
}
fun CharReader.read(count: Int): String = buildString(count) { read(this, count) }
