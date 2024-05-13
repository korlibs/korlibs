package korlibs.io.net

import korlibs.datastructure.*
import korlibs.encoding.*
import korlibs.io.async.*
import korlibs.io.lang.*
import korlibs.platform.*
import kotlinx.coroutines.*

inline class DataURL(val url: String) : AutoCloseable {
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
