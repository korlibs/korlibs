package korlibs.math


fun Double.isNanOrInfinite() = this.isNaN() || this.isInfinite()
fun Float.isNanOrInfinite() = this.isNaN() || this.isInfinite()
