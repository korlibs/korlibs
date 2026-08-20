package korlibs.math

////////////////////
////////////////////

/** Converts this [Boolean] into integer: 1 for true, 0 for false */
fun Boolean.toInt(): Int = if (this) 1 else 0
fun Boolean.toByte(): Byte = if (this) 1 else 0
fun Byte.toBoolean(): Boolean = this.toInt() != 0
