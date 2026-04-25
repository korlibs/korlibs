package korlibs.io.file

import korlibs.io.async.*
import korlibs.io.file.std.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*

class VfsProxyTest {
    @Test
    fun test() = runTest {
        val memory = MemoryVfsMix("hello" to "demo")
        val vfs = object : Vfs.Proxy() {
            var initialized = false
            override suspend fun access(path: String): VfsFile {
                check(initialized)
                return memory[path]
            }
            override suspend fun init() {
                withContext(Dispatchers.CIO) {
                    delay(100L)
                    initialized = true
                }
            }
        }
        val job = CoroutineScope(coroutineContext).launch { vfs["hello"].readString() }
        delay(1L)
        job.cancel()
        assertEquals("demo", vfs["hello"].readString())
    }
}