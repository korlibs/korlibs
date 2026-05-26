package korlibs.io.hash

import java.io.InputStream
import korlibs.crypto.Hash
import korlibs.crypto.HasherFactory
import korlibs.io.internal.bytesTempPool

fun InputStream.hash(algo: HasherFactory): Hash = bytesTempPool.alloc { temp -> algo.digest(temp) { read(it) } }
