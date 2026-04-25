package korlibs.image.bitmap

/** A reference to a native image. So we can use it without depending on other libraries. */
interface NativeImageRef {
    /** Width of the image */
    val width: Int
    /** Height of the image */
    val height: Int
    /** Native data of the image. For example a BufferImage in the case of the JVM. */
    val data: Any?
}
