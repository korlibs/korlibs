@file:OptIn(ExperimentalForeignApi::class)

package korlibs.memory

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.usePinned

class MemExtTest {
    @Test
    fun testStartAddressOf() {
        assertNotNull(byteArrayOf(1).usePinned { it.startAddressOf })
        assertNotNull(byteArrayOf().usePinned { it.startAddressOf })
    }
}
