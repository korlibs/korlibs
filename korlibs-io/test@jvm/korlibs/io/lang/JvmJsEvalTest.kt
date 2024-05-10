package korlibs.io.lang

import javax.script.*
import kotlin.test.*

class JvmJsEvalTest {
    @Test
    fun test() {
        //println(ScriptEngineManager().engineFactories.map { it.names })
        //println(ScriptEngineManager().getEngineByName("rhino"))
        //println(ScriptEngineManager().getEngineByName("js"))
        assertEquals(true, JSEval.available)
        assertEquals(10.0, JSEval("return a * b;", "a" to 2, "b" to 5))
        assertEquals(10.0, JSEval.expr("a * b", "a" to 2, "b" to 5))
        assertEquals("world2", JSEval.expr("hello + 2", "hello" to "world"))
        //assertEquals(jsGlobal, JSEval.globalThis)
    }
}