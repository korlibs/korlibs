package korlibs.wasm

import java.io.PrintWriter
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.util.ASMifier
import org.objectweb.asm.util.CheckClassAdapter
import org.objectweb.asm.util.TraceClassVisitor
import org.objectweb.asm.util.TraceMethodVisitor

class WasmRunJVMOutputExt(OUTPUT_CLASS_NAME: String = "WasmProgram") : WasmRunJVMOutput(OUTPUT_CLASS_NAME) {
    override fun getClassVisitor(cw: ClassWriter, doValidate: Boolean): ClassVisitor {
        return if (doValidate) CheckClassAdapter(cw) else cw
    }

    override fun dumpJVMBytecode(bytes: ByteArray) {
        ClassReader(bytes).accept(TraceClassVisitor(null, ASMifier(), PrintWriter(System.out)), 0)
    }

    override fun useMethodVisitor(myMethod: MethodVisitor, doTrace: Boolean, block: (mv: MethodVisitor) -> Unit) {
        val asmifier = ASMifier()
        val mv: MethodVisitor = if (doTrace) TraceMethodVisitor(myMethod, asmifier) else myMethod
        block(mv)
        if (doTrace) println(asmifier.text.joinToString(""))
    }

    companion object {
        fun build(module: WasmModule, codeTrace: Boolean = false): WasmRunJVMJIT =
            WasmRunJVMJIT.build(module, outputGen = { WasmRunJVMOutputExt() }, codeTrace)
    }
}
