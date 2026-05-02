package korlibs.io.serialization.xml

import korlibs.io.async.suspendTestNoBrowser
import korlibs.io.file.std.resourcesVfs
import korlibs.io.lang.*
import korlibs.io.stream.*
import kotlin.test.Test
import kotlin.test.assertEquals

class Xml2Test {
	@Test
	fun name2() = suspendTestNoBrowser {
		val xml = resourcesVfs["test.xml"].readXml()
		assertEquals("test", xml.name)
		assertEquals("hello", xml.text)
	}

	@Test
	fun testNamedDescendantStream() {
		val xml = Xml.Stream.parse("<xml><a><b/><b/></a><c><b/><b/></c></xml>".toByteArray(UTF8).toCharReader(UTF8))
		//val xml = Xml.Stream.parse("<xml><a><b/><b/></a><c><b/><b/></c></xml>")
		assertEquals(
			"""
                OpenTag(name=xml, attributes={})
                OpenTag(name=a, attributes={})
                OpenCloseTag(name=b, attributes={})
                OpenCloseTag(name=b, attributes={})
                CloseTag(name=a)
                OpenTag(name=c, attributes={})
                OpenCloseTag(name=b, attributes={})
                OpenCloseTag(name=b, attributes={})
                CloseTag(name=c)
                CloseTag(name=xml)
            """.trimIndent(),
			xml.toList().joinToString("\n")
		)
	}

	@Test
	fun testNamedDescendantStreamInfinite() {
		val xml = Xml.Stream.parse(sequenceSyncStream {
			while (true) {
				yield("<xml>".toByteArray())
			}
		}.toCharReader(UTF8))
		assertEquals(
			"""
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
                OpenTag(name=xml, attributes={})
            """.trimIndent(),
			xml.take(10).toList().joinToString("\n")
		)
	}

}
