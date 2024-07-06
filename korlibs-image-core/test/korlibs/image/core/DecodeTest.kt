package korlibs.image.core

import kotlinx.coroutines.test.*
import kotlin.io.encoding.*
import kotlin.test.*

@OptIn(ExperimentalEncodingApi::class)
class DecodeTest {
    val pngData = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=")

    @Test
    fun testInfo() = runTest {
        assertEquals(CoreImageInfo(1, 1), CoreImage.info(pngData))
    }

    @Test
    fun test() = runTest {
        assertEquals(CoreImageInfo(1, 1), CoreImage.decodeBytes(pngData).info())
    }
}