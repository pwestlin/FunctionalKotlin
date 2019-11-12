package nu.westlin.functionalkotlin

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserRepositoryTest {

    private lateinit var repository: UserRepository

    @Suppress("unused")
    @BeforeEach
    fun initData() {
        repository = UserRepository().apply {
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

    @Test
    fun `add a user`() {
        repository.add(user3)

        assertThat(repository.get(user3.name)).isEqualTo(user3)
    }

    @Test
    fun `add a user that already exist`() {
        assertThatThrownBy { repository.add(user1) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("User $user1 already exist.")
    }

    @Test
    fun `remove a user`() {
        repository.remove(user2)

        assertThat(repository.get(user2.name)).isNull()
    }

    @Test
    fun `remove a user that does not exist`() {
        assertThatThrownBy { repository.remove(user3) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("User $user3 does not exist.")
    }
}
