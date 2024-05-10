@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.memory.*

open class MarkableSyncStream(val inp: SyncInputStream) : MarkableSyncInputStream {
    private var markTemp = SimpleBytesDeque(8)
    private var markLimit = 0
    private var doReset = false

    override fun mark(readlimit: Int) {
        markTemp.clear()
        markLimit = readlimit
    }

    override fun reset() {
        doReset = true
    }

    override fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        if (doReset) {
            return markTemp.read(buffer, offset, len).also {
                if (markTemp.availableRead <= 0) {
                    doReset = false
                }
            }
        }
        val out = inp.read(buffer, offset, len)
        if (markLimit > 0) {
            val markRead = kotlin.math.min(markLimit, out)
            markLimit -= markRead
            markTemp.write(buffer, offset, markRead)
        }
        return out
    }
}
