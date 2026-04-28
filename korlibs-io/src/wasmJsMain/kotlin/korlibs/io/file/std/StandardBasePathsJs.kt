package korlibs.io.file.std

actual object StandardPaths : StandardPathsBase {
    override val cwd: String get() = when {
        //isNodeJs -> require_node("fs").realpathSync(process.cwd()).unsafeCast<String>()
        else -> "."
    }
}
