package korlibs.image.core

import kotlinx.cinterop.*
import kotlin.test.*

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
class AppleCoreImageFormatProviderTest {
    @Test
    fun test() {
        val rect = AppleCoreImageFormatProvider.CGRectMakeExt(1.0, 2.0, 3.0, 4.0)
        rect.useContents {
            assertEquals(1.0, this.origin.x.toDouble())
            assertEquals(2.0, this.origin.y.toDouble())
            assertEquals(3.0, this.size.width.toDouble())
            assertEquals(4.0, this.size.height.toDouble())
        }
    }
}
