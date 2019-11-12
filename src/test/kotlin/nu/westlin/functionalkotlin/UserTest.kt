package nu.westlin.functionalkotlin

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class UserTest {

    @Test
    fun `create user with name of 6-16 characters`() {
        (6..16).forEach { User(randomString(it)) }
    }

    @Test
    fun `create user with too short name`() {
        assertThatThrownBy { User("12345") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Name has to be 6-16 characters long")
    }

    @Test
    fun `create user with too long name`() {
        assertThatThrownBy { User("12345678901234567") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Name has to be 6-16 characters long")
    }
}