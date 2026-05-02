package korlibs.io.stream

import java.io.*

fun InputStream.toSyncInputStream(): SyncInputStream {
    return object : SyncInputStream {
        override fun read(buffer: ByteArray, offset: Int, len: Int): Int = this@toSyncInputStream.read(buffer, offset, len)

        override fun read(): Int = this@toSyncInputStream.read()

        override fun skip(count: Int) {
            this@toSyncInputStream.skip(count.toLong())
        }

        override fun close() {
            this@toSyncInputStream.close()
        }
    }
}

fun OutputStream.toSyncOutputStream(): SyncOutputStream {
    return object : SyncOutputStream {
        override fun write(byte: Int) {
            this@toSyncOutputStream.write(byte)
        }

        override fun write(buffer: ByteArray, offset: Int, len: Int) {
            //println("WRITE[s]: $len")
            return this@toSyncOutputStream.write(buffer, offset, len).also {
                this@toSyncOutputStream.flush()
                //println("WRITE[e]: $len")
            }
        }

        override fun flush() = this@toSyncOutputStream.flush()
        override fun close() = this@toSyncOutputStream.close()
    }
}
