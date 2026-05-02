package korlibs.ffi.api

annotation class FFI(
    val commonLib: String = "",
    val windowsLib: String = "",
    val linuxLib: String = "",
    val macosLib: String = "",
)

annotation class FFINativeInt

annotation class FFIWideString

inline class FFIPointer(val address: Long) {
    companion object {
        val NULL = FFIPointer(0L)
    }
    @OptIn(ExperimentalStdlibApi::class)
    override fun toString(): String = "FFIPointer(address=0x${address.toHexString()})"
}

class FFIFunctionRef<T : Function<*>>(val func: T) : AutoCloseable {
    var slot: Int = -1
    var slots: Array<FFIFunctionRef<T>?>? = null
    var closer: (() -> Unit)? = null

    fun allocIn(slots: Array<FFIFunctionRef<T>?>): Int {
        if (slot >= 0) return slot
        slot = slots.indexOf(null)
        this.slots = slots
        if (slot == -1) error("No more slots available")
        slots[slot] = this
        return slot
    }

    override fun close() {
        closer?.invoke()
        closer = null
        if (slot >= 0) this.slots?.set(slot, null)
        slot = -1
        this.slots = null
    }
}
expect fun FFIPointer.getF64(offset: Int = 0): Double
expect fun FFIPointer.getF32(offset: Int = 0): Float
expect fun FFIPointer.getI64(offset: Int = 0): Long
expect fun FFIPointer.getI32(offset: Int = 0): Int
expect fun FFIPointer.getI16(offset: Int = 0): Short
expect fun FFIPointer.getI8(offset: Int = 0): Byte

expect fun FFIPointer.setF64(offset: Int = 0, value: Double)
expect fun FFIPointer.setF32(offset: Int = 0, value: Float)
expect fun FFIPointer.setI64(offset: Int = 0, value: Long)
expect fun FFIPointer.setI32(offset: Int = 0, value: Int)
expect fun FFIPointer.setI16(offset: Int = 0, value: Short)
expect fun FFIPointer.setI8(offset: Int = 0, value: Byte)

expect val isSupportedFFI: Boolean
internal expect fun transferMemory(address: Long, data: ByteArray, offset: Int, size: Int, toPointer: Boolean)
private val SCRATCH_MEM = ByteArray(64)
