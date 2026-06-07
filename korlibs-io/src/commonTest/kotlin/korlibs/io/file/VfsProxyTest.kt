package korlibs.io.file

import korlibs.io.async.CIO
import korlibs.io.file.std.MemoryVfsMix
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

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
