package korlibs.memory

import kotlin.math.max

/**
 * Analogous to [StringBuilder] but for [ByteArray]. Allows to [append] values to end calling [toByteArray].
 * Provides some methods like [s16LE] or [f32BE] to append specific bit representations easily.
 */
public class ByteArrayBuilder(public var data: ByteArray, size: Int = data.size, public val allowGrow: Boolean = true) {
    public constructor(initialCapacity: Int = 4096) : this(ByteArray(initialCapacity), 0)

    private var _size: Int = size
    public var size: Int get() = _size
        set(value) {
            val oldPosition = _size
            val newPosition = value
            ensure(newPosition)
            _size = newPosition
            if (newPosition > oldPosition) {
                data.fill(0, oldPosition, newPosition)
            }
        }

    private fun ensure(expected: Int) {
        if (data.size < expected) {
            if (!allowGrow) throw RuntimeException("ByteArrayBuffer configured to not grow!")
            //val oldCapacity = data.size
            val newSize = (data.size + 7) * 5
            val realNewSize = if (newSize < 0) Int.MAX_VALUE / 2 else newSize
            if (newSize < 0 && expected > realNewSize) error("ByteArrayBuffer can't grow that much")
            data = data.copyOf(max(expected, realNewSize))
            //val newCapacity = data.size
            //println("GROW: $oldCapacity -> $newCapacity")
        }
    }

    private fun ensureCount(count: Int) {
        ensure(_size + count)
    }

    private inline fun prepare(count: Int, callback: () -> Unit): ByteArrayBuilder {
        ensureCount(count)
        callback().also { _size += count }
        return this
    }

    public fun append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset) {
        ensureCount(len)
        arraycopy(array, offset, this.data, _size, len)
        this._size += len
    }

    // @TODO: This exists to not return a reference value, that has a performance hit on K/N, we should then ensure
    public fun appendFast(v: Byte) {
        ensure(_size + 1)
        data[_size++] = v
    }

    public inline fun append(v: Byte): ByteArrayBuilder {
        appendFast(v)
        return this
    }

    public fun append(vararg v: Byte): Unit = append(v)
    public fun append(vararg v: Int): ByteArrayBuilder = prepare(v.size) {
        for (n in 0 until v.size) this.data[this._size + n] = v[n].toByte()
    }

    public fun appendByte(v: Int): ByteArrayBuilder = prepare(1) { data[_size] = v.toByte() }

    public fun s8(v: Int): ByteArrayBuilder = appendByte(v)

    public fun s16(v: Int, little: Boolean): ByteArrayBuilder = prepare(2) { data.set16(_size, v, little) }
    public fun s16LE(v: Int): ByteArrayBuilder = prepare(2) { data.set16LE(_size, v) }
    public fun s16BE(v: Int): ByteArrayBuilder = prepare(2) { data.set16BE(_size, v) }

    public fun s24(v: Int, little: Boolean): ByteArrayBuilder = prepare(3) { data.set24(_size, v, little) }
    public fun s24LE(v: Int): ByteArrayBuilder = prepare(3) { data.set24LE(_size, v) }
    public fun s24BE(v: Int): ByteArrayBuilder = prepare(3) { data.set24BE(_size, v) }

    public fun s32(v: Int, little: Boolean): ByteArrayBuilder = prepare(4) { data.set32(_size, v, little) }
    public fun s32LE(v: Int): ByteArrayBuilder = prepare(4) { data.set32LE(_size, v) }
    public fun s32BE(v: Int): ByteArrayBuilder = prepare(4) { data.set32BE(_size, v) }
    public fun f32(v: Float, little: Boolean): ByteArrayBuilder = prepare(4) { data.setF32(_size, v, little) }
    public fun f32LE(v: Float): ByteArrayBuilder = prepare(4) { data.setF32LE(_size, v) }
    public fun f32BE(v: Float): ByteArrayBuilder = prepare(4) { data.setF32BE(_size, v) }

    public fun f64(v: Double, little: Boolean): ByteArrayBuilder = prepare(8) { data.setF64(_size, v, little) }
    public fun f64LE(v: Double): ByteArrayBuilder = prepare(8) { data.setF64LE(_size, v) }
    public fun f64BE(v: Double): ByteArrayBuilder = prepare(8) { data.setF64BE(_size, v) }

    public fun clear() {
        _size = 0
    }

    public fun toByteArray(): ByteArray = data.copyOf(_size)


    private fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int) {
        src.copyInto(dst, dstPos, srcPos, srcPos + size)
    }

    private fun ByteArray.set8(offset: Int, value: Int) { this[offset] = value.toByte() }
    private fun ByteArray.set8(offset: Int, value: Long) { this[offset] = value.toByte() }
    private fun ByteArray.set16LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8) }
    private fun ByteArray.set24LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 2] = value.extractByte(16) }
    private fun ByteArray.set32LE(offset: Int, value: Int) { this[offset + 0] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 2] = value.extractByte(16); this[offset + 3] = value.extractByte(24) }
    private fun ByteArray.set32LE(offset: Int, value: Long) { set32LE(offset, value.toInt()) }
    private fun ByteArray.set64LE(offset: Int, value: Long) { set32LE(offset + 0, (value ushr 0).toInt()); set32LE(offset + 4, (value ushr 32).toInt()) }

    private fun ByteArray.set16(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set16LE(offset, value) else set16BE(offset, value) }
    private fun ByteArray.set24(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set24LE(offset, value) else set24BE(offset, value) }
    private fun ByteArray.set32(offset: Int, value: Int, littleEndian: Boolean) { if (littleEndian) set32LE(offset, value) else set32BE(offset, value) }
    private fun ByteArray.set64(offset: Int, value: Long, littleEndian: Boolean) { if (littleEndian) set64LE(offset, value) else set64BE(offset, value) }
    private fun ByteArray.setF32(offset: Int, value: Float, littleEndian: Boolean) { if (littleEndian) setF32LE(offset, value) else setF32BE(offset, value) }
    private fun ByteArray.setF64(offset: Int, value: Double, littleEndian: Boolean) { if (littleEndian) setF64LE(offset, value) else setF64BE(offset, value) }
    private fun ByteArray.setF32LE(offset: Int, value: Float) { set32LE(offset + 0, value.toRawBits()) }
    private fun ByteArray.setF64LE(offset: Int, value: Double) { set64LE(offset + 0, value.toRawBits()) }

    private fun ByteArray.set16BE(offset: Int, value: Int) { this[offset + 1] = value.extractByte(0); this[offset + 0] = value.extractByte(8) }
    private fun ByteArray.set24BE(offset: Int, value: Int) { this[offset + 2] = value.extractByte(0); this[offset + 1] = value.extractByte(8); this[offset + 0] = value.extractByte(16) }
    private fun ByteArray.set32BE(offset: Int, value: Int) { this[offset + 3] = value.extractByte(0); this[offset + 2] = value.extractByte(8); this[offset + 1] = value.extractByte(16); this[offset + 0] = value.extractByte(24) }
    private fun ByteArray.set32BE(offset: Int, value: Long) { set32BE(offset, value.toInt()) }
    private fun ByteArray.set64BE(offset: Int, value: Long) { set32BE(offset + 0, (value ushr 32).toInt()); set32BE(offset + 4, (value ushr 0).toInt()) }
    private fun ByteArray.setF32BE(offset: Int, value: Float) { set32BE(offset + 0, value.toRawBits()) }
    private fun ByteArray.setF64BE(offset: Int, value: Double) { set64BE(offset + 0, value.toRawBits()) }

    private fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()
}

public inline class ByteArrayBuilderLE(public val bab: ByteArrayBuilder)

public val ByteArrayBuilderLE.size: Int get() = bab.size
public fun ByteArrayBuilderLE.append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset): Unit = bab.append(array, offset, len)
public fun ByteArrayBuilderLE.append(v: Byte): ByteArrayBuilder = bab.append(v)
public fun ByteArrayBuilderLE.appendByte(v: Int): ByteArrayBuilder = bab.appendByte(v)
public fun ByteArrayBuilderLE.append(vararg v: Byte): Unit = bab.append(*v)
public fun ByteArrayBuilderLE.append(vararg v: Int): ByteArrayBuilder = bab.append(*v)
public fun ByteArrayBuilderLE.s8(v: Int): ByteArrayBuilder = bab.s8(v)
public fun ByteArrayBuilderLE.s16(v: Int): ByteArrayBuilder = bab.s16LE(v)
public fun ByteArrayBuilderLE.s24(v: Int): ByteArrayBuilder = bab.s24LE(v)
public fun ByteArrayBuilderLE.s32(v: Int): ByteArrayBuilder = bab.s32LE(v)
public fun ByteArrayBuilderLE.f32(v: Float): ByteArrayBuilder = bab.f32LE(v)
public fun ByteArrayBuilderLE.f64(v: Double): ByteArrayBuilder = bab.f64LE(v)
public fun ByteArrayBuilderLE.clear(): Unit = bab.clear()
public fun ByteArrayBuilderLE.toByteArray(): ByteArray = bab.toByteArray()

public inline class ByteArrayBuilderBE(public val bab: ByteArrayBuilder)

public val ByteArrayBuilderBE.size: Int get() = bab.size
public fun ByteArrayBuilderBE.append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset): Unit = bab.append(array, offset, len)
public fun ByteArrayBuilderBE.append(v: Byte): ByteArrayBuilder = bab.append(v)
public fun ByteArrayBuilderBE.appendByte(v: Int): ByteArrayBuilder = bab.appendByte(v)
public fun ByteArrayBuilderBE.append(vararg v: Byte): Unit = bab.append(*v)
public fun ByteArrayBuilderBE.append(vararg v: Int): ByteArrayBuilder = bab.append(*v)
public fun ByteArrayBuilderBE.s8(v: Int): ByteArrayBuilder = bab.s8(v)
public fun ByteArrayBuilderBE.s16(v: Int): ByteArrayBuilder = bab.s16BE(v)
public fun ByteArrayBuilderBE.s24(v: Int): ByteArrayBuilder = bab.s24BE(v)
public fun ByteArrayBuilderBE.s32(v: Int): ByteArrayBuilder = bab.s32BE(v)
public fun ByteArrayBuilderBE.f32(v: Float): ByteArrayBuilder = bab.f32BE(v)
public fun ByteArrayBuilderBE.f64(v: Double): ByteArrayBuilder = bab.f64BE(v)
public fun ByteArrayBuilderBE.clear(): Unit = bab.clear()
public fun ByteArrayBuilderBE.toByteArray(): ByteArray = bab.toByteArray()

/** Analogous to [buildString] but for [ByteArray] */
public inline fun buildByteArray(capacity: Int = 4096, callback: ByteArrayBuilder.() -> Unit): ByteArray =
    ByteArrayBuilder(capacity).apply(callback).toByteArray()

/** Analogous to [buildString] but for [ByteArray] (Provides shortcuts for writing Little Endian bit values) */
public inline fun buildByteArrayLE(capacity: Int = 4096, callback: ByteArrayBuilderLE.() -> Unit): ByteArray =
    ByteArrayBuilderLE(ByteArrayBuilder(capacity)).apply(callback).toByteArray()

/** Analogous to [buildString] but for [ByteArray] (Provides shortcuts for writing Big Endian bit values) */
public inline fun buildByteArrayBE(capacity: Int = 4096, callback: ByteArrayBuilderBE.() -> Unit): ByteArray =
    ByteArrayBuilderBE(ByteArrayBuilder(capacity)).apply(callback).toByteArray()
