package korlibs.io.resources

annotation class ResourcePath()

interface Resourceable<T : Any> {
    fun getOrNull(): T?
    suspend fun get(): T

    data class Fixed<T : Any>(val value: T) : Resourceable<T> {
        override fun getOrNull() = value
        override suspend fun get() = value
    }
}

fun <T : Any> Resourceable(value: T) = Resourceable.Fixed(value)
