package nu.westlin.functionalkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class UserRepositoryTest {

    private val repository = UserRepository()

    @Suppress("unused")
    @BeforeAll
    fun initData() {
        repository.apply {
            add(user1)
            add(user2)
        }
    }

    @Test
    fun `get all users`() {
        assertThat(repository.all()).containsExactlyInAnyOrder(user1, user2)
    }

    @Test
    fun `get a user`() {
        assertThat(repository.get(user1.name)).isEqualTo(user1)
    }

    @Test
    fun `get a user that does not exist`() {
        assertThat(repository.get("foo")).isNull()
    }
}
