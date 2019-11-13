package nu.westlin.functionalkotlin

import nu.westlin.functionalkotlin.Logger.Level.ERROR
import nu.westlin.functionalkotlin.Logger.Level.INFO
import java.time.Instant
import kotlin.reflect.KClass

private const val CLASS_NAME_WIDTH = 20

// Why not implement my own Logger... :)
class Logger(private val clazz: KClass<*>) {

    private enum class Level {
        INFO, ERROR
    }

    fun info(msg: String) {
        log(msg, INFO)
    }

    fun error(msg: String) {
        log(msg, ERROR)
    }

    private fun log(msg: String, level: Level) {
        println("${Instant.now()} [${className(clazz)}] ${level.toString().padEnd(ERROR.toString().length)}: $msg - ${Thread.currentThread()}")
    }

    private fun className(clazz: KClass<*>): String {
        return clazz.simpleName?.let { name ->
            when {
                name.length > CLASS_NAME_WIDTH -> name.substring(name.length - CLASS_NAME_WIDTH)
                name.length < CLASS_NAME_WIDTH -> name.padEnd(CLASS_NAME_WIDTH, ' ')
                else -> name
            }
        } ?: " ".repeat(CLASS_NAME_WIDTH)
    }

    companion object {
        inline operator fun <reified C> invoke() = Logger(C::class)
    }
}