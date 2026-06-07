package korlibs.io.i18n

import korlibs.io.util.i18n.Language
import korlibs.logger.Logger
import kotlin.test.Test

class LanguageTest {
    val logger = Logger("LanguageTest")
	@Test
	fun testThatLanguageCurrentDoNotThrowExceptions() {
        val string = "Language.CURRENT: ${Language.CURRENT}" // Ensure Language.CURRENT is called by putting it outside logger
        logger.debug { string }
	}
}
