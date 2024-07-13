package korlibs.io.lang

import kotlin.test.Test
import kotlin.test.assertEquals

class CharsetAppleTest {
    private fun encodeDecodeAndVerify(charsetName: String, originalString: String) {
        val charset = Charset.forName(charsetName)

        // Encode the string to byte array using the specified charset
        val encodedBytes = originalString.toByteArray(charset)

        // Decode the byte array back to a string using the specified charset
        val decodedString = encodedBytes.toString(charset)

        // Verify that the original string and the decoded string are the same
        assertEquals(originalString, decodedString, "The decoded string does not match the original string for charset $charsetName")
    }

    @Test
    fun testEncodingDecoding() {
        val originalStringUtf32 = "Hello, UTF-32 🌍"
        val originalStringOther = "Hello, Charset Test"

        // Test UTF-32 encodings
        encodeDecodeAndVerify("UTF-32", originalStringUtf32)
        encodeDecodeAndVerify("UTF-32BE", originalStringUtf32)
        encodeDecodeAndVerify("UTF-32LE", originalStringUtf32)

        // Test additional charsets
        encodeDecodeAndVerify("NEXTSTEP", originalStringOther)
        encodeDecodeAndVerify("EUC-JP", originalStringOther)
    }
}