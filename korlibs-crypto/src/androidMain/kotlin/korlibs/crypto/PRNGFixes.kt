package korlibs.crypto

/*
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will Google be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, as long as the origin is not misrepresented.
 */

import android.graphics.Bitmap
import android.os.Build
import android.os.Process
import android.util.Log
import java.io.*
import java.security.NoSuchAlgorithmException
import java.security.Provider
import java.security.SecureRandom
import java.security.SecureRandomSpi
import java.security.Security

/**
 * Fixes for the output of the default PRNG having low entropy.
 *
 * The fixes need to be applied via [.apply] before any use of Java
 * Cryptography Architecture primitives. A good place to invoke them is in the
 * application's `onCreate`.
 */
object PRNGFixes {

    private const val VERSION_CODE_JELLY_BEAN = 16
    private const val VERSION_CODE_JELLY_BEAN_MR2 = 18
    private val BUILD_FINGERPRINT_AND_DEVICE_SERIAL: ByteArray = run {
		val result = StringBuilder()
		val fingerprint = Build.FINGERPRINT
		if (fingerprint != null) {
			result.append(fingerprint)
		}
		val serial = deviceSerialNumber
		if (serial != null) {
			result.append(serial)
		}
		try {
			result.toString().toByteArray(charset("UTF-8"))
		} catch (e: UnsupportedEncodingException) {
			throw RuntimeException("UTF-8 encoding not supported")
		}
	}

    /**
     * Gets the hardware serial number of this device.
     *
     * @return serial number or `null` if not available.
     */
    private// We're using the Reflection API because Build.SERIAL is only available
    // since API Level 9 (Gingerbread, Android 2.3).
    val deviceSerialNumber: String?
        get() = try {
			Build::class.java.getField("SERIAL").get(null) as String
		} catch (ignored: Exception) {
			null
		}

    /**
     * Applies all fixes.
     *
     * @throws SecurityException if a fix is needed but could not be applied.
     */
    fun apply() {
        val notMockedUnittests = kotlin.runCatching { Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) }.isSuccess
        if (notMockedUnittests) {
            applyOpenSSLFix()
        }
    }

    /**
     * Applies the fix for OpenSSL PRNG having low entropy. Does nothing if the
     * fix is not needed.
     *
     * @throws SecurityException if the fix is needed but could not be applied.
     */
    @Throws(SecurityException::class)
    private fun applyOpenSSLFix() {
        if (Build.VERSION.SDK_INT < VERSION_CODE_JELLY_BEAN || Build.VERSION.SDK_INT > VERSION_CODE_JELLY_BEAN_MR2) {
            // No need to apply the fix
            return
        }

        try {
            // Mix in the device- and invocation-specific seed.
            Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto")
                .getMethod("RAND_seed", ByteArray::class.java)
                .invoke(null, generateSeed())

            // Mix output of Linux PRNG into OpenSSL's PRNG
            val bytesRead = Class.forName(
                "org.apache.harmony.xnet.provider.jsse.NativeCrypto"
            )
                .getMethod("RAND_load_file", String::class.java, Long::class.javaPrimitiveType)
                .invoke(null, "/dev/urandom", 1024) as Int
            if (bytesRead != 1024) {
                throw IOException(
                    "Unexpected number of bytes read from Linux PRNG: $bytesRead"
                )
            }
        } catch (e: Exception) {
            throw SecurityException("Failed to seed OpenSSL PRNG", e)
        }

    }

    /**
     * Generates a device- and invocation-specific seed to be mixed into the
     * Linux PRNG.
     */
    private fun generateSeed(): ByteArray {
        try {
            val seedBuffer = ByteArrayOutputStream()
            val seedBufferOut = DataOutputStream(seedBuffer)
            seedBufferOut.writeLong(System.currentTimeMillis())
            seedBufferOut.writeLong(System.nanoTime())
            seedBufferOut.writeInt(Process.myPid())
            seedBufferOut.writeInt(Process.myUid())
            seedBufferOut.write(BUILD_FINGERPRINT_AND_DEVICE_SERIAL)
            seedBufferOut.close()
            return seedBuffer.toByteArray()
        } catch (e: IOException) {
            throw SecurityException("Failed to generate seed", e)
        }

    }
}
/** Hidden constructor to prevent instantiation.  */
