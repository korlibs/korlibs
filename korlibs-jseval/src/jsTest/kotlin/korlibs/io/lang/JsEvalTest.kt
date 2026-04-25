package korlibs.io.lang

import korlibs.platform.Platform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JsEvalTest {
    @Test
    fun test() = runTest {
        assertEquals(true, Platform.isJs)
        assertEquals(true, JSEval.available)
        assertEquals(32, JSEval("return a ** b;", "a" to 2, "b" to 5))
        assertEquals(32, JSEval.expr("a ** b", "a" to 2, "b" to 5))
        assertEquals("world2", JSEval.expr("hello + 2", "hello" to "world"))
        //assertEquals(jsGlobal, JSEval.globalThis)
    }
}
