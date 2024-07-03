package korlibs.image.bitmap

interface NativeImageRef {
    val width: Int
    val height: Int
    val data: Any?
}
