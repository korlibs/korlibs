package korlibs.io.dynamic

import kotlin.test.*

class DynCommonTest {
	val data1 = mapOf("a" to mapOf("b" to 10)).dyn

	@Test
	fun test() {
		assertEquals(10, data1["a"]["b"].int)
	}

	@Test
	fun testBinop() {
		assertEquals(11, (10.0.dyn + 1.dyn).int)
		assertEquals(9, (10.0.dyn - 1.dyn).int)
		assertEquals(20, (10.0.dyn * 2.dyn).int)
		assertEquals(5, (10.0.dyn / 2.0.dyn).int)
		assertEquals(0, (10.0.dyn % 2.0.dyn).int)
		assertEquals(100, (10.0.dyn pow 2.0.dyn).int)
		assertEquals(2, (3.dyn bitAnd 6.dyn).int)
		assertEquals(7, (3.dyn bitOr 6.dyn).int)
		assertEquals(5, (3.dyn bitXor 6.dyn).int)
		assertEquals(true, (3.dyn and 6.dyn))
		assertEquals(true, (3.dyn or 6.dyn))
	}

	@Test
	fun testSEq() {
		val THREE = 3.dyn
		val FOUR = 4.dyn
		val THREE_STR = "3".dyn
		val FOUR_STR = "4".dyn
		assertEquals(true, (THREE seq THREE), "a")
		assertEquals(false, (THREE seq FOUR), "b")

		assertEquals(false, (THREE sne THREE), "c")
		assertEquals(true, (THREE sne FOUR), "d")

		assertEquals(false, (THREE seq THREE_STR), "e")
		assertEquals(false, (THREE seq FOUR_STR), "f")
	}

	@Test
	@Ignore // This fails in WASM because probably it is not caching small numbers
	fun testSEq2() {
		assertEquals(true, (3.dyn seq 3.dyn), "a")
		assertEquals(false, (3.dyn seq 4.dyn), "b")

		assertEquals(false, (3.dyn sne 3.dyn), "c")
		assertEquals(true, (3.dyn sne 4.dyn), "d")

		assertEquals(false, (3.dyn seq "3".dyn), "e")
		assertEquals(false, (3.dyn seq "4".dyn), "f")
	}

	@Test
	fun testEq() {
		assertEquals(true, (3.dyn eq "3".dyn), "a")
		assertEquals(false, (3.dyn eq "4".dyn), "b")

		assertEquals(false, (3.dyn ne "3".dyn), "c")
		assertEquals(true, (3.dyn ne "4".dyn), "d")
	}

	@Test
	fun testList() {
		assertEquals(emptyList(), null.dyn.toList())
		assertEquals(listOf("1", "2", "3"), "123".dyn.toList().map { it.str })
		assertEquals(listOf("1", "2", "3"), listOf(1, 2, 3).dyn.toList().map { it.str })
		assertEquals(listOf("(a, 1)", "(b, 2)"), mapOf("a" to 1, "b" to 2).dyn.toList().map { it.str })
	}

	@Test
	fun testUnaryOps() {
		assertEquals(-5, (-5.0.dyn).int)
		assertEquals(5, (+5.0.dyn).int)
		assertEquals(-1, 0.dyn.inv().int)
		assertEquals(false, true.dyn.not().bool)
		assertEquals(true, false.dyn.not().bool)
	}

	@Test
	fun testComparisons() {
		assertEquals(true, (3.dyn lt 4.dyn))
		assertEquals(false, (4.dyn lt 3.dyn))
		assertEquals(true, (3.dyn le 3.dyn))
		assertEquals(true, (3.dyn le 4.dyn))
		assertEquals(false, (4.dyn le 3.dyn))
		assertEquals(true, (4.dyn gt 3.dyn))
		assertEquals(false, (3.dyn gt 4.dyn))
		assertEquals(true, (3.dyn ge 3.dyn))
		assertEquals(true, (4.dyn ge 3.dyn))
		assertEquals(false, (3.dyn ge 4.dyn))
		assertEquals(0, 3.dyn.compareTo(3.dyn))
		assertEquals(-1, 3.dyn.compareTo(4.dyn))
		assertEquals(1, 4.dyn.compareTo(3.dyn))
	}

	@Test
	fun testContains() {
		assertEquals(true, "hello".dyn.contains("ell"))
		assertEquals(false, "hello".dyn.contains("xyz"))
		assertEquals(true, listOf(1, 2, 3).dyn.contains(2.dyn))
		assertEquals(false, listOf(1, 2, 3).dyn.contains(4.dyn))
		assertEquals(true, setOf(1, 2, 3).dyn.contains(2.dyn))
		assertEquals(true, mapOf("a" to 1).dyn.contains("a".dyn))
		assertEquals(false, mapOf("a" to 1).dyn.contains("b".dyn))
	}

	@Test
	fun testCoalesce() {
		assertEquals(5.dyn, 5.dyn.coalesce(10.dyn))
		assertEquals(10.dyn, null.dyn.coalesce(10.dyn))
	}

	@Test
	fun testToString() {
		assertEquals("5", 5.dyn.toString())
		assertEquals("", null.dyn.toString())
		assertEquals("hello", "hello".dyn.toString())
		assertEquals("[1, 2, 3]", listOf(1, 2, 3).dyn.toString())
		assertEquals("{\"a\": 1}", mapOf("a" to 1).dyn.toString())
		assertEquals("5", 5.dyn.toStringOrNull())
		assertEquals(null, null.dyn.toStringOrNull())
	}

	@Test
	fun testConversions() {
		assertEquals(true, 1.dyn.toBool())
		assertEquals(false, 0.dyn.toBool())
		assertEquals(true, "true".dyn.toBool())
		assertEquals(false, "false".dyn.toBool())
		assertEquals(5, 5.dyn.toNumber())
		assertEquals(5.toByte(), 5.dyn.toByte())
		assertEquals('a', "a".dyn.toChar())
		assertEquals(5.toShort(), 5.dyn.toShort())
		assertEquals(5, 5.dyn.toInt())
		assertEquals(5L, 5.dyn.toLong())
		assertEquals(5f, 5.dyn.toFloat())
		assertEquals(5.0, 5.dyn.toDouble())
	}

	@Test
	fun testOrNullConversions() {
		assertEquals(true, true.dyn.toBoolOrNull())
		assertEquals(false, "invalid".dyn.toBoolOrNull())
		assertEquals(false, 0.dyn.toBoolOrNull())
		assertEquals(true, 1.dyn.toBoolOrNull())
		assertEquals(null, Any().dyn.toBoolOrNull())
		assertEquals(5, "5".dyn.toIntOrNull())
		assertEquals(null, "abc".dyn.toIntOrNull())
		assertEquals(null, Any().dyn.toIntOrNull())
		assertEquals(5L, "5".dyn.toLongOrNull())
		assertEquals(null, "abc".dyn.toLongOrNull())
		assertEquals(null, Any().dyn.toLongOrNull())
		assertEquals(5.0, "5.0".dyn.toDoubleOrNull())
		assertEquals(null, "abc".dyn.toDoubleOrNull())
		assertEquals(null, Any().dyn.toDoubleOrNull())
	}

	@Test
	fun testDefaultConversions() {
		assertEquals(5, "5".dyn.toIntDefault())
		assertEquals(10, "abc".dyn.toIntDefault(10))
		assertEquals(10, Any().dyn.toIntDefault(10))
		assertEquals(5L, "5".dyn.toLongDefault())
		assertEquals(10L, "abc".dyn.toLongDefault(10L))
		assertEquals(10L, Any().dyn.toLongDefault(10L))
		assertEquals(5f, "5".dyn.toFloatDefault())
		assertEquals(5f, "5.0".dyn.toFloatDefault(42f))
		assertEquals(10f, "abc".dyn.toFloatDefault(10f))
		assertEquals(10f, Any().dyn.toFloatDefault(10f))
		assertEquals(25.0, "25".dyn.toDoubleDefault())
		assertEquals(26.0, "26.0".dyn.toDoubleDefault())
		assertEquals(27.0, "abc".dyn.toDoubleDefault(27.0))
		assertEquals(28.0, Any().dyn.toDoubleDefault(28.0))
	}

	@Test
	fun testProperties() {
		assertEquals("5", 5.dyn.str)
		assertEquals(5, 5.dyn.int)
		assertEquals(true, true.dyn.bool)
		assertEquals(5f, 5.dyn.float)
		assertEquals(5.0, 5.dyn.double)
		assertEquals(5L, 5.dyn.long)
	}

	@Test
	fun testAccessors() {
		val mapDyn = mapOf("a" to 1, "b" to 2).dyn
		assertEquals(1.dyn, mapDyn["a"])
		assertEquals(2.dyn, mapDyn.get("b"))
		assertEquals(1.dyn, mapDyn.getOrNull("a"))
		assertEquals(null, mapDyn.getOrNull("c"))
		// getOrThrow would throw for "c", but since it's test, maybe skip or use try
		val listDyn = listOf(10, 20).dyn
		assertEquals(10.dyn, listDyn[0])
		assertEquals(20.dyn, listDyn[1])
		assertEquals(null, null.dyn.orNull)
		assertEquals(5.dyn, 5.dyn.orNull)
		assertEquals(mapOf("a" to 1, "b" to 2) as Map<Any?, Any?>, mapDyn.mapAny)
		assertEquals(listOf(10, 20), listDyn.listAny)
		assertEquals(listOf("a", "b"), mapDyn.keysAny)
		assertEquals(mapOf("a".dyn to 1.dyn, "b".dyn to 2.dyn), mapDyn.map)
		assertEquals(listOf(10.dyn, 20.dyn), listDyn.list)
		assertEquals(listOf("a".dyn, "b".dyn), mapDyn.keys)
	}

	@Test
	fun testIsNull() {
		assertEquals(true, null.dyn.isNull)
		assertEquals(false, 5.dyn.isNull)
		assertEquals(false, null.dyn.isNotNull)
		assertEquals(true, 5.dyn.isNotNull)
	}

	@Test
	fun testToIterable() {
		assertEquals(listOf(1.dyn, 2.dyn, 3.dyn), listOf(1, 2, 3).dyn.toIterable().toList())
		assertEquals(listOf('h'.dyn, 'e'.dyn, 'l'.dyn, 'l'.dyn, 'o'.dyn), "hello".dyn.toIterable().toList())
		assertEquals(listOf(("a" to 1).dyn, ("b" to 2).dyn), mapOf("a" to 1, "b" to 2).dyn.toIterable().toList())
	}

	@Test
	fun testToComparable() {
		val comp = "a".dyn.toComparable()
		assertEquals(0, comp.compareTo("a"))
		assertEquals(-1, comp.compareTo("b"))
	}

	@Test
	fun testCompanion() {
		// global might be platform dependent, skip or assume
		assertEquals(0, Dyn.compare(3.dyn, 3.dyn))
		assertEquals(true, Dyn.contains(listOf(1, 2).dyn, 1.dyn))
		assertEquals(false, Dyn.contains(listOf(1, 2).dyn, 3.dyn))
	}

	@Test
	fun testArrays() {
		val intList = listOf(1, 2, 3).dyn
		assertEquals(intArrayOf(1, 2, 3).toList(), intList.intArray.toList())
		assertEquals(floatArrayOf(1f, 2f, 3f).toList(), intList.floatArray.toList())
		assertEquals(doubleArrayOf(1.0, 2.0, 3.0).toList(), intList.doubleArray.toList())
		assertEquals(longArrayOf(1L, 2L, 3L).toList(), intList.longArray.toList())
	}

	@Test
	fun testSet() {
		val mutableMap = mutableMapOf<String, Any?>("a" to 1)
		val mapDyn = mutableMap.dyn
		mapDyn["b"] = 2.dyn
		assertEquals(2.dyn, mapDyn["b"])
		val mutableList = mutableListOf<Any?>(10, 20)
		val listDyn = mutableList.dyn
		listDyn[1] = 30.dyn
		assertEquals(30.dyn, listDyn[1])
	}
}
