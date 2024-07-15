@file:Suppress("PackageDirectoryMismatch")

package korlibs.concurrent.lock

actual class Lock actual constructor() : LockImpl(), BaseLockWithNotifyAndWait {
    actual companion object {}
    actual inline operator fun <T> invoke(callback: () -> T): T = lockUnlock(callback)
}
