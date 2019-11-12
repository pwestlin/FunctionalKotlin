package nu.westlin.functionalkotlin

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class PresenterTest {

    private val service = mockk<UserService>(relaxed = true)
    private val presenter = Presenter(service)

    @Test
    fun `add two users and print them`() {
        val users = listOf(user1, user2)
        presenter.addUsersAndPrintThem(users)

        verify { service.add(users) }

        // println is not possible to verify...
    }
}