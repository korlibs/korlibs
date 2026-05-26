package korlibs.io.internal

import korlibs.datastructure.Pool
import korlibs.io.lang.threadLocal

@PublishedApi
internal const val BYTES_TEMP_SIZE = 0x10000

@PublishedApi
internal val bytesTempPool by threadLocal { Pool(preallocate = 1) { ByteArray(BYTES_TEMP_SIZE) } }
