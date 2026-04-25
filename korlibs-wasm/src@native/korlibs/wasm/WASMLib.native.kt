package korlibs.wasm

open class DefaultNativeWASMLib(content: ByteArray) : IWASMLib, BaseWASMLib(content) {
    override val isAvailable: Boolean get() = false
}
