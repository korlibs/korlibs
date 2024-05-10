package korlibs.io.async

interface AsyncInvokable {
    suspend operator fun <T> invoke(func: suspend () -> T): T
}
