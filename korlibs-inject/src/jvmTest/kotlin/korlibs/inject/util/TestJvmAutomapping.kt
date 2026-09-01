package korlibs.inject.util

import java.io.File
import korlibs.inject.Injector
import korlibs.inject.Prototype
import korlibs.inject.Singleton
import korlibs.inject.jvmAutomapping
import kotlin.test.assertNotSame
import kotlin.test.assertSame
import kotlinx.coroutines.runBlocking
import org.junit.Test

@Suppress("RemoveExplicitTypeArguments")
class JvmAutomappingTest {
    @Test
    fun test() = runBlocking {
        val injector = Injector().jvmAutomapping()
        injector.mapInstance(Folders(File(".")))
        injector.get<ConfigService>()
        assertSame(injector.get<ConfigService>(), injector.get<ConfigService>())
        assertNotSame(injector.get<MyPrototype>(), injector.get<MyPrototype>())
    }

    class Folders(val a: File)
    @Singleton
    class ConfigService(val folders: Folders)
    @Prototype
    class MyPrototype(val folders: Folders)
}
