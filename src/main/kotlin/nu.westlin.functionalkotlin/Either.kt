package nu.westlin.functionalkotlin

sealed class Either<out A, out B> {
    internal abstract val isLeft: Boolean
    internal abstract val isRight: Boolean

    fun isLeft(): Boolean = isLeft
    fun isRight(): Boolean = isRight

    inline fun <C> fold(ifLeft: (A) -> C, ifRight: (B) -> C): C {
        return when (this) {
            is Left -> ifLeft(a)
            is Right -> ifRight(b)
        }
    }

    companion object {
        fun <A> left(left: A): Either<A, Nothing> = Left(left)

        fun <B> right(right: B): Either<Nothing, B> = Right(right)
    }

    data class Left<out A> internal constructor(val a: A) : Either<A, Nothing>() {
        override val isLeft: Boolean
            get() = true
        override val isRight: Boolean
            get() = false

        companion object {
            operator fun <A> invoke(a: A): Either<A, Nothing> = Left(a)
        }
    }

    data class Right<out B> internal constructor(val b: B) : Either<Nothing, B>() {
        override val isLeft: Boolean
            get() = false
        override val isRight: Boolean
            get() = true

        companion object {
            operator fun <B> invoke(b: B): Either<Nothing, B> = Right(b)
        }
    }
}

