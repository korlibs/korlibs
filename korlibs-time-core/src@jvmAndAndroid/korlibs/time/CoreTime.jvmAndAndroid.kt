package korlibs.time

var currentTimeMillisJvm: () -> Long = { System.currentTimeMillis() }
actual fun currentTimeMillis(): Long = currentTimeMillisJvm()
