package nu.westlin.functionalkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@Suppress("UsePropertyAccessSyntax")
internal class EitherTest {

    private val left = Either.Left("left")
    private val right = Either.Right("right")

    @Test
    fun `create Left`() {
        val either: Either<String, Nothing> = left

        assertThat(either.isLeft).isTrue()
        assertThat(either.isLeft()).isTrue()

        assertThat(either.isRight).isFalse()
        assertThat(either.isRight()).isFalse()
    }

    @Test
    fun `create Right`() {
        val either: Either<Nothing, String> = right

        assertThat(either.isLeft).isFalse()
        assertThat(either.isLeft()).isFalse()

        assertThat(either.isRight).isTrue()
        assertThat(either.isRight()).isTrue()
    }

    @Test
    fun `fold Left`() {
        val result = left.fold(
            { left -> left },
            { right -> right }
        )
        assertThat(result).isEqualTo("left")
    }

    @Test
    fun `fold right`() {
        val result = right.fold(
            { left -> left },
            { right -> right }
        )
        assertThat(result).isEqualTo("right")
    }
}