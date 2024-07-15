@file:Suppress("PackageDirectoryMismatch")

package korlibs.concurrent.lock

actual class Lock actual constructor() : LockImpl(), BaseLockWithNotifyAndWait {
    actual companion object {}
}
