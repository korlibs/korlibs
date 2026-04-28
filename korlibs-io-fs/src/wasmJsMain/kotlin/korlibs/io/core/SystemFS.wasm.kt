package korlibs.io.core

actual val defaultSyncSystemFS: SyncSystemFS = NullSyncSystemFS
actual val defaultSystemFS: SystemFS by lazy { NullSystemFS }
