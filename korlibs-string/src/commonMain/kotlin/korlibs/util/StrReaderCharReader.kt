package korlibs.util

import korlibs.io.stream.*

class StrReaderCharReader(val reader: SimpleStrReader) : CharReader {
    override fun read(out: StringBuilder, count: Int): Int {
        for (n in 0 until count) {
            if (!reader.hasMore) return n
            out.append(reader.readChar())
        }
        return count
    }
    override fun clone(): CharReader = StrReaderCharReader(reader.clone())
}

class CharReaderStrReader(val reader: CharReader, val buffer: StringBuilder = StringBuilder(), var bufferPos: Int = 0) : SimpleStrReader {
    private fun ensureBuffer(): Int {
        if (bufferPos >= buffer.length) {
            buffer.clear()
            reader.read(buffer, 1024)
            bufferPos = 0
        }
        return buffer.length - bufferPos
    }

    override var pos: Int = 0
    override val hasMore: Boolean get() = ensureBuffer() > 0

    override fun readChar(): Char {
        return peekChar().also { bufferPos++ }
    }

    override fun peekChar(): Char {
        if (ensureBuffer() <= 0) return '\u0000'
        return buffer[bufferPos]
    }

    override fun clone(): SimpleStrReader = CharReaderStrReader(reader.clone(), StringBuilder(buffer), bufferPos)
}
