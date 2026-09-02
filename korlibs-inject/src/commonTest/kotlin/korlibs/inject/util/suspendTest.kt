package korlibs.inject.util

import kotlinx.coroutines.test.runTest

fun suspendTestIgnoreJs(callback: suspend () -> Unit) = runTest { callback() }
fun suspendTest(callback: suspend () -> Unit) = runTest { callback() }
