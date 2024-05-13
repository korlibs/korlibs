package korlibs.js

suspend fun <T> JSAsyncIterable<T>.toFlow(): Flow<T> = flow {
    val iterator = (this@toFlow.asDynamic())[Symbol_asyncIterator]
    val gen = iterator.call(this)
    //println(gen)
    while (true) {
        val prom = gen.next().unsafeCast<Promise<JSIterableResult<T>>>()
        val value = prom.await()
        if (value.done) break
        emit(value.value)
    }
}
