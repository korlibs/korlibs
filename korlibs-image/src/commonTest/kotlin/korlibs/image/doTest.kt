package korlibs.image

import korlibs.io.async.suspendTest
import korlibs.platform.Platform

fun doTest(block: suspend () -> Unit) {
    suspendTest({ !Platform.isAndroid && !Platform.isJsDenoJs }) {
        block()
    }
}
