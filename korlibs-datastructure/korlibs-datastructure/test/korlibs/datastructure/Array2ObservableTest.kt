package korlibs.datastructure

import korlibs.math.geom.*
import kotlin.test.*

class Array2ObservableTest {
    @Test
    fun testArray() {
        val log = arrayListOf<String>()
        val data = Array(10) { "" }.observe {
            log += "$it:${this[it.first]}"
        }
        data[5] = "hello"
        assertEquals("hello", data[5])
        data[5] = "hello"
        data[7 until 200] = "world"
        assertEquals("""
            5..5:hello
            7..9:world
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testIntArray2() {
        val log = arrayListOf<String>()
        val data = IntArray2(10, 10, 0).observe {
            log += "$it:${this[it.topLeft]}"
        }
        data[5, 5] = 10
        assertEquals(10, data[5, 5])
        data[5, 5] = 10
        data[RectangleInt(8, 8, 20, 20)] = 30
        assertEquals("""
            Rectangle(x=5, y=5, width=1, height=1):10
            Rectangle(x=8, y=8, width=2, height=2):30
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testIntArray() {
        val log = arrayListOf<String>()
        val array = IntArray(10).observe {
            log += "$it:${this[it.first]}"
        }
        val V1 = 10
        val V2 = 11
        array[5] = V1
        assertEquals(V1, array[5])
        assertEquals("""
            5..5:10
        """.trimIndent(), log.joinToString("\n"))
        array[5] = V1
        assertEquals("""
            5..5:10
        """.trimIndent(), log.joinToString("\n"))
        array.lock {
            array[5] = V2
            array[7] = V1
        }
        assertEquals("""
            5..5:10
            5..7:11
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testArray2() {
        val log = arrayListOf<String>()
        val data = Array2<String>(10, 10, "").observe {
            log += "$it:${this.getAt(it.topLeft)}"
        }
        data[5, 5] = "a"
        assertEquals("a", data[5, 5])
        data[5, 5] = "a"
        data[RectangleInt(8, 8, 20, 20)] = "b"
        assertEquals("""
            Rectangle(x=5, y=5, width=1, height=1):a
            Rectangle(x=8, y=8, width=2, height=2):b
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testWidthHeight() {
        val ints = IntArray2(20, 10, 0)
        ints[-10, -15] = 20
        ints[20, 20] = 20
        val array = ints.observe {  }
        ints[-10, -15] = 20
        array[20, 20] = 20
        assertEquals(20, array.width)
        assertEquals(10, array.height)
        assertEquals(ints, array.base)
    }
}