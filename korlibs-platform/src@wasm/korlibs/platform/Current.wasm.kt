package korlibs.platform

import kotlinx.browser.*
import org.khronos.webgl.*

@JsFun("() => { return (typeof Deno === 'object' && Deno.statSync) }")
internal external fun isDenoJs(): Boolean
@JsFun("() => { return (typeof window === 'object') }")
internal external fun isWeb(): Boolean
@JsFun("() => { return (typeof importScripts === 'function') }")
internal external fun isWorker(): Boolean
@JsFun("() => { return ((typeof process !== 'undefined') && process.release && (process.release.name.search(/node|io.js/) !== -1)) }")
internal external fun isNodeJs(): Boolean
internal fun isShell(): Boolean = !isWeb() && !isNodeJs() && !isWorker()

// @TODO: Check navigator.userAgent
internal actual val currentOs: Os = Os.UNKNOWN
internal actual val currentArch: Arch = Arch.UNKNOWN

internal actual val currentRuntime: Runtime = Runtime.WASM
internal actual val currentIsDebug: Boolean = false
internal actual val currentIsLittleEndian: Boolean = Uint8Array(Uint32Array(1).also { it[0] = 0x11223344 }.buffer)[0].toInt() == 0x44
internal actual val multithreadedSharedHeap: Boolean = false // Workers have different heaps

internal actual val currentRawPlatformName: String = when {
    isDenoJs() -> "wasm-deno"
    isWeb() -> "wasm-web"
    isNodeJs() -> "wasm-node"
    isWorker() -> "wasm-worker"
    isShell() -> "wasm-shell"
    else -> "wasm"
}

private external class Navigator {
    val userAgent: String
    val language: String
    val languages: JsArray<JsString>
}
private external class Process {
    val platform: String
    val env: JsAny
}
private external object Deno {
    val env: JsAny
}
private external val navigator: Navigator // browser
private external val process: Process // nodejs

internal actual val currentRawOsName: String = when {
    isDenoJs() -> "deno"
    isWeb() || isWorker() -> navigator.userAgent
    else -> process.platform
}

internal actual val envs: Map<String, String> by lazy {
    when {
        isDenoJs() -> jsObjectToMap(Deno.env).mapValues { it.value.toString() }.toMap()
        isNodeJs() -> jsObjectToMap(process.env).mapValues { it.value.toString() }.toMap()
        else -> LinkedHashMap<String, String>().also { out ->
            val hash = (document.location?.search ?: "").trimStart('?')
            for (part in hash.split("&")) {
                val parts = part.split("=")
                val key = decodeURIComponent(parts[0])
                val value = decodeURIComponent(parts.getOrNull(1) ?: key)
                out[key] = value
            }
        }
    }
}

private external fun decodeURIComponent(str: String): String

fun jsObjectToMap(obj: JsAny): Map<String, JsAny?> = obj.keys().associate { it to obj[it] }
private fun JsAny.keys(): List<String> = jsObjectKeys(this).toList().map { it.toString() }
private operator fun JsAny.get(key: String): JsAny? = jsObjectGet(this, key.toJsString())

@JsFun("(obj, key) => { return obj ? obj[key] : null; }")
@Suppress("UNUSED_PARAMETER")
private external fun jsObjectGet(obj: JsAny, key: JsString?): JsAny?

@JsFun("(obj) => { return Object.keys(obj); }")
@Suppress("UNUSED_PARAMETER")
private external fun jsObjectKeys(obj: JsAny?): JsArray<JsString>

private fun <T : JsAny> JsArray<T>.toList(): List<T?> = List(length) { this[it] }

internal actual val languages: List<String> get() = navigator.languages.toList().map { it.toString() }
