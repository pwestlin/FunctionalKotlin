package nu.westlin.functionalkotlin

import java.util.*
import kotlin.streams.asSequence

private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
private const val DIGITS = "0123456789"
private const val ALL = CHARACTERS + DIGITS

/**
 * Returns a random string of [CHARACTERS] with [length].
 */
fun randomString(length: Int): String {
    return Random().ints(length.toLong(), 0, ALL.length)
        .asSequence()
        .map(ALL::get)
        .joinToString("")
}

fun User.Companion.random() = User(name = randomString(12))