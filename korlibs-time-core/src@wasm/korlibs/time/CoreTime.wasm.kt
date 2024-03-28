package korlibs.time

@JsFun("() => { return Date.now(); }")
private external fun Date_now(): Double

actual fun currentTimeMillis(): Long = Date_now().toLong()
