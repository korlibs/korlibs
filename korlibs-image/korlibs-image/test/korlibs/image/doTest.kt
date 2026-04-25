package korlibs.image

import korlibs.io.async.*
import korlibs.platform.*

fun doTest(block: suspend () -> Unit) {
    suspendTest({ !Platform.isAndroid && !Platform.isJsDenoJs }) {
        block()
    }
}
