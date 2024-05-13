package korlibs.js

import org.khronos.webgl.*

fun Uint8Array.toByteArray(): ByteArray = Int8Array(this.unsafeCast<Int8Array>()).unsafeCast<ByteArray>()
fun Int8Array.toByteArray(): ByteArray = this.unsafeCast<ByteArray>()
//fun ByteArray.toNodeJsBufferU8(): NodeBuffer = Uint8Array(this.unsafeCast<ArrayBuffer>()).asDynamic()

fun ByteArray.asInt8Array(): Int8Array = this.unsafeCast<Int8Array>()
fun ByteArray.toUint8Array(): Uint8Array {
    val i = this.asInt8Array()
    return Uint8Array(i.buffer, i.byteOffset, i.length)
}
