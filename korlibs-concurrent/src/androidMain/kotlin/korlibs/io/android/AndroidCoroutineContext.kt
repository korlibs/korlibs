package korlibs.io.android

import android.content.Context
import korlibs.io.file.std.vfsInitWithAndroidContextOnce
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class AndroidCoroutineContext(val context: Context) : CoroutineContext.Element {
    object Key : CoroutineContext.Key<AndroidCoroutineContext>

    init {
        vfsInitWithAndroidContextOnce(context)
    }

    override val key: CoroutineContext.Key<*> = Key
}

suspend fun <T> withAndroidContext(context: Context, callback: suspend CoroutineScope.() -> T): T {
    return withContext(coroutineContext + AndroidCoroutineContext(context), callback)
}

fun CoroutineContext.androidContextOrNull(): Context? = this[AndroidCoroutineContext.Key]?.context

fun CoroutineContext.androidContext(): Context = androidContextOrNull()
    ?: error("Android context not set! Please call `withAndroidContext()` method in your coroutine body. Current context: $this")

suspend fun androidContext(): Context = coroutineContext.androidContext()
suspend fun androidContextOrNull(): Context? = coroutineContext.androidContextOrNull()
