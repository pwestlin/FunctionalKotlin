package nu.westlin.functionalkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@Suppress("UNUSED_EXPRESSION", "UNCHECKED_CAST")
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
        assertThat(repository.all()().fold({ it }, { it }) as List<User>).containsExactlyInAnyOrder(user1, user2)
    }

    @Test
    fun `get a user`() {
        assertThat(repository.get(user1.name).fold({ it }, { it })).isEqualTo(user1)
    }

    @Test
    fun `get a user that does not exist`() {
        repository.get("doesnotexist").fold({ it }, { it }).let { error ->
            assertThat(error).isInstanceOf(UserError.UserDoesNotExistError::class.java)
            assertThat((error as UserError.UserDoesNotExistError).errorMessage).isEqualTo("User with name doesnotexist does not exist")
        }
    }

    @Test
    fun `add a user`() {
        assertThat(repository.add(user3).fold(
            { it },
            { it }
        )).isEqualTo(Unit)

        assertThat(repository.get(user3.name).fold({ it }, { it })).isEqualTo(user3)
    }

    @Test
    fun `add a user that already exist`() {
        assertThat(repository.add(user1).fold(
            { it.errorMessage },
            { it }
        )).isEqualTo("User $user1 already exist.")

        assertThat(
            repository.get(user3.name).fold(
                { it.errorMessage },
                { it })
        ).isEqualTo("User with name ${user3.name} does not exist")
    }

    @Test
    fun `remove a user`() {
        assertThat(repository.remove(user2).fold(
            { it },
            { it }
        )).isEqualTo(Unit)

        assertThat(
            repository.get(user2.name).fold(
                { it.errorMessage },
                { it })
        ).isEqualTo("User with name ${user2.name} does not exist")
    }

    @Test
    fun `remove a user that does not exist`() {
        assertThat(repository.remove(user3).fold(
            { it.errorMessage },
            { it }
        )).isEqualTo("User $user3 does not exist.")
    }
}
