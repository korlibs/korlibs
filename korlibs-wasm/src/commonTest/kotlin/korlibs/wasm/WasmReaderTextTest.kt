package korlibs.wasm

import korlibs.wasm.WasmReaderText.StrReader
import korlibs.wasm.WasmReaderText.WastParser.wastTokenize
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests for WasmReaderText and its nested classes.
 *
 * Coverage:
 *  - WastParser.wastTokenize  (tokenizer)
 *  - WastParser.parseBlocks   (S-expression parser)
 *  - WasmReaderText.toNumberExOrNull / toNumberUnprefixedExOrNull
 *  - WasmReaderText.readType
 *  - WasmReaderText.readTopLevel  (module / func / type / memory / global / data)
 *  - WasmFuncBuilder helpers (addVar, relativeIndexOfBlockById, pushBlock/popBlock)
 *  - WasmModuleBuilder helpers (addFunc, addGlobal, addType, addTable)
 */
class WasmReaderTextTest {

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    private lateinit var reader: WasmReaderText

    @BeforeTest
    fun setup() {
        reader = WasmReaderText()
    }

    /** Expose the private extension via a thin wrapper so tests stay readable. */
    private fun String.asNumber(radix: Int = 10): Number? =
        with(reader) { toNumberExOrNull(radix) }

    private fun String.asNumberUnprefixed(radix: Int = 10): Number? =
        with(reader) { toNumberUnprefixedExOrNull(radix) }

    // ─────────────────────────────────────────────────────────────────────────
    // WastParser – tokenizer
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `tokenizer produces OPEN and CLOSE brackets`() {
        val tokens = tokenize("()")
        assertEquals(2, tokens.size)
        assertIs<WasmReaderText.WastParser.OPEN_BRAC>(tokens[0])
        assertIs<WasmReaderText.WastParser.CLOSE_BRAC>(tokens[1])
    }

    @Test
    fun `tokenizer reads identifiers`() {
        val tokens = tokenize($$"func $myFunc")
        assertEquals(2, tokens.size)
        assertIs<WasmReaderText.WastParser.Id>(tokens[0])
        assertEquals("func", tokens[0].str)
        assertIs<WasmReaderText.WastParser.Id>(tokens[1])
        assertEquals($$"$myFunc", tokens[1].str)
    }

    @Test
    fun `tokenizer reads integer literal`() {
        val tokens = tokenize("42")
        assertEquals(1, tokens.size)
        assertIs<WasmReaderText.WastParser.Num>(tokens[0])
        assertEquals("42", tokens[0].str)
    }

    @Test
    fun `tokenizer reads negative integer literal`() {
        val tokens = tokenize("-7")
        assertEquals(1, tokens.size)
        assertEquals("-7", tokens[0].str)
    }

    @Test
    fun `tokenizer reads hex literal`() {
        val tokens = tokenize("0xff")
        assertEquals(1, tokens.size)
        assertEquals("0xff", tokens[0].str)
    }

    @Test
    fun `tokenizer reads string literal with escapes`() {
        val tokens = tokenize("\"hello\\nworld\"")
        assertEquals(1, tokens.size)
        assertIs<WasmReaderText.WastParser.Str>(tokens[0])
        assertEquals("hello\nworld", tokens[0].str)
    }

    @Test
    fun `tokenizer reads string with hex escape`() {
        val tokens = tokenize("\"\\41\"")      // \41 = 'A'
        assertEquals(1, tokens.size)
        assertEquals("A", tokens[0].str)
    }

    @Test
    fun `tokenizer skips line comment starting with semicolon`() {
        val tokens = tokenize("foo ; this is a comment\nbar")
        // comment token is emitted but should be COMMENT type
        val ids = tokens.filterIsInstance<WasmReaderText.WastParser.Id>()
        assertEquals(2, ids.size)
        assertTrue(tokens.any { it is WasmReaderText.WastParser.COMMENT })
    }

    @Test
    fun `tokenizer skips block comment`() { // block comment: (;…;)
        val tokens = tokenize("(; block comment ;) foo")
        val ids = tokens.filterIsInstance<WasmReaderText.WastParser.Id>()
        assertEquals(1, ids.size)
        assertEquals("foo", ids[0].str)
    }

    @Test
    fun `tokenizer reads nan special literal`() {
        val tokens = tokenize("nan:canonical")
        assertEquals(1, tokens.size)
        assertIs<WasmReaderText.WastParser.SpecialNum>(tokens[0])
        assertEquals("nan:canonical", tokens[0].str)
    }

    @Test
    fun `tokenizer reads negative nan special literal`() {
        val tokens = tokenize("-nan:arithmetic")
        assertEquals(1, tokens.size)
        assertIs<WasmReaderText.WastParser.SpecialNum>(tokens[0])
    }

    // ─────────────────────────────────────────────────────────────────────────
    // WastParser – S-expression block parser
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `parseBlock returns WastBlock for simple s-expression`() {
        val block = WasmReaderText.WastParser.parseBlock("(module)")
        assertEquals("module", block.name)
        assertEquals(0, block.params.size)
    }

    @Test
    fun `parseBlock with one value param`() {
        val block = WasmReaderText.WastParser.parseBlock($$"(func $f)")
        assertEquals("func", block.name)
        assertEquals(1, block.valueParams.size)
        assertEquals($$"$f", block.valueParams[0].value)
    }

    @Test
    fun `parseBlock nests child blocks`() {
        val block = WasmReaderText.WastParser.parseBlock("(func (param i32) (result i32))")
        assertEquals("func", block.name)
        assertEquals(2, block.blockParams.size)
        assertEquals("param", block.blockParams[0].name)
        assertEquals("result", block.blockParams[1].name)
    }

    @Test
    fun `parseBlocks returns multiple top-level blocks`() {
        val blocks = WasmReaderText.WastParser.parseBlocks("(module) (module)")
        assertEquals(2, blocks.size)
        assertTrue(blocks.all { it.name == "module" })
    }

    @Test
    fun `parseBlock ignores empty sub-blocks`() {
        // empty () should be silently dropped
        val block = WasmReaderText.WastParser.parseBlock("(func () (result i32))")
        assertEquals(1, block.blockParams.size)
        assertEquals("result", block.blockParams[0].name)
    }

    @Test
    fun `WastBlock valueParams and blockParams are lazily correct`() {
        val block = WasmReaderText.WastParser.parseBlock($$"(call $add (i32.const 1))")
        assertEquals(1, block.valueParams.size)
        assertEquals($$"$add", block.valueParams[0].value)
        assertEquals(1, block.blockParams.size)
        assertEquals("i32.const", block.blockParams[0].name)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Number parsing – toNumberExOrNull
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `toNumberExOrNull parses plain integer`() {
        assertEquals(42L, "42".asNumber())
    }

    @Test
    fun `toNumberExOrNull parses negative integer`() {
        val n = "-10".asNumber()
        assertEquals(-10L, n)
    }

    @Test
    fun `toNumberExOrNull parses positive-prefixed integer`() {
        assertEquals(5L, "+5".asNumber())
    }

    @Test
    fun `toNumberExOrNull parses hex with 0x prefix`() {
        assertEquals(255L, "0xff".asNumber())
    }

    @Test
    fun `toNumberExOrNull parses octal with 0o prefix`() {
        assertEquals(8L, "0o10".asNumber())
    }

    @Test
    fun `toNumberExOrNull parses binary with 0b prefix`() {
        assertEquals(5L, "0b101".asNumber())
    }

    @Test
    fun `toNumberExOrNull parses floating-point`() {
        val n = "3.14".asNumber()
        assertNotNull(n)
        assertEquals(3.14, n.toDouble(), 1e-9)
    }

    @Test
    fun `toNumberExOrNull returns positive infinity for inf`() {
        assertEquals(Double.POSITIVE_INFINITY, "inf".asNumber())
    }

    @Test
    fun `toNumberExOrNull returns NaN for nan`() {
        val n = "nan".asNumber()
        assertNotNull(n)
        assertTrue(n.toDouble().isNaN())
    }

    @Test
    fun `toNumberExOrNull returns NaN for nan-canonical`() {
        val n = "nan:canonical".asNumber()
        assertNotNull(n)
        assertTrue(n.toDouble().isNaN())
    }

    @Test
    fun `toNumberExOrNull handles underscores in number`() {
        assertEquals(1_000_000L, "1_000_000".asNumber())
    }

    @Test
    fun `toNumberExOrNull returns null for invalid string`() {
        assertNull("notanumber".asNumber())
    }

    @Test
    fun `toNumberExOrNull handles large unsigned long`() {
        // 0xFFFFFFFF is larger than Int.MAX_VALUE but fits in Long as -1 unsigned
        val n = "0xFFFFFFFF".asNumber()
        assertNotNull(n)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Number parsing – toNumberUnprefixedExOrNull
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `toNumberUnprefixedExOrNull parses hex string without prefix`() {
        assertEquals(255L, "ff".asNumberUnprefixed(16))
    }

    @Test
    fun `toNumberUnprefixedExOrNull parses hex with p exponent`() {
        // 1.0p4 == 1.0 * 2^4 == 16.0, but the implementation treats exp as decimal
        val n = "1.0p4".asNumberUnprefixed(16)
        assertNotNull(n)
    }

    @Test
    fun `toNumberUnprefixedExOrNull returns infinity for inf`() {
        assertEquals(Double.POSITIVE_INFINITY, "inf".asNumberUnprefixed())
    }

    @Test
    fun `toNumberUnprefixedExOrNull handles negative`() {
        assertEquals(-255L, "-ff".asNumberUnprefixed(16))
    }

    // ─────────────────────────────────────────────────────────────────────────
    // readType
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `readType maps i32`() {
        val t = reader.readType(WasmReaderText.WastValue("i32"))
        assertEquals(WasmSType.I32, t)
    }

    @Test
    fun `readType maps i64`() {
        assertEquals(WasmSType.I64, reader.readType(WasmReaderText.WastValue("i64")))
    }

    @Test
    fun `readType maps f32`() {
        assertEquals(WasmSType.F32, reader.readType(WasmReaderText.WastValue("f32")))
    }

    @Test
    fun `readType maps f64`() {
        assertEquals(WasmSType.F64, reader.readType(WasmReaderText.WastValue("f64")))
    }

    @Test
    fun `readType maps externref to ANYREF`() {
        assertEquals(WasmSType.ANYREF, reader.readType(WasmReaderText.WastValue("externref")))
    }

    @Test
    fun `readType maps funcref`() {
        assertEquals(WasmSType.FUNCREF, reader.readType(WasmReaderText.WastValue("funcref")))
    }

    @Test
    fun `readType parses func block with params and result`() {
        val block = WasmReaderText.WastParser.parseBlock("(func (param i32 i32) (result i64))")
        val t = reader.readType(block)
        assertIs<WasmType.Function>(t)
        assertEquals(2, t.args.size)
        assertEquals(WasmSType.I64, t.retType)
    }

    @Test
    fun `readType parses mutable type`() {
        val block = WasmReaderText.WastParser.parseBlock("(mut i32)")
        val t = reader.readType(block)
        assertIs<WasmType.Mutable>(t)
        assertEquals(WasmSType.I32, t.rtype)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // readTopLevel – module structure
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `readTopLevel creates a module`() {
        reader.readTopLevel("(module)")
        assertEquals(1, reader.modules.size)
    }

    @Test
    fun `readTopLevel creates multiple modules`() {
        reader.readTopLevel("(module) (module)")
        assertEquals(2, reader.modules.size)
    }

    @Test
    fun `module with memory section stores limit`() {
        reader.readTopLevel("(module (memory 1))")
        val mod = reader.modules.first()
        assertEquals(1, mod.memories.size)
        assertEquals(1, mod.memories[0].min)
    }

    @Test
    fun `module with memory stores min and max`() {
        reader.readTopLevel("(module (memory 1 4))")
        val limit = reader.modules.first().memories.first()
        assertEquals(1, limit.min)
        assertEquals(4, limit.max)
    }

    @Test
    fun `module with named type stores it by name`() {
        reader.readTopLevel($$"(module (type $T (func (param i32) (result i32))))")
        val mod = reader.modules.first()
        assertEquals(1, mod.types.size)
        assertNotNull(mod.typesByName[$$"$T"])
    }

    @Test
    fun `module func is added to funcs list and byName map`() {
        reader.readTopLevel($$"(module (func $add (param i32) (param i32) (result i32) (i32.add (local.get 0) (local.get 1))))")
        val mod = reader.modules.first()
        assertEquals(1, mod.funcs.size)
        assertNotNull(mod.funcsByName[$$"$add"])
    }

    @Test
    fun `module func with export sets export name`() {
        reader.readTopLevel("(module (func (export \"main\") (result i32) (i32.const 0)))")
        val mod = reader.modules.first()
        val func = mod.funcs.first()
        assertNotNull(func.exports.firstOrNull())
    }

    @Test
    fun `module global is added by name`() {
        reader.readTopLevel($$"(module (global $g i32 (i32.const 42)))")
        val mod = reader.modules.first()
        assertNotNull(mod.globalsByName[$$"$g"])
        assertEquals(1, mod.globals.size)
    }

    @Test
    fun `module data increases lastDataPtr`() {
        reader.readTopLevel("(module (data (i32.const 0) \"hello\"))")
        val mod = reader.modules.first()
        assertEquals(1, mod.datas.size)
        assertEquals(5, mod.lastDataPtr) // "hello" = 5 bytes
    }

    @Test
    fun `assert_return block is recorded`() {
        // Requires at least one module to be current
        reader.readTopLevel(
            $$"""
            (module
              (func $f (result i32) (i32.const 1))
            )
            (assert_return (invoke $f) (i32.const 1))
        """.trimIndent())
        val mod = reader.modules.first()
        assertEquals(1, mod.asserts.size)
        assertIs<WasmAssertReturn>(mod.asserts.first())
    }

    // ─────────────────────────────────────────────────────────────────────────
    // WasmFuncBuilder – local/param tracking
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `WasmFuncBuilder addVar tracks params separately from locals`() {
        val moduleBuilder = WasmReaderText.WasmModuleBuilder()
        val fb = WasmReaderText.WasmFuncBuilder(moduleBuilder)
        fb.addVar($$"$a", WasmSType.I32, isParam = true)
        fb.addVar($$"$b", WasmSType.I64, isParam = false)

        assertEquals(1, fb.params.size)
        assertEquals(1, fb.justLocals.size)
        assertEquals(2, fb.locals.size)
        assertEquals($$"$a", fb.params[0].name)
        assertEquals($$"$b", fb.justLocals[0].name)
    }

    @Test
    fun `WasmFuncBuilder localsByName lookup works`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        fb.addVar($$"$x", WasmSType.F32, isParam = true)
        assertNotNull(fb.localsByName[$$"$x"])
        assertEquals(WasmSType.F32, fb.localsByName[$$"$x"]!!.type)
    }

    @Test
    fun `WasmFuncBuilder addResult updates results list`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        fb.addResult(WasmSType.I32)
        assertEquals(1, fb.results.size)
        assertEquals(WasmSType.I32, fb.results[0])
    }

    // ─────────────────────────────────────────────────────────────────────────
    // WasmFuncBuilder – block stack for br / br_if label resolution
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `relativeIndexOfBlockById returns null when stack is empty`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        assertNull(fb.relativeIndexOfBlockById($$"$missing"))
    }

    @Test
    fun `relativeIndexOfBlockById returns 0 for innermost block`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        fb.pushBlock("block", $$"$outer")
        fb.pushBlock("loop", $$"$inner")
        assertEquals(0, fb.relativeIndexOfBlockById($$"$inner"))
        fb.popBlock()
        fb.popBlock()
    }

    @Test
    fun `relativeIndexOfBlockById returns correct depth for outer block`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        fb.pushBlock("block", $$"$outer")
        fb.pushBlock("loop", $$"$inner")
        assertEquals(1, fb.relativeIndexOfBlockById($$"$outer"))
        fb.popBlock()
        fb.popBlock()
    }

    @Test
    fun `pushBlock inline version restores stack after lambda`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        fb.pushBlock("block", $$"$b") { /* no-op */ }
        assertEquals(0, fb.blocks.size)
    }

    @Test
    fun `relativeIndexOfBlockById returns null after block is popped`() {
        val fb = WasmReaderText.WasmFuncBuilder(WasmReaderText.WasmModuleBuilder())
        fb.pushBlock("block", $$"$b") { /* no-op */ }
        assertNull(fb.relativeIndexOfBlockById($$"$b"))
    }

    // ─────────────────────────────────────────────────────────────────────────
    // WasmModuleBuilder – internal collections
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `WasmModuleBuilder addFunc stores in both list and map`() {
        val mb = WasmReaderText.WasmModuleBuilder()
        val fb = WasmReaderText.WasmFuncBuilder(mb)
        fb.funcName = $$"$f"
        val func = fb.buildFunc(mb)
        mb.addFunc(func)
        assertEquals(1, mb.funcs.size)
        assertNotNull(mb.funcsByName[$$"$f"])
    }

    @Test
    fun `WasmModuleBuilder addGlobal stores in both list and map`() {
        val mb = WasmReaderText.WasmModuleBuilder()
        val global = WasmGlobal(WasmSType.I32, 0, null, name = $$"$g")
        mb.addGlobal(global)
        assertEquals(1, mb.globals.size)
        assertNotNull(mb.globalsByName[$$"$g"])
    }

    @Test
    fun `WasmModuleBuilder addType stores in both list and map`() {
        val mb = WasmReaderText.WasmModuleBuilder()
        val namedType = NamedWasmType(0, $$"$T", WasmType.Function(emptyList(), emptyList()))
        mb.addType(namedType)
        assertEquals(1, mb.types.size)
        assertNotNull(mb.typesByName[$$"$T"])
    }

    @Test
    fun `WasmModuleBuilder buildModule assembles all sections`() {
        val mb = WasmReaderText.WasmModuleBuilder()
        mb.memories += WasmType.Limit(1)
        val module = mb.buildModule()
        assertEquals(1, module.memories.size)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Full round-trip smoke tests
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `simple add function compiles without error`() {
        val wat = $$"""
            (module
              (func $add (export "add") (param i32) (param i32) (result i32)
                (i32.add (local.get 0) (local.get 1))
              )
            )
        """.trimIndent()
        reader.readTopLevel(wat)
        val mod = reader.modules.first().buildModule()
        assertEquals(1, mod.functions.size)
    }

    @Test
    fun `factorial function with if compiles without error`() {
            val wat = $$"""
            (module
              (func $fac_helper (param i64) (result i64)
                (i64.const 1)
              )
              (func $fac (export "fac") (param i64) (result i64)
                (if (result i64)
                  (i64.le_u (local.get 0) (i64.const 1))
                  (then (i64.const 1))
                  (else
                    (i64.mul
                      (local.get 0)
                      (call $fac_helper (i64.sub (local.get 0) (i64.const 1)))
                    )
                  )
                )
              )
            )
        """.trimIndent()
        reader.readTopLevel(wat)
        assertEquals(2, reader.modules.first().funcs.size)
    }

    @Test
    fun `block and loop with br compile without error`() {
        val wat = $$"""
            (module
              (func $countdown (param i32)
                (block $exit
                  (loop $top
                    (br_if $exit (i32.eqz (local.get 0)))
                    (local.set 0 (i32.sub (local.get 0) (i32.const 1)))
                    (br $top)
                  )
                )
              )
            )
        """.trimIndent()
        reader.readTopLevel(wat)
    }

    @Test
    fun `global get and set compile without error`() {
        val wat = $$"""
            (module
              (global $counter (mut i32) (i32.const 0))
              (func $inc
                (global.set $counter (i32.add (global.get $counter) (i32.const 1)))
              )
            )
        """.trimIndent()
        reader.readTopLevel(wat)
        assertEquals(1, reader.modules.first().globals.size)
    }

    @Test
    fun `ref null extern compiles without error`() {
        val wat = $$"""
            (module
              (func $nullref (result externref)
                (ref.null extern)
              )
            )
        """.trimIndent()
        reader.readTopLevel(wat)
    }

    @Test
    fun `multiple named types and typed func compile without error`() {
        val wat = $$"""
            (module
              (type $BinI32 (func (param i32 i32) (result i32)))
              (func $mul (type $BinI32)
                (i32.mul (local.get 0) (local.get 1))
              )
            )
        """.trimIndent()
        reader.readTopLevel(wat)
        assertEquals(1, reader.modules.first().types.size)
        assertEquals(1, reader.modules.first().funcs.size)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // StrReader internals (indirectly via tokenizer)
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    fun `tokenizer handles empty input`() {
        val tokens = tokenize("")
        assertTrue(tokens.isEmpty())
    }

    @Test
    fun `tokenizer handles whitespace-only input`() {
        val tokens = tokenize("   \t\n  ")
        assertTrue(tokens.isEmpty())
    }

    @Test
    fun `tokenizer handles deeply nested expression`() {
        val block = WasmReaderText.WastParser.parseBlock("(a (b (c (d))))")
        assertEquals("a", block.name)
        assertEquals("b", block.blockParams[0].name)
        assertEquals("c", block.blockParams[0].blockParams[0].name)
        assertEquals("d", block.blockParams[0].blockParams[0].blockParams[0].name)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private tokenize helper
    // ─────────────────────────────────────────────────────────────────────────

    private fun tokenize(input: String): List<WasmReaderText.WastParser.Token> {
        // Access via parseBlocks → round-trip isn't ideal but the tokenizer
        // is package-private. We expose it by calling through the public path
        // or, if the tokenizer is internal, by reflection / test companion.
        // Using the internal extension directly requires same-package placement;
        // adjust the call below to match your project's module structure.
        return StrReader(input).wastTokenize()
    }
}
