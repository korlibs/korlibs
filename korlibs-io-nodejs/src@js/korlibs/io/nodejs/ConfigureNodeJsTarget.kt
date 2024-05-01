package korlibs.io.nodejs

import korlibs.io.*

fun configureNodeJsTarget() {
    _jsRuntime = JsRuntimeNode
}

/*
actual object StandardPaths : StandardPathsBase {
    override val cwd: String get() = when {
        isNodeJs -> require_node("fs").realpathSync(process.cwd()).unsafeCast<String>()
        else -> "."
    }

    //override val executableFile: String get() = when {
    //    isNodeJs -> {
    //        console.log("argv", process.argv, process.argv0)
    //        console.log(Error())
    //        (process.argv[1] ?: null)?.unsafeCast<String>() ?: super.executableFile
    //    }
    //    else -> super.executableFile
    //}
}
*/
