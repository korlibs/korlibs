package korlibs.io.net

import korlibs.datastructure.fastCastTo
import korlibs.encoding.base64Url
import korlibs.encoding.fromBase64
import korlibs.io.async.CIO
import korlibs.io.lang.JSEval
import korlibs.io.lang.expr
import korlibs.io.lang.exprSuspend
import korlibs.platform.Platform
import kotlin.jvm.JvmInline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@JvmInline
value class DataURL(val url: String) : AutoCloseable {
    companion object {
        operator fun invoke(data: ByteArray, contentType: String): DataURL = when {
            Platform.isInsideBrowser -> DataURL(JSEval.expr("URL.createObjectURL(new Blob([data], { type: type }))", "data" to data, "type" to contentType).toString())
            else -> DataURL("data:$contentType;base64,${data.base64Url}")
        }

        inline fun <T> temporal(data: ByteArray, contentType: String, callback: (url: DataURL) -> T): T = DataURL(data, contentType).use(callback)
    }

    override fun close() {
        if (url.startsWith("data:")) return
        if (Platform.isInsideBrowser) JSEval.expr("URL.revokeObjectURL(url)", "url" to url)
    }

    suspend fun readBytes(): ByteArray = when {
        Platform.isInsideBrowser -> JSEval.exprSuspend("fetch(url).then(it => it.arrayBuffer()).then(it => new Int8Array(it))", "url" to url).fastCastTo<ByteArray>()
        else -> withContext(Dispatchers.CIO) { url.substringAfter(";base64,", "").fromBase64() }
    }
}
