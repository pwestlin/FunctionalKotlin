package nu.westlin.functionalkotlin

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@Suppress("UNUSED_EXPRESSION")
internal class UserServiceTest {

    private val repository = mockk<UserRepository>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    private val service = UserService(repository, logger)

    @Test
    fun `add one user`() {
        val user = user1
        every { repository.add(user) } returns Either.Right(Unit)

        assertThat(service.add(user).fold({ it }, { it })).isEqualTo(Unit)

        verify { repository.add(user) }
    }

    @Test
    fun `add a user that already exist`() {
        val user = user1
        val errorMessage = "User $user already exist"
        every { repository.add(user) } returns Either.left(UserError.UserAlreadyExistError(errorMessage))

        assertThat(service.add(user).fold({ it.errorMessage }, { it })).isEqualTo(errorMessage)

        verify {
            repository.add(user)
            logger.error(errorMessage)
        }
    }

    @Test
    fun `add list of users`() {
        every { repository.add(user1) } returns Either.Right(Unit)
        every { repository.add(user2) } returns Either.Right(Unit)

        service.add(listOf(user1, user2))

        verify {
            repository.add(user1)
            repository.add(user2)
        }
    }

    @Test
    fun `get a user`() {
        val user = user1
        every { repository.get(user1.name) } returns user
        assertThat(service.getUser(user.name)).isEqualTo(user)
    }
}