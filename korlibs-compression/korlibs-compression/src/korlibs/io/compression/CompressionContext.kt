package korlibs.io.compression

open class CompressionContext(var level: Int = 6) {
    var name: String? = null
    var custom: Any? = null
}
