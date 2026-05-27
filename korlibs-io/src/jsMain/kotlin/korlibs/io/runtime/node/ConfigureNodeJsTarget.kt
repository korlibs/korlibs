package korlibs.io.runtime.node

import korlibs.io._jsRuntime

actual fun configureNodeJsTarget() {
    _jsRuntime = JsRuntimeNode
}
