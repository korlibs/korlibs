package korlibs.io

import android.content.Context
import korlibs.io.android.withAndroidContext
import korlibs.io.async.asyncEntryPoint
import kotlinx.coroutines.CoroutineScope

fun Korio(context: Context, entry: suspend CoroutineScope.() -> Unit) = asyncEntryPoint { withAndroidContext(context) { entry(CoroutineScope(coroutineContext)) } }
