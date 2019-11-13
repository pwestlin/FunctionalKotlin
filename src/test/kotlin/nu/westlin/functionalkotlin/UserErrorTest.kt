package nu.westlin.functionalkotlin

import nu.westlin.functionalkotlin.UserError.UserAlreadyExistError
import nu.westlin.functionalkotlin.UserError.UserDoesNotExistError
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserErrorTest {

    @Test
    fun `equals and hashCode`() {
        val error = UserAlreadyExistError("foo bar")

        assertThat(error).isEqualTo(UserAlreadyExistError(error.errorMessage))
        assertThat(error).isNotEqualTo(UserAlreadyExistError(error.errorMessage + "foo"))
        assertThat(error).isNotEqualTo(UserDoesNotExistError(error.errorMessage))

        assertThat(error.hashCode()).isEqualTo(UserAlreadyExistError::class.hashCode() + 32 * error.errorMessage.hashCode())
        assertThat(error.hashCode()).isNotEqualTo(UserAlreadyExistError(error.errorMessage + "foo").hashCode())
    }
}