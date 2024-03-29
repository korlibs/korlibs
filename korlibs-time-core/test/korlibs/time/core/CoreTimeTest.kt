package korlibs.time.core

import korlibs.time.*
import kotlin.test.*

@OptIn(CoreTimeInternalApi::class)
class CoreTimeTest {
    @Test
    fun test() {
        val start = CoreTime.currentTimeMillis()
        CoreTime.sleep(10.milliseconds)
        val end = CoreTime.currentTimeMillis()
        assertTrue { (end - start) > 0 }
    }
}
