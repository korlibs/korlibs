package korlibs.math

////////////////////
////////////////////

/** Performs a fast integral logarithmic of base two */
fun ilog2(v: Int): Int = if (v == 0) (-1) else (31 - v.countLeadingZeroBits())
// fun ilog2(v: Int): Int = kotlin.math.log2(v.toDouble()).toInt()
fun ilog2Ceil(v: Int): Int = kotlin.math.ceil(kotlin.math.log2(v.toDouble())).toInt()
