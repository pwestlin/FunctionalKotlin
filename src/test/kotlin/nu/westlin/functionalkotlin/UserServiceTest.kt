package nu.westlin.functionalkotlin

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserServiceTest {

    private val repository = mockk<UserRepository>(relaxed = true)
    private val service = UserService(repository)

    @Test
    fun `add one user`() {
        service.add(user1)
        verify { repository.add(user1) }
    }

    @Test
    fun `add list of users`() {
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