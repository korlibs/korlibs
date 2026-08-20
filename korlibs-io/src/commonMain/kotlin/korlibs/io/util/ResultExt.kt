package korlibs.io.util

fun <T> Result<T>.getOrNullLoggingError(): T? {
    this.exceptionOrNull()?.printStackTrace()
    return getOrNull()
}
