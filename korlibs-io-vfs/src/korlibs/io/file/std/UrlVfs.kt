package korlibs.io.file.std

import korlibs.io.file.*

abstract class UrlVfs(
    val url: String,
    dummy: Unit,
    val failFromStatus: Boolean = true
) : Vfs() {

    override val absolutePath: String = url

    fun getFullUrl(path: String): String {
        val result = url.trim('/') + '/' + path.trim('/')
        //println("UrlVfs.getFullUrl: url=$url, path=$path, result=$result")
        return result
    }
}