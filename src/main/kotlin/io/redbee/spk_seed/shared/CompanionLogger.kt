package io.redbee.spk_seed.shared

import reactor.util.Logger
import reactor.util.Loggers

abstract class CompanionLogger {

    @Suppress("JAVA_CLASS_ON_COMPANION")
    val log: Logger by lazy { Loggers.getLogger(javaClass.enclosingClass) }

    inline fun <T> T.log(block: Logger.(T) -> Unit): T =
        also { block(log, this) }

    infix fun Logger.trace(message: String) = log.trace(message)
    infix fun Logger.info(message: String) = log.info(message)
    infix fun Logger.error(message: String) = log.error(message)
}
