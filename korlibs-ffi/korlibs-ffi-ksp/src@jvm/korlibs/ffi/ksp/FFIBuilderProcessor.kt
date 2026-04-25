package korlibs.ffi.ksp

import com.google.devtools.ksp.*
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import korlibs.ffi.ksp.FFIBuilderProcessor.*

class FFIBuilderProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return FFIBuilderProcessor(environment)
    }
}

private interface MetadataPlatformInfo : PlatformInfo {
    companion object : MetadataPlatformInfo
    override val platformName: String get() = "Metadata"
}

private class FFIBuilderProcessor(val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    val codeGenerator: CodeGenerator = environment.codeGenerator
    val logger: KSPLogger = environment.logger
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val isCommon = environment.platforms.size >= 2
        val mainPlatform = if (isCommon) MetadataPlatformInfo else environment.platforms.first()
        val isAndroid = resolver.getClassDeclarationByName(resolver.getKSNameFromString("android.view.View")) != null
        val isJvm = !isCommon && mainPlatform is JvmPlatformInfo && !isAndroid
        val isNative = !isCommon && mainPlatform is NativePlatformInfo
        val isJs = !isCommon && mainPlatform is JsPlatformInfo

        //logger.error("NATIVE: ${environment.options}, ${environment.platforms.map { it.platformName }}, isAndroidProject=$isAndroidProject")

        val casts = when {
            isJvm -> jnaCasts
            isNative -> knativeCasts
            isJs -> denoCasts
            else -> defaultCasts
        }

        for (sym in resolver.getSymbolsWithAnnotation("korlibs.ffi.api.FFI")) {
            if (sym is KSClassDeclaration) {
                if (sym.parentDeclaration != null) {
                    logger.error("Only FFI top-level declarations are supported: ${sym.qualifiedName?.asString()}")
                }
                val packageName = sym.packageName.asString()
                val classNameIfc = sym.sname
                val classNameIfcQualified = sym.qualifiedName!!.asString()
                val classNameImpl = "${classNameIfc}_FFIImpl"
                codeGenerator.createNewFile(Dependencies(false, sym.containingFile!!), packageName, "$classNameImpl.${mainPlatform.platformName}").use {
                    val visibility = sym.getVisibility().name.lowercase()
                    it.bufferedWriter().use {
                        if (isNative) {
                            it.appendLine("@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)")
                        }
                        it.appendLine("package $packageName")
                        if (isNative) {
                            it.appendLine()
                            it.appendLine("import kotlinx.cinterop.*")
                        }
                        it.appendLine()
                        if (isCommon) {
                            it.appendLine("$visibility fun ${sym.sname}(): $classNameIfcQualified = $classNameImpl()")
                        }
                        val expectActual = if (isCommon) "expect" else "actual"
                        val onlyActual = if (isCommon) "" else "actual"
                        it.appendLine("$visibility $expectActual class $classNameImpl${if (isCommon) "()" else " actual constructor()"} : $classNameIfcQualified {")
                        if (isJs) {
                            it.appendLine("  private val __all__$classNameImpl = __load_$classNameImpl()")
                            it.appendLine("  private val __$classNameImpl = __all__$classNameImpl.symbols")
                        }
                        val ffi = FFIContext()
                        for (func in sym.getDeclaredFunctions()) {
                            val context = FuncContext(ffi, func)
                            val params = func.parameters.asString(context = context)
                            val paramsCall = func.parameters.asCallString(casts, context)
                            val body = when {
                                isCommon -> ""
                                isJvm || isNative || isJs -> {
                                    " = ${casts.cast("__$classNameImpl.${func.sname}($paramsCall)", func.returnType, context)}"
                                }
                                else -> " = TODO()"
                            }
                            it.appendLine("  $onlyActual override fun ${func.sname}($params): ${func.returnType.asString()}$body")
                        }
                        when {
                            isCommon -> it.appendLine("  $onlyActual override fun close()")
                            isJs -> it.appendLine("  $onlyActual override fun close() { __all__$classNameImpl.close() }")
                            else -> it.appendLine("  $onlyActual override fun close() = Unit")
                        }
                        it.appendLine("}")

                        val ffiAnnotation = sym.annotations.firstOrNull { it.shortName.getShortName() == "FFI" } ?: error("ERROR: FFI annotation not found")
                        val libs = ffiAnnotation.arguments.associate { it.sname to it.value.toString().takeIf { it.isNotBlank() } }
                        val libraryCommonLib = libs["commonLib"] ?: "libc"
                        val libraryNameWin = libs["windowsLib"] ?: libs["commonLib"] ?: "msvcrt"
                        val libraryNameMac = libs["macosLib"] ?: libs["commonLib"] ?: "/usr/lib/libSystem.dylib"
                        val libraryNameLinux = libs["linuxLib"] ?: libs["commonLib"] ?: "libc"

                        val callbackTypes = arrayListOf<KSTypeReference>()
                        for (func in sym.getDeclaredFunctions()) {
                            for (param in func.parameters) {
                                if (param.type.asString().startsWith("FFIFunctionRef<")) {
                                    callbackTypes += param.type
                                }
                            }
                        }

                        when {
                            isJvm -> {
                                it.appendLine("private inline fun com.sun.jna.Pointer.toFFIPointer() = FFIPointer(com.sun.jna.Pointer.nativeValue(this))")
                                it.appendLine("private inline fun FFIPointer.toPointer() = com.sun.jna.Pointer.createConstant(this.address)")
                                it.appendLine("private object __$classNameImpl : com.sun.jna.Library {")
                                for (func in sym.getDeclaredFunctions()) {
                                    val context = FuncContext(ffi, func)
                                    val params = func.parameters.asString(casts, context = context)
                                    it.appendLine("  external fun ${func.sname}($params): ${casts.typeProcessor(func.returnType, context = context)}")
                                }
                                it.appendLine("  init {")
                                it.appendLine("    when {")
                                it.appendLine("      com.sun.jna.Platform.isWindows() -> com.sun.jna.Native.register(\"$libraryNameWin\")")
                                it.appendLine("      com.sun.jna.Platform.isMac() -> com.sun.jna.Native.register(\"$libraryNameMac\")")
                                it.appendLine("      else -> com.sun.jna.Native.register(\"$libraryNameLinux\")")
                                it.appendLine("    }")
                                it.appendLine("  }")
                                it.appendLine("}")
                                for (callback in ffi.callbacks) {
                                    val name = ffi.getName(callback)
                                    val funcType = callback.param!!.type.resolve().arguments.first().type!!.resolve()
                                    val params = funcType.arguments.dropLast(1).asString(casts, callback)
                                    val retType = funcType.arguments.last().type.asString(casts, callback)
                                    it.appendLine("private fun interface $name : com.sun.jna.Callback { fun callback($params): $retType }")
                                }
                            }
                            isNative -> {
                                it.appendLine("fun COpaquePointer?.toFFIPointer(): FFIPointer = FFIPointer(this.rawValue.toLong())")
                                it.appendLine("fun FFIPointer.toPointer(): COpaquePointer? = address.toCPointer()")

                                it.appendLine("private object __$classNameImpl {")
                                it.appendLine("  val __LIB__ = korlibs.ffi.api.FFIDLOpenPlatform(common = \"$libraryCommonLib\", linux = \"$libraryNameLinux\", macos = \"$libraryNameMac\", windows = \"$libraryNameWin\")")

                                for (func in sym.getDeclaredFunctions()) {
                                    val context = FuncContext(ffi, func)
                                    it.appendLine("  val ${func.sname} by lazy { val funcName = \"${func.sname}\"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(${func.parameters.asTypeString(casts, context)}) -> ${func.returnType.asString(casts, context)}>>() ?: error(\"Can't find ${'$'}funcName\") }")
                                }
                                it.appendLine("}")

                                for (callback in ffi.callbacks) {
                                    val name = ffi.getName(callback)
                                    val funcType = callback.funcType
                                    val params = callback.paramFuncParams.asString(casts, callback)
                                    val retType = callback.paramFuncRet.type.asString(casts, callback)
                                    val NCallbacks = 4
                                    for (n in 0 until NCallbacks) {
                                        it.appendLine("private fun ${name}_$n($params): $retType = fun_$name[$n]!!.func(${callback.paramFuncParams.asCallString(casts, callback)})")
                                    }
                                    val funcs = (0 until NCallbacks).joinToString(", ") { "staticCFunction(::${name}_$it)" }
                                    it.appendLine("private val fun_$name = kotlin.arrayOfNulls<${callback.param!!.type.asString()}>($NCallbacks)")
                                    it.appendLine("private val ref_$name = listOf($funcs)")
                                    it.appendLine("private fun alloc_$name(cfunc: ${callback.param.type.asString()}) = ref_$name[cfunc.allocIn(fun_$name)]")
                                }
                                /*
                                private fun Callback_qsort_compare_0(p0: COpaquePointer?, p1: COpaquePointer?): Int = fun_Callback_qsort_compare[0]!!.func(p0.toFFIPointer(), p1.toFFIPointer())
                                private fun Callback_qsort_compare_1(p0: COpaquePointer?, p1: COpaquePointer?): Int = fun_Callback_qsort_compare[1]!!.func(p0.toFFIPointer(), p1.toFFIPointer())
                                private val fun_Callback_qsort_compare = kotlin.arrayOfNulls<FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>>(8)
                                private val ref_Callback_qsort_compare = listOf(staticCFunction(::Callback_qsort_compare_0), staticCFunction(::Callback_qsort_compare_1), staticCFunction(::Callback_qsort_compare_2), staticCFunction(::Callback_qsort_compare_3), staticCFunction(::Callback_qsort_compare_4), staticCFunction(::Callback_qsort_compare_5), staticCFunction(::Callback_qsort_compare_6), staticCFunction(::Callback_qsort_compare_7))
                                private fun alloc_Callback_qsort_compare(cfunc: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>) = ref_Callback_qsort_compare[cfunc.allocIn(fun_Callback_qsort_compare)]

                                 */
                            }
                            isJs -> {
                                it.appendLine("private fun String_to_DenoPointer(str: String): dynamic = js(\"(Deno.UnsafePointer.of(new TextEncoder().encode(str)))\")")
                                it.appendLine("private fun DenoPointer_to_FFIPointer(v: dynamic): FFIPointer = FFIPointer(js(\"Deno.UnsafePointer.value(v)\").toString().toLong())")
                                it.appendLine("private fun FFIPointer_to_DenoPointer(v: FFIPointer): dynamic { val vv = v.address.toString(); return js(\"Deno.UnsafePointer.create(BigInt(vv))\") }")

                                it.appendLine("private fun  __load_$classNameImpl() = js(\"\"\"")
                                it.appendLine("  (typeof Deno === 'undefined') ? {} : Deno.dlopen(Deno.build.os === 'windows' ? '$libraryNameWin' : Deno.build.os === 'darwin' ? '$libraryNameMac' : '$libraryNameLinux', {")
                                for (func in sym.getDeclaredFunctions()) {
                                    it.appendLine("    \"${func.sname}\": { parameters: [${func.parameters.joinToString(", ") {
                                        jsTypeStr(it.type)
                                    }}], result: ${jsTypeStr(func.returnType)} },")
                                }
                                it.appendLine("  })")
                                it.appendLine("\"\"\")")
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
        return emptyList()
    }

    // `void`, `bool`, `u8`, `i8`, `u16`, `i16`, `u32`, `i32`, `u64`, `i64`, `usize`, `isize`, `f32`, `f64`, `pointer`, `buffer`, `function`, `struct`
    private fun jsTypeStr(type: KSTypeReference?): String {
        val res = __jsType(type)
        return when (res) {
            is String -> "'$res'"
            else -> TODO()
        }
    }

    @Deprecated("Use jsTypeStr")
    private fun jsType(type: KSTypeReference?): String = __jsType(type).toString()

    private fun __jsType(type: KSTypeReference?): Any = when (type.asString()) {
        "Unit" -> "void"
        "Boolean" -> "bool"
        "UByte" -> "u8"
        "Byte" -> "i8"
        "UShort" -> "u16"
        "Char" -> "u16"
        "Short" -> "i16"
        "UInt" -> "u32"
        "Int" -> "i32"
        "ULong" -> "u64"
        "Long" -> "i64"
        "Float" -> "f32"
        "Double" -> "f64"
        "FFIPointer" -> "pointer"
        "String" -> "pointer"
        else -> when {
            type.asString().startsWith("FFIFunctionRef<") -> "function"
            else -> type.asString()
        }
    }

    val defaultCasts = object : PlatformCasts {}
    val denoCasts = object : PlatformCasts {
        override fun cast(str: String, type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when (it) {
                "FFIPointer" -> "DenoPointer_to_FFIPointer($str)"
                else -> str
            }
        }
        override fun revCast(str: String, type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when (it) {
                "FFIPointer" -> "FFIPointer_to_DenoPointer($str)"
                "String" -> "String_to_DenoPointer($str)"
                else -> when {
                    it.startsWith("FFIFunctionRef<") -> {
                        // fun interface QsortCompareCallback : StdCallLibrary { fun callback(l: FFIPointer, r: FFIPointer): Int }
                        //"{ l, r -> $str.func(l, r) }"

                        /*
                                    val name = ffi.getName(callback)
                                    val funcType = callback.funcType
                                    val params = callback.paramFuncParams.asString(casts, callback)
                                    val retType = callback.paramFuncRet.type.asString(casts, callback)
                                    val NCallbacks = 4
                                    for (n in 0 until NCallbacks) {
                                        it.appendLine("private fun ${name}_$n($params): $retType = fun_$name[$n]!!.func(${callback.paramFuncParams.asCallString(casts, callback)})")
                                    }
                                    val funcs = (0 until NCallbacks).joinToString(", ") { "staticCFunction(::${name}_$it)" }
                                    it.appendLine("private val fun_$name = kotlin.arrayOfNulls<${callback.param!!.type.asString()}>($NCallbacks)")
                                    it.appendLine("private val ref_$name = listOf($funcs)")
                                    it.appendLine("private fun alloc_$name(cfunc: ${callback.param.type.asString()}) = ref_$name[cfunc.allocIn(fun_$name)]")

                         */

                        val dargs = context.paramFuncParams.withIndex().joinToString(", ") { "__p${it.index}: dynamic" }
                        val dcall = context.paramFuncParams.asCallString(this, context)
                        val jsParamTypes = context.paramFuncParams.joinToString(", ") { jsTypeStr(it.type) }
                        val jsRetType = jsTypeStr(context.paramFuncRet.type)

                        """
                            run {
                                val func = fun($dargs): dynamic { return ${context.param!!.name!!.asString()}.func(${dcall}) }
                                val callback = js("(new Deno.UnsafeCallback({ parameters: [$jsParamTypes], result: $jsRetType }, func))")
                                compare.closer = { callback.close() }
                                callback.pointer
                            }
                        """.trimIndent()
                    }
                    else -> str
                }
            }
        }
    }

    class FFIContext {
        val callbacks = mutableSetOf<FuncContext>()

        fun getName(context: FuncContext): String {
            callbacks += context
            return "Callback_${context.func.sname}_${context.param?.name?.asString() ?: ""}"
        }
    }

    data class FuncContext(
        val ffi: FFIContext,
        val func: KSFunctionDeclaration,
        val param: KSValueParameter? = null,
    ) {
        val funcType by lazy {
            param!!.type.resolve().arguments.first().type!!.resolve()
        }
        val paramFuncParams by lazy { funcType.arguments.dropLast(1) }
        val paramFuncRet by lazy { funcType.arguments.last() }
    }

    val jnaCasts = object : PlatformCasts {
        override fun typeProcessor(type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when {
                it.startsWith("FFIFunctionRef<") -> context.ffi.getName(context)
                it == "FFIPointer" -> "com.sun.jna.Pointer"
                else -> it
            }
        }
        override fun cast(str: String, type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when (it) {
                "FFIPointer" -> "$str.toFFIPointer()"
                "Unit", "Float", "Int" -> str
                "FFIFunctionRef" -> {
                    TODO()
                }
                else -> str
            }
        }
        override fun revCast(str: String, type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when  {
                it == "FFIPointer" -> "$str.toPointer()"
                it == "String" -> str
                it.startsWith("FFIFunctionRef<") -> {
                    // fun interface QsortCompareCallback : StdCallLibrary { fun callback(l: FFIPointer, r: FFIPointer): Int }
                    //"{ l, r -> $str.func(l, r) }"
                    buildString {
                        append("{ ")
                        context.paramFuncParams.forEachIndexed { index, param ->
                            append("p$index, ")
                        }
                        append(" -> ")
                        append(str)
                        append(".func(")
                        context.paramFuncParams.forEachIndexed { index, param ->
                            append(cast("p$index", param.type, context))
                            append(", ")
                        }
                        append(") }")
                    }
                }
                it == "Unit" || it == "Float" || it == "Int" -> str
                else -> {
                    logger.error("type=$type, it=$it")
                    str
                }
            }
        }
    }
    val knativeCasts = object : PlatformCasts {
        override fun typeProcessor(type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when {
                it.startsWith("FFIFunctionRef<") -> {
                    context.ffi.getName(context)
                    "CPointer<CFunction<(${context.paramFuncParams.map { it.type }.asString(this, context)}) -> ${context.paramFuncRet.type.asString(this, context)}>>"
                }
                it == "FFIPointer" -> "COpaquePointer?"
                it == "String" -> "CValues<ByteVar>"
                else -> it
            }
        }
        override fun cast(str: String, type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when (it) {
                "FFIPointer" -> "$str.toFFIPointer()"
                else -> str
            }
        }
        override fun revCast(str: String, type: KSTypeReference?, context: FuncContext): String = type.asString().let {
            when {
                it == "String" -> "$str.cstr"
                it == "FFIPointer" -> "$str.toPointer()"
                it.startsWith("FFIFunctionRef<") -> {
                    val fname = context.ffi.getName(context)
                    "alloc_$fname($str)"
                }
                else -> str
            }
        }
    }
}

private interface PlatformCasts {
    companion object : PlatformCasts
    fun typeProcessor(type: KSTypeReference?, context: FuncContext): String = type.asString()
    fun cast(str: String, type: KSTypeReference?, context: FuncContext): String = str
    fun revCast(str: String, type: KSTypeReference?, context: FuncContext): String = str
}

private val KSValueArgument.sname get() = name?.asString() ?: "<ERROR>"
private val KSDeclaration.sname get() = simpleName.asString()
private val KSDeclaration.qname get() = qualifiedName?.asString() ?: "<ERROR>"
private fun KSTypeReference?.asString(): String = this?.resolve()?.toString() ?: "<ERROR>"
private fun KSTypeReference?.asString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = casts.typeProcessor(this, context)
@JvmName("List_asStringKSTypeReference")
private fun List<KSTypeReference?>.asString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = joinToString(", ") { casts.typeProcessor(it, context) }
@JvmName("List_asStringKSTypeArgument")
private fun List<KSTypeArgument>.asString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = withIndex().joinToString(", ") { "__p${it.index}: ${casts.typeProcessor(it.value.type, context)}" }

@JvmName("List_asCallStringKSTypeArgument")
private fun List<KSTypeArgument>.asCallString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = withIndex().joinToString(", ") { casts.cast("__p${it.index}", it.value.type, context) }

private fun List<KSValueParameter>.asString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = joinToString(", ") { "${it.name?.asString()}: ${casts.typeProcessor(it.type, context.copy(param = it))}" }
private fun List<KSValueParameter>.asTypeString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = joinToString(", ") { casts.typeProcessor(it.type, context.copy(param = it)) }
private fun List<KSValueParameter>.asCallString(casts: PlatformCasts = PlatformCasts, context: FuncContext): String = joinToString(", ") {
    casts.revCast("${it.name?.asString()}", it.type, context.copy(param = it))
}

/*

val __TestMathFFI_FFIImpl = js("""
    Deno.dlopen("libc", {
        "cosf": { parameters: ["float"], result: "float" },
        "malloc": { parameters: ["int"], result: "pointer" },
    })
""".trimIndent())

 */