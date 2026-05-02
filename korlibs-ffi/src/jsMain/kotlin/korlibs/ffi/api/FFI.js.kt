package korlibs.ffi.api

actual val isSupportedFFI: Boolean get() = js("(typeof Deno != 'undefined')")

private external val Deno: dynamic
private external class BigInt

private fun __toBigInt(str: String): BigInt = js("BigInt(str)")
private fun String.toBigInt(): BigInt = __toBigInt(this)
private fun Long.toBigInt(): BigInt = this.toString().toBigInt()
private fun BigInt.toLong(): Long = this.toString().toLong()
//private fun __createView(bigIntAddress: BigInt): dynamic = js("new Deno.UnsafePointerView(Deno.UnsafePointer.create(bigIntAddress))")
private fun __createView(bigIntAddress: BigInt, size: Int): dynamic = js("new DataView(Deno.UnsafePointerView.getArrayBuffer(Deno.UnsafePointer.create(bigIntAddress), size, 0))")
private fun createView(address: Long, size: Int) = __createView(address.toBigInt(), size) ?: error("Can't create view for address=$address")

actual fun FFIPointer.getF64(offset: Int): Double = createView(this.address + offset, 8).getFloat64(0, true)
actual fun FFIPointer.getF32(offset: Int): Float = createView(this.address + offset, 8).getFloat32(0, true)
actual fun FFIPointer.getI64(offset: Int): Long = createView(this.address + offset, 8).getBigInt64(0, true).unsafeCast<BigInt>().toLong()
actual fun FFIPointer.getI32(offset: Int): Int = createView(this.address + offset, 8).getInt32(0, true)
actual fun FFIPointer.getI16(offset: Int): Short = createView(this.address + offset, 8).getInt16(0, true)
actual fun FFIPointer.getI8(offset: Int): Byte = createView(this.address + offset, 8).getInt8(0)

actual fun FFIPointer.setF64(offset: Int, value: Double): Unit = createView(this.address + offset, 8).setFloat64(0, value, true)
actual fun FFIPointer.setF32(offset: Int, value: Float): Unit = createView(this.address + offset, 8).setFloat32(0, value, true)
actual fun FFIPointer.setI64(offset: Int, value: Long): Unit = createView(this.address + offset, 8).setBigInt64(0, value.toBigInt(), true)
actual fun FFIPointer.setI32(offset: Int, value: Int): Unit = createView(this.address + offset, 8).setInt32(0, value, true)
actual fun FFIPointer.setI16(offset: Int, value: Short): Unit = createView(this.address + offset, 8).setInt16(0, value, true)
actual fun FFIPointer.setI8(offset: Int, value: Byte): Unit = createView(this.address + offset, 8).setInt8(0, value)

internal actual fun transferMemory(address: Long, data: ByteArray, offset: Int, size: Int, toPointer: Boolean) {
    //Deno.UnsafePointerView.copyInto()
    //createView(address).pointer
    TODO()
}