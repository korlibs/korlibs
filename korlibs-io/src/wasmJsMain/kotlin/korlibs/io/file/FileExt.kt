package korlibs.io.file

import korlibs.io.stream.AsyncStream
import korlibs.io.stream.AsyncStreamBase
import korlibs.io.stream.buffered
import korlibs.io.stream.toAsyncStream
import korlibs.io.util.toByteArray
import korlibs.memory.arraycopy
import korlibs.wasm.toLong
import kotlin.math.min
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.files.Blob
import org.w3c.files.File
import org.w3c.files.FileReader

fun Blob.openAsync(): AsyncStream = BlobAsyncBaseStream(this).toAsyncStream().buffered(0x10_000, 0x10)

external class BlobExt : JsAny {
    fun slice(start: JsNumber = definedExternally, end: JsNumber = definedExternally, contentType: String = definedExternally): Blob
}

class BlobAsyncBaseStream(val blob: Blob) : AsyncStreamBase() {
    override suspend fun close() {
    }

    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        val deferred = CompletableDeferred<Int>()
        val reader = FileReader()
        reader.onload = {
            val ab = reader.result!!.unsafeCast<ArrayBuffer>()
            val minLen = min(ab.byteLength.toInt(), len)
            arraycopy(Int8Array(ab).toByteArray(), 0, buffer, offset, minLen)
            deferred.complete(minLen)
            null
        }
        reader.onerror = {
            deferred.completeExceptionally(Throwable("${reader.error}"))
            null
        }
        reader.readAsArrayBuffer(blob.unsafeCast<BlobExt>().slice(position.toDouble().toJsNumber(), (position.toDouble() + len.toDouble()).toJsNumber()))
        return deferred.await()
    }

    override suspend fun getLength(): Long {
        return blob.size.toLong()
    }
}

fun File.toVfs(): VfsFile {
    val file = this
    return object : Vfs() {
        override val absolutePath: String = file.name
        // @TODO: Check path
        override suspend fun open(path: String, mode: VfsOpenMode): AsyncStream = file.openAsync()
        override suspend fun listFlow(path: String): Flow<VfsFile> {
            return if (path == "/" || path == "") {
                listOf(this[file.name]).asFlow()
            } else {
                listOf<VfsFile>().asFlow()
            }
        }
    }[file.name]
}
