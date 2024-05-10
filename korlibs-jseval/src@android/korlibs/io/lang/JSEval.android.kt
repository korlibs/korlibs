package korlibs.io.lang

import android.annotation.*
import android.os.*
import android.webkit.*
import korlibs.io.android.*
import korlibs.io.serialization.json.*
import kotlinx.coroutines.*

actual object JSEval {
    actual val globalThis: Any? get() = null
    actual val available: Boolean get() = false

    @TargetApi(Build.VERSION_CODES.KITKAT)
    actual suspend operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        //val webView = WebView(androidContext())
        //val keys = params.keys.toList()
        //val values = params.values.toList()
        //webView.addJavascriptInterface(values, "__jsparams__")
        //val deferred = CompletableDeferred<Any?>()
        //webView.evaluateJavascript("JSON.stringify((function(${keys.joinToString(", ")}) { $code })(__jsparams__))") {
        //    deferred.complete(Json.parse(it))
        //}
        //return deferred.await()
        return null
    }
}
