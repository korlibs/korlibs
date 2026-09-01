package korlibs.logger.test

import korlibs.logger.Logger
import kotlin.test.Test
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoggerMultithreadedTest {
    @Test
    fun multithreaded() = runBlocking {
        val worker = Dispatchers.IO
        val logger = Logger("WorkerLogger")
        val changeLoggerLevel: () -> Logger.Level = {
            logger.level = Logger.Level.INFO
            logger.level
        }
        val future = CoroutineScope(worker).async { changeLoggerLevel() }
        val result = future.await()

        assertTrue { result == Logger.Level.INFO }
        assertTrue { logger.level == Logger.Level.INFO }

        val logger2 = Logger("WorkerLogger")
        assertSame(logger, logger2)

        val getLoggerLevel: () -> Logger.Level = {
            logger2.level
        }
        val future2 = CoroutineScope(worker).async { getLoggerLevel() }
        val result2 = future2.await()

        assertTrue { result2 == Logger.Level.INFO }
        assertTrue { logger2.level == Logger.Level.INFO }

        assertTrue { logger.level == logger2.level }

        logger.level = Logger.Level.DEBUG
        assertTrue { logger.level == Logger.Level.DEBUG }
        assertTrue { logger.level == logger2.level }
    }
}
