package korlibs.io.vfs

import korlibs.io.async.suspendTest
import korlibs.io.file.fullName
import korlibs.io.file.std.MemoryVfs
import korlibs.io.lang.toByteArray
import korlibs.io.stream.openAsync
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class CopyToTreeTest {
	@Test
	fun name() = suspendTest {
		val mem = MemoryVfs(
			linkedMapOf(
				"root.txt" to "hello".toByteArray().openAsync(),
				"hello/world.txt" to "hello".toByteArray().openAsync()
			)
		)
		val out = MemoryVfs()
		mem.copyToRecursively(out)
		assertEquals(
			"[/root.txt, /hello, /hello/world.txt]",
			out.listRecursive().map { it.fullName }.toList().toString()
		)
	}
}
