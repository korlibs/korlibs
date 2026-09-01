package korlibs.template

import korlibs.template.dynamic.KorteDynamicType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MultiThreadingTests {
    data class Model(val x: Int) : KorteDynamicType<Model> by KorteDynamicType({
        register(Model::x)
    })
    @Test
    fun testTemplateEvaluationOnBackgroundThread() = runBlocking {
        withContext(Dispatchers.Default) {
            val template = KorteTemplate("{{x+1}}")
            val rendered = template(Model(x = 2))
            assertEquals("3", rendered)
        }
    }
}
