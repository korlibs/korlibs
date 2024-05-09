package korlibs.io.file

suspend fun VfsFile.getUnderlyingUnscapedFile(): FinalVfsFile = vfs.getUnderlyingUnscapedFile(this.path)
fun VfsFile.toUnscaped() = FinalVfsFile(this)
fun FinalVfsFile.toFile() = this.file

//inline class FinalVfsFile(val file: VfsFile) {
data class FinalVfsFile(val file: VfsFile) {
    constructor(vfs: Vfs, path: String) : this(vfs[path])
    val vfs: Vfs get() = file.vfs
    val path: String get() = file.path
}
