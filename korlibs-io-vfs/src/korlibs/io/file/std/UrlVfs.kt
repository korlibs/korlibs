package korlibs.io.file.std

import korlibs.io.file.*

abstract class BaseUrlVfs(
    val url: String,
    val failFromStatus: Boolean = true
) : Vfs() {

    override val absolutePath: String = url

    fun getFullUrl(path: String): String {
        val result = url.trim('/') + '/' + path.trim('/')
        //println("UrlVfs.getFullUrl: url=$url, path=$path, result=$result")
        return result
    }
}