package korlibs.compression.deflate


// @TODO: This interface is not good
interface DeflaterBitReader {
    val bigChunkSize: Int
    val readWithSize: Int
    val bitsavailable: Int
    fun ensureBits(bits: Int)
    suspend fun hasAvailable(): Boolean
    suspend fun getAvailable(): Long
    suspend fun abytes(count: Int): ByteArray
    fun su16LE(): Int
    fun sreadBit(): Boolean
    fun skipBits(bits: Int)
    fun peekBits(count: Int): Int
    fun readBits(count: Int): Int
    fun returnToBuffer(data: ByteArray, offset: Int, size: Int)
    suspend fun read(data: ByteArray, offset: Int, size: Int): Int
    suspend fun readBytesExact(count: Int): ByteArray
    suspend fun prepareBigChunkIfRequired()
}

// @TODO: This interface is not good
interface DeflaterAsyncOutputStream {
    suspend fun write(bytes: ByteArray, offset: Int, size: Int)
    suspend fun write8(value: Int)
    suspend fun write16LE(value: Int)
    suspend fun writeBytes(bytes: ByteArray)
}
