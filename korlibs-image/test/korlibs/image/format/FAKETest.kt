package korlibs.image.format

import korlibs.io.async.*
import korlibs.io.file.std.*
import korlibs.math.geom.*
import korlibs.platform.*
import kotlinx.coroutines.test.*
import kotlin.test.*

class FAKETest {
    init {
        RegisteredImageFormats.registerFirst(FAKE)
    }

    @Test
    fun testInfoNotFake() = runTest {
        assertEquals(null, FAKE.decodeHeaderSuspend(SingleFileMemoryVfsWithName("", name = "4x3.png"))?.size)
    }

    @Test
    fun testInfoFake() = runTest {
        assertEquals(Size(4, 3), FAKE.decodeHeaderSuspend(SingleFileMemoryVfsWithName("", name = "4x3.fake"))?.size)
    }

    @Test
    fun testSizeInName() = runTest {
        if (Platform.isWasm) {
            println("Skipping in wasm as it fails for now")
            return@runTest
        }

        assertEquals("4x3", SingleFileMemoryVfsWithName("", name = "4x3.fake").readBitmapSlice().sizeString)
    }

    @Test
    fun testSizeInContent() = runTest {
        if (Platform.isWasm) {
            println("Skipping in wasm as it fails for now")
            return@runTest
        }

        assertEquals("4x3", SingleFileMemoryVfs("4x3", ext = "fake").readBitmapSlice().sizeString)
    }

    @Test
    fun testDefaultSize() = runTest {
        if (Platform.isWasm) {
            println("Skipping in wasm as it fails for now")
            return@runTest
        }

        assertEquals("128x128", SingleFileMemoryVfs("", ext = "fake").readBitmapSlice().sizeString)
    }
}
