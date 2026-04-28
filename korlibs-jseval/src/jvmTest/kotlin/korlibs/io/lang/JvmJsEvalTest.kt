package korlibs.io.lang

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmJsEvalTest {
    @Test
    fun test() = runTest {
        //println(ScriptEngineManager().engineFactories.map { it.names })
        //println(ScriptEngineManager().getEngineByName("rhino"))
        //println(ScriptEngineManager().getEngineByName("js"))
        assertEquals(true, JSEval.available, "available")
        assertEquals(10.0, JSEval("return a * b;", "a" to 2, "b" to 5))
        assertEquals(10.0, JSEval.invokeSuspend("return a * b;", "a" to 2, "b" to 5))
        assertEquals(10.0, JSEval.expr("a * b", "a" to 2, "b" to 5))
        assertEquals("world2", JSEval.expr("hello + 2", "hello" to "world"))
        //assertEquals(jsGlobal, JSEval.globalThis)
    }
}