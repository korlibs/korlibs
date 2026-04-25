package korlibs.io.file.std

@JsName("process")
private external val process: dynamic

actual object StandardPaths : StandardPathsBase {
    override val cwd: String get() = when {
        else -> "."
    }
}
