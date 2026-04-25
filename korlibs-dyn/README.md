# korlibs-dyn

<!-- BADGES -->
[![test](https://github.com/korlibs/korlibs-dyn/actions/workflows/TEST.yml/badge.svg)](https://github.com/korlibs/korlibs-dyn/actions/workflows/TEST.yml)
[![codecov](https://codecov.io/gh/korlibs/korlibs-dyn/graph/badge.svg)](https://codecov.io/gh/korlibs/korlibs-dyn)
[![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/korlibs-dyn)](https://central.sonatype.com/artifact/org.korge/korlibs-dyn)
[![Discord](https://img.shields.io/discord/728582275884908604?logo=discord&label=Discord)](https://discord.korge.org/)
[![KDoc](https://img.shields.io/badge/docs-kdoc-blue)](https://korlibs.github.io/korlibs-dyn/)
[![Documentation](https://img.shields.io/badge/docs-documentation-purple)](https://docs.korge.org/dyn/)
<!-- /BADGES -->

Dyn is a library to handle values dynamically like in non-statically typed languages.

It allows to access maps, lists, to perform arithmetic operations, and to access fields dynamically
without knowing the value at compile time.

This is useful when we want to access data dynamically in a simple way, without doing typing conversion or relying on strict typings.

Some examples from tests:

```kotlin
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
fun testContains() {
    val list = listOf("a", "b")
    val set = setOf("a", "b")
    val map = mapOf("a" to 1, "b" to 2)

    assertEquals(true, "hello".dyn.contains("ll"))
    assertEquals(false, "hello".dyn.contains("le"))

    assertEquals(true, map.dyn.contains("a"))
    assertEquals(true, map.dyn.contains("b"))
    assertEquals(false, map.dyn.contains("c"))

    assertEquals(true, set.dyn.contains("a"))
    assertEquals(true, set.dyn.contains("b"))
    assertEquals(false, set.dyn.contains("c"))

    assertEquals(true, list.dyn.contains("a"))
    assertEquals(true, list.dyn.contains("b"))
    assertEquals(false, list.dyn.contains("c"))
}
```
