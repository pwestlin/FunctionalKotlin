package nu.westlin.functionalkotlin

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class PresenterTest {

    private val service = mockk<UserService>(relaxed = true)
    private val presenter = Presenter(service)

    @Test
    fun `add two users and print them`() {
        val users = listOf(user1, user2)
        every { service.add(users) } returns Either.Right(Unit)

        presenter.addUsersAndPrintThem(users)

        verify { service.add(users) }

        // println is not possible to verify...
    }

    @Test
    fun `add two users and print them when a user already exist`() {
        val users = listOf(user1)
        every { service.add(users) } returns Either.Right(Unit) andThen Either.Left(UserError.UserAlreadyExistError("User $user1 already exist"))

        presenter.addUsersAndPrintThem(users)
        verify { service.add(users) }

        presenter.addUsersAndPrintThem(users)
        verify { service.add(users) }

        // println is not possible to verify...
    }
}