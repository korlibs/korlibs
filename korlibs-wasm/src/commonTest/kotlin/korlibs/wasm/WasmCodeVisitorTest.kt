package korlibs.wasm

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import korlibs.io.stream.openSync
import korlibs.memory.getS32
import korlibs.memory.setArray
import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertEquals

class WasmCodeVisitorTest {
    @Test
    fun test() = suspendTest {
        if (Platform.isIos) return@suspendTest

        val module = WasmReaderBinary()
            .doTrace(false)
            .read(resourcesVfs["wasm/webp.wasm"].readBytes().openSync())
            .toModule()

        //val newInterpreter = WasmRunInterpreterNew(module)
        val newInterpreter = WasmRunInterpreter(module).initGlobals()
        //val newInterpreter = WasmRunInterpreterNew(module).initGlobals()

        //for (func in module.functions) {
        //    newInterpreter.compile(func)
        //}

        //val module = createJIT("webp.wasm", codeTrace = true, validate = true)
        //val module = createJIT("webp.wasm", codeTrace = false)
        val webpBytes = resourcesVfs["wasm/webp.webp"].readBytes()
        val ptr = newInterpreter.invoke("malloc", webpBytes.size) as Int
        newInterpreter.memory.setArray(ptr * Byte.SIZE_BYTES, webpBytes, 0, webpBytes.size - 0)

        //repeat(100) {
        run {
            val infoPtr = newInterpreter.invoke("get_info", ptr, webpBytes.size) as Int
            val success = newInterpreter.memory.getS32(infoPtr + 0)
            val width = newInterpreter.memory.getS32(infoPtr + 4)
            val height = newInterpreter.memory.getS32(infoPtr + 8)
            assertEquals("1,32x32", "$success,${width}x${height}")
        }
    }
}
