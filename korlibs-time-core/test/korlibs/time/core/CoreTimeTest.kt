package korlibs.time.core

import korlibs.time.*
import kotlin.test.*

@OptIn(CoreTimeInternalApi::class)
class CoreTimeTest {
    @Test
    fun test() {
        val start = CoreTime.currentTimeMillisDouble()
        CoreTime.sleep(10.milliseconds)
        val end = CoreTime.currentTimeMillisDouble()
        assertTrue { (end - start) > 0 }
    }
}
